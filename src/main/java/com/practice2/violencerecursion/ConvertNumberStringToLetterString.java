package com.practice2.violencerecursion;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author Xulg
 * @since 2021-11-01 14:25
 */
class ConvertNumberStringToLetterString {

    /*
     *Facebook面试原题
     * 规定1和A对应、2和B对应、3和C对应...11和K对应，那么一个数字字符串比如"111"就可以转化为:
     *      "AAA"、"KA"、"AK"
     * 给定一个只有数字字符组成的字符串str，返回有多少种转化结果？
     *------------------------------------------------------------------------------------------------------------------
     */

    private static class BruteForce {
        public static int calcConvertCount(String str) {
            if (str == null || str.length() == 0) {
                return 0;
            }
            return recurse(0, str.toCharArray());
        }

        private static int recurse(int curIdx, char[] chars) {
            if (curIdx == chars.length) {
                // 字符串转换完了，是1种对的结果
                return 1;
            }
            char currentChar = chars[curIdx];
            if (currentChar == '0') {
                // 0开头没有对应字母
                return 0;
            } else if (currentChar == '1') {
                int r = recurse(curIdx + 1, chars);
                if (curIdx + 1 < chars.length) {
                    r += recurse(curIdx + 2, chars);
                }
                return r;
            } else if (currentChar == '2') {
                int r = recurse(curIdx + 1, chars);
                if (curIdx + 1 < chars.length
                        && chars[curIdx + 1] >= '0' && chars[curIdx + 1] <= '6') {
                    r += recurse(curIdx + 2, chars);
                }
                return r;
            } else {
                // 3 ~ 9
                return recurse(curIdx + 1, chars);
            }
        }
    }

    private static class DP {
        public static int calcConvertCount(String str) {
            if (str == null || str.length() == 0) {
                return 0;
            }
            char[] chars = str.toCharArray();
            int[] table = new int[str.length() + 1];
            // base case
            table[chars.length] = 1;
            for (int curIdx = chars.length - 1; curIdx >= 0; curIdx--) {
                char currentChar = chars[curIdx];
                if (currentChar == '0') {
                    // 0开头没有对应字母
                    table[curIdx] = 0;
                } else if (currentChar == '1') {
                    int r = table[curIdx + 1];
                    if (curIdx + 1 < chars.length) {
                        r += table[curIdx + 2];
                    }
                    table[curIdx] = r;
                } else if (currentChar == '2') {
                    int r = table[curIdx + 1];
                    if (curIdx + 1 < chars.length
                            && chars[curIdx + 1] >= '0' && chars[curIdx + 1] <= '6') {
                        r += table[curIdx + 2];
                    }
                    table[curIdx] = r;
                } else {
                    // 3 ~ 9
                    table[curIdx] = table[curIdx + 1];
                }
            }
            return table[0];
        }
    }

    /* ****************************************************************************************************************/

    private static class Compare {

        /* 对数器方法 */

        public static int number(String str) {
            if (str == null || str.length() == 0) {
                return 0;
            }
            return process(str.toCharArray(), 0);
        }

        /**
         * str[0...i-1]已经转化完了，固定了
         * i之前的位置，如何转化已经做过决定了, 不用再关心
         * i... 有多少种转化的结果
         */
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
        int times = 10000000;
        for (int time = 0; time < times; time++) {
            // 随机生成1~8位长的数字字符串
            String numeric = RandomStringUtils.randomNumeric(5, 9);
            int count1 = BruteForce.calcConvertCount(numeric);
            int count2 = Compare.number(numeric);
            int count3 = DP.calcConvertCount(numeric);
            if (!(count1 == count2 && count2 == count3)) {
                System.out.println("Oops!======numeric为" + numeric);
            }
        }
        System.out.println("finish!");
    }

}
