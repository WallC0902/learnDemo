package com.example.leetCode;

import java.util.Arrays;

/**
 * Created by wangchangpeng on 2019/8/15.
 */
public class LeetCode242 {

//    给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。
//
//    示例 1:
//
//    输入: s = "anagram", t = "nagaram"
//    输出: true
//    示例 2:
//
//    输入: s = "rat", t = "car"
//    输出: false
//    说明:
//    你可以假设字符串只包含小写字母。
//
//    来源：力扣（LeetCode）
//    链接：https://leetcode-cn.com/problems/valid-anagram
//    著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。


    /**
     * 主方法
     */
    public static void main(String[] args) {
        System.out.println(isAnagram("anagram", "nagaram"));
        System.out.println(isAnagram("rat", "car"));
        System.out.println(isAnagramWithSort("anagram", "nagaram"));
        System.out.println(isAnagramWithSort("rat", "car"));
    }

    public static Boolean isAnagramWithSort(String s, String t){
        if (s.length() != t.length()) {
            return false;
        }

        char[] sArr = s.toCharArray();
        char[] tArr = t.toCharArray();

        Arrays.sort(sArr);
        Arrays.sort(tArr);

        String newS = new String(sArr);
        String newT = new String(tArr);

        if (newS.equals(newT)) {
            return true;
        } else {
            return false;
        }
    }


    public static Boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        int[] hash = new int[26];

        for (int i = 0; i < s.length(); i++) {
            hash[s.charAt(i) - 'a']++;
        }

        for (int i = 0; i < t.length(); i++) {
            hash[t.charAt(i) - 'a'] --;

            if (hash[t.charAt(i) - 'a'] < 0){
                return false;
            }
        }
        return true;
    }



}
