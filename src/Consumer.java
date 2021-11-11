import JAO.Proxy;

public class Consumer extends AbstractClient{


    public Consumer(int index, boolean verbose, Proxy<Double> proxy) {
        super(index, verbose, proxy);
    }

    public void run(){
        while(!stop){
            if(verbose)
                System.out.println("Consumer taking items.");

            var future = proxy.take(20);

            //Do something
            safeSleep(20);

            if(verbose)
                System.out.println("Consumer done bookkeeping.");

            var result = future.getResult();
            if(verbose){
                System.out.println("Consumer - got result: " + result.toString());
            }
        }
    }

}
