package JAO.MethodRequest;



public interface IMethodRequest {
    boolean guard();
    void call();
    MethodRequestType getType();
}
