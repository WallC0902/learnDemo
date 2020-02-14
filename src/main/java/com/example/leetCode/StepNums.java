package com.example.leetCode;

/**
 * @program: demo
 * @description: 阶梯数求和
 * @author: Wangchangpeng
 * @create: 2020-02-14
 **/
public class StepNums {

    //评测题目: 阶梯数求和
    //如果一个数的任意相邻两位差的绝对值是1，我们称它为阶梯数，譬如：12，121，345，987，876789
    //现在我们给到输入m 要求输出m以内所有阶梯数的和

    // 输入m，输出m以内所有阶梯数的和方法
    public static Integer sumStepNum(Integer m) {
        if (m == null || m < 10) {
            return 0;
        }
        // 求出m以内所有的阶梯数
        Integer sumNum = 0;
        for (int i = 1; i < m - 9; i++) {
            Integer num = m - i;
            if (isStepNum(num)) {
                System.out.println(num);
                sumNum += num;
            }
        }
        System.out.println(m + "以内所有阶梯数之和为：" + sumNum);
        return sumNum;
    }

    // 判断是否阶梯数方法
    public static Boolean isStepNum(Integer num) {
        int len = String.valueOf(num).length();

        Integer lastNum = null;
        Boolean isStep = true;
        for (int i = 0; i < len; i++) {
            // 取出当前数的个位数，十位数，百位数，千位数。。。。。
            Integer nowNum = (int) ((num / Math.pow(10, i)) % 10);
            if (lastNum != null) {
                int cha = Math.abs(lastNum - nowNum);
                if (cha != 1) {
                    isStep = false;
                    break;
                }
            }
            lastNum = nowNum;
        }
        return isStep;
    }

    public static void main(String[] args) {
        sumStepNum(100000);
    }
}
