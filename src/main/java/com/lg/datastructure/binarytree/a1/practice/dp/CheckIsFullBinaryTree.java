package com.lg.datastructure.binarytree.a1.practice.dp;

/**
 * @author Xulg
 * Created in 2021-01-25 17:58
 */
class CheckIsFullBinaryTree {

    /*
     * 给定一颗二叉树的头节点head，返回这颗二叉树是不是满二叉树？
     */

    public static boolean isFullBinaryTree(Node root) {
        if (root == null) {
            return true;
        }
        return recurse(root).isFull;
    }

    private static Data recurse(Node subTreeRoot) {
        if (subTreeRoot == null) {
            // 高度为0，节点数为0，是满二叉树
            return new Data(0, 0, true);
        }

        // 子树信息
        Data leftSubTreeData = recurse(subTreeRoot.left);
        Data rightSubTreeData = recurse(subTreeRoot.right);

        // 当前树的高度
        int currentTreeHigh = Math.max(leftSubTreeData.high, rightSubTreeData.high) + 1;
        // 当前树的节点数
        int currentTreeSize = leftSubTreeData.size + rightSubTreeData.size + 1;
        // 当前树是否是满二叉树 是否满足：2^H - 1 == N
        boolean currentTreeIsFull = ((int) Math.pow(2, currentTreeHigh) - 1) == currentTreeSize;

        // 当前树的信息
        return new Data(currentTreeHigh, currentTreeSize, currentTreeIsFull);
    }

    private static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
        }
    }

    private static class Data {
        // 树的高度
        private final int high;
        // 树的节点数
        private final int size;
        // 是否是满二叉树
        private final boolean isFull;

        Data(int high, int size, boolean isFull) {
            this.high = high;
            this.size = size;
            this.isFull = isFull;
        }
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

    public static void main(String[] args) {
        System.out.println((int)Math.pow(2, 2) - 1);

        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (isFull1(head) != isFullBinaryTree(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
