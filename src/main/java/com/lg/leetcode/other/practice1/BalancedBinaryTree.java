package com.lg.leetcode.other.practice1;

/**
 * @author Xulg
 * Description: leetcode_110
 * Created in 2021-05-28 10:31
 */
class BalancedBinaryTree {
    /*
     * Given a binary tree, determine if it is height-balanced.
     * For this problem, a height-balanced binary tree is defined as:
     * a binary tree in which the left and right subtrees of every node
     * differ in height by no more than 1.
     *
     * Example 1:
     * Input: root = [3,9,20,null,null,15,7]
     * Output: true
     *
     * Example 2:
     * Input: root = [1,2,2,3,3,null,null,4,4]
     * Output: false
     *
     * Example 3:
     * Input: root = []
     * Output: true
     *
     * Constraints:
     * The number of nodes in the tree is in the range [0, 5000].
     * -104 <= Node.val <= 104
     *------------------------------------------------------------
     * 判断是否是一个平衡二叉树
     */

    public static boolean isBalanced(TreeNode root) {
        return false;
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
}
