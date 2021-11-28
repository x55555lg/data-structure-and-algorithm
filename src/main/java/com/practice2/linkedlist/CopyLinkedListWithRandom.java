package com.practice2.linkedlist;

import java.util.HashMap;

/**
 * @author Xulg
 * @since 2021-10-24 20:32
 * Description: 复制链表
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

    private static class CopyByHashMap {
        public static Node copy(Node head) {
            if (head == null) {
                return null;
            }
            HashMap<Node, Node> copyIndexMap = new HashMap<>();
            for (Node node = head; node != null; node = node.next) {
                Node copy = new Node(node.value);
                copyIndexMap.put(node, copy);
            }
            for (Node node = head; node != null; node = node.next) {
                Node copy = copyIndexMap.get(node);
                copy.next = copyIndexMap.get(node.next);
                if (copy.rand != null) {
                    copy.rand = copyIndexMap.get(node.rand);
                }
            }
            return copyIndexMap.get(head);
        }
    }

    private static class CopyBy {
        public static Node copy(Node head) {
            if (head == null) {
                return null;
            }
            for (Node node = head; node != null; node = node.next.next) {
                Node copy = new Node(node.value);
                Node next = node.next;
                node.next = copy;
                copy.next = next;
            }
            for (Node node = head; node != null; node = node.next.next) {
                Node copy = node.next;
                if (node.rand != null) {
                    copy.rand = node.rand.next;
                }
            }
            Node newHead = head.next;
            for (Node node = head; node != null; ) {
                Node copy = node.next;
                Node oldNext = copy.next;
                node.next = oldNext;
                copy.next = oldNext == null ? null : oldNext.next;
                node = oldNext;
            }
            return newHead;
        }
    }

    private static class Node {
        int value;
        Node next, rand;

        Node(int val) {value = val;}
    }
}
