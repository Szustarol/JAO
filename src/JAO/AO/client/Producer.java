package JAO.AO.client;

import JAO.AO.Proxy;

import java.util.ArrayList;

public class Producer extends AbstractClient{


    public Producer(int index, boolean verbose, Proxy<Double> proxy) {
        super(index, verbose, proxy);
    }

    public void run(){

        ArrayList<Double> data = new ArrayList<>(10);
        for(double i = 0; i < 10; i += 1){
            data.add(i);
        }

        while(!stop){
            if(verbose)
                System.out.println("JAO.AO.client.Producer %d: inserting items: ".formatted(index) + data.toString());

            var result = proxy.put(data, data.size());

            safeSleep(23);
            if(verbose)
                System.out.printf("JAO.AO.client.Producer %d: done bookkeeping.%n", index);

            if(verbose){
                result.getResult();
                System.out.printf("JAO.AO.client.Producer %d: done insertion.%n", index);
            }
        }
    }
}
