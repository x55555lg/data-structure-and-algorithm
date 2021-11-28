package com.lg.leetcode.topinterview.practice1;

/**
 * @author Xulg
 * Created in 2021-02-22 11:14
 */
class LinkedListCycle {

    /*
     * Given head, the head of a linked list, determine if the linked list has a cycle in it.
     * There is a cycle in a linked list if there is some node in the list that can be reached
     * again by continuously following the next pointer. Internally, pos is used to denote the
     * index of the node that tail's next pointer is connected to. Note that pos is not passed
     * as a parameter.
     * Return true if there is a cycle in the linked list. Otherwise, return false.
     *
     * Constraints:
     * The number of the nodes in the list is in the range [0, 104].
     * -105 <= Node.val <= 105
     * pos is -1 or a valid index in the linked-list.
     *
     * Definition for singly-linked list.
     * class ListNode {
     *     int val;
     *     ListNode next;
     *     ListNode(int x) {
     *         val = x;
     *         next = null;
     *     }
     * }
     *-----------------------------------------------------------------------------------------
     * 判断链表是否有环
     */

    public static boolean hasCycle(ListNode head) {
        return false;
    }

    private static class ListNode {
        int val;
        ListNode next;
    }

}
