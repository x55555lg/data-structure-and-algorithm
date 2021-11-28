package com.lg.leetcode.other.practice1;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Xulg
 * Description: 求两个字符串的最长公共子序列长度
 * Created in 2021-05-24 16:24
 */
@SuppressWarnings("ConstantConditions")
class LongestCommonSubsequence {
    /*
     *leetcode 1143
     *
     * Given two strings text1 and text2, return the length of their longest common subsequence.
     * If there is no common subsequence, return 0.
     *
     * A subsequence of a string is a new string generated from the original string with some characters
     * (can be none) deleted without changing the relative order of the remaining characters.
     *
     * For example, "ace" is a subsequence of "abcde".
     * A common subsequence of two strings is a subsequence that is common to both strings.
     *
     * Example 1:
     * Input: text1 = "abcde", text2 = "ace"
     * Output: 3
     * Explanation: The longest common subsequence is "ace" and its length is 3.
     * Example 2:
     * Input: text1 = "abc", text2 = "abc"
     * Output: 3
     * Explanation: The longest common subsequence is "abc" and its length is 3.
     * Example 3:
     * Input: text1 = "abc", text2 = "def"
     * Output: 0
     * Explanation: There is no such common subsequence, so the result is 0.
     *
     * Constraints:
     * 1 <= text1.length, text2.length <= 1000
     * text1 and text2 consist of only lowercase English characters.
     */

    public static int longestCommonSubsequence(String text1, String text2) {
        return 0;
    }

    /* ****************************************************************************************************************/

    /* 对数器方法 */

    public static int lcse(char[] str1, char[] str2) {
        int[][] dp = new int[str1.length][str2.length];
        dp[0][0] = str1[0] == str2[0] ? 1 : 0;
        for (int i = 1; i < str1.length; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], str1[i] == str2[0] ? 1 : 0);
        }
        for (int j = 1; j < str2.length; j++) {
            dp[0][j] = Math.max(dp[0][j - 1], str1[0] == str2[j] ? 1 : 0);
        }
        for (int i = 1; i < str1.length; i++) {
            for (int j = 1; j < str2.length; j++) {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                if (str1[i] == str2[j]) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1] + 1);
                }
            }
        }
        return dp[str1.length - 1][str2.length - 1];
    }

    /* ****************************************************************************************************************/

    public static void main(String[] args) {
        HashSet<String> already = new HashSet<>();
        HashMap<Integer, Integer> countMap = new HashMap<>();
        int times = 1000000;
        for (int time = 0; time < times; time++) {
            String str1 = RandomStringUtils.randomAlphabetic(8);
            String str2 = RandomStringUtils.randomAlphabetic(8);
            if (!already.contains(str1 + str2)) {
                int r0 = 0;
                int r1 = lcse(str1.toCharArray(), str2.toCharArray());
                int r2 = 0;
                int r3 = 0;
                int r4 = 0;
                if (!(r0 == r1 && r1 == r2 && r2 == r3 && r3 == r4)) {
                    System.out.println("Oops");
                }
                already.add(str1 + str2);
                if (countMap.containsKey(r1)) {
                    countMap.put(r1, countMap.get(r1) + 1);
                } else {
                    countMap.put(r1, 1);
                }
            }
        }
        System.out.println("finish!");
    }

}
