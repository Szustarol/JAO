package JAO.MethodRequest;

import JAO.MessageFuture;
import JAO.Servant;

import java.util.ArrayList;

public class Put<T> extends MethodRequestBase<T> {
    int nToPut;
    Iterable<T> data;
    MessageFuture<T> future;

    public Put(Iterable<T> data, int n, MessageFuture<T> future){
        super(MethodRequestType.Produce);
        this.nToPut = n;
        this.data = data;
        this.future = future;
    }

    @Override
    public boolean guard() {
        return servant.nSpaceAvailable() >= this.nToPut;
    }

    @Override
    public void call() {
        this.servant.put(this.data, nToPut);
        this.future.setData(new ArrayList<T>(0), 0);
    }
}
