package com.lg.datastructure.stack;

import com.lg.datastructure.queue.SimpleLinkedListQueue;

import javax.annotation.Nonnull;
import java.util.Iterator;

/**
 * 基于简单的双向队列实现的栈
 *
 * @author Xulg
 * Created in 2020-09-21 17:15
 */
public class SimpleQueueStack<Item> implements SimpleStack<Item> {

    /**
     * 自己实现的双向队列
     */
    private final SimpleLinkedListQueue<Item> queue = new SimpleLinkedListQueue<>();

    @Override
    public void push(Item item) {
        queue.enqueueFirst(item);
    }

    @Override
    public Item pop() {
        return queue.dequeueFirst();
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Nonnull
    @Override
    public Iterator<Item> iterator() {
        return queue.iterator();
    }

    public static void main(String[] args) {
        SimpleQueueStack<Integer> stack = new SimpleQueueStack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);

        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
    }
}
