package com.lg.leetcode.other.practice;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xulg
 * Description: leetcode_131
 * Created in 2021-05-28 15:47
 */
class PalindromePartitioning {
    /*
     *  Given a string s, partition s such that every substring of the partition is
     * a palindrome. Return all possible palindrome partitioning of s.
     *  A palindrome string is a string that reads the same backward as forward.
     *
     * Example 1:
     * Input: s = "aab"
     * Output: [["a","a","b"],["aa","b"]]
     *
     * Example 2:
     * Input: s = "a"
     * Output: [["a"]]
     *
     * Constraints:
     * 1 <= s.length <= 16
     * s contains only lowercase English letters.
     */

    public static List<List<String>> partition(String s) {
        if (s == null || s.length() == 0) {
            return new ArrayList<>(0);
        }
        return null;
    }

    private static class BruteForce {

    }
}
