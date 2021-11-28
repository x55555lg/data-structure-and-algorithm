package com.lg.datastructure.linkedlist.a1.practice;

import java.util.HashMap;

/**
 * 带有random节点的链表复制
 *
 * @author Xulg
 * Created in 2021-01-22 9:58
 */
@SuppressWarnings("MapOrSetKeyShouldOverrideHashCodeEquals")
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
     * 复制链表
     * 使用hash表
     * 时间复杂度O(n)
     * 空间复杂度O(n)
     */
    public static Node copyByHash(Node header) {
        if (header == null) {
            return null;
        }

        // 节点和复制节点的映射关系
        HashMap<Node, Node> oldNewMapping = new HashMap<>();
        for (Node node = header; node != null; node = node.next) {
            Node copyNode = new Node(node.value);
            oldNewMapping.put(node, copyNode);
        }

        // copy the linked list
        for (Node node = header; node != null; node = node.next) {
            Node newNode = oldNewMapping.get(node);
            newNode.next = oldNewMapping.get(node.next);
            if (newNode.rand != null) {
                newNode.rand = oldNewMapping.get(node.rand);
            }
        }

        // 获取新链表的头
        return oldNewMapping.get(header);
    }

    /**
     * 复制链表
     * 使用插入法
     * 时间复杂度O(n)
     * 空间复杂度O(1)
     */
    public static Node copyByInsert(Node header) {
        if (header == null) {
            return null;
        }
        // 循环原链表的每个节点，复制节点然后插入到当前节点的后面
        for (Node node = header; node != null; ) {
            Node newNode = new Node(node.value);
            // 插入复制的节点到原节点后面
            Node next = node.next;
            node.next = newNode;
            newNode.next = next;
            // 复制原链表的下一个节点去
            node = next;
        }

        // 循环原链表的每个节点，将原节点的rand复制到新链表的节点上去
        for (Node node = header; node != null && node.next != null; ) {
            Node copyNode = node.next;
            if (node.rand != null) {
                // 将复制的rand节点挂到新节点上去
                copyNode.rand = node.rand.next;
            }
            // 原链表的下一个节点
            node = copyNode.next;
        }

        Node newHeader = header.next;
        // 剥离原链表和新链表
        for (Node node = header; node != null && node.next != null; ) {
            // 复制节点
            Node copyNode = node.next;
            // 原链表的下一个节点
            Node nextNode = copyNode.next;

            // 原链表拼接起来
            node.next = nextNode;

            // 新链表拼接起来
            copyNode.next = nextNode == null ? null : nextNode.next;

            // 原链表的下一个节点
            node = nextNode;
        }
        return newHeader;
    }

    private static void printRandLinkedList(Node head) {
        Node cur = head;
        System.out.print("order: ");
        while (cur != null) {
            System.out.print(cur.value + " ");
            cur = cur.next;
        }
        System.out.println();
        cur = head;
        System.out.print("rand:  ");
        while (cur != null) {
            System.out.print(cur.rand == null ? "- " : cur.rand.value + " ");
            cur = cur.next;
        }
        System.out.println();
    }

    /* ****************************************************************************************************************/

    private static class Node {
        int value;
        Node next;
        Node rand;

        Node(int val) {
            value = val;
        }
    }

    /* ****************************************************************************************************************/

    public static void main(String[] args) {
        Node head = null;
        Node res1 = null;
        Node res2 = null;
        printRandLinkedList(head);
        res1 = copyByHash(head);
        printRandLinkedList(res1);
        res2 = copyByInsert(head);
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
        res1 = copyByHash(head);
        System.out.println("方法一的拷贝链表：");
        printRandLinkedList(res1);
        System.out.println("=========================");
        res2 = copyByInsert(head);
        System.out.println("方法二的拷贝链表：");
        printRandLinkedList(res2);
        System.out.println("=========================");
        System.out.println("经历方法二拷贝之后的原始链表：");
        printRandLinkedList(head);
        System.out.println("=========================");
    }
}
