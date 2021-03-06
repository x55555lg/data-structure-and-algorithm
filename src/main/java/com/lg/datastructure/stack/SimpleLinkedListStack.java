package com.lg.datastructure.stack;

import javax.annotation.Nonnull;
import java.util.Iterator;

/**
 * 基于链表实现的简单的栈
 *
 * @author Xulg
 * Created in 2020-09-16 11:59
 */
class SimpleLinkedListStack<Item> implements SimpleStack<Item> {
    /**
     * 头节点，也就是栈顶，保存的是最近插入的数据
     */
    private Node first;

    private int size;

    @Override
    public void push(Item item) {
        // 将新插入的节点变为头节点，将原来的头节点拼接到新头节点上
        // 采用头插法，在first节点前面插入
        Node oldFirst = this.first;
        this.first = new Node();
        this.first.item = item;
        this.first.next = oldFirst;
        size++;
    }

    @Override
    public Item pop() {
        // 弹出头节点，
        Node node = this.first;
        // 将原来头节点的后继节点作为头节点
        this.first = node.next;
        size--;
        //noinspection unchecked
        return (Item) node.item;
    }

    @Override
    public boolean isEmpty() {
        // 判断size或者first都可以
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
        return new SimpleStackIterator();
    }

    /**
     * 链表节点
     */
    private static class Node {
        Object item;
        Node next;
    }

    private class SimpleStackIterator implements Iterator<Item> {

        private Node current = first;

        @Override
        public boolean hasNext() {
            return first != null;
        }

        @Override
        public Item next() {
            Node old = current;
            this.current = old.next;
            //noinspection unchecked
            return (Item) old.item;
        }

    }

    public static void main(String[] args) {
        SimpleStack<String> stack = new SimpleLinkedListStack<>();
        stack.push("a");
        stack.push("b");
        stack.push("c");
        stack.push("d");
        stack.push("e");
        stack.push("f");

        for (String s : stack) {
            System.out.println(s);
        }

        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
    }
}
