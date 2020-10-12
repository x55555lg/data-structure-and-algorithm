package com.lg.datastructure.bag;

import javax.annotation.Nonnull;
import java.util.Iterator;

/**
 * 基于链表实现简单的包结构
 *
 * @author Xulg
 * Created in 2020-09-16 17:42
 */
class SimpleLinkedListBag<Item> implements SimpleBag<Item> {

    /**
     * 头节点，保存的是最近插入的数据
     */
    private Node first;

    private int size;

    @Override
    public void add(Item item) {
        // 将数据包装成节点
        Node node = new Node();
        node.item = item;
        node.next = null;

        Node oldFirst = first;

        // 将新插入的节点变为头节点，将原来的头节点拼接到新头节点上
        first = node;
        first.next = oldFirst;

        size++;
    }

    @Override
    public boolean isEmpty() {
        // 两种写法
        //return first == null;
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Nonnull
    @Override
    public Iterator<Item> iterator() {
        return new LinkedListBagIterator();
    }

    private class Node {
        private Item item;
        private Node next;
    }

    private class LinkedListBagIterator implements Iterator<Item> {

        private Node current = first;

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
    }

    public static void main(String[] args) {
        SimpleLinkedListBag<String> stack = new SimpleLinkedListBag<>();
        stack.add("a");
        stack.add("b");
        stack.add("c");
        stack.add("d");
        stack.add("e");
        stack.add("f");

        for (String s : stack) {
            System.out.println(s);
        }

        System.out.println("-------------");

        for (String s : stack) {
            System.out.println(s);
        }
    }
}
