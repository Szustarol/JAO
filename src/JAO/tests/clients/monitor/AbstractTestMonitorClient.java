package JAO.tests.clients.monitor;

import JAO.monitor.interfaces.IMultiMonitor;
import JAO.tests.clients.AbstractTestClient;

public abstract class AbstractTestMonitorClient extends AbstractTestClient {
    protected final IMultiMonitor<Double> monitor;
    public AbstractTestMonitorClient(int index, boolean verbose, IMultiMonitor<Double> monitor) {
        super(index, verbose);
        this.monitor = monitor;
    }
}
