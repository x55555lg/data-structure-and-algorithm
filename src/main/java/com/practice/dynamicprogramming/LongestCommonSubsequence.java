package com.practice.dynamicprogramming;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Xulg
 * Created in 2021-05-13 13:54
 */
class LongestCommonSubsequence {

    /*
     * 求两个字符串的最长公共子序列问题
     * 例如：
     *      字符串1：a123b
     *      字符串2：12de3f
     *      最长公共子序列：123
     * 求最长公共子序列的长度是多少
     *------------------------------------------------------------------------------------------------------------------
     * 实现思路：
     *      对于字符串str1，str2：
     *          char[] str1 = { 'a', '1', '2', '3', 'b' };
     *                 idx1      0    1    2    3    4
     *          char[] str2 = { '1', '2', 'd', 'e', '3', 'f' };
     *                 idx2      0    1    2    3    4    5
     *      动态规划表：
     *          int[][] dpTable = new int[str1.length][str2.length];
     *      dpTable[idx1][idx2]的含义：str1在[0, idx1]上，str2在[0, idx2]上时，此时的最长子序列长度是多少
     *      分析：
     *         对于任意的位置dpTable[idx1][idx2]有如下4种情况：
     *          (1)str1，str2的最长子序列的末尾字符即以str1[idx1]结尾，又以str2[idx2]结尾。str1[idx1]字符 == str2[idx2]字符
     *             str1[0, idx1]，str2[0, idx2]的最长子序列长度就是str1[0, idx1-1]，str2[0, idx2-1]的最长子序列长度加1
     *              r1
     *                  dpTable[idx1][idx2] = dpTable[idx1-1][idx2-1] + 1;
     *          (2)str1，str2的最长子序列的末尾字符即不以str1[idx1]结尾，又不以str2[idx2]结尾。那么最长子序列末尾字符只能
     *             去str1[idx1-1]，str2[idx2-1]上判断了，
     *             str1[0, idx1]，str2[0, idx2]的最长子序列长度就是str1[0, idx1-1]，str2[0, idx2-1]的最长子序列长度
     *              r2
     *                  dpTable[idx1][idx2] = dpTable[idx1-1][idx2-1];
     *          (3)str1，str2的最长子序列的末尾字符以str1[idx1]结尾，不以str2[idx2]结尾。那么最长子序列末尾字符只能
     *             去str1[idx1]，str2[idx2-1]上判断了，
     *             str1[0, idx1]，str2[0, idx2]的最长子序列长度就是str1[0, idx1]，str2[0, idx2-1]的最长子序列长度
     *              r3
     *                  dpTable[idx1][idx2] = dpTable[idx1][idx2-1];
     *          (4)str1，str2的最长子序列的末尾字符即不以str1[idx1]结尾，以str2[idx2]结尾，那么最长子序列末尾字符只能
     *             去str1[idx1-1]，str2[idx2]上判断了，
     *             str1[0, idx1]，str2[0, idx2]的最长子序列长度就是str1[0, idx1-1]，str2[0, idx2]的最长子序列长度
     *              r4
     *                  dpTable[idx1][idx2] = dpTable[idx1-1][idx2];
     *          所以：
     *                  dpTable[idx1][idx2]的最长子序列长度 = max(r1, r2, r3, r4);
     *         特殊位置：base case
     *              (1)dpTable[0][0]
     *                  str1在[0, 0]和str2在[0, 0]时的最长子序列长度：判断0位置的字符是否一样
     *                      如果str1[0] == str2[0]，则最长子序列长度就是1
     *                      如果str1[0] != str2[0]，则最长子序列长度就是0
     *
     *              (2)dpTable[0][idx2]：dpTable[0][1], dpTable[0][2] ... dpTable[0][idx2]
     *                  str1在[0, 0]和str2在[0, idx2]时的最长子序列长度有如下4种情况：
     *                      (1)如果最长子序列以str1[0]结尾，不以str2[idx2]结尾时：
     *                          r1
     *                          dpTable[0][idx2] = dpTable[0][idx2-1]
     *                      (2)如果最长子序列不以str1[0]结尾，以str2[idx2]结尾时：
     *                          r2
     *                          dpTable[0][idx2] = 0
     *                      (3)如果最长子序列不以str1[0]结尾，不以str2[idx2]结尾时：
     *                          r3
     *                          dpTable[0][idx2] = 0
     *                      (4)如果最长子序列以str1[0]结尾，以str2[idx2]结尾时：
     *                          r4
     *                          dpTable[0][idx2] = 1
     *                  所以：
     *                      dpTable[0][idx2]的最长子序列长度 = max(r1, r2, r3, r4)
     *                                                       = max(r1, r4);
     *
     *              (3)dpTable[idx1][0]：dpTable[1][0], dpTable[2][0] ... dpTable[idx1][0]
     *                  str1在[0, idx1]和str2在[0, 0]时的最长子序列长度有如下4种情况：
     *                      (1)如果最长子序列以str1[idx1]结尾，不以str2[0]结尾时：
     *                        str2只有一个字符又不以它结尾，你没的选，那么最长子序列长度为0
     *                          r1
     *                          dpTable[idx1][0] = 0
     *                      (2)如果最长子序列不以str1[idx1]结尾，以str2[0]结尾时：
     *                        看str1[0, idx1-1]和str2[0,0]上的情况
     *                          r2
     *                          dpTable[idx1][0] = dpTable[idx1-1][0];
     *                      (3)如果最长子序列不以str1[0]结尾，不以str2[idx2]结尾时：
     *                        str2只有一个字符又不以它结尾，你没的选，最长子序列长度为0
     *                          r3
     *                          dpTable[idx1][0] = 0
     *                      (4)如果最长子序列以str1[0]结尾，以str2[idx2]结尾时：
     *                        因为str2只有1个字符，所以最长子序列长度为1
     *                          r4
     *                          dpTable[idx1][0] = 1
     *                  所以：
     *                      dpTable[idx1][0]的最长子序列长度 = max(r1, r2, r3, r4)
     *                                                       = max(r2, r4);
     *
     */

