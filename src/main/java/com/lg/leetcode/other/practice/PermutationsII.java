package com.lg.leetcode.other.practice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author Xulg
 * @since 2021-08-14 20:56
 * Description: leetcode_47
 */
class PermutationsII {

    /*
     * Given a collection of numbers, nums, that might contain duplicates,
     * return all possible unique permutations in any order.
     *
     * Example 1:
     * Input: nums = [1,1,2]
     * Output:
     * [[1,1,2],
     *  [1,2,1],
     *  [2,1,1]]
     *
     * Example 2:
     * Input: nums = [1,2,3]
     * Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
     *
     *
     * Constraints:
     * 1 <= nums.length <= 8
     * -10 <= nums[i] <= 10
     */

    public static List<List<Integer>> permuteUnique(int[] nums) {
        return null;
    }

    private static class BruteForce {
        public static List<List<Integer>> permuteUnique(int[] arr) {
            if (arr == null || arr.length == 0) {
                return new ArrayList<>(0);
            }
            Set<List<Integer>> result = new LinkedHashSet<>();
            recurse(arr, new LinkedList<>(), new HashSet<>(), result);
            return new ArrayList<>(result);
        }

        private static void recurse(int[] arr, List<Integer> path, Set<Integer> alreadySelected, Set<List<Integer>> result) {
            if (alreadySelected.size() == arr.length) {
                // base case
                // Set自动去重
                result.add(path);
            } else {
                for (int idx = 0; idx < arr.length; idx++) {
                    if (!alreadySelected.contains(idx)) {
                        // 标记这位置已使用
                        alreadySelected.add(idx);
                        // 新的path
                        LinkedList<Integer> newPath = new LinkedList<>(path);
                        newPath.add(arr[idx]);
                        // 下一个位置去
                        recurse(arr, newPath, alreadySelected, result);
                        // 回溯
                        alreadySelected.remove(idx);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        List<List<Integer>> permuteUnique = BruteForce.permuteUnique(new int[]{1, 1, 2});
        for (List<Integer> list : permuteUnique) {
            System.out.println(list);
        }
        System.out.println("=============================");
        permuteUnique = BruteForce.permuteUnique(new int[]{1, 2, 3});
        for (List<Integer> list : permuteUnique) {
            System.out.println(list);
        }
    }
}
