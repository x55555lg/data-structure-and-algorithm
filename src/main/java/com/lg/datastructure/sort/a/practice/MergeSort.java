package com.lg.datastructure.sort.a.practice;

import java.util.Arrays;

/**
 * 归并排序
 *
 * @author Xulg
 * Created in 2020-10-23 16:13
 */
@SuppressWarnings({"ManualArrayCopy", "DuplicatedCode", "AlibabaUndefineMagicConstant", "SameParameterValue"})
class MergeSort {

    /*
     * 时间复杂度：O(N*logN)
     * 空间复杂度：O(N)
     * 稳定
     */

    /**
     * 使用递归方式进行归并排序
     *
     * @param array the array
     */
    public static void sortByRecurse(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        doSortByRecurse(array, 0, array.length - 1);
    }

    /**
     * 使用非递归方式进行归并排序
     *
     * @param array the array
     */
    public static void sortByLoop(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        doSortByLoop(array, 0, array.length - 1);
    }

    private static void doSortByRecurse(int[] array, int left, int right) {
        if (left >= right) {
            // 左右位置一样了退出递归
            return;
        }
        // 计算中间位置((left+right)/2)
        int mid = left + ((right - left) >> 1);
        // 左边部分归并排序
        doSortByRecurse(array, left, mid);
        // 右边边部分归并排序
        doSortByRecurse(array, mid + 1, right);
        // 归并操作
        merge(array, left, right, mid);
    }

    private static void doSortByLoop(int[] array, int left, int right) {
        // mergeSize就是子数组的一半
        for (int mergeSize = 1; mergeSize <= right; ) {
            // 子数组的开始索引
            for (int leftIdx = left; leftIdx <= right; ) {
                // 中间位置
                int mid = leftIdx + mergeSize - 1;
                // 索引不要越界
                if (mid > right) {
                    break;
                }
                // 子数组的结束索引
                int rightIdx = Math.min(mergeSize + +mid, right);
                // 归并操作
                merge(array, leftIdx, rightIdx, mid);
                // 下一个子数组的开始索引
                leftIdx = rightIdx + 1;
            }

            // 提前退出，防止mergeSize太大翻倍之后值溢出
            if (mergeSize > right / 2) {
                break;
            }

            // mergeSize翻倍
            mergeSize = mergeSize << 1;
        }
    }

    private static void merge(int[] array, int left, int right, int mid) {
        int[] helper = new int[right - left + 1];
        // 临时数组的索引
        int idx = 0;
        // 数组左边部分开始索引
        int leftIdx = left;
        // 数组右边部分开始索引
        int rightIdx = mid + 1;
        // 遍历数组的左边和右边，比较大小
        while (leftIdx <= mid && rightIdx <= right) {
            // 左边 <= 右边，留左边
            if (array[leftIdx] <= array[rightIdx]) {
                helper[idx] = array[leftIdx];
                leftIdx++;
            }
            // 左边 > 右边，留右边
            else {
                helper[idx] = array[rightIdx];
                rightIdx++;
            }
            idx++;
        }
        while (leftIdx <= mid) {
            helper[idx++] = array[leftIdx++];
        }
        while (rightIdx <= right) {
            helper[idx++] = array[rightIdx++];
        }

        // 临时数组内容写回原数组
        for (int i = 0; i < helper.length; i++) {
            array[i + left] = helper[i];
        }
    }

    public static void main(String[] args) {
        int[] array = {9, 5, 3, 2, 0, 6, 1, 4};

        // 递归方式的归并排序
        int[] arr1 = array.clone();
        sortByRecurse(arr1);
        System.out.println("递归after sort: " + Arrays.toString(arr1));

        // 非递归方式的归并排序
        int[] arr2 = array.clone();
        sortByLoop(arr2);
        System.out.println("非递归after sort: " + Arrays.toString(arr2));

        // 求数组的最小和

        // 求数组的降序对数量

        // TODO 2020/10/23 ......
    }
}
