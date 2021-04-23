package com.lg.leetcode.topinterview.practice;

import java.util.Arrays;

/**
 * @author Xulg
 * Created in 2021-03-02 13:44
 */
@SuppressWarnings("ForLoopReplaceableByForEach")
class ValidAnagram {

    /*
     * Given two strings s and t , write a function to determine if t is an anagram of s.
     *
     * Example 1:
     * Input: s = "anagram", t = "nagaram"
     * Output: true
     *
     * Example 2:
     * Input: s = "rat", t = "car"
     * Output: false
     *
     * Note:
     * You may assume the string contains only lowercase alphabets.
     *
     * Follow up:
     * What if the inputs contain unicode characters? How would you adapt your solution to such case?
     *-----------------------------------------------------------------------------------------------
     * 不考虑次序，判断字符串s，t的是否相等
     */

    public static boolean isAnagram(String s, String t) {
        return false;
    }

    /**
     * 时间复杂度O(N*logN)
     */
    private static class Solution1 {

        public static boolean isAnagram(String s, String t) {
            /*
             * 先对字符数组排序，再比对字符数组是否一样
             */
            char[] chars1 = s.toCharArray();
            char[] chars2 = t.toCharArray();
            Arrays.sort(chars1);
            Arrays.sort(chars2);
            return Arrays.equals(chars1, chars2);
        }

    }

    /**
     * 时间复杂度O(N)
     */
    private static class Solution2 {

        public static boolean isAnagram(String s, String t) {
            if (s.length() != t.length()) {
                // 长度不相等，肯定false
                return false;
            }
            int totalChars = 26;
            char[] chars1 = s.toCharArray();
            char[] chars2 = t.toCharArray();
            // 统计字符串的词频，[a-z]26个字母
            int[] arr1 = new int[totalChars];
            for (int i = 0; i < chars1.length; i++) {
                char c = chars1[i];
                arr1[c - 'a']++;
            }
            int[] arr2 = new int[totalChars];
            for (int i = 0; i < chars2.length; i++) {
                char c = chars2[i];
                arr2[c - 'a']++;
            }
            for (int i = 0; i < totalChars; i++) {
                if (arr1[i] != arr2[i]) {
                    return false;
                }
            }
            return true;
        }

    }

    /**
     * 时间复杂度O(N)，常数项更小，空间使用更少
     */
    private static class Solution3 {

        public static boolean isAnagram(String s, String t) {
            if (s.length() != t.length()) {
                // 长度不相等，肯定false
                return false;
            }
            char[] chars1 = s.toCharArray(), chars2 = t.toCharArray();
            // 统计字符串的词频，[a-z]26个字母
            int[] letters = new int[26];
            for (int i = 0; i < chars1.length; i++) {
                letters[chars1[i] - 'a']++;
            }
            for (int i = 0; i < chars2.length; i++) {
                letters[chars1[i] - 'a']--;
            }
            for (int i = 0; i < letters.length; i++) {
                if (letters[i] != 0) {
                    return false;
                }
            }
            return true;
        }

    }

    public static void main(String[] args) {
        System.out.println(Solution1.isAnagram("a", "b"));
        System.out.println(Solution2.isAnagram("a", "b"));
        System.out.println(Solution3.isAnagram("a", "b"));
    }

}

