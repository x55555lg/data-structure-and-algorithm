package com.lg.leetcode.topinterview.practice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * @author Xulg
 * Created in 2021-02-22 11:46
 */
@SuppressWarnings("MapOrSetKeyShouldOverrideHashCodeEquals")
class BinaryTreeLevelOrderTraversal {

    /*
     * Given the root of a binary tree, return the level order traversal of its nodes' values.
     * (i.e., from left to right, level by level).
     *
     * Constraints:
     * The number of nodes in the tree is in the range [0, 2000].
     * -1000 <= Node.val <= 1000
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

    /*
     * levelOrder1(...) levelOrder2(...) 时间复杂度，空间复杂度差不多
     * levelOrder3(...) 时间复杂度，空间复杂度都更优秀
     */

    /**
     * 层级遍历，返回每一层的节点值
     */
    public static List<List<Integer>> levelOrder1(TreeNode root) {
        if (root == null) {
            return new ArrayList<>(0);
        }

        // key为第level层 value为第level层的节点值列表
        LinkedHashMap<Integer, List<Integer>> levelMap = new LinkedHashMap<>(16);

        // key为节点，value为节点在哪层
        HashMap<TreeNode, Integer> nodeLevelMap = new HashMap<>(16);
        // root节点在1层
        nodeLevelMap.put(root, 1);

        Queue<TreeNode> queue = new LinkedList<>();
        // add the root node to the queue
        queue.add(root);

        // 当前在操作哪层
        int currentLevel = 1;
        while (!queue.isEmpty()) {
            TreeNode treeNode = queue.poll();
            // 节点在哪层
            int level = nodeLevelMap.get(treeNode);
            // 判断当前层和节点所在层是否一样
            if (currentLevel == level) {
                // 更新这一层的信息
                if (levelMap.containsKey(currentLevel)) {
                    levelMap.get(currentLevel).add(treeNode.val);
                } else {
                    levelMap.put(currentLevel, new ArrayList<>(Collections.singleton(treeNode.val)));
                }
            } else {
                // 已经是下一层了，加入新层的信息
                levelMap.put(++currentLevel, new ArrayList<>(Collections.singleton(treeNode.val)));
            }
            if (treeNode.left != null) {
                queue.add(treeNode.left);
                nodeLevelMap.put(treeNode.left, level + 1);
            }
            if (treeNode.right != null) {
                queue.add(treeNode.right);
                nodeLevelMap.put(treeNode.right, level + 1);
            }
        }

        // 返回每一层的信息
        return new ArrayList<>(levelMap.values());
    }

    /**
     * 层级遍历，返回每一层的节点值
     */
    public static List<List<Integer>> levelOrder2(TreeNode root) {
        if (root == null) {
            return new ArrayList<>(0);
        }
        List<List<Integer>> result = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            List<Integer> list = new ArrayList<>();
            // 一层的节点
            // size为这一层的节点数量
            for (int i = 0, size = queue.size(); i < size; i++) {
                TreeNode node = queue.poll();
                //noinspection ConstantConditions
                list.add(node.val);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            result.add(list);
        }
        return result;
    }

    /**
     * 层级遍历，返回每一层的节点值
     */
    public static List<List<Integer>> levelOrder3(TreeNode root) {
        if (root == null) {
            return new ArrayList<>(0);
        }
        LinkedHashMap<Integer, List<Integer>> levelMap = new LinkedHashMap<>();
        // 用了先序遍历的思想
        recurse(root, 1, levelMap);
        return new ArrayList<>(levelMap.values());
    }

    private static void recurse(TreeNode treeNode, int level, Map<Integer, List<Integer>> levelMap) {
        if (treeNode == null) {
            return;
        }
        if (levelMap.containsKey(level)) {
            levelMap.get(level).add(treeNode.val);
        } else {
            levelMap.put(level, new ArrayList<>(Collections.singletonList(treeNode.val)));
        }
        recurse(treeNode.left, level + 1, levelMap);
        recurse(treeNode.right, level + 1, levelMap);
    }

    /* ****************************************************************************************************************/

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

        List<List<Integer>> lists = levelOrder1(root);
        for (List<Integer> list : lists) {
            System.out.println(list.toString());
        }

        lists = levelOrder2(root);
        for (List<Integer> list : lists) {
            System.out.println(list.toString());
        }

        lists = levelOrder3(root);
        for (List<Integer> list : lists) {
            System.out.println(list.toString());
        }
    }
}
