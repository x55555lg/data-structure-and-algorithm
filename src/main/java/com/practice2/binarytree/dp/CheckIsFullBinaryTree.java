package com.practice2.binarytree.dp;

/**
 * @author Xulg
 * @since 2021-10-17 19:11
 * Description: 验证是否是满二叉树
 */
class CheckIsFullBinaryTree {

    private static class DP {
        public static boolean isFullBinaryTree(Node root) {
            // 2^H - 1 == N
            Data data = recurse(root);
            return Math.pow(2, data.treeHigh) - 1 == data.treeSize;
        }

        private static Data recurse(Node treeNode) {
            if (treeNode == null) {
                return new Data(0, 0);
            }
            Data leftData = recurse(treeNode.left);
            Data rightData = recurse(treeNode.right);
            int treeSize = leftData.treeSize + rightData.treeSize + 1;
            int treeHigh = Math.min(leftData.treeHigh, rightData.treeHigh) + 1;
            return new Data(treeSize, treeHigh);
        }

        private static class Data {
            private int treeSize;
            private int treeHigh;

            Data(int treeSize, int treeHigh) {
                this.treeSize = treeSize;
                this.treeHigh = treeHigh;
            }
        }
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

    /* ************************************************************************************************************** */

    private static class Compare {
        public static boolean isFullBinarySearchTree(Node head) {
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
    }

    /* 对数器 */

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
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (Compare.isFullBinarySearchTree(head) != DP.isFullBinaryTree(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}
