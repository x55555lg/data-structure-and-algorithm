package com.practice.binarysearch;

import cn.hutool.core.util.RandomUtil;

import java.util.Arrays;

/**
 * @author Xulg
 * Created in 2021-05-07 22:55
 */
class BinarySearchFirstEquals {

    private static class BruteForce {
        public static int binarySearchFirst(int[] arr, int key) {
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

    private static class Traverse {
        public static int binarySearchFirst(int[] arr, int key) {
            if (arr == null || arr.length == 0) {
                return -1;
            }
            int low = 0, high = arr.length - 1;
            while (low <= high) {
                int mid = low + ((high - low) >> 1);
                int midVal = arr[mid];
                if (midVal > key) {
                    high = mid - 1;
                } else if (midVal < key) {
                    low = mid + 1;
                } else {
                    assert midVal == key;
                    if (mid == 0 || arr[mid - 1] != key) {
                        return mid;
                    } else {
                        // mid位置的前一个元素也是等于key啊，所以第一个等于key的值还在左边
                        high = mid - 1;
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
            int r0 = BruteForce.binarySearchFirst(arr.clone(), key);
            int r1 = Traverse.binarySearchFirst(arr.clone(), key);
            if (!(r0 == r1)) {
                System.out.println("fucking......");
            }
        }
        System.out.println("good job");
    }
}
