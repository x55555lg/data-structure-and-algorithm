package com.lg.leetcode.topinterview.practice;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author Xulg
 * Created in 2021-03-01 9:12
 */
class FindMedianFromDataStream {

    /*
     * Median is the middle value in an ordered integer list. If the size of the list is even,
     * there is no middle value. So the median is the mean of the two middle value.
     * For example,
     *  [2,3,4], the median is 3
     *  [2,3], the median is (2 + 3) / 2 = 2.5
     *
     * Design a data structure that supports the following two operations:
     *  void addNum(int num) - Add a integer number from the data stream to the data structure.
     *  double findMedian() - Return the median of all elements so far.
     *
     * Example:
     *  addNum(1)
     *  addNum(2)
     *  findMedian() -> 1.5
     *  addNum(3)
     *  findMedian() -> 2
     *
     * Follow up:
     *  If all integer numbers from the stream are between 0 and 100, how would you optimize it?
     *  If 99% of all integer numbers from the stream are between 0 and 100, how would you optimize it?
     */

    public static void main(String[] args) {
        MedianFinder medianFinder = new MedianFinder();
        medianFinder.addNum(2);
        medianFinder.addNum(3);
        System.out.println(medianFinder.findMedian());
        medianFinder.addNum(4);
        System.out.println(medianFinder.findMedian());
    }

    /**
     * 超时
     */
    private static class MedianFinder1 {
        private final List<Integer> data;

        MedianFinder1() {
            data = new LinkedList<>();
        }

        public void addNum(int num) {
            data.add(num);
        }

        public double findMedian() {
            int size = data.size();
            int middle = size >> 1;
            if (size % 2 == 0) {
                int pre = BFPRT.findKth(data, middle);
                int post = BFPRT.findKth(data, middle + 1);
                return ((double) (pre + post)) / 2;
            } else {
                return BFPRT.findKth(data, middle + 1);
            }
        }

    }

    /**
     * 查找数组中第k小的数
     */
    private static class QuickSortIdea {

        public static int findKth(List<Integer> list, int k) {
            return processRecurse(list, 0, list.size() - 1, k - 1);
        }

        private static int processRecurse(List<Integer> list, int left, int right, int index) {
            if (left == right) {
                assert right == index;
                return list.get(index);
            }

            int swapIdx = left + (int) (Math.random() * (right - left + 1));
            int pivot = list.get(swapIdx);

            int[] partition = partition(list, left, right, pivot);
            int equalZoneStart = partition[0];
            int equalZoneEnd = partition[1];

            if (index >= equalZoneStart && index <= equalZoneEnd) {
                return list.get(index);
            } else if (index < equalZoneStart) {
                return processRecurse(list, left, equalZoneStart - 1, index);
            } else {
                // index > equalZoneEnd
                return processRecurse(list, equalZoneEnd + 1, right, index);
            }
        }

        private static int[] partition(List<Integer> list, int left, int right, int pivot) {
            if (left > right) {
                return new int[]{-1, -1};
            }
            if (left == right) {
                return new int[]{left, right};
            }
            int less = left - 1;
            int more = right + 1;
            for (int idx = left; idx < more; ) {
                if (list.get(idx) < pivot) {
                    swap(list, idx++, ++less);
                } else if (list.get(idx) > pivot) {
                    swap(list, idx, --more);
                } else {
                    idx++;
                }
            }
            if (less + 1 > more - 1) {
                return new int[]{-1, -1};
            } else {
                return new int[]{less + 1, more - 1};
            }
        }

        private static void swap(List<Integer> list, int i, int j) {
            int temp = list.get(i);
            list.set(i, list.get(j));
            list.set(j, temp);
        }
    }

    /**
     * 查找数组中第k小的数
     */
    private static class BFPRT {

        public static int findKth(List<Integer> list, int k) {
            return processRecurse(list, 0, list.size() - 1, k - 1);
        }

