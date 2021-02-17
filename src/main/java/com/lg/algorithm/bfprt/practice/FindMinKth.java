package com.lg.algorithm.bfprt.practice;

import cn.hutool.core.util.RandomUtil;

import java.util.Arrays;

/**
 * @author Xulg
 * Created in 2021-02-14 14:49
 */
class FindMinKth {

    /*
     * 在一个数组int[] arr中，查找第k小的数
     *-------------------------------------
     * 暴力解
     * 对数组arr从小到大排序，取arr[k-1]的值就是第k小
     *-------------------------------------
     * 快排思想解
     *  int[] arr = {0, 2, 1, 9, 3, 5, 3};
     *  int k = 3;
     * 思路：
     *      第k小的index为k-1，index一定在[L, R]上
     *      index    2
     *      pivot    3
     *      arr     {0, 2, 1, 9, 3, 5, 3}
     *               0  1  2  3  4  5  6
     *               L                 R
     *      1.在数组arr[L, R]范围上不停的做荷兰旗分区操作，得到等于区位置equalsZone
     *          将大于pivot的放arr左边
     *          等于pivot的放arr中间
     *          大于pivot的放arr右边
     *      2.看index在等于区equalsZone的什么位置，
     *          index位置在等于区equalsZone内，即index >= equalsZone[0] && index <= equalsZone[1]
     *              arr[index]就是第k小的值
     *          index位置在等于区equalsZone左边，即index < equalsZone[0]
     *              在arr[L, equalsZone[0]-1]上继续分区操作，重复步骤2
     *          index位置在等于区equalsZone右边，即index > equalsZone[1]
     *              在arr[equalsZone[1]+1, R]上继续分区操作，重复步骤2
     *      3.如果 L == R，那么index一定和L，R相等，此时arr[index]就是第k小的值
     *
     *-------------------------------------
     * bfprt算法解
     *...
     */

    /**
     * 在一个数组int[] arr中，查找第k小的数
     * 时间复杂度O(N)
     * 笔试用这种就可以了
     *
     * @param arr the arr
     * @param k   the k
     * @return the min Kth value
     */
    public static int getMinKth(int[] arr, int k) {
        assert arr != null && arr.length > 0 && k > 0 && k <= arr.length;
        return processRecursion(arr, 0, arr.length - 1, k - 1);
    }

    private static int processRecursion(int[] arr, int left, int right, int index) {
        if (left == right) {
            // left == right == index
            return arr[index];
        }

        // [0, 1)
        // [0, right-left+1)
        // [left, right+1)
        int rIdx = left + (int) (Math.random() * (right - left + 1));

        // 分区
        int[] equalsZone = partition(arr, left, right, arr[rIdx]);
        int start = equalsZone[0];
        int end = equalsZone[1];

        if (index >= start && index <= end) {
            return arr[index];
        } else if (index < start) {
            return processRecursion(arr, left, start - 1, index);
        } else {
            // index > end
            return processRecursion(arr, end + 1, right, index);
        }
    }

    /**
     * 荷兰国旗分区
     */
    private static int[] partition(int[] arr, int left, int right, int pivot) {
        if (left > right) {
            return new int[]{-1, -1};
        }
        // equalsZone[lessThanZoneIdx+1. moreThanZoneIdx-1]
        int lessThanZoneIdx = left - 1;
        int moreThanZoneIdx = right + 1;
        for (int idx = left; idx < moreThanZoneIdx; ) {
            if (arr[idx] < pivot) {
                swap(arr, idx++, ++lessThanZoneIdx);
            } else if (arr[idx] > pivot) {
                swap(arr, idx, --moreThanZoneIdx);
            } else {
                // arr[idx] == pivot
                idx++;
            }
        }
        if (lessThanZoneIdx + 1 <= moreThanZoneIdx - 1) {
            return new int[]{lessThanZoneIdx + 1, moreThanZoneIdx - 1};
        } else {
            return new int[]{-1, -1};
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /* ******************************************************************************************************/

    /* 对数器 */

    public static int[] generateIntArray(int length) {
        int[] arr = new int[length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = RandomUtil.randomInt(-10, 11);
        }
        return arr;
    }

    public static int getMinKthForCompare(int[] arr, int k) {
        assert arr != null && arr.length > 0 : "what the hell?";
        assert k >= 1 && k <= arr.length : "what the hell?";
        Arrays.sort(arr);
        return arr[k - 1];
    }

    /* ******************************************************************************************************/

    public static void main(String[] args) {
        int times = 1000000;
        for (int time = 0; time < times; time++) {
            int length = RandomUtil.randomInt(1, 11);
            int[] arr = generateIntArray(length);
            int k = RandomUtil.randomInt(1, arr.length + 1);
            int r = getMinKthForCompare(arr.clone(), k);
            int r1 = getMinKth(arr.clone(), k);
            if (!(r == r1)) {
                System.out.println("Oops");
            }
        }
        System.out.println("finish!");
    }


}
