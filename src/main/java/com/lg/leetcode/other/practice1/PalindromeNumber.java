package com.lg.leetcode.other.practice1;

import cn.hutool.core.util.RandomUtil;

/**
 * @author Xulg
 * @since 2021-08-14 20:24
 * Description: leetcode_9
 */
class PalindromeNumber {
    /*
     * Given an integer x, return true if x is palindrome integer.
     * An integer is a palindrome when it reads the same backward as forward.
     * For example, 121 is palindrome while 123 is not.
     *
     * Example 1:
     * Input: x = 121
     * Output: true
     *
     * Example 2:
     * Input: x = -121
     * Output: false
     * Explanation: From left to right, it reads -121. From right to left, it becomes 121-. Therefore it is not a palindrome.
     *
     * Example 3:
     * Input: x = 10
     * Output: false
     * Explanation: Reads 01 from right to left. Therefore it is not a palindrome.
     *
     * Example 4:
     * Input: x = -101
     * Output: false
     *
     * Constraints:
     * -231 <= x <= 231 - 1
     *
     * Follow up:
     *  Could you solve it without converting the integer to a string?
     */

    public static boolean isPalindrome(int x) {
        return BestSolution.isPalindrome(x);
    }

    private static class BruteForce {
        public static boolean isPalindrome(int num) {
            if (num < 0) {
                return false;
            }
            String str = String.valueOf(num);
            char[] chars = str.toCharArray();
            char[] reversedChars = new char[chars.length];
            for (int i = 0; i < reversedChars.length; i++) {
                reversedChars[i] = chars[chars.length - 1 - i];
            }
            return str.equals(new String(reversedChars));
        }
    }

    private static class MoreBetteSolution {
        public static boolean isPalindrome(int num) {
            if (num < 0) {
                return false;
            }
            char[] chars = String.valueOf(num).toCharArray();
            int left = 0, right = chars.length - 1;
            while (left < right) {
                if (chars[left] != chars[right]) {
                    return false;
                }
                left++;
                right--;
            }
            return true;
        }
    }

    private static class BestSolution {
        public static boolean isPalindrome(int x) {
            if (x < 0) {
                return false;
            }
            // ans为反转之后的数
            int ans = 0;
            int num = x;
            while (num > 0) {
                ans = ans * 10 + num % 10;
                num = num / 10;
            }
            // 原始数和反转后的数是否相等
            return ans == x;
        }
    }

    public static void main(String[] args) {
        int times = 1000000;
        for (int time = 0; time < times; time++) {
            int x = RandomUtil.randomInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
            boolean r0 = BruteForce.isPalindrome(x);
            boolean r1 = MoreBetteSolution.isPalindrome(x);
            boolean r2 = BestSolution.isPalindrome(x);
            if (!(r0 == r1 && r1 == r2)) {
                System.out.println("fucking fucked");
            }
        }
        System.out.println("success");
    }
}
