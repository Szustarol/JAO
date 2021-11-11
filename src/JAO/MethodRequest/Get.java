package JAO.MethodRequest;

import JAO.MessageFuture;
import JAO.Servant;

public class Get<T> extends MethodRequestBase {

    int nToGet;
    MessageFuture<T> future;

    public Get(int n, MessageFuture<T> future){
        super(MethodRequestType.Consume);
        this.nToGet = n;
        this.future = future;
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
