package com.lg.leetcode.topinterview.practice;

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
        return recurse(root).maxPathSum;
    }

    private static Data recurse(TreeNode treeNode) {
        // base case
        if (treeNode == null) {
            return new Data(Integer.MIN_VALUE, 0);
        }

        // left tree info
        Data leftData = recurse(treeNode.left);
        // right tree info
        Data rightData = recurse(treeNode.right);

        // TODO: 2021/2/23 ......有问题

        // 当前子树的路径和
        int pathSum = leftData.pathSum + rightData.pathSum + treeNode.val;

        // 和当前节点无关，左右子树的最大的那个
        int no = Math.max(leftData.maxPathSum, rightData.maxPathSum);

        /* 和当前节点有关 */
        // 左子树 + 右子树 + 当前节点
        int yesWithLeftRight = leftData.pathSum + rightData.pathSum + treeNode.val;
        // 左子树 + 当前节点
        int yesWithLeft = leftData.pathSum + treeNode.val;
        // 右子树 + 当前节点
        int yesWithRight = rightData.pathSum + treeNode.val;
        // 只要当前节点
        int yesWithSelf = treeNode.val;

        int maxPathSum = Math.max(Math.max(Math.max(Math.max(yesWithLeftRight, yesWithLeft), yesWithRight), yesWithSelf), no);

        return new Data(maxPathSum, pathSum);
    }

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    private static class Data {
        int maxPathSum;
        int pathSum;

        Data(int maxPathSum, int pathSum) {
            this.maxPathSum = maxPathSum;
            this.pathSum = pathSum;
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
