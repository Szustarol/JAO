package JAO;

import java.util.ArrayList;

public class Servant<T> {
    private T [] buffer;

    private int writeIndex = 0;
    private int readIndex = 0;

    private int capacity;
    private int size;

    public Servant(int capacity){
        this.capacity = capacity;
        this.size = 0;
        buffer = (T[])new Object[capacity];
    }

    public int nSpaceAvailable(){
        return capacity-size;
    }

    public int nDataAvailable(){
        return size;
    }

    public void put(Iterable<T> data, int n){
        System.out.println("Servant, insertion:");
        System.out.println(data);
        for(T elem : data){
            buffer[writeIndex] = elem;
            writeIndex = (writeIndex+1) % capacity;
        }
        size += n;
    }

    public ArrayList<T> get(int n){
        var result = new ArrayList<T>(n);
        for(int i = readIndex, taken = 0; taken < n; readIndex = (readIndex+1) % capacity, taken++){
            result.add(buffer[i]);
        }
        size -= n;
        String res = "";
        res = ("Servant, buffer while taking:\n");
        for(var elem : buffer){
            res = res  + elem + ", ";
        }
        System.out.println(res);
        return result;
    }
}
