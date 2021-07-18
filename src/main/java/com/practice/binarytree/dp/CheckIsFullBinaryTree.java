package com.practice.binarytree.dp;

/**
 * @author Xulg
 * Description: 验证是否是满二叉树
 * Created in 2021-05-17 23:43
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

    private static class Solution {
        public static boolean isFullBinarySearchTree(Node root) {
            Data data = recurse(root);
            // 2^H - 1 = N
            //return Math.pow(2, data.height) - 1 == data.size;
            return ((1 << data.height) - 1) == data.size;
        }

        private static Data recurse(Node root) {
            if (root == null) {
                return new Data(0, 0);
            }
            Data leftData = recurse(root.left);
            Data rightData = recurse(root.right);
            int height = Math.max(leftData.height, rightData.height) + 1;
            int size = leftData.size + rightData.size + 1;
            return new Data(height, size);
        }

        private static class Data {
            // 树高度
            int height;
            // 树节点数
            int size;

            Data(int height, int size) {
                this.height = height;
                this.size = size;
            }
        }
    }

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

    private static class Node {
        int val;
        Node left, right;

        Node(int val) {
            this.val = val;
        }
    }

    /* ************************************************************************************************************** */

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
            if (Compare.isFullBinarySearchTree(head) != Solution.isFullBinarySearchTree(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}
