package com.practice.linkedlist;

import java.util.HashMap;

/**
 * @author Xulg
 * Created in 2021-05-11 15:02
 */
class CopyLinkedListWithRandom {

    /*
     * 一种特殊的单向链表节点类如下：
     * class Node {
     *     int value;
     *     Node next;
     *     Node rand;
     *     Node(int val) {value=val;}
     * }
     * rand指针是单链表节点结构中新增的指针，rand可能指向链表中的任意一个节点，也可能指向null，
     * 给定一个由Node节点类型组成的无环单向链表的头节点header，请实现一个函数完成这个链表的复制，
     * 并返回复制的新链表的头节点。
     * 要求：
     *      时间复杂度O(N)
     *      额外空间复杂度O(1)
     *---------------------------------------------------------------------------------------------
     * 1.使用hash表记录每个节点，key为原节点，value为复制节点
     *   循环原链表的每个节点，获取节点对应的复制节点，复制节点的next就是原节点的next对应的复制节点，
     *   复制节点的rand就是原节点的rand对应的复制节点。
     * 2.循环原链表的每个节点，复制节点然后插入到当前节点的后面
     *   循环原链表的每个节点，将原节点的rand复制到新链表的节点上去
     *   剥离原链表和新链表
     */

    /**
     * 时间复杂度O(N)
     * 空间复杂度O(N)
     */
    @SuppressWarnings("MapOrSetKeyShouldOverrideHashCodeEquals")
    private static class CopyWithRandomByHashMap {
        public static Node copy(Node head) {
            if (head == null) {
                return null;
            }
            HashMap<Node, Node> originCloneMap = new HashMap<>(16);
            for (Node node = head; node != null; node = node.next) {
                Node cloned = new Node(node.val);
                originCloneMap.put(node, cloned);
            }
            for (Node node = head; node != null; node = node.next) {
                Node cloned = originCloneMap.get(node);
                cloned.next = originCloneMap.get(node.next);
                if (node.rand != null) {
                    cloned.rand = originCloneMap.get(node.rand);
                }
            }
            return originCloneMap.get(head);
        }
    }

    /**
     * 时间复杂度O(N)
     * 空间复杂度O(1)
     */
    private static class Best {
        public static Node copy(Node head) {
            if (head == null) {
                return null;
            }

            /*
             * 原链表
             *    r↑------↓
             * ①→②→③→④→⑤
             *     ↑--↓r
             *
             * 复制和插入之后
             *       r↑--------------↓
             * ①→❶→②→❷→③→❸→④→❹→⑤→❺
             *        ↑------↓r
             */

            // 复制每一个节点并插入到原节点后面
            for (Node node = head; node != null; node = node.next.next) {
                Node cloned = new Node(node.val);
                Node next = node.next;
                node.next = cloned;
                cloned.next = next;
            }
            // 遍历链表原节点，复制rand指针
            for (Node node = head; node != null; node = node.next.next) {
                Node cloned = node.next;
                if (node.rand != null) {
                    cloned.rand = node.rand.next;
                }
            }
            // 剥离2个链表
            // ①→❶→②→❷→③→❸→④→❹→⑤→❺
            Node clonedHead = head.next;
            for (Node node = head; node != null; ) {
                Node cloned = node.next;
                Node next = cloned.next;
                node.next = next;
                cloned.next = next == null ? null : next.next;
                node = next;
            }
            return clonedHead;
        }
    }

    private static class Node {
        private final int val;
        private Node next;
        Node rand;

        Node(int val) {
            this.val = val;
        }
    }

    public static void main(String[] args) {
        Node head = null;
        Node res1 = null;
        Node res2 = null;
        printRandLinkedList(head);

        res1 = CopyWithRandomByHashMap.copy(head);
        printRandLinkedList(res1);
        res2 = Best.copy(head);
        printRandLinkedList(res2);
        printRandLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        head.next.next.next.next = new Node(5);
        head.next.next.next.next.next = new Node(6);

        head.rand = head.next.next.next.next.next; // 1 -> 6
        head.next.rand = head.next.next.next.next.next; // 2 -> 6
        head.next.next.rand = head.next.next.next.next; // 3 -> 5
        head.next.next.next.rand = head.next.next; // 4 -> 3
        head.next.next.next.next.rand = null; // 5 -> null
        head.next.next.next.next.next.rand = head.next.next.next; // 6 -> 4

        System.out.println("原始链表：");
        printRandLinkedList(head);
        System.out.println("=========================");
        res1 = CopyWithRandomByHashMap.copy(head);
        System.out.println("方法一的拷贝链表：");
        printRandLinkedList(res1);
        System.out.println("=========================");
        res2 = Best.copy(head);
        System.out.println("方法二的拷贝链表：");
        printRandLinkedList(res2);
        System.out.println("=========================");
        System.out.println("经历方法二拷贝之后的原始链表：");
        printRandLinkedList(head);
        System.out.println("=========================");

        {
            Node node = new Node(-1);
            node.next = null;
            node.rand = null;
            Node copy = Best.copy(node);
            System.out.println(copy);
        }
    }

    private static void printRandLinkedList(Node head) {
        Node cur = head;
        System.out.print("order: ");
        while (cur != null) {
            System.out.print(cur.val + " ");
            cur = cur.next;
        }
        System.out.println();
        cur = head;
        System.out.print("rand:  ");
        while (cur != null) {
            System.out.print(cur.rand == null ? "- " : cur.rand.val + " ");
            cur = cur.next;
        }
        System.out.println();
    }

}
