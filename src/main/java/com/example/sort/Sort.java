package com.example.sort;

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
        if (arr.length <= 0) {
            return arr;
        }
        for (int i = 0; i < arr.length; i++) {
            boolean isSwitch = false;
            for (int j = 0; j < arr.length - i -1; j++) {
                if (arr[j] > arr[j+1]) {
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                    isSwitch = true;
                }
            }
            if (!isSwitch) break;
        }
        return arr;
    }

    /**
     * 选择排序
     *
     * @param arr
     * @return
     */
    public static int[] SelectSort(int[] arr){
        if (arr.length == 0) {
            return arr;
        }
        for (int i = 0; i < arr.length; i++) {
            int mini = arr[i];
            int n = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < mini) {
                    mini = arr[j];
                    n = j;
                }
            }

            arr[n] = arr[i];
            arr[i] = mini;
        }
        return arr;
    }


    /**
     * 快速排序
     *
     * @param arr
     * @param low
     * @param high
     */
    public static void quickSort(int[] arr, int low, int high){
        if (arr.length == 0) {
            return;
        }
        if (low < high) {
            int middle = getMiddleValue(arr, low, high);
            quickSort(arr, 0, middle - 1);
            quickSort(arr, middle + 1, high);
        }
    }


    public static int getMiddleValue(int arr[], int low, int high){
        int middle = arr[low];
        while (low < high) {
            while (low < high && arr[high] >= middle) {
                high --;
            }
            arr[low] = arr[high];
            while (low < high && arr[low] <= middle) {
                low ++;
            }
            arr[high] = arr[low];
        }
        arr[low] = middle;
        return low;
    }


    /**
     * KMP动态规划算法
     *
     * @param str
     * @param match
     * @return
     */
    public static int kmp (String str, String match) {
        int i = 0, j = 0;
        int[] next = getNext(str);

        while (i < str.length() && j < match.length()) {
            if (j == -1 || str.charAt(i) == match.charAt(j)) {
                i ++;
                j ++;
            } else {
                j = next[j];
            }
        }
        if (j == match.length()) {
            return i - j;
        } else {
            return -1;
        }
    }


    public static int[] getNext(String str){
        int[] next = new int[str.length() + 1];
        next[0] = -1;
        int i = 0, j = -1;

        while (i < str.length()) {
            if (j == -1 || str.charAt(i) == str.charAt(j)) {
                ++ i;
                ++ j;
                next[i] = j;
            } else {
                j = next[j];
            }
        }
        return next;
    }


    /**
     * 主方法
     */
    public static void main(String[] args) {
        int[] arr = {4,8,2,9,23,56,12,6,-34,0,87,3};
//        quickSort(arr, 0, arr.length -1);
//        arr = BubbleSort(arr);
//        arr = SelectSort(arr);
//        arr = getNext("abababca");
        int kmp = kmp("abababca", "abca");
        System.out.println(kmp);
//        for (int t : arr)
//            System.out.println(t);

    }

}
