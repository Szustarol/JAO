package JAO;

import JAO.MethodRequest.IMethodRequest;

import java.util.ArrayDeque;
import java.util.Queue;

public class MessageQueue <T> {



    private final Queue<IMethodRequest> headQueue = new ArrayDeque<>();
    private final Queue<IMethodRequest> tailQueue = new ArrayDeque<>();

    public void enqueue(IMethodRequest request) {
        tailQueue.add(request);
    }

    public IMethodRequest deque() {
        if(!headQueue.isEmpty()) {
            var method = headQueue.element();
            if(!method.guard()) {
                return None;
            }
        }
    }
}
