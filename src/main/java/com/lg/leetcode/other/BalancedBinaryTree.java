package com.lg.leetcode.other;

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
        return TreeDP.isBalanced(root);
    }

    private static class TreeDP {
        public static boolean isBalanced(TreeNode root) {
            return recurse(root).isBalanced;
        }

        private static Data recurse(TreeNode node) {
            if (node == null) {
                return new Data(0, true);
            }
            Data left = recurse(node.left);
            Data right = recurse(node.right);
            int height = Math.max(left.height, right.height) + 1;
            boolean isBalanced = left.isBalanced && right.isBalanced
                    && (Math.abs(right.height - left.height) <= 1);
            return new Data(height, isBalanced);
        }

        private static class Data {
            int height;
            boolean isBalanced;

            Data(int height, boolean isBalanced) {
                this.height = height;
                this.isBalanced = isBalanced;
            }
        }
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
