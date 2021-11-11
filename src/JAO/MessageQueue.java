package JAO;

import JAO.MethodRequest.IMethodRequest;
import JAO.MethodRequest.MethodRequestType;

import java.lang.constant.Constable;
import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MessageQueue <T> {

    Optional<MethodRequestType> pendingType = Optional.empty();

    private final Queue<IMethodRequest> headQueue = new ArrayDeque<>();
    private final Queue<IMethodRequest> tailQueue = new ArrayDeque<>();

    private Lock lock = new ReentrantLock(true);
    private Condition emptyCondition = lock.newCondition();
    private Map<MethodRequestType, Condition> typesConditions = new HashMap<>();

    public void enqueue(IMethodRequest request) {
        tailQueue.add(request);
    }

    private Condition getTypeCondition(MethodRequestType type) {
        if(typesConditions.containsKey(type)) {
            return typesConditions.get(type);
        } else {
            var condition = lock.newCondition();
            typesConditions.put(type, condition);
            return condition;
        }
    }

    void moveToHead(MethodRequestType type) {
        while(!this.tailQueue.isEmpty() && this.tailQueue.element().getType() == type) {
            this.headQueue.add(this.tailQueue.poll());
        }
    }

    private IMethodRequest moveQuesesAsLongAsYouCanPoll(MethodRequestType type) throws InterruptedException {
        while(true) {
            this.moveToHead(type);
            if(this.tailQueue.isEmpty()) {
                var condition = this.getTypeCondition(type);
                condition.await();
            } else {
                return this.tailQueue.poll();
            }
        }
    }

    public IMethodRequest deque() {
        this.lock.lock();
        try {
            while(this.headQueue.isEmpty() && this.tailQueue.isEmpty()) {
                this.emptyCondition.await();
            }
            if(!this.headQueue.isEmpty()) {
                var head = this.headQueue.element();
                if(head.guard()) {
                    return this.headQueue.poll();
                } else {
                    return moveQuesesAsLongAsYouCanPoll(head.getType());
                }
            }
            else {
                var element = this.tailQueue.element();
                if(element.guard()) {
                    return this.tailQueue.poll();
                } else {
                    return moveQuesesAsLongAsYouCanPoll(element.getType());
                }
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } finally {
            this.lock.unlock();
        }
    }
}
