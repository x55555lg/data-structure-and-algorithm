package com.practice2.heap;

/**
 * @author Xulg
 * @since 2021-10-19 11:04
 * Description: 小根堆 大根堆
 */
@SuppressWarnings("SameParameterValue")
public class Heap {

    /**
     * left  = 2*P + 1
     * right = 2*P + 2
     * P = (sub - 1) / 2
     */
    private static class SmallHeap {
        private static final int TOP_IDX = 0;
        private final int limit;
        private int[] array;
        private int size;

        SmallHeap(int capacity) {
            this.limit = capacity;
            array = new int[capacity];
            size = 0;
        }

        public void add(int val) {
            if (size > limit) {
                throw new IllegalArgumentException("heap is full");
            }
            array[size++] = val;
            heapInsert(array, size - 1);
        }

        public int poll() {
            if (size <= 0) {
                throw new IllegalArgumentException("heap is empty");
            }
            int result = array[TOP_IDX];
            // 最后一个元素移动到0位置
            swap(array, TOP_IDX, --size);

            heapify(array, TOP_IDX, size);
            return result;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public boolean isFull() {
            return size == limit;
        }

        private void heapInsert(int[] array, int index) {
            while (index > 0) {
                int parentIdx = (index - 1) / 2;
                if (array[index] < array[parentIdx]) {
                    swap(array, parentIdx, index);
                }
                index = parentIdx;
            }
        }

        private void swap(int[] array, int i, int j) {
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }

        private void heapify(int[] array, int parentIdx, int size) {
            int leftSubIdx = 2 * parentIdx + 1;
            while (leftSubIdx < size) {
                int rightSubIdx = leftSubIdx + 1;
                // 最小子节点
                int minSubIdx = rightSubIdx >= size ? leftSubIdx :
                        (array[leftSubIdx] < array[rightSubIdx] ? leftSubIdx : rightSubIdx);
                // 父节点，子最小节点哪个更小
                int minIdx = array[parentIdx] < array[minSubIdx] ? parentIdx : minSubIdx;
                if (minIdx == parentIdx) {
                    break;
                }
                swap(array, parentIdx, minIdx);
                parentIdx = minIdx;
                leftSubIdx = 2 * parentIdx + 1;
            }
        }
    }

    /**
     * 正确的小根堆
     * 用作对数器
     */
    private static class RightMinHeap {
        private int[] arr;
        private final int limit;
        private int size;

        public RightMinHeap(int capacity) {
            arr = new int[capacity];
            this.limit = capacity;
            size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public boolean isFull() {
            return size == limit;
        }

        public void add(int value) {
            if (size == limit) {
                throw new RuntimeException("heap is full");
            }
            arr[size++] = value;
        }

        public int deleteMax() {
            int minIndex = 0;
            for (int i = 1; i < size; i++) {
                if (arr[i] < arr[minIndex]) {
                    minIndex = i;
                }
            }
            int ans = arr[minIndex];
            arr[minIndex] = arr[--size];
            return ans;
        }
    }

    public static void main(String[] args) {
        // 使用对数器验证自己写的MaxHeap是否正确
        int value = 1000;
        int limit = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            int curLimit = (int) (Math.random() * limit) + 1;
            SmallHeap my = new SmallHeap(curLimit);
            RightMinHeap test = new RightMinHeap(curLimit);
            int curOpTimes = (int) (Math.random() * limit);
            for (int j = 0; j < curOpTimes; j++) {
                if (my.isEmpty() != test.isEmpty()) {
                    System.out.println("Oops1!");
                }
                if (my.isFull() != test.isFull()) {
                    System.out.println("Oops2!");
                }
                if (my.isEmpty()) {
                    int curValue = (int) (Math.random() * value);
                    my.add(curValue);
                    test.add(curValue);
                } else if (my.isFull()) {
                    if (my.poll() != test.deleteMax()) {
                        System.out.println("Oops3!");
                    }
                } else {
                    if (Math.random() < 0.5) {
                        int curValue = (int) (Math.random() * value);
                        my.add(curValue);
                        test.add(curValue);
                    } else {
                        if (my.poll() != test.deleteMax()) {
                            System.out.println("Oops4!");
                        }
                    }
                }
            }
        }
        System.out.println("finish!");
    }

}
