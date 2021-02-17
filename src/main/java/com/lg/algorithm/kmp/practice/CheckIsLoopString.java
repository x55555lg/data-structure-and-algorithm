package com.lg.algorithm.kmp.practice;

/**
 * @author Xulg
 * Created in 2021-02-05 15:15
 */
class CheckIsLoopString {

    /*
     * str1 = "abc123" 它的旋转字符串有：
     *      "abc123"  "bc123a"  "c123ab"  "123abc"  "23abc1"  "3abc12"
     * 问str2是否是str1的其中一个旋转字符串？
     *----------------------------------------------------------------
     * 暴力解思路：
     *  枚举str1的所有旋转串，然后逐个和str2比较，如果有相等的则返回true
     *----------------------------------------------------------------
     * KMP解法思路：
     *      求解str1 = "abc123"，str2是否是str1的旋转串
     *      等价于
     *      求解str = "abc123abc123"，str2是否是str的子串
     *          因为str = "abc123abc123"串中包含了str1的所有选择串：
     *              "abc123"  "bc123a"  "c123ab"  "123abc"  "23abc1"  "3abc12"
     */

    public static boolean isLoop(String str1, String str2) {
        String str = str1 + str1;
        return indexOf(str, str2) != -1;
    }

    /**
     * KMP算法
     */
    private static int indexOf(String str, String match) {
        if (str == null || match == null || match.length() < 1 || str.length() < match.length()) {
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
                // 等价条件
                // if (matchIdx == 0)
                if (nextArray[matchIdx] == -1) {
                    assert matchIdx == 0 : "what the hell?";
                    strIdx++;
                } else {
                    // jump 加速
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
                // 等价写法
                // next[idx] = cn + 1;
                next[idx] = next[idx - 1] + 1;
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

}
