package com.lg.datastructure.binarytree;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;

/**
 * 计算二叉树有几层
 *
 * @author Xulg
 * Created in 2020-11-02 19:43
 */
@SuppressWarnings("all")
class CountBinaryTreeLevel {

    /*
     * 计算二叉树有几层
     */

    /**
     * 计算二叉树的层数
     */
    public static int countLevelByLoop(Node root) {
        if (root == null) {
            return 0;
        }
        Queue<Node> queue = new ArrayDeque<>();
        // 队列加入根节点
        queue.add(root);
        // 当前所在的层
        int currentLevel = 1;
        // 层数
        int levelCount = 1;
        // 记录每个节点在哪层，key为节点，value为节点所在的层
        HashMap<Node, Integer> nodeLevelMap = new HashMap<>();
        // 根节点所在的层是1层
        nodeLevelMap.put(root, currentLevel);
        while (!queue.isEmpty()) {
            // 弹出队列中的第一个节点
            Node node = queue.poll();
            // 获取这个节点在哪层啊
            Integer nodeLevel = nodeLevelMap.get(node);

            // 如果节点所在的层和当前层一样，啥也不做
            if (nodeLevel == currentLevel) {
                // 节点所在层和当前层是一样的
            }
            // 节点在的层和当前层不一样了，说明节点是下一层的了，当前层要加1了
            else {
                // 下一层了
                currentLevel++;
                // 层数加1
                levelCount++;
            }
            System.out.println(node.value + "在第" + nodeLevel + "层");

            if (node.left != null) {
                queue.add(node.left);
                // 子节点在哪层 = 父节点层 + 1;
                nodeLevelMap.put(node.left, nodeLevel + 1);
            }
            if (node.right != null) {
                queue.add(node.right);
                // 子节点在哪层 = 父节点层 + 1;
                nodeLevelMap.put(node.right, nodeLevel + 1);
            }
        }
        return levelCount;
    }

    /**
     * 计算二叉树的层数
     */
    public static int countLevelByRecurse(Node root) {
        if (root == null) {
            return 0;
        }
        // 计算左子树的层数
        int leftLevelCount = countLevelByRecurse(root.left);
        // 计算右子树的层数
        int rightLevelCount = countLevelByRecurse(root.right);
        // 取左右子树层数最大值
        int maxLevelCount = Math.max(leftLevelCount, rightLevelCount);
        // 当前树的层数 = 子树层数 + 1
        return maxLevelCount + 1;
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
        Node tree = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        Node node7 = new Node(7);
        tree.left = node2;
        tree.right = node3;
        node2.left = node4;
        node2.right = node5;
        node3.left = node6;
        node3.right = node7;
        System.out.println(countLevelByRecurse(tree));
    }
}
