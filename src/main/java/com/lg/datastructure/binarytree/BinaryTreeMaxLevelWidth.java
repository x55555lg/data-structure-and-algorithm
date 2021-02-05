package com.lg.datastructure.binarytree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 二叉树的最大层宽
 *
 * @author Xulg
 * Created in 2020-10-30 17:34
 */
class BinaryTreeMaxLevelWidth {

    /*
     * 求二叉树的最宽的层有多少个节点
     */

    /**
     * 求二叉树的最宽的层有多少个节点
     */
    @SuppressWarnings("MapOrSetKeyShouldOverrideHashCodeEquals")
    public static int getMaxLevelWidth(Node root) {
        if (root == null) {
            return 0;
        }
        // 创建队列，加入头节点啊
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        // 记录每个节点在哪层，key为节点，value为节点所在的层
        HashMap<Node, Integer> nodeLevelIndexMap = new HashMap<>();
        // 根节点所在的层是第1层
        nodeLevelIndexMap.put(root, 1);

        // 当前在统计哪层
        int currentLevel = 1;
        // 当前统计层的宽度
        int currentLevelWidth = 0;
        // 最大层宽
        int maxLevelWidth = 0;

        while (!queue.isEmpty()) {
            // 弹出队列的第一个节点
            Node node = queue.poll();
            // 节点在哪层啊
            Integer nodeLevel = nodeLevelIndexMap.get(node);
            // 如果节点所在层和当前层一样，那么这层的层宽加1，否则就是下一层了
            if (nodeLevel == currentLevel) {
                currentLevelWidth++;
            } else {
                // 当前层宽度和之前的最大值比较，谁大留谁
                maxLevelWidth = Math.max(maxLevelWidth, currentLevelWidth);
                // 下一层了
                currentLevel++;
                // 新的一层了啊，新层的层宽需要初始化啊
                currentLevelWidth = 1;
            }
            if (node.left != null) {
                queue.add(node.left);
                nodeLevelIndexMap.put(node.left, nodeLevel + 1);
            }
            if (node.right != null) {
                queue.add(node.right);
                nodeLevelIndexMap.put(node.right, nodeLevel + 1);
            }
        }
        // 最后一层的层宽还没参与过比较，最大值和最后一层层宽谁大
        maxLevelWidth = Math.max(maxLevelWidth, currentLevelWidth);
        return maxLevelWidth;
    }

    private static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
        }
    }

    public static void main(String[] args) {
        int maxLevel = 10;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (getMaxLevelWidth(head) != maxWidthNoMap(head)) {
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
