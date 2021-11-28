package com.practice2.linkedlist;

/**
 * @author Xulg
 * @since 2021-11-08 9:57
 * Description: 反转链表
 */
class ReverseLinkedList2 {

    public static SingleNode reverseSingleLinkedList(SingleNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        SingleNode prev = null;
        SingleNode next = null;
        while (head != null) {
            next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }
        return prev;
    }

    public static DoubleNode reverseDoubleLinkedList(DoubleNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        DoubleNode prev = null;
        DoubleNode next = null;
        while (head != null) {
            next = head.next;
            head.next = prev;
            head.prev = next;
            prev = head;
            head = next;
        }
        return prev;
    }

    private static class SingleNode {
        private int value;
        private SingleNode next;

        SingleNode(int value) {
            this.value = value;
        }

        SingleNode(int value, SingleNode next) {
            this.value = value;
            this.next = next;
        }
    }

    private static class DoubleNode {
        private int value;
        private DoubleNode prev, next;

        DoubleNode(int value) {
            this.value = value;
        }

        DoubleNode(int value, DoubleNode prev, DoubleNode next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
