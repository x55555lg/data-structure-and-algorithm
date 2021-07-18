package com.practice.stack;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Xulg
 * Created in 2021-05-11 9:49
 */
class StackImplByTwoQueues<E> {

    private Queue<E> queue = new LinkedList<>();
    private Queue<E> helper = new LinkedList<>();

    public void add(E e) {
        queue.add(e);
    }

    public E pop() {
        while (queue.size() > 1) {
            helper.add(queue.poll());
        }
        E e = queue.poll();
        // 交换2个队列，保证queue变量是一直存储数据的
        Queue<E> temp = queue;
        queue = helper;
        helper = temp;
        return e;
    }

    public E peek() {
        while (queue.size() > 1) {
            helper.add(queue.poll());
        }
        E e = queue.poll();
        helper.add(e);
        // 交换2个队列，保证queue变量是一直存储数据的
        Queue<E> temp = queue;
        queue = helper;
        helper = temp;
        return e;
    }
}
