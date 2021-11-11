import JAO.Proxy;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main {
    public static void main(String [] args){
        var proxy = new Proxy<Double>();
        AbstractClient [] clients = {
                new Consumer(0, true, proxy),
                new Consumer(1, true, proxy),
                new Consumer(2, true, proxy),
                new Producer(0, true, proxy),
                new Producer(1, true, proxy),
                new Producer(2, true, proxy)
        };

        ArrayList<Thread> threads = new ArrayList<Thread>();
        for(var client : clients){
            threads.add(new Thread(client));
        }

        for(var thread : threads){
            thread.start();
        }

    }
}
