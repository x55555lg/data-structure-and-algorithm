package com.lg.leetcode.topinterview.practice;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xulg
 * Created in 2021-03-09 17:07
 */
class HappyNumber {

    /*
     * Write an algorithm to determine if a number n is happy.
     * A happy number is a number defined by the following process:
     *      1.Starting with any positive integer, replace the number by
     *  the sum of the squares of its digits.
     *      2.Repeat the process until the number equals 1 (where it will
     *  stay), or it loops endlessly in a cycle which does not include 1.
     *      3.Those numbers for which this process ends in 1 are happy.
     *      4.Return true if n is a happy number, and false if not.
     *
     * Example 1:
     * Input: n = 19
     * Output: true
     * Explanation:
     *  1^2 + 9^2 = 82
     *  8^2 + 2^2 = 68
     *  6^2 + 8^2 = 100
     *  1^2 + 0^2 + 0^2 = 1
     *
     * Example 2:
     * Input: n = 2
     * Output: false
     *
     * Constraints:
     *  1 <= n <= 2^31 - 1
     */

    public static boolean isHappy(int n) {
        return BruteForce.isHappy(n);
    }

    /**
     * 暴力解
     */
    private static class BruteForce {

        public static boolean isHappy(int num) {
            return recurse(getAllDigit(num));
        }

        private static boolean recurse(List<Integer> digits) {
            int sumOfSquares = sumOfSquares(digits);
            if (sumOfSquares == 1) {
                // base case
                return true;
            }
            // base case
            // TODO: 2021/3/9 ...
            List<Integer> allDigits = getAllDigit(sumOfSquares);
            return recurse(allDigits);
        }

        private static int getCountOfOne(List<Integer> digits) {
            return 0;
        }

        private static int sumOfSquares(List<Integer> digits) {
            int sum = 0;
            for (int digit : digits) {
                sum = sum + (int) Math.pow(digit, 2);
            }
            return sum;
        }

        private static List<Integer> getAllDigit(int num) {
            String str = String.valueOf(num);
            List<Integer> list = new ArrayList<>();
            for (char c : str.toCharArray()) {
                if (c != '0') {
                    list.add(Integer.valueOf(String.valueOf(c)));
                }
            }
            return list;
        }

    }

    public static void main(String[] args) {
        System.out.println(BruteForce.isHappy(19));
        System.out.println(BruteForce.isHappy(2));
    }

}
