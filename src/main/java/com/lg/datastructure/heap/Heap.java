package com.lg.datastructure.heap;

/**
 * 堆
 * 很重要的结构啊
 *
 * @author Xulg
 * Created in 2020-10-13 20:38
 */
@SuppressWarnings("FieldMayBeFinal")
class Heap {

    /*
     * 完全二叉树
     *  最后一层的叶子结点都连续靠左排列
     *  除了最后一层，其他层的节点个数达到最大
     *
     * 由数组实现的完全二叉树
     *
     * i = 0作为树的根节点
     * 数组
     * a    b   c   d   e   f   g
     * 0    1   2   3   4   5   6
     *
     * 二叉树
     *                    0
     *              1           2
     *           3     4     5     6
     * 任意节点的左子节点索引计算公式：
     *      leftIdx = 2 * i + 1
     * 任意节点的右子节点索引计算公式：
     *      rightIdx = 2 * i + 2
     * 任意节点的父节点索引计算公式：
     *      parentIdx = (i - 1) / 2
     *====================================================
     * i = 1作为树的根节点，i = 0处不使用，这种可以用到位运算提高计算速度
     * 数组
     *      a   b   c   d   e   f   g
     * 0    1   2   3   4   5   6   7
     *
     * 二叉树
     *                    1
     *              2           3
     *           4     5     6     7
     * 任意节点的左子节点索引计算公式：
     *      leftIdx = 2 * i      可以使用位运算=====>>> leftIdx = i << 1
     * 任意节点的右子节点索引计算公式：
     *      rightIdx = 2 * i + 1 可以使用位运算=====>>> rightIdx = i << 1 | 1
     * 任意节点的父节点索引计算公式：
     *      parentIdx = i / 2    可以使用位运算=====>>> parentIdx = i >> 1
     */

    /*
     * 堆：本质上是一个由数组实现的完全二叉树
     * 大根堆：堆顶一定是最大值
     *      每一颗子树的最大值都是该子树的头(父)节点的值
     *          数组：[6, 5, 3, 0, 1]
     *          二叉树：
     *                       6
     *                  5         3
     *               0      1
     *            6,5,3这颗子树的头节点值为6，比子节点5,3都大
     *            5,0,1这颗子树的头节点值为5，比子节点0,1都大
     *            0这颗子树的头节点值为0，没有子节点也是最大
     *            1这颗子树的头节点值为1，没有子节点也是最大
     *      左右子节点的值都比父节点要小
     * 小根堆：堆顶一定是最小值
     *      每一颗子树的最小值都是该子树的头(父)节点的值
     *其他情况都不是堆
     */

    /**
     * 大根堆实现
     */
    static class MaxHeap {

        /**
         * 堆顶索引位置
         */
        private static final int HEAP_TOP_IDX = 0;

        private int[] array;

        private final int maxLength;

        /**
         * 堆中已经存储的个数
         */
        private int count;

        public MaxHeap(int capacity) {
            array = new int[capacity];
            maxLength = capacity;
            count = 0;
        }

        /**
         * 往堆中增加元素
         * 时间复杂度：O(log(n))
         *
         * @param data the data
         */
        public void add(int data) {
            if (count >= maxLength) {
                throw new IllegalStateException("堆已经满了");
            }

            // 将数据存入数组中，
            array[count++] = data;

            /*
             * 插入元素9，index为5，进行大根堆化操作
             *             6(0)
             *     5(1)            3(2)
             * 0(3)    1(4)   9(5)
             */

            // 插入节点的索引
            int index = count - 1;

            // 从插入节点索引位置开始，向上进行堆化操作
            this.heapInsert(array, index);
        }

        /**
         * 堆化操作
         * 自下而上的堆化操作
         *
         * @param array the array
         * @param index the index to start heapify with
         */
        private void heapInsert(int[] array, int index) {
            while (index > 0) {
                int parentIdx = (index - 1) / 2;
                // 如果子节点大于它的父节点，就进行交换
                if (array[index] > array[parentIdx]) {
                    this.swap(array, index, parentIdx);
                }
                // 看父节点的状况
                index = parentIdx;
            }

            // arr[index]
            // arr[index] 不比 arr[index父]大了 ， 停
            // index = 0;
            /*视频中的写法
            while (array[index] > array[(index - 1) / 2]) {
                swap(array, index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
            */
        }

