package com.lg.leetcode.topinterview.practice1;

/**
 * @author Xulg
 * Created in 2021-03-15 13:53
 */
@SuppressWarnings({"unused", "AlibabaLowerCamelCaseVariableNaming"})
class ConvertSortedArrayToBinarySearchTree {

    /*
     * Given an integer array nums where the elements are sorted in ascending order, convert it to a height-balanced binary search tree.
     * A height-balanced binary tree is a binary tree in which the depth of the two subtrees of every node never differs by more than one.
     *
     * Example 1:
     *
     * Input: nums = [-10,-3,0,5,9]
     * Output: [0,-3,9,-10,null,5]
     * Explanation: [0,-10,5,null,-3,null,9] is also accepted:
     *
     * Example 2:
     *
     * Input: nums = [1,3]
     * Output: [3,1]
     * Explanation: [1,3] and [3,1] are both a height-balanced BSTs.
     *
     * Constraints:
     *  1 <= nums.length <= 104
     *  -104 <= nums[i] <= 104
     *  nums is sorted in a strictly increasing order.
     *
     * Definition for a binary tree node.
     * public class TreeNode {
     *     int val;
     *     TreeNode left;
     *     TreeNode right;
     *     TreeNode() {}
     *     TreeNode(int val) { this.val = val; }
     *     TreeNode(int val, TreeNode left, TreeNode right) {
     *         this.val = val;
     *         this.left = left;
     *         this.right = right;
     *     }
     * }
     */

    public static TreeNode sortedArrayToBST(int[] nums) {
        return null;
    }

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return "TreeNode{" + "val=" + val + '}';
        }
    }

    public static void main(String[] args) {
        TreeNode root = sortedArrayToBST(new int[]{1, 2, 3, 4, 5, 6});
        System.out.println(root);
        root = sortedArrayToBST(new int[]{-10, -3, 0, 5, 9});
        System.out.println(root);
    }
}
