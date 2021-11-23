package JAO.AO.client;

import JAO.AO.Proxy;

public class Consumer extends AbstractClient{


    public Consumer(int index, boolean verbose, Proxy<Double> proxy) {
        super(index, verbose, proxy);
    }

    public void run(){
        while(!stop){
            if(verbose)
                System.out.printf("JAO.AO.client.Consumer %d: taking items.%n", index);

            var future = proxy.take(4);

            //Do something
            safeSleep(130);

            if(verbose)
                System.out.printf("JAO.AO.client.Consumer %d: done bookkeeping.%n", index);

            var result = future.getResult();
            if(verbose){
                System.out.println("JAO.AO.client.Consumer %d: got result: ".formatted(index) + result.toString());
            }
        }
    }

}
