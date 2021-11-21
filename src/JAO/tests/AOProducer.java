package JAO.tests;

import JAO.Proxy;
import JAO.client.AbstractClient;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class AOProducer extends AbstractClient {
    private final int toProduce;
    private final int workUnits;

    public AOProducer(int index, boolean verbose, Proxy<Double> proxy, int toProduce, int workUnits) {
        super(index, verbose, proxy);
        this.toProduce = toProduce;
        this.workUnits = workUnits;
    }

    public void run(){
        int toProduce = ThreadLocalRandom.current().nextInt(this.toProduce, this.toProduce + 3);
        ArrayList<Double> data = new ArrayList<>(toProduce);
        for(double i = 0; i < toProduce; i += 1){
            data.add(i);
        }

        while(!stop){
            if(verbose)
                System.out.println("AOProducer %d: inserting items: ".formatted(index) + data.toString());

            var result = proxy.put(data, data.size());

            for(int i = 0; i < this.workUnits; ++i) {
                UnitOfWork.doUnitOfWork(i + 2);
            }
            if(verbose)
                System.out.printf("AOProducer %d: done bookkeeping.%n", index);

            if(verbose){
                result.getResult();
                System.out.printf("AOProducer %d: done insertion.%n", index);
            }
        }
    }
}
