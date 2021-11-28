package com.lg.leetcode.other.practice1;

/**
 * @author Xulg
 * Description: leetcode_92
 * Created in 2021-06-01 14:05
 */
class ReverseLinkedListII {

    /*
     *  Given the head of a singly linked list and two integers left and right where left <= right,
     * reverse the nodes of the list from position left to position right, and return the reversed
     * list.
     *
     * Example 1:
     * Input: head = [1,2,3,4,5], left = 2, right = 4
     * Output: [1,4,3,2,5]
     *
     * Example 2:
     * Input: head = [5], left = 1, right = 1
     * Output: [5]
     *
     * Constraints:
     * The number of nodes in the list is n.
     * 1 <= n <= 500
     * -500 <= Node.val <= 500
     * 1 <= left <= right <= n
     *
     * Follow up: Could you do it in one pass?
     */

    public static ListNode reverseBetween(ListNode head, int left, int right) {
        return null;
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) { this.val = val; }
    }
}