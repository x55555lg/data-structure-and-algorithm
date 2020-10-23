package com.lg.datastructure.sort.radixsort;

import java.util.Arrays;

/**
 * 基数排序2.0
 * 这个实现太骚了，理解费劲
 * 时间复杂度：O(N) + lg(M) M是数组中最大值的位数
 * 局限性：必须是十进制的整数
 *
 * @author Xulg
 * Created in 2020-10-22 10:35
 */
@SuppressWarnings({"SameParameterValue", "ManualArrayCopy"})
class RadixSort2 {

    /**
     * 基数排序
     *
     * @param array the array
     */
    public static void radixSort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        doRadixSort(array, 0, array.length - 1, calcMaxBits(array));
    }

    private static void doRadixSort(int[] array, int left, int right, int maxBits) {
        // 定义临时数组，长度和array一样
        int[] helper = new int[right - left + 1];
        // 有多少位就循环几次：个位，十位，百位，千位......
        for (int bit = 1; bit <= maxBits; bit++) {
            // 申请一个长度为10的数组表示十进制，index[0, 9]表示十进制的10个数
            int[] counts = new int[10];
            // 分别统计第bit位是0,1,2,3,4,5,6,7,8,9的数量
            for (int i = left; i <= right; i++) {
                // 第bit位的值
                int digit = getDigit(array[i], bit);
                counts[digit]++;
            }
            // 后一位加上前一位的值
            // counts[1] = 3; 表示第bit位<=1的有3个
            // 执行完此循环之后的(counts[i]-1)就是第i个桶右边界的位置
            for (int i = 1; i < counts.length; i++) {
                counts[i] += counts[i - 1];
            }
            // 逆序遍历array
            for (int i = right; i >= left; i--) {
                // 第bit位的值
                int digit = getDigit(array[i], bit);
                int idx = counts[digit] - 1;
                helper[idx] = array[i];
                // 处理一个数就减1
                counts[digit]--;
            }
            // 将临时数组的数据写回原数组
            for (int i = 0; i < helper.length; i++) {
                array[i + left] = helper[i];
            }
        }
    }

    /**
     * 取数字的某一位
     *
     * @param value the int value
     * @param bit   取第几位的值(个位,十位,百位...)
     * @return 数字的某一位
     */
    private static int getDigit(int value, int bit) {
        return (value / ((int) Math.pow(10, bit - 1))) % 10;
    }

    /**
     * 求数组中最大值的位数
     *
     * @param array the array
     * @return the max bits
     */
    private static int calcMaxBits(int[] array) {
        // 找出数组的最大值
        int max = array[0];
        for (int value : array) {
            max = Math.max(max, value);
        }
        // 算出max值有几位
        int digit = 0;
        while (max != 0) {
            digit++;
            max = max / 10;
        }
        return digit;
    }

    public static void main(String[] args) {
        int[] array = {321, 12, 9, 21, 3, 75, 101};
        radixSort(array);
        System.out.println("基数排序2.0之后：" + Arrays.toString(array));
    }
}
