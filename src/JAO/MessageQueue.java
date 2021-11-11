package JAO;

import java.util.ArrayDeque;
import java.util.Queue;

public class MessageQueue <T> {

    private final Queue<IMethodRequest> headQueue = new ArrayDeque<>();
    private final Queue<IMethodRequest> tailQueue = new ArrayDeque<>();

    public void enqueue(IMethodRequest request) {
        tailQueue.add(request);
    }

    public IMethodRequest deque() {

    }
}
