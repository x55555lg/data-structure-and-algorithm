package com.lg.algorithm.sort.a2.practice;

import cn.hutool.core.util.RandomUtil;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * 基数排序
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 * 稳定性：稳定
 *
 * @author Xulg
 * Created in 2021-01-18 10:56
 */
class RadixSort {

    // TODO: 2021/1/19 已经忘的差不多了，重新看看

    public static void main(String[] args) {
        {
            int[] arr = {913, 19, 11, 8, 1, 123, 5};
            sort(arr);
            System.out.println(Arrays.toString(arr));
        }
        int times = 1000000;
        for (int time = 0; time < times; time++) {
            int length = RandomUtil.randomInt(0, 100);
            int[] arr = new int[length];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = RandomUtil.randomInt(0, Integer.MAX_VALUE);
            }
            int[] arr1 = arr.clone();
            int[] arr2 = arr.clone();
            Arrays.sort(arr1);
            sort(arr2);
            if (!Arrays.equals(arr1, arr2)) {
                System.out.println("Oops");
            }
        }
        System.out.println("finish!");
    }

    /**
     * 基数排序，这里的写法必须是自然数(0和正整数)
     */
    public static void sort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        radixSort(arr, 0, arr.length - 1, maxBits(arr));
    }

    @SuppressWarnings({"SameParameterValue", "unchecked"})
    private static void radixSort(int[] arr, int left, int right, int maxBits) {
        // 十进制 0~9
        LinkedList<Integer>[] queues = new LinkedList[10];
        for (int i = 0; i < queues.length; i++) {
            queues[i] = new LinkedList<>();
        }
        for (int bit = 1; bit <= maxBits; bit++) {
            for (int idx = left; idx <= right; idx++) {
                int bitVal = calcBitVal(arr[idx], bit);
                queues[bitVal].addFirst(arr[idx]);
            }
            int index = left;
            for (LinkedList<Integer> queue : queues) {
                while (!queue.isEmpty()) {
                    arr[index++] = queue.pollLast();
                }
            }
        }
    }

    /**
     * 计算数组中最大值是几位数
     */
    @SuppressWarnings("DuplicatedCode")
    private static int maxBits(int[] arr) {
        assert arr != null && arr.length > 0;
        // find the max value in the array
        int maxVal = arr[0];
        for (int val : arr) {
            assert val >= 0;
            maxVal = Math.max(maxVal, val);
        }
        // calculate the max value bits
        int bits = 0;
        while (maxVal != 0) {
            bits++;
            maxVal = maxVal / 10;
        }
        return bits;
    }

    /**
     * 取数字的某一位
     *
     * @param val the int value
     * @param bit 取第几位的值(个位,十位,百位...)
     * @return 数字的某一位
     */
    private static int calcBitVal(int val, int bit) {
        return val / ((int) Math.pow(10, bit - 1)) % 10;
    }

}
