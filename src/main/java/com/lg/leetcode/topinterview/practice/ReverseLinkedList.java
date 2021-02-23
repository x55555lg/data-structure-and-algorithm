package com.lg.leetcode.topinterview.practice;

/**
 * @author Xulg
 * Created in 2021-02-22 11:03
 */
class ReverseLinkedList {

    /*
     * Given the head of a singly linked list, reverse the list, and return the reversed list.
     * Constraints:
     *
     * The number of nodes in the list is the range [0, 5000].
     * -5000 <= Node.val <= 5000
     *
     * Follow up: A linked list can be reversed either iteratively or recursively. Could you implement both?
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

    public static ListNode reverseList(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode prev = null;
        ListNode next = null;
        while (head != null) {
            next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }
        // the new linked list header
        return prev;
    }

    private static class ListNode {
        int val;
        ListNode next;
    }

}
