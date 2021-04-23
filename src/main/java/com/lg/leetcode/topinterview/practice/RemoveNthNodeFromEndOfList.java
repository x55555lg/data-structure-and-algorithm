package com.lg.leetcode.topinterview.practice;

import java.util.HashMap;

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
        if (head == null) {
            return null;
        }
        if (head.next == null && n == 1) {
            return null;
        }

        /*
         * ①->②->③->④->⑤
         * 1    ①
         * 2    ②
         * 3    ③
         * 4    ④
         * 5    ⑤
         */

        // node position index map
        HashMap<Integer, ListNode> nodePositionMap = new HashMap<>(16);
        // head is the first position
        nodePositionMap.put(1, head);

        int length = 1;
        for (ListNode node = head.next; node != null; node = node.next) {
            nodePositionMap.put(++length, node);
        }

        // get the kth node from end
        int position = length - n + 1;
        ListNode deletedNode = nodePositionMap.get(position);

        if (position == 1) {
            head = deletedNode.next;
        } else {
            // get the node before the delete node by position
            nodePositionMap.get(position - 1).next = deletedNode.next;
        }
        // help GC
        deletedNode.next = null;

        return head;
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
