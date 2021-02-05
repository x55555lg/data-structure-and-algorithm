package com.lg.datastructure.stack.a1.practice;

import java.util.Stack;

/**
 * @author Xulg
 * Created in 2021-01-21 13:38
 */
class GetMinStack {

    /*
     * 实现一个特殊的栈，在基本的功能上，再实现返回栈中最小元素的功能，要求：
     *  1.pop，push，getMin操作的时间复杂度都是O(1)
     *  2.可以使用现有的栈来实现这种特殊的栈
     *
     * 实现思路：
     *      准备两个栈，data栈和min栈，data栈存正常压入的数据，min栈存最小值
     *      每压入一个数据，都需要比较min栈的栈顶和压入数据的大小，将小的那个压入
     */

    private final Stack<Integer> stack = new Stack<>();
    private final Stack<Integer> min = new Stack<>();

    public int pop() {
        if (stack.isEmpty()) {
            throw new IllegalStateException("Your stack is empty.");
        }
        min.pop();
        return stack.pop();
    }

    public void push(int val) {
        if (stack.isEmpty()) {
            stack.push(val);
            min.push(val);
        } else {
            min.push(Math.min(min.peek(), val));
            stack.push(val);
        }
    }

    /**
     * 获取当前栈中的最小值元素
     */
    public int getMin() {
        if (min.isEmpty()) {
            throw new RuntimeException("Your stack is empty.");
        }
        return min.peek();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public int size() {
        return stack.size();
    }

    public static void main(String[] args) {
        GetMinStack getMinStack = new GetMinStack();
        getMinStack.push(1);
        getMinStack.push(4);
        getMinStack.push(3);
        getMinStack.push(2);
        getMinStack.push(0);
        getMinStack.push(5);

        while (!getMinStack.isEmpty()) {
            System.out.println(getMinStack.getMin());
            System.out.println(getMinStack.pop());
        }
    }

}
