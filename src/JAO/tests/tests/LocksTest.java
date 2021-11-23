package JAO.tests.tests;

import JAO.monitor.locks.ThreeLocksMultiMonitor;
import JAO.tests.clients.monitor.LockTestConsumer;
import JAO.tests.clients.monitor.LockTestProducer;
import JAO.tests.utility.TestParameters;

import java.util.ArrayList;

public class LocksTest extends BaseTest{
    private final ThreeLocksMultiMonitor<Double> monitor;

    public LocksTest(TestParameters parameters) {
        super(parameters);
        this.monitor = new ThreeLocksMultiMonitor<>(parameters.bufferSize, true);
    }

    @Override
    protected Iterable<Runnable> createClients() {
        var clients = new ArrayList<Runnable>();
        for(int i = 0; i < parameters.consumers; ++i) {
            var consumer = new LockTestConsumer(i, false, monitor, parameters.pollWorkUnits, parameters.consumersWorkUnits);
            clients.add(consumer);
        }
        for(int i = 0; i < parameters.producers; ++i) {
            var producer = new LockTestProducer(i + parameters.consumers, false, monitor, parameters.insertionWorkUnits, parameters.producersWorkUnits);
            clients.add(producer);
        }
        return clients;
    }
}
