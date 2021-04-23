package com.lg.leetcode.topinterview.practice;

import java.util.HashMap;

/**
 * @author Xulg
 * Created in 2021-03-02 14:17
 */
@SuppressWarnings("ForLoopReplaceableByForEach")
class FirstUniqueCharacterInString {

    /*
     * Given a string, find the first non-repeating character in it and return its index.
     * If it doesn't exist, return -1.
     *
     * Examples:
     * s = "leetcode"
     * return 0.
     *
     * s = "loveleetcode"
     * return 2.
     *
     * Note: You may assume the string contains only lowercase English letters.
     */

    public static int firstUniqChar(String s) {
        return -1;
    }

    /**
     * 暴力解法O(N^2)
     */
    private static class BruteForce {

        public static int firstUniqChar(String s) {
            char[] chars = s.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                boolean isRepeat = true;
                // [0, n)上是否出现过chars[i]字符
                for (int j = 0; j < chars.length; j++) {
                    if (i != j && chars[i] == chars[j]) {
                        isRepeat = false;
                        break;
                    }
                }
                if (isRepeat) {
                    return i;
                }
            }
            return -1;
        }

    }

    /**
     * O(N)
     */
    private static class Solution2 {

        public static int firstUniqChar(String s) {
            char[] chars = s.toCharArray();
            // 记录每个字符第一次出现的位置
            HashMap<Character, Integer> charFirstPositionMap = new HashMap<>(16);
            int[] letters = new int[26];
            for (int i = 0; i < chars.length; i++) {
                Character c = chars[i];
                if (!charFirstPositionMap.containsKey(c)) {
                    charFirstPositionMap.put(c, i);
                }
                letters[c - 'a']++;
            }
            // 找出第一个
            int firstUniqCharPosition = Integer.MAX_VALUE;
            for (int i = 0; i < letters.length; i++) {
                if (letters[i] == 1) {
                    Character character = (char) ('a' + i);
                    Integer position = charFirstPositionMap.get(character);
                    firstUniqCharPosition = Math.min(firstUniqCharPosition, position);
                }
            }
            return firstUniqCharPosition == Integer.MAX_VALUE ? -1 : firstUniqCharPosition;
        }

    }

    /**
     * O(N)，常数项更小
     */
    private static class Solution3 {

        public static int firstUniqChar(String s) {
            char[] chars = s.toCharArray();
            int[] letters = new int[26];
            for (int i = 0; i < chars.length; i++) {
                letters[chars[i] - 'a']++;
            }
            for (int i = 0; i < chars.length; i++) {
                // 看字符个数是否为1
                if (letters[chars[i] - 'a'] == 1) {
                    return i;
                }
            }
            return -1;
        }

    }

    public static void main(String[] args) {
        System.out.println(Solution2.firstUniqChar("leetcode"));
        System.out.println(Solution2.firstUniqChar("loveleetcode"));

        System.out.println(BruteForce.firstUniqChar("leetcode"));
        System.out.println(BruteForce.firstUniqChar("loveleetcode"));

        System.out.println(BruteForce.firstUniqChar("cc"));
        System.out.println(BruteForce.firstUniqChar("cc"));
    }

}
