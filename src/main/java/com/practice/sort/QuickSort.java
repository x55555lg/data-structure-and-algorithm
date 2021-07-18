package com.practice.sort;

import cn.hutool.core.util.RandomUtil;

import java.util.Arrays;

/**
 * @author Xulg
 * Created in 2021-04-29 11:06
 */
class QuickSort {

    public static void sort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        recurse(arr, 0, arr.length - 1);
    }

    private static void recurse(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int randomIdx = left + (int) (Math.random() * (right - left + 1));
        int pivot = arr[randomIdx];
        int[] equalsZone = partition(arr, left, right, pivot);
        int equalsStart = equalsZone[0], equalsEnd = equalsZone[1];
        recurse(arr, left, equalsStart - 1);
        recurse(arr, equalsEnd + 1, right);
    }

    private static int[] partition(int[] arr, int left, int right, int pivot) {
        if (left > right) {
            return new int[]{-1, -1};
        }
        if (left == right) {
            return new int[]{left, right};
        }
        int lessThanIdx = left - 1;
        int moreThanIdx = right + 1;
        for (int idx = left; idx < moreThanIdx; ) {
            if (arr[idx] < pivot) {
                swap(arr, idx++, ++lessThanIdx);
            } else if (arr[idx] > pivot) {
                swap(arr, idx, --moreThanIdx);
            } else {
                assert arr[idx] == pivot;
                idx++;
            }
        }
        if (lessThanIdx + 1 > moreThanIdx - 1) {
            return new int[]{-1, -1};
        } else {
            return new int[]{lessThanIdx + 1, moreThanIdx - 1};
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int times = 1000000;
        for (int time = 0; time < times; time++) {
            int[] arr = new int[100];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = RandomUtil.randomInt();
            }
            int[] arr1 = arr.clone();
            Arrays.sort(arr1);
            int[] arr2 = arr.clone();
            sort(arr2);
            if (!Arrays.equals(arr1, arr2)) {
                System.out.println("fucking......");
                return;
            }
        }
        System.out.println("success......");
    }

}
