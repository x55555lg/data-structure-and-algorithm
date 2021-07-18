package com.lg.leetcode.other;

import java.util.Stack;

/**
 * @author Xulg
 * Description: leetcode_394
 * Created in 2021-05-31 16:22
 */
class DecodeString {
    /*
     *  Given an encoded string, return its decoded string.
     *  The encoding rule is: k[encoded_string], where the encoded_string inside the square brackets is
     * being repeated exactly k times. Note that k is guaranteed to be a positive integer.
     *  You may assume that the input string is always valid; No extra white spaces, square brackets are
     * well-formed, etc.
     *  Furthermore, you may assume that the original data does not contain any digits and that digits
     * are only for those repeat numbers, k. For example, there won't be input like 3a or 2[4].
     *
     * Example 1:
     * Input: s = "3[a]2[bc]"
     * Output: "aaabcbc"
     *
     * Example 2:
     * Input: s = "3[a2[c]]"
     * Output: "accaccacc"
     *
     * Example 3:
     * Input: s = "2[abc]3[cd]ef"
     * Output: "abcabccdcdcdef"
     *
     * Example 4:
     * Input: s = "abc3[cd]xyz"
     * Output: "abccdcdcdxyz"
     *
     * Constraints:
     * 1 <= s.length <= 30
     * s consists of lowercase English letters, digits, and square brackets '[]'.
     * s is guaranteed to be a valid input.
     * All the integers in s are in the range [1, 300].
     */

    public static String decodeString(String s) {
        return Solution.decodeString(s);
    }

    /**
     * // TODO 2021/5/31 BUG
     */
    private static class Solution {
        public static String decodeString(String str) {
            if (str == null || str.length() == 0) {
                return str;
            }
            StringBuilder sb = new StringBuilder();
            Stack<Integer> numberStack = new Stack<>();
            Stack<String> encodedStringStack = new Stack<>();
            char[] chars = str.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                char c = chars[i];
                if (c >= '1' && c <= '9') {
                    numberStack.push((int) c);
                } else if (c == '[') {
                    // 截取'['和']'之间的字符
                    int indexOf = str.indexOf(']', i + 1);
                    String encodedStr = str.substring(i + 1, indexOf);
                    encodedStringStack.push(encodedStr);
                    i = indexOf;
                } else if (c == ']') {
                    int number = numberStack.pop();
                    String encodedStr = encodedStringStack.pop();
                    for (int j = 0; j < number; j++) {
                        sb.append(encodedStr);
                    }
                    i++;
                } else {
                    sb.append(c);
                    i++;
                }
            }
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        System.out.println(Solution.decodeString("3[a]2[bc]"));
        System.out.println(Solution.decodeString("3[a]2[bc]"));
        System.out.println(Solution.decodeString("3[a]2[bc]"));
    }
}
