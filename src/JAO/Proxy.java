package JAO;

import JAO.MethodRequest.Get;
import JAO.MethodRequest.Put;

import java.util.ArrayList;

public class Proxy <T>{

    private Scheduler<T> scheduler;

    public Proxy(){
        scheduler = new Scheduler<T>();
    }

    public void put(Iterable<T> input, int n){
        var request = new Put<T>(input, n);
        scheduler.enqueue(request);
    }

    public MessageFuture<T> take(int n){
        var result = new MessageFuture<T>();
        var request = new Get<T>(n, result);
        scheduler.enqueue(request);
        return result;
    }
}
