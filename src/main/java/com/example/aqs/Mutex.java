package com.example.aqs;

import java.io.Serializable;
import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;

/**
 * Created by wangchangpeng on 2019/9/17.
 */
public class Mutex implements Serializable {

    private static class Sync extends AbstractQueuedLongSynchronizer{

        @Override
        protected boolean isHeldExclusively(){
            return getState() == 1;
        }

        @Override
        public boolean tryRelease(long arg) {
            if (getState() == 0) throw new IllegalMonitorStateException();
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        @Override
        public boolean tryAcquire(long acquire) {
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

    }

    private final Sync sync = new Sync();

    public void lock() {
        sync.acquire(1);
    }

    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    public void unlock() {
        sync.release(1);
    }

    public boolean isLock() {
        return sync.isHeldExclusively();
    }


}
