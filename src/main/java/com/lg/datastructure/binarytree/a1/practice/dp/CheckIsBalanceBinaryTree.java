package com.lg.datastructure.binarytree.a1.practice.dp;

/**
 * @author Xulg
 * Created in 2021-01-25 17:57
 */
class CheckIsBalanceBinaryTree {

    /*
     * 给定一颗二叉树的头节点head，返回这颗二叉树是不是平衡二叉树？
     *  是否平衡：3个条件都满足，则给定树就是平衡二叉树
     *              1.判断左子树是否平衡
     *              2.判断右子树是否平衡
     *              3.左右子树的高度相差不超过1
     */

    public static boolean isBalanceBinaryTree(Node root) {
        return recurse(root).isBalance;
    }

    private static Data recurse(Node subTreeRoot) {
        if (subTreeRoot == null) {
            // 高度为0，是平衡的
            return new Data(0, true);
        }

        // 左右子树信息
        Data leftSubTreeData = recurse(subTreeRoot.left);
        Data rightSubTreeData = recurse(subTreeRoot.right);

        // 当前树高
        int high = Math.max(leftSubTreeData.high, rightSubTreeData.high) + 1;
        // 当前树是否平衡
        boolean isBalance = leftSubTreeData.isBalance && rightSubTreeData.isBalance
                && (Math.abs(leftSubTreeData.high - rightSubTreeData.high) <= 1);

        // 当前树的信息
        return new Data(high, isBalance);
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
        int high;
        // 树是否平衡
        boolean isBalance;

        public Data(int high, boolean isBalance) {
            this.high = high;
            this.isBalance = isBalance;
        }
    }

    /* ************************************************************************************************************** */

    /* 对数器 start */

    public static boolean isBalanced(Node head) {
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

    /* ************************************************************************************************************** */

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (isBalanceBinaryTree(head) != isBalanced(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
