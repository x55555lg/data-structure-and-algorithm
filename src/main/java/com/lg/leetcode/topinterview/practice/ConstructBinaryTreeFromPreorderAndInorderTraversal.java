package com.lg.leetcode.topinterview.practice;

/**
 * @author Xulg
 * Created in 2021-02-24 9:22
 */
class ConstructBinaryTreeFromPreorderAndInorderTraversal {

    /*
     * Given two integer arrays preorder and inorder where preorder is
     * the preorder traversal of a binary tree and inorder is the inorder
     * traversal of the same tree, construct and return the binary tree.
     *
     * Example 1:
     *                  3
     *          9               20
     *                      15       7
     * Input: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
     * Output: [3,9,20,null,null,15,7]
     *
     * Example 2:
     * Input: preorder = [-1], inorder = [-1]
     * Output: [-1]
     *
     * Constraints:
     *  1 <= preorder.length <= 3000
     *  inorder.length == preorder.length
     *  -3000 <= preorder[i], inorder[i] <= 3000
     *  preorder and inorder consist of unique values.
     *  Each value of inorder also appears in preorder.
     *  preorder is guaranteed to be the preorder traversal of the tree.
     *  inorder is guaranteed to be the inorder traversal of the tree.
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
     *------------------------------------------------------------------------------------
     * 根据先序中序序列化结果反向构建二叉树
     * 思路：
     *      利用二叉树的前序遍历顺序和中序遍历顺序来构建二叉树，这道题我们可以利用前序遍历和中序遍历的特点来解决，
     *      前序遍历时第一个节点为根节点，然后我们使用该节点找出中序遍历中存在的位置下标，
     *      以中序遍历的特点我们可以知道，根节点的左子树一共存在几个节点，右子树一共存在几个节点。
     *      然后将前序遍历和中序遍历分成两部分，左子树节点和右子树节点。继续使用之前的方法来进行查找(前序遍历的第一个节点，找到中序遍历中存在的位置)。
     */

    public static TreeNode buildTree(int[] preorder, int[] inorder) {
        // TODO: 2021/2/24 不会
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

}
