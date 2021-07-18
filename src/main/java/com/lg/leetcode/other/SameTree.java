package com.lg.leetcode.other;

/**
 * @author Xulg
 * Description: leetcode_100
 * Created in 2021-05-27 23:46
 */
class SameTree {

    /*
     *  Given the roots of two binary trees p and q, write a function to check
     * if they are the same or not.
     *  Two binary trees are considered the same if they are structurally identical,
     * and the nodes have the same value.
     *
     * Example 1:
     * Input: p = [1,2,3], q = [1,2,3]
     * Output: true
     *
     * Example 2:
     * Input: p = [1,2], q = [1,null,2]
     * Output: false
     *
     * Example 3:
     * Input: p = [1,2,1], q = [1,1,2]
     * Output: false
     *
     * Constraints:
     * The number of nodes in both trees is in the range [0, 100].
     * -104 <= Node.val <= 104
     */

    public boolean isSameTree(TreeNode p, TreeNode q) {
        return BruteForce.isSameTree(p, q);
    }

    @SuppressWarnings("ConstantConditions")
    private static class BruteForce {
        public static boolean isSameTree(TreeNode root1, TreeNode root2) {
            if (root1 == null && root2 == null) {
                return true;
            }
            if (root1 == null && root2 != null) {
                return false;
            }
            if (root1 != null && root2 == null) {
                return false;
            }
            assert root1 != null && root2 != null;
            if (root1.val != root2.val) {
                return false;
            }
            boolean left = isSameTree(root1.left, root2.left);
            boolean right = isSameTree(root1.right, root2.right);
            return left && right;
        }
    }

    private static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val) { this.val = val; }
    }

}
