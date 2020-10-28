package com.lg.datastructure.linkedlist.mianshiti;

import java.util.HashSet;

/**
 * 判断单向链表是否是循环链表
 *
 * @author Xulg
 * Created in 2020-10-27 14:03
 */
class LoopLinkedListCheck {

    /*
     * 给定一个单向链表的头节点，如何判断一个链表是否有环？
     *  循环链表
     *  		    ○←○
     *              ↓  ↑
     *      ○→○→○→○
     * 1.使用HashSet集合，遍历链表，将节点加入集合中，如果集合中已经存在待加入的节点，那么就是循环列表
     * 2.使用快慢指针
     */

    /**
     * 判断是否是循环链表
     * 如果是循环链表，则返回第一个入环节点
     * 否则返回null
     */
    @SuppressWarnings("MapOrSetKeyShouldOverrideHashCodeEquals")
    public static Node isLoopLinkedList1(Node header) {
        if (header == null || header.next == null || header.next.next == null) {
            // 链表为null，链表只有一个节点，链表只有两个节点，都不可能是循环链表
            return null;
        }
        /*
         *循环链表
         * 		   ○←○
         *         ↓  ↑
         * ○→○→○→○
         */
        HashSet<Node> hashSet = new HashSet<>();
        for (Node node = header; node != null; node = node.next) {
            if (hashSet.contains(node)) {
                // 集合中已经加过这个节点了，肯定是循环链表了
                return node;
            } else {
                hashSet.add(node);
            }
        }
        return null;
    }

    /**
     * 使用快慢指针判断是否是循环链表
     * 如果是循环链表，则返回第一个入环节点
     * 否则返回null
     */
    public static Node isLoopLinkedList2(Node header) {
        if (header == null || header.next == null || header.next.next == null) {
            // 链表为null，链表只有一个节点，链表只有两个节点，都不可能是循环链表
            return null;
        }

        // 慢指针，步长为1
        Node slow = header.next;
        // 快指针，步长为2
        Node faster = header.next.next;

        /*
         *		               ○←○
         * 		               ↓  ↑
         * ○→○→○→○→○→○→○
         *      s  f
         * 循环之后
         * ●为快慢指针的相遇点
         *		               ●←○
         * 		               ↓  ↑
         * ○→○→○→○→○→○→○
         */

        // 只要有环，快慢指针一定会相遇的
        while (slow != faster) {
            if (faster.next == null || faster.next.next == null) {
                // 快指针已经走完了，肯定没有环
                return null;
            }
            slow = slow.next;
            faster = faster.next.next;
        }

        /*
         *                      s
         *		               ●←○
         * 		               ↓  ↑
         * ○→○→○→○→○→○→○
         * f
         * 1.快指针回到header起点，慢指针还在相遇点
         * 2.快慢指针一起移动，步长为1
         * 3.若快慢指针相遇了，那么相遇点就是入环点
         *		               ●←○
         * 		               ↓  ↑
         * ○→○→○→○→○→★→○
         *                     sf
         * ★就是入环节点
         */

        // 保持慢指针的位置不动，快指针从header重新开始
        faster = header;
        // 快慢指针一起移动，步长为1
        // 如果快指针和慢指针相遇了，那么相遇点就是入环点
        while (faster != slow) {
            faster = faster.next;
            slow = slow.next;
        }
        // 返回相遇点
        return slow;
    }

    /* ************************************************************************************************************** */

    private static class Node {
        private final int value;
        private Node next;

        Node(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    '}';
        }
    }

    public static void main(String[] args) {
        Node head = new Node(0);
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        Node node7 = new Node(7);
        Node node8 = new Node(8);
        head.next = node1;
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node6;
        node6.next = node7;
        node7.next = node8;
        node8.next = node5;

        Node loopNode1 = isLoopLinkedList1(head);
        Node loopNode2 = isLoopLinkedList2(head);
        System.out.println(loopNode1);
        System.out.println(loopNode2);
    }
}
