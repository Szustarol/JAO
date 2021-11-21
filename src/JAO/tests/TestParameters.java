package JAO.tests;

import java.util.concurrent.ThreadLocalRandom;

public class TestParameters {
    public int bufferSize;
    public int consumers;
    public int consumersWorkUnits;
    public int producers;
    public int producersWorkUnits;
    public int insertionWorkUnits;
    public int pollWorkUnits;

    public static TestParameters randomTest(int bufferSize, int consumers, int consumersWorkUnits, int producers, int producersWorkUnits) {
        var test = new TestParameters();
        test.bufferSize = ThreadLocalRandom.current().nextInt(1, bufferSize + 1);
        test.consumers = ThreadLocalRandom.current().nextInt(1, consumers + 1);
        test.consumersWorkUnits = ThreadLocalRandom.current().nextInt(1, consumersWorkUnits + 1);
        test.producersWorkUnits = ThreadLocalRandom.current().nextInt(1, producersWorkUnits + 1);
        test.insertionWorkUnits = ThreadLocalRandom.current().nextInt(1, test.bufferSize / 2 + 1);
        test.producers = ThreadLocalRandom.current().nextInt(1, producers + 1);
        test.pollWorkUnits = ThreadLocalRandom.current().nextInt(1, test.bufferSize / 2 + 1);
        return test;
    }

    @Override
    public String toString() {
        return  bufferSize +
                ", " + consumers +
                ", " + consumersWorkUnits +
                ", " + producers +
                ", " + producersWorkUnits +
                ", " + insertionWorkUnits +
                ", " + pollWorkUnits;
    }
}
