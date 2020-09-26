package com.lg.datastructure.stack;

import java.util.Stack;

/**
 * @author Xulg
 * Created in 2020-09-24 14:55
 */
public class GetMinStack {

    /*
     * 实现一个特殊的栈，在基本的功能上，再实现返回栈中最小元素的功能，要求：
     *  1.pop，push，getMin操作的时间复杂度都是O(1)
     *  2.可以使用现有的栈来实现这种特殊的栈
     *
     * 实现思路：
     *      准备两个栈，data栈和min栈，data栈存正常压入的数据，min栈存最小值
     *      每压入一个数据，都需要比较min栈的栈顶和压入数据的大小，将小的那个压入
     */

    private final Stack<Integer> data = new Stack<>();

    // 存放最小值的栈
    private final Stack<Integer> min = new Stack<>();

    public Integer pop() {
        if (data.isEmpty()) {
            throw new RuntimeException("Your stack is empty.");
        }
        min.pop();
        return data.pop();
    }

    public void push(Integer item) {
        data.push(item);
        if (min.isEmpty()) {
            min.push(item);
        } else {
            // 比较最小值栈的栈顶和输入元素的大小
            Integer oldMin = this.getMin();
            if (oldMin > item) {
                // 新输入的元素更小
                min.push(item);
            } else {
                min.push(oldMin);
            }
        }
    }

    /**
     * 获取当前栈中的最小值元素
     */
    public Integer getMin() {
        if (min.isEmpty()) {
            throw new RuntimeException("Your stack is empty.");
        } else {
            return min.peek();
        }
    }

    public static void main(String[] args) {
        GetMinStack stack = new GetMinStack();
        stack.push(3);
        stack.push(1);
        stack.push(2);
        stack.push(4);

        System.out.println(stack.pop());
        System.out.println(stack.getMin());

        System.out.println(stack.pop());
        System.out.println(stack.getMin());

        System.out.println(stack.pop());
        System.out.println(stack.getMin());

        System.out.println(stack.pop());
        try {
            System.out.println(stack.getMin());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
