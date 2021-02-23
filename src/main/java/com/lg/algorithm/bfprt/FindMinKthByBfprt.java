package com.lg.algorithm.bfprt;

import cn.hutool.core.util.RandomUtil;

import java.util.Arrays;

/**
 * bfprt算法
 * 五个朋友算法
 *
 * @author Xulg
 * Created in 2021-02-06 9:31
 */
class FindMinKthByBfprt implements FindMinKth2 {

    /*
     * 使用bfprt算法求数组array中第k小的数
     * 时间复杂度O(N)
     */

    /*
     * BFPRT算法
     *  定义如下算法：
     *      在数组arr[left, right]范围上，找第index小的数
     *          int bfprt(int[] arr, int left, int right, int index) {
     *              if (left == right) {
     *                  return arr[index];
     *              }
     *              // 选择数
     *              int pivot = getMedianOfMedians(arr, left, right);
     *              // 分区
     *              int[] partition = partition(arr, left, right, pivot);
     *              if (index >= partition[0] && index <= partition[1]) {
     *                  return arr[index];
     *              } else if (index < partition[0]) {
     *                  return bfprt(arr, left, partition[0] - 1, index);
     *              } else {
     *                  // index > partition[1]
     *                  return bfprt(arr, partition[1] + 1, right, index);
     *              }
     *          }
     *------------------------------------------------------------------------
     *  int[] array = { 7, 2, 1, 4, 5, 1, 6, 3, 9, 1, 8, 7 };
     *
     * 求取中位数的流程：
     *      定义如下方法求数组arr[left, right]范围上的中位数
     *          int getMedianOfMedians(int[] arr, int left, int right) {
     *              // 对arr数组按照5个一组拆分
     *              int size = right - left + 1;
     *              int offset = size % 5 == 0 ? 0 : 1;
     *              int[] medianArray = new int[size / 5 + offset];
     *
     *              // 对各个子数组排序并拿到其对应的中位数
     *              for (int group = 0; group < medianArray.length; group++) {
     *                  // 子数组排序
     *                  insertSort(子数组);
     *                  // 取排序后的子数组的中位数
     *                  medianArray[group] = 子数组的中位数;
     *              }
     *
     *              // 求medianArray数组的中位数
     *              return bfprt(medianArray, 0, medianArray.length-1, medianArray.length/2);
     *          }
     *  （1）将数组array[left, right]范围上，按照5个元素一组，不足5个的也算单独一组，
     *       均分成( (right - left + 1) ) / 5 + ( (right - left + 1) % 5 == 0 )? 0 : 1 )组
     *          sub1 { 7, 2, 1, 4, 5 }
     *          sub2 { 1, 6, 3, 9, 1 }
     *          sub3 { 8, 7 }
     *  （2）对分组后的每个子数组进行排序，使用插入排序算法即可
     *          sub1 { 1, 2, 4, 5, 7 }
     *          sub2 { 1, 1, 3, 6, 9 }
     *          sub3 { 7, 8 }
     *  （3）对排序后的各个子数组取中位数，如果是偶数个数的子数组就取上中位数
     *          sub1 { 1, 2, 4, 5, 7 }  中位数 4
     *          sub2 { 1, 1, 3, 6, 9 }  中位数 3
     *          sub3 { 7, 8 }           中位数 7
     *  （4）将从子数组中取得的各个中位数组成中位数数组
     *          middleArray { 4, 3, 7 }
     *  （5）调用方法bfprt(middleArray, 0, middleArray.length-1, middleArray.length/2);
     *       求取middleArray数组的中位数
     */

    /*
     * bfprt算法分析
     * TODO: 2021/2/20 ......看视频去
     */

    /* ****************************************************************************************************************/

