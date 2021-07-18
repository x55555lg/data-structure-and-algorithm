package com.practice.sort;

import cn.hutool.core.util.RandomUtil;

import java.util.Arrays;

/**
 * @author Xulg
 * Created in 2021-04-29 14:04
 */
class SmallSum {

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
     *
     * 思路：求一个数左边比它小的数，就是看它有几个比它大的，例如上面的数组[1, 3, 4, 2, 5]中
     *       1的右边有4个比它大的数，所以最终的小和结果中一定有4个1；
     *       3的右边有2个比它大的数，所以最终的小和结果中一定有2个3；
     *       4的右边有1个比它大的数，所以最终的小和结果中一定有1个4；
     *       2的右边有1个比它大的数，所以最终的小和结果中一定有1个2；
     *       5的右边没有比它大的数；
     *      最小和：4*1 + 2*3 + 1*4 + 1*2
     *
     * 零分做法：一个一个的比较过去，时间复杂度：O(n^2)
     * 最优解：使用归并排序的思想，时间复杂度：O(n) = N*logN
     */

    private static class BruteForce {
        public static int calcSmallSum(int[] array) {
            if (array == null || array.length < 2) {
                return 0;
            }
            int sum = 0;
            for (int i = array.length - 1; i >= 0; i--) {
                for (int j = 0; j < i; j++) {
                    if (array[j] < array[i]) {
                        sum += array[j];
                    }
                }
            }
            return sum;
        }
    }

    private static class Best {
        public static int calcSmallSum(int[] array) {
            if (array == null || array.length < 2) {
                return 0;
            }
            return recurse(array, 0, array.length - 1);
        }

        private static int recurse(int[] arr, int left, int right) {
            if (left == right) {
                return 0;
            }
            int mid = left + ((right - left) >> 1);
            int leftSmallSum = recurse(arr, left, mid);
            int rightSmallSum = recurse(arr, mid + 1, right);
            int smallSum = merge(arr, left, right, mid);
            return leftSmallSum + rightSmallSum + smallSum;
        }

        private static int merge(int[] arr, int left, int right, int mid) {
            if (left == right) {
                return 0;
            }
            int sum = 0;
            int[] helper = new int[right - left + 1];
            int leftIdx = left;
            int rightIdx = mid + 1;
            int idx = 0;
            while (leftIdx <= mid && rightIdx <= right) {
                if (arr[leftIdx] < arr[rightIdx]) {
                    sum = sum + (right - rightIdx + 1) * arr[leftIdx];
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
            return sum;
        }
    }

    private static class Test {
        public static int calcSmallSumOfArray(int[] array) {
            if (array == null || array.length < 2) {
                return 0;
            }
            return process(array, 0, array.length - 1);
        }

        private static int process(int[] array, int left, int right) {
            if (left == right) {
                return 0;
            }

            // 取得中间位置
            int mid = left + ((right - left) >> 1);

            // 计算左边数组的小和
            int leftSmallSum = process(array, left, mid);

            // 计算右边数组的小和
            int rightSmallSum = process(array, mid + 1, right);

            // 计算小和
            String snapshotArray = Arrays.toString(array);
            int smallSum = merge(array, left, mid, right);
            //System.out.println("数组为" + snapshotArray + "范围：[" + left + "," + right + "] " + "smallSum = " + smallSum);

            // 最终的小和结果
            return leftSmallSum + rightSmallSum + smallSum;
        }

        @SuppressWarnings("ManualArrayCopy")
        private static int merge(int[] array, int left, int mid, int right) {
            // 申请一个临时数组，数组大小为能装下[left, right]部分的数据
            int[] temp = new int[right - left + 1];

            // 小和
            int sum = 0;
            // 临时数组的下标索引
            int idx = 0;
            // 子数组的左半部分的开始下标索引
            int leftIdx = left;
            // 子数组的右半部分的开始下标索引
            int rightIdx = mid + 1;

            // 同时遍历数组的左半部分和右半部分
            while (leftIdx <= mid && rightIdx <= right) {
                // 如果左半部分数组的值小于右半部分数组的值，则在临时数组中插入左半部分数组的值
                // 否则的话在临时数组中插入右半部分数组的值
                // 左半部分数组的值a小于右半部分数组的值的时候，就会产生小和
                // 右半部分数组有x个值比左半部分数组的值a大的，就有x个a
                if (array[leftIdx] < array[rightIdx]) {
                    temp[idx] = array[leftIdx];
                    // 计算小和，在[rightIdx, right]上有几个个数大于array[leftIdx]
                    sum += array[leftIdx] * (right - rightIdx + 1);
                    // 左半部分数组的下标索引后移
                    leftIdx++;
                } else {
                    temp[idx] = array[rightIdx];
                    // 右半部分数组的下标索引后移
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

            // 上面操作之后，临时数组temp中的数据是一定有序的

            // 将临时数组的数据复制到原数组的[left, right]位置上去
            for (int i = 0; i < temp.length; i++) {
                array[left + i] = temp[i];
            }

            return sum;
        }
    }

    public static void main(String[] args) {
        int times = 1000000;
        for (int time = 0; time < times; time++) {
            int[] arr = new int[50];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = RandomUtil.randomInt(0, 100);
            }
            int c1 = BruteForce.calcSmallSum(arr.clone());
            int c2 = Best.calcSmallSum(arr.clone());
            int c3 = Test.calcSmallSumOfArray(arr.clone());
            if (c1 != c2 || c2 != c3) {
                System.out.println("fucking......");
                return;
            }
        }
        System.out.println("success......");
    }
}
