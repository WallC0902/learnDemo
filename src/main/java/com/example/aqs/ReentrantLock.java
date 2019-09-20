package com.example.aqs;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Created by wangchangpeng on 2019/9/18.
 */
public class ReentrantLock implements Serializable, Lock {

    private final Sync sync;

    private abstract class Sync extends AbstractQueuedSynchronizer{

        abstract void lock();

        final boolean noFairTryAcquire(int acquires){
            Thread current = Thread.currentThread();
            int c = getState();
            if (c == 0) {
                if (compareAndSetState(0, 1)) {
                    setExclusiveOwnerThread(current);
                    return true;
                }
            } else if (current == getExclusiveOwnerThread()){
                int update = c + acquires;
                if (c < 0) {
                    throw new Error("Maximum lock count exceeded");
                }
                setState(update);
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            int state = getState() - arg;
            if (getExclusiveOwnerThread() != Thread.currentThread()) {
                throw  new IllegalMonitorStateException();
            }
            boolean free = false;
            if (state == 0) {
                free = true;
                setExclusiveOwnerThread(null);
            }
            setState(state);
            return free;
        }

        @Override
        protected final boolean isHeldExclusively() {
            return getExclusiveOwnerThread() == Thread.currentThread();
        }

        final ConditionObject newCondition() {
            return new ConditionObject();
        }

        final Thread getOwner() {
            return getState() == 0 ? null : getExclusiveOwnerThread();
        }

        final int getHoldCount() {
            return isHeldExclusively() ? getState() : 0;
        }

        final boolean isLocked() {
            return getState() != 0;
        }

        private void readObject(java.io.ObjectInputStream s)
                throws java.io.IOException, ClassNotFoundException {
            s.defaultReadObject();
            setState(0);
        }
    }

    private class NoFairSync extends Sync {

        @Override
        void lock() {
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
            } else {
                acquire(1);
            }
        }

        @Override
        protected boolean tryAcquire(int arg) {
            return noFairTryAcquire(arg);
        }
    }


    private class FairSync extends Sync{
        @Override
        void lock() {
            acquire(1);
        }

        @Override
        protected boolean tryAcquire(int arg) {
            final Thread current = Thread.currentThread();
            int stat = getState();
            if (stat == 0) {
                if (!hasQueuedPredecessors()
                    && compareAndSetState(0, arg)) {
                    setExclusiveOwnerThread(current);
                    return true;
                }
            } else if (current == getExclusiveOwnerThread()) {
                int update = stat + arg;
                if (update < 0) {
                    throw new Error("Maximum lock count exceeded");
                }
                setState(update);
                return true;
            }
            return false;
        }
    }


    public ReentrantLock() {
        sync = new NoFairSync();
    }

    public ReentrantLock(Boolean fair) {
        sync = fair ? new FairSync() : new NoFairSync();
    }

    @Override
    public void lock() {
        sync.lock();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return sync.noFairTryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}
