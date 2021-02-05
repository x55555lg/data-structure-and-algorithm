package com.lg.datastructure.binarytree.a1.practice;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 计算二叉树层数
 *
 * @author Xulg
 * Created in 2021-01-25 16:52
 */
@SuppressWarnings({"unused", "AlibabaLowerCamelCaseVariableNaming", "MapOrSetKeyShouldOverrideHashCodeEquals"})
class CountBinaryTreeLevels {

    /**
     * 树形DP方式获取二叉树的高度
     */
    public static int getBinaryTreeLevelsByDP(Node root) {
        return recurse(root);
    }

    private static int recurse(Node subTreeRoot) {
        if (subTreeRoot == null) {
            return 0;
        }
        // 左树高度
        int leftTreeLevels = recurse(subTreeRoot.left);
        // 右树高度
        int rightTreeLevels = recurse(subTreeRoot.right);
        return Math.max(leftTreeLevels, rightTreeLevels) + 1;
    }

    /**
     * 广度优先遍历(BFS)方式获取二叉树的高度
     */
    public static int getBinaryTreeLevelsByBFS(Node root) {
        if (root == null) {
            return 0;
        }

        int totalLevels = 1;
        int currentLevel = 1;

        HashMap<Node, Integer> nodeLevelMap = new HashMap<>(16);
        nodeLevelMap.put(root, 1);

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            int nodeLevel = nodeLevelMap.get(node);

            if (nodeLevel != currentLevel) {
                currentLevel++;
                totalLevels++;
            }

            if (node.left != null) {
                queue.add(node.left);
                nodeLevelMap.put(node.left, nodeLevel + 1);
            }
            if (node.right != null) {
                queue.add(node.right);
                nodeLevelMap.put(node.right, nodeLevel + 1);
            }
        }
        return totalLevels;
    }

    private static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
        }
    }

    /* ************************************************************************************************************** */

    private static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    private static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    /* ************************************************************************************************************** */

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            int r1 = getBinaryTreeLevelsByDP(head);
            int r2 = getBinaryTreeLevelsByBFS(head);
            if (r1 != r2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
