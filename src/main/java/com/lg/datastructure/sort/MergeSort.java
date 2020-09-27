package com.lg.datastructure.sort;

import java.util.Arrays;

/**
 * 归并排序
 * 时间复杂度：n*log(n)
 *
 * @author Xulg
 * Created in 2020-09-24 17:33
 */
class MergeSort {

    /**
     * 归并排序的思想：
     * 将大数组以不同的粒度划分，先让小数组有序
     */

    /*
     * 递归方式实现的归并排序
     * 时间复杂度：
     *  根据递归复杂度计算公式：T(N) = a*T(N/b) + O(N^d)
     *      a = 2;  b = 2;  d = 1;
     *      因为：(log(2)^2 = 1) == d
     *      所以复杂度：O( N^d * log(N) ) = O(N*logN)
     */

    @SuppressWarnings("WeakerAccess")
    static class RecursionMergeSort extends BaseMergeSort {

        public static void mergeSort(int[] array) {
            if (array == null || array.length < 2) {
                return;
            }
            recursionProcess(array, 0, array.length - 1);
        }

        private static void recursionProcess(int[] array, int left, int right) {
            if (left == right) {
                // 左右索引一样了，就只有一个元素，不需要排序了
                return;
            }
            // 计算中间值
            int mid = left + ((right - left) >> 1);
            // 排序左边的
            recursionProcess(array, left, mid);
            // 排序右边的
            recursionProcess(array, mid + 1, right);
            // 合并左右两边排序后的数组
            merge(array, left, mid, right);
        }
    }

    /**
     * 非递归方式实现的归并排序
     * 时间复杂度    O(N*logN)
     */
    @SuppressWarnings("WeakerAccess")
    static class SimpleMergeSort extends BaseMergeSort {

        public static void mergeSort(int[] array) {
            if (array == null || array.length < 2) {
                return;
            }

            /*
                array: 3, 1, 5, 2, 4, 7, 0, 9, 8
                k = 1;  一个数一组，分成左右两部分
                    3  1  5  2  4  7  0  9  8

                k = 2;  mergeSize = 1，2个数一组，分成左右两部分
                    3|1  5|2  4|7  0|9  8|
                    0 1  2 3  4 5  6 7  8
                k = 4;  mergeSize = 2，4个数一组，分成左右两部分
                    31|52  47|09  8|
                    01 23  45 67  8
                k = 8;  mergeSize = 4，8个数一组，分成左右两部分
                    3152|4709  8|
                    0123 4567  8
                k = 16 > mergeSize = 8，array.length 跳出
             */

            // mergeSize就是数组的一半，即k/2
            for (int mergeSize = 1; mergeSize < array.length; ) {
                for (int left = 0; left < array.length; ) {
                    // left ~ mid是数组的左边部分
                    int mid = left + mergeSize - 1;
                    if (mid >= array.length) {
                        break;
                    }
                    // mid+1 ~ right是数组的右边部分
                    int right = Math.min(mid + mergeSize, array.length - 1);
                    merge(array, left, mid, right);
                    // 下一个数组的left
                    left = right + 1;
                }

                // 如果mergeSize=21亿时，mergeSize*2就溢出了，
                // 结果可能还是小于array.length，退不出循环了
                if (mergeSize > (array.length / 2)) {
                    // mergeSize大于数组的一半了，提前break
                    break;
                }

                // mergeSize = mergeSize * 2;
                mergeSize = mergeSize << 1;
            }
        }

    }

