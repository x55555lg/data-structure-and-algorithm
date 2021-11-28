package com.lg.leetcode.topinterview.practice1;

import java.util.Arrays;
import java.util.List;

/**
 * @author Xulg
 * @since 2021-07-08 21:13
 * Description: 阿里面试题2数之和
 */
class TwoSum2 {

    /*
     * 数相加为指定值
     * 给定一个数组和一个目标值，找出数组中所有加起来等于目标值的整数对(数组中同一个元素不能使用两次)
     * 例如：数组为：[2, 5, 5, 3, 3, 4, 4, 1, 6, 7]，目标值是8
     *       结果为：[2, 6], [5, 3], [5, 3], [4, 4], [1, 7]
     * 求出所有的两数之和的组合，不能重复使用
     */

    public static List<int[]> twoSum(int[] arr, int target) {
        return null;
    }

    public static void main(String[] args) {
        // 数组为：[2, 5, 5, 3, 3, 4, 4, 1, 6, 7]，目标值是8
        // 结果为：[2, 6], [5, 3], [5, 3], [4, 4], [1, 7]
        int[] arr = {2, 5, 5, 3, 3, 4, 4, 1, 6, 7};
        int target = 8;
        List<int[]> list = twoSum(arr, target);
        for (int[] group : list) {
            System.out.println(Arrays.toString(group));
        }
    }
}
