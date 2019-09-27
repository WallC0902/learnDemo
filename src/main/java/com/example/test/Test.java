package com.example.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by wangchangpeng on 2019/9/11.
 */
public class Test {

//    /**
//     * 主方法
//     */
//    public static void main(String[] args) {
//        Map map = new HashMap(20);
//        map.put("123", "123123");
//        System.out.println(map.size());
//
//        int MAXIMUM_CAPACITY = 1 << 30;
//        int n = 17 - 1;
//        n |= n >>> 1;
//        n |= n >>> 2;
//        n |= n >>> 4;
//        n |= n >>> 8;
//        n |= n >>> 16;
//        System.out.println((n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1);
//
//        ArrayList<Integer> list1 = new ArrayList<>();
//        ArrayList<String> list2 = new ArrayList<>();
//        System.out.print(list1.getClass() == list2.getClass());
//        System.out.println(zixuan());

//        int a = 10 >> 1;
//        int b = a ++;
//        int c = ++a;
//        int d = b * a ++;
//        System.out.println(a);
//        System.out.println(b);
//        System.out.println(c);
//        System.out.println(d);
//    }


    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        System.out.println(Integer.MAX_VALUE);
//        new Thread(() -> {
//            try {
//                for (int i = 0; i < 100; i++) {
//                    threadLocal.set(i);
//                    System.out.println(Thread.currentThread().getName() + "====" + threadLocal.get());
//                    try {
//                        Thread.sleep(200);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            } finally {
//                threadLocal.remove();
//            }
//        }, "threadLocal1").start();
//
//        new Thread(() -> {
//            try {
//                for (int i = 0; i < 100; i++) {
//                    System.out.println(Thread.currentThread().getName() + "====" + threadLocal.get());
//                    try {
//                        Thread.sleep(200);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            } finally {
//                threadLocal.remove();
//            }
//        }, "threadLocal2").start();
    }



    public static boolean zixuan() {
        for (;;) {
            int c = 5;
            c --;
            System.out.println("c = " + c);
            return c == 0;
        }
    }




}
