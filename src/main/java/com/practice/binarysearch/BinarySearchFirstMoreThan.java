package com.practice.binarysearch;

import cn.hutool.core.util.RandomUtil;

import java.util.Arrays;

/**
 * @author Xulg
 * Created in 2021-05-08 9:23
 */
class BinarySearchFirstMoreThan {

    /*
     * 查找第一个值大于等于给定值的元素，数组可能有重复
     */

    private static class BruteForce {
        public static int binarySearchFirstMoreThan(int[] arr, int key) {
            if (arr == null || arr.length == 0) {
                return -1;
            }
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] >= key) {
                    return i;
                }
            }
            return -1;
        }
    }

    private static class Traverse {
        public static int binarySearchFirstMoreThan(int[] arr, int key) {
            if (arr == null || arr.length == 0) {
                return -1;
            }
            int low = 0, high = arr.length - 1;
            while (low <= high) {
                int mid = low + ((high - low) >> 1);
                int midVal = arr[mid];
                if (midVal >= key) {
                    if (mid == 0 || arr[mid - 1] < key) {
                        // mid就是第一个大于等于的位置
                        return mid;
                    } else {
                        // 不是第一个，左边还有
                        high = mid - 1;
                    }
                } else {
                    // midVal < key;
                    low = mid + 1;
                }
            }
            return -1;
        }
    }

    public static void main(String[] args) {
        int times = 1000000;
        for (int time = 0; time < times; time++) {
            int[] arr = new int[RandomUtil.randomInt(1, 101)];
            for (int i = 0; i < arr.length; ) {
                int v = RandomUtil.randomInt(0, 100000);
                arr[i++] = v;
            }
            int val = RandomUtil.randomBoolean() ? RandomUtil.randomInt() : arr[RandomUtil.randomInt(0, arr.length)];
            Arrays.sort(arr);
            int r0 = BruteForce.binarySearchFirstMoreThan(arr.clone(), val);
            int r1 = Traverse.binarySearchFirstMoreThan(arr.clone(), val);
            if (!(r0 == r1)) {
                System.out.println("fucking......");
            }
        }
        System.out.println("good job");
    }

}
