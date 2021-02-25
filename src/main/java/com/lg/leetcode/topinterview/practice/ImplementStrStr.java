package com.lg.leetcode.topinterview.practice;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author Xulg
 * Created in 2021-02-25 11:31
 */
@SuppressWarnings({"unused", "AlibabaClassNamingShouldBeCamel"})
class ImplementStrStr {

    /*
     * Implement strStr().
     * Return the index of the first occurrence of needle in haystack, or -1 if needle is not part of haystack.
     *
     * Clarification:
     *  What should we return when needle is an empty string? This is a great question to ask during an interview.
     *  For the purpose of this problem, we will return 0 when needle is an empty string. This is consistent to C's strstr() and Java's indexOf().
     *
     * Example 1:
     * Input: haystack = "hello", needle = "ll"
     * Output: 2
     *
     * Example 2:
     * Input: haystack = "aaaaa", needle = "bba"
     * Output: -1
     *
     * Example 3:
     * Input: haystack = "", needle = ""
     * Output: 0
     *
     * Constraints:
     *  0 <= haystack.length, needle.length <= 5 * 104
     *  haystack and needle consist of only lower-case English characters.
     *
     *-----------------------------------------------------------------------------------------------
     * 实现indexOf(str, match)方法，如果str或match是空串，返回0
     */

    public static int strStr(String haystack, String needle) {
        return KMP.indexOf(haystack, needle);
    }

    private static class Test {
        public static int indexOf(String str, String match) {
            if (str == null || match == null) {
                return -1;
            }
            if (match.length() == 0) {
                return 0;
            }
            if (str.length() < match.length()) {
                return -1;
            }
            return str.indexOf(match);
        }
    }

    private static class BF {

        public static int indexOf(String str, String match) {
            if (str == null || match == null) {
                return -1;
            }
            if (match.length() == 0) {
                return 0;
            }
            if (str.length() < match.length()) {
                return -1;
            }
            char[] strChars = str.toCharArray();
            char[] matchChars = match.toCharArray();
            int strIdx = 0;
            int matchIdx = 0;
            while (strIdx < str.length() && matchIdx < match.length()) {
                if (strChars[strIdx] == matchChars[matchIdx]) {
                    strIdx++;
                    matchIdx++;
                } else {
                    // 回溯
                    strIdx = strIdx - matchIdx + 1;
                    matchIdx = 0;
                }
            }
            return matchIdx == match.length() ? strIdx - matchIdx : -1;
        }

    }

    private static class KMP {

        public static int indexOf(String str, String match) {
            if (str == null || match == null) {
                return -1;
            }
            if (match.length() == 0) {
                return 0;
            }
            if (str.length() < match.length()) {
                return -1;
            }
            int[] nextArray = getNextArray(match);
            char[] strChars = str.toCharArray();
            char[] matchChars = match.toCharArray();
            int strIdx = 0;
            int matchIdx = 0;
            while (strIdx < str.length() && matchIdx < match.length()) {
                if (strChars[strIdx] == matchChars[matchIdx]) {
                    strIdx++;
                    matchIdx++;
                } else {
                    if (nextArray[matchIdx] == -1) {
                        assert matchIdx == 0;
                        strIdx++;
                    } else {
                        // jump
                        matchIdx = nextArray[matchIdx];
                    }
                }
            }
            return matchIdx == matchChars.length ? strIdx - matchIdx : -1;
        }

        private static int[] getNextArray(String match) {
            char[] chars = match.toCharArray();
            if (chars.length == 1) {
                return new int[]{-1};
            }
            if (chars.length == 2) {
                return new int[]{-1, 0};
            }
            int[] nextArray = new int[chars.length];
            nextArray[0] = -1;
            nextArray[1] = 0;
            int idx = 2;
            int cn = nextArray[1];
            while (idx < chars.length) {
                if (chars[idx - 1] == chars[cn]) {
                    nextArray[idx] = cn + 1;
                    cn = nextArray[idx];
                    idx++;
                } else {
                    if (cn > 0) {
                        // jump
                        cn = nextArray[cn];
                    } else {
                        nextArray[idx++] = 0;
                    }
                }
            }
            return nextArray;
        }
    }

    /**
     * leetcode上的写法
     */
    private static class Unknown {

        public static int indexOf(String str, String match) {
            for (int i = 0; ; i++) {
                for (int j = 0; ; j++) {
                    if (j == match.length()) {
                        return i;
                    }
                    if (i + j == str.length()) {
                        return -1;
                    }
                    if (match.charAt(j) != str.charAt(i + j)) {
                        break;
                    }
                }
            }
        }

    }

    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<>();
        HashSet<Integer> set = new HashSet<>();
        int possibilities = 5;
        int strSize = 20;
        int matchSize = 5;
        int testTimes = 5000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            String str = getRandomString(possibilities, strSize);
            String match = getRandomString(possibilities, matchSize);
            int r1 = Test.indexOf(str, match);
            int r2 = KMP.indexOf(str, match);
            int r3 = BF.indexOf(str, match);
            int r4 = Unknown.indexOf(str, match);
            if (map.containsKey(r1)) {
                map.put(r1, map.get(r1) + 1);
            } else {
                map.put(r1, 1);
            }
            if (!(r1 == r2 && r2 == r3 && r3 == r4)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }

    /* 对数器 */

    static String getRandomString(int possibilities, int size) {
        char[] ans = new char[(int) (Math.random() * size) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
        }
        return String.valueOf(ans);
    }
}
