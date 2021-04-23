package com.lg.leetcode.topinterview.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * @author Xulg
 * Created in 2021-02-24 14:45
 */
@SuppressWarnings("ForLoopReplaceableByForEach")
class IntersectionOfTwoArrays2 {

    /*
     * Given two arrays, write a function to compute their intersection.
     *
     * Example 1:
     * Input: nums1 = [1,2,2,1], nums2 = [2,2]
     * Output: [2,2]
     *
     * Example 2:
     * Input: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
     * Output: [4,9]
     *
     * Note:
     * Each element in the result should appear as many times as it shows in both arrays.
     * The result can be in any order.
     *
     * Follow up:
     *  1.What if the given array is already sorted? How would you optimize your algorithm?
     *  2.What if nums1's size is small compared to nums2's size? Which algorithm is better?
     *  3.What if elements of nums2 are stored on disk, and the memory is limited such that
     *    you cannot load all elements into the memory at once?
     */

    public static int[] intersect(int[] nums1, int[] nums2) {
        return BruteForceSolution.intersect(nums1, nums2);
    }

    /**
     * 暴力方法
     * // TODO: 2021/3/1 写法错误的
     */
    private static class BruteForceSolution {

        public static int[] intersect(int[] arr1, int[] arr2) {
            if (arr1 == null || arr2 == null || arr1.length == 0 || arr2.length == 0) {
                return new int[0];
            }
            int[] bigger = arr1.length > arr2.length ? arr1 : arr2;
            int[] small = bigger == arr1 ? arr2 : arr1;
            List<Integer> intersect = new ArrayList<>();
            for (int i = 0; i < small.length; i++) {
                if (contains(bigger, small[i])) {
                    intersect.add(small[i]);
                }
            }
            if (intersect.isEmpty()) {
                return new int[0];
            } else {
                int[] result = new int[intersect.size()];
                for (int i = 0; i < result.length; i++) {
                    result[i] = intersect.get(i);
                }
                return result;
            }
        }

        private static boolean contains(int[] arr, int key) {
            if (true) {
                return indexOf(arr, key) != -1;
            } else {
                return binarySearch(arr, key) != -1;
            }
        }

        private static int indexOf(int[] array, int key) {
            for (int i = 0; i < array.length; i++) {
                if (array[i] == key) {
                    return i;
                }
            }
            return -1;
        }

        private static int binarySearch(int[] array, int key) {
            int low = 0, high = array.length - 1;
            while (low <= high) {
                int middle = low + ((high - low) >> 1);
                if (array[middle] > key) {
                    high = middle - 1;
                } else if (array[middle] < key) {
                    low = middle + 1;
                } else {
                    // array[middle] == key
                    return middle;
                }
            }
            return -1;
        }
    }

    /**
     * 优秀解法？从apache common库抄来的
     */
    private static class BestSolution {

        public static int[] intersect(int[] arr1, int[] arr2) {
            if (arr1 == null || arr2 == null || arr1.length == 0 || arr2.length == 0) {
                return new int[0];
            }
            List<Integer> intersect = new ArrayList<>();
            // O(N)
            HashMap<Integer, Integer> countMap1 = getCountMap(arr1);
            // O(N)
            HashMap<Integer, Integer> countMap2 = getCountMap(arr2);
            // O(N)
            HashSet<Integer> distinctArr2 = new HashSet<>();
            for (int i = 0; i < arr2.length; i++) {
                distinctArr2.add(arr2[i]);
            }
            // O(N)
            for (Integer value : distinctArr2) {
                // value在arr1中有几个
                Integer c1 = countMap1.getOrDefault(value, 0);
                // value在arr2中有几个
                Integer c2 = countMap2.getOrDefault(value, 0);
                // 取最小的个数
                int min = Math.min(c1, c2);
                for (int i = 0; i < min; i++) {
                    intersect.add(value);
                }
            }
            if (intersect.isEmpty()) {
                return new int[0];
            } else {
                int[] result = new int[intersect.size()];
                for (int i = 0; i < result.length; i++) {
                    result[i] = intersect.get(i);
                }
                return result;
            }
        }

        private static HashMap<Integer, Integer> getCountMap(int[] arr) {
            HashMap<Integer, Integer> countMap = new HashMap<>();
            for (int i = 0; i < arr.length; i++) {
                int v = arr[i];
                if (countMap.containsKey(v)) {
                    countMap.put(v, countMap.get(v) + 1);
                } else {
                    countMap.put(v, 1);
                }
            }
            return countMap;
        }

    }

    public static void main(String[] args) {
        int[] arr1 = new int[]{1, 2, 2, 1};
        int[] arr2 = new int[]{2, 2};
        int[] intersect = BruteForceSolution.intersect(arr1, arr2);
        System.out.println(Arrays.toString(intersect));

        arr1 = new int[]{4, 9, 5};
        arr2 = new int[]{9, 4, 9, 8, 4};
        intersect = BruteForceSolution.intersect(arr1, arr2);
        System.out.println(Arrays.toString(intersect));

        arr1 = new int[]{1, 2, 2, 1};
        arr2 = new int[]{2, 2};
        intersect = BestSolution.intersect(arr1, arr2);
        System.out.println(Arrays.toString(intersect));

        arr1 = new int[]{4, 9, 5};
        arr2 = new int[]{9, 4, 9, 8, 4};
        intersect = BestSolution.intersect(arr1, arr2);
        System.out.println(Arrays.toString(intersect));

    }

}
