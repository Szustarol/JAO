package JAO;

import java.util.ArrayList;

public class Servant<T> {
    private T [] buffer;

    private long writeIndex = 0;
    private long readIndex = 0;

    private long capacity;
    private long size;

    public Servant(int capacity){
        this.capacity = capacity;
        this.size = size;
    }

    public ArrayList<T> get(int n){
        var result = new ArrayList<>(n);
        for(long i = readIndex; i < n; readIndex = (readIndex+1) % capacity){

        }
    }
}
