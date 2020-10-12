package com.lg.array;

import java.io.Serializable;

/**
 * 链表学习
 *
 * @author Xulg
 * Created in 2020-04-29 10:01
 */
@SuppressWarnings("WeakerAccess")
class LinkedListStudy {

    public static void main(String[] args) {
        // 创建一个单向链表
        ListNode head = new ListNode(1);
        ListNode second = new ListNode(2);
        ListNode third = new ListNode(3);
        ListNode fourth = new ListNode(4);
        ListNode fifth = new ListNode(5);
        ListNode last = new ListNode(6);
        head.next = second;
        second.next = third;
        third.next = fourth;
        fourth.next = fifth;
        fifth.next = last;

        // 反转链表
        ListNode reverseSingleLinkedList = reverseSingleLinkedList(head);
        forEach(reverseSingleLinkedList);
    }

    /**
     * 反转单向链表
     *
     * @param head 待反转的链表
     * @return 反转后的链表
     */
    public static ListNode reverseSingleLinkedList(ListNode head) {
        if (head == null) {
            return null;
        }
        // 只有一个节点
        if (head.next == null) {
            return head;
        }

        // 1>2>3>4>5>6
        // 6>5>4>3>2>1
        // 原始链表的头节点作为反转链表的尾节点
        ListNode reverseLastNode = new ListNode(head.value, null);

        // 1
        // 2>1
        // 3>2>1
        // ......
        // 原始链表的第二个节点开始遍历
        ListNode currentNode = head.next;
        while (currentNode != null) {
            // 将最后一个节点挂载上去，返回整个反转链表
            reverseLastNode = new ListNode(currentNode.value, reverseLastNode);
            // 下一个节点
            currentNode = currentNode.next;
        }
        return reverseLastNode;
    }

    private static void forEach(ListNode head) {
        for (ListNode node = head; node != null; node = node.next) {
            System.out.println(node.value);
        }
    }

    /**
     * 链表节点
     */
    private static class ListNode implements Serializable {
        private static final long serialVersionUID = 1L;

        /**
         * 节点上的值
         */
        int value;

        /**
         * 下一个节点
         */
        ListNode next;

        ListNode() {
        }

        ListNode(Integer value) {
            this.value = value;
        }

        ListNode(Integer value, ListNode next) {
            this.value = value;
            this.next = next;
        }
    }
}