        private static int processRecurse(List<Integer> list, int left, int right, int index) {
            if (left == right) {
                assert right == index;
                return list.get(index);
            }

            // 获取中位数
            int median = getMedianOfMedians(list, left, right);

            int[] partition = partition(list, left, right, median);
            int equalZoneStart = partition[0];
            int equalZoneEnd = partition[1];

            if (index >= equalZoneStart && index <= equalZoneEnd) {
                return list.get(index);
            } else if (index < equalZoneStart) {
                return processRecurse(list, left, equalZoneStart - 1, index);
            } else {
                // index > equalZoneEnd
                return processRecurse(list, equalZoneEnd + 1, right, index);
            }
        }

        private static int getMedianOfMedians(List<Integer> list, int left, int right) {
            int size = right - left + 1;
            int offset = size % 5 == 0 ? 0 : 1;
            int total = size / 5 + offset;
            // 中位数数组
            List<Integer> medians = new ArrayList<>(5 + total + (total / 10));
            for (int group = 0; group < total; group++) {
                int start = left + group * 5;
                int end = Math.min(start + 4, right);
                insertSort(list, start, end);
                // 获取中位数
                Integer median = list.get((start + end) >> 1);
                medians.add(median);
            }
            return processRecurse(medians, 0, medians.size() - 1, medians.size() >> 1);
        }

        private static void insertSort(List<Integer> list, int left, int right) {
            for (int i = left; i < right; i++) {
                for (int j = i; j > 0; j--) {
                    if (list.get(j - 1) > list.get(j)) {
                        swap(list, j - 1, j);
                    }
                }
            }
        }

        private static int[] partition(List<Integer> list, int left, int right, int pivot) {
            if (left > right) {
                return new int[]{-1, -1};
            }
            if (left == right) {
                return new int[]{left, right};
            }
            int less = left - 1;
            int more = right + 1;
            for (int idx = left; idx < more; ) {
                if (list.get(idx) < pivot) {
                    swap(list, idx++, ++less);
                } else if (list.get(idx) > pivot) {
                    swap(list, idx, --more);
                } else {
                    idx++;
                }
            }
            if (less + 1 > more - 1) {
                return new int[]{-1, -1};
            } else {
                return new int[]{less + 1, more - 1};
            }
        }

        private static void swap(List<Integer> list, int i, int j) {
            int temp = list.get(i);
            list.set(i, list.get(j));
            list.set(j, temp);
        }
    }

    /**
     * 用大根堆，小根堆实现
     */
    private static class MedianFinder {
        private final PriorityQueue<Integer> minHeap;
        private final PriorityQueue<Integer> maxHeap;
        private int count;

        MedianFinder() {
            minHeap = new PriorityQueue<>();
            maxHeap = new PriorityQueue<>((x, y) -> y.compareTo(x));
            count = 0;
        }

        @SuppressWarnings("ConstantConditions")
        public void addNum(int num) {
            if (count % 2 == 0) {
                // 当数据总数为偶数时，新加入的元素，应当进入小根堆
                // 注意不是直接进入小根堆，而是经大根堆筛选后取大根堆中最大元素进入小根堆

                // 1.新加入的元素先入到大根堆，由大根堆筛选出堆中最大的元素
                maxHeap.add(num);
                // 2.筛选后的【大根堆中的最大元素】进入小根堆
                int maxVal = maxHeap.poll();
                minHeap.add(maxVal);
            } else {
                // 当数据总数为奇数时，新加入的元素，应当进入大根堆
                // 注意不是直接进入大根堆，而是经小根堆筛选后取小根堆中最大元素进入大根堆

                // 1.新加入的元素先入到小根堆，由小根堆筛选出堆中最小的元素
                minHeap.add(num);
                int minVal = minHeap.poll();
                // 2.筛选后的【小根堆中的最小元素】进入大根堆
                maxHeap.add(minVal);
            }
            count++;
        }

        public double findMedian() {
            if (count % 2 == 0) {
                assert !minHeap.isEmpty() && !maxHeap.isEmpty();
                return (double) (minHeap.peek() + maxHeap.peek()) / 2;
            } else {
                assert !minHeap.isEmpty();
                return minHeap.peek();
            }
        }
    }

}
