package com.example.aqs;

import java.io.Serializable;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * Created by wangchangpeng on 2019/9/19.
 */
public class Semaphore implements Serializable {

    private Sync sync;

    abstract class Sync extends AbstractQueuedSynchronizer {

        public void setSemaphore(int semaphore){
            if (semaphore < 0) {
                throw new IllegalArgumentException("semaphore < 0");
            }
            setState(semaphore);
        }

        protected int noFairTryAcquireShared(int acquires) {
            for (;;) {
                int state = getState();
                int nextc = state - acquires;
                if (nextc < 0 || compareAndSetState(state, nextc)) {
                    return acquires;
                }
            }
        }

        @Override
        protected boolean tryReleaseShared(int acquires) {
            for (;;) {
                int state = getState();
                int nextc = state + acquires;
                if (nextc < state) {
                    throw new IllegalArgumentException("Maximum permit count exceeded");
                }
                return compareAndSetState(state, nextc);
            }
        }
    }


    private class NoFairSync extends Sync {

        public NoFairSync(int semaphore) {
            super.setSemaphore(semaphore);
        }

        @Override
        protected int tryAcquireShared(int acquires) {
            return noFairTryAcquireShared(acquires);
        }
    }

    private class FairSync extends Sync{

        public FairSync(int semaphore) {
            super.setSemaphore(semaphore);
        }

        @Override
        protected int tryAcquireShared(int acquires) {
            for (;;) {
                if (hasQueuedPredecessors()) {
                   return -1;
                }
                int state = getState();
                int nextc = state - acquires;
                if (nextc < 0 || compareAndSetState(state, nextc)) {
                    return acquires;
                }
            }
        }
    }


    public Semaphore(int semaphore) {
        sync = new NoFairSync(semaphore);
    }

    public Semaphore(int semaphore, boolean fair) {
        sync = fair ? new FairSync(semaphore) : new NoFairSync(semaphore);
    }

    public void acquire() throws InterruptedException {
        sync.acquireSharedInterruptibly(1);
    }

    public void acquire(int acquires) throws InterruptedException {
        if (acquires < 0) {
            throw new IllegalMonitorStateException("<0");
        }
        sync.acquireSharedInterruptibly(acquires);
    }

    public void release(){
        sync.release(1);
    }


    public void release(int acquires){
        if (acquires < 0) {
            throw new IllegalMonitorStateException("<0");
        }
        sync.release(acquires);
    }

}
