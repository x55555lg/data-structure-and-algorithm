package com.lg.datastructure.linkedlist.a1.practice;

/**
 * 简单队列
 *
 * @author Xulg
 * Created in 2021-01-21 15:13
 */
interface SimpleQueue<Item> {
    void addFirst(Item item);

    void addLast(Item item);

    Item pollFirst();

    Item pollLast();

    Item peekFirst();

    Item peekLast();

    boolean isEmpty();

    int size();

    void reverse();

    void delete(Item item);
}
