package JAO.AO.MethodRequest;

import JAO.AO.Servant;

public abstract class MethodRequestBase<T> implements IMethodRequest<T> {

    protected Servant<T> servant;

    public void setServant(Servant<T> servant){
        this.servant = servant;
    }

    private final MethodRequestType type;

    protected MethodRequestBase(MethodRequestType type) {
        this.type = type;
    }

    @Override
    public MethodRequestType getType() {
        return this.type;
    }
}