    @SuppressWarnings("ManualArrayCopy")
    private static class BaseMergeSort {
        static void merge(int[] array, int left, int mid, int right) {
            /*
                 left  = 0;
                 mid   = 2;
                 right = 4;
                 L为leftIdx
                 R为rightIdx
                 M为mid
                 array：3, 1, 5, 2, 4, 7, 0, 9, 8
                 index: 0  1  2  3  4  5  6  7  8
                     left                       right
                        L     M  R
                        左数组         右数组
                 temp:  0  1  2  3  4  5  6  7  8
                      idx
             */

            // 创建一个数组用于存放合并的数据
            int[] temp = new int[right - left + 1];
            // 左边数组的下标
            int leftIdx = left;
            // 右边数组的下标
            int rightIdx = mid + 1;
            // 新建数组的下标
            int idx = 0;
            while (leftIdx <= mid && rightIdx <= right) {
                if (array[leftIdx] <= array[rightIdx]) {
                    // 左边数组的值小于等于右边的数组的值，留下左边数组的值
                    temp[idx] = array[leftIdx];
                    // 左边数组的下标递增
                    leftIdx++;
                } else {
                    // 左边数组的值大于右边的数组的值，留下右边数组的值
                    temp[idx] = array[rightIdx];
                    // 右边数组的下标递增
                    rightIdx++;
                }
                // 新数组的下标递增
                idx++;
            }

            // 左边或者右边数组还有部分数组没处理掉
            if (leftIdx <= mid) {
                while (leftIdx <= mid) {
                    temp[idx] = array[leftIdx];
                    leftIdx++;
                    idx++;
                }
            } else if (rightIdx <= right) {
                while (rightIdx <= right) {
                    temp[idx] = array[rightIdx];
                    rightIdx++;
                    idx++;
                }
            }

            // 将新数组的数组写入到原数组中
            for (int i = left; i <= right; i++) {
                array[i] = temp[i - left];
            }
            /*
            // 这么写也可以
            for (int i = 0; i < temp.length; i++) {
                array[left + i] = temp[i];
            }
            */
        }
    }

    /**
     * 非递归的归并排序
     * 时间复杂度    O(N*logN)
     * 归并排序一般还是用非递归方式的
     */
    @SuppressWarnings({"DuplicatedCode", "ManualArrayCopy"})
    static class LoopMergeSort {
        public static void mergeSort(int[] array) {
            if (array == null || array.length < 2) {
                // 排个鸡儿
                return;
            }
            int length = array.length;
            /*
             *说明：
             * mergeSize表示切分后数组的左边的大小，即数组的一半大小，例如：
             *  array = {3, 1, 5, 2, 4, 7, 0, 9, 8};
             *      (1) mergeSize=1时，大数组切分成多个长度为2的子数组，每个子数组分成左右两半，
             *      mergeSize就是子数组的一半的大小。
             *          3|1    5|2    4|7    0|9    8
             *      (2) mergeSize=2时，大数组切分成多个长度为4的子数组，每个子数组分成左右两半，
             *      mergeSize就是子数组的一半的大小。
             *          31|52    47|09    8
             *      (3) mergeSize=4时，大数组切分成多个长度为8的子数组，每个子数组分成左右两半，
             *      mergeSize就是子数组的一半的大小。
             *          3152|4709    8
             *
             * left为切分后的子数组的左边界
             *      left初始值为0，left = right + 1;
             * right为切分后的子数组的右边界
             *      right = 2 * mid - left + 1;
             * mid为切分后的子数组的中间位置
             *      mid = left + mergeSize - 1;
             *  例如：
             *      mergeSize=2时，数组切分后为5个子数组如下：
             *          3|1    5|2    4|7    0|9    8
             *      子数组1：left = 0, mid = 0, right = 1
             *      子数组2：left = 2, mid = 2, right = 3
             *      子数组3：left = 4, mid = 4, right = 5
             *      子数组4：left = 6, mid = 6, right = 7
             *      子数组5：不完整
             *
             *      mergeSize=2时，数组切分后为3个子数组如下：
             *          31|52    47|09    8
             *      子数组1：left = 0, mid = 1, right = 3
             *      子数组2：left = 4, mid = 5, right = 7
             *      子数组3: 不完整
             */
            for (int mergeSize = 1; mergeSize < length; ) {
                //遍历各个子数组
                for (int left = 0; left < length; ) {
                    // 数组的中间位置
                    int mid = left + mergeSize - 1;
                    // mid也不能越界啊
                    if (mid >= length) {
                        break;
                    }
                    // 算出数组的右边界，不能越界啊
                    // 等价写法：int right = Math.min(mid + mergeSize, length - 1);
                    int right = Math.min(2 * mid - left + 1, length - 1);
                    // merge合并操作
                    merge(array, left, mid, right);
                    // 下一个数组的左边界
                    left = right + 1;
                }

                /*
                 * 1.这里的话，如果mergeSize大于了数组一半的长度，那么下一次循环，
                 * 肯定会跳出了，所以这里加这个判断可以提前退出循环
                 * 2.其次，如果当数组特别大的时候，mergeSize=21亿，当mergeSize再翻倍就会溢出，
                 * 溢出后的mergeSize就小于了length，退不出循环了，导致错误
                 */
                if (mergeSize > length / 2) {
                    break;
                }

                // 子数组长度翻倍
                // 等价写法：mergeSize = mergeSize * 2;
                mergeSize = mergeSize << 1;
            }
        }

