package com.lg.algorithm.sort.a1.practice;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * 基数排序
 *
 * @author Xulg
 * Created in 2020-10-26 10:56
 */
@SuppressWarnings({"ForLoopReplaceableByForEach", "SameParameterValue", "ManualArrayCopy", "unchecked"})
class RadixSort {

    /*
     * 时间复杂度：O(N)
     * 空间复杂度：O(N)
     * 稳定
     */

    public static void radixSort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        // 计算数组中最大值的位数
        int maxBit = calcMaxBit(array);
        // 进行基数排序
        doSort2(array, 0, array.length - 1, maxBit);
    }

    /**
     * 好理解的简单实现
     */
    private static void doSort1(int[] array, int left, int right, int maxBit) {
        int radix = 10;
        // 有多少个数准备多少个辅助空间
        int[] temp = new int[right - left + 1];
        // 有多少位就进出几次
        for (int bit = 1; bit <= maxBit; bit++) {
            // index作为十进制的数[0, 9],array[index]的队列存放数据
            LinkedList<Integer>[] counts = new LinkedList[radix];
            for (int i = 0; i < counts.length; i++) {
                counts[i] = new LinkedList<>();
            }
            for (int i = left; i <= right; i++) {
                int digit = getDigit(array[i], bit);
                counts[digit].addFirst(array[i]);
            }
            int tempIdx = 0;
            for (int i = 0; i < counts.length; i++) {
                LinkedList<Integer> queue = counts[i];
                while (!queue.isEmpty()) {
                    Integer val = queue.pollLast();
                    temp[tempIdx++] = val;
                }
            }
            for (int i = 0; i < temp.length; i++) {
                array[i + left] = temp[i];
            }
        }
    }

    /**
     * 空间复杂度更优秀的实现
     */
    private static void doSort2(int[] array, int left, int right, int maxBit) {
        int radix = 10;
        // 有多少个数准备多少个辅助空间
        int[] temp = new int[right - left + 1];
        // 有多少位就进出几次
        for (int bit = 1; bit <= maxBit; bit++) {
            // index作为十进制的数[0, 9],array[index]作为计数
            // count[0] 当前位(bit位)是0的数字有多少个
            // count[1] 当前位(bit位)是(0和1)的数字有多少个
            // count[2] 当前位(bit位)是(0、1和2)的数字有多少个
            // count[i] 当前位(bit位)是(0~i)的数字有多少个
            int[] counts = new int[radix];
            for (int i = left; i <= right; i++) {
                int digit = getDigit(array[i], bit);
                counts[digit]++;
            }
            for (int i = 1; i < counts.length; i++) {
                counts[i] = counts[i] + counts[i - 1];
            }
            for (int i = right; i >= left; i--) {
                int digit = getDigit(array[i], bit);
                temp[counts[digit] - 1] = array[i];
                counts[digit]--;
            }
            for (int i = 0; i < temp.length; i++) {
                array[i + left] = temp[i];
            }
        }
    }

    /**
     * 计算数组中最大值的位数
     *
     * @param array the array
     * @return the max bits of the array
     */
    private static int calcMaxBit(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        int maxVal = array[0];
        for (int i = 0; i < array.length; i++) {
            maxVal = Math.max(maxVal, array[i]);
        }
        // 计算最大值有几位
        int remainVal = maxVal;
        int bitCount = 1;
        while (true) {
            remainVal = remainVal / 10;
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
        int pow = (int) Math.pow(10, bit - 1);
        return value / pow % 10;
    }

    /* 对数器 start */

    static void comparator(int[] arr) {
        Arrays.sort(arr);
    }

    static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random());
        }
        return arr;
    }

    static int[] copyArray(int[] arr) {
        return arr.clone();
    }

    static boolean isEqual(int[] arr1, int[] arr2) {
        return Arrays.equals(arr1, arr2);
    }

    static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        System.out.println(Arrays.toString(arr));
    }

    /* 对数器 end */

    public static void main(String[] args) {
        int[] array = {3, 1, 2, 8, 7, 5, 6, 4};
        radixSort(array);
        System.out.println("after sort: " + Arrays.toString(array));

        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100000;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            radixSort(arr1);
            comparator(arr2);
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");

        int[] arr = generateRandomArray(maxSize, maxValue);
        printArray(arr);
        radixSort(arr);
        printArray(arr);
    }
}
