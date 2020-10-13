package com.lg.datastructure.sort.mergesort;

import java.util.Arrays;

/**
 * 过了好久之后手写归并排序以及相关的变种题
 *
 * @author Xulg
 * Created in 2020-10-12 10:23
 */
@SuppressWarnings("all")
class MergeSortDemo {

    /* 递归方式的归并排序 */

    public static void sortByRecurse(int[] array) {
        if (array == null || array.length < 2) {
            // 排个鸡儿
            return;
        }
        doProcessByRecurse(array, 0, array.length - 1);
    }

    private static void doProcessByRecurse(int[] array, int left, int right) {
        if (left == right) {
            // 一个元素了，排个鸡儿
            return;
        }
        // (left + right)/2
        int mid = left + ((right - left) >> 1);
        // 排序左边部分
        doProcessByRecurse(array, left, mid);
        // 排序右边部分
        doProcessByRecurse(array, mid + 1, right);

        merge(array, left, mid, right);
    }

    /* 循环方式的归并排序 */

    public static void sortByLoop(int[] array) {
        if (array == null || array.length < 2) {
            // 排个锤子
            return;
        }

        int mergeSize = 1;
        while (mergeSize < array.length) {
            int left = 0;
            while (left < array.length) {
                // 算出数组的中间位置
                int mid = left + mergeSize - 1;
                if (mid >= array.length) {
                    // 提前结束
                    break;
                }

                // 算出数组的右边位置，不能越界
                int right = Math.min(mid + mergeSize, array.length - 1);

                // merge操作
                merge(array, left, mid, right);

                // 下一个数组的左边位置
                //left = left + mergeSize * 2;
                left = right + 1;
            }
            // 提前退出，防止值太大溢出
            if (mergeSize > (array.length / 2)) {
                break;
            }
            // mergeSize翻倍
            mergeSize = mergeSize * 2;
        }
    }

    private static void merge(int[] array, int left, int mid, int right) {
        // 临时数组存放数据
        int[] tempArray = new int[right - left + 1];
        int index = 0;
        int leftIdx = left;
        int rightIdx = mid + 1;
        while (leftIdx <= mid && rightIdx <= right) {
            /* 哪边的数小就往临时数组中存放 */
            // 左边数大于右边数，留下右边数组的值
            if (array[leftIdx] > array[rightIdx]) {
                tempArray[index] = array[rightIdx];
                rightIdx++;
            }
            // 左边数小于等于右边数，留下左边数组的值
            else {
                tempArray[index] = array[leftIdx];
                leftIdx++;
            }
            index++;
        }
        while (leftIdx <= mid) {
            tempArray[index++] = array[leftIdx++];
        }
        while (rightIdx <= right) {
            tempArray[index++] = array[rightIdx++];
        }
        // 临时数组数据复制到原数组中去
        for (int i = 0; i < tempArray.length; i++) {
            array[left + i] = tempArray[i];
        }
    }

    /* ============================================================================================================== */

    /* 使用归并排序求数组的小和 */

    public static int calcArraySmallSum(int[] array) {
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

        // 数组只有一个元素，没有小和
        if (array == null || array.length < 2) {
            return 0;
        }

        // 数组只有2个元素，看第1个元素是不是小和
        if (array.length == 2 && array[0] < array[1]) {
            return array[0];
        }

        // 归并求小和
        return doCalcProcess(array, 0, array.length - 1);
    }

    private static int doCalcProcess(int[] array, int left, int right) {
        if (left == right) {
            return 0;
        }

        // 取得中间位置
        int mid = left + (right - left) / 2;

        // 计算左边数组的小和
        int leftSmallSum = doCalcProcess(array, left, mid);

        // 计算右边数组的小和
        int rightSmallSum = doCalcProcess(array, mid + 1, right);

        // 计算当前这部分数组的小和
        int smallSum = mergeCalc(array, left, mid, right);

        // 累加各部分小和
        return smallSum + leftSmallSum + rightSmallSum;
    }

