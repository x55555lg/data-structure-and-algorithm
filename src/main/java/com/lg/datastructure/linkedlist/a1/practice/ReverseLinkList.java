package com.lg.datastructure.linkedlist.a1.practice;

import org.junit.jupiter.api.Test;

/**
 * 反转链表
 * 单向链表
 * 双向链表
 *
 * @author Xulg
 * Created in 2021-02-19 9:05
 */
class ReverseLinkList {

    @Test
    public void testReverseSingleLinkedList() {
        SingleNode header = new SingleNode(1);
        SingleNode singleNode2 = new SingleNode(2);
        SingleNode singleNode3 = new SingleNode(3);
        SingleNode singleNode4 = new SingleNode(4);
        SingleNode singleNode5 = new SingleNode(5);
        header.next = singleNode2;
        singleNode2.next = singleNode3;
        singleNode3.next = singleNode4;
        singleNode4.next = singleNode5;
        singleNode5.next = null;

        System.out.println("before reverse......");
        for (SingleNode node = header; node != null; node = node.next) {
            System.out.print(node.value);
        }
        System.out.println();
        SingleNode reversedHeader = reverse(header);
        System.out.println("after reverse......");
        for (SingleNode node = reversedHeader; node != null; node = node.next) {
            System.out.print(node.value);
        }
    }

    @Test
    public void testReverseDoubleLinkedList() {
        DoubleNode header = new DoubleNode(1);
        DoubleNode doubleNode2 = new DoubleNode(2);
        DoubleNode doubleNode3 = new DoubleNode(3);
        DoubleNode doubleNode4 = new DoubleNode(4);
        DoubleNode tail = new DoubleNode(5);
        header.next = doubleNode2;
        header.prev = null;
        doubleNode2.next = doubleNode3;
        doubleNode2.prev = header;
        doubleNode3.next = doubleNode4;
        doubleNode3.prev = doubleNode2;
        doubleNode4.next = tail;
        doubleNode4.prev = doubleNode3;
        tail.next = null;
        tail.prev = doubleNode4;

        System.out.println("before reverse......");
        for (DoubleNode node = header; node != null; node = node.next) {
            System.out.print(node.value);
        }
        System.out.println();
        for (DoubleNode node = tail; node != null; node = node.prev) {
            System.out.print(node.value);
        }
        System.out.println();

        System.out.println("after reverse......");

        DoubleNode[] reversed = reverse(header, tail);
        DoubleNode newHeader = reversed[0];
        DoubleNode newTail = reversed[1];
        for (DoubleNode node = newHeader; node != null; node = node.next) {
            System.out.print(node.value);
        }
        System.out.println();
        for (DoubleNode node = newTail; node != null; node = node.prev) {
            System.out.print(node.value);
        }
    }

    /**
     * 反转单向链表
     *
     * @param header the header of the linked list
     * @return the reversed linked list header
     */
    public static SingleNode reverse(SingleNode header) {
        if (header == null) {
            return null;
        }
        if (header.next == null) {
            return header;
        }
        SingleNode prev = null;
        SingleNode next = null;
        while (header != null) {
            next = header.next;
            header.next = prev;
            prev = header;
            // 下一个节点去
            header = next;
        }
        return prev;
    }

    /**
     * 反转双向链表
     *
     * @param header the header of the linked list
     * @param tail   the tail of the linked list
     * @return the reversed linked list header and tail
     */
    public static DoubleNode[] reverse(DoubleNode header, DoubleNode tail) {
        assert (header == null && tail == null) || (header != null && tail != null);

        // 原链表的头就是反转后链表的尾巴
        DoubleNode newTail = header;

        DoubleNode prev = null;
        DoubleNode next = null;
        while (header != null) {
            next = header.next;
            header.prev = next;
            header.next = prev;
            prev = header;
            header = next;
        }

        // prev就是反转链表的头了
        return new DoubleNode[]{prev, newTail};
    }

    /* ****************************************************************************************************************/

    /**
     * 单向链表节点
     */
    private static class SingleNode {
        int value;
        SingleNode next;

        SingleNode(int value) {
            this.value = value;
        }
    }

    /**
     * 双向链表节点
     */
    private static class DoubleNode {
        int value;
        DoubleNode prev;
        DoubleNode next;

        DoubleNode(int value) {
            this.value = value;
        }
    }
}
