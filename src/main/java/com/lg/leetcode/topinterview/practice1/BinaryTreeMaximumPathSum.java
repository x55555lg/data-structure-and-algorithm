package com.lg.leetcode.topinterview.practice1;

/**
 * @author Xulg
 * Created in 2021-02-23 15:12
 */
class BinaryTreeMaximumPathSum {

    /*
     * A path in a binary tree is a sequence of nodes where each pair of adjacent
     * nodes in the sequence has an edge connecting them. A node can only appear
     * in the sequence at most once. Note that the path does not need to pass
     * through the root.
     *
     * The path sum of a path is the sum of the node's values in the path.
     *
     * Given the root of a binary tree, return the maximum path sum of any path.
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
     *------------------------------------------------------------------------------
     */

    public static int maxPathSum(TreeNode root) {
        return 0;
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
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        int maxPathSum = maxPathSum(root);
        System.out.println(maxPathSum);

        root = new TreeNode(-10);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        maxPathSum = maxPathSum(root);
        System.out.println(maxPathSum);

        root = new TreeNode(-3);
        maxPathSum = maxPathSum(root);
        System.out.println(maxPathSum);

        root = new TreeNode(1);
        root.left = new TreeNode(2);
        maxPathSum = maxPathSum(root);
        System.out.println(maxPathSum);

        root = new TreeNode(2);
        root.left = new TreeNode(-1);
        maxPathSum = maxPathSum(root);
        System.out.println(maxPathSum);

        root = new TreeNode(1);
        root.left = new TreeNode(-2);
        root.right = new TreeNode(3);
        maxPathSum = maxPathSum(root);
        System.out.println(maxPathSum);

        // [5,4,8,11,null,13,4,7,2,null,null,null,1]
        // expect 48
    }

}
