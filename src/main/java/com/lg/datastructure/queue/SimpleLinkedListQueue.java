package com.lg.datastructure.queue;

import javax.annotation.Nonnull;
import java.util.Iterator;

/**
 * 简单的基于链表的单向队列实现
 *
 * @author Xulg
 * Created in 2020-09-16 13:42
 */
class SimpleLinkedListQueue<Item> implements SimpleQueue<Item> {

    /**
     * 最早存放的数据，是一个链表
     */
    private Node first;

    /**
     * 最后存放的一个数据
     */
    private Node last;

    private int size;

    @Override
    public void enqueue(Item item) {
        Node oldLast = last;

        Node node = new Node();
        node.item = item;
        node.next = null;

        if (isEmpty()) {
            // 链表空的，那么最早和最近都一样
            first = node;
        } else {
            // 将新节点拼接到头节点尾部的后面
            oldLast.next = node;
        }

        // 将新数据查到链表尾部
        last = node;

        size++;
    }

    @Override
    public Item dequeue() {
        if (first == null) {
            return null;
        }
        // 队列头部就是最早的数据
        @SuppressWarnings("unchecked")
        Item item = (Item) first.item;
        first = first.next;
        size--;
        if (isEmpty()) {
            // 队列都空了，last就没用了
            last = null;
        }
        return item;
    }

    @Override
    public boolean isEmpty() {
        // return size == 0;
        return first == null;
    }

    @Override
    public int size() {
        return size;
    }

    @Nonnull
    @Override
    public Iterator<Item> iterator() {
        return new QueueIterator();
    }

    private static class Node {
        Object item;
        Node next;
    }

    private class QueueIterator implements Iterator<Item> {

        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            @SuppressWarnings("unchecked")
            Item item = (Item) current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        SimpleLinkedListQueue<String> queue = new SimpleLinkedListQueue<>();
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
        System.out.println(queue.dequeue());
    }
}
