package com.lg.datastructure.queue;

/**
 * 简单的队列实现
 *
 * @author Xulg
 * Created in 2020-09-16 13:41
 */
interface SimpleQueue<Item> extends Iterable<Item> {

    /**
     * 入队
     *
     * @param item the item
     */
    void enqueue(Item item);

    /**
     * 出队
     *
     * @return the item
     */
    Item dequeue();

    /**
     * 队列是否空的
     *
     * @return is the stack empty
     */
    boolean isEmpty();

    /**
     * 队列的大小
     *
     * @return the size of the stack
     */
    int size();
}
