package JAO;

import javax.swing.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MessageFuture<T>{
    private volatile Iterable<T> data = null;
    private int dataSize = -1;
    private Lock resultLock;

    public boolean poll(){
        return data != null;
    }

    public void setData(Iterable<T> data, int n){
        this.data = data;
        this.dataSize = n;
        resultLock.notify();
        resultLock = new ReentrantLock();
    }

    public Iterable<T> getResult(){
        resultLock.lock();
        while (this.data == null){
            try {
                resultLock.wait();
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        resultLock.unlock();
        return data;
    }
}
