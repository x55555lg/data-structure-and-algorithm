package com.lg.leetcode.other.practice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * @author Xulg
 * Description: leetcode_639
 * Created in 2021-05-25 23:09
 */
class DecodeWaysII {

    /*
     *  A message containing letters from A-Z can be encoded into numbers using the following mapping:
     *      'A' -> "1"
     *      'B' -> "2"
     *      ...
     *      'Z' -> "26"
     *  To decode an encoded message, all the digits must be grouped then mapped back into letters using
     * the reverse of the mapping above (there may be multiple ways). For example, "11106" can be mapped into:
     *      "AAJF" with the grouping (1 1 10 6)
     *      "KJF" with the grouping (11 10 6)
     *  Note that the grouping (1 11 06) is invalid because "06" cannot be mapped into 'F' since "6" is different
     * from "06".
     *
     *  In addition to the mapping above, an encoded message may contain the '*' character, which can represent
     * any digit from '1' to '9' ('0' is excluded). For example, the encoded message "1*" may represent any of the
     * encoded messages "11", "12", "13", "14", "15", "16", "17", "18", or "19". Decoding "1*" is equivalent to
     * decoding any of the encoded messages it can represent.
     *
     *  Given a string s containing digits and the '*' character, return the number of ways to decode it.
     *
     *  Since the answer may be very large, return it modulo 10^9 + 7.
     *
     * Example 1:
     * Input: s = "*"
     * Output: 9
     * Explanation: The encoded message can represent any of the encoded messages "1", "2", "3", "4", "5", "6", "7", "8", or "9".
     * Each of these can be decoded to the strings "A", "B", "C", "D", "E", "F", "G", "H", and "I" respectively.
     * Hence, there are a total of 9 ways to decode "*".
     *
     * Example 2:
     * Input: s = "1*"
     * Output: 18
     * Explanation: The encoded message can represent any of the encoded messages "11", "12", "13", "14", "15", "16", "17", "18", or "19".
     * Each of these encoded messages have 2 ways to be decoded (e.g. "11" can be decoded to "AA" or "K").
     * Hence, there are a total of 9 * 2 = 18 ways to decode "1*".
     *
     * Example 3:
     * Input: s = "2*"
     * Output: 15
     * Explanation: The encoded message can represent any of the encoded messages "21", "22", "23", "24", "25", "26", "27", "28", or "29".
     * "21", "22", "23", "24", "25", and "26" have 2 ways of being decoded, but "27", "28", and "29" only have 1 way.
     * Hence, there are a total of (6 * 2) + (3 * 1) = 12 + 3 = 15 ways to decode "2*".
     *
     * Constraints:
     * 1 <= s.length <= 105
     * s[i] is a digit or '*'.
     */

    public static int numDecodings(String str) {
        return 0;
    }

    /**
     * // TODO 2021/5/30 error
     */
    private static class BruteForce {
        public static int numDecodings(String str) {
            if (str == null || str.length() == 0) {
                return 0;
            }
            return recurse(0, str.toCharArray());
        }

        private static int recurse(int curIdx, char[] str) {
            if (curIdx == str.length) {
                return 1;
            }
            char c = str[curIdx];
            int answer;
            if (c == '0') {
                answer = 0;
            } else if (c == '1') {
                // 一位数
                answer = recurse(curIdx + 1, str);
                // 两位数，这里有2种情况
                if (curIdx + 1 < str.length) {
                    // 下一位是*号
                    if (str[curIdx + 1] == '*') {
                        // 把*替换为[1, 9]
                        answer = answer + 9 * recurse(curIdx + 2, str);
                    } else {
                        // 下一位是数字
                        answer = answer + recurse(curIdx + 2, str);
                    }
                }
            } else if (c == '2') {
                // 一位数
                answer = recurse(curIdx + 1, str);
                // 两位数
                if (curIdx + 1 < str.length) {
                    // 下一位是*号
                    if (str[curIdx + 1] == '*') {
                        // 把*替换为[1, 6]
                        answer = answer + 6 * recurse(curIdx + 2, str);
                    }
                    // 下一位是[0, 6]范围
                    else if (str[curIdx + 1] >= '0' && str[curIdx + 1] <= '6') {
                        answer = answer + recurse(curIdx + 2, str);
                    }
                }
            } else if (c == '*') {
                // *号当作一位数用，*号可以表示1, 2, 3, 4, 5, 6, 7, 8, 9
                answer = 9 * recurse(curIdx + 1, str);
                // *号和下一位数当作两位数用
                if (curIdx + 1 < str.length) {
                    // *号位置的数可以表示1, 2, 3, 4, 5, 6, 7, 8, 9
                    if (str[curIdx + 1] == '*') {
                        // 第二位数是*
                        // * = 1
                        answer = answer + recurse(curIdx + 2, str);
                        // * = 2
                        answer = answer + recurse(curIdx + 2, str);
                        // * = 3
                        answer = answer + recurse(curIdx + 2, str);
                        // * = 4
                        answer = answer + recurse(curIdx + 2, str);
                        // * = 5
                        answer = answer + recurse(curIdx + 2, str);
                        // * = 6
                        answer = answer + recurse(curIdx + 2, str);
                    } else {
                        // * = 1
                        answer = answer + recurse(curIdx + 2, str);
                        // * = 2
                        answer = answer + recurse(curIdx + 2, str);
                    }
                }
            } else {
                // c >= '3' 只能是一位数
                answer = recurse(curIdx + 1, str);
            }
            return answer;
        }
    }

