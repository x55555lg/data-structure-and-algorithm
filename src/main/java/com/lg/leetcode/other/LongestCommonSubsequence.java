package com.lg.leetcode.other;

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

    private static class BruteForce {
        public static int longestCommonSubsequence(String str1, String str2) {
            if (str1 == null || str2 == null) {
                return 0;
            }
            return recurse(str1.length() - 1, str2.length() - 1, str1.toCharArray(), str2.toCharArray());
        }

        private static int recurse(int curIdx1, int curIdx2, char[] str1, char[] str2) {
            // base case
            if (curIdx1 == 0 && curIdx2 == 0) {
                // 都比较到第一个字符了，看是不是相等
                return str1[curIdx1] == str2[curIdx2] ? 1 : 0;
            }

            // base case
            if (curIdx1 == 0 && curIdx2 != 0) {
                // 最长公共子序列既以str1[curIdx1]结尾，又以str2[curIdx2]结尾
                int r1 = str1[0] == str2[curIdx2] ? 1 : 0;
                // 最长公共子序列以str1[curIdx1]结尾，不以str2[curIdx2]结尾
                int r2 = recurse(0, curIdx2 - 1, str1, str2); ;
                // 最长公共子序列既不以str1[curIdx1]结尾，又不以str2[curIdx2]结尾
                int r4 = 0;
                // 最长公共子序列不以str1[curIdx1]结尾，以str2[curIdx2]结尾
                int r3 = 0;
                // 求r1,r2,r3,r4的最大值
                return Math.max(Math.max(r1, r2), Math.max(r3, r4));
            }

            // base case
            if (curIdx1 != 0 && curIdx2 == 0) {
                // 最长公共子序列既以str1[curIdx1]结尾，又以str2[curIdx2]结尾
                int r1 = str1[curIdx1] == str2[0] ? 1 : 0;
                // 最长公共子序列以str1[curIdx1]结尾，不以str2[curIdx2]结尾
                int r2 = 0;
                // 最长公共子序列既不以str1[curIdx1]结尾，又不以str2[curIdx2]结尾
                int r4 = 0;
                // 最长公共子序列不以str1[curIdx1]结尾，以str2[curIdx2]结尾
                int r3 = recurse(curIdx1 - 1, 0, str1, str2);
                // 求r1,r2,r3,r4的最大值
                return Math.max(Math.max(r1, r2), Math.max(r3, r4));
            }

            assert curIdx1 != 0 && curIdx2 != 0;

            // 最长公共子序列既以str1[curIdx1]结尾，又以str2[curIdx2]结尾
            int r1 = 0;
            if (str1[curIdx1] == str2[curIdx2]) {
                r1 = 1 + recurse(curIdx1 - 1, curIdx2 - 1, str1, str2);
            }

            // 最长公共子序列以str1[curIdx1]结尾，不以str2[curIdx2]结尾
            int r2 = recurse(curIdx1, curIdx2 - 1, str1, str2);

            // 最长公共子序列不以str1[curIdx1]结尾，以str2[curIdx2]结尾
            int r3 = recurse(curIdx1 - 1, curIdx2, str1, str2);

            // 最长公共子序列既不以str1[curIdx1]结尾，又不以str2[curIdx2]结尾
            int r4 = recurse(curIdx1 - 1, curIdx2 - 1, str1, str2);

            // 求r1,r2,r3,r4的最大值
            return Math.max(Math.max(r1, r2), Math.max(r3, r4));
        }
    }

    private static class MemorySearch {
        public static int longestCommonSubsequence(String s1, String s2) {
            if (s1 == null || s2 == null) {
                return 0;
            }
            int[][] table = new int[s1.length()][s2.length()];
            for (int i = 0; i < s1.length(); i++) {
                for (int j = 0; j < s2.length(); j++) {
                    table[i][j] = -1;
                }
            }
            return recurse(s1.length() - 1, s2.length() - 1, s1.toCharArray(), s2.toCharArray(), table);
        }

        private static int recurse(int idx1, int idx2, char[] str1, char[] str2, int[][] table) {
            // go cache
            if (table[idx1][idx2] != -1) {
                return table[idx1][idx2];
            }

            // base case
            if (idx1 == 0 && idx2 == 0) {
                int r = str1[0] == str2[0] ? 1 : 0;
                table[0][0] = r;
                return r;
            }

            if (idx1 == 0 && idx2 != 0) {
                int r1 = str1[0] == str2[idx2] ? 1 : 0;
                int r2 = recurse(0, idx2 - 1, str1, str2, table);
                int r3 = 0;
                int r4 = 0;
                // get the max value
                int r = Math.max(Math.max(r1, r2), Math.max(r3, r4));
                table[idx1][idx2] = r;
                return r;
            }

            if (idx1 != 0 && idx2 == 0) {
                int r1 = str1[idx1] == str2[0] ? 1 : 0;
                int r2 = recurse(idx1 - 1, 0, str1, str2, table);
                int r3 = 0;
                int r4 = 0;
                // get the max value
                int r = Math.max(Math.max(r1, r2), Math.max(r3, r4));
                table[idx1][idx2] = r;
                return r;
            }

            assert idx1 != 0 && idx2 != 0;

            int r1 = 0;
            if (str1[idx1] == str2[idx2]) {
                r1 = 1 + recurse(idx1 - 1, idx2 - 1, str1, str2, table);
            }
            int r2 = recurse(idx1, idx2 - 1, str1, str2, table);
            int r3 = recurse(idx1 - 1, idx2, str1, str2, table);
            int r4 = recurse(idx1 - 1, idx2 - 1, str1, str2, table);

            // get the max value
            int r = Math.max(Math.max(r1, r2), Math.max(r3, r4));
            table[idx1][idx2] = r;
            return r;
        }
    }

    private static class DP {
        public static int longestCommonSubsequence(String s1, String s2) {
            if (s1 == null || s2 == null) {
                return 0;
            }
            int[][] table = new int[s1.length()][s2.length()];
            char[] str1 = s1.toCharArray(), str2 = s2.toCharArray();
            // base case
            table[0][0] = str1[0] == str2[0] ? 1 : 0;
            // idx1 == 0 && idx2 != 0
            for (int idx2 = 1; idx2 < str2.length; idx2++) {
                int r1 = str1[0] == str2[idx2] ? 1 : 0;
                int r2 = 0;
                int r3 = table[0][idx2 - 1];
                int r4 = 0;
                table[0][idx2] = Math.max(Math.max(r1, r2), Math.max(r3, r4));
            }
            // idx1 != 0 && idx2 == 0
            for (int idx1 = 1; idx1 < str1.length; idx1++) {
                int r1 = str1[idx1] == str2[0] ? 1 : 0;
                int r2 = table[idx1 - 1][0];
                int r3 = 0;
                int r4 = 0;
                table[idx1][0] = Math.max(Math.max(r1, r2), Math.max(r3, r4));
            }
            for (int idx1 = 1; idx1 < str1.length; idx1++) {
                for (int idx2 = 1; idx2 < str2.length; idx2++) {
                    int r1 = str1[idx1] == str2[idx2] ? 1 + table[idx1 - 1][idx2 - 1] : 0;
                    int r2 = table[idx1][idx2 - 1];
                    int r3 = table[idx1 - 1][idx2];
                    int r4 = table[idx1 - 1][idx2 - 1];
                    // get the max value
                    table[idx1][idx2] = Math.max(Math.max(r1, r2), Math.max(r3, r4));
                }
            }
            if (false) {
                char[] chars = new char[table[str1.length - 1][str2.length - 1]];
                int idx = chars.length - 1;
                int idx1 = str1.length - 1, idx2 = str2.length - 1;
                while (table[idx1][idx2] != 0) {
                    if (table[idx1][idx2] == table[idx1 - 1][idx2]) {
                        idx1--;
                    } else if (table[idx1][idx2] == table[idx1][idx2 - 1]) {
                        idx2--;
                    } else {
                        chars[idx--] = str1[idx1];
                        idx1--;
                        idx2--;
                    }
                }
                System.out.println(new String(chars));
            }
            return table[str1.length - 1][str2.length - 1];
        }
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

    /**
     * cn.hutool.core.text.TextSimilarity#longestCommonSubstring(java.lang.String, java.lang.String)
     */
    private static int longestCommonSubstring(String strA, String strB) {
        if (strA == null || strB == null) {
            return 0;
        }
        char[] chars_strA = strA.toCharArray();
        char[] chars_strB = strB.toCharArray();
        int m = chars_strA.length;
        int n = chars_strB.length;

        // 初始化矩阵数据,matrix[0][0]的值为0， 如果字符数组chars_strA和chars_strB的对应位相同，
        // 则matrix[i][j]的值为左上角的值加1， 否则，matrix[i][j]的值等于左上方最近两个位置的较大值， 矩阵中其余各点的值为0.
        int[][] matrix = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (chars_strA[i - 1] == chars_strB[j - 1]) {
                    matrix[i][j] = matrix[i - 1][j - 1] + 1;
                } else {
                    matrix[i][j] = Math.max(matrix[i][j - 1], matrix[i - 1][j]);
                }
            }
        }

        // 矩阵中，如果matrix[m][n]的值不等于matrix[m-1][n]的值也不等于matrix[m][n-1]的值，
        // 则matrix[m][n]对应的字符为相似字符元，并将其存入result数组中。
        char[] result = new char[matrix[m][n]];
        int currentIndex = result.length - 1;
        while (matrix[m][n] != 0) {
            if (matrix[m][n] == matrix[m][n - 1]) {
                n--;
            } else if (matrix[m][n] == matrix[m - 1][n]) {
                m--;
            } else {
                result[currentIndex] = chars_strA[m - 1];
                currentIndex--;
                n--;
                m--;
            }
        }
        System.out.println(new String(result));
        return new String(result).length();
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
                int r0 = longestCommonSubstring(str1, str2);
                int r1 = lcse(str1.toCharArray(), str2.toCharArray());
                int r2 = BruteForce.longestCommonSubsequence(str1, str2);
                int r3 = DP.longestCommonSubsequence(str1, str2);
                int r4 = MemorySearch.longestCommonSubsequence(str1, str2);
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
