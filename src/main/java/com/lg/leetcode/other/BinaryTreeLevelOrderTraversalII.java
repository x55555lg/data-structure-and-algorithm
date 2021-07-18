package com.lg.leetcode.other;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * @author Xulg
 * Description: leetcode_107
 * Created in 2021-05-27 23:09
 */
class BinaryTreeLevelOrderTraversalII {
    /*
     *  Given the root of a binary tree, return the bottom-up level order traversal
     * of its nodes' values.
     *  (i.e., from left to right, level by level from leaf to root).
     *
     * Example 1:
     * Input: root = [3,9,20,null,null,15,7]
     * Output: [[15,7],[9,20],[3]]
     *
     * Example 2:
     * Input: root = [1]
     * Output: [[1]]
     *
     * Example 3:
     * Input: root = []
     * Output: []
     *
     * Constraints:
     * The number of nodes in the tree is in the range [0, 2000].
     * -1000 <= Node.val <= 1000
     */

    public static List<List<Integer>> levelOrderBottom(TreeNode root) {
        return LevelOrderFromBottom.levelOrderBottom(root);
    }

    @SuppressWarnings("MapOrSetKeyShouldOverrideHashCodeEquals")
    private static class LevelOrderFromBottom {
        public static List<List<Integer>> levelOrderBottom(TreeNode root) {
            if (root == null) {
                return new ArrayList<>(0);
            }

            // 每一层的节点列表
            LinkedHashMap<Integer, List<Integer>> answerMap = new LinkedHashMap<>();
            // 节点在哪层
            Map<TreeNode, Integer> levelMap = new HashMap<>();
            levelMap.put(root, 1);
            // 当前所在层
            int currentLevel = 1;

            Queue<TreeNode> queue = new LinkedList<>();
            queue.add(root);
            while (!queue.isEmpty()) {
                TreeNode node = queue.poll();
                int level = levelMap.get(node);
                if (level == currentLevel) {
                    // 节点还是当前层的
                    if (answerMap.containsKey(level)) {
                        answerMap.get(level).add(node.val);
                    } else {
                        List<Integer> path = new ArrayList<>();
                        path.add(node.val);
                        answerMap.put(level, path);
                    }
                } else {
                    // 节点是新的一层的
                    currentLevel++;
                    List<Integer> path = new ArrayList<>();
                    path.add(node.val);
                    answerMap.put(currentLevel, path);
                }
                if (node.left != null) {
                    queue.add(node.left);
                    levelMap.put(node.left, level + 1);
                }
                if (node.right != null) {
                    queue.add(node.right);
                    levelMap.put(node.right, level + 1);
                }
            }
            LinkedList<List<Integer>> lists = new LinkedList<>();
            for (List<Integer> list : answerMap.values()) {
                lists.addFirst(list);
            }
            return lists;
        }
    }

    private static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val) { this.val = val; }
    }
}
