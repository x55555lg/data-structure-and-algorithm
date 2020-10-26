package com.lg.datastructure.sort.a.practice;

/**
 * 基数排序
 *
 * @author Xulg
 * Created in 2020-10-26 10:56
 */
@SuppressWarnings({"ForLoopReplaceableByForEach", "SameParameterValue"})
class RadixSort {

    /*
     * 时间复杂度：O(N)
     * 空间复杂度：O(N)
     * 稳定
     */

    public static void sort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        // 计算数组中最大值的位数
        int maxBit = calcMaxBit(array);
        // 进行基数排序
        doSort(array, 0, array.length - 1, maxBit);
    }

    private static void doSort(int[] array, int left, int right, int maxBit) {
        // 循环每个数的bit位
        for (int bit = 1; bit <= maxBit; bit++) {

        }
    }

    /**
     * 计算数组中最大值的位数
     *
     * @param array the array
     * @return the max bits of the array
     */
    private static int calcMaxBit(int[] array) {
        int maxVal = array[0];
        for (int i = 0; i < array.length; i++) {
            maxVal = Math.max(maxVal, array[i]);
        }
        // 计算最大值有几位
        int bitCount = 0;
        while (true) {
            int remainVal = maxVal / 10;
            if (remainVal == 0) {
                break;
            }
            bitCount++;
        }
        return bitCount;
    }

    /**
     * 获取数字第bit位的值
     * getDigit(123, 1) ====>>> 3
     * 获取个位数
     * getDigit(123, 2) ====>>> 2
     * 获取十位数
     * getDigit(123, 3) ====>>> 1
     * 获取百位数
     *
     * @param value the value
     * @param bit   the bit
     */
    private static int getDigit(int value, int bit) {
        int pow = (int) Math.pow(10, bit);
        return value / pow % 10;
    }
}
