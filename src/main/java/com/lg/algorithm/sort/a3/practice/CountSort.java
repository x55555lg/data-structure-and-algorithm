package com.lg.algorithm.sort.a3.practice;

import cn.hutool.core.util.RandomUtil;
import com.lg.algorithm.sort.a3.ComparatorSort;

import java.util.Arrays;

/**
 * @author Xulg
 * @since 2021-09-02 21:02
 * Description: 计数排序，只能是非负整数
 */
class CountSort {

    public static void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int maxVal = 0;
        for (int val : arr) {
            assert val >= 0 : "计数排序要求必须是非负数";
            maxVal = Math.max(val, maxVal);
        }
        // 计数数组
        int[] helper = new int[maxVal + 1];
        for (int val : arr) {
            helper[val]++;
        }
        int idx = 0;
        for (int val = 0; val < helper.length; val++) {
            int count = helper[val];
            if (count > 0) {
                for (int c = 0; c < count; c++) {
                    arr[idx++] = val;
                }
            }
        }
    }

    /* ****************************************************************************************************************/

    public static void main(String[] args) {
        int times = 100_0000;
        for (int time = 0; time < times; time++) {
            int[] arr = new int[RandomUtil.randomInt(100)];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = RandomUtil.randomInt(0, 1000);
            }
            int[] arr1 = arr.clone();
            int[] arr2 = arr.clone();
            sort(arr1);
            ComparatorSort.sort(arr2);
            if (!Arrays.equals(arr1, arr2)) {
                System.out.println("fucking");
            }
        }
        System.out.println("good job");
    }

}
