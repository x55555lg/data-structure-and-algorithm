package com.lg.leetcode.topinterview.practice1;

import cn.hutool.core.lang.Assert;

/**
 * @author Xulg
 * Created in 2021-03-02 11:02
 */
class ValidParentheses {

    /*
     * Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
     * An input string is valid if:
     *  Open brackets must be closed by the same type of brackets.
     *  Open brackets must be closed in the correct order.
     *
     * Example 1:
     * Input: s = "()"
     * Output: true
     *
     * Example 2:
     * Input: s = "()[]{}"
     * Output: true
     *
     * Example 3:
     * Input: s = "(]"
     * Output: false
     *
     * Example 4:
     * Input: s = "([)]"
     * Output: false
     *
     * Example 5:
     * Input: s = "{[]}"
     * Output: true
     *
     *
     * Constraints:
     *  1 <= s.length <= 104
     *  s consists of parentheses only '()[]{}'.
     */

    public static boolean isValid(String s) {
        return false;
    }

    public static void main(String[] args) {
        Assert.isTrue(isValid("()"));
        Assert.isTrue(isValid("()[]{}"));
        Assert.isFalse(isValid("(]"));
        Assert.isFalse(isValid("([)]"));
        Assert.isTrue(isValid("{[]}"));
    }
}
