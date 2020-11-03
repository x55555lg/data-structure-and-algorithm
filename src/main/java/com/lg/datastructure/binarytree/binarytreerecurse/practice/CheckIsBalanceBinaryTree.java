package com.lg.datastructure.binarytree.binarytreerecurse.practice;

/**
 * 二叉树的递归套路练习
 *
 * @author Xulg
 * Created in 2020-11-02 9:44
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

    /**
     * 判断二叉树是否是平衡二叉树
     */
    public static boolean isBalanceBinaryTree(Node treeRoot) {
        if (treeRoot == null) {
            return true;
        }
        return doCheck(treeRoot).isBalance;
    }

    private static Info doCheck(Node node) {
        if (node == null) {
            return new Info(true, 0);
        }
        Info leftInfo = doCheck(node.left);
        Info rightInfo = doCheck(node.right);
        // 计算当前节点子树是否平衡的
        boolean isBalance = (leftInfo.isBalance && rightInfo.isBalance)
                && (Math.abs(leftInfo.height - rightInfo.height) <= 1);
        // 左右子树高度大的那个 + 1
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        return new Info(isBalance, height);
    }

    private static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
        }
    }

    private static class Info {
        // 是否是平衡二叉树
        boolean isBalance;
        // 树的高度
        int height;

        Info(boolean isBalance, int height) {
            this.isBalance = isBalance;
            this.height = height;
        }
    }

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

    @SuppressWarnings("all")
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
