package com.lg.leetcode.topinterview.practice1;

/**
 * @author Xulg
 * Created in 2021-02-26 9:56
 */
class RemoveNthNodeFromEndOfList {

    /*
     * Given the head of a linked list, remove the nth node from the end of the
     * list and return its head.
     *
     * Follow up: Could you do this in one pass?
     *
     * Example 1:
     * Input: head = [1,2,3,4,5], n = 2
     * Output: [1,2,3,5]
     *
     * Example 2:
     * Input: head = [1], n = 1
     * Output: []
     *
     * Example 3:
     * Input: head = [1,2], n = 1
     * Output: [1]
     *
     * Definition for singly-linked list.
     * public class ListNode {
     *     int val;
     *     ListNode next;
     *     ListNode() {}
     *     ListNode(int val) { this.val = val; }
     *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     * }
     *---------------------------------------------------------------------------
     * 删除链表的倒数第n个节点
     */

    // 空间还不是最优
    public static ListNode removeNthFromEnd(ListNode head, int n) {
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
        {
            ListNode head1 = new ListNode(1);
            ListNode head2 = new ListNode(2);
            ListNode head3 = new ListNode(3);
            ListNode head4 = new ListNode(4);
            ListNode head5 = new ListNode(5);
            head1.next = head2;
            head2.next = head3;
            head3.next = head4;
            head4.next = head5;
            head5.next = null;
            ListNode head = removeNthFromEnd(head1, 2);
            System.out.println(head);
        }
    }
}
