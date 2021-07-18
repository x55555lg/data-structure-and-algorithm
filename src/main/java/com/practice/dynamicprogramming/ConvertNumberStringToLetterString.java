package com.practice.dynamicprogramming;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author Xulg
 * Description: TODO 请加入类描述信息
 * Created in 2021-05-19 9:43
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
        public static int getConvertCount(String str) {
            if (str == null || str.length() == 0) {
                return 0;
            }
            return recurse(0, str.toCharArray());
        }

        /**
         * [0, curIdx)已经都转换完了，[curIdx, N)是我要转换的
         * 返回有多少种转换方式
         *
         * @param curIdx the current idx
         * @param chars  the str chars
         * @return the count
         */
        private static int recurse(int curIdx, char[] chars) {
            // base case
            if (curIdx == chars.length) {
                // 数字都被转换完了
                return 1;
            }
            int answer = 0;
            if (chars[curIdx] == '0') {
                // 以0开头的话，没有可以组合出来的字母啊
                return answer;
            }
            if (chars[curIdx] == '1') {
                // 1开头，有2种选择，要么是1个数组成字母，要么是2个数组成字母
                answer += recurse(curIdx + 1, chars);
                if (curIdx + 1 < chars.length) {
                    answer += recurse(curIdx + 2, chars);
                }
            } else if (chars[curIdx] == '2') {
                // 2开头，有2种选择，1个数组成字母，2个数组成字母，但是第二位数只能是(0~6)
                answer += recurse(curIdx + 1, chars);
                if (curIdx + 1 < chars.length
                        && chars[curIdx + 1] >= '0' && chars[curIdx + 1] <= '6') {
                    answer += recurse(curIdx + 2, chars);
                }
            } else {
                // 3以上的数开头只能是1位数
                answer += recurse(curIdx + 1, chars);
            }
            return answer;
        }
    }

    private static class Compare {
        public static int getConvertCount(String str) {
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

    private static class DP {
        public static int getConvertCount(String str) {
            if (str == null || str.length() == 0) {
                return 0;
            }
            char[] chars = str.toCharArray();
            int[] table = new int[chars.length + 1];
            // base case
            table[chars.length] = 1;

            // fill table
            for (int curIdx = chars.length - 1; curIdx >= 0; curIdx--) {
                int answer = 0;
                if (chars[curIdx] == '0') {
                    table[curIdx] = 0;
                } else if (chars[curIdx] == '1') {
                    answer += table[curIdx + 1];
                    if (curIdx + 1 < chars.length) {
                        answer += table[curIdx + 2];
                    }
                } else if (chars[curIdx] == '2') {
                    answer += table[curIdx + 1];
                    if (curIdx + 1 < chars.length && (chars[curIdx + 1] >= '0' && chars[curIdx + 1] <= '6')) {
                        answer += table[curIdx + 2];
                    }
                } else {
                    answer += table[curIdx + 1];
                }
                table[curIdx] = answer;
            }
            return table[0];
        }
    }

    /* ****************************************************************************************************************/

    public static void main(String[] args) {
        int times = 10000000;
        for (int time = 0; time < times; time++) {
            // 随机生成1~8位长的数字字符串
            String numeric = RandomStringUtils.randomNumeric(1, 9);
            int count1 = Compare.getConvertCount(numeric);
            int count2 = BruteForce.getConvertCount(numeric);
            int count3 = DP.getConvertCount(numeric);
            if (!(count1 == count2 && count2 == count3)) {
                System.out.println("Oops!======numeric为" + numeric);
            }
        }
        System.out.println("finish!");
    }
}
