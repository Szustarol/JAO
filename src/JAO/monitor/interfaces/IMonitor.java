package JAO.monitor.interfaces;

public interface IMonitor<T> {
    T popElement();
    void putElement(T element);
    void printStatus();
    void stopWork();
}
