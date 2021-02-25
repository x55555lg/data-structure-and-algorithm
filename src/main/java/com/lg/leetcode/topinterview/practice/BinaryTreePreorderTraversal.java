package com.lg.leetcode.topinterview.practice;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author Xulg
 * Created in 2021-02-25 16:00
 */
class BinaryTreePreorderTraversal {

    /*
     * Given the root of a binary tree, return the preorder traversal of its nodes' values.
     *
     * Example 1:
     * Input: root = [1,null,2,3]
     * Output: [1,2,3]
     *
     * Example 2:
     * Input: root = []
     * Output: []
     *
     * Example 3:
     * Input: root = [1]
     * Output: [1]
     *
     * Example 4:
     * Input: root = [1,2]
     * Output: [1,2]
     *
     * Example 5:
     * Input: root = [1,null,2]
     * Output: [1,2]
     *
     * Constraints:
     *  The number of nodes in the tree is in the range [0, 100].
     *  -100 <= Node.val <= 100
     *
     * Follow up: Recursive solution is trivial, could you do it iteratively?
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

    public static List<Integer> preorderTraversal(TreeNode root) {
        if (root == null) {
            return new ArrayList<>(0);
        }

        return null;
    }

    private static class PreorderTraversalByRecurse {

        public static List<Integer> preorderTraversal(TreeNode root) {
            List<Integer> data = new ArrayList<>();
            recurse(root, data);
            return data;
        }

        private static void recurse(TreeNode treeNode, List<Integer> data) {
            if (treeNode == null) {
                return;
            }
            data.add(treeNode.val);
            recurse(treeNode.left, data);
            recurse(treeNode.right, data);
        }

    }

    private static class PreorderTraversalByIterative {

        public static List<Integer> preorderTraversal(TreeNode root) {
            if (root == null) {
                return new ArrayList<>(0);
            }
            List<Integer> data = new ArrayList<>();
            Stack<TreeNode> stack = new Stack<>();
            stack.add(root);
            while (!stack.isEmpty()) {
                TreeNode treeNode = stack.pop();
                data.add(treeNode.val);
                if (treeNode.right != null) {
                    stack.add(treeNode.right);
                }
                if (treeNode.left != null) {
                    stack.add(treeNode.left);
                }
            }
            return data;
        }

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
