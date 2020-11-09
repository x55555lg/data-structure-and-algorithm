package com.lg.datastructure.binarytree.binarytreerecurse.practice;

/**
 * 二叉树的递归套路练习
 *
 * @author Xulg
 * Created in 2020-11-05 15:29
 */
class CheckIsFullBinaryTree {

    /*
     * 给定一颗二叉树的头节点head，返回这颗二叉树是不是满二叉树？
     *-----------------------------------------------------------
     * 思路：
     *      对于任意的头节点X
     *      1.找左子树要到高度和节点数
     *      2.找右子树要到高度和节点数
     *      3.根据左右子树的信息计算当前节点的树的高度和节点数
     *      4.拿到头节点X的树的高度和节点数量后，判断是否满足公式：
     *          2^H - 1 = N
     */

    /**
     * 判断是不是满二叉树
     */
    public static boolean isFullBalanceTree(Node root) {
        Info treeInfo = doCheckIsFullBalanceTree(root);
        // 是否满二叉树：2^H = N
        return (int) (Math.pow(2, treeInfo.treeHigh) - 1) == treeInfo.treeSize;
    }

    private static Info doCheckIsFullBalanceTree(Node node) {
        if (node == null) {
            return new Info(0, 0);
        }
        // 左子树的信息
        Info leftInfo = doCheckIsFullBalanceTree(node.left);
        // 右子树的信息
        Info rightInfo = doCheckIsFullBalanceTree(node.right);
        // 当前节点的树的高度
        int treeHigh = Math.max(leftInfo.treeHigh, rightInfo.treeHigh) + 1;
        // 当前节点的树的节点数量
        int treeSize = leftInfo.treeSize + rightInfo.treeSize + 1;
        return new Info(treeHigh, treeSize);
    }

    /* ************************************************************************************************************** */

    /* 对数器 */

    public static boolean isFull1(Node head) {
        if (head == null) {
            return true;
        }
        int height = h(head);
        int nodes = n(head);
        return (1 << height) - 1 == nodes;
    }

    private static int h(Node head) {
        if (head == null) {
            return 0;
        }
        return Math.max(h(head.left), h(head.right)) + 1;
    }

    private static int n(Node head) {
        if (head == null) {
            return 0;
        }
        return n(head.left) + n(head.right) + 1;
    }

    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    /* ************************************************************************************************************** */

    private static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
        }
    }

    private static class Info {
        // 二叉树的高度
        int treeHigh;
        // 二叉树的节点数量
        int treeSize;

        Info(int treeHigh, int treeSize) {
            this.treeHigh = treeHigh;
            this.treeSize = treeSize;
        }
    }

    /* ************************************************************************************************************** */

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (isFull1(head) != isFullBalanceTree(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
