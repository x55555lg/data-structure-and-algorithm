package com.lg.leetcode.other;

/**
 * @author Xulg
 * Description: leetcode_392
 * Created in 2021-06-01 9:14
 */
@SuppressWarnings("ConstantConditions")
class IsSubsequence {
    /*
     *  Given two strings s and t, return true if s is a subsequence of t, or false otherwise.
     * A subsequence of a string is a new string that is formed from the original string by
     * deleting some (can be none) of the characters without disturbing the relative positions
     * of the remaining characters.
     * (i.e., "ace" is a subsequence of "abcde" while "aec" is not).
     *
     * Example 1:
     * Input: s = "abc", t = "ahbgdc"
     * Output: true
     *
     * Example 2:
     * Input: s = "axc", t = "ahbgdc"
     * Output: false
     *
     *
     * Constraints:
     * 0 <= s.length <= 100
     * 0 <= t.length <= 104
     * s and t consist only of lowercase English letters.
     *------------------------------------------------------------------------------------------------------------------
     * s是否是t的子序列
     */

    public static boolean isSubsequence(String s, String t) {
        return false;
    }

    private static class BruteForce {
        public static boolean isSubsequence(String s, String t) {
            if (s == null || t == null || s.length() > t.length()) {
                return false;
            }
            if (s.length() == 0) {
                return true;
            }
            return recurse(s.length() - 1, t.length() - 1, s.toCharArray(), t.toCharArray());
        }

        private static boolean recurse(int sIdx, int tIdx, char[] s, char[] t) {
            if (sIdx == 0 && tIdx == 0) {
                // 最后一个字符是否相等
                return s[sIdx] == t[tIdx];
            }
            if (sIdx == 0 && tIdx != 0) {
                // 子序列以s[sIdx]结尾
                boolean r1 = s[0] == t[tIdx];
                // 子序列不以s[sIdx]结尾
                boolean r2 = recurse(0, tIdx - 1, s, t);
                return r1 || r2;
            }
            if (sIdx != 0 && tIdx == 0) {
                // t已经走完了，s还有剩余，s肯定不是t的子序列
                return false;
            }
            // 子序列以s[sIdx]结尾
            boolean r1 = (s[sIdx] == t[tIdx]) && recurse(sIdx - 1, tIdx - 1, s, t);
            // 子序列不以s[sIdx]结尾
            boolean r2 = recurse(sIdx, tIdx - 1, s, t);
            return r1 || r2;
        }
    }

    private static class MemorySearch {
        public static boolean isSubsequence(String s, String t) {
            if (s == null || t == null || s.length() > t.length()) {
                return false;
            }
            if (s.length() == 0) {
                return true;
            }
            int[][] table = new int[s.length()][t.length()];
            for (int r = 0; r < s.length(); r++) {
                for (int c = 0; c < t.length(); c++) {
                    table[r][c] = -1;
                }
            }
            return recurse(s.length() - 1, t.length() - 1, s.toCharArray(), t.toCharArray(), table);
        }

        private static boolean recurse(int sIdx, int tIdx, char[] s, char[] t, int[][] table) {
            if (table[sIdx][tIdx] != -1) {
                return table[sIdx][tIdx] == 1;
            }
            if (sIdx == 0 && tIdx == 0) {
                // 最后一个字符是否相等
                boolean r = s[sIdx] == t[tIdx];
                table[0][0] = r ? 1 : 0;
                return r;
            }
            if (sIdx == 0 && tIdx != 0) {
                // 子序列以s[sIdx]结尾
                boolean r1 = s[0] == t[tIdx];
                // 子序列不以s[sIdx]结尾
                boolean r2 = recurse(0, tIdx - 1, s, t, table);
                boolean r = r1 || r2;
                table[0][tIdx] = r ? 1 : 0;
                return r;
            }
            if (sIdx != 0 && tIdx == 0) {
                // t已经走完了，s还有剩余，s肯定不是t的子序列
                table[sIdx][0] = 0;
                return false;
            }
            // 子序列以s[sIdx]结尾
            boolean r1 = (s[sIdx] == t[tIdx]) && recurse(sIdx - 1, tIdx - 1, s, t, table);
            // 子序列不以s[sIdx]结尾
            boolean r2 = recurse(sIdx, tIdx - 1, s, t, table);
            boolean r = r1 || r2;
            table[sIdx][tIdx] = r ? 1 : 0;
            return r;
        }
    }

    private static class DP {
        public static boolean isSubsequence(String s, String t) {
            if (s == null || t == null || s.length() > t.length()) {
                return false;
            }
            if (s.length() == 0) {
                return true;
            }
            char[] sChars = s.toCharArray(), tChars = t.toCharArray();
            int[][] table = new int[sChars.length][tChars.length];
            // base case
            table[0][0] = sChars[0] == tChars[0] ? 1 : 0;
            // s是第一个字符了
            for (int tIdx = 1; tIdx < tChars.length; tIdx++) {
                // 子序列以s[sIdx]结尾
                boolean r1 = sChars[0] == tChars[tIdx];
                // 子序列不以s[sIdx]结尾
                boolean r2 = table[0][tIdx - 1] == 1;
                table[0][tIdx] = r1 || r2 ? 1 : 0;
            }
            // t已经走完了，s还有剩余，s肯定不是t的子序列
            for (int sIdx = 1; sIdx < sChars.length; sIdx++) {
                table[sIdx][0] = 0;
            }
            for (int sIdx = 1; sIdx < sChars.length; sIdx++) {
                for (int tIdx = 1; tIdx < tChars.length; tIdx++) {
                    // 子序列以s[sIdx]结尾
                    boolean r1 = (sChars[sIdx] == tChars[tIdx]) && table[sIdx - 1][tIdx - 1] == 1;
                    // 子序列不以s[sIdx]结尾
                    boolean r2 = table[sIdx][tIdx - 1] == 1;
                    table[sIdx][tIdx] = r1 || r2 ? 1 : 0;
                }
            }
            return table[sChars.length - 1][tChars.length - 1] == 1;
        }
    }

    private static class Best {
        public static boolean isSubsequence(String s, String t) {
            if (s == null || t == null || s.length() > t.length()) {
                return false;
            }
            if (s.length() == 0) {
                return true;
            }
            int prev = 0;
            for (int sIdx = 0; sIdx < s.length(); sIdx++) {
                char c = s.charAt(sIdx);
                prev = t.indexOf(c, prev);
                if (prev == -1) {
                    // 字符都没有，s肯定不是t的子序列
                    return false;
                }
                // 下一个开始搜索的位置
                prev++;
            }
            return true;
        }
    }

    public static void main(String[] args) {
        System.out.println(BruteForce.isSubsequence("axc", "ahbgdc"));
        System.out.println(BruteForce.isSubsequence("abc", "ahbgdc"));
    }
}
