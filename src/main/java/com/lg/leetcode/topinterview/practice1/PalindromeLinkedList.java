package com.lg.leetcode.topinterview.practice1;

/**
 * @author Xulg
 * Created in 2021-02-24 15:09
 */
class PalindromeLinkedList {

    /*
     * Given a singly linked list, determine if it is a palindrome.
     *
     * Example 1:
     * Input: 1->2
     * Output: false
     *
     * Example 2:
     * Input: 1->2->2->1
     * Output: true
     *
     * Follow up:
     *  Could you do it in O(n) time and O(1) space?
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

    public static boolean isPalindrome(ListNode head) {
        return false;
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
        {
            ListNode head1 = new ListNode(1);
            ListNode head2 = new ListNode(2);
            ListNode head3 = new ListNode(3);
            ListNode head4 = new ListNode(4);
            head1.next = head2;
            head2.next = head3;
            head3.next = head4;
            head4.next = null;
        }

        {
            ListNode head1 = new ListNode(1);
            ListNode head2 = new ListNode(2);
            ListNode head3 = new ListNode(3);
            head1.next = head2;
            head2.next = head3;
            head3.next = null;
        }

        {
            ListNode head1 = new ListNode(1);
            ListNode head2 = new ListNode(2);
            head1.next = head2;
            head2.next = null;
        }

        {
            ListNode head1 = new ListNode(1);
            ListNode head2 = new ListNode(0);
            ListNode head3 = new ListNode(1);
            head1.next = head2;
            head2.next = head3;
            head3.next = null;
        }
    }

}
