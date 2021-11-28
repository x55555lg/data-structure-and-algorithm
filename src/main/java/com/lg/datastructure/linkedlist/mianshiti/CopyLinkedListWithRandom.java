package com.lg.datastructure.linkedlist.mianshiti;

import java.util.HashMap;

/**
 * 带有random节点的链表复制
 *
 * @author Xulg
 * Created in 2020-10-27 15:18
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
     */
    @SuppressWarnings("MapOrSetKeyShouldOverrideHashCodeEquals")
    public static Node copy1(Node header) {
        if (header == null) {
            return null;
        }
        HashMap<Node, Node> originCloneIndexMap = new HashMap<>();
        for (Node node = header; node != null; node = node.next) {
            // 复制节点
            Node cloneNode = new Node(node.value);
            // 将原节点，克隆节点存入hash表
            originCloneIndexMap.put(node, cloneNode);
        }
        // 拼接复制链表
        for (Node node = header; node != null; node = node.next) {
            // 通过原链表节点获取对应的复制节点
            Node cloneNode = originCloneIndexMap.get(node);
            // 复制节点的next = 原节点的next对应的复制节点
            cloneNode.next = originCloneIndexMap.get(node.next);
            if (cloneNode.rand != null) {
                cloneNode.rand = originCloneIndexMap.get(node.rand);
            }
        }
        // 返回头节点
        return originCloneIndexMap.get(header);
    }

    /**
     * 复制链表
     * 在原链表上操作
     */
    public static Node copy2(Node header) {
        if (header == null) {
            return null;
        }

        /*
         * 原链表
         *    r↑------↓
         * ①→②→③→④→⑤
         *     ↑--↓r
         *
         * 复制和插入之后
         *       r↑-------------↓
         * ①→❶→②→❷→③→❸→④→❹→⑤→❺
         *        ↑------↓r
         */
        for (Node node = header; node != null; ) {
            Node oldNext = node.next;
            // 复制节点
            Node cloneNode = new Node(node.value);
            // 加复制节点插入到节点后面
            node.next = cloneNode;
            cloneNode.next = oldNext;
            // 原链表的下一个节点
            node = oldNext;
        }

        // 复制原链表的rand到新链表
        for (Node node = header; node != null; ) {
            // 复制节点
            Node cloneNode = node.next;
            // 复制节点的rand = 原节点的rand的next
            cloneNode.rand = (node.rand == null) ? null : node.rand.next;
            node = cloneNode.next;
        }

        // 复制链表的头节点
        Node cloneHeader = header.next;
        // 将复制的链表剥离出来
        for (Node currentOriginNode = header; currentOriginNode != null; ) {
            // 复制节点
            Node cloneNode = currentOriginNode.next;
            // 下一个原节点
            Node nextOriginNode = currentOriginNode.next.next;
            // 当前原节点指向下一个原节点
            currentOriginNode.next = nextOriginNode;

            // 复制节点的next指向下一个复制节点
            cloneNode.next = (nextOriginNode == null) ? null : nextOriginNode.next;

            // 操作下一个原节点去
            currentOriginNode = nextOriginNode;
        }
        return cloneHeader;
    }

    /* ************************************************************************************************************** */

    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        node1.next = node2;
        node1.rand = null;
        node2.next = node3;
        node2.rand = node4;
        node3.next = node4;
        node3.rand = node2;

        for (Node node = node1; node != null; node = node.next) {
            System.out.println("value: " + node.value);
            if (node.next != null) {
                System.out.println("next: " + node.next.value);
            }
            if (node.rand != null) {
                System.out.println("rand: " + node.rand.value);
            }
            System.out.println("===========================0");
        }

        // 复制
        Node cloneHeader = copy1(node1);
        System.out.println("**********************************************");

        for (Node node = cloneHeader; node != null; node = node.next) {
            System.out.println("value: " + node.value);
            if (node.next != null) {
                System.out.println("next: " + node.next.value);
            }
            if (node.rand != null) {
                System.out.println("rand: " + node.rand.value);
            }
            System.out.println("===========================1");
        }

        cloneHeader = copy1(node1);
        System.out.println("**********************************************");

        for (Node node = cloneHeader; node != null; node = node.next) {
            System.out.println("value: " + node.value);
            if (node.next != null) {
                System.out.println("next: " + node.next.value);
            }
            if (node.rand != null) {
                System.out.println("rand: " + node.rand.value);
            }
            System.out.println("===========================2");
        }
    }

    /* ************************************************************************************************************** */

    private static class Node {
        int value;
        Node next;
        Node rand;

        Node(int val) {
            value = val;
        }
    }
}
