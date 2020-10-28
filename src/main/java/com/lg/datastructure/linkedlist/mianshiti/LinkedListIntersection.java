package com.lg.datastructure.linkedlist.mianshiti;

/**
 * 求两个单向链表(链表可能有环可能没有)的交点
 *
 * @author Xulg
 * Created in 2020-10-27 19:00
 */
class LinkedListIntersection {

    /*
     * 给定两个可能有环也可能没环的单链表，头节点为header1和header2。
     * 请实现一个函数：
     *  如果两个链表相交，请返回相交的第一个节点；
     *  如果不想交，请返回null
     * 要求：
     *      如果两个链表的长度之和为N，时间复杂度请达到O(N)，额外空间复杂度请达到O(1)
     */

    /**
     * 获取两个(可能有环也可能没环)链表的交点
     */
    public static Node getIntersection(Node header1, Node header2) {
        if (header1 == null || header2 == null) {
            return null;
        }
        // 分别获取两个链表的入环节点
        Node loopNode1 = isLoopLinkedList(header1);
        Node loopNode2 = isLoopLinkedList(header2);

        // 判断两个链表的入环状况
        if (loopNode1 != null && loopNode2 != null) {
            // 两个链表都是循环链表，绝对不可能出现交点
            return null;
        } else if (loopNode1 == null && loopNode2 == null) {
            /*
             * 两个链表都不是循环链表，可能会有交点
             * 只会有一种情形，两个链表会有公共部分
             *-----------------------------------
             * 链表1
             * ○→○→○→○→●→○→○
             *                 ↑
             * 链表2   ○→○→○
             *
             * ●就是交点
             *-----------------------------------
             */
            return getBothNotLoopIntersection(header1, header2);
        } else {
            /*
             * 其中一个链表是循环链表，可能会有交点
             * 只会有一种情形，链表1，链表2的入环点就是交点，所以求得一个入环点就可以了
             *-----------------------------------
             * 链表1                  链表2
             * ○→○→●←○←●←○←○
             * 	       ↓	   ↑
             * 	       ○→○→○
             * ●即是入环点又是交点
             *-----------------------------------
             */
            // 返回链表1的入环点或者链表2的入环点
            return loopNode1;
        }
    }

    private static Node getBothNotLoopIntersectionLow(Node header1, Node header2) {
        // 使用HashSet实现
        // TODO 2020/10/27 ......
        return null;
    }

    /**
     * 求两个(都不是循环链表)链表的交点
     */
    private static Node getBothNotLoopIntersection(Node header1, Node header2) {
        // TODO 2020/10/27 ......
        return null;
    }

    /**
     * 使用快慢指针判断是否是循环链表
     * 如果是循环链表，则返回第一个入环节点
     * 否则返回null
     */
    @SuppressWarnings({"unused", "DuplicatedCode"})
    private static Node isLoopLinkedList(Node header) {
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
        int value;
        Node next;

        Node(int val) {
            value = val;
        }
    }
}
