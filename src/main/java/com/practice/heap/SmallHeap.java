package com.practice.heap;

/**
 * @author Xulg
 * Created in 2021-04-29 17:20
 */
class SmallHeap {

    private static class MyMaxHeap {

        private final int limit;
        private final int[] table;
        private int size;

        MyMaxHeap(int capacity) {
            limit = capacity;
            size = 0;
            table = new int[capacity];
        }

        public void add(int val) {
            if (size >= limit) {
                throw new IllegalStateException();
            }
            table[size] = val;
            heapInsert(table, size);
            size++;
        }

        public int poll() {
            if (size <= 0) {
                throw new IllegalStateException();
            }
            int minVal = table[0];
            swap(table, 0, size - 1);
            table[size - 1] = 0;
            heapify(table, 0, size - 1);
            size--;
            return minVal;
        }

        public int size() {
            return size;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        private void heapify(int[] arr, int index, int heapSize) {
            // 向下堆化
            int leftSubIdx = (index << 1) + 1;
            while (leftSubIdx < heapSize) {
                int rightSubIdx = leftSubIdx + 1;
                // 取子节点的最大那个
                int minSubIdx = (rightSubIdx < heapSize) ? (arr[leftSubIdx] > arr[rightSubIdx] ? leftSubIdx : rightSubIdx) : leftSubIdx;
                // 取父节点，左节点，右节点的最大值
                int minIdx = (arr[index] < arr[minSubIdx]) ? minSubIdx : index;
                if (index == minIdx) {
                    // 调整结束了
                    break;
                }
                swap(arr, index, minIdx);
                // 继续向下
                index = minIdx;
                leftSubIdx = (minIdx << 1) + 1;
            }
        }

        private void heapInsert(int[] arr, int index) {
            while (index > 0) {
                int parentIdx = (index - 1) >> 1;
                if (arr[parentIdx] < arr[index]) {
                    swap(arr, parentIdx, index);
                }
                index = parentIdx;
            }
        }

        private void swap(int[] arr, int i, int j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }

        public boolean isFull() {
            return size == limit;
        }
    }

    @SuppressWarnings("all")
    private static class RightMaxHeap {
        private int[] arr;
        private final int limit;
        private int size;

        public RightMaxHeap(int capacity) {
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
            int maxIndex = 0;
            for (int i = 1; i < size; i++) {
                if (arr[i] > arr[maxIndex]) {
                    maxIndex = i;
                }
            }
            int ans = arr[maxIndex];
            arr[maxIndex] = arr[--size];
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
            MyMaxHeap my = new MyMaxHeap(curLimit);
            RightMaxHeap test = new RightMaxHeap(curLimit);
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
