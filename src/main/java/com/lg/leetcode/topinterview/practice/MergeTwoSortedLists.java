package com.lg.leetcode.topinterview.practice;

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

        mergeTwoLists(head1, head2);
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }

    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null && l2 == null) {
            return null;
        }
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        // l1 != null && l2 != null;
        ListNode left = l1, right = l2;
        ListNode header = null;
        ListNode tail = null;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                if (header == null) {
                    header = l1;
                    tail = header;
                } else {
                    tail.next = l1;
                    tail = tail.next;
                }
                // 左边下一个
                l1 = l1.next;
            } else {
                if (header == null) {
                    header = l2;
                    tail = header;
                } else {
                    tail.next = l2;
                    tail = tail.next;
                }
                // 右边下一个
                l2 = l2.next;
            }
        }
        while (l1 != null) {
            tail.next = l1;
            tail = tail.next;
            l1 = l1.next;
        }
        while (l2 != null) {
            tail.next = l2;
            tail = tail.next;
            l2 = l2.next;
        }
        // 防止链表循环
        tail.next = null;
        return header;
    }

}
