package com.lg.leetcode.topinterview.practice1;

/**
 * @author Xulg
 * Created in 2021-02-25 13:56
 */
class ValidPalindrome {

    /*
     * Given a string s, determine if it is a palindrome, considering only alphanumeric
     * characters and ignoring cases.
     *
     * Example 1:
     * Input: s = "A man, a plan, a canal: Panama"
     * Output: true
     * Explanation: "amanaplanacanalpanama" is a palindrome.
     *
     * Example 2:
     * Input: s = "race a car"
     * Output: false
     * Explanation: "raceacar" is not a palindrome.
     *
     * Constraints:
     *  1 <= s.length <= 2 * 105
     *  s consists only of printable ASCII characters.
     */

    public static boolean isPalindrome(String s) {
        return check(s);
    }

    private static boolean check(String str) {
        return false;
    }

    public static void main(String[] args) {
    }
}
