package JAO.tests.clients.AO;

import JAO.AO.Proxy;
import JAO.tests.clients.AbstractTestClient;

public abstract class AbstractTestAOClient extends AbstractTestClient {
    protected final Proxy<Double> proxy;
    public AbstractTestAOClient(int index, boolean verbose, Proxy<Double> proxy) {
        super(index, verbose);
        this.proxy = proxy;
    }
}
