package com.lg.leetcode.topinterview.practice1;

/**
 * @author Xulg
 * Created in 2021-02-24 17:43
 */
class OddEvenLinkedList {

    /*
     *  Given a singly linked list, group all odd nodes together followed by the even nodes.
     * Please note here we are talking about the node number and not the value in the nodes.
     *  You should try to do it in place. The program should run in O(1) space complexity and
     * O(nodes) time complexity.
     *
     * Example 1:
     * Input: 1->2->3->4->5->NULL
     * Output: 1->3->5->2->4->NULL
     *
     * Example 2:
     * Input: 2->1->3->5->6->4->7->NULL
     * Output: 2->3->6->7->1->5->4->NULL
     *
     * Constraints:
     *  The relative order inside both the even and odd groups should remain as it was in the input.
     *  The first node is considered odd, the second node even and so on ...
     *  The length of the linked list is between [0, 10^4].
     *
     * Definition for singly-linked list.
     * public class ListNode {
     *     int val;
     *     ListNode next;
     *     ListNode() {}
     *     ListNode(int val) { this.val = val; }
     *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     * }
     */

    /**
     * 时间复杂度O(N)
     * 空间复杂度O(1)
     */
    public static ListNode oddEvenList(ListNode head) {
        return null;
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return "ListNode{" + "val=" + val + '}';
        }
    }

    public static void main(String[] args) {
    }
}
