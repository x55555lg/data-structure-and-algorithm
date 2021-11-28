package com.lg.leetcode.other.practice1;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xulg
 * Description: leetcode_147
 * Created in 2021-05-28 17:13
 */
class InsertionSortList {
    /*
     *  Given the head of a singly linked list, sort the list using insertion sort,
     * and return the sorted list's head.
     *  The steps of the insertion sort algorithm:
     *  Insertion sort iterates, consuming one input element each repetition and growing
     * a sorted output list.
     *  At each iteration, insertion sort removes one element from the input data, finds
     * the location it belongs within the sorted list and inserts it there.
     *  It repeats until no input elements remain.
     *  The following is a graphical example of the insertion sort algorithm. The partially
     * sorted list (black) initially contains only the first element in the list. One element
     * (red) is removed from the input data and inserted in-place into the sorted list with each
     * iteration.
     *
     * Example 1:
     * Input: head = [4,2,1,3]
     * Output: [1,2,3,4]
     *
     * Example 2:
     * Input: head = [-1,5,3,4,0]
     * Output: [-1,0,3,4,5]
     *
     * Constraints:
     * The number of nodes in the list is in the range [1, 5000].
     * -5000 <= Node.val <= 5000
     */

    public ListNode insertionSortList(ListNode head) {
        return null;
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) { this.val = val; }

        @Override
        public String toString() {
            return "ListNode{" + "val=" + val + '}';
        }
    }

    public static void main(String[] args) {
        ListNode n0 = new ListNode(6);
        ListNode n1 = new ListNode(5);
        ListNode n2 = new ListNode(4);
        ListNode n3 = new ListNode(3);
        ListNode n4 = new ListNode(2);
        ListNode n5 = new ListNode(1);
        n0.next = n1;
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        n5.next = null;
    }

}
