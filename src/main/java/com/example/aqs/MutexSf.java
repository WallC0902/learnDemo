package com.example.aqs;

import java.io.Serializable;
import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;

/**
 * Created by wangchangpeng on 2019/9/18.
 */
public class MutexSf implements Serializable {

    private class Sync extends AbstractQueuedLongSynchronizer {
        @Override
        protected boolean tryAcquire(long arg) {
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(long arg) {
            if (getState() == 0) {
                throw new IllegalMonitorStateException();
            }
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }
    }

    private Sync sync = new Sync();

    public void lock(){
        sync.acquire(1);
    }

    public void unlock(){
        sync.release(1);
    }

    public boolean isLock(){
        return sync.isHeldExclusively();
    }

    public boolean tryLock(){
        return sync.tryAcquire(1);
    }



}
