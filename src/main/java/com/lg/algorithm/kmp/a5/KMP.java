package com.lg.algorithm.kmp.a5;

/**
 * @author Xulg
 * @since 2021-10-12 11:14
 */
public class KMP {

    public static int indexOf(String str, String match) {
        if (str == null || match == null
                || str.length() == 0
                || str.length() < match.length()) {
            return -1;
        }
        int[] nextArr = getNextArray(match);
        int strIdx = 0, matchIdx = 0;
        char[] strChars = str.toCharArray();
        char[] matchChars = match.toCharArray();
        while (strIdx < strChars.length && matchIdx < matchChars.length) {
            if (strChars[strIdx] == matchChars[matchIdx]) {
                strIdx++;
                matchIdx++;
            } else {
                if (nextArr[matchIdx] == -1) {
                    assert matchIdx == 0;
                    strIdx++;
                } else {
                    matchIdx = nextArr[matchIdx];
                }
            }
        }
        return matchIdx == matchChars.length ? strIdx - matchIdx : -1;
    }

    private static int[] getNextArray(String match) {
        char[] chars = match.toCharArray();
        if (chars.length == 0) {
            return new int[0];
        }
        if (chars.length == 1) {
            return new int[]{-1};
        }
        if (chars.length == 2) {
            return new int[]{-1, 0};
        }
        int[] next = new int[chars.length];
        next[0] = -1;
        next[1] = 0;
        int cn = next[1];
        for (int idx = 2; idx < chars.length; ) {
            if (chars[idx - 1] == chars[cn]) {
                next[idx] = cn + 1;
                cn = next[idx];
                idx++;
            } else {
                if (cn > 0) {
                    cn = next[cn];
                } else {
                    next[idx++] = 0;
                }
            }
        }
        return next;
    }

    /* 对数器 */

    static String getRandomString(int possibilities, int size) {
        char[] ans = new char[(int) (Math.random() * size) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
        }
        return String.valueOf(ans);
    }

    public static void main(String[] args) {
        int possibilities = 5;
        int strSize = 20;
        int matchSize = 5;
        int testTimes = 5000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            String str = getRandomString(possibilities, strSize);
            String match = getRandomString(possibilities, matchSize);
            int r = str.indexOf(match);
            int r1 = indexOf(str, match);
            if (r != r1) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }
}