        /**
         * 删除大根堆中的最大值
         * 大根堆就是移除堆顶
         * 时间复杂度：O(log(n))
         */
        public int deleteMax() {
            // 堆顶就是最大值
            int maxValue = array[HEAP_TOP_IDX];

            // 最后一个元素放到堆顶，count减1
            swap(array, HEAP_TOP_IDX, --count);

            // [0, count-1]上进行堆化操作(heapify)
            this.heapify(array, HEAP_TOP_IDX, count);

            // 返回堆顶的值
            return maxValue;
        }

        public boolean isEmpty() {
            return count == 0;
        }

        public boolean isFull() {
            return maxLength == count;
        }

        private void swap(int[] array, int a, int b) {
            int temp = array[a];
            array[a] = array[b];
            array[b] = temp;
        }

        /**
         * 堆化操作
         * 自上而下的进行堆化操作
         *
         * @param array    the array
         * @param maxPos   堆化开始的index，一般是堆顶
         * @param heapSize 堆的大小
         */
        @SuppressWarnings("SameParameterValue")
        private void heapify(int[] array, int maxPos, int heapSize) {
            // maxPos是父节点
            while (true) {
                // 左子节点
                int leftIdx = 2 * maxPos + 1;
                // 右子节点
                int rightIdx = leftIdx + 1;

                // 左子节点索引越界了，右子节点肯定也越界了，直接break结束
                if (leftIdx >= heapSize) {
                    break;
                }

                // largestSubIdx就是左右子节点中值最大的那个索引
                int largestSubIdx;
                if (rightIdx < heapSize) {
                    // 右节点没有越界，这里说明左右节点都在
                    // 左右节点都有，取左右节点中值大的和父节点比较
                    largestSubIdx = array[rightIdx] > array[leftIdx] ? rightIdx : leftIdx;
                } else {
                    // 只有左节点，那么左子节点就是最大的子节点
                    largestSubIdx = leftIdx;
                }

                // 比较父节点值和子节点值的大小
                int oldMaxPos = maxPos;
                if (array[largestSubIdx] > array[maxPos]) {
                    // 子节点值大于父节点值，交换父节点和子节点的位置
                    swap(array, largestSubIdx, maxPos);
                    // 将交换的这个子节点作为下一个父节点进行处理
                    maxPos = largestSubIdx;
                }
                // 父节点没有变化，说明堆化完成了咯
                if (oldMaxPos == maxPos) {
                    break;
                }
            }
        }

        /**
         * 视频中的heapify方法实现，写的简洁但是不好理解
         */
        @SuppressWarnings("SameParameterValue")
        private void heapify2(int[] arr, int index, int heapSize) {
            // 算出左子节点
            int left = index * 2 + 1;
            while (left < heapSize) {
                // 右子节点
                int right = left + 1;
                /*
                 * 取左右两子节点中值大的那个的索引
                 * 如果右子节点没有越界，而且右子节点值大于左子节点，那么就取右子节点的索引
                 * 否则的话要么是右子节点越界了或者左子节点值大于右子节点，则取左子节点的索引
                 */
                int largest = right < heapSize && arr[right] > arr[left] ? right : left;
                // 父节点和子节点谁大，取谁的索引
                largest = arr[largest] > arr[index] ? largest : index;

                // 取得的索引就是父节点的，就不需要再操作了，已经是大根堆了
                if (largest == index) {
                    break;
                }

                // 交换父节点和子节点
                swap(arr, largest, index);

                // 操作下一个父节点
                index = largest;
                // 重新计算左子节点
                left = index * 2 + 1;
            }
        }
    }

    /**
     * 正确的大根堆
     * 用作对数器
     */
    static class RightMaxHeap {
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
            MaxHeap my = new MaxHeap(curLimit);
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
                    if (my.deleteMax() != test.deleteMax()) {
                        System.out.println("Oops3!");
                    }
                } else {
                    if (Math.random() < 0.5) {
                        int curValue = (int) (Math.random() * value);
                        my.add(curValue);
                        test.add(curValue);
                    } else {
                        if (my.deleteMax() != test.deleteMax()) {
                            System.out.println("Oops4!");
                        }
                    }
                }
            }
        }
        System.out.println("finish!");
    }
}
