package com.lg.datastructure.sort;

import java.util.Arrays;

/**
 * 归并排序
 * 时间复杂度：n*log(n)
 *
 * @author Xulg
 * Created in 2020-09-24 17:33
 */
class MergeSort {

    /*
     * 递归方式实现的归并排序
     * 时间复杂度：
     *  根据递归复杂度计算公式：T(N) = a*T(N/b) + O(N^d)
     *      a = 2;  b = 2;  d = 1;
     *      因为：(log(2)^2 = 1) == d
     *      所以复杂度：O( N^d * log(N) ) = O(N*logN)
     */

    static class RecursionMergeSort {

        public static void mergeSort(int[] array) {
            if (array == null || array.length < 2) {
                return;
            }
            recursionProcess(array, 0, array.length - 1);
        }

        private static void recursionProcess(int[] array, int left, int right) {
            if (left == right) {
                // 左右索引一样了，就只有一个元素，不需要排序了
                return;
            }
            // 计算中间值
            int mid = left + ((right - left) >> 1);
            // 排序左边的
            recursionProcess(array, left, mid);
            // 排序右边的
            recursionProcess(array, mid + 1, right);
            // 合并左右两边排序后的数组
            merge(array, left, mid, right);
        }

        private static void merge(int[] array, int left, int mid, int right) {

            /*
                 left  = 0;
                 mid   = 4;
                 right = 1;
                 array：3, 1, 5, 2, 4, 7, 0, 9, 8
             */

            // 创建一个数组用于存放合并的数据
            int[] temp = new int[right - left + 1];
            // 左边数组的下标
            int leftIdx = left;
            // 右边数组的下标
            int rightIdx = mid + 1;
            // 新建数组的下标
            int idx = 0;
            while (leftIdx <= mid && rightIdx <= right) {
                if (array[leftIdx] <= array[rightIdx]) {
                    // 左边数组的值小于等于右边的数组的值，留下左边数组的值
                    temp[idx] = array[leftIdx];
                    // 左边数组的下标递增
                    leftIdx++;
                } else {
                    // 左边数组的值大于右边的数组的值，留下右边数组的值
                    temp[idx] = array[rightIdx];
                    rightIdx++;
                }
                // 新数组的下标递增
                idx++;
            }

            // 左边或者右边数组还有部分数组没处理掉
            if (leftIdx <= mid) {
                while (leftIdx <= mid) {
                    temp[idx] = array[leftIdx];
                    leftIdx++;
                    idx++;
                }
            } else if (rightIdx <= right) {
                while (rightIdx <= right) {
                    temp[idx] = array[rightIdx];
                    rightIdx++;
                    idx++;
                }
            }

            // 将新数组的数组写入到原数组中
            for (int i = left; i <= right; i++) {
                array[i] = temp[i - left];
            }
            /*
            // 这么写也可以
            for (int i = 0; i < temp.length; i++) {
                array[left + i] = temp[i];
            }
            */
        }
    }

    /**
     * 非递归方式实现的归并排序
     */
    static class SimpleMergeSort {
    }

    public static void main(String[] args) {
        int[] array = {3, 1, 5, 2, 4, 7, 0, 9, 8};
        System.out.println("merge sort before: " + Arrays.toString(array));
        // 递归方式的归并排序
        RecursionMergeSort.mergeSort(array);
        System.out.println("merge sort after: " + Arrays.toString(array));
    }

}
