package com.lg.datastructure.linkedlist;

import java.util.NoSuchElementException;
import java.util.function.Consumer;

/**
 * 简单实现的双向链表
 *
 * @author Xulg
 * Created in 2020-09-16 11:33
 */
class SimpleLinkedList<Item> {

    @SuppressWarnings("FieldMayBeFinal")
    private class Node {
        Item item;
        Node prev;
        Node next;

        Node(Item item) {
            this.item = item;
        }
    }

    /**
     * 链表头节点的引用
     */
    private Node head;

    /**
     * 链表尾节点的引用
     */
    private Node tail;

    private int size;

    /**
     * 从头插入数据
     *
     * @param item the item
     */
    public void addFirst(Item item) {
        // 将代插入的数据打包成node节点
        Node node = new Node(item);
        if (head == null) {
            // 链表是空的，头和尾是同一个节点
            head = node;
            tail = node;
        } else {
            /*
            // 这种写法视频中的
            node.next = head;
            head.prev = node;
            head = node;
            */

            Node oldHead = head;
            // 插入数据变成头节点
            head = node;
            // 老节点的上一个节点指向新的节点
            oldHead.prev = head;
            // 之前的老节点挂到新的head的后面
            head.next = oldHead;
        }
        size++;
    }

    /**
     * 从尾巴插入数据
     *
     * @param item the item
     */
    public void addLast(Item item) {
        // 将代插入的数据打包成node节点
        Node node = new Node(item);
        if (head == null) {
            // 链表是空的
            head = node;
            tail = node;
        } else {
            /*
            // 这种写法视频中的
            node.prev = tail;
            tail.next = node;
            tail = node;
            */

            Node oldTail = tail;
            // 插入数据变成尾节点
            tail = node;
            // 老节点的next指向新节点
            oldTail.next = tail;
            // 新节点的上一个节点指向老节点
            tail.prev = oldTail;
        }
        size++;
    }

    /**
     * 反转链表
     */
    public void reverse() {
        // TODO 2020/9/21 双向链表最复杂了
        /*
         * 1  ->  2  ->  3  ->  4 ->  5
         *    <-     <-     <-    <-
         * 反转后
         * 5  ->  4  ->  3  ->  2  ->  1
         *    <-     <-     <-     <-
         */
        // 原来的头节点将会变成尾节点
        tail = head;
        Node prev = null;
        Node next = null;
        while (head != null) {
            next = head.next;
            head.next = prev;
            head.prev = next;
            prev = head;
            head = next;
        }
        head = prev;
    }

    /**
     * 删除值为item的所有节点
     *
     * @param item the item
     */
    public void deleteAll(Item item) {
        /*
         * 3  ->  3  ->  3  ->  4  ->  5  ->  3  ->  2  ->  3
         *    <-     <-     <-     <-     <-     <-     <-
         * 先删除头部的3以后
         * 4  ->  5  ->  3  ->  2  ->  3
         *    <-     <-     <-     <-
         * 再删除尾部的3以后
         * 4  ->  5  ->  3  ->  2
         *    <-     <-     <-
         * 删除中间的3以后
         * 4  ->  5  ->  2
         *    <-     <-
         */

        if (item == null) {
            return;
        }

        // 头节点等于item的都删除掉
        while (head != null) {
            if (head.item.equals(item)) {
                /*
                 * 3  ->  3  ->  3  ->  4 ->  5  ->  3  ->  2
                 *    <-     <-     <-    <-     <-     <-
                 */
                Node next = head.next;
                head.next = null;
                next.prev = null;
                head = next;
                size--;
            } else {
                break;
            }
        }

        // 尾节点等于item的都删除掉
        while (tail != null) {
            if (tail.item.equals(item)) {
                /*
                 * 4  ->  5  ->  3  ->  2  ->  3
                 *    <-     <-     <-     <-
                 */
                Node prev = tail.prev;
                tail.prev = null;
                prev.next = null;
                tail = prev;
                size--;
            } else {
                break;
            }
        }

        /*
         * 4  ->  5  ->  3  ->  2
         *    <-     <-     <-
         */
        // 删除中间的
        for (Node node = head; node != null; node = node.next) {
            if (node.item.equals(item)) {
                /*
                 * 5  ->  3  ->  2
                 *    <-     <-
                 */
                Node prev = node.prev;
                Node next = node.next;
                prev.next = next;
                next.prev = prev;
                node.prev = null;
                node.next = null;
                size--;
            }
        }
    }

