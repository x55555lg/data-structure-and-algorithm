package com.lg.leetcode.topinterview.practice1;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author Xulg
 * Created in 2021-03-03 14:52
 */
@SuppressWarnings({"AlibabaUndefineMagicConstant", "AlibabaClassNamingShouldBeCamel"})
class DecodeWays {

    /*
     * A message containing letters from A-Z can be encoded into numbers using the following mapping:
     *  'A' -> "1"
     *  'B' -> "2"
     *  ...
     *  'Z' -> "26"
     *
     * To decode an encoded message, all the digits must be grouped then mapped back into letters
     * using the reverse of the mapping above (there may be multiple ways).
     *
     * For example, "11106" can be mapped into:
     *  "AAJF" with the grouping (1 1 10 6)
     *  "KJF" with the grouping (11 10 6)
     * Note that the grouping (1 11 06) is invalid because "06" cannot be mapped into 'F' since "6" is different from "06".
     *
     * Given a string s containing only digits, return the number of ways to decode it.
     * The answer is guaranteed to fit in a 32-bit integer.
     *
     * Example 1:
     * Input: s = "12"
     * Output: 2
     * Explanation: "12" could be decoded as "AB" (1 2) or "L" (12).
     *
     * Example 2:
     * Input: s = "226"
     * Output: 3
     * Explanation: "226" could be decoded as "BZ" (2 26), "VF" (22 6), or "BBF" (2 2 6).
     *
     * Example 3:
     * Input: s = "0"
     * Output: 0
     * Explanation: There is no character that is mapped to a number starting with 0.
     * The only valid mappings with 0 are 'J' -> "10" and 'T' -> "20", neither of which start with 0.
     * Hence, there are no valid ways to decode this since all digits need to be mapped.
     *
     * Example 4:
     * Input: s = "06"
     * Output: 0
     * Explanation: "06" cannot be mapped to "F" because of the leading zero ("6" is different from "06").
     *
     * Constraints:
     * 1 <= s.length <= 100
     * s contains only digits and may contain leading zero(s).
     *----------------------------------------------------------------------------------------------------
     * 规定1和A对应、2和B对应、3和C对应...11和K对应，那么一个数字字符串比如"111"就可以转化为:
     *      "AAA"、"KA"、"AK"
     * 给定一个只有数字字符组成的字符串str，返回有多少种转化结果？
     */

    public static int numDecodings(String s) {
        return 0;
    }

    /* ****************************************************************************************************************/

    /* 对数器方法 */

    @SuppressWarnings("all")
    private static class CompareMethod {

        public static int number(String str) {
            if (str == null || str.length() == 0) {
                return 0;
            }
            return process(str.toCharArray(), 0);
        }

        private static int process(char[] str, int i) {
            // base case
            if (i == str.length) {
                return 1;
            }
            if (str[i] == '0') {
                return 0;
            }
            if (str[i] == '1') {
                int res = process(str, i + 1);
                if (i + 1 < str.length) {
                    res += process(str, i + 2);
                }
                return res;
            }
            if (str[i] == '2') {
                int res = process(str, i + 1);
                if (i + 1 < str.length && (str[i + 1] >= '0' && str[i + 1] <= '6')) {
                    // (i和i+1)作为单独的部分，后续有多少种方法
                    res += process(str, i + 2);
                }
                return res;
            }
            return process(str, i + 1);
        }

    }

    /* ****************************************************************************************************************/

    public static void main(String[] args) {
        System.out.println(Math.pow(8, 10));
        int times = 10000000;
        for (int time = 0; time < times; time++) {
            // 随机生成1~8位长的数字字符串
            String numeric = RandomStringUtils.randomNumeric(1, 9);
            int count0 = CompareMethod.number(numeric);
            int count1 = 0;
            int count2 = 0;
            int count3 = 0;
            if (!(count0 == count1 && count1 == count2 && count2 == count3)) {
                System.out.println("Oops!======numeric为" + numeric);
            }
        }
        System.out.println("finish!");
    }

}
