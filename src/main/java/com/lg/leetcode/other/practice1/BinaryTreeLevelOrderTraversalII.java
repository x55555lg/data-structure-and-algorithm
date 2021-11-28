package com.lg.leetcode.other.practice1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * @author Xulg
 * Description: leetcode_107
 * Created in 2021-05-27 23:09
 */
class BinaryTreeLevelOrderTraversalII {
    /*
     *  Given the root of a binary tree, return the bottom-up level order traversal
     * of its nodes' values.
     *  (i.e., from left to right, level by level from leaf to root).
     *
     * Example 1:
     * Input: root = [3,9,20,null,null,15,7]
     * Output: [[15,7],[9,20],[3]]
     *
     * Example 2:
     * Input: root = [1]
     * Output: [[1]]
     *
     * Example 3:
     * Input: root = []
     * Output: []
     *
     * Constraints:
     * The number of nodes in the tree is in the range [0, 2000].
     * -1000 <= Node.val <= 1000
     */

    public static List<List<Integer>> levelOrderBottom(TreeNode root) {
        return null;
    }

    private static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val) { this.val = val; }
    }
}
