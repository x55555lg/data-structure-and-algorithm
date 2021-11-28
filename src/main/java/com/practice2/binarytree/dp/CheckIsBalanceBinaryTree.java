package com.practice2.binarytree.dp;

/**
 * @author Xulg
 * @since 2021-10-15 11:26
 * Description: 验证是否是平衡二叉树
 */
class CheckIsBalanceBinaryTree {

    /*
     * 左子树是平衡的
     * 右子树是平衡的
     * 左右子树的高度相差不超过1
     */

    private static class DP {
        public static boolean isBalanceBinaryTree(Node root) {
            return recurse(root).isBBT;
        }

        private static Data recurse(Node root) {
            if (root == null) {
                return new Data(true, 0);
            }
            Data leftData = recurse(root.left);
            Data rightData = recurse(root.right);
            // 当前树是否是平衡二叉树 = 左子树是平衡二叉树 && 右子树是平衡二叉树 && 左右子树高度小于1
            boolean isBBT = leftData.isBBT && rightData.isBBT
                    && (Math.abs(leftData.treeHigh - rightData.treeHigh) <= 1);
            int treeHigh = Math.max(leftData.treeHigh, rightData.treeHigh) + 1;
            return new Data(isBBT, treeHigh);
        }

        private static class Data {
            private boolean isBBT;
            private int treeHigh;

            Data(boolean isBBT, int treeHigh) {
                this.isBBT = isBBT;
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
            if (DP.isBalanceBinaryTree(head) != Compare.isBalancedBinaryTree(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
