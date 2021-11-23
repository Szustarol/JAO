package JAO.monitor.base_classes;

import JAO.monitor.interfaces.IMonitor;

import java.util.ArrayDeque;
import java.util.Deque;

public abstract class BaseMonitor<T> implements IMonitor<T> {
    protected final Deque<T> buffer;
    protected final int capacity;

    public BaseMonitor(int capacity) {
        this.buffer = new ArrayDeque<>();
        this.capacity = capacity;
    }

    protected boolean isEmpty() {
        return this.buffer.isEmpty();
    }
    protected boolean isFull() {
        return this.buffer.size() == this.capacity;
    }


    @Override
    public abstract T popElement();

    @Override
    public abstract void putElement(T element);

    @Override
    public void printStatus() {
    }

    @Override
    public void stopWork() {
    }

    protected void addToBuffer(T element) {
        buffer.add(element);
    }

    protected T getFromBuffer() {
        return this.buffer.poll();
    }
}
