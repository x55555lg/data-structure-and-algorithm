package com.lg.leetcode.other.practice1;

import java.util.List;

/**
 * @author Xulg
 * Description: leetcode_113
 * Created in 2021-05-28 13:49
 */
class PathSumII {

    /*
     *  Given the root of a binary tree and an integer targetSum, return
     * all root-to-leaf paths where each path's sum equals targetSum.
     *  A leaf is a node with no children.
     *
     * Example 1:
     * Input: root = [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22
     * Output: [[5,4,11,2],[5,8,4,5]]
     *
     * Example 2:
     * Input: root = [1,2,3], targetSum = 5
     * Output: []
     *
     * Example 3:
     * Input: root = [1,2], targetSum = 0
     * Output: []
     *
     * Constraints:
     * The number of nodes in the tree is in the range [0, 5000].
     * -1000 <= Node.val <= 1000
     * -1000 <= targetSum <= 1000
     */

    public static List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        return null;
    }

    private static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val) { this.val = val; }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(4);
        root.right = new TreeNode(8);
        root.left.left = new TreeNode(11);
        root.left.left.left = new TreeNode(7);
        root.left.left.right = new TreeNode(2);
        root.right.left = new TreeNode(13);
        root.right.right = new TreeNode(4);
        root.right.right.left = new TreeNode(5);
        root.right.right.right = new TreeNode(1);
    }
}