    @SuppressWarnings("ConstantConditions")
    private static class BruteForce {
        public static int getLongestCommonSubsequenceSize(String str1, String str2) {
            if (str1 == null | str2 == null) {
                return 0;
            }
            return recurse(str1.toCharArray(), str2.toCharArray(),
                    str1.length() - 1, str2.length() - 1);
        }

        private static int recurse(char[] str1, char[] str2, int idx1, int idx2) {
            // str1[0, 0] str2[0, 0]上的最长公共子序列就看0字符是否相等了，相等的话最长子序列就是1
            if (idx1 == 0 && idx2 == 0) {
                return str1[idx1] == str2[idx2] ? 1 : 0;
            }

            // str1[0, 0] str2[0, idx2]上的最长公共子序列，有4种情况
            if (idx1 == 0 && idx2 != 0) {
                /*
                 *str1在[0, 0]和str2在[0, idx2]时的最长子序列长度有如下4种情况：
                 *    (1)如果最长子序列以str1[0]结尾，不以str2[idx2]结尾时：
                 *        r1
                 *        dpTable[0][idx2] = dpTable[0][idx2-1]
                 *    (2)如果最长子序列不以str1[0]结尾，以str2[idx2]结尾时：
                 *        r2
                 *        dpTable[0][idx2] = 0
                 *    (3)如果最长子序列不以str1[0]结尾，不以str2[idx2]结尾时：
                 *        r3
                 *        dpTable[0][idx2] = 0
                 *    (4)如果最长子序列以str1[0]结尾，以str2[idx2]结尾时：
                 *        r4
                 *        dpTable[0][idx2] = 1
                 *所以：
                 *    dpTable[0][idx2]的最长子序列长度 = max(r1, r2, r3, r4)
                 *                                     = max(r1, r4);
                 */
                int r1 = recurse(str1, str2, 0, idx2 - 1);
                int r2 = 0;
                int r3 = 0;
                int r4 = str1[0] == str2[idx2] ? 1 : 0;
                return Math.max(Math.max(r1, r2), Math.max(r3, r4));
                /*
                if (str1[0] == str2[idx2]) {
                    // 最后一个字符相等，那么当前的最长公共子序列是1
                    // 然后求str1[0, 0] str2[0, idx2-1]上的最大公共子序列
                    // 取最大值
                    //return Math.max(1, recurse(str1, str2, 0, idx2 - 1));
                    return 1;
                } else {
                    // 求str1[0, 0] str2[0, idx2-1]上的最大公共子序列
                    return recurse(str1, str2, 0, idx2 - 1);
                }
                */
            }

            // str1[0, idx1] str2[0, 0]上的最长公共子序列，有4种情况
            if (idx1 != 0 && idx2 == 0) {
                /*
                 *str1在[0, idx1]和str2在[0, 0]时的最长子序列长度有如下4种情况：
                 *    (1)如果最长子序列以str1[idx1]结尾，不以str2[0]结尾时：
                 *       str2只有一个字符又不以它结尾，你没的选，那么最长子序列长度为0
                 *        r1
                 *      dpTable[idx1][0] = 0
                 *    (2)如果最长子序列不以str1[idx1]结尾，以str2[0]结尾时：
                 *       看str1[0, idx1-1]和str2[0,0]上的情况
                 *        r2
                 *        dpTable[idx1][0] = dpTable[idx1-1][0];
                 *    (3)如果最长子序列不以str1[0]结尾，不以str2[idx2]结尾时：
                 *       str2只有一个字符又不以它结尾，你没的选，最长子序列长度为0
                 *        r3
                 *        dpTable[idx1][0] = 0
                 *    (4)如果最长子序列以str1[0]结尾，以str2[idx2]结尾时：
                 *       因为str2只有1个字符，所以最长子序列长度为1
                 *        r4
                 *        dpTable[idx1][0] = 1
                 *所以：
                 *    dpTable[idx1][0]的最长子序列长度 = max(r1, r2, r3, r4)
                 *                                     = max(r2, r4);
                 */
                int r1 = 0;
                int r2 = recurse(str1, str2, idx1 - 1, 0);
                int r3 = 0;
                int r4 = str1[idx1] == str2[0] ? 1 : 0;
                return Math.max(Math.max(r1, r2), Math.max(r3, r4));
                /*
                if (str1[idx1] == str2[0]) {
                    // 最后一个字符相等，那么当前的最长公共子序列是1
                    // 然后求str1[0, idx1-1] str2[0, 0]上的最大公共子序列
                    // 取最大值
                    //return Math.max(1, recurse(str1, str2, idx1 - 1, 0));
                    return 1;
                } else {
                    return recurse(str1, str2, idx1 - 1, 0);
                }
                */
            }

            assert idx1 != 0 && idx2 != 0;
            /*对于任意的位置dpTable[idx1][idx2]有如下4种情况*/

            /*
             * (1)str1，str2的最长子序列的末尾字符即以str1[idx1]结尾，又以str2[idx2]结尾。str1[idx1]字符 == str2[idx2]字符
             *    str1[0, idx1]，str2[0, idx2]的最长子序列长度就是str1[0, idx1-1]，str2[0, idx2-1]的最长子序列长度加1
             *     dpTable[idx1][idx2] = dpTable[idx1-1][idx2-1] + 1;
             */
            int r1 = 0;
            if (str1[idx1] == str2[idx2]) {
                r1 = recurse(str1, str2, idx1 - 1, idx2 - 1) + 1;
            }

            /*
             * (2)str1，str2的最长子序列的末尾字符即不以str1[idx1]结尾，又不以str2[idx2]结尾。那么最长子序列末尾字符只能
             *    去str1[idx1-1]，str2[idx2-1]上判断了，
             *    str1[0, idx1]，str2[0, idx2]的最长子序列长度就是str1[0, idx1-1]，str2[0, idx2-1]的最长子序列长度
             *     dpTable[idx1][idx2] = dpTable[idx1-1][idx2-1];
             */
            int r2 = recurse(str1, str2, idx1 - 1, idx2 - 1);

            /*
             * (3)str1，str2的最长子序列的末尾字符以str1[idx1]结尾，不以str2[idx2]结尾。那么最长子序列末尾字符只能
             *    去str1[idx1]，str2[idx2-1]上判断了，
             *    str1[0, idx1]，str2[0, idx2]的最长子序列长度就是str1[0, idx1]，str2[0, idx2-1]的最长子序列长度
             *     dpTable[idx1][idx2] = dpTable[idx1][idx2-1];
             */
            int r3 = recurse(str1, str2, idx1, idx2 - 1);

            /*
             * (4)str1，str2的最长子序列的末尾字符即不以str1[idx1]结尾，以str2[idx2]结尾，那么最长子序列末尾字符只能
             *    去str1[idx1-1]，str2[idx2]上判断了，
             *    str1[0, idx1]，str2[0, idx2]的最长子序列长度就是str1[0, idx1-1]，str2[0, idx2]的最长子序列长度
             *     dpTable[idx1][idx2] = dpTable[idx1-1][idx2];
             */
            int r4 = recurse(str1, str2, idx1 - 1, idx2);

            // 求r1,r2,r3,r4的最大值
            return Math.max(Math.max(r1, r2), Math.max(r3, r4));
        }
    }

