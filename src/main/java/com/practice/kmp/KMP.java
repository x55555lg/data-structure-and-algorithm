package com.practice.kmp;

/**
 * @author Xulg
 * Created in 2021-05-08 14:39
 */
public class KMP {

    public static int indexOf(String str, String match) {
        if (str == null || str.length() == 0 || str.length() < match.length()) {
            return -1;
        }
        char[] strChars = str.toCharArray();
        char[] matchChars = match.toCharArray();
        int[] next = getNextArray(matchChars);
        int strIdx = 0, matchIdx = 0;
        while (strIdx < strChars.length && matchIdx < matchChars.length) {
            if (strChars[strIdx] == matchChars[matchIdx]) {
                strIdx++;
                matchIdx++;
            } else {
                if (next[matchIdx] == -1) {
                    assert matchIdx == 0;
                    strIdx++;
                } else {
                    // jump
                    matchIdx = next[matchIdx];
                }
            }
        }
        return matchIdx == matchChars.length ? strIdx - matchIdx : -1;
    }

    private static int[] getNextArray(char[] matchChars) {
        assert matchChars != null;
        if (matchChars.length == 0) {
            return new int[0];
        }
        if (matchChars.length == 1) {
            return new int[]{-1};
        }
        if (matchChars.length == 2) {
            return new int[]{-1, 0};
        }
        int[] next = new int[matchChars.length];
        // 0位置规定为-1
        next[0] = -1;
        // 1位置规定为0
        next[1] = 0;
        // cn代表，cn位置的字符，是当前和i-1位置比较的字符
        int cn = next[1];
        for (int idx = 2; idx < matchChars.length; ) {
            if (matchChars[idx - 1] == matchChars[cn]) {
                next[idx] = cn + 1;
                cn = next[idx];
                idx++;
            } else {
                if (cn > 0) {
                    // jump
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
