package com.lg.leetcode.topinterview.practice;

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
        return Solution.mergeKLists(lists);
    }

    /**
     * 归并排序的思想
     */
    private static class Solution {

        public static ListNode mergeKLists(ListNode[] lists) {
            if (lists == null || lists.length == 0) {
                return null;
            }
            return recurse(lists, 0, lists.length - 1);
        }

        private static ListNode recurse(ListNode[] arr, int left, int right) {
            // left = 0, right = 3;
            //                        recurse(0, 3)
            //       recurse(0, 1)                      recurse(2, 3)
            // recurse(0, 0) recurse(1, 1)      recurse(2, 2) recurse(3, 3)
            if (left == right) {
                return arr[left];
            } else if (right - left == 1) {
                return merge(arr[left], arr[right]);
            } else {
                int mid = left + ((right - left) >> 1);
                ListNode leftHead = recurse(arr, left, mid);
                ListNode rightHead = recurse(arr, mid + 1, right);
                return merge(leftHead, rightHead);
            }
        }

        private static ListNode merge(ListNode head1, ListNode head2) {
            if (head1 == null && head2 == null) {
                return null;
            }
            if (head1 == null) {
                return head2;
            }
            if (head2 == null) {
                return head1;
            }
            // head1 != null && head2 != null;
            ListNode header = null;
            ListNode tail = null;
            while (head1 != null && head2 != null) {
                if (head1.val <= head2.val) {
                    if (header == null) {
                        header = head1;
                        tail = header;
                    } else {
                        tail.next = head1;
                        tail = tail.next;
                    }
                    // 左边下一个
                    head1 = head1.next;
                } else {
                    if (header == null) {
                        header = head2;
                        tail = header;
                    } else {
                        tail.next = head2;
                        tail = tail.next;
                    }
                    // 右边下一个
                    head2 = head2.next;
                }
            }
            while (head1 != null) {
                tail.next = head1;
                tail = tail.next;
                head1 = head1.next;
            }
            while (head2 != null) {
                tail.next = head2;
                tail = tail.next;
                head2 = head2.next;
            }
            // 防止链表循环
            tail.next = null;
            return header;
        }
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

        ListNode listNode = Solution.mergeKLists(arr);
        System.out.println(listNode);
    }

}
