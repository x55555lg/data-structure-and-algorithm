package com.lg.algorithm.sort.a2.practice;

import cn.hutool.core.util.RandomUtil;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 堆排
 * 时间复杂度：O(N*logN)
 * 空间复杂度：O(1)
 * 稳定性：不稳定
 *
 * @author Xulg
 * Created in 2021-01-18 10:51
 */
@SuppressWarnings("DuplicatedCode")
class HeapSort {

    public static void main(String[] args) {
        {
            int[] arr = {3, 1, 5, 2, 4};
            System.out.println(Arrays.toString(arr));
            sort(arr);
            System.out.println(Arrays.toString(arr));
        }
        {
            // 验证自己写的小根堆
            int times = 1000000;
            for (int time = 0; time < times; time++) {
                int size = 10;
                SmallHeap smallHeap = new SmallHeap(size);
                PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
                for (int i = 0; i < size; i++) {
                    int val = RandomUtil.randomInt(-100, 100);
                    smallHeap.add(val);
                    priorityQueue.add(val);
                }
                for (int i = 0; i < size; i++) {
                    @SuppressWarnings("ConstantConditions")
                    int r = priorityQueue.poll();
                    int r1 = smallHeap.pop();
                    if (r != r1) {
                        System.out.println("Oops");
                    }
                }
            }
            System.out.println("finish!");
        }
        {
            // 验证堆排序
            int times = 1000000;
            for (int time = 0; time < times; time++) {
                int length = RandomUtil.randomInt(0, 100);
                int[] arr = new int[length];
                for (int i = 0; i < arr.length; i++) {
                    arr[i] = RandomUtil.randomInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
                }
                int[] arr1 = arr.clone();
                int[] arr2 = arr.clone();
                Arrays.sort(arr1);
                sort(arr2);
                if (!Arrays.equals(arr1, arr2)) {
                    System.out.println("Oops");
                }
            }
            System.out.println("finish!");
        }
    }

    /**
     * 堆排序(从小到大)
     * 将数组从小到大排序，需要使用大根堆的算法
     */
    @SuppressWarnings("ConstantConditions")
    public static void sort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        // 将数组arr进行堆化
        if (true) {
            // 这里的时间复杂度是：O(N)
            for (int idx = arr.length - 1; idx >= 0; idx--) {
                heapify(arr, idx, arr.length);
            }
        } else {
            // 这里的时间复杂度是：O(N*LogN)
            for (int i = 0; i < arr.length; i++) {
                heapInsert(arr, i);
            }
        }
        // 上面堆化之后，最大值已经在array[0]位置了

        /*
         * int n = array.length - 1;
         * [0, n]上面进行堆化，保证最大值是array[0]，array[0]和array[n]交换
         * [0, n-1]上面进行堆化，保证最大值是array[0]，array[0]和array[n-1]交换
         * [0, n-2]上面进行堆化，保证最大值是array[0]，array[0]和array[n-2]交换
         * ......
         * [0, 1]上面进行堆化，保证最大值是array[0]，array[0]和array[1]交换
         */

