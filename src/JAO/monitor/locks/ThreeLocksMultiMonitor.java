package JAO.monitor.locks;

import JAO.monitor.base_classes.BaseMultiMonitor;
import JAO.tests.utility.UnitOfWork;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreeLocksMultiMonitor<T> extends BaseMultiMonitor<T> {
    final Lock consumerLock;
    final Lock producerLock;
    final Lock accessLock;

    final Condition accessCondition;

//    private

    public ThreeLocksMultiMonitor(int capacity) {
        this(capacity, true);
    }

    public ThreeLocksMultiMonitor(int capacity, boolean fair) {
        super(capacity);
        this.consumerLock = new ReentrantLock(fair);
        this.producerLock = new ReentrantLock(fair);
        this.accessLock = new ReentrantLock(fair);
        this.accessCondition = this.accessLock.newCondition();
    }

    @Override
    public List<T> popElements(int times) {
        this.consumerLock.lock();
        this.accessLock.lock();
        var result = new ArrayList<T>(times);

        try {
            while (super.hasNotEnoughElements(times)) {
                this.accessCondition.await();
            }

            for (int i = 0; i < times; ++i) {
                var element = super.getFromBuffer();
                result.add(element);
                UnitOfWork.doUnitOfWork((double)element);
            }
            this.accessCondition.signal();
            return result;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return result;
        } finally {
            this.accessLock.unlock();
            this.consumerLock.unlock();
        }
    }


    @Override
    public void putElements(Iterable<T> elements, int times) {
        this.producerLock.lock();
        this.accessLock.lock();

        try {
            while (hasNotEnoughFreeSlots(times)) {
                this.accessCondition.await();
            }

            for (var element : elements) {
                super.addToBuffer(element);
                UnitOfWork.doUnitOfWork((double) element);
            }

            this.accessCondition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.accessLock.unlock();
            this.producerLock.unlock();
        }
    }
}
