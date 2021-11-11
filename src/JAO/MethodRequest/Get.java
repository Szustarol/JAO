package JAO.MethodRequest;

import JAO.MessageFuture;
import JAO.Servant;

public class Get<T> extends MethodRequestBase {

    private Servant<T> servant;
    int nToGet;
    MessageFuture<T> future;

    public Get(Servant<T> servant, int n, MessageFuture<T> future){
        super(MethodRequestType.Consume);
        this.servant = servant;
        this.nToGet = n;
        future = future;
    }

    @Override
    public boolean guard() {
        return servant.nDataAvailable() >= this.nToGet;
    }

    @Override
    public void call() {
        var result = servant.get(nToGet);
        this.future.setData(result, nToGet);
    }
}
