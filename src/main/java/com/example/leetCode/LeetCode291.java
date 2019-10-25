package com.example.leetCode;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangchangpeng on 2019/10/21.
 */
public class LeetCode291 {

    /**
     * 给定一种规律 pattern 和一个字符串 str ，判断 str 是否遵循相同的规律。
     *
     * 这里的 遵循 指完全匹配，例如， pattern 里的每个字母和字符串 str 中的每个非空单词之间存在着双向连接的对应规律。
     *
     * 示例1:
     *
     * 输入: pattern = "abba", str = "dog cat cat dog"
     * 输出: true
     * 示例 2:
     *
     * 输入:pattern = "abba", str = "dog cat cat fish"
     * 输出: false
     * 示例 3:
     *
     * 输入: pattern = "aaaa", str = "dog cat cat dog"
     * 输出: false
     * 示例 4:
     *
     * 输入: pattern = "abba", str = "dog dog dog dog"
     * 输出: false
     * 说明:
     * 你可以假设 pattern 只包含小写字母， str 包含了由单个空格分隔的小写字母。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/word-pattern
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */

    /**
     * 主方法
     */
    public static void main(String[] args) {
//        System.out.println(solution("abba", "dog cat cat dog"));
//        System.out.println(solution("abba", "dog cat cat fish"));
//        System.out.println(solution("aaaa", "dog cat cat dog"));
//        System.out.println(solution("abba", "dog dog dog dog"));
        System.out.println(solutionB("abba", "dog cat cat dog"));
        System.out.println(solutionB("abba", "dog cat cat fish"));
        System.out.println(solutionB("aaaa", "dog cat cat dog"));
        System.out.println(solutionB("abba", "dog dog dog dog"));
    }



    public static boolean solution(String pattern, String str){
        if (StringUtils.isEmpty(pattern) || StringUtils.isEmpty(str)) {
            return false;
        }
        String[] splitB = str.split(" ");
//        System.out.println("splitB.length------>" + splitB.length);
        if (splitB.length != pattern.length()) {
            return false;
        }
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < splitB.length; i++) {
            String chat = String.valueOf(pattern.charAt(i));
            if (map.get(splitB[i]) == null && !map.values().contains(chat)) {
               map.put(splitB[i], chat);
            }
        }
        String buildB = "";
        for (int i = 0; i < splitB.length; i++){
            buildB += map.get(splitB[i]);
        }
        System.out.println("buildB------->" + buildB);
        if (pattern.equals(buildB)) {
            return true;
        }
        return false;
    }


    public static boolean solutionB(String pattern, String str){
        if (pattern.isEmpty() || str.isEmpty()) {
            return false;
        }
        String[] spiltStr = str.split(" ");
        if (spiltStr.length != pattern.length()) {
            return false;
        }
        Map<Character, String> map = new HashMap<>();
        for (int i = 0; i < spiltStr.length; i++) {
            if (map.get(pattern.charAt(i)) != null) {
                if (!map.get(pattern.charAt(i)).equals(spiltStr[i])) {
                    return false;
                }
            } else if (map.values().contains(spiltStr[i])) {
                return false;
            } else {
                map.put(pattern.charAt(i), spiltStr[i]);
            }
        }
        return true;
    }


}
