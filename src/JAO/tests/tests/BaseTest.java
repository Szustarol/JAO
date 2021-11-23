package JAO.tests.tests;

import JAO.tests.utility.TestParameters;
import JAO.utility.DebugEntry;
import JAO.utility.DebugPrint;

import java.util.ArrayList;

public abstract class BaseTest {
    protected final TestParameters parameters;

    public BaseTest(TestParameters parameters) {
        this.parameters = parameters;
    }

    public DebugEntry runTest(int seconds)  {
        DebugPrint.clear();
        DebugPrint.register(0, "All");
        var clients = this.createClients();
        var threads = this.createThreads(clients);
        this.runThreads(threads);
        try {
            for(int secondsLeft = seconds; secondsLeft > 0; --secondsLeft) {
                if(secondsLeft % 10 == 0) {
                    System.out.println("Test finished in " + secondsLeft + " seconds.");
                }
                Thread.sleep(1000);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        var results = DebugPrint.getSumUpResults();
        this.killThreads(threads);
        return results;
    }

    protected abstract Iterable<Runnable> createClients();

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
