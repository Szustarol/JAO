import JAO.Proxy;
import JAO.client.AbstractClient;
import JAO.client.Consumer;
import JAO.client.Producer;
import JAO.tests.AllTests;

import java.util.ArrayList;

public class Main {
    public static void main(String [] args){
//        var proxy = new Proxy<Double>(20);
//        AbstractClient[] clients = {
//                new Consumer(0, true, proxy),
//                new Consumer(1, true, proxy),
//                new Consumer(2, true, proxy),
//                new Producer(0, true, proxy),
//                new Producer(1, true, proxy),
//                new Producer(2, true, proxy)
//        };
//
//        ArrayList<Thread> threads = new ArrayList<Thread>();
//        for(var client : clients){
//            threads.add(new Thread(client));
//        }
//
//        for(var thread : threads){
//            thread.start();
//        }
        AllTests tests = new AllTests();
        tests.run(3);
    }
}
