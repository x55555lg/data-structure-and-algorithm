package com.lg.leetcode.other.practice;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Xulg
 * Description: leetcode_404
 * Created in 2021-05-31 22:55
 */
class SumOfLeftLeaves {
    /*
     * Given the root of a binary tree, return the sum of all left leaves.
     *
     * Example 1:
     * Input: root = [3,9,20,null,null,15,7]
     * Output: 24
     * Explanation: There are two left leaves in the binary tree, with values 9 and 15 respectively.
     *
     * Example 2:
     * Input: root = [1]
     * Output: 0
     *
     *
     * Constraints:
     * The number of nodes in the tree is in the range [1, 1000].
     * -1000 <= Node.val <= 1000
     */

    public static int sumOfLeftLeaves(TreeNode root) {
        return 0;
    }

    private static class LevelOrder {
        public static int sumOfLeftLeaves(TreeNode root) {
            if (root == null) {
                return 0;
            }
            if (root.left == null && root.right == null) {
                return 0;
            }
            int sum = 0;
            int currentLevel = 1;
            HashMap<TreeNode, Integer> levelMap = new HashMap<>();
            levelMap.put(root, 1);
            HashMap<TreeNode, Boolean> isLeftMap = new HashMap<>();
            isLeftMap.put(root, false);
            Queue<TreeNode> queue = new LinkedList<>();
            queue.add(root);
            while (!queue.isEmpty()) {
                TreeNode treeNode = queue.poll();
                int level = levelMap.get(treeNode);
                if (level != currentLevel) {
                    currentLevel++;
                }
                if (isLeftMap.get(treeNode) && (treeNode.left == null && treeNode.right == null)) {
                    sum += treeNode.val;
                }
                if (treeNode.left != null) {
                    queue.add(treeNode.left);
                    levelMap.put(treeNode.left, level + 1);
                    isLeftMap.put(treeNode.left, true);
                }
                if (treeNode.right != null) {
                    queue.add(treeNode.right);
                    levelMap.put(treeNode.right, level + 1);
                    isLeftMap.put(treeNode.right, false);
                }
            }
            return sum;
        }
    }

    private static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val) { this.val = val; }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        int sum = LevelOrder.sumOfLeftLeaves(root);
        System.out.println(sum);

        root = new TreeNode(1);
        root.right = new TreeNode(2);
        sum = LevelOrder.sumOfLeftLeaves(root);
        System.out.println(sum);

        root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        sum = LevelOrder.sumOfLeftLeaves(root);
        System.out.println(sum);

        /**
         *             -9
         *         -3      2
         *       n    4   4  0
         *          -6 n  -5
         */
        root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        sum = LevelOrder.sumOfLeftLeaves(root);
        System.out.println(sum);
    }
}
