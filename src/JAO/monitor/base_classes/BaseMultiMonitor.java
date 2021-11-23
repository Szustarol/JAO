package JAO.monitor.base_classes;

import JAO.monitor.interfaces.IMultiMonitor;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseMultiMonitor<T> extends BaseMonitor<T> implements IMultiMonitor<T> {
    public BaseMultiMonitor(int capacity) {
        super(capacity);
    }

    @Override
    public T popElement() { return popElements(1).get(0); }
    @Override
    public void putElement(T element) {
        ArrayList<T> tab = new ArrayList<>();
        tab.add(element);
        putElements(tab, 1);
    }

    @Override
    public abstract List<T> popElements(int times);

    @Override
    public abstract void putElements(Iterable<T> data, int times);

    protected boolean hasNotEnoughFreeSlots(int free) {
        return free > capacity - super.buffer.size();
    }

    protected boolean hasNotEnoughElements(int taken) {
        return taken > super.buffer.size();
    }
}
