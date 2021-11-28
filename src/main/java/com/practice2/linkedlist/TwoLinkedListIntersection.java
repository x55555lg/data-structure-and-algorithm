package com.practice2.linkedlist;

import java.util.HashSet;

/**
 * @author Xulg
 * @since 2021-10-25 9:45
 * Description: 2个链表交点
 */
class TwoLinkedListIntersection {

    /*
     * 给定两个可能有环也可能没环的单链表，头节点为header1和header2。
     * 请实现一个函数：
     *  如果两个链表相交，请返回相交的第一个节点；
     *  如果不想交，请返回null
     * 要求：
     *      如果两个链表的长度之和为N，时间复杂度请达到O(N)，额外空间复杂度请达到O(1)
     */

    private static class Solution {
        public static Node findIntersection(Node head1, Node head2) {
            if (head1 == null || head2 == null) {
                return null;
            }
            Node circleLoopNode1 = getCircleLoopNode(head1);
            Node circleLoopNode2 = getCircleLoopNode(head2);
            if (circleLoopNode1 == null && circleLoopNode2 == null) {
                return bothNotCircle(head1, head2);
            } else if (circleLoopNode1 != null && circleLoopNode2 != null) {
                return bothCircle(head1, head2, circleLoopNode1, circleLoopNode2);
            } else {
                // 一个有环，一个无环
                return null;
            }
        }

        private static Node bothCircle(Node head1, Node head2, Node circleLoopNode1, Node circleLoopNode2) {
            if (circleLoopNode1 == circleLoopNode2) {
                // 2个链表共环，入环点是同一个
                HashSet<Node> existNodes = new HashSet<>();
                for (Node node = head1; node != circleLoopNode1; node = node.next) {
                    existNodes.add(node);
                }
                for (Node node = head2; node != circleLoopNode1; node = node.next) {
                    if (existNodes.contains(node)) {
                        return node;
                    }
                }
                return null;
            } else {
                Node node = circleLoopNode1.next;
                while (node != circleLoopNode1) {
                    if (node == circleLoopNode2) {
                        // 两个链表有公共的环
                        return circleLoopNode1;
                    }
                    node = node.next;
                }
                // 两个链表没有公共的环，没有交点
                return null;
            }
        }

        private static Node getCircleLoopNode(Node head) {
            if (head == null || head.next == null || head.next.next == null) {
                return null;
            }
            Node faster = head.next.next;
            Node slower = head.next;
            while (faster != slower) {
                if (faster.next == null || faster.next.next == null) {
                    return null;
                }
                faster = faster.next.next;
                slower = slower.next;
            }
            faster = head;
            while (faster != slower) {
                faster = faster.next;
                slower = slower.next;
            }
            return faster;
        }

        private static Node bothNotCircle(Node head1, Node head2) {
            /*
             * 两个链表一定有公共部分才会出现交点
             * A链表
             * ○→○→○→○→●→○→○
             * 		           ↑
             * 		           ○
             * 		           ↑
             * 		           ○
             *                 B链表
             */
            HashSet<Node> existNodes = new HashSet<>();
            for (Node node = head1; node != null; node = node.next) {
                existNodes.add(node);
            }
            for (Node node = head2; node != null; node = node.next) {
                if (existNodes.contains(node)) {
                    return node;
                }
            }
            return null;
        }
    }

    private static class Node {
        int val;
        Node next;

        Node(int val) { this.val = val; }
    }
}
