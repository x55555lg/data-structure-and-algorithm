package com.lg.leetcode.top100likedquestions.practice1;

/**
 * @author Xulg
 * Created in 2021-03-11 10:14
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
class DiameterOfBinaryTree {

    /*
     *  Given the root of a binary tree, return the length of the diameter of the tree.
     *  The diameter of a binary tree is the length of the longest path between any two
     * nodes in a tree. This path may or may not pass through the root.
     *  The length of a path between two nodes is represented by the number of edges
     * between them.
     *
     * Example 1:
     *		            ①
     *	            ╱     ╲
     *	         ╱	          ╲
     *         ②	            ③
     *      ╱	  ╲
     *   ╱	         ╲
     * ④			  ⑤
     * Input: root = [1,2,3,4,5]
     * Output: 3
     * Explanation: 3is the length of the path [4,2,1,3] or [5,2,1,3].
     *
     * Example 2:
     * Input: root = [1,2]
     * Output: 1
     *
     * Constraints:
     *  The number of nodes in the tree is in the range [1, 104].
     *  -100 <= Node.val <= 100
     */

    public static int diameterOfBinaryTree(TreeNode root) {
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
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);
        TreeNode node6 = new TreeNode(6);
        TreeNode node7 = new TreeNode(7);
        root.left = node2;
        root.right = node3;
        node2.left = node4;
        node2.right = node5;
        node3.left = node6;
        node3.right = node7;
    }
}
