package com.lg.algorithm.sort.a2.practice;

import cn.hutool.core.util.RandomUtil;

import java.util.Arrays;

/**
 * 归并排序
 * 时间复杂度：O(N*logN)
 * 空间复杂度：O(N)
 * 稳定性：稳定
 *
 * @author Xulg
 * Created in 2021-01-18 10:54
 */
@SuppressWarnings("DuplicatedCode")
class MergeSort {

    /**
     * 递归版本的归并排序
     */
    public static void sort1(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        mergeSortByRecurse(arr, 0, arr.length - 1);
    }

    /**
     * 循环版本的归并排序
     */
    public static void sort2(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        mergeSortByLoop(arr, 0, arr.length - 1);
    }

    private static void mergeSortByRecurse(int[] arr, int left, int right) {
        // base case
        if (left == right) {
            return;
        }
        // 计算中间位置，另一种写法 (left + right)/2
        int mid = (right - left) / 2 + left;
        // 排左边
        mergeSortByRecurse(arr, left, mid);
        // 排右边
        mergeSortByRecurse(arr, mid + 1, right);
        // 归并操作
        merge(arr, left, right, mid);
    }

    @SuppressWarnings("SameParameterValue")
    private static void mergeSortByLoop(int[] arr, int left, int right) {
        // 子数组的一半的mergeSize
        int mergeSize = 1;
        // 不能越界
        while (mergeSize <= right) {
            // 子数组的左边界位置
            int leftIdx = left;
            // 不能越界啊
            while (leftIdx <= right) {
                // 子数组的中间位置
                int midIdx = leftIdx + mergeSize - 1;
                if (midIdx > right) {
                    // 子数组的中间位置越界了
                    break;
                }
                // 子数组的右边界位置，越界就去整个数组的右边界
                int rightIdx = Math.min(midIdx + mergeSize, right);
                // 归并操作
                merge(arr, leftIdx, rightIdx, midIdx);
                // 下一个子数组的开始索引，等价写法 leftIdx = leftIdx + mergeSize * 2;
                leftIdx = rightIdx + 1;
            }

            // 提前退出，防止mergeSize太大翻倍之后值溢出
            if (mergeSize > (right / 2)) {
                break;
            }
            // 翻倍，等价写法 mergeSize = mergeSize * 2;
            mergeSize = mergeSize << 1;
        }
    }

    private static void merge(int[] arr, int left, int right, int mid) {
        if (left == right) {
            return;
        }

        // 申请一个临时数组
        int[] helper = new int[right - left + 1];

        // 临时数组的索引位置
        int idx = 0;
        // 左子数组的开始位置
        int leftIdx = left;
        // 右子数组的开始位置
        int rightIdx = mid + 1;

        // 左子数组和右子数组都不能越界
        while (leftIdx <= mid && rightIdx <= right) {
            /* 哪边的数小就往临时数组中存放 */
            // 左边小于等于右边，留下左边的，否则留下右边的
            // 注意：这里必须是左边的小于等于右边才留左边的，因为为了保证稳定性
            // 如果左边等于右边，留下右边，那么两个相等数的位置就发生了变化，变成不稳定了
            if (arr[leftIdx] <= arr[rightIdx]) {
                helper[idx] = arr[leftIdx];
                leftIdx++;
            } else {
                helper[idx] = arr[rightIdx];
                rightIdx++;
            }
            idx++;
        }

        // 剩余的数据写入临时数组中
        while (leftIdx <= mid) {
            helper[idx++] = arr[leftIdx++];
        }
        while (rightIdx <= right) {
            helper[idx++] = arr[rightIdx++];
        }

        // 将临时数组的内容写回原数组
        //noinspection ManualArrayCopy
        for (int i = 0; i < helper.length; i++) {
            arr[left + i] = helper[i];
        }
    }

    public static void main(String[] args) {
        {
            int[] array = {3, 1, 2, 5, 4};
            sort2(array);
            System.out.println(Arrays.toString(array));

            array = new int[]{9, 2, 3, 3, 0, 4, 8, 2, 3, 6};
            sort2(array);
            System.out.println(Arrays.toString(array));
        }
        {
            // 验证归并排序
            int times = 1000000;
            for (int time = 0; time < times; time++) {
                int length = RandomUtil.randomInt(0, 100);
                int[] arr = new int[length];
                for (int i = 0; i < arr.length; i++) {
                    arr[i] = RandomUtil.randomInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
                }
                int[] arr1 = arr.clone();
                int[] arr2 = arr.clone();
                int[] arr3 = arr.clone();
                Arrays.sort(arr1);
                sort1(arr2);
                sort2(arr3);
                if (!Arrays.equals(arr1, arr2) || !Arrays.equals(arr2, arr3)) {
                    System.out.println("Oops");
                }
            }
        }
        System.out.println("finish!");
    }

}
