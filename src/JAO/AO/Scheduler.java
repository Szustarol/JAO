package JAO.AO;

import JAO.AO.MethodRequest.IMethodRequest;
import JAO.AO.message.MessageQueue;

public class Scheduler<T>{

    private final MessageQueue<T> messageQueue;
    private final Servant<T> servant;

    private class ServantHandler<T> implements Runnable{
        volatile boolean stop = false;
        @Override
        public void run() { //a.k.a. dispatch
            while(!stop){
                var request = messageQueue.deque();
                request.call();
            }
        }
    }

    private final ServantHandler<T> servantHandler;

    public void enqueue(IMethodRequest<T> request){
        request.setServant(this.servant);
        this.messageQueue.enqueue(request);
    }

    public Scheduler(int capacity){
        this.messageQueue = new MessageQueue<T>();
        servantHandler = new ServantHandler<T>();
        var servantThread = new Thread(servantHandler);
        servantThread.start();
        servant = new Servant<T>(capacity, false);
    }

    public void stop(){
        this.servantHandler.stop = true;
    }


}
