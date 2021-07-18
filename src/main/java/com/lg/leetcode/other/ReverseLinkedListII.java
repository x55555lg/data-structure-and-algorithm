package com.lg.leetcode.other;

import java.util.LinkedList;
import java.util.List;

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

    private static class BruteForce {
        public static ListNode reverseBetween(ListNode head, int left, int right) {
            if (head == null || head.next == null || left == right) {
                return head;
            }
            List<ListNode> list = new LinkedList<>();
            for (ListNode node = head; node != null; node = node.next) {
                list.add(node);
            }
            int low = left - 1, high = right - 1;
            while (low < high) {
                swap(list, low++, high--);
            }
            ListNode temp = list.get(0);
            for (ListNode node : list) {
                temp.next = node;
                temp = node;
            }
            temp.next = null;
            return list.get(0);
        }

        private static void swap(List<ListNode> list, int i, int j) {
            ListNode temp = list.get(i);
            list.set(i, list.get(j));
            list.set(j, temp);
        }
    }

    private static class AAAA {
        public ListNode reverseBetween(ListNode head, int left, int right) {
            ListNode dummy = new ListNode(0);
            dummy.next = head;
            ListNode pre = dummy; //pre is the node before original M
            ListNode M = head;    //M is after pre

            for (int i = 1; i < left; i++) { //Move pre and M to original place
                pre = pre.next;
                M = M.next;
            }

            for (int i = 0; i < right - left; i++) {
                ListNode current = M.next; //Both pre and M are all fixed, only current is assigned every time to M.next. M is pushed back everytime
                M.next = current.next;     //Move current to the position after pre
                current.next = pre.next;
                pre.next = current;
            }

            return dummy.next;
        }
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) { this.val = val; }
    }
}