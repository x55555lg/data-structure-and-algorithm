package com.lg.datastructure.binarytree.a1.practice;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 二叉树的最大层宽
 *
 * @author Xulg
 * Created in 2021-01-25 16:51
 */
@SuppressWarnings({"MapOrSetKeyShouldOverrideHashCodeEquals"})
class BinaryTreeMaxLevelWidth {

    /**
     * 获取二叉树的最大层宽
     * 即二叉树节点最多的那层是多少个节点
     */
    public static int getMaxLevelWidth(Node root) {
        if (root == null) {
            return 0;
        }

        // 节点在哪层，root节点在第1层
        HashMap<Node, Integer> nodeLevelMap = new HashMap<>(16);
        nodeLevelMap.put(root, 1);

        // 最大层宽
        int maxWidth = 0;
        // 当前在遍历的层
        int currentLevel = 1;
        // 当前在遍历的层的宽度
        int currentLevelWidth = 0;

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            // 看节点在哪一层
            int nodeLevel = nodeLevelMap.get(node);

            if (nodeLevel == currentLevel) {
                // 当前遍历的层就是节点所在层，当前层的宽度加1
                currentLevelWidth++;
            } else {
                // 比较最大层宽
                maxWidth = Math.max(maxWidth, currentLevelWidth);
                // 开始统计新的一层了
                currentLevel++;
                // 新的一层的宽度重置
                currentLevelWidth = 1;
            }
            if (node.left != null) {
                queue.add(node.left);
                // 记录子节点在哪一层 父节点的下一层
                nodeLevelMap.put(node.left, nodeLevel + 1);
            }
            if (node.right != null) {
                queue.add(node.right);
                // 记录子节点在哪一层 父节点的下一层
                nodeLevelMap.put(node.right, nodeLevel + 1);
            }
        }

        // 上面循环结束后，还有最后一层没参与比较
        maxWidth = Math.max(maxWidth, currentLevelWidth);

        return maxWidth;
    }

    /* ****************************************************************************************************************/

    private static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
        }
    }

    /* ****************************************************************************************************************/

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

    // 不需要额外空间
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

    /* ****************************************************************************************************************/

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

}
