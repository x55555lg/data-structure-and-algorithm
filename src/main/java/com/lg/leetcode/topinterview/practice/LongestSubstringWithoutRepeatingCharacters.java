package com.lg.leetcode.topinterview.practice;

import cn.hutool.core.lang.Assert;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Xulg
 * Created in 2021-03-02 15:23
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
class LongestSubstringWithoutRepeatingCharacters {

    /*
     * Given a string s, find the length of the longest substring without repeating characters.
     *
     * Example 1:
     * Input: s = "abcabcbb"
     * Output: 3
     * Explanation: The answer is "abc", with the length of 3.
     * Example 2:
     * Input: s = "bbbbb"
     * Output: 1
     * Explanation: The answer is "b", with the length of 1.
     *
     * Example 3:
     * Input: s = "pwwkew"
     * Output: 3
     * Explanation: The answer is "wke", with the length of 3.
     * Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.
     *
     * Example 4:
     * Input: s = ""
     * Output: 0
     *
     * Constraints:
     *  0 <= s.length <= 5 * 10^4
     *  s consists of English letters, digits, symbols and spaces.
     */

    public static int lengthOfLongestSubstring(String s) {
        return DP2.lengthOfLongestSubstring(s);
    }

    /**
     * 时间超限
     */
    private static class BruteForce {

        public static int lengthOfLongestSubstring(String str) {
            if (str == null || str.length() == 0) {
                return 0;
            }
            HashMap<String, Boolean> cacheMap = new HashMap<>();
            for (int i = 0; i < str.length(); i++) {

            }
            return recurse(str.toCharArray(), 0);
        }

        /**
         * 以chars[curIdx]字符开头的最长子串是多少
         */
        private static int recurse(char[] chars, int curIdx) {
            if (curIdx == chars.length) {
                return 0;
            }
            int curMax = 1;
            for (int endIdx = curIdx + 1; endIdx < chars.length; endIdx++) {
                //char[] subStrChars = Arrays.copyOfRange(chars, curIdx, endIdx + 1);
                // 子串[curIdx, endIdx]上有没有重复字符
                if (isDuplicate(chars, curIdx, endIdx)) {
                    // i...后面的不需要试了，肯定有重复字符
                    break;
                }
                curMax = Math.max(curMax, endIdx - curIdx + 1);
                //System.out.println("[from, to]=[" + curIdx + ", " + i + "]   " + new String(subStrChars));
            }
            int nextMax = recurse(chars, curIdx + 1);
            return Math.max(curMax, nextMax);
        }

        private static boolean isDuplicate(char[] chars, int left, int right) {
            if (left == right) {
                return false;
            }
            HashSet<Character> characters = new HashSet<>();
            for (int i = left; i <= right; i++) {
                characters.add(chars[i]);
            }
            return characters.size() < (right - left + 1);
        }
    }

    /**
     * 内存超限
     * 使用了二维表
     */
    private static class DP {

        public static int lengthOfLongestSubstring(String str) {
            if (str == null || str.length() == 0) {
                return 0;
            }
            char[] chars = str.toCharArray();
            // table[row][column]表示在[row, column]范围上有没有字符重复
            // table[row][column] = 1表示在[row, column]范围上没有字符重复
            // table[row][column] = 0表示在[row, column]范围上存在字符重复
            int[][] table = new int[chars.length][chars.length];

            /*
             * str = "abcabcbb"时，table表如下：
             * X表示该位置无意义不可用
             *
             * 	    0	1	2	3	4	5	6	7
             *-----------------------------------
             * 0	1	1	1	0	0	0	0	0
             *
             * 1	X	1	1	1	0	0	0	0
             *
             * 2	X	X	1	1	1	0	0	0
             *
             * 3	X	X	X	1	1	1	0	0
             *
             * 4	X	X	X	X	1	1	0	0
             *
             * 5	X	X	X	X	X	1	1	0
             *
             * 6	X	X	X	X	X	X	1	0
             *
             * 7	X	X	X	X	X	X	X	1
             *-----------------------------------
             * (0, 1) = (0, 0)有没有重复 + (0, 0)上有没有chars[1]
             * (0, 2) = (0, 1)有没有重复 + (0, 1)上有没有chars[2]
             * (0, 3) = (0, 2)有没有重复 + (0, 2)上有没有chars[3]
             * (0, 4) = (0, 3)有没有重复 + (0, 3)上有没有chars[4]
             * (0, 5) = (0, 4)有没有重复 + (0, 4)上有没有chars[5]
             * (0, 6) = (0, 5)有没有重复 + (0, 5)上有没有chars[6]
             * (0, 7) = (0, 6)有没有重复 + (0, 6)上有没有chars[7]
             */

            // base case 单个字符的时候，肯定没有重复字符
            for (int i = 0; i < chars.length; i++) {
                table[i][i] = 1;
            }

            // 填表格的上半部分，下半部分无意义
            int max = Integer.MIN_VALUE;
            //HashMap<Integer, Integer> map = new HashMap<>();
            for (int startIdx = 0; startIdx < chars.length; startIdx++) {
                // 统计这行有几个1，因为对角线的值就是1，所有初始值为1
                int count = 1;
                for (int endIdx = startIdx + 1; endIdx < chars.length; endIdx++) {
                    if (table[startIdx][endIdx - 1] == 0) {
                        // 小范围上有重复的字符，后面不需要继续判断了
                        break;
                    } else {
                        // 判断[startIdx, endIdx-1]上有没有chars[endIdx]字符存在
                        boolean exist = isExist(chars, startIdx, endIdx - 1, chars[endIdx]);
                        table[startIdx][endIdx] = exist ? 0 : 1;
                        if (table[startIdx][endIdx] == 1) {
                            count++;
                        }
                    }
                }
                //map.put(startIdx, count);
                max = Math.max(max, count);
            }
            return max == Integer.MAX_VALUE ? 0 : max;
        }

