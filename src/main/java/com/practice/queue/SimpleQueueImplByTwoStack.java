package com.practice.queue;

import java.util.Stack;

/**
 * 使用2个栈实现1个队列功能(FIFO)
 *
 * @author Xulg
 * Created in 2021-05-11 9:31
 */
class SimpleQueueImplByTwoStack<E> {

    private final Stack<E> push = new Stack<>();
    private final Stack<E> pop = new Stack<>();

    public void add(E e) {
        while (!pop.isEmpty()) {
            push.add(pop.pop());
        }
        push.add(e);
    }

    public E poll() {
        if (push.isEmpty() && pop.isEmpty()) {
            return null;
        }
        while (!push.isEmpty()) {
            pop.add(push.pop());
        }
        return pop.pop();
    }
}
