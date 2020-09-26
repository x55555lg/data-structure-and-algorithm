package com.lg.datastructure.binary;

/**
 * 二分算法
 * 事件复杂度：O(logN)
 *
 * @author Xulg
 * Created in 2020-09-18 10:46
 */
public class BinaryDemo {

    /*
     * 二分的使用场景：
     * 1.查找一个数
     * 2.在array中，找出array[index] >= key的最左边的一个
     * 3.在array中，找出array[index] <= key的最右边的一个
     * 4.局部最小值问题
     *      什么叫局部最小？
     *          1.array[0]的数据比array[1]要小
     *          2.array[n]的数据比array[n-1]（n为数组的最后一个）
     *          3.array[k]的数据比array[k-1]，array[k+1]都小（0<k<n）
     *    题目：数组array无序，相邻两个数据都不相等，求一个局部最小值
     */

    public static void main(String[] args) {
        int[] array = {1, 1, 1, 2, 2, 2, 3, 3, 3, 5, 5, 6, 6, 9, 9};
        binary1(array, 3);
        binary2(array, 3);
    }

    /**
     * 在array中，找出array[index] >= key的最左边的一个
     *
     * @param array the array
     * @param key   the key
     */
    public static void binary1(int[] array, int key) {
        // 1   1   1   2   2   2   3   3   3   5   5   6   6   9   9
        // 0   1   2   3   4   5   6   7   8   9   10  11  12  13  14
        if (array == null || array.length <= 0) {
            return;
        }
        int start = 0;
        int end = array.length - 1;
        // 最左边的索引
        int index = -1;
        while (start <= end) {
            // 取中间索引
            // 等价写法：int middleIndex = (start + end) / 2;
            int middleIndex = start + ((end - start) >> 1);
            if (array[middleIndex] >= key) {
                index = middleIndex;
                end = middleIndex - 1;
            } else {
                start = middleIndex + 1;
            }
        }
        System.out.println(index);
    }

    /**
     * 在array中，找出array[index] <= key的最右边的一个
     *
     * @param array the array
     * @param key   the key
     */
    public static void binary2(int[] array, int key) {
        // 1   1   1   2   2   2   3   3   3   5   5   6   6   9   9
        // 0   1   2   3   4   5   6   7   8   9   10  11  12  13  14
        if (array == null || array.length <= 0) {
            return;
        }

        int start = 0;
        int end = array.length - 1;
        // 最右边的索引
        int index = -1;
        while (start <= end) {
            // 取中间索引
            int middle = start + ((end - start) >> 1);
            if (array[middle] <= key) {
                index = middle;
                start = middle + 1;
            } else {
                end = middle - 1;
            }
        }
        System.out.println(index);
    }

    /**
     * 数组array无序，相邻两个数据都不相等，求一个局部最小值
     *
     * @param array the array
     */
    public static int getLocalMin(int[] array) {

        // 值：

        // 局部最小值的索引
        if (array == null || array.length <= 0) {
            return -1;
        }
        if (array.length == 1) {
            return 0;
        }
        // array[0]是不是局部最小
        if (array[0] < array[1]) {
            return 0;
        }
        // array[n]是不是局部最小
        if (array[array.length - 1] < array[array.length - 2]) {
            return array.length - 1;
        }

        // array[1, array.length -2]之间找了咯
        int start = 1;
        int end = array.length - 2;
        while (start < end) {
            // 取中间索引和中间索引位置的值
            int middleIndex = (start + end) / 2;
            // 中间值比左边的大
            if (array[middleIndex] > array[middleIndex - 1]) {
                end = middleIndex - 1;
            }
            // 中间值比右边的大
            else if (array[middleIndex] > array[middleIndex + 1]) {
                start = middleIndex + 1;
            } else {
                // 中间值就是局部最小啊
                return middleIndex;
            }
        }
        return start;
    }
}
