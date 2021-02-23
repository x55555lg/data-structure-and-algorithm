package com.lg.leetcode.topinterview.practice;

/**
 * @author Xulg
 * Created in 2021-02-23 16:41
 */
class MaximumDepthOfBinaryTree {

    /*
     * Given the root of a binary tree, return its maximum depth.
     * A binary tree's maximum depth is the number of nodes along
     * the longest path from the root node down to the farthest leaf node.
     *
     * Example 1:
     * Input: root = [3,9,20,null,null,15,7]
     * Output: 3
     *
     * Example 2:
     * Input: root = [1,null,2]
     * Output: 2
     *
     * Example 3:
     * Input: root = []
     * Output: 0
     *
     * Example 4:
     * Input: root = [0]
     * Output: 1
     *
     * Constraints:
     *  The number of nodes in the tree is in the range [0, 104].
     *  -100 <= Node.val <= 100
     *----------------------------------------------------------------------
     * 求二叉树的最大深度
     */

    public static int maxDepth(TreeNode root) {
        return recurse(root);
    }

    private static int recurse(TreeNode treeNode) {
        if (treeNode == null) {
            return 0;
        }
        int leftLevel = recurse(treeNode.left);
        int rightLevel = recurse(treeNode.right);
        return Math.max(leftLevel, rightLevel) + 1;
    }

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }
}
