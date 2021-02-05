package com.lg.datastructure.linkedlist.a1.practice;

/**
 * 双向队列
 *
 * @author Xulg
 * Created in 2021-01-21 15:06
 */
@SuppressWarnings("unchecked")
class SimpleLinkedList<Item> implements SimpleQueue<Item> {

    private Node header;
    private Node tail;
    private int count;

    @Override
    public void addFirst(Item item) {
        Node node = new Node(item);
        if (header == null) {
            header = node;
            tail = node;
        } else {
            Node oldHeader = header;
            header = node;
            oldHeader.prev = header;
            header.next = oldHeader;
        }
        count++;
    }

    @Override
    public void addLast(Item item) {
        Node node = new Node(item);
        if (tail == null) {
            header = node;
            tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
        count++;
    }

    @Override
    public Item pollFirst() {
        if (header == null) {
            return null;
        }
        Node oldHeader = this.header;
        header = oldHeader.next;
        oldHeader.next = null;
        if (header != null) {
            header.prev = null;
        } else {
            // 队列空了
            tail = null;
        }
        count--;
        return (Item) oldHeader.val;
    }

    @Override
    public Item pollLast() {
        if (tail == null) {
            return null;
        }
        Node oldTail = this.tail;
        tail = oldTail.prev;
        oldTail.prev = null;
        if (tail != null) {
            tail.next = null;
        } else {
            // 队列空了
            header = null;
        }
        count--;
        return (Item) oldTail.val;
    }

    @Override
    public Item peekFirst() {
        return (Item) header.val;
    }

    @Override
    public Item peekLast() {
        return (Item) tail.val;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public void reverse() {
        if (header == null || tail == null) {
            return;
        }

        {
            /*
             * <-  1  ->  2  ->  3  ->  4  ->  5  ->
             *        <-     <-     <-     <-
             * 反转后
             * <-  5  ->  4  ->  3  ->  2  ->  1  ->
             *        <-     <-     <-     <-
             */

            // 头节点位置会变成尾节点
            tail = header;

            // prev用来存放当前节点的
            Node prev = null;
            // next用来存放当前节点的下一个位置的
            Node next = null;
            while (header != null) {
                next = header.next;
                header.next = prev;
                header.prev = next;
                prev = header;
                header = next;
            }
            // prev位置就是头节点
            header = prev;
        }

        //noinspection ConstantConditions
        if (false) {
            /*
             * 1  ->  2  ->  3  ->  4 ->  5
             * 反转后
             * 5  ->  4  ->  3  ->  2  -> 1
             */
            // 单向链表的反转
            Node prev = null;
            Node next = null;
            while (header != null) {
                next = header.next;
                header.next = prev;
                prev = header;
                header = next;
            }
            header = prev;
        }
    }

    @Override
    public void delete(Item item) {
        if (item == null) {
            return;
        }
        // 头节点等于item的都删除掉
        while (header != null) {
            if (header.val.equals(item)) {
                Node next = header.next;
                header.next = null;
                if (next != null) {
                    next.prev = null;
                }
                count--;
                header = next;
            } else {
                break;
            }
        }
        // 尾节点等于item的都删除掉
        while (tail != null) {
            if (tail.val.equals(item)) {
                Node prev = tail.prev;
                tail.prev = null;
                if (prev != null) {
                    prev.next = null;
                }
                count--;
                tail = prev;
            } else {
                break;
            }
        }

        // 删除中间的等于item的节点
        Node node = header;
        while (node != null) {
            if (node.val.equals(item)) {
                Node prev = node.prev;
                Node next = node.next;
                node.prev = null;
                node.next = null;
                prev.next = next;
                next.prev = prev;
                count--;
                // 看下一个节点去
                node = next;
            } else {
                // 看下一个节点去
                node = node.next;
            }
        }
    }

    /* ****************************************************************************************************************/

    private static class Node {
        Object val;
        Node prev;
        Node next;

        Node(Object val) {
            this.val = val;
        }
    }

    public static void main(String[] args) {
        SimpleLinkedList<Integer> simpleLinkedList = new SimpleLinkedList<>();
        simpleLinkedList.addFirst(5);
        simpleLinkedList.addFirst(4);
        simpleLinkedList.addFirst(3);
        simpleLinkedList.addFirst(2);
        simpleLinkedList.addFirst(1);
        simpleLinkedList.reverse();
        System.out.println();
    }

}
