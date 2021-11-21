package JAO.tests;

import JAO.Proxy;
import JAO.client.AbstractClient;
import JAO.tests.utility.DebugEntry;
import JAO.tests.utility.DebugPrint;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class AOTest {
    private final TestParameters parameters;
    private final Proxy<Double> proxy;


    public AOTest(TestParameters parameters) {
        this.parameters = parameters;
        this.proxy = new Proxy<>(parameters.bufferSize);
    }

    public DebugEntry runTest(int seconds)  {
        DebugPrint.clear();
        DebugPrint.register(0, "All");
        var clients = this.createClients();
        var threads = this.createThreads(clients);
        this.runThreads(threads);
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        var results = DebugPrint.getSumUpResults();
        this.killThreads(threads);
        return results;
    }

    private Iterable<Runnable> createClients() {
        var clients = new ArrayList<Runnable>();
        for(int i = 0; i < parameters.consumers; ++i) {
            var consumer = new AOConsumer(i, false, proxy, parameters.pollWorkUnits, parameters.consumersWorkUnits);
            clients.add(consumer);
        }
        for(int i = 0; i < parameters.producers; ++i) {
            var producer = new AOProducer(i, false, proxy, parameters.insertionWorkUnits, parameters.producersWorkUnits);
            clients.add(producer);
        }
        return clients;
    }

    private @NotNull
    Iterable<Thread> createThreads(Iterable<Runnable> runnables) {
        var threads = new ArrayList<Thread>();
        for(var runnable : runnables) {
            threads.add(new Thread(runnable));
        }
        return threads;
    }

    private void runThreads(Iterable<Thread> threads) {
        for(var thread : threads) {
            thread.start();
        }
    }

    private void killThreads(Iterable<Thread> threads) {
        for(var thread : threads) {
            thread.stop();
        }
    }
}
