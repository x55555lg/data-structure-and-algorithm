package com.lg.datastructure.stack;

import java.util.Stack;

/**
 * @author Xulg
 * Created in 2020-09-24 14:55
 */
class GetMinStack2 {

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
        Integer data = this.data.pop();
        if (data.equals(getMin())) {
            // 弹出的值就是当前的最小值，把最小值也弹出
            min.pop();
        }
        return data;
    }

    public void push(Integer item) {
        data.push(item);
        if (min.isEmpty()) {
            min.push(item);
        } else {
            // 新值比之前的最小值更小(或相等)就存入，否则不操作
            if (item <= getMin()) {
                min.push(item);
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
        GetMinStack2 stack = new GetMinStack2();
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
