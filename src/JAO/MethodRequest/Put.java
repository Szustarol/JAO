package JAO.MethodRequest;

import JAO.MessageFuture;
import JAO.Servant;

public class Put<T> extends MethodRequestBase {
    int nToPut;
    Iterable<T> data;

    public Put(Iterable<T> data, int n){
        super(MethodRequestType.Produce);
        this.nToPut = n;
        this.data = data;
    }

    @Override
    public boolean guard() {
        return servant.nSpaceAvailable() >= this.nToPut;
    }

    @Override
    public void call() {
        this.servant.put(this.data, nToPut);
    }
}
