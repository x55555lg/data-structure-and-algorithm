package com.lg.datastructure.array;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * 数组相关的操作
 *
 * @author Xulg
 * Created in 2020-09-15 18:26
 */
class ArrayDemo {

    @Test
    public void testReversedArray() {
        int[] array = {1, 2, 3, 4, 5, 6, 8, 7, 9};
        System.out.println("Before Reversed: " + Arrays.toString(array));
        this.reversedArray1(array);
        System.out.println("After Reversed: " + Arrays.toString(array));

        System.out.println("==========================================");

        System.out.println("Before Reversed: " + Arrays.toString(array));
        this.reversedArray2(array);
        System.out.println("After Reversed: " + Arrays.toString(array));
    }

    /**
     * 反转数组
     */
    private void reversedArray1(int[] array) {
        // 数组内容和索引
        // 1    2   3   4   5   6   8   7   9
        // 0    1   2   3   4   5   6   7   8

        // 0 <-> 8  对换
        // 1 <-> 7  对换
        // 2 <-> 6  对换
        // 3 <-> 5  对换

        // i = 0, j = 9 -1 - 0;
        // i = 1, j = 9 -1 - 1;
        // i = 2, j = 9 -1 - 2;
        // i = 3, j = 9 -1 - 3;

        // 中间索引位置
        int middleIndex = array.length / 2;

        for (int i = 0; i < middleIndex; i++) {
            // 计算需要交换的位置的索引
            int swapIndex = array.length - 1 - i;
            int value = array[i];

            // 交换i和swapIndex处的数据
            array[i] = array[swapIndex];
            array[swapIndex] = value;
        }
    }

    /**
     * 反转数组
     */
    @SuppressWarnings("ConditionalBreakInInfiniteLoop")
    private void reversedArray2(int[] array) {
        // 数组内容和索引
        // 1    2   3   4   5   6   8   7   9
        // 0    1   2   3   4   5   6   7   8

        // start    0   1   2   3   4   5   6   7   8
        //   end    8   7   6   5   4   3   2   1   0

        int start = 0;
        int end = array.length - 1;
        while (true) {
            // 遍历完了跳出
            if (start >= end) {
                break;
            }

            // 交换值
            int value = array[start];
            array[start] = array[end];
            array[end] = value;

            // 起始索引+1
            start++;
            // 截止索引-1
            end--;
        }
    }

    /* ************************************************************************************************************** */

    @Test
    public void testBinarySearch() {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int i = binarySearch1(array, 8);
        System.out.println(i);
        i = binarySearch1(array, 2);
        System.out.println(i);
        i = binarySearch1(array, 12);
        System.out.println(i);

        System.out.println("==================================================");

        // 递归实现的二分查找
        i = binarySearch2(array, 8);
        System.out.println(i);
        i = binarySearch2(array, 2);
        System.out.println(i);
        i = binarySearch2(array, 12);
        System.out.println(i);
    }

    /**
     * 二分查询，前提是数组必须是有序的
     *
     * @param array the array to be searched
     * @param key   the value to be searched for
     * @return the index of the key in the array
     */
    @SuppressWarnings("ConditionalBreakInInfiniteLoop")
    private int binarySearch1(int[] array, int key) {
        // 数组内容和索引
        // 1    2   3   4   5   6   7   8   9
        // 0    1   2   3   4   5   6   7   8

        int start = 0;
        int end = array.length - 1;
        while (true) {
            // 遍历完了就跳出
            if (start > end) {
                break;
            }

            // 数组的中间索引位置和值
            // 等价写法：int middleIndex = start + ((end - start) >> 1);
            int middleIndex = (start + end) / 2;
            int middleValue = array[middleIndex];

            // 中间值就是查询的数据
            if (middleValue == key) {
                // 返回索引值
                return middleIndex;
            }
            // 在中间值的右边
            else if (key > middleValue) {
                start = middleIndex + 1;
            }
            // 在中间值的左边
            else {
                end = middleIndex - 1;
            }
        }
        return -1;
    }

    /**
     * 二分查询，前提是数组必须是有序的
     *
     * @param array the array to be searched
     * @param key   the value to be searched for
     * @return the index of the key in the array
     */
    private int binarySearch2(int[] array, int key) {
        return binarySearchByRecursion(array, key, 0, array.length - 1);
    }

    /**
     * 使用递归实现二分查找
     *
     * @param array the array to be searched
     * @param key   the value to be searched for
     * @param start the start index to be searched
     * @param end   the end index to be searched
     * @return the index of the key in the array
     */
    private int binarySearchByRecursion(int[] array, int key, int start, int end) {
        // 数组内容和索引
        // 1    2   3   4   5   6   7   8   9
        // 0    1   2   3   4   5   6   7   8

        // 遍历完了就跳出
        if (start > end) {
            return -1;
        }

        // 数组的中间索引位置和值
        int middleIndex = (start + end) / 2;
        int middleValue = array[middleIndex];

        // 中间值就是查询的数据
        if (key == middleValue) {
            return middleIndex;
        }
        // 在中间值的右边
        else if (key > middleValue) {
            return binarySearchByRecursion(array, key, middleIndex + 1, end);
        }
        // 在中间值的左边
        else {
            return binarySearchByRecursion(array, key, start, middleIndex - 1);
        }
    }
}
