package com.lg.datastructure.binary;

import cn.hutool.core.lang.Assert;
import org.junit.jupiter.api.Test;

/**
 * 二分查找相关的变种题
 *
 * @author Xulg
 * Created in 2020-09-22 9:38
 */
public class BinarySearch {

    /**
     * 在一个没有重复的数组中查询指定的数
     * int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9};
     */
    @Test
    public void test1() {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Assert.isTrue(binarySearch1(array, 5) == 4);
        Assert.isTrue(binarySearch1(array, 5) == 4);
        Assert.isTrue(binarySearch1(array, 5) == binarySearch2(array, 5));
    }

    public static int binarySearch1(int[] array, int key) {
        if (array == null || array.length <= 0) {
            return -1;
        }
        int low = 0;
        int high = array.length - 1;
        while (low <= high) {
            // (high+low)/2 可能会数值溢出，这样写可以防止数太大溢出
            int mid = low + ((high - low) >> 1);
            if (array[mid] == key) {
                return mid;
            } else if (array[mid] > key) {
                // 在左边
                high = mid - 1;
            } else if (array[mid] < key) {
                // 在右边
                low = mid + 1;
            }
        }
        return -1;
    }

    public static int binarySearch2(int[] array, int key) {
        if (array == null || array.length <= 0) {
            return -1;
        }
        return doBinarySearch2(array, key, 0, array.length - 1);
    }

    private static int doBinarySearch2(int[] array, int key, int low, int high) {
        if (low > high) {
            // 找不到
            return -1;
        }
        int mid = low + ((high - low) >> 1);
        if (array[mid] == key) {
            return mid;
        } else if (array[mid] > key) {
            // 在左边
            high = mid - 1;
            return doBinarySearch2(array, key, low, high);
        } else if (array[mid] < key) {
            // 在右边
            low = mid + 1;
            return doBinarySearch2(array, key, low, high);
        }
        return -1;
    }

    /* ************************************************************************************************************** */
    /* ************************************************************************************************************** */
    /* ************************************************************************************************************** */

    /**
     * 查找第一个值等于给定值的元素，数组可能有重复
     * int[] array = {1, 1, 3, 4, 5, 5, 7, 8, 8};
     */
    @Test
    public void test2() {
        int[] array = {1, 1, 3, 4, 5, 5, 7, 8, 8};
        System.out.println(binarySearchFirstOneEquals(array, 1));
        System.out.println(binarySearchFirstOneEquals(array, 5));
        System.out.println(binarySearchFirstOneEquals(array, 3));
    }

