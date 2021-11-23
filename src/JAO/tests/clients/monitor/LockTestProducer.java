package JAO.tests.clients.monitor;

import JAO.monitor.interfaces.IMultiMonitor;
import JAO.tests.utility.UnitOfWork;
import JAO.utility.DebugEntry;
import JAO.utility.DebugPrint;

import java.util.ArrayList;

public class LockTestProducer extends AbstractTestMonitorClient {
    private final int toProduce;
    private final int workUnits;

    public LockTestProducer(int index, boolean verbose, IMultiMonitor<Double> monitor, int toProduce, int workUnits) {
        super(index, verbose, monitor);
        this.workUnits = workUnits;
        this.toProduce = toProduce;
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
                System.out.println("LockProducer %d: inserting items: ".formatted(index) + data.toString());


            for(int i = 0; i < this.workUnits; ++i) {
                UnitOfWork.doUnitOfWork(i + 2);
            }

            if(verbose)
                System.out.printf("LockProducer %d: done bookkeeping.%n", index);

            super.monitor.putElements(data, data.size());
            if(verbose){
                System.out.printf("LockProducer %d: done insertion.%n", index);
            }

            var entry = new DebugEntry();
            entry.unitsOfWork = this.workUnits;
            DebugPrint.addOperation(0, entry);
        }
    }
}
