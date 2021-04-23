package com.lg.algorithm.kmp.a1;

/**
 * @author Xulg
 * Created in 2021-04-12 10:54
 */
class KMP {

    public static int indexOf(String str, String match) {
        if (str == null || match == null || str.length() == 0 || str.length() < match.length()) {
            return -1;
        }
        char[] strChars = str.toCharArray();
        char[] matchChars = match.toCharArray();
        int[] nextArray = getNextArray(match);
        int strIdx = 0;
        int matchIdx = 0;
        while (strIdx < strChars.length && matchIdx < matchChars.length) {
            if (strChars[strIdx] == matchChars[matchIdx]) {
                strIdx++;
                matchIdx++;
            } else {
                if (nextArray[matchIdx] == -1) {
                    assert matchIdx == 0;
                    strIdx++;
                } else {
                    matchIdx = nextArray[matchIdx];
                }
            }
        }
        return matchIdx == matchChars.length ? strIdx - matchIdx : -1;
    }

    private static int[] getNextArray(String match) {
        if (match == null) {
            return null;
        }
        if (match.length() == 0) {
            return new int[0];
        }
        if (match.length() == 1) {
            return new int[]{-1};
        }
        if (match.length() == 2) {
            return new int[]{-1, 0};
        }
        char[] chars = match.toCharArray();
        int[] next = new int[chars.length];
        // 规定
        next[0] = -1;
        // 规定
        next[1] = 0;

        int idx = 2;
        int cn = next[1];
        while (idx < chars.length) {
            if (chars[idx - 1] == chars[cn]) {
                next[idx] = cn + 1;
                cn = next[idx];
                idx++;
            } else {
                if (cn > 0) {
                    // jump
                    cn = next[cn];
                } else {
                    // cn == -1 || cn == 0;
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