    public static int binarySearchFirstOneEquals(int[] array, int key) {
        if (array == null || array.length <= 0) {
            return -1;
        }
        int low = 0;
        int high = array.length - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            // 在左边
            if (array[mid] > key) {
                high = mid - 1;
            }
            // 在右边
            else if (array[mid] < key) {
                low = mid + 1;
            }
            // 值相等，那么是不是第一个呢
            else {
                // 数组中第一个元素就是，或者中间位置的前一个元素不等于key
                if (mid == 0 || array[mid - 1] != key) {
                    return mid;
                } else {
                    // 中间值的前一个元素也是等于key啊，所以第一个等于key的值还在左边
                    high = mid - 1;
                }
            }
        }
        return -1;
    }

    /* ************************************************************************************************************** */
    /* ************************************************************************************************************** */
    /* ************************************************************************************************************** */

    /**
     * 查找最后一个值等于给定值的元素，数组可能有重复
     * int[] array = {1, 1, 3, 4, 5, 5, 7, 8, 8};
     */
    @Test
    public void test3() {
        int[] array = {1, 1, 3, 4, 5, 5, 7, 8, 8};
        Assert.isTrue(binarySearchLastOneEquals(array, 1) == 1);
        Assert.isTrue(binarySearchLastOneEquals(array, 5) == 5);
        Assert.isTrue(binarySearchLastOneEquals(array, 8) == 8);
        Assert.isTrue(binarySearchLastOneEquals(array, 3) == 2);
    }

    public static int binarySearchLastOneEquals(int[] array, int key) {
        if (array == null || array.length <= 0) {
            return -1;
        }
        int low = 0;
        int high = array.length - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            // 在左边
            if (array[mid] > key) {
                high = mid - 1;
            }
            // 在右边
            else if (array[mid] < key) {
                low = mid + 1;
            }
            // 值相等，那么是不是最后一个呢
            else {
                // 数组中最后一个元素就是，或者中间位置的后一个元素不等于key
                if (mid == array.length - 1 || array[mid + 1] != key) {
                    return mid;
                } else {
                    // 中间值的后一个元素也是等于key啊，所以最后一个等于key的值还在右边
                    low = mid + 1;
                }
            }
        }
        return -1;
    }

    /* ************************************************************************************************************** */
    /* ************************************************************************************************************** */
    /* ************************************************************************************************************** */

    /**
     * 查找第一个值大于等于给定值的元素，数组可能有重复
     * int[] array = {1, 1, 3, 4, 5, 5, 7, 8, 8};
     */
    @Test
    public void test4() {
        int[] array = {1, 1, 3, 4, 5, 5, 7, 8, 8};
        Assert.isTrue(binarySearchFirstOneGreatOrEqual(array, 0) == 0);
        Assert.isTrue(binarySearchFirstOneGreatOrEqual(array, 5) == 4);
        Assert.isTrue(binarySearchFirstOneGreatOrEqual(array, 8) == 7);
        Assert.isTrue(binarySearchFirstOneGreatOrEqual(array, 2) == 2);
    }

    public static int binarySearchFirstOneGreatOrEqual(int[] array, int key) {
        if (array == null || array.length <= 0) {
            return -1;
        }
        int low = 0;
        int high = array.length - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (array[mid] >= key) {
                // 是不是第一个呢
                if (mid == 0 || array[mid - 1] < key) {
                    // 数组的第一个元素了，那肯定就是了
                    // 中间值的前一个元素小于key，那中间值肯定就是第一个大于等于key的位置了
                    return mid;
                }
                // 不是第一个，左边还有
                else {
                    high = mid - 1;
                }
            } else {
                // 在右边
                low = mid + 1;
            }
        }
        return -1;
    }

    /* ************************************************************************************************************** */
    /* ************************************************************************************************************** */
    /* ************************************************************************************************************** */

    /**
     * 查找最后一个值小于等于给定值的元素，数组可能有重复
     * int[] array = {1, 1, 3, 4, 5, 5, 7, 8, 8};
     */
    @Test
    public void test5() {
        int[] array = {1, 1, 3, 4, 5, 5, 7, 8, 8};
        Assert.isTrue(binarySearchLastOneLessOrEqual(array, 1) == 1);
    }

    public static int binarySearchLastOneLessOrEqual(int[] array, int key) {
        if (array == null || array.length <= 0) {
            return -1;
        }
        int low = 0;
        int high = array.length - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (array[mid] <= key) {
                // 是不是最后一个呢
                if (mid == array.length - 1 || array[mid + 1] > key) {
                    // 数组的最后一个元素了，那肯定就是了
                    // 中间值的后一个元素大于key，那中间值肯定就是最后一个小于等于key的位置了
                    return mid;
                }
                // 不是最后一个，右边还有
                else {
                    low = mid + 1;
                }
            } else {
                // 在右边
                high = mid - 1;
            }
        }
        return -1;
    }

    /* ************************************************************************************************************** */
    /* ************************************************************************************************************** */
    /* ************************************************************************************************************** */

    /*
     * 局部最小值问题
     *      什么叫局部最小？
     *          1.array[0]的数据比array[1]要小
     *          2.array[n]的数据比array[n-1]要小（n为数组的最后一个）
     *          3.array[k]的数据比array[k-1]，array[k+1]都小（0<k<n）
     *    题目：数组array无序，相邻两个数据都不相等，求一个局部最小值
     */

    public static int findLocalMinimum(int[] array) {
        if (array == null || array.length == 0) {
            return -1;
        }
        // array只有一个元素，那个就是啊
        if (array.length == 1) {
            return 0;
        }
        // array[0]是不是局部最小
        if (array[0] < array[1]) {
            return 0;
        }
        // array[array.length-1]是不是局部最小
        if (array[array.length - 1] < array[array.length - 2]) {
            return array.length - 1;
        }
        // array[1, array.length -2]之间找了咯
        int low = 1;
        int high = array.length - 2;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (array[mid] < array[mid + 1] && array[mid] < array[mid - 1]) {
                // mid就是局部最小
                return mid;
            } else if (array[mid] > array[mid - 1]) {
                high = mid - 1;
            } else if (array[mid] > array[mid + 1]) {
                low = mid + 1;
            }
        }
        return -1;
    }
}
