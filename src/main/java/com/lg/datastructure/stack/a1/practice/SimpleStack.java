package com.lg.datastructure.stack.a1.practice;

import cn.hutool.core.util.RandomUtil;

/**
 * 自己实现的简单栈
 *
 * @author Xulg
 * Created in 2020-09-16 12:34
 */
interface SimpleStack<Item> {

    /**
     * 入栈
     *
     * @param item the item
     */
    void push(Item item);

    /**
     * 出栈
     *
     * @return the item
     */
    Item pop();

    /**
     * 查看栈顶的元素
     *
     * @return the item
     */
    Item peek();

    /**
     * 栈是否空的
     *
     * @return is the stack empty
     */
    boolean isEmpty();

    /**
     * 栈的大小
     *
     * @return the size of the stack
     */
    int size();

    static void main(String[] args) {
        SimpleStack<Integer> stack1 = new StackImplByArray<>(10);
        SimpleStack<Integer> stack2 = new StackImplByNode<>();
        SimpleStack<Integer> stack3 = new StackImplByTwoQueues<>();
        int times = 1000000;
        for (int time = 0; time < times; time++) {
            for (int i = 0; i < 10; i++) {
                int val = RandomUtil.randomInt(0, 100);
                stack1.push(val);
                stack2.push(val);
                stack3.push(val);
            }
            for (int i = 0; i < 10; i++) {
                int r1 = stack1.pop();
                int r2 = stack2.pop();
                int r3 = stack3.pop();
                if (!(r1 == r2 && r2 == r3)) {
                    System.out.println("Oops");
                }
            }
        }
        System.out.println("finish!");
    }
}
