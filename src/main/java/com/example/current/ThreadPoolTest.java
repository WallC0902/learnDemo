package com.example.current;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by wangchangpeng on 2019/9/23.
 */
public class ThreadPoolTest {

    /**
     * 主方法
     */
    public static void main(String[] args) {
        //创建线程池,线程池core和max都相同
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(10);
//        BasicThreadFactory.Builder builder = new BasicThreadFactory.Builder().namingPattern("processor-" + "-thread-%s");
//        MyThreadPoolExecutor executorPool = new MyThreadPoolExecutor(
//                10,
//                10, 30L, TimeUnit.SECONDS, workQueue, builder.build(),2500L);
//
//        executorPool.submit(()->{
//            try {
//                System.out.println(Thread.currentThread().getName()+" start sleep 1 ms....");
//                Thread.sleep(1);
//            }catch (InterruptedException e){
//                System.out.println(Thread.currentThread().getName()+e.getMessage());
//            }
//        });
//        executorPool.submit(()->{
//            try {
//                System.out.println(Thread.currentThread().getName()+" start sleep 30 s....");
//                Thread.sleep(30000);
//            }catch (InterruptedException e){
//                System.out.println(Thread.currentThread().getName()+e.getMessage());
//            }
//        });
//
//        while (true);
    }

}
