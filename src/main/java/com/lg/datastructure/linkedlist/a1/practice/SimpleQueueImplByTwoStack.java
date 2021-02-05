package com.lg.datastructure.linkedlist.a1.practice;

import java.util.Stack;

/**
 * 使用2个栈实现队列
 *
 * @author Xulg
 * Created in 2021-01-21 15:09
 */
class SimpleQueueImplByTwoStack<Item> {

    private final Stack<Item> push = new Stack<>();
    private final Stack<Item> pop = new Stack<>();

    public void add(Item item) {
        while (!pop.isEmpty()) {
            push.push(pop.pop());
        }
        push.add(item);
    }

    public Item poll() {
        while (!push.isEmpty()) {
            pop.push(push.pop());
        }
        return pop.pop();
    }

    public Item peek() {
        while (!push.isEmpty()) {
            pop.push(push.pop());
        }
        return pop.peek();
    }

    public boolean isEmpty() {
        return push.isEmpty() && pop.isEmpty();
    }

    public int size() {
        return push.size() + pop.size();
    }

    public static void main(String[] args) {
        SimpleQueueImplByTwoStack<Integer> queue = new SimpleQueueImplByTwoStack<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.add(4);
        queue.add(5);

        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
    }
}
