package com.lg.leetcode.top100likedquestions.practice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * @author Xulg
 * Created in 2021-03-11 9:36
 */
@SuppressWarnings("MapOrSetKeyShouldOverrideHashCodeEquals")
class BinaryTreeRightSideView {

    /*
     * Given the root of a binary tree, imagine yourself standing on the right side of it,
     * return the values of the nodes you can see ordered from top to bottom.
     *
     * Example 1:
     *		            ①
     *	            ╱     ╲
     *	         ╱	          ╲
     *         ②	            ③
     *      	  ╲               ╲
     *   	         ╲               ╲
     *  			  ⑤                ④
     * Input: root = [1,2,3,null,5,null,4]
     * Output: [1,3,4]
     *
     * Example 2:
     *		    ①
     *	           ╲
     *	              ╲
     *                  ③
     * Input: root = [1,null,3]
     * Output: [1,3]
     *
     * Example 3:
     * Input: root = []
     * Output: []
     *
     * Constraints:
     *  The number of nodes in the tree is in the range [0, 100].
     *  -100 <= Node.val <= 100
     *-----------------------------------------------------------
     * 字节跳动面试题
     */

    public static List<Integer> rightSideView(TreeNode root) {
        return OrderTraversal.rightSideView(root);
    }

    private static class LevelTraversal {

        public static List<Integer> rightSideView(TreeNode root) {
            if (root == null) {
                return new ArrayList<>(0);
            }

            /*层级遍历，从右边往左边遍历*/
            List<Integer> rightSideView = new ArrayList<>();
            rightSideView.add(root.val);

            Queue<TreeNode> queue = new LinkedList<>();
            queue.add(root);
            Map<TreeNode, Integer> levelMap = new HashMap<>(16);
            levelMap.put(root, 1);

            int curLevel = 1;
            while (!queue.isEmpty()) {
                TreeNode treeNode = queue.poll();
                int nodeLevel = levelMap.get(treeNode);
                if (curLevel != nodeLevel) {
                    // 已经是下一层了
                    curLevel++;
                    // 这个值就是这一层的最右侧的值
                    rightSideView.add(treeNode.val);
                }
                if (treeNode.right != null) {
                    queue.add(treeNode.right);
                    levelMap.put(treeNode.right, nodeLevel + 1);
                }
                if (treeNode.left != null) {
                    queue.add(treeNode.left);
                    levelMap.put(treeNode.left, nodeLevel + 1);
                }
            }
            return rightSideView;
        }

    }

    private static class OrderTraversal {

        public static List<Integer> rightSideView(TreeNode root) {
            List<Integer> rightSideView = new ArrayList<>();
            recurse(root, 0, rightSideView);
            return rightSideView;
        }

        private static void recurse(TreeNode treeNode, int level, List<Integer> rightSideView) {
            if (treeNode == null) {
                return;
            }
            if (rightSideView.size() == level) {
                rightSideView.add(treeNode.val);
            }
            recurse(treeNode.right, level + 1, rightSideView);
            recurse(treeNode.left, level + 1, rightSideView);
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
        System.out.println(LevelTraversal.rightSideView(root));

        System.out.println(OrderTraversal.rightSideView(root));
    }

}
