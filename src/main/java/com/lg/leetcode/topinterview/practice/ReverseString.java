package com.lg.leetcode.topinterview.practice;

import cn.hutool.core.util.RandomUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Xulg
 * Created in 2021-02-24 13:22
 */
class ReverseString {

    /*
     * Write a function that reverses a string. The input string is given as an array of characters char[].
     * Do not allocate extra space for another array, you must do this by modifying the input array in-place
     * with O(1) extra memory.
     * You may assume all the characters consist of printable ascii characters.
     *
     * Example 1:
     * Input: ["h","e","l","l","o"]
     * Output: ["o","l","l","e","h"]
     *
     * Example 2:
     * Input: ["H","a","n","n","a","h"]
     * Output: ["h","a","n","n","a","H"]
     */

    public static void reverseString(char[] s) {
        if (s == null || s.length < 2) {
            return;
        }
        int left = 0, right = s.length - 1;
        while (left < right) {
            char temp = s[left];
            s[left] = s[right];
            s[right] = temp;
            left++;
            right--;
        }
    }

    public static void main(String[] args) {
        int times = 100_0000;
        for (int time = 0; time < times; time++) {
            String str = RandomUtil.randomString(10);
            String r = StringUtils.reverse(str);
            char[] chars = str.toCharArray();
            reverseString(chars);
            String r1 = new String(chars);
            if (!r.equals(r1)) {
                System.out.println("Oops");
            }
        }
        System.out.println("finished!");
    }
}
