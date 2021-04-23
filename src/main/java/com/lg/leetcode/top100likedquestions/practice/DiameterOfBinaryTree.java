package com.lg.leetcode.top100likedquestions.practice;

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
        return TreeDP.diameterOfBinaryTree(root);
    }

    private static class TreeDP {

        public static int diameterOfBinaryTree(TreeNode root) {
            return recurse(root).maxDiameter;
        }

        private static Data recurse(TreeNode treeNode) {
            if (treeNode == null) {
                return new Data(0, 0);
            }

            // 左右子树的信息
            Data leftData = recurse(treeNode.left);
            Data rightData = recurse(treeNode.right);

            // 当前树的高度
            int height = Math.max(leftData.height, rightData.height) + 1;

            // 最大直径和当前节点无关，取左右子节点直径的最大值
            int no = Math.max(leftData.maxDiameter, rightData.maxDiameter);
            // 最大直径和当前节点有关，当前树的直径 = 左右子树的高度和
            int yes = leftData.height + rightData.height;
            // 取最大值
            int maxDiameter = Math.max(no, yes);

            return new Data(height, maxDiameter);
        }

        private static class Data {
            // 最大直径
            int maxDiameter;
            // 树的高度
            int height;

            Data(int height, int maxDiameter) {
                this.height = height;
                this.maxDiameter = maxDiameter;
            }
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
        System.out.println(TreeDP.diameterOfBinaryTree(root));
    }
}
