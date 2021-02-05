package com.lg.algorithm.sort.mergesort;

/**
 * 数组的降序对
 * 最优解法：使用归并排序
 *
 * @author Xulg
 * Created in 2020-10-12 14:58
 */
@SuppressWarnings("all")
class ArrayDescendPair {

    /*
     * 在一个数组中，只要前面的数比后面的大，就叫做降序对，例如
     *  数组：{3, 1, 7, 0, 2}中，有这些降序对：
     *      3   1
     *      3   0
     *      3   2
     *      1   0
     *      7   0
     *      7   2
     * 思路一：看一个数的右边有几个比它小：
     *      3的右边小的数有1，0，2
     *      1的右边小的数有0
     *      7的右边小的数有0，2
     * 总共6个
     * 思路二：看一个数的左边有几个数比它大
     *      3的左边没有
     *      1的左边有大的数有3
     *      7的左边没有
     *      0的左边有大的数有3，1，7
     *      2的左边有大的数有3，7
     * 总共6个
     */

    private static boolean debug = true;

    /* 思路一的做法，最终数组是降序排序的 */

    public static int calculateArrayDescendPairCount1(int[] array) {
        if (array == null || array.length < 2) {
            return 0;
        }
        return doCalculateDescendPairCount1(array, 0, array.length - 1);
    }

    private static int doCalculateDescendPairCount1(int[] array, int left, int right) {
        if (left == right) {
            return 0;
        }
        // 计算中间值
        int mid = left + ((right - left) >> 1);

        // 计算左数组有多少个降序对
        int leftCount = doCalculateDescendPairCount1(array, left, mid);

        // 计算右数组有多少个降序对
        int rightCount = doCalculateDescendPairCount1(array, mid + 1, right);

        // 当前数组有多少个降序对
        int count = mergeCalcCount1(array, left, mid, right);

        // 返回总数
        return count + leftCount + rightCount;
    }

    private static int mergeCalcCount1(int[] array, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];

        // 降序对数量
        int count = 0;
        // 临时数组的下标索引
        int idx = 0;
        // 左数组的开始索引
        int leftIdx = left;
        // 右数组的开始索引
        int rightIdx = mid + 1;
        while (leftIdx <= mid && rightIdx <= right) {
            // 左边数大于右边数，产生降序对
            // 哪个数大，哪个就往临时数组中存
            if (array[leftIdx] > array[rightIdx]) {
                if (debug) {
                    // 打印出降序对
                    for (int i = rightIdx; i <= right; i++) {
                        System.out.println("降序对：" + array[leftIdx] + "-" + array[i]);
                    }
                }
                // 在数组[rightIdx, right]上有这么多个数比array[leftIdx]小
                count = count + (right - rightIdx + 1);
                temp[idx] = array[leftIdx];
                leftIdx++;
            } else {
                temp[idx] = array[rightIdx++];
            }
            idx++;
        }

        // 上面的操作之后，左半部分或者右半部分可能
        // 还有部分数组没有处理到，接着处理
        while (leftIdx <= mid) {
            temp[idx++] = array[leftIdx++];
        }
        while (rightIdx <= right) {
            temp[idx++] = array[rightIdx++];
        }

        // 将临时数组的数据复制到原数组的[left, right]位置上去
        for (int i = 0; i < temp.length; i++) {
            array[left + i] = temp[i];
        }

        return count;
    }

    /* 思路二的做法，最终数组是升序排序的 */

    public static int calculateArrayDescendPairCount2(int[] array) {
        if (array == null || array.length < 2) {
            return 0;
        }
        return doCalculateDescendPairCount2(array, 0, array.length - 1);
    }

    private static int doCalculateDescendPairCount2(int[] array, int left, int right) {
        if (left == right) {
            return 0;
        }
        // 计算中间值
        int mid = left + ((right - left) >> 1);

        // 计算左数组有多少个降序对
        int leftCount = doCalculateDescendPairCount2(array, left, mid);

        // 计算右数组有多少个降序对
        int rightCount = doCalculateDescendPairCount2(array, mid + 1, right);

        // 当前数组有多少个降序对
        int count = mergeCalcCount2(array, left, mid, right);

        // 返回总数
        return count + leftCount + rightCount;
    }

    private static int mergeCalcCount2(int[] array, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];

        // 降序对数量
        int count = 0;
        // 临时数组的下标索引
        int idx = 0;
        // 左数组的开始索引
        int leftIdx = left;
        // 右数组的开始索引
        int rightIdx = mid + 1;
        while (leftIdx <= mid && rightIdx <= right) {
            // 右边的数小于左边的数，在左边数组[leftIdx, mid]有降序对
            if (array[rightIdx] < array[leftIdx]) {
                if (debug) {
                    // 打印出降序对
                    for (int i = leftIdx; i <= mid; i++) {
                        System.out.println("降序对：" + array[i] + "-" + array[rightIdx]);
                    }
                }
                // 在数组[leftIdx, mid]上有这么多个数比array[rightIdx]大
                count = count + (mid - leftIdx + 1);
                temp[idx] = array[rightIdx];
                rightIdx++;
            } else {
                temp[idx] = array[leftIdx];
                leftIdx++;
            }
            /*
            if (array[leftIdx] > array[rightIdx]) {
                if (debug) {
                    // 打印出降序对
                    for (int i = leftIdx; i <= mid; i++) {
                        System.out.println("降序对：" + array[i] + "-" + array[rightIdx]);
                    }
                }
                count = count + (mid - leftIdx + 1);
                temp[idx] = array[rightIdx];
                rightIdx++;
            } else {
                temp[idx] = array[leftIdx];
                leftIdx++;
            }
            */
            idx++;
        }

        // 上面的操作之后，左半部分或者右半部分可能
        // 还有部分数组没有处理到，接着处理
        while (leftIdx <= mid) {
            temp[idx++] = array[leftIdx++];
        }
        while (rightIdx <= right) {
            temp[idx++] = array[rightIdx++];
        }

        // 将临时数组的数据复制到原数组的[left, right]位置上去
        for (int i = 0; i < temp.length; i++) {
            array[left + i] = temp[i];
        }

        return count;
    }

    public static void main(String[] args) {
        int[] array = {3, 1, 7, 0, 2};
        int count = calculateArrayDescendPairCount1(array.clone());
        System.out.println(count);
        System.out.println("===================================");
        count = calculateArrayDescendPairCount2(array.clone());
        System.out.println(count);

        System.out.println("***********************************");

        array = new int[]{1, 1, 2, 2, 3, 1, 1, 1, 4, 4, 4};
        count = calculateArrayDescendPairCount1(array.clone());
        System.out.println(count);
        System.out.println("===================================");
        count = calculateArrayDescendPairCount2(array.clone());
        System.out.println(count);
    }
}
