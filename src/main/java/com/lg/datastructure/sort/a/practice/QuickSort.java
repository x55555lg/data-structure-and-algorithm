package com.lg.datastructure.sort.a.practice;

import java.util.Arrays;

/**
 * 随机快排
 *
 * @author Xulg
 * Created in 2020-10-23 17:09
 */
@SuppressWarnings("SameParameterValue")
class QuickSort {

    /*
     * 时间复杂度：O(N*logN)
     * 空间复杂度：O(logN)
     * 不稳定
     */

    /**
     * 递归方式的快速排序
     */
    public static void sortByRecurse(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        doSortByRecurse(array, 0, array.length - 1);
    }

    private static void doSortByRecurse(int[] array, int left, int right) {
        if (left >= right) {
            // 退出递归
            return;
        }

        // 随机在[left, right]中取一个索引位置和最后一个元素交换
        int randomIdx = (int) (Math.random() * (right - left + 1) + left);
        swap(array, randomIdx, right);

        // 进行分区
        int[] equalsAreaIndex = partition(array, left, right);
        // 对小于区进行分区
        doSortByRecurse(array, left, equalsAreaIndex[0] - 1);
        // 对大于区进行分区
        doSortByRecurse(array, equalsAreaIndex[1] + 1, right);
    }

    /**
     * 对数组在[left, right-1]上进行分区
     * ---小于array[right]的数放数组的左边
     * ---等于array[right]的数放数组的中间
     * ---大于array[right]的数放数组的右边
     * 返回：等于区的开始索引和结束索引
     *
     * @param array the array
     * @param left  the start index of the array
     * @param right the end index of the array
     * @return the start and end index of the equals area
     */
    private static int[] partition(int[] array, int left, int right) {
        // 小于区的开始索引，只能往右边走
        int lessThanIdx = left - 1;
        // 大于区的截止索引，只能往左边走
        int greatThanIdx = right;
        // 遍历array，不能越过大于区的边界
        for (int idx = left; idx < greatThanIdx; ) {
            if (array[idx] < array[right]) {
                // 和小于区的下一个位置交换
                swap(array, idx, lessThanIdx + 1);
                // 小于区的索引加1
                lessThanIdx++;
                // 判断下一个数去
                idx++;
            } else if (array[idx] > array[right]) {
                // 和大于区的前一个位置交换
                swap(array, idx, greatThanIdx - 1);
                // 大于区的索引减1
                greatThanIdx--;
                // idx不需要加1，因为从大于区换过来的数不知道大小的，需要判断过的
            } else {
                // 相等的话，直接判断下一个数去
                idx++;
            }
        }
        // 将array[right]值放到等于区的最后面去，也就是大于区的前一个位置啊
        // 这里只能放等于区的最后，这样算法才有稳定性
        swap(array, greatThanIdx, right);
        // 等于区间的索引位置
        return new int[]{lessThanIdx + 1, greatThanIdx};
    }

    private static void swap(int[] array, int a, int b) {
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    public static void main(String[] args) {
        int[] array = {5, 5, 3, 4, 3, 3, 4};

        int[] array2 = array.clone();
        int[] partition = partition(array2, 0, array2.length - 1);
        System.out.println(Arrays.toString(partition));

        int[] array3 = array.clone();
        sortByRecurse(array3);
        System.out.println("非递归after sort: " + Arrays.toString(array3));
    }
}
