package JAO;

enum MethoRequestType {
    Consume, Produce
}

public interface IMethodRequest {
    boolean guard();
    void call();
}
