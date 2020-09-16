package com.lg.datastructure.queue;

import javax.annotation.Nonnull;
import java.util.Iterator;

/**
 * 基于数组实现简单的队列
 *
 * @author Xulg
 * Created in 2020-09-16 14:50
 */
class SimpleArrayQueue<Item> implements SimpleQueue<Item> {

    private Item[] array;

    /**
     * 队列的头节点所在的下标
     */
    private int headIndex = 0;

    /**
     * 队列的尾节点所在的下标
     */
    private int tailIndex;

    private int size;

    public SimpleArrayQueue(int capacity) {
        //noinspection unchecked
        array = (Item[]) new Object[capacity];
    }

    @Override
    public void enqueue(Item item) {
        if (array.length == tailIndex) {
            if (headIndex == 0) {
                throw new IllegalStateException("队列满了啦");
            }
            // 进行数据迁移，将head至tail的数据，迁移到0至X
            //noinspection ManualArrayCopy
            for (int i = headIndex; i < tailIndex; i++) {
                array[i - headIndex] = array[i];
            }
            // 重新计算head和tail
            tailIndex = tailIndex - headIndex;
            headIndex = 0;
        }
        // 往尾巴插
        array[tailIndex] = item;
        tailIndex++;
        size++;
    }

    @Override
    public Item dequeue() {
        if (headIndex == tailIndex) {
            return null;
        }
        // 读取头节点
        Item item = array[headIndex];
        array[headIndex] = null;
        headIndex++;
        size--;
        return item;
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
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
        SimpleArrayQueue<String> queue = new SimpleArrayQueue<>(6);
        queue.enqueue("a");
        queue.enqueue("b");
        queue.enqueue("c");
        queue.enqueue("d");
        queue.enqueue("e");
        queue.enqueue("f");
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());

        queue.enqueue("g");
        System.out.println(queue.dequeue());
    }
}
