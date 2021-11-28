package com.lg.algorithm.sort.a3;

import java.util.Arrays;

/**
 * @author Xulg
 * @since 2021-09-02 21:07
 * Description: 对数器
 */
public class ComparatorSort {

    public static void sort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        Arrays.sort(arr);
    }

}
