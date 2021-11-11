package JAO.MethodRequest;


import JAO.Servant;

public interface IMethodRequest<T> {
    boolean guard();
    void call();
    void setServant(Servant<T> servant);
    MethodRequestType getType();
}
