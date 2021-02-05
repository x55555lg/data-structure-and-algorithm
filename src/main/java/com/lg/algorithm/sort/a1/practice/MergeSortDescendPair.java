package com.lg.algorithm.sort.a1.practice;

import java.util.Arrays;

/**
 * 使用归并排序求数组降序对个数
 *
 * @author Xulg
 * Created in 2020-10-26 14:08
 */
@SuppressWarnings({"ManualArrayCopy", "DuplicatedCode", "ConstantConditions"})
class MergeSortDescendPair {

    /*
     * 在一个数组中，只要前面的数比后面的大，就叫做降序对，例如
     *  数组：{3, 1, 7, 0, 2}中，有这些降序对：
     *      3   1
     *      3   0
     *      3   2
     *      1   0
     *      7   0
     *      7   2
     */

    static boolean debug = false;

    public static int calcDescendPairCount(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        return doCalcDescendPairCount(array, 0, array.length - 1);
    }

    private static int doCalcDescendPairCount(int[] array, int left, int right) {
        if (left >= right) {
            return 0;
        }
        // 计算中间位置
        int mid = ((right - left) / 2) + left;
        int leftCount = doCalcDescendPairCount(array, left, mid);
        int rightCount = doCalcDescendPairCount(array, mid + 1, right);
        int count = merge(array, left, right, mid);
        return count + leftCount + rightCount;
    }

    private static int merge(int[] array, int left, int right, int mid) {
        int count = 0;
        int[] helper = new int[right - left + 1];
        int idx = 0;
        int leftIdx = left;
        int rightIdx = mid + 1;
        while (leftIdx <= mid && rightIdx <= right) {
            // 左边比右边大，有降序对
            if (array[leftIdx] > array[rightIdx]) {
                // 在左数组有这么多个数大于array[rightIdx]
                count = count + (mid - leftIdx + 1);
                if (debug) {
                    // 打印出这些降序对
                    for (int i = leftIdx; i <= mid; i++) {
                        System.out.println("降序对: " + array[i] + "-" + array[rightIdx]);
                    }
                }
                // 右边小就放右边的数到临时数组
                helper[idx] = array[rightIdx];
                rightIdx++;
            } else {
                // 左边小就放左边的数到临时数组
                helper[idx] = array[leftIdx];
                leftIdx++;
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
        return count;
    }

    /* 对数器 start */

    static int comparator(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = i; j < array.length; j++) {
                if (array[i] > array[j]) {
                    count = count + 1;
                    if (debug) {
                        System.out.println("降序对: " + array[i] + "-" + array[j]);
                    }
                }
            }
        }
        return count;
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
        int[] array = {3, 1, 7, 0, 2};
        System.out.println(calcDescendPairCount(array.clone()));
        System.out.println(comparator(array.clone()));

        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            if (calcDescendPairCount(arr1) != comparator(arr2)) {
                succeed = false;
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }
}
