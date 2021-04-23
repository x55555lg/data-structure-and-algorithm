package com.lg.leetcode.topinterview.practice;

/**
 * @author Xulg
 * Created in 2021-03-04 17:05
 */
class KthSmallestElementInSortedMatrix {

    /*
     * Given an n x n matrix where each of the rows and columns are sorted in ascending order,
     * return the kth smallest element in the matrix.
     * Note that it is the kth smallest element in the sorted order, not the kth distinct element.
     *
     * Example 1:
     * Input: matrix = [[1,5,9],[10,11,13],[12,13,15]], k = 8
     * Output: 13
     * Explanation: The elements in the matrix are [1,5,9,10,11,12,13,13,15], and the 8th smallest number is 13
     *
     * Example 2:
     * Input: matrix = [[-5]], k = 1
     * Output: -5
     *
     * Constraints:
     *  n == matrix.length
     *  n == matrix[i].length
     *  1 <= n <= 300
     *  -109 <= matrix[i][j] <= -109
     *  All the rows and columns of matrix are guaranteed to be sorted in non-degreasing order.
     *  1 <= k <= n2
     */

    public static int kthSmallest(int[][] matrix, int k) {
        int[] arr = recurse(matrix, 0, matrix.length - 1);
        return arr[k - 1];
    }

    private static int[] recurse(int[][] matrix, int left, int right) {
        if (left == right) {
            return matrix[left];
        }
        int mid = left + ((right - left) >> 1);
        int[] leftArr = recurse(matrix, left, mid);
        int[] rightArr = recurse(matrix, mid + 1, right);
        return mergeSort(leftArr, rightArr);
    }

    private static int[] mergeSort(int[] arr1, int[] arr2) {
        int[] helper = new int[arr1.length + arr2.length];
        int idx = 0, leftIdx = 0, rightIdx = 0;
        while (leftIdx < arr1.length && rightIdx < arr2.length) {
            if (arr1[leftIdx] <= arr2[rightIdx]) {
                helper[idx++] = arr1[leftIdx++];
            } else {
                helper[idx++] = arr2[rightIdx++];
            }
        }
        while (leftIdx < arr1.length) {
            helper[idx++] = arr1[leftIdx++];
        }
        while (rightIdx < arr2.length) {
            helper[idx++] = arr2[rightIdx++];
        }
        return helper;
    }

    public static void main(String[] args) {
        int[][] matrix = {{1, 5, 9}, {10, 11, 13}, {12, 13, 15}};
        int k = 8;
        int kthSmallest = kthSmallest(matrix, k);
        System.out.println(kthSmallest);

        matrix = new int[][]{{-5}};
        k = 1;
        kthSmallest = kthSmallest(matrix, k);
        System.out.println(kthSmallest);
    }

}
