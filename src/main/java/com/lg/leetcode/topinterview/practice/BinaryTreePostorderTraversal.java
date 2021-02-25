package com.lg.leetcode.topinterview.practice;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author Xulg
 * Created in 2021-02-25 16:01
 */
class BinaryTreePostorderTraversal {

    /*
     * Given the root of a binary tree, return the postorder traversal of its nodes' values.
     *
     * Example 1:
     * Input: root = [1,null,2,3]
     * Output: [3,2,1]
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
     * Output: [2,1]
     *
     * Example 5:
     * Input: root = [1,null,2]
     * Output: [2,1]
     *
     * Constraints:
     *  The number of the nodes in the tree is in the range [0, 100].
     *  -100 <= Node.val <= 100
     *
     * Follow up:
     *  Recursive solution is trivial, could you do it iteratively?
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

    public List<Integer> postorderTraversal(TreeNode root) {
        return null;
    }

    private static class PostorderTraversalByRecurse {

        public static List<Integer> postorderTraversal(TreeNode root) {
            List<Integer> data = new ArrayList<>();
            recurse(root, data);
            return data;
        }

        private static void recurse(TreeNode treeNode, List<Integer> data) {
            if (treeNode == null) {
                return;
            }
            recurse(treeNode.left, data);
            recurse(treeNode.right, data);
            data.add(treeNode.val);
        }

    }

    private static class PostorderTraversalByIterative {

        public static List<Integer> postorderTraversal(TreeNode root) {
            if (root == null) {
                return new ArrayList<>(0);
            }
            List<Integer> data = new ArrayList<>();
            Stack<TreeNode> helper = new Stack<>();
            Stack<TreeNode> stack = new Stack<>();
            stack.add(root);
            while (!stack.isEmpty()) {
                TreeNode treeNode = stack.pop();
                helper.add(treeNode);
                if (treeNode.left != null) {
                    stack.add(treeNode.left);
                }
                if (treeNode.right != null) {
                    stack.add(treeNode.right);
                }
            }
            while (!helper.isEmpty()) {
                data.add(helper.pop().val);
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
