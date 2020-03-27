package com.example.test;

import org.apache.commons.lang.StringUtils;

import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
        System.out.println(returnTest("14"));

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

    private static String returnTest(String str) {
        returnTest2(str);
        if (str.equals("14")) {
            System.out.println("111111");
            return "16";
        }
        return null;
    }

    private static String returnTest2(String str) {
        if (str.equals("10")) {
            System.out.println("22222222");
            return "12";
        }
        return null;
    }


    /**
     * 去除字段前后空格，中间空格不处理
     *
     * @param content
     * @return
     */
    public static String trimLeadTrail(String content) {
//        List<String> list = Arrays.asList("　氨基酸的  asdfa  玩儿热若33  asdf  123  ", "　212 3", "森岛 帆高　", "阿斯蒂芬 为", " ");
//        List<String> collect = list.stream().filter(str -> StringUtils.isNotBlank(str)).map(str -> trimLeadTrail(str)).collect(Collectors.toList());
//        System.out.println(collect.size());
//        collect.stream().forEach(str -> System.out.println(str));


        if (StringUtils.isBlank(content)) {
            return null;
        }
        // 判断全角和半角
        while (content.startsWith(" ") || content.startsWith("　")) {
            content = content.substring(1);
        }
        // 判断全角和半角
        while (content.endsWith(" ") || content.endsWith("　")) {
            content = content.substring(0, content.length() - 1);
        }
        return content;
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
