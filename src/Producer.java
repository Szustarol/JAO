import JAO.Proxy;

import java.util.ArrayList;

public class Producer extends AbstractClient{


    public Producer(int index, boolean verbose, Proxy<Double> proxy) {
        super(index, verbose, proxy);
    }

    public void run(){

        ArrayList<Double> data = new ArrayList<>(20);
        for(double i = 0; i < 20; i += 1){
            data.add(i);
        }

        while(!stop){
            if(verbose)
                System.out.println("Producer inserting items.");

            var result = proxy.put(data, data.size());

            //Do something
            safeSleep(20);
            if(verbose){
                result.getResult();
                System.out.println("Producer - done bookkeeping.");
            }
        }
    }
}
