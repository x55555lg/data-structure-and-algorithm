package com.lg.datastructure.linkedlist.a1.practice;

import java.util.HashSet;

/**
 * 验证一个链表是否是循环链表
 *
 * @author Xulg
 * Created in 2021-01-22 11:29
 */
@SuppressWarnings("MapOrSetKeyShouldOverrideHashCodeEquals")
class CheckLinkedListWhetherLoop {

    /*
     * 给定一个单向链表的头节点，如何判断一个链表是否有环？
     *  循环链表
     *  		    ○←○
     *              ↓  ↑
     *      ○→○→○→○
     *           S   F
     */

    /**
     * 判断是否是有环链，返回入环点
     * 时间复杂度O(n)
     * 空间复杂度O(n)
     *
     * @param header the linked list header
     * @return the loop node
     */
    public static Node isLoopBySet(Node header) {
        if (header == null || header.next == null || header.next.next == null) {
            // 0个节点，1个节点，2个节点都是不可能形成环的
            return null;
        }
        // 循环链表往set集合加入节点，如果有环链表的话，一定会遇到节点重复
        HashSet<Node> nodes = new HashSet<>();
        for (Node node = header; node != null; node = node.next) {
            if (nodes.contains(node)) {
                // 返回这个入环点
                return node;
            }
            nodes.add(node);
        }
        return null;
    }

    /**
     * 判断是否是有环链，返回入环点
     * 时间复杂度O(n)
     * 空间复杂度O(1)
     *
     * @param header the linked list header
     * @return the loop node
     */
    public static Node isLoopBySlowerFasterPoint(Node header) {
        if (header == null || header.next == null || header.next.next == null) {
            // 0个节点，1个节点，2个节点都是不可能形成环的
            return null;
        }

        // 慢指针(步长1)
        Node slower = header.next;
        // 快指针(步长2)
        Node faster = header.next.next;
        // 如果是有环链表，那么快慢指针一定会相遇的(这里的相遇点不一定是入环点)
        while (slower != faster) {
            if (faster.next == null || faster.next.next == null) {
                // 快指针走完了，后面没有节点了，说明肯定不是环
                return null;
            }
            slower = slower.next;
            faster = faster.next.next;
        }

        // 快指针回到header位置，满指针不边，两个指针都按照步长1移动
        // 如果快慢指针相遇，那么相遇点就是入环点
        faster = header;
        while (faster != slower) {
            slower = slower.next;
            faster = faster.next;
        }
        // faster和slower快慢指针相遇了，那么相遇点就是入环点
        return faster;
    }

    private static class Node {
        int value;
        Node next;
    }
}
