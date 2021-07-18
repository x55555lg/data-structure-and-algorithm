package com.practice.kmp;

import cn.hutool.core.util.RandomUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Xulg
 * Created in 2021-05-08 15:28
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

    private static class BruteForce {
        public static boolean isLoopStr(String str1, String str2) {
            return getAllLoopStrs(str1).contains(str2);
        }

        private static List<String> getAllLoopStrs(String str) {
            List<String> list = new ArrayList<>();
            list.add(str);
            /*
             * a    b   c   1   2   3
             * 0    1   2   3   4   5
             *      i
             *  bc123a
             *
             * a    b   c   1   2   3
             * 0    1   2   3   4   5
             *          i
             *  c123ab
             */
            for (int idx = 1; idx < str.length(); idx++) {
                String loopStr = str.substring(idx) + str.substring(0, idx);
                list.add(loopStr);
            }
            return list;
        }
    }

    private static class CheckByKMP {

        public static boolean isLoopStr(String str1, String str2) {
            return KMP.indexOf(str1 + str1, str2) != -1;
        }

        private static class KMP {
            public static int indexOf(String str, String match) {
                if (str == null || match == null || str.length() < match.length()) {
                    return -1;
                }
                char[] strChars = str.toCharArray();
                char[] matchChars = match.toCharArray();
                int strIdx = 0, matchIdx = 0;
                int[] nextArray = getNextArray(matchChars);
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

            private static int[] getNextArray(char[] matchChars) {
                int[] next = new int[matchChars.length];
                next[0] = -1;
                next[1] = 0;
                int cn = next[1];
                for (int idx = 2; idx < matchChars.length; ) {
                    if (matchChars[idx - 1] == matchChars[cn]) {
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
        }
    }

    public static void main(String[] args) {
        int times = 1000000;
        Map<Boolean, Integer> countMap = new HashMap<>(3);
        countMap.put(true, 0);
        countMap.put(false, 0);
        for (int time = 0; time < times; time++) {
            String str1 = RandomUtil.randomString("abcd1234", 6);
            String str2 = RandomUtil.randomString("abcd1234", 6);
            boolean r0 = BruteForce.isLoopStr(str1, str2);
            boolean r1 = CheckByKMP.isLoopStr(str1, str2);
            if (r0 != r1) {
                System.out.println("fucking");
                return;
            } else {
                countMap.put(r0, countMap.get(r0) + 1);
            }
        }
        System.out.println("good job......");
    }
}
