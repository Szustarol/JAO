package JAO.tests.clients.AO;

import JAO.AO.Proxy;
import JAO.tests.utility.UnitOfWork;
import JAO.utility.DebugEntry;
import JAO.utility.DebugPrint;


public class AOConsumer extends AbstractTestAOClient {
    private final int toConsume;
    private final int workUnits;

    public AOConsumer(int index, boolean verbose, Proxy<Double> proxy, int toConsume, int workUnits) {
        super(index, verbose, proxy);
        this.toConsume = toConsume;
        this.workUnits = workUnits;
    }

    @Override
    public void run(){
        while(!stop){
            if(verbose)
                System.out.printf("AOConsumer %d: taking items.%n", index);

            int toConsume = super.rng.nextInt(this.toConsume, this.toConsume + 3);
            var future = proxy.take(toConsume);

            for(int i = 0; i < this.workUnits; ++i) {
                UnitOfWork.doUnitOfWork(i + 2);
            }

            if(verbose)
                System.out.printf("AOConsumer %d: done own work.%n", index);

            var result = future.getResult();
            if(verbose){
                System.out.println("AOConsumer %d: got result: ".formatted(index) + result.toString());
            }

            var entry = new DebugEntry();
            entry.unitsOfWork = this.workUnits;
            DebugPrint.addOperation(0, entry);
        }
    }

}