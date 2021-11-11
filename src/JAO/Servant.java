package JAO;

import java.util.ArrayList;

public class Servant<T> {
    private T [] buffer;

    private int writeIndex = 0;
    private int readIndex = 0;

    private int capacity;
    private int size;

    private boolean verbose;

    public Servant(int capacity, boolean verbose){
        this.capacity = capacity;
        this.size = 0;
        this.verbose = verbose;
        buffer = (T[])new Object[capacity];
    }

    public int nSpaceAvailable(){
        return capacity-size;
    }

    public int nDataAvailable(){
        return size;
    }

    public void put(Iterable<T> data, int n){
        if(verbose){
            System.out.println("Servant - data insertion: " + data.toString());
        }
        for(T elem : data){
            buffer[writeIndex] = elem;
            writeIndex = (writeIndex+1) % capacity;
        }
        size += n;
    }

    public ArrayList<T> get(int n){
        var result = new ArrayList<T>(n);
        for(int taken = 0; taken < n; readIndex = (readIndex+1) % capacity, taken++){
            result.add(buffer[readIndex]);
        }
        size -= n;
        if(verbose){
            System.out.println("Servant - data removal: " + result.toString());
        }
        return result;
    }
}
