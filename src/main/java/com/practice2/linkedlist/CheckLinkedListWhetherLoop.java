package com.practice2.linkedlist;

import java.util.HashSet;

/**
 * @author Xulg
 * @since 2021-10-19 16:57
 * Description: 检验是否是循环链表
 */
@SuppressWarnings("MapOrSetKeyShouldOverrideHashCodeEquals")
class CheckLinkedListWhetherLoop {

    private static class CheckIsCircleLoop1 {
        public static boolean isLoop(Node head) {
            if (head == null || head.next == null || head.next.next == null) {
                return false;
            }
            HashSet<Node> existNodes = new HashSet<>(); ;
            for (Node node = head; node != null; node = node.next) {
                if (existNodes.contains(node)) {
                    // 已经出现过的节点了，出现了环
                    return true;
                } else {
                    existNodes.add(node);
                }
            }
            return false;
        }
    }

    private static class CheckIsCircleLoop2 {
        public static boolean isLoop(Node head) {
            if (head == null || head.next == null || head.next.next == null) {
                return false;
            }
            Node faster = head.next.next;
            Node slower = head.next;
            while (faster != slower) {
                if (faster.next == null || faster.next.next == null) {
                    // 链表都结束了，肯定没有环
                    return false;
                }
                faster = faster.next.next;
                slower = slower.next;
            }
            // 快慢指针相遇，说明有环
            return false;
        }
    }

    private static class FindCircleLoopNode1 {
        public static Node findLoopNode(Node head) {
            if (head == null || head.next == null || head.next.next == null) {
                return null;
            }
            HashSet<Node> existNodes = new HashSet<>();
            for (Node node = head; node != null; node = node.next) {
                if (existNodes.contains(node)) {
                    // 入环点
                    return node;
                } else {
                    existNodes.add(node);
                }
            }
            return null;
        }
    }

    private static class FindCircleLoopNode2 {
        public static Node findLoopNode(Node head) {
            if (head == null || head.next == null) {
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
    }

    private static class Node {
        private int val;
        private Node next;
    }

}
