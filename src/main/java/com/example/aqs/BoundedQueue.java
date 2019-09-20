package com.example.aqs;

import java.util.concurrent.locks.Condition;

/**
 * Created by wangchangpeng on 2019/9/20.
 */
public class BoundedQueue<T> {
    private Object[] items;
    private ReentrantLock lock = new ReentrantLock();
    private Condition getCondition = lock.newCondition();
    private Condition putCondition = lock.newCondition();
    private int getIdx, putIdx, count;

    public BoundedQueue(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException();
        }
        items = new Object[size];
    }

    public T get() throws InterruptedException {
        lock.lock();
        try {
            while (0 == items.length) {
                getCondition.await();
            }
            T t = (T) items[getIdx];
            if (++getIdx == items.length -1){
                getIdx = 0;
            }
            count --;
            putCondition.signal();
            return t;
        }finally {
            lock.unlock();
        }
    }

    public void put(T t) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length) {
                putCondition.await();
            }
            items[putIdx] = t;
            if (++putIdx == items.length) {
                putIdx = 0;
            }
            count ++;
            getCondition.signal();
        }finally {
            lock.unlock();
        }
    }


}