    private static int mergeCalc(int[] array, int left, int mid, int right) {
        // 申请一个临时数组啊
        int[] temp = new int[right - left + 1];

        // 临时数组的索引
        int idx = 0;
        // 数组的左边索引
        int leftIdx = left;
        // 数组的右边索引
        int rightIdx = mid + 1;
        // 小和
        int smallSum = 0;

        while (leftIdx <= mid && rightIdx <= right) {
            // 左边小于右边，产生小和
            if (array[leftIdx] < array[rightIdx]) {
                temp[idx] = array[leftIdx];
                // 在右数组上有(right - rightIdx + 1)个数是大于array[leftIdx]的
                smallSum = smallSum + (right - rightIdx + 1) * array[leftIdx];
                leftIdx++;
            } else {
                temp[idx] = array[rightIdx];
                rightIdx++;
            }
            idx++;
        }

        // 上面的操作之后，左半部分或者右半部分可能
        // 还有部分数组没有处理到，接着处理
        while (leftIdx <= mid) {
            temp[idx++] = array[leftIdx++];
        }
        while (rightIdx <= right) {
            temp[idx++] = array[rightIdx++];
        }

        // 将临时数组的数据复制到原数组的[left, right]位置上去
        for (int i = 0; i < temp.length; i++) {
            array[left + i] = temp[i];
        }

        return smallSum;
    }

    /* ============================================================================================================== */

    /* 使用归并排序求数组有多少个降序对 */

    public static int calcDescendPairCount(int[] array) {
        if (array == null || array.length < 2) {
            return 0;
        }
        return doCalcDescendPairCount(array, 0, array.length - 1);
    }

    private static int doCalcDescendPairCount(int[] array, int left, int right) {
        if (left == right) {
            return 0;
        }
        // 计算中间位置
        int mid = left + ((right - left) >> 1);
        // 左边数组中的降序对数量
        int leftCount = doCalcDescendPairCount(array, left, mid);
        // 右边数组中的降序对数量
        int rightCount = doCalcDescendPairCount(array, mid + 1, right);
        // 当前部分数组的降序对数量
        int count = mergeCalcCount(array, left, mid, right);
        // total count
        return count + leftCount + rightCount;
    }

    private static int mergeCalcCount(int[] array, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int count = 0;
        int idx = 0;
        int leftIdx = left;
        int rightIdx = mid + 1;
        while (leftIdx <= mid && rightIdx <= right) {
            // 右边的数小于左边的数，左边数组[leftIdx, mid]有多个大于右边的数
            if (array[rightIdx] < array[leftIdx]) {
                if (true) {
                    // 打印出降序对
                    for (int i = leftIdx; i <= mid; i++) {
                        System.out.println("发现降序对：" + array[i] + "-" + array[rightIdx]);
                    }
                }
                count = count + (mid - leftIdx + 1);
                temp[idx++] = array[rightIdx++];
            } else {
                temp[idx++] = array[leftIdx++];
            }
        }
        while (leftIdx <= mid) {
            temp[idx++] = array[leftIdx++];
        }
        while (rightIdx <= right) {
            temp[idx++] = array[rightIdx++];
        }
        for (int i = 0; i < temp.length; i++) {
            array[left + i] = temp[i];
        }
        return count;
    }

    public static void main(String[] args) {
        // 归并排序
        int[] array = {3, 1, 5, 2, 4, 7, 0, 9, 8};
        int[] array1 = array.clone();
        System.out.println("merge sort before: " + Arrays.toString(array1));
        // 递归方式的归并排序
        sortByRecurse(array1);
        System.out.println("merge sort after:  " + Arrays.toString(array1));

        int[] array2 = array.clone();
        System.out.println("merge sort before: " + Arrays.toString(array2));
        // 循环方式的归并排序
        sortByLoop(array2);
        System.out.println("merge sort after:  " + Arrays.toString(array2));

        // 数组小和
        array = new int[]{1, 3, 4, 2, 5};
        int smallSum = calcArraySmallSum(array.clone());
        System.out.println("数组" + Arrays.toString(array) + "的小和：" + smallSum);
        array = new int[]{3, 1, 2, 5, 4, 2, 2, 1};
        smallSum = calcArraySmallSum(array.clone());
        System.out.println("数组" + Arrays.toString(array) + "的小和：" + smallSum);

        // 数组的降序对数量
        array = new int[]{3, 1, 7, 0, 2};
        int count = calcDescendPairCount(array.clone());
        System.out.println("数组" + Arrays.toString(array) + "的降序对数量：" + count);
    }
}
