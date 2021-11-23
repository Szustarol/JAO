package JAO.tests;

import JAO.tests.tests.AOTest;
import JAO.tests.tests.LocksTest;
import JAO.tests.utility.TestParameters;
import JAO.utility.DebugEntry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class SingleTest {
    private final TestParameters parameters;
    private DebugEntry resultsAO = new DebugEntry();
    private DebugEntry resultsLock = new DebugEntry();

    public SingleTest(TestParameters parameters) {
        this.parameters = parameters;
    }

    public void runTest(int seconds)  {
        AOTest aoTest = new AOTest(this.parameters);
        System.out.println("Starting AO test " + parameters.toString() + "...");
        resultsAO = aoTest.runTest(seconds);
        try {
            Thread.sleep(50);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("Finished AO test.");

        LocksTest threeLocksTest = new LocksTest(this.parameters);
        System.out.println("Starting Lock test " + parameters.toString() + "...");
        resultsLock = threeLocksTest.runTest(seconds);
        try {
            Thread.sleep(50);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("Finished Lock test.");
    }

    private void write(final String filename, final String s) throws IOException {
        Files.write(Paths.get(filename), (s + "\n").getBytes(), APPEND, CREATE);
    }

    public void appendResultToFile(final String filename) {
        String resultString = parameters.toString() + ", " + resultsAO.toString() + ", " + resultsLock.toString();

        while(true) {
            try {
                System.out.println("Saving test...");
                write(filename, resultString);
                System.out.println("Test saved.");
                break;
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }


}
