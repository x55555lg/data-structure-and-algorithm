package com.lg.leetcode.topinterview.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author Xulg
 * Created in 2021-03-01 23:27
 */
class ThreeSum {

    /*
     * Given an array nums of n integers, are there elements a, b, c in nums such that a + b + c = 0?
     * Find all unique triplets in the array which gives the sum of zero.
     * Notice that the solution set must not contain duplicate triplets.
     *
     * Example 1:
     * Input: nums = [-1,0,1,2,-1,-4]
     * Output: [[-1,-1,2],[-1,0,1]]
     *
     * Example 2:
     * Input: nums = []
     * Output: []
     *
     * Example 3:
     * Input: nums = [0]
     * Output: []
     *
     * Constraints:
     *  0 <= nums.length <= 3000
     *  -105 <= nums[i] <= 105
     */

    public static List<List<Integer>> threeSum(int[] nums) {
        return BruteForce1.threeSum(nums);
    }

    /**
     * 超级暴力解法O(N^3)
     */
    private static class BruteForce1 {

        public static List<List<Integer>> threeSum(int[] arr) {
            if (arr == null || arr.length < 3) {
                return new ArrayList<>(0);
            }
            Set<List<Integer>> set = new HashSet<>();
            for (int first = 0; first < arr.length; first++) {
                for (int second = 0; second < arr.length; second++) {
                    if (second != first) {
                        for (int third = 0; third < arr.length; third++) {
                            if (third != first && third != second) {
                                if (arr[first] + arr[second] + arr[third] == 0) {
                                    ArrayList<Integer> triplets = new ArrayList<>(Arrays.asList(arr[first], arr[second], arr[third]));
                                    Collections.sort(triplets);
                                    set.add(triplets);
                                }
                            }
                        }
                    }
                }
            }
            return new ArrayList<>(set);
        }

        private static void recurse(int[] arr, int first, int second, int third, Set<List<Integer>> set) {
            if (first >= arr.length || second >= arr.length || third >= arr.length) {
                return;
            }

            System.out.println("first=" + first + ", second=" + second + ", third=" + third);
            if (arr[first] + arr[second] + arr[third] == 0) {
                ArrayList<Integer> triplets = new ArrayList<>(Arrays.asList(arr[first], arr[second], arr[third]));
                Collections.sort(triplets);
                set.add(triplets);
            }

            for (int f = first + 1; f < arr.length; f++) {
                for (int s = second + 1; s < arr.length; s++) {
                    for (int t = third + 1; t < arr.length; t++) {
                        recurse(arr, f, s, t, set);
                    }
                }
            }
//
//            /*
//             * Input: nums = [-1,0,1,2,-1,-4]
//             * Output: [[-1,-1,2],[-1,0,1]]
//             */
//            int s = second + 1, t = third + 1;
//            while (t < arr.length) {
//                if (arr[first] + arr[s] + arr[t] == 0) {
//                    ArrayList<Integer> triplets = new ArrayList<>(Arrays.asList(arr[first], arr[s], arr[t]));
//                    Collections.sort(triplets);
//                    set.add(triplets);
//                }
//                s++;
//                t++;
//            }
//
//            // 下一组
//            recurse(arr, first + 1, second + 1, third + 1, set);
        }

    }

    /**
     * 暴力解法O(N^2)
     */
    private static class BruteForce2 {

        public static List<List<Integer>> threeSum(int[] arr) {
            if (arr == null || arr.length < 3) {
                return new ArrayList<>(0);
            }
            int zero = 0;

            // 对每个数建立indexMap
            HashMap<Integer, Integer> indexMap = new HashMap<>(16);
            for (int i = 0; i < arr.length; i++) {
                indexMap.put(arr[i], i);
            }

            Set<List<Integer>> set = new HashSet<>();
            for (int firstIdx = 0; firstIdx < arr.length; firstIdx++) {
                for (int secondIdx = 0; secondIdx < arr.length; secondIdx++) {
                    if (secondIdx != firstIdx) {
                        // 看数组中有没有第三个数，找到第三个数的索引
                        Integer thirdIdx = indexMap.get(zero - arr[firstIdx] - arr[secondIdx]);
                        if (thirdIdx != null && thirdIdx != firstIdx && thirdIdx != secondIdx) {
                            // 是有效的组合
                            ArrayList<Integer> triplets = new ArrayList<>(Arrays.asList(arr[firstIdx], arr[secondIdx], arr[thirdIdx]));
                            Collections.sort(triplets);
                            set.add(triplets);
                        }
                    }
                }
            }
            return new ArrayList<>(set);
        }

    }

    private static class BruteForce3 {

        public static List<List<Integer>> threeSum(int[] arr) {
            // O(N*logN)
            Arrays.sort(arr);

            for (int i = 0; i < arr.length; i++) {

            }

            return null;
        }

    }

    private static class Test {

        public static List<List<Integer>> threeSum(int[] arr) {
            Arrays.sort(arr);
            int zero = 0;
            List<List<Integer>> res = new LinkedList<>();
            for (int i = 0; i < arr.length - 2; i++) {
                if (i == 0 || (i > 0 && arr[i] != arr[i - 1])) {
                    int low = i + 1;
                    int high = arr.length - 1;
                    int sum = zero - arr[i];
                    while (low < high) {
                        if (arr[low] + arr[high] == sum) {
                            res.add(Arrays.asList(arr[i], arr[low], arr[high]));
                            while (low < high && arr[low] == arr[low + 1]) {
                                low++;
                            }
                            while (low < high && arr[high] == arr[high - 1]) {
                                high--;
                            }
                            low++;
                            high--;
                        } else if (arr[low] + arr[high] < sum) {
                            // improve: skip duplicates
                            while (low < high && arr[low] == arr[low + 1]) {
                                low++;
                            }
                            low++;
                        } else {
                            // improve: skip duplicates
                            while (low < high && arr[high] == arr[high - 1]) {
                                high--;
                            }
                            high--;
                        }
                    }
                }
            }
            return res;
        }

    }

    public static void main(String[] args) {
        {
            int[] arr = new int[]{-1, 0, 1, 2, -1, -4};
            List<List<Integer>> lists = BruteForce1.threeSum(arr);
            System.out.println(lists.toString());

            arr = new int[]{-2, 0, 1, 1, 2};
            lists = BruteForce1.threeSum(arr);
            System.out.println(lists.toString());
        }

        {
            int[] arr = new int[]{-1, 0, 1, 2, -1, -4};
            List<List<Integer>> lists = BruteForce2.threeSum(arr);
            System.out.println(lists.toString());

            arr = new int[]{-2, 0, 1, 1, 2};
            lists = BruteForce2.threeSum(arr);
            System.out.println(lists.toString());
        }
    }

}
