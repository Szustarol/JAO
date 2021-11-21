package JAO.tests;

import JAO.Proxy;
import JAO.client.AbstractClient;
import JAO.tests.utility.DebugEntry;
import JAO.tests.utility.DebugPrint;

import java.util.concurrent.ThreadLocalRandom;


public class AOConsumer extends AbstractClient {
    private final int toConsume;
    private final int workUnits;

    public AOConsumer(int index, boolean verbose, Proxy<Double> proxy, int toConsume, int workUnits) {
        super(index, verbose, proxy);
        this.toConsume = toConsume;
        this.workUnits = workUnits;
    }

    public void run(){
        while(!stop){
            if(verbose)
                System.out.printf("AOConsumer %d: taking items.%n", index);

            int toConsume = ThreadLocalRandom.current().nextInt(this.toConsume, this.toConsume + 3);
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