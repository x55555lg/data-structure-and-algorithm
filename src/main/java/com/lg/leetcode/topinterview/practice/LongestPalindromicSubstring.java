package com.lg.leetcode.topinterview.practice;

/**
 * @author Xulg
 * Created in 2021-03-05 15:10
 */
class LongestPalindromicSubstring {

    /*
     * Given a string s, return the longest palindromic substring in s.
     *
     * Example 1:
     * Input: s = "babad"
     * Output: "bab"
     * Note: "aba" is also a valid answer.
     *
     * Example 2:
     * Input: s = "cbbd"
     * Output: "bb"
     *
     * Example 3:
     * Input: s = "a"
     * Output: "a"
     *
     * Example 4:
     * Input: s = "ac"
     * Output: "a"
     *
     *
     * Constraints:
     *  1 <= s.length <= 1000
     *  s consist of only digits and English letters (lower-case and/or upper-case),
     */

    public static String longestPalindrome(String s) {
        return null;
    }

    /**
     * 暴力枚举
     */
    private static class BruteForce {

        public static String longestPalindrome(String str) {
            return recurse(str.toCharArray(), 0);
        }

        private static String recurse(char[] chars, int curIdx) {
            if (curIdx == chars.length) {
                return null;
            }

            // [curIdx, n]上的最长回文子串
            // 单个字符本身就是回文串
            int maxIdx = curIdx;
            for (int idx = curIdx + 1; idx < chars.length; idx++) {
                if (isPalindrome(chars, curIdx, idx)) {
                    maxIdx = Math.max(maxIdx, idx);
                }
            }
            String currentLongest = new String(chars, curIdx, maxIdx - curIdx + 1);

            // [curIdx+1, n]上的最长回文子串
            String nextLongest = recurse(chars, curIdx + 1);
            if (nextLongest == null) {
                return currentLongest;
            } else {
                // 当前最长回文子串和后续最长回文子串，返回长的那个
                if (currentLongest.length() >= nextLongest.length()) {
                    return currentLongest;
                } else {
                    return nextLongest;
                }
            }
        }

        private static boolean isPalindrome(char[] chars, int left, int right) {
            if (left == right) {
                return true;
            }
            int low = left, high = right;
            while (low < high) {
                if (chars[low] != chars[high]) {
                    return false;
                }
                low++;
                high--;
            }
            return true;
        }

    }

    /**
     * 暴力递归
     */
    private static class BruteForce2 {

        public static String longestPalindrome(String str) {
            // [row, column]是否是回文串 0不是，1是，-1未知
            int[][] cache = new int[str.length()][str.length()];
            for (int i = 0; i < str.length(); i++) {
                // 对角线位置都是回文串 [0,0] [1,1] [2,2] [3,3]...
                cache[i][i] = 1;
            }
            return recurse(str.toCharArray(), 0, cache);
        }

        private static String recurse(char[] chars, int curIdx, int[][] cache) {
            if (curIdx == chars.length) {
                return "";
            }
            // 单个字符本身就是回文串
            int maxIdx = curIdx;
            for (int idx = curIdx + 1; idx < chars.length; idx++) {
                // [curIdx, idx]
                if (isPalindrome(chars, curIdx, idx, cache)) {
                    maxIdx = Math.max(maxIdx, idx);
                }
            }
            // 当前位置开始的最长回文串
            String currentLongest = new String(chars, curIdx, maxIdx - curIdx + 1);
            // 下1个位置开始的最长回文串
            String nextLongest = recurse(chars, curIdx + 1, cache);
            // 返回最长的那个
            if (currentLongest.length() >= nextLongest.length()) {
                return currentLongest;
            } else {
                return nextLongest;
            }
        }

        private static String recurse(char[] chars, int startIdx, int endIdx) {
            if (startIdx >= chars.length) {
                return null;
            }
            if (startIdx == endIdx) {
                // 一个字符一定是回文串
                return new String(chars, startIdx, 1);
            }

            return null;
        }

        // left < right
        private static boolean isPalindrome(char[] chars, int left, int right, int[][] cache) {
            // 看[left, right-1]上是否是回文串
            int flag = cache[left][right - 1];
            if (flag == 1) {
                if (chars[right] == chars[left]) {

                }
            } else {

            }

            if (flag != -1) {
                return false;
            } else {
                int low = left, high = right;
                while (low < high) {
                    if (chars[low] != chars[high]) {
                        return false;
                    }
                    low++;
                    high--;
                }
                return true;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(BruteForce.longestPalindrome("babad"));
    }

}
