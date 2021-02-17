package com.lg.algorithm.bfprt;

import cn.hutool.core.util.RandomUtil;

import java.util.Arrays;

/**
 * @author Xulg
 * Created in 2021-02-06 9:43
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

    /* ******************************************************************************************************/

    /**
     * 在一个数组int[] arr中，查找第k小的数
     * 时间复杂度O(N)
     * 笔试用这种就可以了
     *
     * @param arr the arr
     * @param k   the k
     * @return the min kth value
     */
    public static int getMinKth(int[] arr, int k) {
        assert arr != null && arr.length > 0 : "what the hell?";
        assert k >= 1 && k <= arr.length : "what the hell?";
        return processRecurse(arr, 0, arr.length - 1, k - 1);
    }

    /**
     * 递归对数组分区
     * index一定在[left, right]范围上的
     */
    private static int processRecurse(int[] arr, int left, int right, int index) {
        if (left == right) {
            // 因为index一定在[left, right]上，所有此时left = right = index
            assert right == index;
            return arr[left];
        }

        // left = 1    right = 2
        // [0.0, 1.0) * 2 = [0.0, 2.0)
        // [0.0, 2.0) + 1 = [1.0, 3.0)
        // 随机取一个位置
        int randomIdx = left + (int) (Math.random() * (right - left + 1));
        int pivot = arr[randomIdx];

        // 求出等于区
        int[] equalsZone = partition(arr, left, right, pivot);

        if (index >= equalsZone[0] && index <= equalsZone[1]) {
            return arr[index];
        } else if (index < equalsZone[0]) {
            // 往左边
            return processRecurse(arr, left, equalsZone[0] - 1, index);
        } else {
            // index > equalsZone[1]
            // 往右边
            return processRecurse(arr, equalsZone[1] + 1, right, index);
        }
    }

    /**
     * 荷兰旗分区
     */
    private static int[] partition(int[] arr, int left, int right, int pivot) {
        int lessThanIdx = left - 1;
        int moreThanIdx = right + 1;
        // equalsZone: [lessThanIdx+1, moreThanIdx-1]
        for (int idx = left; idx < moreThanIdx; ) {
            if (arr[idx] < pivot) {
                swap(arr, idx++, ++lessThanIdx);
            } else if (arr[idx] > pivot) {
                swap(arr, idx, --moreThanIdx);
            } else {
                // arr[idx] == pivot
                idx++;
            }
        }
        if (lessThanIdx + 1 <= moreThanIdx - 1) {
            return new int[]{lessThanIdx + 1, moreThanIdx - 1};
        } else {
            // not exist equals zone
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

    @SuppressWarnings("AssertWithSideEffects")
    public static void main(String[] args) {
        {
            // 开启断言，加JVM参数 -ea
            boolean isOpen = false;

            // 如果开启了断言，会将isOpen的值改为true
            assert isOpen = true;

            // 打印是否开启了断言，如果为false，则没有启用断言
            System.out.println(isOpen);
        }

        {
            int[] arr = {1, 1, 1, 1};
            int[] partition = partition(arr, 0, 3, 0);
            System.out.println(Arrays.toString(arr) + "--->" + Arrays.toString(partition));

            arr = new int[]{0, 0, 0, 0};
            partition = partition(arr, 0, 3, 1);
            System.out.println(Arrays.toString(arr) + "--->" + Arrays.toString(partition));

            arr = new int[]{4, 1, 0, 6, 3, 2};
            partition = partition(arr, 0, 5, 3);
            System.out.println(Arrays.toString(arr) + "--->" + Arrays.toString(partition));
        }

        {
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

}
