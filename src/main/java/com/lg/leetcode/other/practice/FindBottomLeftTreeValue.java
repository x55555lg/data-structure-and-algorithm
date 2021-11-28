package com.lg.leetcode.other.practice;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Xulg
 * Description: leetcode_513
 * Created in 2021-05-31 22:15
 */
@SuppressWarnings("MapOrSetKeyShouldOverrideHashCodeEquals")
class FindBottomLeftTreeValue {

    /*
     * Given the root of a binary tree, return the leftmost value in the last row of the tree.
     *
     * Example 1:
     * Input: root = [2,1,3]
     * Output: 1
     *
     * Example 2:
     * Input: root = [1,2,3,4,null,5,6,null,null,7]
     * Output: 7
     *
     * Constraints:
     * The number of nodes in the tree is in the range [1, 104].
     * -2^31 <= Node.val <= 2^31 - 1
     */

    public int findBottomLeftValue(TreeNode root) {
        return 0;
    }

    private static class LevelOrder {
        public static int findBottomLeftValue(TreeNode root) {
            if (root == null) {
                throw new Error();
            }
            if (root.left == null && root.right == null) {
                return root.val;
            }
            TreeNode mostBottomLeftTreeNode = root;
            int currentLevel = 1;
            HashMap<TreeNode, Integer> levelMap = new HashMap<>();
            levelMap.put(root, 1);
            Queue<TreeNode> queue = new LinkedList<>();
            queue.add(root);
            while (!queue.isEmpty()) {
                TreeNode treeNode = queue.poll();
                int level = levelMap.get(treeNode);
                if (level != currentLevel) {
                    // 记录这新层的第一个节点
                    mostBottomLeftTreeNode = treeNode;
                    currentLevel++;
                }
                if (treeNode.left != null) {
                    queue.add(treeNode.left);
                    levelMap.put(treeNode.left, level + 1);
                }
                if (treeNode.right != null) {
                    queue.add(treeNode.right);
                    levelMap.put(treeNode.right, level + 1);
                }
            }
            return mostBottomLeftTreeNode.val;
        }
    }

    private static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val) { this.val = val; }
    }
}
