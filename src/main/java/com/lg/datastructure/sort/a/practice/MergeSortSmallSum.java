package com.lg.datastructure.sort.a.practice;

import java.util.Arrays;

/**
 * 使用归并排序求数组小和
 *
 * @author Xulg
 * Created in 2020-10-26 14:08
 */
@SuppressWarnings("ManualArrayCopy")
class MergeSortSmallSum {

    /*
     * 在一个数组中，一个数左边比它小的数的总和，叫做数的小和，所有数的小和累加起来，
     * 叫做数组小和。求一个给定数组的小和：
     * 例如：[1, 3, 4, 2, 5]
     *      1左边比1小的数：没有
     *      3左边比3小的数：1
     *      4左边比4小的数：1, 3
     *      2左边比2小的数：1
     *      5左边比5小的数：1, 3, 4, 2
     *     最小和：1 + 1 + 3 + 1 + 1 + 3 + 4 + 2
     */

    public static int calcSmallSum(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        return doCalcSmallSum(array, 0, array.length - 1);
    }

    private static int doCalcSmallSum(int[] array, int left, int right) {
        if (left == right) {
            return 0;
        }

        // 计算中间位置
        int mid = ((right - left) / 2) + left;

        // 计算左数组的小和
        int leftSmallSum = doCalcSmallSum(array, left, mid);
        // 计算右数组的小和
        int rightSmallSum = doCalcSmallSum(array, mid + 1, right);

        // 计算当前部分数组的小和
        int smallSum = merge(array, left, right, mid);

        // 返回总的小和
        return smallSum + leftSmallSum + rightSmallSum;
    }

    private static int merge(int[] array, int left, int right, int mid) {
        int sum = 0;
        int[] helper = new int[right - left + 1];
        int idx = 0;
        int leftIdx = left;
        int rightIdx = mid + 1;
        while (leftIdx <= mid && rightIdx <= right) {
            // 左边小于右边，产生小和
            if (array[leftIdx] < array[rightIdx]) {
                // 在右边数组有(right - rightIdx + 1)个数比array[leftIdx]大
                sum = sum + (right - rightIdx + 1) * array[leftIdx];
                helper[idx] = array[leftIdx];
                leftIdx++;
            } else {
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
        for (int i = 0; i < helper.length; i++) {
            array[i + left] = helper[i];
        }
        return sum;
    }

    /* 对数器 start */

    static int comparator(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int res = 0;
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < i; j++) {
                res += arr[j] < arr[i] ? arr[j] : 0;
            }
        }
        return res;
    }

    static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    static int[] copyArray(int[] arr) {
        return arr.clone();
    }

    static boolean isEqual(int[] arr1, int[] arr2) {
        return Arrays.equals(arr1, arr2);
    }

    static void printArray(int[] arr) {
        System.out.println(Arrays.toString(arr));
    }

    /* 对数器 end */

    public static void main(String[] args) {
        int[] array = {1, 3, 4, 2, 5};
        int smallSum = calcSmallSum(array);
        System.out.println(smallSum);

        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            if (calcSmallSum(arr1) != comparator(arr2)) {
                succeed = false;
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }
}