    /**
     * 删除并返回第一个元素
     *
     * @return the item
     */
    public Item removeFirst() {
        if (head == null) {
            throw new NoSuchElementException("没有元素啦");
        }
        Item item = head.item;
        Node next = head.next;
        if (next != null) {
            next.prev = null;
        } else {
            // 头节点没有next了，说明链表空了，那么尾节点也要置为null，
            // 否则的话removeLast还能被调用
            tail = null;
        }
        head.next = null;
        head = next;
        size--;
        return item;
    }

    /**
     * 删除并返回最后一个元素
     *
     * @return the item
     */
    public Item removeLast() {
        if (tail == null) {
            throw new NoSuchElementException("没有元素啦");
        }
        Item item = tail.item;
        Node prev = tail.prev;
        if (prev != null) {
            prev.next = null;
        } else {
            // 尾节点没有prev了，说明链表空了，那么头节点也要置为null，
            // 否则的话removeFirst还能被调用
            head = null;
        }
        tail.prev = null;
        tail = prev;
        size--;
        return item;
    }

    /**
     * 正序遍历
     *
     * @param action the action
     */
    public void forEachFromHead(Consumer<? super Item> action) {
        for (Node node = head; node != null; node = node.next) {
            action.accept(node.item);
        }
    }

    /**
     * 逆序遍历
     *
     * @param action the action
     */
    public void forEachFromTail(Consumer<? super Item> action) {
        for (Node node = tail; node != null; node = node.prev) {
            action.accept(node.item);
        }
    }

    /**
     * 链表的大小
     *
     * @return the size
     */
    public int size() {
        return size;
    }

    /**
     * 链表是否空的
     *
     * @return is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /* ************************************************************************************************************** */

    public static void main(String[] args) {
        /*
         * 3  ->  3  ->  3  ->  4  ->  5  ->  3  ->  2  ->  3
         *    <-     <-     <-     <-     <-     <-     <-
         */
        SimpleLinkedList<Integer> linkedList = new SimpleLinkedList<>();
        linkedList.addFirst(3);
        linkedList.addFirst(3);
        linkedList.addFirst(3);
        linkedList.addLast(4);
        linkedList.addLast(5);
        linkedList.addLast(3);
        linkedList.addLast(2);
        linkedList.addLast(3);

        StringBuilder sb1 = new StringBuilder("正序遍历：");
        linkedList.forEachFromHead(item -> sb1.append(item).append(" "));
        System.out.println(sb1.toString());

        System.out.println("-----------------------------------------");

        StringBuilder sb2 = new StringBuilder("逆序遍历：");
        linkedList.forEachFromTail(item -> sb2.append(item).append(" "));
        System.out.println(sb2.toString());

        // 删除
        linkedList.deleteAll(3);

        linkedList.forEachFromHead(System.out::println);
        System.out.println("-----------------------------------------");
        linkedList.forEachFromTail(System.out::println);
        System.out.println("-----------------------------------------");

        linkedList.reverse();

        linkedList.forEachFromHead(System.out::println);
        System.out.println("-----------------------------------------");
        linkedList.forEachFromTail(System.out::println);
        System.out.println("-----------------------------------------");

        System.out.println(linkedList.removeFirst());
        System.out.println(linkedList.removeLast());

        System.out.println("-----------------------------------------");

        linkedList.forEachFromHead(System.out::println);
        System.out.println("-----------------------------------------");
        linkedList.forEachFromTail(System.out::println);
        System.out.println("-----------------------------------------");

        // 移最后一个元素
        System.out.println(linkedList.removeFirst());
        // 这里链表已经空了，再操作就会报错，没有元素了
        try {
            System.out.println(linkedList.removeLast());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