    /**
     * 在一个数组int[] arr中，查找第k小的数
     * 时间复杂度O(N)
     * 使用bfprt算法
     *
     * @param arr the arr
     * @param k   the k
     * @return the min kth value
     */
    @Override
    public int getMinKth(int[] arr, int k) {
        assert arr != null && arr.length > 0 && k >= 1 && k <= arr.length;
        return bfprt(arr, 0, arr.length - 1, k - 1);
    }

    private static int bfprt(int[] arr, int left, int right, int index) {
        if (left == right) {
            assert right == index;
            return arr[index];
        }
        // 获取中位数
        int pivot = getMedianOfMedians(arr, left, right);
        // 分区
        int[] partition = partition(arr, left, right, pivot);
        if (index >= partition[0] && index <= partition[1]) {
            return arr[index];
        } else if (index < partition[0]) {
            return bfprt(arr, left, partition[0] - 1, index);
        } else {
            return bfprt(arr, partition[1] + 1, right, index);
        }
    }

    private static int getMedianOfMedians(int[] arr, int left, int right) {
        // 将数组arr按5个元素一组，求各个子数组的中位数
        int size = right - left + 1;
        int offset = size % 5 == 0 ? 0 : 1;
        int[] medianArray = new int[size / 5 + offset];

        if (false) {
            // 视频中的写法
            // group为中位数数组medianArray的索引
            for (int group = 0; group < medianArray.length; group++) {
                // start，end为均分后的子数组的范围索引
                int start = left + group * 5;
                // 子数组的右边界不能越界
                int end = Math.min(start + 4, right);
                // 子数组[start, end]范围上排序
                insertSort(arr, start, end);
                // 取得排序后的子数组的中位数
                medianArray[group] = arr[(start + end) >> 1];
            }
        } else {
            // 自己的写法
            // idx为中位数数组medianArray的索引
            // start，end为均分后的子数组的范围索引
            for (int idx = 0, start = left, end = start + 4; idx < medianArray.length; ) {
                // 子数组的右边界不能越界
                int l = start, r = Math.min(end, right);
                // 子数组[l, r]范围上排序
                insertSort(arr, l, r);
                // 取得排序后的子数组的中位数
                medianArray[idx] = arr[(l + r) >> 1];
                // 下一个子数组
                start = end + 1;
                end += 5;
                idx++;
            }
        }

        // 继续求中位数
        return bfprt(medianArray, 0, medianArray.length - 1, medianArray.length / 2);
    }

    /**
     * 在array[left, right]范围上进行插入排序
     */
    private static void insertSort(int[] array, int left, int right) {
        if (array == null || array.length == 0) {
            return;
        }
        for (int i = left; i <= right; i++) {
            // [left+1, i]范围上保证有序
            for (int j = i; j > left; j--) {
                if (array[j - 1] > array[j]) {
                    swap(array, j - 1, j);
                }
            }
        }
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * 荷兰旗分区
     */
    private static int[] partition(int[] arr, int left, int right, int pivot) {
        if (left > right) {
            return new int[]{-1, -1};
        }
        int less = left - 1;
        int more = right + 1;
        for (int idx = left; idx < more; ) {
            if (arr[idx] > pivot) {
                swap(arr, idx, --more);
            } else if (arr[idx] < pivot) {
                swap(arr, idx++, ++less);
            } else {
                // arr[idx] == pivot
                idx++;
            }
        }
        if (less + 1 > more - 1) {
            return new int[]{-1, -1};
        } else {
            return new int[]{less + 1, more - 1};
        }
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
        FindMinKth2 bfprt = new FindMinKthByBfprt();
        for (int time = 0; time < times; time++) {
            int length = RandomUtil.randomInt(1, 11);
            int[] arr = generateIntArray(length);
            int k = RandomUtil.randomInt(1, arr.length + 1);
            int r = getMinKthForCompare(arr.clone(), k);
            int r1 = bfprt.getMinKth(arr.clone(), k);
            if (!(r == r1)) {
                System.out.println("Oops");
            }
        }
        System.out.println("finish!");
    }

}
