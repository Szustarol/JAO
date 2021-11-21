package JAO.tests;

import JAO.tests.utility.DebugEntry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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



    }

    private void write(final String filename, final String s) throws IOException {
        Files.writeString(
                Path.of(System.getProperty("java.io.tmpdir"), filename),
                s + System.lineSeparator(),
                CREATE, APPEND
        );
    }

    public void appendResultToFile(final String filename) {
        String resultString = parameters.toString() + ", " + resultsAO.toString() + ", " + resultsBuffer.toString();

        while(true) {
            try {
                write(filename, resultString);
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
