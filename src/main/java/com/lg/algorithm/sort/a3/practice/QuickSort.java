package com.lg.algorithm.sort.a3.practice;

import cn.hutool.core.util.RandomUtil;
import com.lg.algorithm.sort.a3.ComparatorSort;

import java.util.Arrays;

/**
 * @author Xulg
 * @since 2021-09-02 21:03
 * Description: 随机快速排序
 */
class QuickSort {

    public static void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        recurse(arr, 0, arr.length - 1);
    }

    private static void recurse(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }

        // 随机选一个位置的值作为分区轴
        int pivot = arr[left + (int) (Math.random() * (right - left + 1))];

        // 分区操作
        int[] partition = partition(arr, left, right, pivot);
        int equalsZoneStart = partition[0];
        int equalsZoneEnd = partition[1];

        // 向左分区
        recurse(arr, left, equalsZoneStart - 1);
        // 向右分区
        recurse(arr, equalsZoneEnd + 1, right);
    }

    private static int[] partition(int[] arr, int left, int right, int pivot) {
        if (left > right) {
            return new int[]{-1, -1};
        }
        if (left == right) {
            return new int[]{left, right};
        }
        int lessIdx = left - 1;
        int moreIdx = right + 1;
        for (int idx = left; idx < moreIdx; ) {
            if (arr[idx] > pivot) {
                swap(arr, idx, --moreIdx);
            } else if (arr[idx] < pivot) {
                swap(arr, idx++, ++lessIdx);
            } else {
                idx++;
            }
        }
        if (lessIdx + 1 > moreIdx - 1) {
            return new int[]{-1, -1};
        } else {
            return new int[]{lessIdx + 1, moreIdx - 1};
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
