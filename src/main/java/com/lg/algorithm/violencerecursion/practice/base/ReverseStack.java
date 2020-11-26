package com.lg.algorithm.violencerecursion.practice.base;

import java.util.Stack;

/**
 * @author Xulg
 * Created in 2020-11-23 16:43
 */
class ReverseStack {

    /*
     * 题目：
     * 1.给你一个栈，请你逆序这个栈，不能申请额外的数据结构，只能使用递归函数。 如何实现?
     * 本质：递归栈是可以帮助保存一些信息的
     */

    /**
     * 反转栈
     * 不能申请额外的数据结构，只能使用递归函数
     */
    public static void reverse(Stack<Integer> stack) {
        if (stack.isEmpty()) {
            return;
        }
        Integer lastOne = removeStackButton(stack);
        reverse(stack);
        stack.push(lastOne);
    }

    /**
     * 移除栈底的元素并返回，不使用额外的数据结构
     * 初始状态：
     * stack = [1, 2, 3];
     * 执行之后：
     * stack = [1, 2];
     * 返回3
     *
     * @return 栈底的元素
     */
    private static Integer removeStackButton(Stack<Integer> stack) {
        Integer result = stack.pop();
        if (stack.isEmpty()) {
            return result;
        } else {
            // 获取栈底元素
            Integer lastOne = removeStackButton(stack);
            stack.push(result);
            return lastOne;
        }
    }

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.add(1);
        stack.add(2);
        stack.add(3);
        stack.add(4);
        stack.add(5);
        reverse(stack);

        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }

}
