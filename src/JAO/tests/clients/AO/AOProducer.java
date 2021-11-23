package JAO.tests.clients.AO;

import JAO.AO.Proxy;
import JAO.tests.utility.UnitOfWork;
import JAO.utility.DebugEntry;
import JAO.utility.DebugPrint;

import java.util.ArrayList;

public class AOProducer extends AbstractTestAOClient {
    private final int toProduce;
    private final int workUnits;

    public AOProducer(int index, boolean verbose, Proxy<Double> proxy, int toProduce, int workUnits) {
        super(index, verbose, proxy);
        this.toProduce = toProduce;
        this.workUnits = workUnits;
    }

    @Override
    public void run(){
        while(!stop){
            int toProduce = super.rng.nextInt(this.toProduce, this.toProduce + 3);
            ArrayList<Double> data = new ArrayList<>(toProduce);
            for(double i = 0; i < toProduce; i += 1) {
                data.add(i);
            }
            if(verbose)
                System.out.println("AOProducer %d: inserting items: ".formatted(super.index) + data.toString());

            var result = proxy.put(data, data.size());

            for(int i = 0; i < this.workUnits; ++i) {
                UnitOfWork.doUnitOfWork(i + 2);
            }
            if(verbose)
                System.out.printf("AOProducer %d: done bookkeeping.%n", super.index);

            result.getResult();
            if(verbose){
                System.out.printf("AOProducer %d: done insertion.%n", super.index);
            }

            var entry = new DebugEntry();
            entry.unitsOfWork = this.workUnits;
            DebugPrint.addOperation(0, entry);
        }
    }
}
