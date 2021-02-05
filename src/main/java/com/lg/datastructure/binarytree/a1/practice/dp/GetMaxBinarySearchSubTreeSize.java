package com.lg.datastructure.binarytree.a1.practice.dp;

import java.util.ArrayList;

/**
 * @author Xulg
 * Created in 2021-01-25 18:01
 */
class GetMaxBinarySearchSubTreeSize {

    /*
     * 给定一个二叉树的头节点head。返回这颗二叉树中最大的二叉搜索子树的节点数
     * 什么是二叉搜索树
     *      对于任意节点X都要满足：
     *          X节点的值都要大于其左子树中任意节点的值
     *          X节点的值都要小于右子树中任意节点的值
     */

    public static int getBiggestBinarySearchTreeSize(Node root) {
        return recurse(root).biggestSearchTreeSize;
    }

    private static Data recurse(Node subTreeRoot) {
        if (subTreeRoot == null) {
            return new Data(Integer.MIN_VALUE, Integer.MAX_VALUE, true, 0);
        }

        // 左子树信息
        Data leftData = recurse(subTreeRoot.left);
        // 右子树信息
        Data rightData = recurse(subTreeRoot.right);

        // 当前树的最大值 = max(value, max(左子树的最大值, 右子树的最大值));
        int maxValue = Math.max(subTreeRoot.value, Math.max(leftData.maxValue, rightData.maxValue));
        // 当前树的最大值 = max(value, max(左子树的最小值, 右子树的最小值));
        int minValue = Math.min(subTreeRoot.value, Math.min(leftData.minValue, rightData.minValue));
        // 当前树是否是搜索二叉树
        boolean isSearchTree = leftData.isSearchTree && rightData.isSearchTree
                && (subTreeRoot.value > leftData.maxValue && subTreeRoot.value < rightData.minValue);

        int biggestSearchTreeSize;
        if (isSearchTree) {
            // 当前树也是二叉搜索树，也就是最大二叉搜索子树和当前节点X有关，
            // 即以X节点为头节点的整颗树都是二叉搜索树啊
            // 当前树最大二叉搜索子树节点数量 = 左子树节点数 + 右子树节点数 + 当前节点1;
            biggestSearchTreeSize = leftData.biggestSearchTreeSize + rightData.biggestSearchTreeSize + 1;
        } else {
            // 当前树不是二叉搜索树，也就是最大二叉搜索子树和当前节点X无关，
            // 所有以X节点为头节点的树中，最大二叉搜索子树在左右子树中，
            // 当前树最大二叉搜索子树节点数量 = max(左子树节点数, 右子树节点数);
            biggestSearchTreeSize = Math.max(leftData.biggestSearchTreeSize, rightData.biggestSearchTreeSize);
        }

        return new Data(maxValue, minValue, isSearchTree, biggestSearchTreeSize);
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
        int maxValue;
        int minValue;
        boolean isSearchTree;
        int biggestSearchTreeSize;

        Data(int maxValue, int minValue, boolean isSearchTree, int biggestSearchTreeSize) {
            this.maxValue = maxValue;
            this.minValue = minValue;
            this.isSearchTree = isSearchTree;
            this.biggestSearchTreeSize = biggestSearchTreeSize;
        }
    }

    /* ************************************************************************************************************** */

    /* 对数器 */

    public static int maxSubBSTSize(Node head) {
        if (head == null) {
            return 0;
        }
        int h = getBSTSize(head);
        if (h != 0) {
            return h;
        }
        return Math.max(maxSubBSTSize(head.left), maxSubBSTSize(head.right));
    }

    private static int getBSTSize(Node head) {
        if (head == null) {
            return 0;
        }
        ArrayList<Node> arr = new ArrayList<>();
        in(head, arr);
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i).value <= arr.get(i - 1).value) {
                return 0;
            }
        }
        return arr.size();
    }

    private static void in(Node head, ArrayList<Node> arr) {
        if (head == null) {
            return;
        }
        in(head.left, arr);
        arr.add(head);
        in(head.right, arr);
    }

    private static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    @SuppressWarnings("DuplicatedCode")
    private static Node generate(int level, int maxLevel, int maxValue) {
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
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (maxSubBSTSize(head) != getBiggestBinarySearchTreeSize(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
