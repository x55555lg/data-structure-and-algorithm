package com.lg.algorithm.sort.a2.practice;

import cn.hutool.core.util.RandomUtil;

import java.util.Arrays;

/**
 * 快排
 * 时间复杂度：O(N*logN)
 * 空间复杂度：O(logN)
 * 稳定性：不稳定
 *
 * @author Xulg
 * Created in 2021-01-18 10:55
 */
@SuppressWarnings({"SameParameterValue", "DuplicatedCode"})
public class QuickSort {

    public static void sort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }

        // 加入随机，降低时间复杂度
        int randomIdx = (int) (Math.random() * (right - left + 1) + left);
        swap(arr, randomIdx, right);

        int[] equalToZone = partition(arr, left, right);

        quickSort(arr, left, equalToZone[0] - 1);
        quickSort(arr, equalToZone[1] + 1, right);
    }

    /**
     * 荷兰旗问题
     * 将原数组arr中：
     * 小于num的放数组左边
     * 等于num的放数组中间
     * 大于num的放数组右边
     *
     * @param arr   the array
     * @param left  the left index of array
     * @param right the right index of array
     * @param num   the compare value
     * @return the equal to zone index
     */
    private static int[] partition(int[] arr, int left, int right, int num) {
        if (left > right) {
            return new int[]{-1, -1};
        }
        if (left == right) {
            return new int[]{left, right};
        }

        // 小于区起始位置
        int lessThanZoneIdx = left - 1;
        // 大于区的截止位置
        int greaterThanIdx = right + 1;
        // 等于区：[lessThanZoneIdx+1, greaterThanIdx-1]

        // 遍历数组arr[left, greaterThanIdx)
        for (int idx = left; idx < greaterThanIdx; ) {
            int val = arr[idx];
            if (val < num) {
                // 当前位置的值小于num
                // 将当前位置的数移动到小于区去
                swap(arr, ++lessThanZoneIdx, idx++);
            } else if (val > num) {
                // 当前位置的值大于num
                // 将当前位置的数移动到大于区去
                swap(arr, --greaterThanIdx, idx);
            } else {
                // 当前位置的值等于num，直接判断下一个位置去
                idx++;
            }
        }
        if (lessThanZoneIdx + 1 > greaterThanIdx - 1) {
            // 没有等于区
            return new int[]{-1, -1};
        } else {
            return new int[]{lessThanZoneIdx + 1, greaterThanIdx - 1};
        }
    }

    /**
     * 荷兰旗问题
     * 将原数组arr中：
     * 小于arr[right]的放数组左边
     * 等于arr[right]的放数组中间
     * 大于arr[right]的放数组右边
     *
     * @param arr   the array
     * @param left  the left index of array
     * @param right the right index of array
     * @return the equal to zone index
     */
    private static int[] partition(int[] arr, int left, int right) {
        if (left > right) {
            return new int[]{-1, -1};
        }
        if (left == right) {
            return new int[]{left, right};
        }
        int num = arr[right];
        int lessThanZoneIdx = left - 1;
        int greaterThanZoneIdx = right;
        for (int idx = left; idx < greaterThanZoneIdx; ) {
            int val = arr[idx];
            if (val < num) {
                swap(arr, ++lessThanZoneIdx, idx++);
            } else if (val > num) {
                swap(arr, --greaterThanZoneIdx, idx);
            } else {
                idx++;
            }
        }
        swap(arr, right, greaterThanZoneIdx);
        return new int[]{lessThanZoneIdx + 1, greaterThanZoneIdx};
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        {
            int[] arr = {5, 3, 1, 2, 3, 4};
            int[] partition = partition(arr, 0, arr.length - 1, 4);
            System.out.println(Arrays.toString(partition));
            arr = new int[]{6, 5, 2, 3, 2, 1};
            partition = partition(arr, 0, arr.length - 1, 1);
            System.out.println(Arrays.toString(partition));

            arr = new int[]{5, 3, 1, 2, 3};
            partition = partition(arr, 0, arr.length - 1, 4);
            System.out.println(Arrays.toString(partition));
        }

        {
            int[] arr = {5, 3, 4, 2, 3, 4};
            int[] partition = partition(arr, 0, arr.length - 1);
            System.out.println(Arrays.toString(partition));
            arr = new int[]{6, 5, 2, 3, 2, 1};
            partition = partition(arr, 0, arr.length - 1);
            System.out.println(Arrays.toString(partition));
        }

        {
            int times = 1000000;
            for (int time = 0; time < times; time++) {
                int length = RandomUtil.randomInt(0, 100);
                int[] arr = new int[length];
                for (int i = 0; i < arr.length; i++) {
                    arr[i] = RandomUtil.randomInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
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
    }

}
