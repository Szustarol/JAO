package JAO.tests.clients;

import java.util.Random;

public abstract class AbstractTestClient  implements Runnable {
    protected boolean verbose;
    protected int index;
    protected volatile boolean stop = false;
    protected final Random rng = new Random();

    public AbstractTestClient(int index, boolean verbose){
        this.index = index;
        this.verbose = verbose;
        rng.setSeed(index);
    }

    public void stop(){
        this.stop = true;
    }
}
