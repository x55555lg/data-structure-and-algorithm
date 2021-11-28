package com.lg.leetcode.other.practice1;

/**
 * @author Xulg
 * Description: leetcode_404
 * Created in 2021-05-31 22:55
 */
class SumOfLeftLeaves {
    /*
     * Given the root of a binary tree, return the sum of all left leaves.
     *
     * Example 1:
     * Input: root = [3,9,20,null,null,15,7]
     * Output: 24
     * Explanation: There are two left leaves in the binary tree, with values 9 and 15 respectively.
     *
     * Example 2:
     * Input: root = [1]
     * Output: 0
     *
     *
     * Constraints:
     * The number of nodes in the tree is in the range [1, 1000].
     * -1000 <= Node.val <= 1000
     */

    public static int sumOfLeftLeaves(TreeNode root) {
        return 0;
    }

    private static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val) { this.val = val; }
    }

    public static void main(String[] args) {
    }
}
