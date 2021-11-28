package com.practice2.binarytree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * @author Xulg
 * @since 2021-10-15 10:51
 */
class BinaryTreeMaxLevelWidth {

    /*
     * 二叉树的最大层宽
     */

    public static int calcMaxLevelWidth(Node root) {
        if (root == null) {
            return 0;
        }

        Map<Node, Integer> levelMap = new HashMap<>(16);
        levelMap.put(root, 1);

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        int currentLevel = 1;//当前所在层
        int currentLevelWidth = 0;// 当前所在层的宽度
        int maxWidth = 0;// 最大层宽
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            int level = levelMap.get(node);
            if (level == currentLevel) {
                // 当前层宽度加1
                currentLevelWidth++;
            } else {
                // 上新的一层了，先求最大宽度
                maxWidth = Math.max(maxWidth, currentLevelWidth);
                // 指向下一层，下一层的宽度为1
                currentLevel++;
                currentLevelWidth = 1;
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
        // 最后一层还没有参与比较
        maxWidth = Math.max(maxWidth, currentLevelWidth);
        return maxWidth;
    }

    private static class Node {
        private int value;
        private Node left, right;

        Node(int value) {
            this.value = value;
        }

        Node(int value, Node left, Node right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    public static void main(String[] args) {
        int maxLevel = 10;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (calcMaxLevelWidth(head) != maxWidthNoMap(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }


    // for test
    static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    static int maxWidthNoMap(Node head) {
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
