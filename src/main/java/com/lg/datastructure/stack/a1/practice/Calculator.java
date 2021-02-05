package com.lg.datastructure.stack.a1.practice;

import java.util.Stack;

/**
 * 使用栈实现一个加减乘除的计算器
 *
 * @author Xulg
 * Created in 2021-01-21 13:55
 */
class Calculator {

    /*
     * 需要使用到2个栈，一个存操作数，一个存操作符，遇到左括号则忽略；
     * 遇到右括号，则弹出操作符，弹出2个操作数，进行计算，将计算结果压入操作数栈。
     */

    @SuppressWarnings({"StatementWithEmptyBody", "ForLoopReplaceableByForEach"})
    public static int calc(String expression) {
        if (expression == null) {
            throw new IllegalArgumentException("表达式不能为空");
        }
        if (!expression.startsWith("(") && !expression.endsWith(")")) {
            expression = "(" + expression + ")";
        }

        // 数值栈
        Stack<Integer> valueStack = new Stack<>();
        // 符号栈
        Stack<String> symbolStack = new Stack<>();
        char[] chars = expression.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            String c = String.valueOf(chars[i]);
            if ("(".equals(c)) {
                // ignore
            } else if (")".equals(c)) {
                // 遇到右括号，弹1个操作符，2个数值
                String symbol = symbolStack.pop();
                int val1 = valueStack.pop();
                // 表达式中靠前的数
                int val2 = valueStack.pop();
                if ("+".equals(symbol)) {
                    valueStack.push(val2 + val1);
                } else if ("-".equals(symbol)) {
                    valueStack.push(val2 - val1);
                } else if ("*".equals(symbol)) {
                    valueStack.push(val2 * val1);
                } else if ("/".equals(symbol)) {
                    valueStack.push(val2 / val1);
                } else {
                    throw new IllegalStateException("不支持的操作符" + symbol);
                }
            } else {
                // 操作符或者数值入自己对应的栈
                switch (c) {
                    case "+":
                    case "/":
                    case "-":
                    case "*":
                        symbolStack.push(c);
                        break;
                    default:
                        // 是数值
                        valueStack.push(Integer.valueOf(c));
                        break;
                }
            }
        }
        // 数值栈中，最后一个数据就是最终的计算结果
        return valueStack.pop();
    }

    public static void main(String[] args) {
        String expression = "(1+((2+3)*(4*5)))";
        int r = calc(expression);
        System.out.println(r);
        System.out.println(calc("1+2"));
        System.out.println(calc("1-2)"));
    }

}
