package com.lg.datastructure.stack;

import java.util.Stack;

/**
 * 使用栈实现一个加减乘除的计算器
 *
 * @author Xulg
 * Created in 2020-09-16 9:14
 */
class Calculator {

    /*
     * 思路：
     *  需要使用到2个栈，一个存操作数，一个存操作符，遇到左括号则忽略；
     *  遇到右括号，则弹出操作符，弹出2个操作数，进行计算，将计算结果压入操作数栈。
     */

    public static void main(String[] args) {
        String expression = "(1+((2+3)*(4*5)))";
        double calculate = calculate(expression);
        System.out.println(calculate);
    }

    private static Double calculate(String expression) {
        // 存放操作符的栈，操作符栈
        Stack<String> operateStack = new Stack<>();
        // 存放操作数的栈，操作数栈
        Stack<Double> valueStack = new Stack<>();

        // 遍历整个表达式的字符
        for (char c : expression.toCharArray()) {
            String symbol = String.valueOf(c);
            switch (symbol) {
                // 如果是左括号则直接忽略
                case "(":
                    break;
                // 如果是操作符则将操作符压入操作符栈
                case "+":
                case "-":
                case "*":
                case "/":
                    operateStack.push(symbol);
                    break;
                // 如果是右括号则进行计算
                case ")":
                    // 弹出操作符和操作数，将计算结果压入栈
                    String operate = operateStack.pop();
                    Double value = valueStack.pop();
                    if ("+".equals(operate)) {
                        value = valueStack.pop() + value;
                    } else if ("-".equals(operate)) {
                        value = valueStack.pop() - value;
                    } else if ("*".equals(operate)) {
                        value = valueStack.pop() * value;
                    } else if ("/".equals(operate)) {
                        value = valueStack.pop() / value;
                    }
                    valueStack.push(value);
                    break;
                // 既不是操作符也不是括号，那么就是数字咯，压入操作数栈
                default:
                    valueStack.push(Double.valueOf(symbol));
                    break;
            }
        }

        // 操作数栈中，最后一个数据就是最终的计算结果了
        Double calcResult = valueStack.pop();

        System.out.println(expression + "=" + calcResult);
        return calcResult;
    }
}
