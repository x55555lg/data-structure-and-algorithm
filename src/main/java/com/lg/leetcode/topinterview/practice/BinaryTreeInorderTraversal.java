package com.lg.leetcode.topinterview.practice;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author Xulg
 * Created in 2021-02-22 10:49
 */
class BinaryTreeInorderTraversal {

    /*
     * Given the root of a binary tree, return the inorder traversal of its nodes' values.
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
     *----------------------------------------------------------------------------------------
     * 二叉树的中序遍历
     */

    public static List<Integer> inorderTraversal(TreeNode root) {
        if (root == null) {
            return null;
        }
        List<Integer> list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        while (!stack.isEmpty() || root != null) {
            if (root != null) {
                stack.push(root);
                root = root.left;
            } else {
                TreeNode node = stack.pop();
                // operate the node value
                list.add(node.val);
                root = node.right;
            }
        }
        return list;
    }

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }

    public static List<Integer> inorderTraversal2222(TreeNode root) {
        if (root == null) {
            return new ArrayList<>(0);
        }
        List<Integer> list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        while (!stack.isEmpty() || root != null) {
            if (root != null) {
                stack.push(root);
                root = root.left;
            } else {
                TreeNode node = stack.pop();
                list.add(node.val);
                root = node.right;
            }
        }
        return list;
    }

}
