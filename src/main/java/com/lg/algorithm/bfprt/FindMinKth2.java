package com.lg.algorithm.bfprt;

import java.util.Arrays;

/**
 * 在一个数组int[] arr中，查找第k小的数
 *
 * @author Xulg
 * Created in 2021-02-20 14:29
 */
interface FindMinKth2 {

    /**
     * 在一个数组int[] arr中，查找第k小的数
     * 默认使用排序算法实现
     * 时间复杂度O(N*logN)
     *
     * @param arr the arr
     * @param k   the k
     * @return the min Kth value
     */
    default int getMinKth(int[] arr, int k) {
        assert arr != null && arr.length > 0 && k >= 1 && k <= arr.length;
        Arrays.sort(arr);
        return arr[k - 1];
    }

}
