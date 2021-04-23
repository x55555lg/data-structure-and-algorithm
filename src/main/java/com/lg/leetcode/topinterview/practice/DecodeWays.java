package com.lg.leetcode.topinterview.practice;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Arrays;

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
        return BruteForce.numDecodings(s);
    }

    private static class BruteForce {

        public static int numDecodings(String str) {
            if (str == null || str.length() == 0) {
                return 0;
            }
            return recurse(str.toCharArray(), 0);
        }

        /**
         * str[0...i-1]已经转化完了，固定了
         * i之前的位置，如何转化已经做过决定了, 不用再关心
         * i... 有多少种转化的结果
         */
        private static int recurse(char[] chars, int curIdx) {
            if (curIdx == chars.length) {
                // 所有的字符都判断完了，表示这种组合是ok的，返回1
                return 1;
            }

            /*
             * 对于每个字符，有2种选择：
             *      第1种：当前字符单独看做1个数，转为字母
             *      第2种：当前字符和下一个字符一起变成2位数，转为字母
             *
             * 1.当前数等于0
             *      0无法转成字母
             * 2,当前数等于1
             *      当前数作为1位数
             *      当前数和下一个数变成2位数
             * 2,当前数等于2
             *      当前数作为1位数
             *      当前数和下一个数变成2位数，那么第2位数字只能是[0, 6]，其余组成的数字无法转为字母
             * 3.当前数大于等于3
             *      当前数只能是1位数
             */

            // 当前数
            char curNum = chars[curIdx];
            if (curNum == '0') {
                // 0没有对应的字母啊
                return 0;
            } else if (curNum == '1') {
                // 当前数组成1位数
                int r = recurse(chars, curIdx + 1);
                // 当前数和下一位数组成2位数
                if (curIdx + 1 < chars.length) {
                    r = r + recurse(chars, curIdx + 2);
                }
                return r;
            } else if (curNum == '2') {
                // 当前数组成1位数
                int r = recurse(chars, curIdx + 1);
                // 当前数和下一位数组成2位数
                if (curIdx + 1 < chars.length) {
                    // 下一位数只能是[0, 6]才能组成字母啊
                    if (chars[curIdx + 1] >= '0' && chars[curIdx + 1] <= '6') {
                        r = r + recurse(chars, curIdx + 2);
                    }
                }
                return r;
            } else {
                // curNum >= 3 当前数只能组成1位数
                // 首位是3,4,5,6,7,8,9的话只能是1位数
                return recurse(chars, curIdx + 1);
            }
        }

    }

    private static class MemorySearch {

        public static int numDecodings(String str) {
            if (str == null || str.length() == 0) {
                return 0;
            }
            int[] table = new int[str.length() + 1];
            Arrays.fill(table, -1);
            return recurse(str.toCharArray(), 0, table);
        }

        /**
         * str[0...i-1]已经转化完了，固定了
         * i之前的位置，如何转化已经做过决定了, 不用再关心
         * i... 有多少种转化的结果
         */
        private static int recurse(char[] chars, int curIdx, int[] table) {
            if (table[curIdx] != -1) {
                return table[curIdx];
            }

            if (curIdx == chars.length) {
                // 所有的字符都判断完了，表示这种组合是ok的，返回1
                table[curIdx] = 1;
                return 1;
            }

            /*
             * 对于每个字符，有2种选择：
             *      第1种：当前字符单独看做1个数，转为字母
             *      第2种：当前字符和下一个字符一起变成2位数，转为字母
             *
             * 1.当前数等于0
             *      0无法转成字母
             * 2,当前数等于1
             *      当前数作为1位数
             *      当前数和下一个数变成2位数
             * 2,当前数等于2
             *      当前数作为1位数
             *      当前数和下一个数变成2位数，那么第2位数字只能是[0, 6]，其余组成的数字无法转为字母
             * 3.当前数大于等于3
             *      当前数只能是1位数
             */

            // 当前数
            char curNum = chars[curIdx];
            if (curNum == '0') {
                // 0没有对应的字母啊
                table[curIdx] = 0;
                return 0;
            } else if (curNum == '1') {
                // 当前数组成1位数
                int r = recurse(chars, curIdx + 1, table);
                // 当前数和下一位数组成2位数
                if (curIdx + 1 < chars.length) {
                    r = r + recurse(chars, curIdx + 2, table);
                }
                table[curIdx] = r;
                return r;
            } else if (curNum == '2') {
                // 当前数组成1位数
                int r = recurse(chars, curIdx + 1, table);
                // 当前数和下一位数组成2位数
                if (curIdx + 1 < chars.length) {
                    // 下一位数只能是[0, 6]才能组成字母啊
                    if (chars[curIdx + 1] >= '0' && chars[curIdx + 1] <= '6') {
                        r = r + recurse(chars, curIdx + 2, table);
                    }
                }
                table[curIdx] = r;
                return r;
            } else {
                // curNum >= 3 当前数只能组成1位数
                // 首位是3,4,5,6,7,8,9的话只能是1位数
                int r = recurse(chars, curIdx + 1, table);
                table[curIdx] = r;
                return r;
            }
        }

    }

    private static class DP {

        public static int numDecodings(String str) {
            if (str == null || str.length() == 0) {
                return 0;
            }
            char[] chars = str.toCharArray();
            int[] table = new int[chars.length + 1];
            // base case
            table[chars.length] = 1;

            // 填表
            for (int curIdx = chars.length - 1; curIdx >= 0; curIdx--) {
                /*
                 * 对于每个字符，有2种选择：
                 *      第1种：当前字符单独看做1个数，转为字母
                 *      第2种：当前字符和下一个字符一起变成2位数，转为字母
                 *
                 * 1.当前数等于0
                 *      0无法转成字母
                 * 2,当前数等于1
                 *      当前数作为1位数
                 *      当前数和下一个数变成2位数
                 * 2,当前数等于2
                 *      当前数作为1位数
                 *      当前数和下一个数变成2位数，那么第2位数字只能是[0, 6]，其余组成的数字无法转为字母
                 * 3.当前数大于等于3
                 *      当前数只能是1位数
                 */

                // 当前数
                char curNum = chars[curIdx];

                int r;
                if (curNum == '0') {
                    // 0没有对应的字母啊
                    r = 0;
                } else if (curNum == '1') {
                    // 当前数组成1位数
                    r = table[curIdx + 1];
                    // 当前数和下一位数组成2位数
                    if (curIdx + 1 < chars.length) {
                        r = r + table[curIdx + 2];
                    }
                } else if (curNum == '2') {
                    // 当前数组成1位数
                    r = table[curIdx + 1];
                    // 当前数和下一位数组成2位数
                    if (curIdx + 1 < chars.length) {
                        // 下一位数只能是[0, 6]才能组成字母啊
                        if (chars[curIdx + 1] >= '0' && chars[curIdx + 1] <= '6') {
                            r = r + table[curIdx + 2];
                        }
                    }
                    table[curIdx] = r;
                } else {
                    // curNum >= 3 当前数只能组成1位数
                    // 首位是3,4,5,6,7,8,9的话只能是1位数
                    r = table[curIdx + 1];
                }
                table[curIdx] = r;
            }
            return table[0];
        }

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
            int count1 = BruteForce.numDecodings(numeric);
            int count2 = MemorySearch.numDecodings(numeric);
            int count3 = DP.numDecodings(numeric);
            if (!(count0 == count1 && count1 == count2 && count2 == count3)) {
                System.out.println("Oops!======numeric为" + numeric);
            }
        }
        System.out.println("finish!");
    }

}
