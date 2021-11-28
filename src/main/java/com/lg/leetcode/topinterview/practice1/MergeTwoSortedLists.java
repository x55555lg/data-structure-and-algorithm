package com.lg.leetcode.topinterview.practice1;

/**
 * @author Xulg
 * Created in 2021-03-01 10:58
 */
class MergeTwoSortedLists {

    /*
     * Merge two sorted linked lists and return it as a sorted list.
     * The list should be made by splicing together the nodes of the first two lists.
     *
     * Example 1:
     *      1 -> 2 -> 4
     *      1 -> 3 -> 4
     * Input: l1 = [1,2,4], l2 = [1,3,4]
     * Output: [1,1,2,3,4,4]
     *
     * Example 2:
     * Input: l1 = [], l2 = []
     * Output: []
     *
     * Example 3:
     * Input: l1 = [], l2 = [0]
     * Output: [0]
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

    public static void main(String[] args) {
        ListNode head1 = new ListNode(1);
        ListNode node11 = new ListNode(2);
        ListNode node12 = new ListNode(4);
        head1.next = node11;
        node11.next = node12;
        node12.next = null;

        ListNode head2 = new ListNode(1);
        ListNode node21 = new ListNode(3);
        ListNode node22 = new ListNode(4);
        head2.next = node21;
        node21.next = node22;
        node22.next = null;
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }
}
