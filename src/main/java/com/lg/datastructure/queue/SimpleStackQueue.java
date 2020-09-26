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
    public void add(Item item) {
        while (!pop.isEmpty()) {
            push.push(pop.pop());
        }
        push.push(item);
    }

    /**
     * 获取并移除数据
     *
     * @return the item
     */
    public Item poll() {
        while (!push.empty()) {
            pop.push(push.pop());
        }
        return pop.pop();
    }

    /**
     * 获取数据
     *
     * @return the item
     */
    public Item peek() {
        while (!push.empty()) {
            pop.push(push.pop());
        }
        return pop.peek();
    }

    public static void main(String[] args) {
        SimpleStackQueue<Integer> queue = new SimpleStackQueue<>();
        queue.add(1);
        queue.add(2);
        System.out.println(queue.poll());
        queue.add(3);
        queue.add(4);
        queue.add(5);
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
    }
}
