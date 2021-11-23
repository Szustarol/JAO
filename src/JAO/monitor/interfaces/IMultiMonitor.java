package JAO.monitor.interfaces;

import java.util.List;

public interface IMultiMonitor<T> extends IMonitor<T> {
    List<T> popElements(int times);
    void putElements(Iterable<T> elements, int times);
}
