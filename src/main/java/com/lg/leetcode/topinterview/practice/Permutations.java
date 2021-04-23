package com.lg.leetcode.topinterview.practice;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author Xulg
 * Created in 2021-03-02 17:30
 */
class Permutations {

    /*
     * Given an array nums of distinct integers, return all the possible permutations. You can return the
     * answer in any order.
     *
     * Example 1:
     * Input: nums = [1,2,3]
     * Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
     *
     * Example 2:
     * Input: nums = [0,1]
     * Output: [[0,1],[1,0]]
     *
     * Example 3:
     * Input: nums = [1]
     * Output: [[1]]
     *
     * Constraints:
     * 1 <= nums.length <= 6
     * -10 <= nums[i] <= 10
     * All the integers of nums are unique.
     */

    public static List<List<Integer>> permute(int[] nums) {
        return BruteForceRecurse.permute(nums);
    }

    private static class BruteForceRecurse {

        public static List<List<Integer>> permute(int[] arr) {
            if (arr == null || arr.length == 0) {
                return new ArrayList<>(0);
            }
            List<List<Integer>> result = new ArrayList<>();
            recurse(arr, new HashSet<>(), new ArrayList<>(), result);
            return result;
        }

        private static void recurse(int[] arr, HashSet<Integer> alreadySelected,
                                    List<Integer> path, List<List<Integer>> result) {
            if (alreadySelected.size() == arr.length) {
                result.add(path);
            } else {
                for (int idx = 0; idx < arr.length; idx++) {
                    if (!alreadySelected.contains(idx)) {
                        alreadySelected.add(idx);
                        List<Integer> newPath = new ArrayList<>(path);
                        newPath.add(arr[idx]);
                        recurse(arr, alreadySelected, newPath, result);
                        // 回溯
                        alreadySelected.remove(idx);
                    }
                }
            }
        }

    }

    public static void main(String[] args) {
        List<List<Integer>> permute = BruteForceRecurse.permute(new int[]{1, 2, 3});
        System.out.println(JSON.toJSONString(permute));

        permute = BruteForceRecurse.permute(new int[]{1});
        System.out.println(JSON.toJSONString(permute));
    }

}
