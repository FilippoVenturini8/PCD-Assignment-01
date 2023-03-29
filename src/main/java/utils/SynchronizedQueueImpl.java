package utils;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SynchronizedQueueImpl<T> implements SynchronizedQueue<T> {

    private final List<T> list = new LinkedList<>();
    private final Lock mutex = new ReentrantLock();
    private final Condition notEmpty = mutex.newCondition();

    @Override
    public void add(T elem) {
        try{
            mutex.lock();
            this.list.add(elem);
            notEmpty.signal();
        }finally {
            mutex.unlock();
        }
    }

    @Override
    public T remove() throws InterruptedException{
        try{
            mutex.lock();
            if(this.isEmpty()){
                notEmpty.await();
            }
            return this.list.remove(0);
        } finally {
            mutex.unlock();
        }
    }

    @Override
    public boolean isEmpty() {
        try{
            mutex.lock();
            return this.list.isEmpty();
        }finally {
            mutex.unlock();
        }
    }
}
