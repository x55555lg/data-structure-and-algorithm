package com.practice2.sort;

import java.util.Arrays;

/**
 * @author Xulg
 * @since 2021-10-30 16:05
 */
public class CompareSort {

    public static void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        Arrays.sort(arr);
    }

}
