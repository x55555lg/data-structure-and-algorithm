package com.practice.binarytree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * 计算二叉树的最大层宽
 *
 * @author Xulg
 * Created in 2021-05-08 14:00
 */
@SuppressWarnings("MapOrSetKeyShouldOverrideHashCodeEquals")
class BinaryTreeMaxLevelWidth {

    public static int calcMaxWidth(Node root) {
        if (root == null) {
            return 0;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        Map<Node, Integer> nodeLevelMap = new HashMap<>(16);
        nodeLevelMap.put(root, 1);
        int currentLevel = 1;
        int currentLevelWidth = 0;
        int maxWidth = Integer.MIN_VALUE;
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            int nodeLevel = nodeLevelMap.get(node);
            if (nodeLevel == currentLevel) {
                // 还在这一层，层宽加1
                currentLevelWidth++;
            } else {
                // 和之前的最大层宽比较，取最大层宽
                maxWidth = Math.max(maxWidth, currentLevelWidth);
                // 新的一层了
                currentLevel++;
                // 新的一层了，重新初始化，初始值为1，因为当前这个node得算进去
                currentLevelWidth = 1;
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
        // 最后一层还没比较
        maxWidth = Math.max(maxWidth, currentLevelWidth);
        return maxWidth;
    }

    private static class Node {
        int val;
        Node left, right;

        Node(int val) {
            this.val = val;
        }
    }

    public static void main(String[] args) {
        int maxLevel = 10;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (calcMaxWidth(head) != maxWidthNoMap(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

    // for test
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static int maxWidthNoMap(Node head) {
        if (head == null) {
            return 0;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        Node curEnd = head; // 当前层，最右节点是谁
        Node nextEnd = null; // 下一层，最右节点是谁
        int max = 0;
        int curLevelNodes = 0; // 当前层的节点数
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            if (cur.left != null) {
                queue.add(cur.left);
                nextEnd = cur.left;
            }
            if (cur.right != null) {
                queue.add(cur.right);
                nextEnd = cur.right;
            }
            curLevelNodes++;
            if (cur == curEnd) {
                max = Math.max(max, curLevelNodes);
                curLevelNodes = 0;
                curEnd = nextEnd;
            }
        }
        return max;
    }

}
