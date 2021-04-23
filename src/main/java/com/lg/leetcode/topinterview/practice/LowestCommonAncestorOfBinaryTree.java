package com.lg.leetcode.topinterview.practice;

/**
 * @author Xulg
 * Created in 2021-02-26 14:24
 */
class LowestCommonAncestorOfBinaryTree {

    /*
     * Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.
     * According to the definition of LCA on Wikipedia:
     *   The lowest common ancestor is defined between two nodes p and q as the
     *   lowest node in T that has both p and q as descendants (where we allow a
     *   node to be a descendant of itself).
     *
     * Example 1:
     * Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
     * Output: 3
     * Explanation: The LCA of nodes 5 and 1 is 3.
     *
     * Example 2:
     * Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
     * Output: 5
     * Explanation: The LCA of nodes 5 and 4 is 5, since a node can be a descendant of itself according to the LCA definition.
     *
     * Example 3:
     * Input: root = [1,2], p = 1, q = 2
     * Output: 1
     *
     * Constraints:
     *  The number of nodes in the tree is in the range [2, 105].
     *  -109 <= Node.val <= 109
     *  All Node.val are unique.
     *  p != q
     *  p and q will exist in the tree.
     *
     * Definition for a binary tree node.
     * public class TreeNode {
     *     int val;
     *     TreeNode left;
     *     TreeNode right;
     *     TreeNode(int x) { val = x; }
     * }
     */

    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        return recurse(root, p, q).lca;
    }

    private static Data recurse(TreeNode treeNode, TreeNode p, TreeNode q) {
        if (treeNode == null) {
            return new Data(false, false, null);
        }
        Data leftData = recurse(treeNode.left, p, q);
        Data rightData = recurse(treeNode.right, p, q);
        // 是否在当前子树上找到了p节点
        boolean findP = leftData.findP || rightData.findP || treeNode == p;
        // 是否在当前子树上找到了q节点
        boolean findQ = leftData.findQ || rightData.findQ || treeNode == q;
        // find the lowest common ancestor
        TreeNode lca;
        if (treeNode != p && treeNode != q) {
            // 当前节点X不等于p，q
            if (leftData.findP && leftData.findQ) {
                // p，q节点在左子树上
                lca = leftData.lca;
            } else if (rightData.findP && rightData.findQ) {
                // p，q节点在右子树上
                lca = rightData.lca;
            } else {
                // p，q节点不在一颗树上，当前节点就是lca
                lca = treeNode;
            }
        } else {
            // 当前节点X是p，q中的一个
            lca = treeNode;
        }
        return new Data(findP, findQ, lca);
    }

    private static class Data {
        // 是否在子树上找到了p节点
        boolean findP;
        // 是否在子树上找到了q节点
        boolean findQ;
        // lowest common ancestor
        TreeNode lca;

        Data(boolean findP, boolean findQ, TreeNode lca) {
            this.findP = findP;
            this.findQ = findQ;
            this.lca = lca;
        }
    }

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

}
