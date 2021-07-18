package com.practice.stack;

import java.util.Stack;

/**
 * @author Xulg
 * Created in 2021-05-11 18:07
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

    private static class MinStack {
        private final Stack<Integer> data = new Stack<>();
        private final Stack<Integer> min = new Stack<>();

        public void push(Integer num) {
            data.push(num);
            // 压入最小值
            if (min.isEmpty()) {
                min.push(num);
            } else {
                int oldMinNum = min.peek();
                min.push(Math.min(oldMinNum, num));
            }
        }

        public Integer pop() {
            if (data.isEmpty()) {
                return null;
            }
            Integer num = data.pop();
            min.pop();
            return num;
        }

        public Integer getMin() {
            return min.peek();
        }

        public int size() {
            return data.size();
        }

        public boolean isEmpty() {
            return data.isEmpty();
        }
    }

    public static void main(String[] args) {
        MinStack getMinStack = new MinStack();
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
