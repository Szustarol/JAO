package JAO;

import javax.swing.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MessageFuture<T>{
    private volatile Iterable<T> data = null;
    private int dataSize = -1;

    public boolean poll(){
        return data != null;
    } // dodac synchro

    public synchronized void setData(Iterable<T> data, int n){
        this.data = data;
        this.dataSize = n;
        notify();
    }

    public synchronized Iterable<T> getResult(){
        while(this.data == null){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return data;
    }
}
