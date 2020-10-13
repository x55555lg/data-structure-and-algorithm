package com.lg.datastructure.sort;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * 快速排序
 * 时间复杂度：n*log(n)
 *
 * @author Xulg
 * Created in 2020-09-27 8:36
 */
@SuppressWarnings("WeakerAccess")
class QuickSort {

    /*
     * 快排的前置问题：
     *      给定一个数组array，和一个整数num，请把小于等于num的数放在数组的左边，大于num的数放在数组的右边。
     *      要求额外的空间复杂度O(1)，时间复杂度O(n)
     * 思路：设置一个小于等于区，起始index=-1，遍历array数组，
     *       如果array[i] > num，直接下一个(i++)
     *       如果array[i] <= num，array[i]和小于等于区的下一个值交换，即array[i]和array[index+1]交换
     */

    @Test
    public void test1() {
        int[] array = {5, 3, 7, 2, 3, 4, 1};
        partition1(array, 3);
        System.out.println(Arrays.toString(array));
    }

    /**
     * 将数组中小于等于value的值放数组的左边，大于的值放数组的右边
     * 要求：空间复杂度O(1)，时间复杂度O(n)
     *
     * @param array the array
     * @param value the compare value
     */
    public static void partition1(int[] array, int value) {
        // 小于等于区的起始索引
        int lessThanIndex = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] <= value) {
                // 小于等于区的下一个位置和当前的数进行交换
                swap(array, lessThanIndex + 1, i);
                // 小于等于区的索引右移
                lessThanIndex++;
            }
        }
    }

    private static void swap(int[] array, int a, int b) {
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    /*
     * 快排的前置问题：荷兰旗问题(https://www.jianshu.com/p/356604b8903f)
     *      给定一个数组array，和一个整数num，请把小于num的数放在数组的左边，
     *      等于num的数放在数组中间，大于num的数放在数组的右边。
     *      要求额外的空间复杂度O(1)，时间复杂度O(n)
     * 思路：设置一个小于区lessThanIndex，一个大于区greatThanIndex，中间是一个等于区，遍历array数组，
     *      如果array[i] < num，把小于区的下一个数和array[i]互换，i++
     *      如果array[i] == num，直接i++
     *      如果array[i] > num，把大于区的前一个数和array[i]互换，i不变
     */

    public static void partition2(int[] array, int value) {
        // 小于区的索引
        int lessThanIndex = -1;
        // 大于区的索引
        int greatThanIndex = array.length;
        // 遍历数组，遍历直到大于区位置就结束了
        for (int i = 0; i < greatThanIndex; ) {
            if (array[i] < value) {
                // 把小于区的下一个数和array[i]互换
                swap(array, lessThanIndex + 1, i);
                // 小于区索引增1
                lessThanIndex++;
                i++;
            } else if (array[i] > value) {
                // 把大于区的前一个数和array[i]互换，这里不需要i++，因为
                // 从大于区换过来的数是不确定大小的，需要重新判断过
                swap(array, greatThanIndex - 1, i);
                // 大于区索引减1
                greatThanIndex--;
            } else {
                // 相等则直接遍历下一个数去
                i++;
            }
        }
    }

    @Test
    public void test2() {
        int[] array = {5, 3, 7, 2, 3, 4, 1};
        partition2(array, 3);
        System.out.println(Arrays.toString(array));
    }
}
