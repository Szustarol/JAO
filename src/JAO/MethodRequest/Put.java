package JAO.MethodRequest;

import JAO.MessageFuture;
import JAO.Servant;

public class Put<T> extends MethodRequestBase {

    private Servant<T> servant;
    int nToPut;
    Iterable<T> data;

    public Put(Servant<T> servant, Iterable<T> data, int n){
        super(MethodRequestType.Produce);
        this.servant = servant;
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
