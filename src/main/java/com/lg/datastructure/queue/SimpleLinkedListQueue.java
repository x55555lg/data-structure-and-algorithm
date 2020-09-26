package com.lg.datastructure.queue;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 简单的基于链表的双向队列实现
 *
 * @author Xulg
 * Created in 2020-09-16 13:42
 */
public class SimpleLinkedListQueue<Item> {

    private Node<Item> head;
    private Node<Item> tail;
    private int size;

    /**
     * 从队列头插入数据
     *
     * @param item the item
     */
    public void enqueueFirst(Item item) {
        Node<Item> node = new Node<>(item);
        if (head == null) {
            // 队列空的啊
            head = tail = node;
        } else {
            // 队列已经有数据了啊，新加入的数据将会变成新的头节点
            Node<Item> oldHead = this.head;
            oldHead.prev = node;
            node.prev = null;
            node.next = oldHead;
            head = node;
        }
        // 队列长度自增1
        size++;
    }

    /**
     * 从队列尾插入数据
     *
     * @param item the item
     */
    public void enqueueLast(Item item) {
        Node<Item> node = new Node<>(item);
        if (head == null) {
            // 队列空的啊
            head = tail = node;
        } else {
            // 队列已经有数据了啊，新加入的数据将会变成新的尾节点
            Node<Item> oldTail = this.tail;
            node.prev = oldTail;
            node.next = null;
            oldTail.next = node;
            tail = node;
        }
        // 队列长度自增1
        size++;
    }

    /**
     * 从头部出队
     *
     * @return the item
     */
    public Item dequeueFirst() {
        if (head == null) {
            throw new NoSuchElementException("队列头没有元素了啊");
        }
        Item item = head.item;
        Node<Item> next = head.next;
        if (next == null) {
            // 取掉头部，队列已经空了
            tail = null;
        } else {
            next.prev = null;
        }
        head.next = null;
        head = next;
        size--;
        return item;
    }

    /**
     * 从尾部出队
     *
     * @return the item
     */
    public Item dequeueLast() {
        if (tail == null) {
            throw new NoSuchElementException("队列尾没有元素了啊");
        }
        Item item = tail.item;
        Node<Item> prev = tail.prev;
        if (prev == null) {
            // 取掉尾部，队列已经空了
            head = null;
        } else {
            prev.next = null;
        }
        tail.prev = null;
        tail = prev;
        size--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new Iterator<Item>() {

            private Node<Item> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Item next() {
                Item item = current.item;
                current = current.next;
                return item;
            }
        };
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    /* ************************************************************************************************************** */

    private static class Node<Item> {
        Item item;
        Node<Item> prev;
        Node<Item> next;

        Node(Item item) {
            this.item = item;
        }
    }

    /* ************************************************************************************************************** */

    public static void main(String[] args) {
        SimpleLinkedListQueue<Integer> queue = new SimpleLinkedListQueue<>();
        queue.enqueueFirst(1);
        queue.enqueueFirst(2);
        queue.enqueueFirst(3);
        queue.enqueueFirst(4);
        queue.enqueueLast(5);
        queue.enqueueLast(6);

        // 4    3   2   1   5   6
        StringBuilder sb = new StringBuilder("for each queue: ");
        for (Iterator<Integer> iterator = queue.iterator(); iterator.hasNext(); ) {
            sb.append(iterator.next()).append(" ");
        }
        System.out.println(sb.toString());

        // 4
        System.out.println(queue.dequeueFirst());

        // 6
        System.out.println(queue.dequeueLast());

        // 3
        System.out.println(queue.dequeueFirst());

        // 5
        System.out.println(queue.dequeueLast());

        // 2
        System.out.println(queue.dequeueFirst());

        // 1
        System.out.println(queue.dequeueLast());

        try {
            System.out.println(queue.dequeueFirst());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.out.println(queue.dequeueLast());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
