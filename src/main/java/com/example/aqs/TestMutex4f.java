package com.example.aqs;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by wangchangpeng on 2019/9/18.
 */
public class TestMutex4f {

    private static CyclicBarrier barrier = new CyclicBarrier(31);
    private static int a = 0;
    private static ReentrantLock lock = new ReentrantLock();
    private static Mutex mutex = new Mutex();

    /**
     * 主方法
     */
    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        long before1 = System.currentTimeMillis();
        for (int i = 0; i < 30; i++) {
            Thread t = new Thread(() -> {
                for (int j = 0; j< 10000; j++) {
                    incrementNoLock();
                }
                try {
                    // 等待30个线程加载完毕
                    barrier.await();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            });
            t.start();
        }
        barrier.await();
        System.out.println("metux锁的a = " + a);
        System.out.println("metux锁消耗的时间 = " + (System.currentTimeMillis() - before1));

        barrier.reset();
        long before = System.currentTimeMillis();
        a = 0;
        for (int i= 0; i < 30 ; i++) {
            new Thread(() -> {
                for (int k = 0; k< 10000; k++) {
                    incrementForLock();
                }
                try {
                    // 等待30个线程加载完毕
                    barrier.await();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        barrier.await();
        System.out.println("reentrantLock锁的a = " + a);
        System.out.println("reentrantLock消耗的时间 = " + (System.currentTimeMillis() - before));
    }

    public static void incrementNoLock() {
        mutex.lock();
        a ++;
        mutex.unlock();
    }

    public static void incrementForLock() {
        lock.lock();
        a ++;
        lock.unlock();
    }
}
