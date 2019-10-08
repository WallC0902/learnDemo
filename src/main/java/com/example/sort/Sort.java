package com.example.sort;

import java.util.Arrays;

/**
 * 排序算法
 *
 * Created by wangchangpeng on 2019/10/8.
 */
public class Sort {

    /**
     * 冒泡排序
     *
     * @param arr
     * @return
     */
    public static int[] BubbleSort(int[] arr){
        boolean isSwitch = false;
        for (int i = 0; i < arr.length-1; i ++) {
            for(int j = i+1; j <= arr.length-1; j++){
                if (arr[i] < arr[j]) {
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                    isSwitch = true;
                }
            }
            if (!isSwitch) break;
        }
        return arr;
    }


    /**
     * 主方法
     */
    public static void main(String[] args) {
        int[] arr = {4,8,2,9,23,56,12,6,-34,0,87,3};

        arr = BubbleSort(arr);

        for (int t : arr)
            System.out.println(t);


    }

}
