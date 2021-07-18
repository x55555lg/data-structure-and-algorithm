package com.practice.binarytree.dp;

/**
 * @author Xulg
 * Description: 检测是否是平衡二叉树 leetcode_110
 * Created in 2021-05-18 9:26
 */
class CheckIsBalanceBinaryTree {

    /*
     * 给定一颗二叉树的头节点head，返回这颗二叉树是不是平衡二叉树？
     *-------------------------------------------------------------
     * 思路：
     *  给定二叉树的头节点head：
     *      判断左子树是否平衡？
     *      判断右子树是否平衡？
     *      左右子树的高度差是否小于等于1?
     *  3个条件都满足，则给定树就是平衡二叉树
     *  递归的判断树的左右子树
     */

    private static class Solution {
        public static boolean isBalancedBinaryTree(Node root) {
            return recurse(root).isBalanced;
        }

        private static Data recurse(Node node) {
            if (node == null) {
                return new Data(0, true);
            }
            Data leftData = recurse(node.left);
            Data rightData = recurse(node.right);
            int height = Math.max(leftData.height, rightData.height) + 1;
            boolean isBalanced = leftData.isBalanced && rightData.isBalanced
                    && (Math.abs(leftData.height - rightData.height) <= 1);
            return new Data(height, isBalanced);
        }

        private static class Data {
            int height;
            boolean isBalanced;

            Data(int height, boolean isBalanced) {
                this.height = height;
                this.isBalanced = isBalanced;
            }
        }
    }

    private static class Compare {

        public static boolean isBalancedBinaryTree(Node head) {
            boolean[] ans = new boolean[1];
            ans[0] = true;
            process1(head, ans);
            return ans[0];
        }

        private static int process1(Node head, boolean[] ans) {
            if (!ans[0] || head == null) {
                return -1;
            }
            int leftHeight = process1(head.left, ans);
            int rightHeight = process1(head.right, ans);
            if (Math.abs(leftHeight - rightHeight) > 1) {
                ans[0] = false;
            }
            return Math.max(leftHeight, rightHeight) + 1;
        }
    }

    private static class Node {
        int val;
        Node left, right;

        Node(int val) {
            this.val = val;
        }
    }

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

    /* 对数器 end */

    public static void main(String[] args) {
        int maxLevel = 50;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (Solution.isBalancedBinaryTree(head) != Compare.isBalancedBinaryTree(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