        // 这里总的时间复杂度是: O(N*LogN)
        int heapSize = arr.length - 1;
        while (heapSize > 0) {
            swap(arr, 0, heapSize);
            heapify(arr, 0, heapSize--);
        }
    }

    /**
     * 大根堆
     * 从指定位置开始，自下而上的进行堆调整
     */
    private static void heapInsert(int[] array, int index) {
        while (index > 0) {
            // 根据子节点位置计算父节点位置
            int parentIdx = (index - 1) / 2;
            // 子节点的值大于父节点就进行交换
            if (array[index] > array[parentIdx]) {
                swap(array, index, parentIdx);
            }
            // 继续向上进行判断
            index = parentIdx;
        }
    }

    /**
     * 大根堆
     * 在array[index, heapSize-1]范围上，自上而下的进行堆调整
     */
    @SuppressWarnings("SameParameterValue")
    private static void heapify(int[] array, int index, int heapSize) {
        // 左子节点的位置
        int leftIdx = index * 2 + 1;
        // 左子节点不能越界
        while (leftIdx < heapSize) {
            // 右子节点位置(右子节点可能越界可能不越界)
            int rightIdx = leftIdx + 1;

            // 取左右子节点中最大的那个的位置
            int maxSubIdx;
            if (rightIdx >= heapSize) {
                // 右子节点越界了不存在
                maxSubIdx = leftIdx;
            } else {
                // 左右子节点均没越界，取小的那个
                maxSubIdx = array[leftIdx] < array[rightIdx] ? rightIdx : leftIdx;
            }

            // 父节点和左右子节点最大值进行比较
            int oldIndex = index;
            if (array[maxSubIdx] > array[index]) {
                // 子节点大于父节点，为了满足大根堆的要求，进行交换
                swap(array, index, maxSubIdx);
                // 需要判断的下一个父节点
                index = maxSubIdx;
            }

            // 父节点没发生变化，说明没发生交换
            if (oldIndex == index) {
                break;
            }

            leftIdx = index * 2 + 1;
        }
    }

    private static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    /**
     * 小根堆
     */
    private static class SmallHeap {
        // 堆顶位置
        private static final int HEAP_TOP_INDEX = 0;
        private final int[] array;
        private int count;

        SmallHeap(int capacity) {
            array = new int[capacity];
            count = 0;
        }

        /**
         * 把数据加入堆中
         */
        public void add(int val) {
            if (count >= array.length) {
                throw new IllegalStateException("堆已经满了");
            }

            // 数据存入堆中
            int insertIndex = count;
            array[insertIndex] = val;
            count++;

            // 进行堆的调整
            heapInsert(array, insertIndex);
        }

        // 从指定位置开始，自下而上的进行堆调整
        private static void heapInsert(int[] array, int index) {
            while (index > 0) {
                // 根据子节点位置计算父节点位置
                int parentIdx = (index - 1) / 2;
                // 父节点的值大于子节点就进行交换
                if (array[parentIdx] > array[index]) {
                    swap(array, index, parentIdx);
                }
                // 继续向上进行判断
                index = parentIdx;
            }
        }

        private static void swap(int[] array, int a, int b) {
            int temp = array[a];
            array[a] = array[b];
            array[b] = temp;
        }

        /**
         * 弹出堆顶数据
         */
        public int pop() {
            // 堆顶点的值
            int r = array[HEAP_TOP_INDEX];

            // 长度减1
            count--;
            // 堆顶和最后一个值进行交换
            swap(array, HEAP_TOP_INDEX, count);
            array[count] = 0;

            // 进行堆的调整
            heapify(array, HEAP_TOP_INDEX, count);

            return r;
        }

        // 在array[index, heapSize]范围上，自上而下的进行堆调整
        @SuppressWarnings("SameParameterValue")
        private static void heapify(int[] array, int index, int heapSize) {
            // 左子节点的位置
            int leftIdx = index * 2 + 1;
            // 左子节点不能越界
            while (leftIdx < heapSize) {
                // 右子节点位置(右子节点可能越界可能不越界)
                int rightIdx = leftIdx + 1;

                // 取左右子节点中最小的那个的位置
                int minSubIdx;
                if (rightIdx >= heapSize) {
                    // 右子节点越界了不存在
                    minSubIdx = leftIdx;
                } else {
                    // 左右子节点均没越界，取小的那个
                    minSubIdx = array[leftIdx] < array[rightIdx] ? leftIdx : rightIdx;
                }

                // 父节点和左右子节点最小值进行比较
                int oldIndex = index;
                if (array[index] > array[minSubIdx]) {
                    // 父节点大于子节点，为了满足小根堆的要求，进行交换
                    swap(array, index, minSubIdx);
                    // 需要判断的下一个父节点
                    index = minSubIdx;
                }

                // 父节点没发生变化，说明没发生交换
                if (oldIndex == index) {
                    break;
                }

                leftIdx = index * 2 + 1;
            }
        }

        private boolean isEmpty() {
            return count == 0;
        }
    }
}
