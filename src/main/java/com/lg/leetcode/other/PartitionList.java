package com.lg.leetcode.other;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xulg
 * Description: leetcode_86
 * Created in 2021-06-01 12:33
 */
class PartitionList {
    /*
     *  Given the head of a linked list and a value x, partition it such that
     * all nodes less than x come before nodes greater than or equal to x.
     *  You should preserve the original relative order of the nodes in each of
     * the two partitions.
     *
     * Example 1:
     * Input: head = [1,4,3,2,5,2], x = 3
     * Output: [1,2,2,4,3,5]
     *
     * Example 2:
     * Input: head = [2,1], x = 2
     * Output: [1,2]
     *
     * Constraints:
     * The number of nodes in the list is in the range [0, 200].
     * -100 <= Node.val <= 100
     * -200 <= x <= 200
     */

    public ListNode partition(ListNode head, int x) {
        return PartitionByArray.partition(head, x);
    }

    /**
     * TODO 2021/6/1 BUG
     */
    private static class PartitionByArray {

        public static ListNode partition(ListNode head, int pivot) {
            if (head == null) {
                return head;
            }
            List<ListNode> list = new ArrayList<>();
            for (ListNode node = head; node != null; node = node.next) {
                list.add(node);
            }
            int less = -1, more = list.size();
            for (int idx = 0; idx < more; ) {
                ListNode node = list.get(idx);
                if (node.val > pivot) {
                    swap(list, idx, --more);
                } else if (node.val < pivot) {
                    swap(list, idx++, ++less);
                } else {
                    idx++;
                }
            }
            ListNode newHead = list.get(0);
            ListNode tail = newHead;
            for (int idx = 1; idx < list.size(); idx++) {
                tail.next = list.get(idx);
                tail = tail.next;
            }
            // 防止链表循环
            tail.next = null;
            return newHead;
        }

        private static void swap(List<ListNode> list, int i, int j) {
            ListNode temp = list.get(i);
            list.set(i, list.get(j));
            list.set(j, temp);
        }
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) { this.val = val; }
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(4);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(2);
        head.next.next.next.next = new ListNode(5);
        head.next.next.next.next.next = new ListNode(2);
        ListNode partition = PartitionByArray.partition(head, 3);
        System.out.println(partition);
    }
}
