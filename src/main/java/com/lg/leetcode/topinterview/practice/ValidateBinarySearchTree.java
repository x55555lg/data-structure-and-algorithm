package com.lg.leetcode.topinterview.practice;

/**
 * @author Xulg
 * Created in 2021-02-25 10:50
 */
@SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
class ValidateBinarySearchTree {

    /*
     * Given the root of a binary tree, determine if it is a valid binary search tree (BST).
     * A valid BST is defined as follows:
     *  The left subtree of a node contains only nodes with keys less than the node's key.
     *  The right subtree of a node contains only nodes with keys greater than the node's key.
     *  Both the left and right subtrees must also be binary search trees.
     *
     * Example 1:
     * Input: root = [2,1,3]
     * Output: true
     *
     * Example 2:
     * Input: root = [5,1,4,null,null,3,6]
     * Output: false
     * Explanation: The root node's value is 5 but its right child's value is 4.
     *
     * Constraints:
     *  The number of nodes in the tree is in the range [1, 104].
     *  -231 <= Node.val <= 231 - 1
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
     *----------------------------------------------------------------------------------
     * 二叉搜索树(BinarySearchTree，BST)
     * 对于任意节点X都要满足：
     *      X节点的值都要大于其左子树中任意节点的值
     *      X节点的值都要小于右子树中任意节点的值
     *
     */

    public static boolean isValidBST(TreeNode root) {
        return recurse(root).isBST;
    }

    private static Data recurse(TreeNode treeNode) {
        if (treeNode == null) {
            // leetcode的测试用例数据有坑，这里得用long类型
            // 输入二叉树如下[2147483647]，通不过测试
            return new Data(Long.MIN_VALUE, Long.MAX_VALUE, true);
        }
        Data leftData = recurse(treeNode.left);
        Data rightData = recurse(treeNode.right);
        long maxVal = Math.max(Math.max(leftData.maxVal, rightData.maxVal), treeNode.val);
        long minVal = Math.min(Math.min(leftData.minVal, rightData.minVal), treeNode.val);
        boolean isBST = leftData.isBST && rightData.isBST
                && (treeNode.val > leftData.maxVal && treeNode.val < rightData.minVal);
        return new Data(maxVal, minVal, isBST);
    }

    private static class Data {
        // 子树的最大值
        long maxVal;
        // 子树的最小值
        long minVal;
        // 子树是否是二叉搜索树
        boolean isBST;

        Data(long maxVal, long minVal, boolean isBST) {
            this.maxVal = maxVal;
            this.minVal = minVal;
            this.isBST = isBST;
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
