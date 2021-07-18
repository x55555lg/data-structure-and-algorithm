package com.practice.stack;

import java.util.Stack;

/**
 * @author Xulg
 * Created in 2021-05-11 18:07
 */
class Calculator {

    /*
     * 需要使用到2个栈，一个存操作数，一个存操作符，遇到左括号则忽略；
     * 遇到右括号，则弹出操作符，弹出2个操作数，进行计算，将计算结果压入操作数栈。
     */

    public static int calc(String expression) {
        assert expression != null && expression.length() > 0;
        Stack<Character> operates = new Stack<>();
        Stack<Integer> values = new Stack<>();
        char[] chars = expression.toCharArray();
        for (char c : chars) {
            if (c == '(') {
                continue;
            }
            if (c == ')') {
                // 遇右括号，弹出一个操作符，2个操作数
                char symbol = operates.pop();
                int val1 = values.pop();
                int val2 = values.pop();
                // 计算
                int r;
                if (symbol == '+') {
                    r = val2 + val1;
                } else if (symbol == '-') {
                    r = val2 - val1;
                } else if (symbol == '*') {
                    r = val2 * val1;
                } else if (symbol == '/') {
                    r = val2 / val1;
                } else {
                    throw new UnsupportedOperationException("unknown symbol " + symbol);
                }
                // 保存结果
                values.push(r);
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                operates.push(c);
            } else {
                // 数字
                values.push(Integer.valueOf(String.valueOf(c)));
            }
        }
        return values.pop();
    }

    public static void main(String[] args) {
        String expression = "(1+((2-3)*(4*5)))";
        int calculate = calc(expression);
        System.out.println(calculate);
    }
}
