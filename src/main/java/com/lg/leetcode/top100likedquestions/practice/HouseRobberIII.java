package com.lg.leetcode.top100likedquestions.practice;

import java.util.HashMap;

/**
 * @author Xulg
 * Created in 2021-03-11 10:48
 */
@SuppressWarnings({"AlibabaClassNamingShouldBeCamel", "MapOrSetKeyShouldOverrideHashCodeEquals"})
class HouseRobberIII {

    /*
     *  The thief has found himself a new place for his thievery again. There is only one entrance to this area,
     * called root.
     *  Besides the root, each house has one and only one parent house. After a tour, the smart thief realized
     * that all houses in this place form a binary tree. It will automatically contact the police if two
     * directly-linked houses were broken into on the same night.
     *  Given the root of the binary tree, return the maximum amount of money the thief can rob without alerting
     * the police.
     *
     * Example 1:
     *		            ③
     *	            ╱     ╲
     *	         ╱	          ╲
     *         ②	            ③
     *      	  ╲               ╲
     *   	         ╲               ╲
     *   	  		  ③                ①
     * Input: root = [3,2,3,null,3,null,1]
     * Output: 7
     * Explanation: Maximum amount of money the thief can rob = 3 + 3 + 1 = 7.
     *
     * Example 2:
     *		            ③
     *	            ╱     ╲
     *	         ╱	          ╲
     *         ④	            ⑤
     *      ╱	  ╲               ╲
     *   ╱	         ╲               ╲
     * ①			   ③               ①
     * Input: root = [3,4,5,1,3,null,1]
     * Output: 9
     * Explanation: Maximum amount of money the thief can rob = 4 + 5 = 9.
     *
     * Constraints:
     *  The number of nodes in the tree is in the range [1, 104].
     *  0 <= Node.val <= 104
     */

    public static int rob(TreeNode root) {
        return BruteForce.rob(root);
    }

    private static class BruteForce {

        public static int rob(TreeNode root) {
            return recurse(root);
        }

        private static int recurse(TreeNode treeNode) {
            if (treeNode == null) {
                return 0;
            }

            /*
             * 抢了当前的节点，后面只能去抢当前节点的子节点的子节点
             *  当前节点的左子节点的左节点
             *  当前节点的左子节点的右节点
             *  当前节点的右子节点的左节点
             *  当前节点的右子节点的右节点
             */
            int yes = treeNode.val;
            if (treeNode.left != null) {
                yes = yes + recurse(treeNode.left.left) + recurse(treeNode.left.right);
            }
            if (treeNode.right != null) {
                yes = yes + recurse(treeNode.right.left) + recurse(treeNode.right.right);
            }

            /*
             * 不抢当前的节点，可以有2种选择：
             *  抢当前节点的左子节点
             *  抢当前节点的右子节点
             */
            int no = recurse(treeNode.left) + recurse(treeNode.right);

            // 抢和不抢，返回最大的结果
            return Math.max(yes, no);
        }

    }

    private static class MemorySearch {

        public static int rob(TreeNode root) {
            return recurse(root, new HashMap<>(16));
        }

        private static int recurse(TreeNode treeNode, HashMap<TreeNode, Integer> table) {
            if (treeNode == null) {
                return 0;
            }

            if (table.containsKey(treeNode)) {
                // get from the cache
                return table.get(treeNode);
            }

            /*
             * 抢了当前的节点，后面只能去抢当前节点的子节点的子节点
             *  当前节点的左子节点的左节点
             *  当前节点的左子节点的右节点
             *  当前节点的右子节点的左节点
             *  当前节点的右子节点的右节点
             */
            int yes = treeNode.val;
            if (treeNode.left != null) {
                yes = yes + recurse(treeNode.left.left, table) + recurse(treeNode.left.right, table);
            }
            if (treeNode.right != null) {
                yes = yes + recurse(treeNode.right.left, table) + recurse(treeNode.right.right, table);
            }

            /*
             * 不抢当前的节点，可以有2种选择：
             *  抢当前节点的左子节点
             *  抢当前节点的右子节点
             */
            int no = recurse(treeNode.left, table) + recurse(treeNode.right, table);

            // 抢和不抢，返回最大的结果
            int max = Math.max(yes, no);
            table.put(treeNode, max);

            return max;
        }
    }

    private static class TreeDP {

        public static int rob(TreeNode root) {
            Data data = recurse(root);
            return Math.max(data.yes, data.no);
        }

        private static Data recurse(TreeNode treeNode) {
            if (treeNode == null) {
                return new Data(0, 0);
            }

            Data leftData = recurse(treeNode.left);
            Data rightData = recurse(treeNode.right);

            /*
             * 不抢当前的节点，可以有2种选择：
             *  抢当前节点的左子节点
             *  抢当前节点的右子节点
             */
            int no = Math.max(leftData.yes, leftData.no) + Math.max(rightData.yes, rightData.no);

            /*
             * 抢了当前的节点，后面只能去抢当前节点的子节点的子节点
             *  当前节点的左子节点的左节点
             *                             => 左子节点不抢的值
             *  当前节点的左子节点的右节点
             *
             *  当前节点的右子节点的左节点
             *                             => 右子节点不抢的值
             *  当前节点的右子节点的右节点
             */
            int yes = leftData.no + rightData.no + treeNode.val;

            return new Data(yes, no);
        }

        private static class Data {
            // 当前节点抢了
            int yes;
            // 当前节点没抢
            int no;

            Data(int yes, int no) {
                this.yes = yes;
                this.no = no;
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
        {
            /*
             *		            ③
             *	            ╱     ╲
             *	         ╱	          ╲
             *         ②	            ③
             *      	  ╲               ╲
             *   	         ╲               ╲
             *   	  		  ③                ①
             */
            TreeNode root = new TreeNode(3);
            TreeNode node2 = new TreeNode(2);
            TreeNode node3 = new TreeNode(3);
            root.left = node2;
            root.right = node3;
            node2.left = null;
            node2.right = new TreeNode(3);
            node3.left = null;
            node3.right = new TreeNode(1);
            System.out.println(BruteForce.rob(root));
            System.out.println(MemorySearch.rob(root));
            System.out.println(TreeDP.rob(root));
        }

        {
            /*
             *		            ③
             *	            ╱     ╲
             *	         ╱	          ╲
             *         ④	            ⑤
             *      ╱	  ╲               ╲
             *   ╱	         ╲               ╲
             * ①			   ③               ①
             */
            TreeNode root = new TreeNode(3);
            TreeNode node2 = new TreeNode(4);
            TreeNode node3 = new TreeNode(5);
            root.left = node2;
            root.right = node3;
            node2.left = new TreeNode(1);
            node2.right = new TreeNode(3);
            node3.left = null;
            node3.right = new TreeNode(1);
            System.out.println(BruteForce.rob(root));
            System.out.println(MemorySearch.rob(root));
            System.out.println(TreeDP.rob(root));
        }

    }

}
