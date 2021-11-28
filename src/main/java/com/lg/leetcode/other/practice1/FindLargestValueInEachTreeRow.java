package com.lg.leetcode.other.practice1;

import java.util.List;

/**
 * @author Xulg
 * Description: leetcode_515
 * Created in 2021-05-31 17:35
 */
class FindLargestValueInEachTreeRow {

    /*
     *  Given the root of a binary tree, return an array of the largest
     * value in each row of the tree (0-indexed).
     *
     * Example 1:
     * Input: root = [1,3,2,5,3,null,9]
     * Output: [1,3,9]
     *
     * Example 2:
     * Input: root = [1,2,3]
     * Output: [1,3]
     *
     * Example 3:
     * Input: root = [1]
     * Output: [1]
     *
     * Example 4:
     * Input: root = [1,null,2]
     * Output: [1,2]
     *
     * Example 5:
     * Input: root = []
     * Output: []
     *
     * Constraints:
     * The number of nodes in the tree will be in the range [0, 104].
     * -231 <= Node.val <= 231 - 1
     *-----------------------------------------------------------------------
     * 获取二叉树每一层的最大值
     */

    public static List<Integer> largestValues(TreeNode root) {
        return null;
    }

    private static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val) { this.val = val; }
    }
}
