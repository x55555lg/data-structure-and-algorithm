package com.lg.datastructure.sort.quicksort;

import java.util.Arrays;

/**
 * 快排实现2.0
 *
 * @author Xulg
 * Created in 2020-10-13 16:20
 */
@SuppressWarnings({"all"})
class QuickSort2 {

    /*
     * 快排的实现原理2.0：使用荷兰旗问题的解法写
     *  在整个数组array[]中，取数组的最后一个值作为比较值，然后进行区间划分
     *      ...   1   2   3   4   5   6   ...   7    X
     *            L                                  R
     *            在[L, R-1]上划分区间：小于等于区、大于区
     *              array[i] < array[R]     放在区间的左边
     *              array[i] > array[R]     放在区间的右边
     *              array[i] == array[R]    放在区间的中间
     *              将array[R]和大于区间的第一个数互换，array[R]是最后一个等于区间的数
     *            此时数组的区间划分：start是等于区的开始索引  end是等于区的结束索引
     *              [L, start-1]    小于区
     *              [start, end]    等于区
     *              [end+1, R]      大于区
     *            跳过中间的等于区[start, end]
     *            在[L, start-1]进行区域划分。。。
     *            在[end+1, R]进行区域划分。。。
     */

    /* *****************快排递归方式实现***************************************************************************** */

    public static void quickSort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        doQuickSort(array, 0, array.length - 1);
    }

    private static void doQuickSort(int[] array, int left, int right) {
        if (left >= right) {
            return;
        }

        // 在[left, right]上随机选择一个值和array[right]交换，随机选择比较的值
        int randomIdx = (int) (Math.random() * (right - left + 1) + left);
        swap(array, randomIdx, right);

        // [left, right]上划分区间，获取等于区的索引
        int[] equalsAreaIndex = partition(array, left, right);

        // 跳过中间等于区间，排小于区
        doQuickSort(array, left, equalsAreaIndex[0] - 1);
        // 跳过中间等于区间，排大于区
        doQuickSort(array, equalsAreaIndex[1] + 1, right);
    }

    /**
     * 荷兰旗问题的解法
     * array[i] < array[right]
     * 放数组左边
     * array[i] > array[right]
     * 放数组右边
     * array[i] == array[right]
     * 放数组中间
     * 返回等于区间的开始索引和结束索引
     *
     * @param array the array
     * @param left  the left index of array
     * @param right the right index of array
     * @return the equals region start and end index
     */
    private static int[] partition(int[] array, int left, int right) {
        if (left > right) {
            return new int[]{-1, -1};
        }
        // 只有一个元素
        if (left == right) {
            return new int[]{left, right};
        }
        // 小于区的索引
        int lessIdx = left - 1;
        // 大于区的边界index，大于区从right开始，这样保证被比较的array{right}不会改变
        int moreIdx = right;
        // 遍历数组[left, moreIdx)
        for (int index = left; index < moreIdx; ) {
            if (array[index] < array[right]) {
                // 把小于区的下一个数和array[index]互换
                swap(array, lessIdx + 1, index);
                // 小于区索引增1
                lessIdx++;
                index++;
            } else if (array[index] > array[right]) {
                // 把大于区的前一个数和array[index]互换，这里不需要index++，
                // 因为从大于区换过来的数是不确定大小的，需要重新判断过
                swap(array, moreIdx - 1, index);
                // 大于区索引减1
                moreIdx--;
            } else {
                // 相等则直接遍历下一个数去
                index++;
            }
        }
        /*
         * 此时的数组划分：
         *  [left, lessThanIdx] 小于区
         *  [lessThanIdx+1, greatThanIdx-1] 等于区
         *  [greatThanIdx, right-1] 大于区
         *  right位置的数还没处理，应该要放在等于区，所以array[right]要换到大于区的第一个位置
         *  即array[right]和array[greatThanIdx]互换
         */
        // array[right]换到等于区，也就是array[right]换到大于区的第一个位置
        swap(array, moreIdx, right);
        /*
         * 最终数组的划分：
         *  [left, lessThanIdx] 小于区
         *  [lessThanIdx+1, greatThanIdx] 等于区
         *  [greatThanIdx+1, right] 大于区
         */
        return new int[]{lessIdx + 1, moreIdx};
    }

    private static void swap(int[] array, int a, int b) {
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    /* *****************快排循环方式实现***************************************************************************** */

    public static void main(String[] args) {
        int[] array = {5, 3, 7, 2, 3, 4, 1};
        System.out.println("merge sort before: " + Arrays.toString(array));
        quickSort(array);
        System.out.println("merge sort after:  " + Arrays.toString(array));
    }
}
