package JAO;

import java.util.ArrayList;

public class Proxy <T>{

    private Scheduler<T> scheduler;
    private MessageQueue<T> messageQueue;

    public Proxy(){
        scheduler = new Scheduler<T>();
        messageQueue = new MessageQueue<T>();
    }

    public void put(Iterable<T> input, int n);
    public ArrayList<T> take(int n);
}
