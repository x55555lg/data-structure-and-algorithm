package com.lg.datastructure.sort.a.practice;

import java.util.Arrays;

/**
 * 计数排序
 *
 * @author Xulg
 * Created in 2020-10-26 10:46
 */
@SuppressWarnings("ForLoopReplaceableByForEach")
class CountSort {

    /*
     * 时间复杂度：O(N)
     * 空间复杂度：O(M)
     * 稳定
     */

    public static void sort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }

        // 找出数组中的最大值啊
        int maxVal = array[0];
        for (int i = 0; i < array.length; i++) {
            maxVal = Math.max(maxVal, array[i]);
        }

        // 申请一个临时数组，长度为能装下[0, maxVal]
        int[] bucket = new int[maxVal + 1];

        // 统计数组中各个值出现的次数
        for (int i = 0; i < array.length; i++) {
            bucket[array[i]]++;
        }

        // 将数据按照出现次数写回原数组
        int idx = 0;
        for (int val = 0; val < bucket.length; val++) {
            // count是val这个数出现的次数
            for (int count = bucket[val]; count > 0; count--) {
                array[idx++] = val;
            }
        }
    }

    public static void main(String[] args) {
        int[] array = {3, 1, 2, 8, 7, 5, 6, 4};
        sort(array);
        System.out.println("after sort: " + Arrays.toString(array));
    }
}
