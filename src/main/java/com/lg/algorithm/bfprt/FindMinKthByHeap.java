package com.lg.algorithm.bfprt;

import cn.hutool.core.util.RandomUtil;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * @author Xulg
 * Created in 2021-02-20 14:39
 */
class FindMinKthByHeap implements FindMinKth2 {

    /*
     * 在一个数组int[] arr中，查找第k小的数
     *-------------------------------------
     * 使用堆
     */

    /**
     * 在一个数组int[] arr中，查找第k小的数
     * 时间复杂度O(N*logK)
     * 使用堆
     *
     * @param arr the arr
     * @param k   the k
     * @return the min kth value
     */
    @SuppressWarnings("ConstantConditions")
    @Override
    public int getMinKth(int[] arr, int k) {
        assert arr != null && arr.length > 0 && k >= 1 && k <= arr.length;

        // 创建大根堆
        PriorityQueue<Integer> bigHeap = new PriorityQueue<>((v1, v2) -> v2 - v1);

        // arr[0, k-1]范围的数加入大根堆
        for (int i = 0; i < k; i++) {
            bigHeap.add(arr[i]);
        }

        for (int i = k; i < arr.length; i++) {
            if (arr[i] < bigHeap.peek()) {
                bigHeap.poll();
                bigHeap.add(arr[i]);
            }
        }

        return bigHeap.peek();
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
        FindMinKth2 findMinKth = new FindMinKthByHeap();
        int times = 1000000;
        for (int time = 0; time < times; time++) {
            int length = RandomUtil.randomInt(1, 11);
            int[] arr = generateIntArray(length);
            int k = RandomUtil.randomInt(1, arr.length + 1);
            int r = getMinKthForCompare(arr.clone(), k);
            int r1 = findMinKth.getMinKth(arr.clone(), k);
            if (!(r == r1)) {
                System.out.println("Oops");
            }
        }
        System.out.println("finish!");
    }

}
