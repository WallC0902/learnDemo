package com.example.test;

/**
 * Created by wangchangpeng on 2019/9/12.
 */
public class DoubleLockSingleton {

    private static volatile DoubleLockSingleton singleton;

    public static DoubleLockSingleton getInstance() {
        if (singleton == null) {
            synchronized (DoubleLockSingleton.class) {
                if (singleton == null) {
                    singleton = new DoubleLockSingleton();
                }
            }
        }
        return singleton;
    }


    /**
     * 主方法
     */
    public static void main(String[] args) {
//        int i = 0;
//        for (;;) {
//            System.out.println("-------> i = " + i);
//            if ( ++i > 5) {
//                return;
//            }
//        }
        System.out.println(10 >>> 16);

    }
}
