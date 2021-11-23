package JAO.tests.clients.monitor;

import JAO.monitor.interfaces.IMultiMonitor;
import JAO.tests.utility.UnitOfWork;
import JAO.utility.DebugEntry;
import JAO.utility.DebugPrint;

public class LockTestConsumer extends AbstractTestMonitorClient {
    private final int workUnits;
    private final int toConsume;
    public LockTestConsumer(int index, boolean verbose, IMultiMonitor<Double> monitor, int toConsume, int workUnits) {
        super(index, verbose, monitor);
        this.workUnits = workUnits;
        this.verbose = verbose;
        this.toConsume = toConsume;
    }

    @Override
    public void run(){
        while(!stop){
            if(verbose)
                System.out.printf("LockConsumer %d: taking items.%n", super.index);

            int toConsume = super.rng.nextInt(this.toConsume, this.toConsume + 3);

            for(int i = 0; i < this.workUnits; ++i) {
                UnitOfWork.doUnitOfWork(i + 2);
            }

            var result = super.monitor.popElements(toConsume);
            if(verbose)
                System.out.printf("LockConsumer %d: done own work.%n", super.index);

            if(verbose){
                System.out.println("LockConsumer %d: got result: ".formatted(super.index) + result.toString());
            }

            var entry = new DebugEntry();
            entry.unitsOfWork = this.workUnits;
            DebugPrint.addOperation(0, entry);
        }
    }

}
