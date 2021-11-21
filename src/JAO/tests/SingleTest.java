package JAO.tests;

import JAO.tests.utility.DebugEntry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class SingleTest {
    private final TestParameters parameters;
    private DebugEntry resultsAO = new DebugEntry();
    private DebugEntry resultsBuffer = new DebugEntry();

    public SingleTest(TestParameters parameters) {
        this.parameters = parameters;
    }

    public void runTest(int seconds)  {
        AOTest aoTest = new AOTest(this.parameters);
        System.out.println("Starting test...");
        resultsAO = aoTest.runTest(5);
        try {
            Thread.sleep(50);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("Finished test.");
    }

    private void write(final String filename, final String s) throws IOException {
        Files.write(Paths.get(filename), (s + "\n").getBytes(), APPEND, CREATE);
    }

    public void appendResultToFile(final String filename) {
        String resultString = parameters.toString() + ", " + resultsAO.toString() + ", " + resultsBuffer.toString();

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
