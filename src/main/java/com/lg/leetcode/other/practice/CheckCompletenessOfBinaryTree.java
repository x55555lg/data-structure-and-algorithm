package com.lg.leetcode.other.practice;

/**
 * @author Xulg
 * Description: leetcode_958
 * Created in 2021-05-31 9:15
 */
class CheckCompletenessOfBinaryTree {

    /*
     *  Given the root of a binary tree, determine if it is a complete binary tree.
     *  In a complete binary tree, every level, except possibly the last, is completely
     * filled, and all nodes in the last level are as far left as possible. It can have
     * between 1 and 2h nodes inclusive at the last level h.
     *
     * Example 1:
     * Input: root = [1,2,3,4,5,6]
     * Output: true
     * Explanation: Every level before the last is full (ie. levels with node-values {1} and {2, 3}),
     * and all nodes in the last level ({4, 5, 6}) are as far left as possible.
     *
     * Example 2:
     * Input: root = [1,2,3,4,5,null,7]
     * Output: false
     * Explanation: The node with value 7 isn't as far left as possible.
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [1, 100].
     * 1 <= Node.val <= 1000
     */

    public static boolean isCompleteTree(TreeNode root) {
        return TreeDP.isCompleteTree(root);
    }

    private static class TreeDP {
        public static boolean isCompleteTree(TreeNode root) {
            return recurse(root).isCompleted;
        }

        private static Data recurse(TreeNode treeNode) {
            if (treeNode == null) {
                return new Data(0, true, true);
            }
            Data left = recurse(treeNode.left);
            Data right = recurse(treeNode.right);
            int height = Math.max(left.height, right.height) + 1;
            boolean isFull = left.isFull && right.isFull && (left.height == right.height);
            // 当前树是否是完全二叉树
            boolean isCompleted;
            if (isFull) {
                // 当前节点X为首的树即是满二叉树又是完全二叉树
                isCompleted = true;
            } else {
                // 当前节点X为首的树是否是完全二叉树
                if (left.isFull && right.isFull) {
                    // 左边必须比右边高1
                    isCompleted = left.height - right.height == 1;
                } else if (left.isFull && right.isCompleted) {
                    // 左右高度一样
                    isCompleted = left.height == right.height;
                } else if (left.isCompleted && right.isFull) {
                    // 左边必须比右边高1
                    isCompleted = (left.height - right.height == 1);
                } else {
                    isCompleted = false;
                }
            }
            return new Data(height, isCompleted, isFull);
        }

        private static class Data {
            int height;
            boolean isCompleted;
            boolean isFull;

            Data(int height, boolean isCompleted, boolean isFull) {
                this.height = height;
                this.isCompleted = isCompleted;
                this.isFull = isFull;
            }
        }
    }

    private static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val) { this.val = val; }
    }

}
