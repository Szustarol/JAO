package JAO.AO;

import JAO.AO.MethodRequest.Get;
import JAO.AO.MethodRequest.Put;
import JAO.AO.message.MessageFuture;

public class Proxy <T>{

    private final Scheduler<T> scheduler;

    public Proxy(int capacity){
        scheduler = new Scheduler<T>(capacity);
    }

    public MessageFuture<T> put(Iterable<T> input, int n){
        var result = new MessageFuture<T>();
        var request = new Put<T>(input, n, result);
        scheduler.enqueue(request);
        return result;
    }

    public MessageFuture<T> take(int n){
        var result = new MessageFuture<T>();
        var request = new Get<T>(n, result);
        scheduler.enqueue(request);
        return result;
    }
}
