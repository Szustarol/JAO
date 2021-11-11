import JAO.Proxy;

public class Consumer extends AbstractClient{


    public Consumer(int index, boolean verbose, Proxy<Double> proxy) {
        super(index, verbose, proxy);
    }

    public void run(){
        while(!stop){
            if(verbose)
                System.out.printf("Consumer %d: taking items.%n", index);

            var future = proxy.take(1);

            //Do something
            safeSleep(13);

            if(verbose)
                System.out.printf("Consumer %d: done bookkeeping.%n", index);

            var result = future.getResult();
            if(verbose){
                System.out.println("Consumer %d: got result: ".formatted(index) + result.toString());
            }
        }
    }

}
