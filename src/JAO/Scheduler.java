package JAO;

public class Scheduler<T>{

    private class ServantHandler implements Runnable{
        volatile boolean stop = false;

        @Override
        public void run() { //a.k.a. dispatch
            while(!stop){

            }
        }
    }

    private final ServantHandler servantHandler;

    public Scheduler(){
        servantHandler = new ServantHandler();
    }

    public void stop(){
        this.servantHandler.stop = true;
    }


}