    private static class DP {
        public static int getLongestCommonSubsequenceSize(String s1, String s2) {
            if (s1 == null | s2 == null) {
                return 0;
            }

            char[] str1 = s1.toCharArray();
            char[] str2 = s2.toCharArray();
            int[][] table = new int[str1.length][str2.length];

            /* base case */

            // str1[0, 0] str2[0, 0]上的最长公共子序列就看0字符是否相等了，相等的话最长子序列就是1
            table[0][0] = str1[0] == str2[0] ? 1 : 0;

            // str1[0, 0] str2[0, idx2]上的最长公共子序列，有4种情况
            for (int idx2 = 1; idx2 < str2.length; idx2++) {
                /*
                 *str1在[0, 0]和str2在[0, idx2]时的最长子序列长度有如下4种情况：
                 *    (1)如果最长子序列以str1[0]结尾，不以str2[idx2]结尾时：
                 *        r1
                 *        dpTable[0][idx2] = dpTable[0][idx2-1]
                 *    (2)如果最长子序列不以str1[0]结尾，以str2[idx2]结尾时：
                 *        r2
                 *        dpTable[0][idx2] = 0
                 *    (3)如果最长子序列不以str1[0]结尾，不以str2[idx2]结尾时：
                 *        r3
                 *        dpTable[0][idx2] = 0
                 *    (4)如果最长子序列以str1[0]结尾，以str2[idx2]结尾时：
                 *        r4
                 *        dpTable[0][idx2] = 1
                 *所以：
                 *    dpTable[0][idx2]的最长子序列长度 = max(r1, r2, r3, r4)
                 *                                     = max(r1, r4);
                 */
                int r1 = table[0][idx2 - 1];
                int r2 = 0;
                int r3 = 0;
                int r4 = str1[0] == str2[idx2] ? 1 : 0;
                table[0][idx2] = Math.max(Math.max(r1, r2), Math.max(r3, r4));
            }

            // str1[0, idx1] str2[0, 0]上的最长公共子序列，有4种情况
            for (int idx1 = 1; idx1 < str1.length; idx1++) {
                /*
                 *str1在[0, idx1]和str2在[0, 0]时的最长子序列长度有如下4种情况：
                 *    (1)如果最长子序列以str1[idx1]结尾，不以str2[0]结尾时：
                 *       str2只有一个字符又不以它结尾，你没的选，那么最长子序列长度为0
                 *        r1
                 *      dpTable[idx1][0] = 0
                 *    (2)如果最长子序列不以str1[idx1]结尾，以str2[0]结尾时：
                 *       看str1[0, idx1-1]和str2[0,0]上的情况
                 *        r2
                 *        dpTable[idx1][0] = dpTable[idx1-1][0];
                 *    (3)如果最长子序列不以str1[0]结尾，不以str2[idx2]结尾时：
                 *       str2只有一个字符又不以它结尾，你没的选，最长子序列长度为0
                 *        r3
                 *        dpTable[idx1][0] = 0
                 *    (4)如果最长子序列以str1[0]结尾，以str2[idx2]结尾时：
                 *       因为str2只有1个字符，所以最长子序列长度为1
                 *        r4
                 *        dpTable[idx1][0] = 1
                 *所以：
                 *    dpTable[idx1][0]的最长子序列长度 = max(r1, r2, r3, r4)
                 *                                     = max(r2, r4);
                 */
                int r1 = 0;
                int r2 = table[idx1 - 1][0];
                int r3 = 0;
                int r4 = str1[idx1] == str2[0] ? 1 : 0;
                table[idx1][0] = Math.max(Math.max(r1, r2), Math.max(r3, r4));
            }

            // fill table
            for (int idx1 = 1; idx1 < str1.length; idx1++) {
                for (int idx2 = 1; idx2 < str2.length; idx2++) {
                    /*对于任意的位置dpTable[idx1][idx2]有如下4种情况*/

                    /*
                     * (1)str1，str2的最长子序列的末尾字符即以str1[idx1]结尾，又以str2[idx2]结尾。str1[idx1]字符 == str2[idx2]字符
                     *    str1[0, idx1]，str2[0, idx2]的最长子序列长度就是str1[0, idx1-1]，str2[0, idx2-1]的最长子序列长度加1
                     *     dpTable[idx1][idx2] = dpTable[idx1-1][idx2-1] + 1;
                     */
                    int r1 = (str1[idx1] == str2[idx2]) ? table[idx1 - 1][idx2 - 1] + 1 : 0;

                    /*
                     * (2)str1，str2的最长子序列的末尾字符即不以str1[idx1]结尾，又不以str2[idx2]结尾。那么最长子序列末尾字符只能
                     *    去str1[idx1-1]，str2[idx2-1]上判断了，
                     *    str1[0, idx1]，str2[0, idx2]的最长子序列长度就是str1[0, idx1-1]，str2[0, idx2-1]的最长子序列长度
                     *     dpTable[idx1][idx2] = dpTable[idx1-1][idx2-1];
                     */
                    int r2 = table[idx1 - 1][idx2 - 1];

                    /*
                     * (3)str1，str2的最长子序列的末尾字符以str1[idx1]结尾，不以str2[idx2]结尾。那么最长子序列末尾字符只能
                     *    去str1[idx1]，str2[idx2-1]上判断了，
                     *    str1[0, idx1]，str2[0, idx2]的最长子序列长度就是str1[0, idx1]，str2[0, idx2-1]的最长子序列长度
                     *     dpTable[idx1][idx2] = dpTable[idx1][idx2-1];
                     */
                    int r3 = table[idx1][idx2 - 1];

                    /*
                     * (4)str1，str2的最长子序列的末尾字符即不以str1[idx1]结尾，以str2[idx2]结尾，那么最长子序列末尾字符只能
                     *    去str1[idx1-1]，str2[idx2]上判断了，
                     *    str1[0, idx1]，str2[0, idx2]的最长子序列长度就是str1[0, idx1-1]，str2[0, idx2]的最长子序列长度
                     *     dpTable[idx1][idx2] = dpTable[idx1-1][idx2];
                     */
                    int r4 = table[idx1 - 1][idx2];

                    // 求r1,r2,r3,r4的最大值
                    table[idx1][idx2] = Math.max(Math.max(r1, r2), Math.max(r3, r4));
                }
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

    /* ****************************************************************************************************************/

    public static void main(String[] args) {
        HashSet<String> already = new HashSet<>();
        HashMap<Integer, Integer> countMap = new HashMap<>();
        int times = 1000000;
        for (int time = 0; time < times; time++) {
            String str1 = RandomStringUtils.randomAlphabetic(8);
            String str2 = RandomStringUtils.randomAlphabetic(8);
            if (!already.contains(str1 + str2)) {
                int r1 = lcse(str1.toCharArray(), str2.toCharArray());
                int r2 = BruteForce.getLongestCommonSubsequenceSize(str1, str2);
                int r3 = DP.getLongestCommonSubsequenceSize(str1, str2);
                if (!(r1 == r2 && r2 == r3)) {
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
