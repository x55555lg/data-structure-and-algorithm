package com.lg.datastructure.stack.a1.practice;

/**
 * 链表实现的栈
 *
 * @author Xulg
 * Created in 2021-01-21 9:51
 */
@SuppressWarnings("unchecked")
class StackImplByNode<Item> implements SimpleStack<Item> {

    private Node header;
    private Node tail;

    /**
     * 栈的元素个数
     */
    private int count;

    /**
     * first指向最后进栈的节点
     */
    private Node first;

    @Override
    public void push(Item item) {
        if (true) {
            // 采用头插法
            Node node = new Node(item);
            if (first == null) {
                first = node;
            } else {
                Node oldNode = this.first;
                first = node;
                node.next = oldNode;
            }
            count++;
        } else {
            Node node = new Node(item);
            if (header == null) {
                // 头尾一起指向这个节点
                header = node;
            } else {
                // 新节点挂到尾巴后面，新节点指向尾巴
                tail.next = node;
                node.prev = tail;
            }
            tail = node;
            count++;
        }
    }

    @Override
    public Item pop() {
        if (count <= 0) {
            throw new IllegalStateException("栈已经空了");
        }
        if (true) {
            Node oldNode = this.first;
            first = oldNode.next;
            oldNode.next = null;
            count--;
            return (Item) oldNode.value;
        } else {
            Node node = tail;
            Node prev = tail.prev;
            tail = prev;
            if (prev != null) {
                prev.next = null;
            }
            count--;
            if (count == 0) {
                header = null;
                tail = null;
            }
            return (Item) node.value;
        }
    }

    @Override
    public Item peek() {
        if (count <= 0) {
            throw new IllegalStateException("栈已经空了");
        }
        if (true) {
            return (Item) first.value;
        } else {
            return (Item) tail.value;
        }
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public int size() {
        return count;
    }

    private static class Node {
        Object value;
        Node prev;
        Node next;

        Node(Object value) {
            this.value = value;
        }
    }

    public static void main(String[] args) {
        StackImplByNode<Integer> stack = new StackImplByNode<>();
        stack.push(1);
        stack.push(2);
        System.out.println(stack.pop());
        stack.push(3);
        stack.push(4);
        System.out.println(stack.pop());
        stack.push(5);
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
    }
}
