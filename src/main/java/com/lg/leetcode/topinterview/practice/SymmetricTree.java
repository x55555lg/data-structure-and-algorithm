package com.lg.leetcode.topinterview.practice;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Xulg
 * Created in 2021-03-08 17:21
 */
class SymmetricTree {

    /*
     * Given the root of a binary tree, check whether it is a mirror of itself (i.e., symmetric around its center).
     *
     * Example 1:
     *		            ①
     *	            ╱     ╲
     *	         ╱	          ╲
     *         ②	             ②
     *      ╱	  ╲	       ╱  ╲
     *   ╱	         ╲     ╱	      ╲
     * ③			  ④  ④		    ③
     * Input: root = [1,2,2,3,4,4,3]
     * Output: true
     *
     * Example 2:
     *		            ①
     *	            ╱     ╲
     *	         ╱	          ╲
     *         ②	             ②
     *      	  ╲	           ╲
     *   	         ╲     	      ╲
     *  			  ③    		    ③
     * Input: root = [1,2,2,null,3,null,3]
     * Output: false
     *
     * Constraints:
     *  The number of nodes in the tree is in the range [1, 1000].
     *  -100 <= Node.val <= 100
     *
     * Follow up: Could you solve it both recursively and iteratively?
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

    public static boolean isSymmetric(TreeNode root) {
        return Solution1.isSymmetric(root);
    }

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    /**
     * 递归
     */
    private static class Solution1 {

        public static boolean isSymmetric(TreeNode root) {
            if (root == null) {
                return true;
            }
            return isSymmetricTree(root.left, root.right);
        }

        /**
         * 数1和树2是否镜像对称
         *
         * @param tree1 the tree one
         * @param tree2 the tree two
         * @return is symmetric
         */
        @SuppressWarnings("ConstantConditions")
        private static boolean isSymmetricTree(TreeNode tree1, TreeNode tree2) {
            if (tree1 == null && tree2 == null) {
                // 两颗树都是null，那么肯定镜像对称
                return true;
            }
            if (tree1 == null && tree2 != null) {
                // 一颗树为null，一颗树非null，一定不是镜像对称
                return false;
            }
            if (tree1 != null && tree2 == null) {
                // 一颗树为null，一颗树非null，一定不是镜像对称
                return false;
            }
            if (tree1.val != tree2.val) {
                // 值不想等，肯定不是
                return false;
            }
            // 树1的左节点是否和树2的右节点相等
            boolean left = isSymmetricTree(tree1.left, tree2.right);
            // 树1的右节点是否和树2的左节点相等
            boolean right = isSymmetricTree(tree1.right, tree2.left);
            return left && right;
        }
    }

    /**
     * 非递归
     */
    private static class Solution2 {

        @SuppressWarnings({"ConstantConditions", "RedundantIfStatement"})
        public static boolean isSymmetric(TreeNode root) {
            if (root == null) {
                return true;
            }
            if (root.left == null && root.right == null) {
                return true;
            }
            if (root.left == null && root.right != null) {
                return false;
            }
            if (root.left != null && root.right == null) {
                return false;
            }

            Queue<TreeNode> leftQueue = new LinkedList<>();
            leftQueue.add(root.left);
            Queue<TreeNode> rightQueue = new LinkedList<>();
            rightQueue.add(root.right);
            while (!leftQueue.isEmpty() && !rightQueue.isEmpty()) {
                TreeNode leftNode = leftQueue.poll();
                TreeNode rightNode = rightQueue.poll();
                if (leftNode.val != rightNode.val) {
                    return false;
                }
                if (leftNode.left == null && rightNode.right != null) {
                    // 子树1的左节点为空，子树2的右节点不为空，肯定不是镜像对称
                    return false;
                }
                if (leftNode.left != null && rightNode.right == null) {
                    // 子树1的左节点不为空，子树2的右节点为空，肯定不是镜像对称
                    return false;
                }

                // 这里2种情况
                // 子树1子树2的左右节点都为空
                // 子树1子树2的左右节点都不为空
                if (leftNode.left != null) {
                    leftQueue.add(leftNode.left);
                }
                if (leftNode.right != null) {
                    leftQueue.add(leftNode.right);
                }
                if (rightNode.right != null) {
                    rightQueue.add(rightNode.right);
                }
                if (rightNode.left != null) {
                    rightQueue.add(rightNode.left);
                }
            }
            if (!leftQueue.isEmpty() || !rightQueue.isEmpty()) {
                // 左右子树的节点数量都不一样，肯定不是镜像对称
                return false;
            }
            return true;
        }

    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(2);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.right.left = new TreeNode(4);
        root.right.right = new TreeNode(3);
        System.out.println(Solution1.isSymmetric(root));

        root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(2);
        root.left.left = null;
        root.left.right = new TreeNode(3);
        root.right.right = null;
        root.right.right = new TreeNode(3);
        System.out.println(Solution1.isSymmetric(root));

        root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(2);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.right.left = new TreeNode(4);
        root.right.right = new TreeNode(3);
        System.out.println(Solution2.isSymmetric(root));

        root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(2);
        root.left.left = null;
        root.left.right = new TreeNode(3);
        root.right.right = null;
        root.right.right = new TreeNode(3);
        System.out.println(Solution2.isSymmetric(root));
    }

}