    private static class MemorySearch {
        public static int numDecodings(String str) {
            if (str == null || str.length() == 0) {
                return 0;
            }
            int[] table = new int[str.length() + 1];
            Arrays.fill(table, -1);
            table[str.length()] = 1;
            return recurse(0, str.toCharArray(), table);
        }

        private static int recurse(int curIdx, char[] str, int[] table) {
            if (table[curIdx] != -1) {
                return table[curIdx];
            }
            // base case
            if (curIdx == str.length) {
                return 1;
            }
            char c = str[curIdx];
            int answer;
            if (c == '0') {
                answer = 0;
            } else if (c == '1') {
                // 一位数
                answer = recurse(curIdx + 1, str, table);
                // 两位数，这里有2种情况
                if (curIdx + 1 < str.length) {
                    // 下一位是*号
                    if (str[curIdx + 1] == '*') {
                        // 把*替换为[1, 9]
                        for (char i = '1'; i <= '9'; i++) {
                            str[curIdx + 1] = i;
                            answer += recurse(curIdx + 2, str, table);
                            // 恢复
                            str[curIdx + 1] = c;
                        }
                    } else {
                        // 下一位是数字
                        answer = answer + recurse(curIdx + 2, str, table);
                    }
                }
            } else if (c == '2') {
                // 一位数
                answer = recurse(curIdx + 1, str, table);
                // 两位数
                if (curIdx + 1 < str.length) {
                    // 下一位是*号
                    if (str[curIdx + 1] == '*') {
                        // 把*替换为[1, 6]
                        for (char i = '1'; i <= '6'; i++) {
                            str[curIdx + 1] = i;
                            answer += recurse(curIdx + 2, str, table);
                            // 恢复
                            str[curIdx + 1] = c;
                        }
                    }
                    // 下一位是[0, 6]范围
                    else if (str[curIdx + 1] >= '0' && str[curIdx + 1] <= '6') {
                        answer = answer + recurse(curIdx + 2, str, table);
                    }
                }
            } else if (c == '*') {
                answer = 0;
                // 把*替换为[1, 9]
                for (char i = '1'; i <= '9'; i++) {
                    str[curIdx] = i;
                    answer += recurse(curIdx + 1, str, table);
                    // 恢复
                    str[curIdx] = c;
                }
            } else {
                // c >= '3' 只能是一位数
                answer = recurse(curIdx + 1, str, table);
            }
            table[curIdx] = answer;
            return answer;
        }
    }

    public static void main(String[] args) {
        System.out.println(BruteForce.numDecodings("*"));
        System.out.println(BruteForce.numDecodings("1*"));
        System.out.println(BruteForce.numDecodings("2*"));
        System.out.println(BruteForce.numDecodings("3*"));
        System.out.println(BruteForce.numDecodings("**"));

        System.out.println(MemorySearch.numDecodings("*"));
        System.out.println(MemorySearch.numDecodings("1*"));
        System.out.println(MemorySearch.numDecodings("2*"));
        System.out.println(MemorySearch.numDecodings("3*"));


        LocalDateTime small = LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonth(),
                LocalDate.now().getDayOfMonth(), 10, 0, 0, 0);

        LocalDateTime big = LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonth(),
                LocalDate.now().getDayOfMonth(), 12, 0, 0, 0);

        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonth(),
                LocalDate.now().getDayOfMonth(), 11, 0, 0, 0);

        System.out.println(small.isBefore(localDateTime));
        System.out.println(big.isAfter(localDateTime));
    }
}
