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
    }

}
