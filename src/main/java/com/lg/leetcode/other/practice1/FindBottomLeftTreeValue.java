package com.lg.leetcode.other.practice1;

/**
 * @author Xulg
 * Description: leetcode_513
 * Created in 2021-05-31 22:15
 */
@SuppressWarnings("MapOrSetKeyShouldOverrideHashCodeEquals")
class FindBottomLeftTreeValue {

    /*
     * Given the root of a binary tree, return the leftmost value in the last row of the tree.
     *
     * Example 1:
     * Input: root = [2,1,3]
     * Output: 1
     *
     * Example 2:
     * Input: root = [1,2,3,4,null,5,6,null,null,7]
     * Output: 7
     *
     * Constraints:
     * The number of nodes in the tree is in the range [1, 104].
     * -2^31 <= Node.val <= 2^31 - 1
     */

    public int findBottomLeftValue(TreeNode root) {
        return 0;
    }

    private static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val) { this.val = val; }
    }
}