        private static void merge(int[] array, int left, int mid, int right) {
            /*
             * 原数组：3        1        5        2         4        7        0        9        8
             * 子数组：1        3        2        5    |    4        7        0        9
             * index:  0        1        2        3         4        5        6        7
             *         left                       mid                                  right
             *         leftIdx                              rightIdx
             *      left = 0, right = 7, mid = 3
             *      leftIdx = 0, rightIdx = mid + 1
             *  左半数组范围：[leftIdx, mid]
             *  左半数组范围：[rightIdx, right]
             */
            // 申请一个临时数组，数组的大小就是子数组的大小
            int[] temp = new int[right - left + 1];

            // 临时数组的下标索引
            int idx = 0;
            // 子数组的左半部分的开始下标索引
            int leftIdx = left;
            // 子数组的右半部分的开始下标索引
            int rightIdx = mid + 1;

            // 同时遍历数组的左半部分和右半部分
            while (leftIdx <= mid && rightIdx <= right) {
                // 如果左半部分数组的值小于等于右半部分数组的值，则在临时数组中插入左半部分数组的值
                // 否则的话在临时数组中插入右半部分数组的值
                if (array[leftIdx] <= array[rightIdx]) {
                    temp[idx] = array[leftIdx];
                    // 左半部分数组的下标索引后移
                    leftIdx++;
                } else {
                    temp[idx] = array[rightIdx];
                    // 右半部分数组的下标索引后移
                    rightIdx++;
                }
                // 临时数组的下标索引后移
                idx++;
            }

            // 上面的操作之后，左半部分或者右半部分可能
            // 还有部分数组没有处理到，接着处理
            while (leftIdx <= mid) {
                temp[idx] = array[leftIdx];
                leftIdx++;
                idx++;
            }
            while (rightIdx <= right) {
                temp[idx] = array[rightIdx];
                rightIdx++;
                idx++;
            }

            // 上面操作之后，临时数组temp中的数据是一定有序的

            // 将临时数组的数据复制到原数组的[left, right]位置上去
            for (int i = 0; i < temp.length; i++) {
                array[left + i] = temp[i];
            }
        }
    }

    /* ************************************************************************************************************** */

    public static void main(String[] args) {
        int[] array1 = {3, 1, 5, 2, 4, 7, 0, 9, 8};
        System.out.println("merge sort before: " + Arrays.toString(array1));
        // 递归方式的归并排序
        RecursionMergeSort.mergeSort(array1);
        System.out.println("merge sort after:  " + Arrays.toString(array1));

        int[] array2 = {3, 1, 5, 2, 4, 7, 0, 9, 8};
        System.out.println("merge sort before: " + Arrays.toString(array2));
        // 非递归方式归并排序
        SimpleMergeSort.mergeSort(array2);
        System.out.println("merge sort after:  " + Arrays.toString(array2));

        int[] array3 = {3, 1, 5, 2, 4, 7, 0, 9, 8};
        System.out.println("merge sort before: " + Arrays.toString(array3));
        // 非递归方式归并排序
        LoopMergeSort.mergeSort(array3);
        System.out.println("merge sort after:  " + Arrays.toString(array3));
    }
}
