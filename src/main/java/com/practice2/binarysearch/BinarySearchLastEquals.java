package com.practice2.binarysearch;

import cn.hutool.core.util.RandomUtil;

import java.util.Arrays;

/**
 * @author Xulg
 * @since 2021-10-13 23:22
 */
class BinarySearchLastEquals {
    private static class BruteForce {
        public static int binarySearchLastEquals(int[] arr, int key) {
            if (arr == null || arr.length == 0) {
                return -1;
            }
            for (int i = arr.length - 1; i >= 0; i--) {
                if (arr[i] == key) {
                    return i;
                }
            }
            return -1;
        }
    }

    private static class Traverse {
        public static int binarySearchLastEquals(int[] arr, int key) {
            if (arr == null || arr.length == 0) {
                return -1;
            }
            int low = 0, high = arr.length - 1;
            while (low <= high) {
                int middle = low + ((high - low) >> 1);
                if (arr[middle] > key) {
                    high = middle - 1;
                } else if (arr[middle] < key) {
                    low = middle + 1;
                } else {
                    if (middle == arr.length - 1 || arr[middle + 1] != key) {
                        return middle;
                    } else {
                        low = middle + 1;
                    }
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
            int key = RandomUtil.randomBoolean() ? RandomUtil.randomInt() : arr[RandomUtil.randomInt(0, arr.length)];
            Arrays.sort(arr);
            int r0 = BruteForce.binarySearchLastEquals(arr.clone(), key);
            int r1 = Traverse.binarySearchLastEquals(arr.clone(), key);
            if (!(r0 == r1)) {
                System.out.println("fucking......");
            }
        }
        System.out.println("good job");
    }

}