        private static boolean isExist(char[] chars, int left, int right, int c) {
            for (int i = left; i <= right; i++) {
                if (chars[i] == c) {
                    return true;
                }
            }
            return false;
        }

    }

    /**
     * AC了，但还是慢
     * 使用了一维表格
     */
    private static class DP2 {

        public static int lengthOfLongestSubstring(String str) {
            if (str == null || str.length() == 0) {
                return 0;
            }
            char[] chars = str.toCharArray();

            // 0-0
            // 0-1
            // 0-2
            // 0-3
            // 0-4
            // 0-5
            // 0-6
            // 0-7
            // 1-1
            // 1-2
            // 1-3
            // 1-4
            // 1-5
            // 1-6
            // 1-7
            // ...
            // 7-7
            // 这个数组可以被重复利用
            int[] helper = new int[chars.length];

            int max = Integer.MIN_VALUE;
            for (int startIdx = 0; startIdx < chars.length; startIdx++) {
                // base case
                helper[startIdx] = 1;
                // 统计这行有几个1，因为对角线的值就是1，所有初始值为1
                int count = 1;
                for (int endIdx = startIdx + 1; endIdx < chars.length; endIdx++) {
                    if (helper[endIdx - 1] == 0) {
                        // 小范围上有重复的字符，后面不需要继续判断了
                        break;
                    } else {
                        // 判断[startIdx, endIdx-1]上有没有chars[endIdx]字符存在
                        boolean exist = isExist(chars, startIdx, endIdx - 1, chars[endIdx]);
                        helper[endIdx] = exist ? 0 : 1;
                        if (helper[endIdx] == 1) {
                            count++;
                        }
                    }
                }
                max = Math.max(max, count);
            }
            return max == Integer.MAX_VALUE ? 0 : max;
        }

        private static boolean isExist(char[] chars, int left, int right, int c) {
            for (int i = left; i <= right; i++) {
                if (chars[i] == c) {
                    return true;
                }
            }
            return false;
        }

    }

    public static void main(String[] args) {
        Assert.isTrue(BruteForce.lengthOfLongestSubstring("abcabcbb") == 3);
        Assert.isTrue(BruteForce.lengthOfLongestSubstring("bbbbb") == 1);
        Assert.isTrue(BruteForce.lengthOfLongestSubstring("pwwkew") == 3);
        Assert.isTrue(BruteForce.lengthOfLongestSubstring("") == 0);

        Assert.isTrue(DP.lengthOfLongestSubstring("abcabcbb") == 3);
        Assert.isTrue(DP.lengthOfLongestSubstring("bbbbb") == 1);
        Assert.isTrue(DP.lengthOfLongestSubstring("pwwkew") == 3);
        Assert.isTrue(DP.lengthOfLongestSubstring("") == 0);

        Assert.isTrue(DP2.lengthOfLongestSubstring("abcabcbb") == 3);
        Assert.isTrue(DP2.lengthOfLongestSubstring("bbbbb") == 1);
        Assert.isTrue(DP2.lengthOfLongestSubstring("pwwkew") == 3);
        Assert.isTrue(DP2.lengthOfLongestSubstring("") == 0);
    }

}
