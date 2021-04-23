package com.lg.leetcode.topinterview.practice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Xulg
 * Created in 2021-02-26 15:38
 */
@SuppressWarnings("MapOrSetKeyShouldOverrideHashCodeEquals")
class BinaryTreeZigzagLevelOrderTraversal {

    /*
     *  Given the root of a binary tree, return the zigzag level order traversal
     * of its nodes' values.(i.e., from left to right, then right to left for the
     * next level and alternate between).
     *
     * Example 1:
     *		            3
     *	            ╱     ╲
     *	         ╱	          ╲
     *         9	            20
     *                       ╱	   ╲
     *                    ╱	      ╲
     *                   15            7
     * Input: root = [3,9,20,null,null,15,7]
     * Output: [[3],[20,9],[15,7]]
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
     * `The number of nodes in the tree is in the range [0, 2000].
     * `-100 <= Node.val <= 100
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

    public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        if (root == null) {
            return new ArrayList<>(0);
        }

        HashMap<Integer, LinkedList<Integer>> result = new HashMap<>(16);
        HashMap<TreeNode, Integer> levelMap = new HashMap<>(16);
        levelMap.put(root, 1);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int currentLevel = 1;

        while (!queue.isEmpty()) {
            TreeNode treeNode = queue.poll();
            int nodeLevel = levelMap.get(treeNode);
            if (nodeLevel == currentLevel) {
                if (!result.containsKey(currentLevel)) {
                    // 1层还没有加进去过
                    result.put(currentLevel, new LinkedList<>());
                }
                // 层数为偶数，奇数处理逻辑不一样
                if (currentLevel % 2 == 0) {
                    // 偶数层，头插法
                    result.get(currentLevel).addFirst(treeNode.val);
                } else {
                    // 奇数层，尾插法
                    result.get(currentLevel).addLast(treeNode.val);
                }
            } else {
                // 新的一层的节点了
                currentLevel++;
                result.put(currentLevel, new LinkedList<>(Collections.singleton(treeNode.val)));
            }
            if (treeNode.left != null) {
                queue.add(treeNode.left);
                levelMap.put(treeNode.left, nodeLevel + 1);
            }
            if (treeNode.right != null) {
                queue.add(treeNode.right);
                levelMap.put(treeNode.right, nodeLevel + 1);
            }
        }
        return new ArrayList<>(result.values());
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
        TreeNode root = new TreeNode(3);
        TreeNode node9 = new TreeNode(9);
        TreeNode node20 = new TreeNode(20);
        TreeNode node15 = new TreeNode(15);
        TreeNode node7 = new TreeNode(7);
        root.left = node9;
        root.right = node20;
        node20.left = node15;
        node20.right = node7;
        List<List<Integer>> lists = zigzagLevelOrder(root);
        for (List<Integer> list : lists) {
            System.out.println(list.toString());
        }
    }
}
