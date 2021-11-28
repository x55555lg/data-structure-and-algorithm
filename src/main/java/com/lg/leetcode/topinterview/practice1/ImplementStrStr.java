package com.lg.leetcode.topinterview.practice1;

/**
 * @author Xulg
 * Created in 2021-02-25 11:31
 */
@SuppressWarnings({"unused", "AlibabaClassNamingShouldBeCamel"})
class ImplementStrStr {

    /*
     * Implement strStr().
     * Return the index of the first occurrence of needle in haystack, or -1 if needle is not part of haystack.
     *
     * Clarification:
     *  What should we return when needle is an empty string? This is a great question to ask during an interview.
     *  For the purpose of this problem, we will return 0 when needle is an empty string. This is consistent to C's strstr() and Java's indexOf().
     *
     * Example 1:
     * Input: haystack = "hello", needle = "ll"
     * Output: 2
     *
     * Example 2:
     * Input: haystack = "aaaaa", needle = "bba"
     * Output: -1
     *
     * Example 3:
     * Input: haystack = "", needle = ""
     * Output: 0
     *
     * Constraints:
     *  0 <= haystack.length, needle.length <= 5 * 104
     *  haystack and needle consist of only lower-case English characters.
     *
     *-----------------------------------------------------------------------------------------------
     * 实现indexOf(str, match)方法，如果str或match是空串，返回0
     */

    public static int strStr(String haystack, String needle) {
        return 0;
    }

    public static void main(String[] args) {
    }

    /* 对数器 */

    static String getRandomString(int possibilities, int size) {
        char[] ans = new char[(int) (Math.random() * size) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
        }
        return String.valueOf(ans);
    }
}
