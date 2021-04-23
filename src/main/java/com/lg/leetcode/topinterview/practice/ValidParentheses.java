package com.lg.leetcode.topinterview.practice;

import cn.hutool.core.lang.Assert;

import java.util.Stack;

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
        if (s == null || s.length() == 0) {
            return true;
        }
        if (s.length() % 2 != 0) {
            return false;
        }
        Stack<Character> stack = new Stack<>();
        char[] chars = s.toCharArray();
        // 左括号入栈，遇到右括号出栈，如果栈为空直接返回false，因为没有匹配的左括号
        // 遍历结束后，如果栈中还有元素，返回false，栈为空表示都匹配成功了，返回true
        for (char c : chars) {
            if ('(' == c || '[' == c || '{' == c) {
                stack.add(c);
            } else {
                // 遇到),],}了，需要出栈
                if (stack.isEmpty()) {
                    // 栈为空，直接返回false，不能匹配到(,[,{
                    return false;
                }
                // 弹出(,[,{，进行匹配
                Character character = stack.pop();
                if (')' == c) {
                    if (!character.equals('(')) {
                        return false;
                    }
                } else if (']' == c) {
                    if (!character.equals('[')) {
                        return false;
                    }
                } else if ('}' == c) {
                    if (!character.equals('{')) {
                        return false;
                    }
                } else {
                    // unknown char
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        Assert.isTrue(isValid("()"));
        Assert.isTrue(isValid("()[]{}"));
        Assert.isFalse(isValid("(]"));
        Assert.isFalse(isValid("([)]"));
        Assert.isTrue(isValid("{[]}"));
    }
}
