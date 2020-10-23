package com.lg.datastructure.sort.radixsort;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * 基数排序1.0
 * 时间复杂度：O(N)*lg(M) M是数组中最大值的位数
 * 局限性：必须是十进制的整数
 *
 * @author Xulg
 * Created in 2020-10-22 10:35
 */
@SuppressWarnings({"DuplicatedCode", "SameParameterValue", "unchecked"})
class RadixSort1 {

    /*
     * 基数排序的实现思想：
     *      1.算出数组中最大值是几位数，记为maxBits
     *      2.定义长度为10的队列数组countQueues(因为是十进制所有长度为10)
     *      3.遍历整个数组，取各个数value的个位数digit，将digit作为下标，从队列数组中选择对应的队列，将数组的值加入
     *              countQueues[digit].addFirst[value];
     *      4.遍历整个队列数组，按照先进先出的原则，将所有队列中的值写回原数组
     *      5.遍历整个数组，取各个数value的十位数digit，将digit作为下标，从队列数组中选择对应的队列，将数组的值加入
     *              countQueues[digit].addFirst[value];
     *      6.遍历整个队列数组，按照先进先出的原则，将所有队列中的值写回原数组
     *      ......
     *      7.遍历整个数组，取各个数value的maxBits位数digit，将digit作为下标，从队列数组中选择对应的队列，将数组的值加入
     *              countQueues[digit].addFirst[value];
     *      8.遍历整个队列数组，按照先进先出的原则，将所有队列中的值写回原数组
     */

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
        // 准备10个队列
        LinkedList<Integer>[] countQueues = new LinkedList[10];
        for (int i = 0; i < countQueues.length; i++) {
            countQueues[i] = new LinkedList<>();
        }
        for (int bit = 1; bit <= maxBits; bit++) {
            // 将数组中的值按照计算的对应位的值，选择存入哪个队列
            for (int i = left; i <= right; i++) {
                // 取数字对应位的数
                int digit = getDigit(array[i], bit);
                // 根据对应位的数选择队列，加入数值
                countQueues[digit].addFirst(array[i]);
            }
            // 遍历每个队列，按照先进先出的原则将数写回原数组
            int idx = left;
            for (LinkedList<Integer> queue : countQueues) {
                while (!queue.isEmpty()) {
                    array[idx++] = queue.pollLast();
                }
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
        System.out.println("基数排序1.0之后：" + Arrays.toString(array));
    }
}
