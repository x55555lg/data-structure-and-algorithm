package com.lg.datastructure.queue;

import java.util.Stack;

/**
 * 基于栈实现单向队列
 *
 * @author Xulg
 * Created in 2020-09-21 17:20
 */
public class SimpleStackQueue<Item> {

    private final Stack<Item> push = new Stack<>();
    private final Stack<Item> pop = new Stack<>();

    /*
     * 原理：
     *      push stack
     *      pop  stack
     */

    /**
     * 插入数据
     *
     * @param item the item
     */
    public void push(Item item) {
        while (!pop.isEmpty()) {
            push.push(pop.pop());
        }
        push.push(item);
    }

    /**
     * 获取数据
     *
     * @return the item
     */
    public Item pop() {
        while (!push.empty()) {
            pop.push(push.pop());
        }
        return pop.pop();
    }

    public static void main(String[] args) {
        SimpleStackQueue<Integer> queue = new SimpleStackQueue<>();
        queue.push(1);
        queue.push(2);
        System.out.println(queue.pop());
        queue.push(3);
        queue.push(4);
        queue.push(5);
        System.out.println(queue.pop());
        System.out.println(queue.pop());
        System.out.println(queue.pop());
        System.out.println(queue.pop());
    }
}
