package com.practice2.binarysearch;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.RandomUtil;

import java.util.Arrays;

/**
 * @author Xulg
 * @since 2021-10-13 22:52
 * Description: 二分查找
 */
class BinarySearch {

    private static class BruteForce {
        public static int binarySearch(int[] arr, int key) {
            if (arr == null || arr.length == 0) {
                return -1;
            }
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] == key) {
                    return i;
                }
            }
            return -1;
        }
    }

    private static class Recurse {
        public static int binarySearch(int[] arr, int key) {
            if (arr == null || arr.length == 0) {
                return -1;
            }
            return recurse(arr, 0, arr.length - 1, key);
        }

        private static int recurse(int[] arr, int low, int high, int key) {
            if (low > high) {
                return -1;
            }
            int middleIdx = ((high - low) >> 1) + low;
            if (arr[middleIdx] < key) {
                return recurse(arr, middleIdx + 1, high, key);
            } else if (arr[middleIdx] > key) {
                return recurse(arr, low, middleIdx - 1, key);
            } else {
                return middleIdx;
            }
        }
    }

    private static class Traverse {
        public static int binarySearch(int[] arr, int key) {
            if (arr == null || arr.length == 0) {
                return -1;
            }
            int left = 0, right = arr.length - 1;
            while (left <= right) {
                int middleIdx = ((right - left) >> 1) + left;
                if (arr[middleIdx] > key) {
                    right = middleIdx - 1;
                } else if (arr[middleIdx] < key) {
                    left = middleIdx + 1;
                } else {
                    return middleIdx;
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
                // 去重
                if (!ArrayUtil.contains(arr, v)) {
                    arr[i++] = v;
                }
            }
            int val = RandomUtil.randomBoolean() ? RandomUtil.randomInt() : arr[RandomUtil.randomInt(0, arr.length)];
            Arrays.sort(arr);
            int r0 = BruteForce.binarySearch(arr.clone(), val);
            int r1 = Recurse.binarySearch(arr.clone(), val);
            int r2 = Traverse.binarySearch(arr.clone(), val);
            if (!(r0 == r1 && r1 == r2)) {
                System.out.println("fucking......");
            }
        }
        System.out.println("good job");
    }

}
