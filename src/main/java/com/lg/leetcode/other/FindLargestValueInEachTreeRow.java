package com.lg.leetcode.other;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Xulg
 * Description: leetcode_515
 * Created in 2021-05-31 17:35
 */
class FindLargestValueInEachTreeRow {

    /*
     *  Given the root of a binary tree, return an array of the largest
     * value in each row of the tree (0-indexed).
     *
     * Example 1:
     * Input: root = [1,3,2,5,3,null,9]
     * Output: [1,3,9]
     *
     * Example 2:
     * Input: root = [1,2,3]
     * Output: [1,3]
     *
     * Example 3:
     * Input: root = [1]
     * Output: [1]
     *
     * Example 4:
     * Input: root = [1,null,2]
     * Output: [1,2]
     *
     * Example 5:
     * Input: root = []
     * Output: []
     *
     * Constraints:
     * The number of nodes in the tree will be in the range [0, 104].
     * -231 <= Node.val <= 231 - 1
     *-----------------------------------------------------------------------
     * 获取二叉树每一层的最大值
     */

    public static List<Integer> largestValues(TreeNode root) {
        return LevelOrder.largestValues(root);
    }

    private static class LevelOrder {
        public static List<Integer> largestValues(TreeNode root) {
            if (root == null) {
                return new ArrayList<>(0);
            }
            List<Integer> answer = new LinkedList<>();
            int currentLevel = 1;
            int currentLevelMaxVal = Integer.MIN_VALUE;
            HashMap<TreeNode, Integer> levelMap = new HashMap<>();
            levelMap.put(root, 1);
            Queue<TreeNode> queue = new LinkedList<>();
            queue.add(root);
            while (!queue.isEmpty()) {
                TreeNode treeNode = queue.poll();
                int level = levelMap.get(treeNode);
                if (level == currentLevel) {
                    currentLevelMaxVal = Math.max(treeNode.val, currentLevelMaxVal);
                } else {
                    // 记录这层的最大值
                    answer.add(currentLevelMaxVal);
                    // 下一层了
                    currentLevelMaxVal = treeNode.val;
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
            answer.add(currentLevelMaxVal);
            return answer;
        }
    }

    private static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val) { this.val = val; }
    }
}
