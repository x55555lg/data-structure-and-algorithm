package com.lg.leetcode.other.practice;

/**
 * @author Xulg
 * Description: leetcode_2
 * Created in 2021-06-01 13:11
 */
class AddTwoNumbers {

    /*
     *  You are given two non-empty linked lists representing two non-negative integers.
     * The digits are stored in reverse order, and each of their nodes contains a single
     * digit. Add the two numbers and return the sum as a linked list.
     *  You may assume the two numbers do not contain any leading zero, except the number
     * 0 itself.
     *
     * Example 1:
     * Input: l1 = [2,4,3], l2 = [5,6,4]
     * Output: [7,0,8]
     * Explanation: 342 + 465 = 807.
     *
     * Example 2:
     * Input: l1 = [0], l2 = [0]
     * Output: [0]
     * Example 3:
     * Input: l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
     * Output: [8,9,9,9,0,0,0,1]
     *
     * Constraints:
     * The number of nodes in each linked list is in the range [1, 100].
     * 0 <= Node.val <= 9
     * It is guaranteed that the list represents a number that does not have leading zeros.
     */

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) { this.val = val; }
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        return null;
    }

    /**
     * // TODO 2021/6/1 BUG
     */
    private static class BruteForce {
        public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            assert l1 != null && l2 != null;
            StringBuilder sb1 = new StringBuilder();
            for (ListNode node = l1; node != null; node = node.next) {
                sb1.append(node.val);
            }
            StringBuilder sb2 = new StringBuilder();
            for (ListNode node = l2; node != null; node = node.next) {
                sb2.append(node.val);
            }
            String sum = String.valueOf(Integer.parseInt(sb1.toString()) + Integer.parseInt(sb2.toString()));
            ListNode sumHead = new ListNode(Integer.parseInt(String.valueOf(sum.charAt(sum.length() - 1))));
            ListNode temp = sumHead;
            for (int idx = sum.length() - 2; idx >= 0; idx--) {
                ListNode node = new ListNode(Integer.parseInt(String.valueOf(sum.charAt(idx))));
                temp.next = node;
                temp = node;
            }
            return sumHead;
        }
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(2);
        l1.next = new ListNode(4);
        l1.next.next = new ListNode(3);

        ListNode l2 = new ListNode(5);
        l2.next = new ListNode(6);
        l2.next.next = new ListNode(4);

        ListNode sumNode = BruteForce.addTwoNumbers(l1, l2);
        System.out.println(sumNode);
    }
}
