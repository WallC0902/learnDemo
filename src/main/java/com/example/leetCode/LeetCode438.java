package com.example.leetCode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangchangpeng on 2019/8/14.
 */
public class LeetCode438 {

//    给定一个字符串 s 和一个非空字符串 p，找到 s 中所有是 p 的字母异位词的子串，返回这些子串的起始索引。
//
//    字符串只包含小写英文字母，并且字符串 s 和 p 的长度都不超过 20100。
//
//    说明：
//
//    字母异位词指字母相同，但排列不同的字符串。
//    不考虑答案输出的顺序。
//    示例 1:
//
//    输入:
//    s: "cbaebabacd" p: "abc"
//
//    输出:
//            [0, 6]
//
//    解释:
//    起始索引等于 0 的子串是 "cba", 它是 "abc" 的字母异位词。
//    起始索引等于 6 的子串是 "bac", 它是 "abc" 的字母异位词。
//             示例 2:
//
//    输入:
//    s: "abab" p: "ab"
//
//    输出:
//            [0, 1, 2]
//
//    解释:
//    起始索引等于 0 的子串是 "ab", 它是 "ab" 的字母异位词。
//    起始索引等于 1 的子串是 "ba", 它是 "ab" 的字母异位词。
//    起始索引等于 2 的子串是 "ab", 它是 "ab" 的字母异位词。
//
//    来源：力扣（LeetCode）
//    链接：https://leetcode-cn.com/problems/find-all-anagrams-in-a-string
//    著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。


    /**
     * 主方法
     */
    public static void main(String[] args) {
//        System.out.println('a'-'a');

        List<Integer> anagrams = findAnagrams("cbaebertfsgffdfabacdfsfg", "ffgs");
        for(int i : anagrams)
            System.out.println(i);
    }


    public static List<Integer> findAnagrams(String s, String p) {
        if (s.length() < p.length()) {
            return new ArrayList<>();
        }

        List<Integer> result = new ArrayList<>();
        int[] hash = new int[26];

        char[] sArr = s.toCharArray();
        char[] pArr = p.toCharArray();

        for (int i = 0; i < pArr.length; i ++) {
            hash[pArr[i] - 'a'] ++;
        }

        int l = 0;
        int count = 0;
        int len = pArr.length;

        for (int i = 0 ; i < sArr.length; i ++) {
            hash[sArr[i] - 'a'] --;

            if (hash[sArr[i] - 'a'] >= 0) {
                count ++;
            }

            if (i > len -1) {
                hash[sArr[l]  - 'a'] ++;

                if (hash[sArr[l]  - 'a'] > 0) {
                    count --;
                }
                l++;
            }
            if (count == len) {
                result.add(l);
            }

        }
        return result;
    }




}
