package JAO.AO.MethodRequest;


import JAO.AO.Servant;

public interface IMethodRequest<T> {
    boolean guard();
    void call();
    void setServant(Servant<T> servant);
    MethodRequestType getType();
}
