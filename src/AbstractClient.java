import JAO.Proxy;

public abstract class AbstractClient implements Runnable{
    protected boolean verbose;
    protected int index;
    protected volatile boolean stop = false;
    protected Proxy<Double> proxy;

    public AbstractClient(int index, boolean verbose, Proxy<Double> proxy){
        this.index = index;
        this.verbose = verbose;
        this.proxy = proxy;
    }

    public void stop(){
        this.stop = true;
    }

    public void safeSleep(long millis){
        try{
            Thread.sleep(millis);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }

    }
}
