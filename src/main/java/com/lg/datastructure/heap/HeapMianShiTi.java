package com.lg.datastructure.heap;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 堆相关的面试题
 *
 * @author Xulg
 * Created in 2020-10-19 16:04
 */
@SuppressWarnings("ConstantConditions")
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

    public static void main(String[] args) {
        int[] array = {5, 3, 7, 2, 3, 4, 1};
        System.out.println("sort before: " + Arrays.toString(array));
        sortWithDistanceLimit(array, 6);
        System.out.println("sort after:  " + Arrays.toString(array));
    }

    /**
     * 时间复杂度O(N*log(k))
     */
    private static void sortWithDistanceLimit(int[] array, int k) {
        if (array == null || array.length < 2 || k == 0) {
            return;
        }
        // 将数组的前k个数([0, k-1])放入最小堆中
        PriorityQueue<Integer> heap = new PriorityQueue<>(k - 1);

        // addTo记录堆加到了数组的哪个位置
        int addTo = 0;
        for (; addTo < Math.min(array.length, k); addTo++) {
            heap.add(array[addTo]);
        }

        // 从上面记录的位置开始，往堆中加一个数，弹一个数
        int index = 0;
        for (; addTo < array.length; addTo++) {
            // 数组加到堆中
            heap.add(array[addTo]);
            // 从堆中弹出一个数写回原数组
            array[index++] = heap.poll();
        }

        // 堆中剩余的数写回原数组中
        while (!heap.isEmpty()) {
            array[index++] = heap.poll();
        }
    }

}
