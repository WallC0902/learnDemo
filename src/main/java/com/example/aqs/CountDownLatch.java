package com.example.aqs;

import java.io.Serializable;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * Created by wangchangpeng on 2019/9/19.
 */
public class CountDownLatch implements Serializable {

    private class Sync extends AbstractQueuedSynchronizer{

        void count(int count) {
            setState(count);
        }

        int getCount(){
            return getState();
        }

        @Override
        protected int tryAcquireShared(int arg) {
            return getState() == 0 ? 1 : -1;

        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            for (;;) {
                int c = getState();
                if (c == 0) {
                    return false;
                }
                int nextc = c - 1;
                if (compareAndSetState(c, nextc)) {
                    return nextc == 0;
                }
            }
        }
    }

    private Sync sync;

    public CountDownLatch(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("count < 0");
        }
        sync = new Sync();
        sync.count(count);
    }

    public boolean countDown(){
        return sync.releaseShared(1);
    }

    public void await() throws InterruptedException {
        sync.acquireSharedInterruptibly(1);
    }

    public int getCount(){
        return sync.getCount();
    }

}
