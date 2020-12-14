package com.lg.algorithm.dynamicprogramming.practice;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.HashSet;

/**
 * @author Xulg
 * Created in 2020-12-10 14:14
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

    /* ****************************************************************************************************************/

    /**
     * 暴力递归
     * 求两个字符串的最长公共子序列的长度
     *
     * @param str1 字符串1
     * @param str2 字符串2
     * @return 最长子序列长度
     */
    public static int getLongestCommonSubsequenceLengthByViolenceRecursion(String str1, String str2) {
        if (str1 == null || str2 == null || "".equals(str1) || "".equals(str2)) {
            return 0;
        }
        return recurse(str1.toCharArray(), str2.toCharArray(), str1.length() - 1, str2.length() - 1);
    }

    /**
     * 暴力递归字符串的最长子序列长度
     *
     * @param strChars1 字符串1的字符数组
     * @param strChars2 字符串2的字符数组
     * @param strIdx1   字符串1的字符数组的索引位置
     * @param strIdx2   字符串2的字符数组的索引位置
     * @return str1和str2的最长子序列的长度
     */
    private static int recurse(char[] strChars1, char[] strChars2, int strIdx1, int strIdx2) {
        // base case
        if (strIdx1 == 0 && strIdx2 == 0) {
            return strChars1[0] == strChars2[0] ? 1 : 0;
        }
        if (strIdx1 == 0 && strIdx2 != 0) {
            if (strChars1[0] == strChars2[strIdx2]) {
                return Math.max(1, recurse(strChars1, strChars2, 0, strIdx2 - 1));
            } else {
                return recurse(strChars1, strChars2, 0, strIdx2 - 1);
            }
        }
        if (strIdx1 != 0 && strIdx2 == 0) {
            if (strChars1[strIdx1] == strChars2[0]) {
                return Math.max(1, recurse(strChars1, strChars2, strIdx1 - 1, 0));
            } else {
                return recurse(strChars1, strChars2, strIdx1 - 1, 0);
            }
        }

        /*
         * 对于任意位置idx1，idx2，最长子序列的结尾字符有4种情况：
         *  1）str1[idx1]位置字符，str2[idx2]位置字符，都不是最长子序列的结尾字符
         *      这种情况下，字符串str1[0...idx1]和字符串str2[0...idx2]的最长子序列长度，等价于：
         *          字符串str1[0...idx1-1]和字符串str2[0...idx2-1]的最长子序列
         *      因为最长子序列的最后一个字符只可能是str1[idx1-1]，str2[idx2-1]
         *              dpTable[idx1][idx2] = dpTable[idx1 - 1][idx2 - 1];
         *
         *  2）str1[idx1]位置字符，str2[idx2]位置字符，str1[idx1]是最长子序列的结尾字符，str2[idx2]不是最长子序列的结尾字符
         *      这种情况下，字符串str1[0...idx1]和字符串str2[0...idx2]的最长子序列长度，等价于：
         *          字符串str1[0...idx1]和字符串str2[0...idx2-1]的最长子序列
         *      因为最长子序列的最后一个字符只可能是str1[idx1-1]，str2[idx2-1]
         *
         *  3）str1[idx1]位置字符，str2[idx2]位置字符，str2[idx2]是最长子序列的结尾字符，str1[idx1]不是最长子序列的结尾字符
         *  4）str1[idx1]位置字符，str2[idx2]位置字符，都是最长子序列的结尾字符，即：str1[idx1] ==str2[idx2]
         *
         * (idx1,idx2)位置的最长子序列长度 = 4种情况下的最大值
         */

        // 1）
        int r1 = recurse(strChars1, strChars2, strIdx1 - 1, strIdx2 - 1);

        // 2）
        int r2 = recurse(strChars1, strChars2, strIdx1 - 1, strIdx2);

        // 3）
        int r3 = recurse(strChars1, strChars2, strIdx1, strIdx2 - 1);

        // 先取上面3种情况下的最大值
        int maxVal = NumberUtils.max(r1, r2, r3);

        // 如果此时两个字符串的字符相同，那么就求第4种情况，然后再取最大值
        if (strChars1[strIdx1] == strChars2[strIdx2]) {
            // 4）
            int r4 = recurse(strChars1, strChars2, strIdx1 - 1, strIdx2 - 1) + 1;
            maxVal = Math.max(maxVal, r4);
        }

        return maxVal;
    }

    /* ****************************************************************************************************************/

    /**
     * 动态规划
     * 求两个字符串的最长公共子序列的长度
     *
     * @param str1 字符串1
     * @param str2 字符串2
     * @return 最长子序列长度
     */
    public static int getLongestCommonSubsequenceLengthByDp(String str1, String str2) {
        if (str1 == null || str2 == null || "".equals(str1) || "".equals(str2)) {
            return 0;
        }

        char[] strChars1 = str1.toCharArray();
        char[] strChars2 = str2.toCharArray();
        int[][] dpTable = new int[str1.length()][str2.length()];

        // 0行0列
        // str1第1个字符和str2第1个字符是否相等的，如果相等的话。最长子序列的长度就是1，因为首字符就是最长子序列
        dpTable[0][0] = strChars1[0] == strChars2[0] ? 1 : 0;

        // 1行0列~n行0列
        for (int i = 1; i < str1.length(); i++) {
            /*
             * 1）最长子序列即以str1[i]字符结尾，又以str2[0]字符结尾，即：str1[i] == str2[0]
             *      dpTable[i][0] = 1
             * 2）最长子序列以str1[i]字符结尾，不以str2[0]字符结尾(不可能)
             *      dpTable[i][0] = 0
             * 3）最长子序列不以str1[i]字符结尾，以str2[0]字符结尾
             *      dpTable[i][0] = dpTable[i-1][0]
             * 4）最长子序列即不以str1[i]字符结尾，又不以str2[0]字符结尾(不可能)
             *      dpTable[i][0] = 0
             * 上面4种情况的最大值
             *  dpTable[i][0] = max( 1）, 2）, 3）, 4）);
             */
            if (strChars1[i] == strChars2[0]) {
                // 最长子序列即以str1[i]字符结尾，又以str2[0]字符结尾，即：str1[i] == str2[0]
                dpTable[i][0] = Math.max(1, dpTable[i - 1][0]);
            } else {
                dpTable[i][0] = dpTable[i - 1][0];
            }
        }

        // 0行1列~0行n列
        // str1的第1个字符和str2[1...n]的字符是否相等的，如果相等，那么最长子序列就是str1的第1个字符
        for (int i = 1; i < str2.length(); i++) {
            /*
             * 1）最长子序列即以str1[0]字符结尾，又以str2[i]字符结尾，即：str1[0] == str2[i]
             *      dpTable[0][i] = 1
             * 2）最长子序列以str1[0]字符结尾，不以str2[i]字符结尾
             *      dpTable[0][i] = dpTable[0][i-1]
             * 3）最长子序列不以str1[0]字符结尾，以str2[i]字符结尾
             *      dpTable[0][i] = 0
             * 4）最长子序列即不以str1[0]字符结尾，又不以str2[i]字符结尾
             *      dpTable[0][i] = 0
             */
            dpTable[0][i] = Math.max(strChars1[0] == strChars2[i] ? 1 : 0, dpTable[0][i - 1]);
        }

        // 0行和0列都已经处理掉了，所以从左到右从上到下遍历表格
        for (int idx1 = 1; idx1 < str1.length(); idx1++) {
            for (int idx2 = 1; idx2 < str2.length(); idx2++) {
                /*
                 * 对于任意位置idx1，idx2，最长子序列的结尾字符有4种情况：
                 *  1）str1[idx1]位置字符，str2[idx2]位置字符，都不是最长子序列的结尾字符
                 *      这种情况下，字符串str1[0...idx1]和字符串str2[0...idx2]的最长子序列长度，等价于：
                 *          字符串str1[0...idx1-1]和字符串str2[0...idx2-1]的最长子序列
                 *      因为最长子序列的最后一个字符只可能是str1[idx1-1]，str2[idx2-1]
                 *              dpTable[idx1][idx2] = dpTable[idx1 - 1][idx2 - 1];
                 *
                 *  2）str1[idx1]位置字符，str2[idx2]位置字符，str1[idx1]是最长子序列的结尾字符，str2[idx2]不是最长子序列的结尾字符
                 *      这种情况下，字符串str1[0...idx1]和字符串str2[0...idx2]的最长子序列长度，等价于：
                 *          字符串str1[0...idx1]和字符串str2[0...idx2-1]的最长子序列
                 *      因为最长子序列的最后一个字符只可能是str1[idx1-1]，str2[idx2-1]
                 *
                 *  3）str1[idx1]位置字符，str2[idx2]位置字符，str2[idx2]是最长子序列的结尾字符，str1[idx1]不是最长子序列的结尾字符
                 *  4）str1[idx1]位置字符，str2[idx2]位置字符，都是最长子序列的结尾字符，即：str1[idx1] ==str2[idx2]
                 *
                 * (idx1,idx2)位置的最长子序列长度 = 4种情况下的最大值
                 */

                // 1）
                int r1 = dpTable[idx1 - 1][idx2 - 1];
                // 2）
                int r2 = dpTable[idx1 - 1][idx2];
                // 3）
                int r3 = dpTable[idx1][idx2 - 1];

                // 先取上面3种情况下的最大值
                int maxVal = NumberUtils.max(r1, r2, r3);

                // 如果此时两个字符串的字符相同，那么就求第4种情况，然后再取最大值
                if (strChars1[idx1] == strChars2[idx2]) {
                    // 4）
                    int r4 = dpTable[idx1 - 1][idx2 - 1] + 1;
                    maxVal = Math.max(maxVal, r4);
                }

                // 取最大值
                dpTable[idx1][idx2] = maxVal;
            }
        }
        return dpTable[str1.length() - 1][str2.length() - 1];
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
        int times = 1000000;
        for (int time = 0; time < times; time++) {
            String str1 = RandomStringUtils.randomAlphabetic(6);
            String str2 = RandomStringUtils.randomAlphabetic(6);
            if (!already.contains(str1 + str2)) {
                int r1 = lcse(str1.toCharArray(), str2.toCharArray());
                int r2 = getLongestCommonSubsequenceLengthByDp(str1, str2);
                int r3 = getLongestCommonSubsequenceLengthByViolenceRecursion(str1, str2);
                if (!(r1 == r2 && r2 == r3)) {
                    System.out.println("Oops");
                }
                already.add(str1 + str2);
            }
        }
        System.out.println("finish!");
    }

}
