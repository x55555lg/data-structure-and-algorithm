package com.lg.leetcode.topinterview.practice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
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
        if (arr == null || arr.length == 0) {
            return new LinkedList<>();
        }

        List<int[]> list = new LinkedList<>();

        // 统计每个数的个数
        HashMap<Integer, Integer> countMap = new HashMap<>(arr.length * 4 / 3 + 1);
        for (int val : arr) {
            if (countMap.containsKey(val)) {
                countMap.put(val, countMap.get(val) + 1);
            } else {
                countMap.put(val, 1);
            }
        }

        for (int curVal : arr) {
            // 剩余数字
            int restVal = target - curVal;
            if (countMap.containsKey(curVal) && countMap.containsKey(restVal)
                    && countMap.get(curVal) > 0 && countMap.get(restVal) > 0) {
                list.add(new int[]{curVal, restVal});
                // 必须这么写，如果curVal == restVal时会错误
                countMap.put(curVal, countMap.get(curVal) - 1);
                countMap.put(restVal, countMap.get(restVal) - 1);
            }
        }

        return list;
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

    // public static List<int[]> twoSum(int[] arr, int target) {
    //     if (arr == null || arr.length == 0) {
    //         return null;
    //     }
    //
    //     List<int[]> list = new LinkedList<>();
    //
    //     /*
    //     // 记录哪些位置已经使用过了
    //     HashSet<Integer> alreadyUsedIndex = new HashSet<>();
    //
    //     // key为数，value为数在数组中的index位置
    //     HashMap<Integer, Integer> valueIndexMap = new HashMap<>(arr.length * 4 / 3 + 1);
    //
    //     for (int idx = 0; idx < arr.length; idx++) {
    //         int rest = target - arr[idx];
    //         if (valueIndexMap.containsKey(rest)) {
    //             // 数组中有这个数rest可以和arr[idx]组成target，获取这个数字rest的index位置
    //             int restIdx = valueIndexMap.get(rest);
    //             // 都是未使用过的，则是有效的结果
    //             if (!alreadyUsedIndex.contains(idx) && !alreadyUsedIndex.contains(restIdx)) {
    //                 list.add(new int[]{idx, restIdx});
    //                 alreadyUsedIndex.add(idx);
    //                 alreadyUsedIndex.add(restIdx);
    //             }
    //         } else {
    //             valueIndexMap.put(arr[idx], idx);
    //         }
    //     }
    //     */
    //
    //
    //     // 记录哪些位置已经使用过了
    //     HashSet<Integer> alreadyUsedIndex = new HashSet<>();
    //
    //     // key为数，value为数在数组中的index位置
    //     HashMap<Integer, LinkedList<Integer>> valueIndexMap = new HashMap<>(arr.length * 4 / 3 + 1);
    //
    //     for (int idx = 0; idx < arr.length; idx++) {
    //         int rest = target - arr[idx];
    //         if (valueIndexMap.containsKey(rest)) {
    //             LinkedList<Integer> indexQueue = valueIndexMap.get(rest);
    //             if (!indexQueue.isEmpty()) {
    //
    //             }
    //         } else {
    //             // 记录arr[idx]的位置信息
    //             if (valueIndexMap.containsKey(arr[idx])) {
    //                 valueIndexMap.get(arr[idx]).addLast(idx);
    //             } else {
    //                 LinkedList<Integer> indexQueue = new LinkedList<>();
    //                 indexQueue.add(idx);
    //                 valueIndexMap.put(arr[idx], indexQueue);
    //             }
    //         }
    //     }
    //
    //     /*for (int idx = 0; idx < arr.length; idx++) {
    //         int rest = target - arr[idx];
    //         if (valueIndexMap.containsKey(rest)) {
    //             // 数组中有这个数rest可以和arr[idx]组成target，获取这个数字rest的index位置
    //             int restIdx = valueIndexMap.get(rest);
    //             // 都是未使用过的，则是有效的结果
    //             if (!alreadyUsedIndex.contains(idx) && !alreadyUsedIndex.contains(restIdx)) {
    //                 list.add(new int[]{idx, restIdx});
    //                 alreadyUsedIndex.add(idx);
    //                 alreadyUsedIndex.add(restIdx);
    //             }
    //         } else {
    //             valueIndexMap.put(arr[idx], idx);
    //         }
    //     }*/
    //     return list;
    // }

}
