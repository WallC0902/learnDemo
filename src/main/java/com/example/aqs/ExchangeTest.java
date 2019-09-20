package com.example.aqs;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by wangchangpeng on 2019/9/20.
 */
public class ExchangeTest {

    private static final Exchanger<String> exchanger = new Exchanger<>();

    private static ExecutorService executors = Executors.newFixedThreadPool(2);

    /**
     * 主方法
     */
    public static void main(String[] args) {
        executors.execute(()->{
            String a = "流水A";
            try {
                String b = exchanger.exchange(a);
                System.out.println("b=" + b);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executors.execute(()->{
            try {
                String b = "流水B";
                String a = exchanger.exchange(b);
                System.out.println("a == b --->" + a.equals(b));
                System.out.println("a = " + a +"-------->"+ "b=" + b);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

}
