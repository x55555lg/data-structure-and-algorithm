package com.lg.algorithm.sort.a2.practice;

import cn.hutool.core.util.RandomUtil;

import java.util.Arrays;

/**
 * 计数排序
 * 时间复杂度：O(n)
 * 空间复杂度：O(M)(M是数组中的最大值)
 * 稳定性：稳定
 *
 * @author Xulg
 * Created in 2021-01-18 10:51
 */
class CountSort {

    public static void sort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        // 计数排序，每一项必须都非负且必须是整数
        // 找出数组中的最大值
        int maxVal = Integer.MIN_VALUE;
        for (int val : arr) {
            assert val >= 0 : "计数排序要求必须是非负数";
            maxVal = Math.max(maxVal, val);
        }

        // 申请一个临时数组，长度为能装下[0, maxVal]
        int[] helper = new int[maxVal + 1];

        // 统计数组中各个值出现的次数
        for (int val : arr) {
            helper[val]++;
        }

        // 将数据按照出现次数写回原数组
        int idx = 0;
        for (int val = 0; val < helper.length; val++) {
            // 数组中有count个val
            int count = helper[val];
            // 写回原数组
            for (int j = 0; j < count; j++) {
                arr[idx++] = val;
            }
        }
    }

    public static void main(String[] args) {
        int times = 1000000;
        for (int time = 0; time < times; time++) {
            int length = 20;
            int[] arr = new int[length];
            for (int i = 0; i < arr.length; i++) {
                // [0,9999]
                arr[i] = RandomUtil.randomInt(0, 10000);
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
