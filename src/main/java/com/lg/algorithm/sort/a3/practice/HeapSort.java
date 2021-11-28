package com.lg.algorithm.sort.a3.practice;

import cn.hutool.core.util.RandomUtil;
import com.lg.algorithm.sort.a3.ComparatorSort;

import java.util.Arrays;

/**
 * @author Xulg
 * @since 2021-09-02 21:02
 * Description: 堆排序
 */
class HeapSort {

    public static void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        // 将数组大根堆化
        for (int idx = arr.length - 1; idx >= 0; idx--) {
            heapify(arr, idx, arr.length);
        }
        // 将0位置的最大值不停的和最后一个数交换
        for (int idx = arr.length - 1; idx > 0; idx--) {
            swap(arr, 0, idx);
            heapify(arr, 0, idx);
        }
    }

    /**
     * 自下向上开始大根堆化
     */
    private static void heapInsert(int[] arr, int idx) {
        // left   = 2 * parent + 1
        // right  = 2 * parent + 2
        // parent = left / 2 或 parent = right / 2
        int parentIdx = idx >> 1;
        while (parentIdx > 0) {
            if (arr[parentIdx] < arr[idx]) {
                // 不满足大根堆，进行交换
                swap(arr, parentIdx, idx);
            }
            // 继续往上
            parentIdx = parentIdx >> 1;
        }
    }

    /**
     * 从上到下开始大根堆化
     */
    private static void heapify(int[] arr, int parentIdx, int heapSize) {
        int leftIdx = (parentIdx << 1) + 1;
        while (leftIdx < heapSize) {
            // 右节点，可能越界
            int rightIdx = leftIdx + 1;
            // 左右子节点中最大的子节点
            int maxSubIdx = rightIdx >= heapSize ? leftIdx
                    : (arr[leftIdx] > arr[rightIdx] ? leftIdx : rightIdx);
            // 最大子节点和父节点比较，取最大那个
            int maxIdx = arr[maxSubIdx] > arr[parentIdx] ? maxSubIdx : parentIdx;
            if (maxIdx == parentIdx) {
                // 还是父节点最大，说明完成大根堆的堆化
                break;
            }
            // 父节点小于最大子节点，进行交换
            swap(arr, maxIdx, parentIdx);
            // 从交换点开始继续下去
            parentIdx = maxIdx;
            leftIdx = (parentIdx << 1) + 1;
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /* ****************************************************************************************************************/

    public static void main(String[] args) {
        int times = 100_0000;
        for (int time = 0; time < times; time++) {
            int[] arr = new int[RandomUtil.randomInt(0, 100)];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = RandomUtil.randomInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
            }
            int[] arr1 = arr.clone();
            int[] arr2 = arr.clone();
            sort(arr1);
            ComparatorSort.sort(arr2);
            if (!Arrays.equals(arr1, arr2)) {
                System.out.println("fucking");
            }
        }
        System.out.println("good job");
    }
}
