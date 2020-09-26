package com.lg.datastructure.queue;

import javax.annotation.Nonnull;
import java.util.Iterator;

/**
 * 基于数组实现的队列
 *
 * @author Xulg
 * Created in 2020-09-16 14:50
 */
class ArrayQueue<Item> implements SimpleQueue<Item> {

    private final Item[] table;

    /**
     * 队列的头指针
     */
    private int headIdx;

    /**
     * 队列的尾指针
     */
    private int tailIdx;

    /**
     * 队列的大小
     */
    private int size;

    /**
     * 最大容量值
     */
    private final int limit;

    public ArrayQueue(int initialCapacity) {
        //noinspection unchecked
        table = (Item[]) new Object[initialCapacity];
        limit = table.length;
    }

    @Override
    public void enqueue(Item item) {
        if (size >= limit) {
            throw new IllegalStateException("队列已经满了啦，不给你放进去了");
        }
        table[headIdx] = item;
        headIdx = nextIndex(headIdx);
        size++;
    }

    @Override
    public Item dequeue() {
        if (size <= 0) {
            throw new IllegalStateException("队列空了，没东西给你了");
        }
        Item item = table[tailIdx];
        tailIdx = nextIndex(tailIdx);
        size--;
        return item;
    }

    private int nextIndex(int index) {
        return index < limit - 1 ? index + 1 : 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Nonnull
    @Override
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            private int cursor = tailIdx;

            @Override
            public boolean hasNext() {
                return cursor < size;
            }

            @Override
            public Item next() {
                return table[cursor++];
            }
        };
    }

    public static void main(String[] args) {
        ArrayQueue<String> queue = new ArrayQueue<>(6);
        queue.enqueue("a");
        queue.enqueue("b");
        queue.enqueue("c");
        queue.enqueue("d");
        queue.enqueue("e");
        queue.enqueue("f");

        // 迭代
        for (String s : queue) {
            System.out.println(s);
        }

        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());

        try {
            System.out.println(queue.dequeue());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            queue.enqueue("g");
            System.out.println(queue.dequeue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
