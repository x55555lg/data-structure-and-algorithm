package com.lg.leetcode.top100likedquestions.practice;

import java.util.HashSet;

/**
 * @author Xulg
 * Created in 2021-03-10 17:53
 */
@SuppressWarnings({"AlibabaClassNamingShouldBeCamel", "MapOrSetKeyShouldOverrideHashCodeEquals"})
class LinkedListCycleII {

    /*
     * Given a linked list, return the node where the cycle begins. If there is no cycle, return null.
     * There is a cycle in a linked list if there is some node in the list that can be reached again by continuously following the next pointer. Internally, pos is used to denote the index of the node that tail's next pointer is connected to. Note that pos is not passed as a parameter.
     * Notice that you should not modify the linked list.
     *
     * Example 1:
     * Input: head = [3,2,0,-4], pos = 1
     * Output: tail connects to node index 1
     * Explanation: There is a cycle in the linked list, where tail connects to the second node.
     *
     * Example 2:
     * Input: head = [1,2], pos = 0
     * Output: tail connects to node index 0
     * Explanation: There is a cycle in the linked list, where tail connects to the first node.
     *
     * Example 3:
     * Input: head = [1], pos = -1
     * Output: no cycle
     * Explanation: There is no cycle in the linked list.
     *
     * Constraints:
     *  The number of the nodes in the list is in the range [0, 104].
     *  -10^5 <= Node.val <= 10^5
     *  pos is -1 or a valid index in the linked-list.
     *
     * Follow up: Can you solve it using O(1) (i.e. constant) memory?
     *
     * Definition for singly-linked list.
     * class ListNode {
     *     int val;
     *     ListNode next;
     *     ListNode(int x) {
     *         val = x;
     *         next = null;
     *     }
     * }
     */

    public static ListNode detectCycle(ListNode head) {
        return BestSolution.detectCycle(head);
    }

    /**
     * 快慢指针
     * 时间复杂度O(N)
     * 空间复杂度O(1)
     */
    private static class BestSolution {

        public static ListNode detectCycle(ListNode header) {
            if (header == null || header.next == null || header.next.next == null) {
                // 0节点，1节点，2节点 不可能有环
                return null;
            }

            // 快指针
            ListNode faster = header.next.next;
            // 慢指针
            ListNode slower = header.next;

            // 快慢指针一起移动
            while (faster != slower) {
                if (faster.next == null || faster.next.next == null) {
                    // 快指针已经走完了，没有环
                    return null;
                }
                faster = faster.next.next;
                slower = slower.next;
            }

            faster = header;
            while (faster != slower) {
                faster = faster.next;
                slower = slower.next;
            }
            return faster;
        }

    }

    /**
     * HashSet集合
     * 时间复杂度O(N)
     * 空间复杂度O(N)
     */
    private static class EasySolution {

        public static ListNode detectCycle(ListNode header) {
            if (header == null || header.next == null || header.next.next == null) {
                // 0个节点，1个节点，2个节点都是不可能形成环的
                return null;
            }
            // 循环链表往set集合加入节点，如果有环链表的话，一定会遇到节点重复
            HashSet<ListNode> set = new HashSet<>();
            for (ListNode node = header; node != null; node = node.next) {
                if (set.contains(node)) {
                    return node;
                } else {
                    set.add(node);
                }
            }
            return null;
        }

    }

    private static class ListNode {
        int val;
        ListNode next;
    }
}
