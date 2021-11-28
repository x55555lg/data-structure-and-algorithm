package com.lg.leetcode.topinterview.practice1;

/**
 * @author Xulg
 * Created in 2021-03-01 16:20
 */
@SuppressWarnings({"unused", "DuplicatedCode", "AlibabaLowerCamelCaseVariableNaming"})
class MergeKSortedLists {

    /*
     * You are given an array of k linked-lists lists, each linked-list is sorted in ascending order.
     * Merge all the linked-lists into one sorted linked-list and return it.
     *
     * Example 1:
     * Input: lists = [[1,4,5],[1,3,4],[2,6]]
     * Output: [1,1,2,3,4,4,5,6]
     * Explanation: The linked-lists are:
     * [
     *   1->4->5,
     *   1->3->4,
     *   2->6
     * ]
     * merging them into one sorted list:
     * 1->1->2->3->4->4->5->6
     *
     * Example 2:
     * Input: lists = []
     * Output: []
     *
     * Example 3:
     * Input: lists = [[]]
     * Output: []
     *
     * Constraints:
     *  k == lists.length
     *  0 <= k <= 10^4
     *  0 <= lists[i].length <= 500
     *  -10^4 <= lists[i][j] <= 10^4
     *  lists[i] is sorted in ascending order.
     *  The sum of lists[i].length won't exceed 10^4.
     *
     * Definition for singly-linked list.
     * public class ListNode {
     *     int val;
     *     ListNode next;
     *     ListNode() {}
     *     ListNode(int val) { this.val = val; }
     *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     * }
     *-----------------------------------------------------------------------------
     * 将k个有序的链表合并为1个有序链表
     */

    public static ListNode mergeKLists(ListNode[] lists) {
        // lists = { {1,2,3}, {1,2,3}, {1,2,3}, {1,2,3} }
        // {1,2,3}    {1,2,3}    {1,2,3}    {1,2,3}
        //    {1,2,3,1,2,3}          {1,2,3,1,2,3}
        //          {1,2,3,1,2,3,1,2,3,1,2,3}
        return null;
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }

    public static void main(String[] args) {
        // [[1,4,5],[1,3,4],[2,6]]
        ListNode[] arr = new ListNode[3];

        ListNode head1 = new ListNode(1);
        ListNode node11 = new ListNode(4);
        ListNode node12 = new ListNode(5);
        head1.next = node11;
        node11.next = node12;
        node12.next = null;

        ListNode head2 = new ListNode(1);
        ListNode node21 = new ListNode(3);
        ListNode node22 = new ListNode(4);
        head2.next = node21;
        node21.next = node22;
        node22.next = null;

        ListNode head3 = new ListNode(1);
        ListNode node31 = new ListNode(3);
        head3.next = node31;
        node31.next = null;

        arr[0] = head1;
        arr[1] = head2;
        arr[2] = head3;
    }

}
