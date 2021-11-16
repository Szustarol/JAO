package JAO;

import JAO.MethodRequest.IMethodRequest;
import JAO.MethodRequest.MethodRequestType;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MessageQueue <T> {
    private final Queue<IMethodRequest<T>> priorityQueue = new ArrayDeque<>();
    private final Queue<IMethodRequest<T>> tailQueue = new ArrayDeque<>();

    private final Lock lock = new ReentrantLock(true);
    private final Condition emptyCondition = lock.newCondition();
    private final Map<MethodRequestType, Condition> typesConditions = new HashMap<>();
    private final Map<MethodRequestType, Integer> typesCount = new HashMap<>();

    private Condition getTypeCondition(MethodRequestType type) {
        if(typesConditions.containsKey(type)) {
            return typesConditions.get(type);
        } else {
            var condition = lock.newCondition();
            typesConditions.put(type, condition);
            return condition;
        }
    }

    private void addToQueueUsingCounter(Queue<IMethodRequest<T>> queue, IMethodRequest<T> request) {
        queue.add(request);
        final var type = request.getType();
        if (typesCount.containsKey(type)) {
            typesCount.put(type, typesCount.get(type) + 1);
        } else {
            typesCount.put(type, 1);
        }
    }

    private IMethodRequest<T> pollQueueUsingCounter(Queue<IMethodRequest<T>> queue) {
        final var type = queue.element().getType();
        typesCount.put(type, typesCount.get(type) - 1);
        return queue.poll();
    }

    private boolean hasRequestWithOtherType(MethodRequestType type) {
        for(var obj : this.typesCount.entrySet()) {
            if(obj.getKey() != type && obj.getValue() > 0) {
                return true;
            }
        }
        return false;
    }

    private void moveToPriority(MethodRequestType type) {
        while(!this.tailQueue.isEmpty() && this.tailQueue.element().getType() == type) {
            this.priorityQueue.add(this.tailQueue.poll());
        }
    }

    private void signalOtherTypes(MethodRequestType type) {
        for(var obj : this.typesConditions.entrySet()) {
            if(obj.getKey() != type) {
                obj.getValue().signal();
            }
        }
    }

    private IMethodRequest<T> moveToPriorityAsLongAsYouCannotPollTail(MethodRequestType type) throws InterruptedException {
        while(!this.hasRequestWithOtherType(type)) {
            var condition = this.getTypeCondition(type);
            condition.await();
        }
        this.moveToPriority(type);
        return this.pollFrom(this.tailQueue);
    }

    private IMethodRequest<T> pollFrom(Queue<IMethodRequest<T>> queue) throws InterruptedException {
        var head = queue.element();
        if(head.guard()) {
            return this.pollQueueUsingCounter(queue);
        } else {
            return this.moveToPriorityAsLongAsYouCannotPollTail(head.getType());
        }
    }

    public IMethodRequest<T> deque() {
        this.lock.lock();
        try {
            while(this.priorityQueue.isEmpty() && this.tailQueue.isEmpty()) {
                this.emptyCondition.await();
            }

            if(!this.priorityQueue.isEmpty()) {
                return this.pollFrom(this.priorityQueue);
            }
            else {
                return this.pollFrom(this.tailQueue);
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } finally {
            this.lock.unlock();
        }
    }

    public void enqueue(IMethodRequest<T> method) {
        this.lock.lock();
        final var type = method.getType();

        this.addToQueueUsingCounter(this.tailQueue, method);

        this.emptyCondition.signal();
        this.signalOtherTypes(type);

        this.lock.unlock();
    }
}
