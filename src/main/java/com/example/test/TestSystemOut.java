package com.example.test;

/**
 * @program: demo
 * @description: 测试SystemOut性能
 * @author: Wangchangpeng
 * @create: 2019-12-13
 **/
public class TestSystemOut {
    private static long timeOut = System.currentTimeMillis() + 1000L;

    private static void outQuick() {
        long i = 1L;
        while (timeOut >= System.currentTimeMillis()) {
            i++;
        }
        System.out.println(i);
    }

    private static void outOnlyNumber() {
        long i = 1L;
        while (timeOut >= System.currentTimeMillis()) {
            System.out.println(i++);
        }
    }

    private static void outOnlyString() {
        long i = 1L;
        while (timeOut >= System.currentTimeMillis()) {
            System.out.println("" + i++);
        }
    }

    public static void main(String[] args) {
        outQuick(); // 执行即结果：29697174 30269398 28856801 28643530 29579120
//        outOnlyNumber(); // 执行即结果：150146 150597  138035 138759 141642
//        outOnlyString(); // 执行即结果：150873 147664 147249 158283 156499
        // 相差200倍
    }

}
