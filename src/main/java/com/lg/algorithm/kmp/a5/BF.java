package com.lg.algorithm.kmp.a5;

/**
 * @author Xulg
 * @since 2021-10-12 11:14
 */
public class BF {

    public static int indexOf(String str, String match) {
        if (str == null || match == null
                || str.length() == 0
                || str.length() < match.length()) {
            return -1;
        }
        char[] strChars = str.toCharArray(), matchChars = match.toCharArray();
        int strIdx = 0, matchIdx = 0;
        while (strIdx < strChars.length && matchIdx < matchChars.length) {
            if (strChars[strIdx] == matchChars[matchIdx]) {
                strIdx++;
                matchIdx++;
            } else {
                strIdx = strIdx - matchIdx + 1;
                matchIdx = 0;
            }
        }
        return matchIdx == matchChars.length ? strIdx - matchIdx : -1;
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
