package com.lg.algorithm.kmp.practice;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Xulg
 * Created in 2021-02-14 11:59
 */
public class KMP {

    /*
     * 字符串匹配算法BF
     */

    /**
     * BF算法的字符串匹配
     *
     * @param str   the str
     * @param match the match str
     * @return the index of the match int the str
     */
    public static int indexOf(String str, String match) {
        if (str == null || match == null || str.length() <= 0 || str.length() < match.length()) {
            return -1;
        }
        int[] nextArray = getNextArray(match);
        char[] strChars = str.toCharArray();
        char[] matchChars = match.toCharArray();
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
        int[] next = new int[chars.length];
        next[0] = -1;
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
                    // cn == -1 || cn ==0
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
        Map<Integer, Integer> map = new HashMap<>();
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
            if (map.containsKey(r1)) {
                map.put(r1, map.get(r1) + 1);
            } else {
                map.put(r1, 1);
            }
            if (r != r1) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }

}
