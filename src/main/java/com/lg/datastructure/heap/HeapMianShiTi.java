package com.lg.datastructure.heap;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 堆相关的面试题
 *
 * @author Xulg
 * Created in 2020-10-19 16:04
 */
class HeapMianShiTi {

    /*
     *  已知有一个几乎有序的数组，几乎有序是指，如果把数组排好序的话，
     * 每个元素移动的距离一定不超过k，并且k相对于数组长度来说比较小。
     * (每个数原始位置，距离自己排完序的位置，不超过k)
     *      移动前：array[i]
     *      移动后：array[i']
     *      要求：i - i' < k
     * 请选择一种合适的排序策略，对数组进行排序
     *
     * 思路：
     *      前提：根据给定的数组选择一个合适的k值
     *      1）建立一个大小为k的小根堆，并取堆顶元素保存到原数组中。
     *      2）依次加入数组中的后一个元素并对堆结构进行调整，始终维持大小为k的小根堆。
     *      3）采用普通堆排序的方法将最后k个元素排序并保存到原数组中。
     */

    // TODO 2020/10/23 还没做呢。。。

    @Test
    public void test1() {
        int[] array = {5, 3, 7, 2, 3, 4, 1};
        System.out.println("sort before: " + Arrays.toString(array));
        sortWithDistanceLimit(array, array.length, 2);
        System.out.println("sort after:  " + Arrays.toString(array));
    }

    /**
     * 时间复杂度O(N*log(k))
     */
    private static void sortWithDistanceLimit(int[] array, int k) {

    }

    @SuppressWarnings("ConstantConditions")
    private static void sortWithDistanceLimit(int[] array, int n, int k) {
        // 将数组的前k个数放入最小堆中
        PriorityQueue<Integer> heap = new PriorityQueue<>(k + 1);
        for (int i = 0; i < Math.min(array.length, k + 1); i++) {
            heap.add(array[i]);
        }
        for (int i = 0; i < n; i++) {
            array[i] = heap.poll();
            // 将后续的[k+1, array.length]加入堆中
            if (i + k + 1 < array.length) {
                heap.add(array[i + k + 1]);
            }
        }
    }

    /**
     * 视频中的写法
     * 时间复杂度O(N*log(k))
     */
    private static void sortWithDistanceLimit2(int[] array, int k) {
        if (array == null || array.length < 2 || k == 0) {
            return;
        }

        // 默认小根堆
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        int index = 0;
        // [0, k-1]
        for (; index <= Math.min(array.length - 1, k - 1); index++) {
            heap.add(array[index]);
        }

        int i = 0;
        for (; index < array.length; i++, index++) {
            heap.add(array[index]);
            array[i] = heap.poll();
        }

        while (!heap.isEmpty()) {
            array[i++] = heap.poll();
        }
    }
}
