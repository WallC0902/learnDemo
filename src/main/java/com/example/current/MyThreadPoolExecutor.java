package com.example.current;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 中断自动重跑的线程池
 *
 * Created by wangchangpeng on 2019/9/23.
 */
public class MyThreadPoolExecutor extends ThreadPoolExecutor {

    //定义监控线程
    private static final ScheduledExecutorService monitorExecutor = Executors.newScheduledThreadPool(1);
    //存储线程ID和它对应的起始执行时间
    private final ConcurrentMap<Integer, Long> aliveThreadRefreshTimeMap = new ConcurrentHashMap<>();
    //存储线程ID和它对应的Future对象
    private final ConcurrentMap<Integer, Future<?>> aliveThreadFutureMap = new ConcurrentHashMap<>();
    //存储线程ID和它对应的Runnable对象
    private final ConcurrentMap<Integer, Runnable> aliveThreadTaskMap = new ConcurrentHashMap<>();
    //线程ID
    private AtomicInteger aliveThreadNum = new AtomicInteger(0);
    //自动重试时间
    private long retryTime;

    private boolean isMonitoring = false;
    //构造时传入重试时间
    public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, long retryTime) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
        this.retryTime = retryTime;
    }

    //重写：在提交线程池后记录线程信息
    @Override
    public Future<?> submit(Runnable task) {
        if (task == null) {
            throw new NullPointerException();
        }
        Future<?> future = super.submit(task);
        afterSubmit(future, task);
        return future;
    }

    //每次提交任务后将线程信息加入到对应的Map里，并且首次提交时启动monitor线程
    private void afterSubmit(Future future, Runnable task) {
        aliveThreadFutureMap.put(aliveThreadNum.get(), future);
        aliveThreadTaskMap.put(aliveThreadNum.get(), task);
        long currentTime = System.currentTimeMillis();
        aliveThreadRefreshTimeMap.put(aliveThreadNum.get(), currentTime);
        initializeMonitorThread();
        aliveThreadNum.incrementAndGet();
    }

    //启动一个看门狗线程
    private void initializeMonitorThread() {
        //看门狗线程只需启动一次
        if (isMonitoring) {
            return;
        }
        monitorExecutor.scheduleAtFixedRate(() -> {
            isMonitoring = true;
            System.out.printf("monitor thread start..., aliveThreadRefreshTimeMap:%s\n", aliveThreadRefreshTimeMap);
            List<Integer> removeIdList = new ArrayList<>();
            //遍历储存线程开始时间的那个Map
            for (int threadId : aliveThreadRefreshTimeMap.keySet()) {
                long currentTime = System.currentTimeMillis();
                long refreshTimes = currentTime - aliveThreadRefreshTimeMap.get(threadId);
                System.out.printf("thread %d, refreshTimes is %d\n", threadId, refreshTimes);
                //判断时间大小，注意这里并不意味着超时，因为我们在线程提交时将它的时间加入map，但线程可能很快就结束了，map里的值在结束时不会更新
                if (refreshTimes > retryTime) {
                    System.out.printf("alive thread %d: is %dms to refresh, will restart\n", threadId,
                            currentTime - aliveThreadRefreshTimeMap.get(threadId));
                    Future future = aliveThreadFutureMap.get(threadId);
                    future.cancel(true);
                    //isCanceled返回true说明超时线程已被中断成功，返回false说明线程早已经跑完了，没有超时
                    //注意这里用了isCanceled并没有用cancel，是因为如果一个线程已经被取消后，需要resubmit时可能提交失败，我们需要支持对于这种情况让它可以重复尝试提交
                    if(!future.isCancelled()){
                        System.out.println("canceled failed with thread id : "+threadId+" the thread may already stopped");
                        removeIdList.add(threadId);
                        continue;
                    }
                    //超时线程重新提交
                    Future<?> resubmitFuture;
                    try {
                        resubmitFuture = super.submit(aliveThreadTaskMap.get(threadId));
                    } catch (Exception e) {
                        //注意这里可能提交失败，因为可能线程池被占满，走拒绝策略了。。我们这里的处理方式是直接跳过，这样它还会再下一次monitor线程执行时被重试
                        System.out.println("error when resubmit , the threadPool may be full will retry with  error info: " + e.getMessage());
                        continue;
                    }
                    //resubmit成功后登记信息
                    aliveThreadNum.incrementAndGet();
                    currentTime = System.currentTimeMillis();
                    aliveThreadRefreshTimeMap.put(aliveThreadNum.get(), currentTime);
                    aliveThreadFutureMap.put(aliveThreadNum.get(), resubmitFuture);
                    aliveThreadTaskMap.put(aliveThreadNum.get(), aliveThreadTaskMap.get(threadId));
                    removeIdList.add(threadId);
                    System.out.printf("restart success, thread id is:%d\n", aliveThreadNum.get());
                }
            }
            for (int id : removeIdList) {
                //清理不需要的已经结束的线程
                aliveThreadFutureMap.remove(id);
                aliveThreadTaskMap.remove(id);
                aliveThreadRefreshTimeMap.remove(id);
            }
            //定义每一秒执行一次的定时线程
        }, 0, 1, TimeUnit.SECONDS);
    }

}


