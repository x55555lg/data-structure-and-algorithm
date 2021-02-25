package com.lg.leetcode.topinterview.practice;

/**
 * @author Xulg
 * Created in 2021-02-24 9:36
 */
class SortList {

    /*
     * Given the head of a linked list, return the list after sorting it in ascending order.
     * Follow up: Can you sort the linked list in O(N*logN) time and O(1) memory (i.e. constant space)?
     *
     * Example 1:
     * Input: head = [4,2,1,3]
     * Output: [1,2,3,4]
     *
     * Example 2:
     * Input: head = [-1,5,3,4,0]
     * Output: [-1,0,3,4,5]
     *
     * Example 3:
     * Input: head = []
     * Output: []
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

    public static ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            // 0节点，1节点
            return head;
        }

        int length = 0;
        for (ListNode node = head; node != null; node = node.next) {
            length++;
        }
        ListNode[] arr = new ListNode[length];
        int idx = 0;
        for (ListNode node = head; node != null; node = node.next) {
            arr[idx++] = node;
        }

        if (length < 7) {
            InsertionSort.sort(arr);
        } else {
            QuickSort.sort(arr);
        }

        ListNode node = arr[0];
        for (int i = 1; i < arr.length; i++) {
            node.next = arr[i];
            node = node.next;
        }
        node.next = null;
        return arr[0];
    }

    private static class InsertionSort {

        public static void sort(ListNode[] arr) {
            for (int i = 0; i < arr.length; i++) {
                for (int j = i; j > 0; j--) {
                    if (arr[j - 1].val > arr[j].val) {
                        swap(arr, j - 1, j);
                    }
                }
            }
        }

        private static void swap(ListNode[] arr, int i, int j) {
            ListNode temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }

    }

    private static class MergeSort {

        public static void sort(ListNode[] arr) {
            if (arr == null || arr.length < 2) {
                return;
            }
            doMergeSort(arr, 0, arr.length - 1);
        }

        private static void doMergeSort(ListNode[] arr, int left, int right) {
            // base case
            if (left == right) {
                return;
            }
            int mid = left + ((right - left) >> 1);
            doMergeSort(arr, left, mid);
            doMergeSort(arr, mid + 1, right);
            merge(arr, left, right, mid);
        }

        private static void merge(ListNode[] arr, int left, int right, int mid) {
            if (left == right) {
                return;
            }

            ListNode[] helper = new ListNode[right - left + 1];
            int idx = 0;
            int leftIdx = left;
            int rightIdx = mid + 1;
            while (leftIdx <= mid && rightIdx <= right) {
                if (arr[leftIdx].val <= arr[rightIdx].val) {
                    helper[idx++] = arr[leftIdx++];
                } else {
                    helper[idx++] = arr[rightIdx++];
                }
            }
            while (leftIdx <= mid) {
                helper[idx++] = arr[leftIdx++];
            }
            while (rightIdx <= right) {
                helper[idx++] = arr[rightIdx++];
            }
            for (int i = 0; i < helper.length; i++) {
                arr[left + i] = helper[i];
            }
        }
    }

    private static class QuickSort {

        public static void sort(ListNode[] arr) {
            if (arr == null || arr.length < 2) {
                return;
            }
            doQuickSort(arr, 0, arr.length - 1);
        }

        private static void doQuickSort(ListNode[] arr, int left, int right) {
            if (left >= right) {
                return;
            }
            // random pivot
            int randomIdx = left + (int) (Math.random() * (right - left + 1));
            swap(arr, randomIdx, right);
            // partition
            int[] partition = partition(arr, left, right);
            doQuickSort(arr, left, partition[0] - 1);
            doQuickSort(arr, partition[1] + 1, right);
        }

        private static int[] partition(ListNode[] arr, int left, int right, ListNode pivot) {
            if (left > right) {
                return new int[]{-1, -1};
            }
            int less = left - 1;
            int more = right + 1;
            for (int idx = left; idx < more; ) {
                if (arr[idx].val < pivot.val) {
                    swap(arr, idx++, ++less);
                } else if (arr[idx].val > pivot.val) {
                    swap(arr, idx, --more);
                } else {
                    // arr[idx] == pivot
                    idx++;
                }
            }
            if (less + 1 > more - 1) {
                return new int[]{-1, -1};
            } else {
                return new int[]{less + 1, more - 1};
            }
        }

        private static int[] partition(ListNode[] arr, int left, int right) {
            if (left > right) {
                return new int[]{-1, -1};
            }
            if (left == right) {
                return new int[]{left, right};
            }
            int less = left - 1;
            int more = right;
            ListNode pivot = arr[right];
            for (int idx = left; idx < more; ) {
                if (arr[idx].val < pivot.val) {
                    swap(arr, idx++, ++less);
                } else if (arr[idx].val > pivot.val) {
                    swap(arr, idx, --more);
                } else {
                    // arr[idx] == pivot
                    idx++;
                }
            }
            swap(arr, more, right);
            return new int[]{less + 1, more};
        }

        private static void swap(ListNode[] arr, int i, int j) {
            ListNode temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }

    }

    private static class HeapSort {

        public static void sort(ListNode[] arr) {
            if (arr == null || arr.length < 2) {
                return;
            }

            // 大根堆堆化数组
            for (int i = arr.length - 1; i >= 0; i--) {
                heapify(arr, i, arr.length);
            }

            // 第一个数和i位置交换，保证最大值在最后
            for (int i = arr.length - 1; i >= 0; i--) {
                swap(arr, 0, i);
                heapify(arr, 0, i);
            }
        }

        private static void heapInsert(ListNode[] arr, int index) {
            // 向上堆化
            while (index > 0) {
                int parentIdx = (index - 1) >> 1;
                if (arr[parentIdx].val < arr[index].val) {
                    swap(arr, parentIdx, index);
                }
                index = parentIdx;
            }
        }

        private static void heapify(ListNode[] arr, int index, int heapSize) {
            // 向下堆化
            int leftIdx = (index << 1) + 1;
            while (leftIdx < heapSize) {
                int rightIdx = leftIdx + 1;
                // 取左右子节点中的最大子节点
                int maxSubIdx;
                if (rightIdx >= heapSize) {
                    // 右子节点越界，最大就是左子节点
                    maxSubIdx = leftIdx;
                } else {
                    // 哪个大取哪个
                    maxSubIdx = arr[leftIdx].val >= arr[rightIdx].val ? leftIdx : rightIdx;
                }
                // 父节点，子节点取最大的那个
                int maxIdx = arr[index].val >= arr[maxSubIdx].val ? index : maxSubIdx;
                if (maxIdx == index) {
                    // 父节点已经是最大的了，堆化完成
                    break;
                }
                swap(arr, index, maxIdx);
                // 继续向下
                index = maxIdx;
                leftIdx = (maxIdx << 1) + 1;
            }
        }

        private static void swap(ListNode[] arr, int i, int j) {
            ListNode temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
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
            // [4,19,14,5,-3,1,8,5,11,15]
            ListNode node1 = new ListNode(4);
            ListNode node2 = new ListNode(19);
            ListNode node3 = new ListNode(14);
            ListNode node4 = new ListNode(5);
            ListNode node5 = new ListNode(-3);
            ListNode node6 = new ListNode(1);
            ListNode node7 = new ListNode(8);
            ListNode node8 = new ListNode(5);
            ListNode node9 = new ListNode(11);
            ListNode node10 = new ListNode(15);
            node1.next = node2;
            node2.next = node3;
            node3.next = node4;
            node4.next = node5;
            node5.next = node6;
            node6.next = node7;
            node7.next = node8;
            node8.next = node9;
            node9.next = node10;

            ListNode node = sortList(node1);
            for (ListNode listNode = node; listNode != null; listNode = node.next) {
                System.out.println(listNode.val);
            }
        }
    }
}
