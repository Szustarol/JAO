package JAO.tests.tests;

import JAO.AO.Proxy;
import JAO.tests.clients.AO.AOConsumer;
import JAO.tests.clients.AO.AOProducer;
import JAO.tests.utility.TestParameters;

import java.util.ArrayList;


public class AOTest extends BaseTest {
    private final Proxy<Double> proxy;

    public AOTest(TestParameters parameters) {
        super(parameters);
        this.proxy = new Proxy<>(parameters.bufferSize);
    }

    @Override
    protected Iterable<Runnable> createClients() {
        var clients = new ArrayList<Runnable>();
        for(int i = 0; i < parameters.consumers; ++i) {
            var consumer = new AOConsumer(i, false, proxy, parameters.pollWorkUnits, parameters.consumersWorkUnits);
            clients.add(consumer);
        }
        for(int i = 0; i < parameters.producers; ++i) {
            var producer = new AOProducer(i + parameters.consumers, false, proxy, parameters.insertionWorkUnits, parameters.producersWorkUnits);
            clients.add(producer);
        }
        return clients;
    }

}
