package com.lg.leetcode.other;

import java.util.ArrayList;
import java.util.Arrays;
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
        return Solution1.insertionSortList(head);
    }

    private static class Solution1 {
        public static ListNode insertionSortList(ListNode head) {
            if (head == null || head.next == null) {
                return head;
            }
            List<ListNode> list = new ArrayList<>();
            for (ListNode node = head; node != null; node = node.next) {
                list.add(node);
            }
            InsertionSort.sort(list);
            ListNode node = list.get(0);
            for (int i = 1; i < list.size(); i++) {
                ListNode temp = list.get(i);
                node.next = temp;
                node = temp;
            }
            node.next = null;
            return list.get(0);
        }

        private static class InsertionSort {
            public static void sort(List<ListNode> arr) {
                for (int i = 0; i < arr.size(); i++) {
                    for (int j = i; j > 0 && arr.get(j - 1).val > arr.get(j).val; j--) {
                        swap(arr, j - 1, j);
                    }
                }
            }

            private static void swap(List<ListNode> arr, int i, int j) {
                ListNode temp = arr.get(i);
                arr.set(i, arr.get(j));
                arr.set(j, temp);
            }
        }
    }

    private static class InsertionSort {
        public static void sort(int[] arr) {
            if (arr == null || arr.length < 2) {
                return;
            }
            for (int i = 0; i < arr.length; i++) {
                for (int j = i; j > 0 && arr[j - 1] > arr[j]; j--) {
                    swap(arr, j - 1, j);
                }
            }
        }

        private static void swap(int[] arr, int i, int j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    private static class Solution2 {
        public static ListNode insertionSortList(ListNode head) {
            if (head == null || head.next == null) {
                return head;
            }

            // 占位节点
            ListNode dummyHead = new ListNode(0);
            dummyHead.next = head;

            ListNode currentNode = head;
            while (currentNode != null && currentNode.next != null) {
                if (currentNode.val <= currentNode.next.val) {
                    // 是升序的，继续往后判断
                    currentNode = currentNode.next;
                } else {
                    /*
                     * dummy0 -> 1 -> 2 -> 3 -> 6 -> 5 -> 4
                     *      pre                 cur  to
                     *-------------------------------------
                     * dummy0 -> 1 -> 2 -> 3 -> 6 -> 5 -> 4
                     *                     pre  cur  to
                     */
                    // 插入位置：当前节点要插入到当前节点的next的后面
                    ListNode toInsertLocation = currentNode.next;
                    // 找到当前节点的前一个节点
                    ListNode preInsertLocation = dummyHead;
                    while (preInsertLocation.next.val < toInsertLocation.val) {
                        preInsertLocation = preInsertLocation.next;
                    }
                    // 交换pre节点和to节点
                    /*
                     * 1 -> 2 -> 3 -> 6 -> 4
                     *           p    c
                     * 5 -> 4
                     * t
                     */
                    currentNode.next = toInsertLocation.next;
                    /*
                     * 1 -> 2 -> 3 -> 6 -> 4
                     *           p    c
                     * 5 -> 6 -> 4
                     * t    c
                     */
                    toInsertLocation.next = preInsertLocation.next;
                    /*
                     * 1 -> 2 -> 3 -> 5 -> 6 -> 4
                     *           p    c    t
                     */
                    preInsertLocation.next = toInsertLocation;
                }
            }
            return dummyHead.next;
        }
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
        ListNode node = Solution2.insertionSortList(n0);
        System.out.println(node);
    }

}
