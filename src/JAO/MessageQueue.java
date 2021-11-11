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

    private Condition getTypeCondition(MethodRequestType type) {
        if(typesConditions.containsKey(type)) {
            return typesConditions.get(type);
        } else {
            var condition = lock.newCondition();
            typesConditions.put(type, condition);
            return condition;
        }
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
        while(true) {
            this.moveToPriority(type);
            if(this.tailQueue.isEmpty()) {
                var condition = this.getTypeCondition(type);
                condition.await();
            } else {
                return this.tailQueue.poll();
            }
        }
    }

    private IMethodRequest<T> pollFrom(Queue<IMethodRequest<T>> queue) throws InterruptedException {
        var head = queue.element();
        if(head.guard()) {
            return queue.poll();
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
        this.tailQueue.add(method);

        this.emptyCondition.signal();
        this.signalOtherTypes(type);

        this.lock.unlock();
    }
}
