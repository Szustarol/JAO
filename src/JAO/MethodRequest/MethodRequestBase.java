package JAO.MethodRequest;

public abstract class MethodRequestBase implements IMethodRequest {
    private final MethodRequestType type;

    protected MethodRequestBase(MethodRequestType type) {
        this.type = type;
    }

    @Override
    public MethodRequestType getType() {
        return this.type;
    }
}
