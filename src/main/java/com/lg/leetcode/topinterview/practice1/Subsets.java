package com.lg.leetcode.topinterview.practice1;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xulg
 * Created in 2021-03-08 10:51
 */
class Subsets {

    /*
     * Given an integer array nums of unique elements, return all possible subsets (the power set).
     * The solution set must not contain duplicate subsets. Return the solution in any order.
     *
     * Example 1:
     * Input: nums = [1,2,3]
     * Output: [[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
     *
     * Example 2:
     * Input: nums = [0]
     * Output: [[],[0]]
     *
     * Constraints:
     *  1 <= nums.length <= 10
     *  -10 <= nums[i] <= 10
     *  All the numbers of nums are unique.
     */

    public static List<List<Integer>> subsets(int[] nums) {
        return BruteForce.subsets(nums);
    }

    /**
     * 暴力枚举
     */
    private static class BruteForce {

        public static List<List<Integer>> subsets(int[] arr) {
            if (arr == null || arr.length == 0) {
                return new ArrayList<>(0);
            }
            ArrayList<List<Integer>> list = new ArrayList<>();
            //list.add(new ArrayList<>(0));
            //recurse(arr, 0, list);
            recurse(arr, 0, new ArrayList<>(), list);
            return list;
        }

        private static void recurse(int[] arr, int curIdx, List<Integer> path, List<List<Integer>> list) {
            if (curIdx == arr.length) {
                list.add(path);
                return;
            }

            // 子集合加上当前位置的数
            List<Integer> yesPath = new ArrayList<>(path);
            yesPath.add(arr[curIdx]);
            recurse(arr, curIdx + 1, yesPath, list);

            // 子集合不加当前位置的数
            List<Integer> noPath = new ArrayList<>(path);
            recurse(arr, curIdx + 1, noPath, list);
        }

    }

    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 3};
        List<List<Integer>> subsets = BruteForce.subsets(arr);
        System.out.println(JSON.toJSONString(subsets, false));

        arr = new int[]{0};
        subsets = BruteForce.subsets(arr);
        System.out.println(JSON.toJSONString(subsets, false));

        arr = new int[]{1, 2, 3, 4};
        subsets = BruteForce.subsets(arr);
        System.out.println(JSON.toJSONString(subsets, false));

        arr = new int[]{1, 2, 2};
        subsets = BruteForce.subsets(arr);
        System.out.println(JSON.toJSONString(subsets, false));
    }
}
