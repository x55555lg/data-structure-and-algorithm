package com.lg.leetcode.topinterview.practice1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Xulg
 * Created in 2021-02-26 15:38
 */
@SuppressWarnings("MapOrSetKeyShouldOverrideHashCodeEquals")
class BinaryTreeZigzagLevelOrderTraversal {

    /*
     *  Given the root of a binary tree, return the zigzag level order traversal
     * of its nodes' values.(i.e., from left to right, then right to left for the
     * next level and alternate between).
     *
     * Example 1:
     *		            3
     *	            ╱     ╲
     *	         ╱	          ╲
     *         9	            20
     *                       ╱	   ╲
     *                    ╱	      ╲
     *                   15            7
     * Input: root = [3,9,20,null,null,15,7]
     * Output: [[3],[20,9],[15,7]]
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
     * `The number of nodes in the tree is in the range [0, 2000].
     * `-100 <= Node.val <= 100
     *
     * Definition for a binary tree node.
     * public class TreeNode {
     *     int val;
     *     TreeNode left;
     *     TreeNode right;
     *     TreeNode() {}
     *     TreeNode(int val) { this.val = val; }
     *     TreeNode(int val, TreeNode left, TreeNode right) {
     *         this.val = val;
     *         this.left = left;
     *         this.right = right;
     *     }
     * }
     */

    public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        return null;
    }

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        TreeNode node9 = new TreeNode(9);
        TreeNode node20 = new TreeNode(20);
        TreeNode node15 = new TreeNode(15);
        TreeNode node7 = new TreeNode(7);
        root.left = node9;
        root.right = node20;
        node20.left = node15;
        node20.right = node7;
        List<List<Integer>> lists = zigzagLevelOrder(root);
        for (List<Integer> list : lists) {
            System.out.println(list.toString());
        }
    }
}
