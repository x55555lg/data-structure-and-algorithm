package com.practice.linkedlist;

import java.util.HashSet;

/**
 * 检测链表是否有环
 *
 * @author Xulg
 * Created in 2021-05-11 11:22
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

    private static class CheckByHashSet {
        public static Node isCircle(Node head) {
            if (head == null || head.next == null || head.next.next == null) {
                // 0节点 1节点 2节点
                return null;
            }
            HashSet<Node> records = new HashSet<>();
            for (Node node = head; node != null; node = node.next) {
                if (records.contains(node)) {
                    // 找到入环点
                    return node;
                }
                records.add(node);
            }
            return null;
        }
    }

    private static class CheckBySlowFasterPointer {
        public static Node isCircle(Node head) {
            if (head == null || head.next == null || head.next.next == null) {
                // 0节点 1节点 2节点
                return null;
            }

            /*
             *  		    ○←○
             *              ↓  ↑
             *      ○→○→○→○
             *          S   F
             */

            Node slow = head.next;
            Node faster = head.next.next;
            while (slow != faster) {
                if (faster.next == null || faster.next.next == null) {
                    // 快指针跑完了，没有环
                    return null;
                }
                slow = slow.next;
                faster = faster.next.next;
            }

            // 此时快慢指针已经相遇，链表一定有环，找出入环点

            /*
             *  		    ○←○ FS，快慢指针在这里相遇
             *              ↓  ↑
             *      ○→○→○→○
             *
             *  		    ○←○ S
             *              ↓  ↑
             *      ○→○→○→○
             *      F
             *      快指针从头开始，慢指针不变，再次相遇的点就是入环点
             *
             *  		    ○←○
             *              ↓  ↑
             *      ○→○→○→○
             *              FS
             */

            faster = head;
            while (faster != slow) {
                slow = slow.next;
                faster = faster.next;
            }
            return faster;
        }
    }

    private static class Node {
        int value;
        Node next;
    }
}
