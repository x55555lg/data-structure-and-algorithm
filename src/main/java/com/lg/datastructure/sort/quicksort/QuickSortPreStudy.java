package com.lg.datastructure.sort.quicksort;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * 快速排序
 * 时间复杂度：n*log(n)
 *
 * @author Xulg
 * Created in 2020-09-27 8:36
 */
@SuppressWarnings({"WeakerAccess", "SameParameterValue", "DuplicatedCode"})
class QuickSortPreStudy {

    /*
     * 快排的前置问题：
     *      给定一个数组array，和一个整数num，请把小于等于num的数放在数组的左边，大于num的数放在数组的右边。
     *      要求额外的空间复杂度O(1)，时间复杂度O(n)
     * 思路：设置一个小于等于区，起始index=-1，遍历array数组，
     *       如果array[i] > num，直接下一个(i++)
     *       如果array[i] <= num，array[i]和小于等于区的下一个值交换，即array[i]和array[index+1]交换，然后index++
     */

    @Test
    public void test1() {
        int[] array = {5, 3, 7, 2, 3, 4, 1};
        int lessEqualsIndex = partition1(array, 3);
        System.out.println(Arrays.toString(array));
        System.out.println(0 + "-" + lessEqualsIndex);
    }

    /**
     * 将数组中小于等于value的值放数组的左边，大于的值放数组的右边
     * 要求：空间复杂度O(1)，时间复杂度O(n)
     *
     * @param array the array
     * @param value the compare value
     * @return the end of the less equals index
     */
    public static int partition1(int[] array, int value) {
        // 小于等于区的起始索引
        int lessThanIndex = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] <= value) {
                // 小于等于区的下一个位置和当前的数进行交换
                swap(array, lessThanIndex + 1, i);
                // 小于等于区的索引右移
                lessThanIndex++;
            }
        }
        return lessThanIndex;
    }

    private static void swap(int[] array, int a, int b) {
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    /* *****************荷兰旗问题*********************************************************************************** */

    /*
     * 快排的前置问题：荷兰旗问题(https://www.jianshu.com/p/356604b8903f)
     *      给定一个数组array，和一个整数num，请把小于num的数放在数组的左边，
     *      等于num的数放在数组中间，大于num的数放在数组的右边。
     *      要求额外的空间复杂度O(1)，时间复杂度O(n)
     * 思路：设置一个小于区lessThanIndex，一个大于区greatThanIndex，中间是一个等于区，遍历array数组，
     *      如果array[i] < num，把小于区的下一个数和array[i]互换，i++
     *      如果array[i] == num，直接i++
     *      如果array[i] > num，把大于区的前一个数和array[i]互换，i不变，因为
     *       从大于区换过来的数是不确定大小的，需要重新判断过
     */

    /**
     * 荷兰旗问题的解法
     * array[i] < value
     * 放数组左边
     * array[i] > value
     * 放数组右边
     * array[i] == value
     * 放数组中间
     * 返回等于区间的开始索引和结束索引
     *
     * @param array the array
     * @param value the compare value
     * @return the equals region start and end index
     */
    public static int[] netherlandsFlag(int[] array, int value) {
        return doCalcNetherlandsFlag(array, 0, array.length - 1, value);
    }

    /**
     * array[i] < value
     * 放数组左边
     * array[i] > value
     * 放数组右边
     * array[i] == value
     * 放数组中间
     * 返回等于区间的开始索引和结束索引
     *
     * @param array the array
     * @param left  the left index of array
     * @param right the right index of array
     * @return the equals region start and end index
     */
    private static int[] doCalcNetherlandsFlag(int[] array, int left, int right, int value) {
        // 小于区的索引
        int lessThanIndex = left - 1;
        // 大于区的索引
        int greatThanIndex = right + 1;
        // 遍历数组，遍历直到大于区位置就结束了
        for (int i = 0; i < greatThanIndex; ) {
            if (array[i] < value) {
                // 把小于区的下一个数和array[i]互换
                swap(array, lessThanIndex + 1, i);
                // 小于区索引增1
                lessThanIndex++;
                // 遍历下一个数去
                i++;
            } else if (array[i] > value) {
                // 把大于区的前一个数和array[i]互换，这里不需要i++，因为
                // 从大于区换过来的数是不确定大小的，需要重新判断过
                swap(array, greatThanIndex - 1, i);
                // 大于区索引减1
                greatThanIndex--;
            } else {
                // 相等则直接遍历下一个数去
                i++;
            }
        }
        return new int[]{lessThanIndex + 1, greatThanIndex - 1};
    }

    @Test
    public void test2() {
        int[] array = {5, 3, 7, 2, 3, 4, 1};
        int[] equalsArea = netherlandsFlag(array, 3);
        System.out.println(Arrays.toString(array));
        System.out.println("等于区的索引：" + Arrays.toString(equalsArea));
    }

    /* *****************快排递归方式实现***************************************************************************** */

    /**
     * array[i] < array[right]
     * 放数组左边
     * array[i] > array[right]
     * 放数组右边
     * array[i] == array[right]
     * 放数组中间
     * 返回等于区间的开始索引和结束索引
     *
     * @param array the array
     * @param left  the left index of array
     * @param right the right index of array
     * @return the equals region start and end index
     */
    private static int[] partition(int[] array, int left, int right) {
        if (left > right) {
            return new int[]{-1, -1};
        }
        // 只有一个元素
        if (left == right) {
            return new int[]{left, right};
        }
        // 小于区的边界index
        int lessThanIdx = left - 1;
        // 大于区的边界index，大于区从right开始，这样保证被比较的array{right}不会改变
        int greatThanIdx = right;
        // 遍历数组[left, greatThanIdx]
        for (int idx = left; idx < greatThanIdx; ) {
            if (array[idx] < array[right]) {
                // 把小于区的下一个数和array[idx]互换
                swap(array, lessThanIdx + 1, idx);
                // 小于区索引增1
                lessThanIdx++;
                idx++;
            } else if (array[idx] > array[right]) {
                // 把大于区的前一个数和array[idx]互换，这里不需要idx++，因为
                // 从大于区换过来的数是不确定大小的，需要重新判断过
                swap(array, greatThanIdx - 1, idx);
                // 大于区索引减1
                greatThanIdx--;
            } else {
                // 相等则直接遍历下一个数去
                idx++;
            }
        }
        /*
         * 此时的数组划分：
         *  [left, lessThanIdx] 小于区
         *  [lessThanIdx+1, greatThanIdx-1] 等于区
         *  [greatThanIdx, right-1] 大于区
         *  right位置的数还没处理，应该要放在等于区，所以array[right]要换到大于区的第一个位置
         *  即array[right]和array[greatThanIdx]互换
         */
        // array[right]换到等于区，也就是array[right]换到大于区的第一个位置
        swap(array, greatThanIdx, right);
        /*
         * 最终数组的划分：
         *  [left, lessThanIdx] 小于区
         *  [lessThanIdx+1, greatThanIdx] 等于区
         *  [greatThanIdx+1, right] 大于区
         */
        return new int[]{lessThanIdx + 1, greatThanIdx};
    }

    /**
     * 上面代码的另一种写法
     */
    private static int[] partition2222222222222222222222(int[] array, int left, int right) {
        if (left > right) {
            return new int[]{-1, -1};
        }
        if (left == right) {
            return new int[]{left, right};
        }
        // 小于区的索引
        int lessThanIdx = left - 1;
        // 大于区的索引
        int greatThanIdx = right + 1;
        // 被比较的数
        int value = array[right];
        for (int idx = left; idx < greatThanIdx; ) {
            if (array[idx] < value) {
                swap(array, lessThanIdx + 1, idx);
                lessThanIdx++;
                idx++;
            } else if (array[idx] > value) {
                swap(array, greatThanIdx - 1, idx);
                greatThanIdx--;
            } else {
                idx++;
            }
        }
        return new int[]{lessThanIdx + 1, greatThanIdx - 1};
    }

    @Test
    public void test3() {
        int[] array = {5, 3, 7, 2, 3, 4, 1};
        int[] equalsStartEndIndex = partition(array, 0, array.length - 1);
        System.out.println(Arrays.toString(array));
        System.out.println(Arrays.toString(equalsStartEndIndex));

        array = new int[]{5, 3, 7, 2, 3, 4, 1};
        equalsStartEndIndex = partition2222222222222222222222(array, 0, array.length - 1);
        System.out.println(Arrays.toString(array));
        System.out.println(Arrays.toString(equalsStartEndIndex));
    }

    /**
     * 递归方式的快速排序
     *
     * @param array the array
     */
    public static void quickSortByRecurse(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        recurseProcess(array, 0, array.length - 1);
    }

    private static void recurseProcess(int[] array, int left, int right) {
        if (left >= right) {
            return;
        }

        // 在[left, right]之间随机一个索引下标
        int randomIdx = (int) (Math.random() * (right - left + 1) + left);
        // 将随机索引处的数和array[right]互换
        swap(array, randomIdx, right);

        // [left, right]上划分区间，获取等于区的索引
        int[] equalsArea = partition(array, left, right);

        recurseProcess(array, left, equalsArea[0] - 1);
        recurseProcess(array, equalsArea[1] + 1, right);
    }

    /* *****************快排循环方式实现***************************************************************************** */
    // TODO 2020/10/13 ......

    public static void main(String[] args) {
        int[] array = {5, 3, 7, 2, 3, 4, 1};
        quickSortByRecurse(array);
        System.out.println(Arrays.toString(array));

        // [3, 9]
        while (true) {
            int randomIdx = (int) (Math.random() * (9 - 3 + 1) + 3);
            System.out.println(randomIdx);
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
