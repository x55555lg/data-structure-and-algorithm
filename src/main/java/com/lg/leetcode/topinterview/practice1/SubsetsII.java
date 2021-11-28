package com.lg.leetcode.topinterview.practice1;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Xulg
 * Created in 2021-03-08 13:55
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
class SubsetsII {

    /*
     * Given an integer array nums that may contain duplicates, return all possible subsets (the power set).
     * The solution set must not contain duplicate subsets. Return the solution in any order.
     *
     * Example 1:
     * Input: nums = [1,2,2]
     * Output: [[],[1],[1,2],[1,2,2],[2],[2,2]]
     *
     * Example 2:
     * Input: nums = [0]
     * Output: [[],[0]]
     *
     * Constraints:
     *  1 <= nums.length <= 10
     *  -10 <= nums[i] <= 10
     */

    public static List<List<Integer>> subsetsWithDup(int[] nums) {
        return null;
    }

    private static class BruteForce {

        public static List<List<Integer>> subsetsWithDup(int[] arr) {
            if (arr == null || arr.length == 0) {
                return new ArrayList<>(0);
            }
            Set<List<Integer>> list = new HashSet<>();
            recurse(arr, 0, new ArrayList<>(), list);
            return new ArrayList<>(list);
        }

        private static void recurse(int[] arr, int curIdx, List<Integer> subset, Set<List<Integer>> list) {
            if (curIdx == arr.length) {
                // 排序，防止数据重复
                Collections.sort(subset);
                list.add(subset);
                return;
            }

            List<Integer> yesSubset = new ArrayList<>(subset);
            yesSubset.add(arr[curIdx]);
            recurse(arr, curIdx + 1, yesSubset, list);

            List<Integer> noSubset = new ArrayList<>(subset);
            recurse(arr, curIdx + 1, noSubset, list);
        }

    }

    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 3};
        List<List<Integer>> subsets = BruteForce.subsetsWithDup(arr);
        System.out.println(JSON.toJSONString(subsets, false));

        arr = new int[]{0};
        subsets = BruteForce.subsetsWithDup(arr);
        System.out.println(JSON.toJSONString(subsets, false));

        arr = new int[]{1, 2, 3, 4};
        subsets = BruteForce.subsetsWithDup(arr);
        System.out.println(JSON.toJSONString(subsets, false));

        arr = new int[]{1, 2, 2};
        subsets = BruteForce.subsetsWithDup(arr);
        System.out.println(JSON.toJSONString(subsets, false));

        arr = new int[]{2, 1, 2};
        subsets = BruteForce.subsetsWithDup(arr);
        System.out.println(JSON.toJSONString(subsets, false));

        arr = new int[]{4, 4, 4, 1, 4};
        subsets = BruteForce.subsetsWithDup(arr);
        System.out.println(JSON.toJSONString(subsets, false));
    }

}
