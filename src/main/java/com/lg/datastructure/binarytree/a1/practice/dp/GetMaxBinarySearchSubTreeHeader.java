package com.lg.datastructure.binarytree.a1.practice.dp;

import java.util.ArrayList;

/**
 * @author Xulg
 * Created in 2021-01-25 18:00
 */
class GetMaxBinarySearchSubTreeHeader {

    /*
     * 给定一个二叉树的头节点head。返回这颗二叉树中最大的二叉搜索子树的头节点
     * 什么是二叉搜索树
     *      对于任意节点X都要满足：
     *          X节点的值都要大于其左子树中任意节点的值
     *          X节点的值都要小于右子树中任意节点的值
     */

    public static Node getBiggestBinarySearchTree(Node root) {
        if (root == null) {
            return null;
        }
        return recurse(root).biggestBinarySearchTreeHeader;
    }

    private static Data recurse(Node subTreeRoot) {
        if (subTreeRoot == null) {
            // 树最大值为int类型的最小值，树最小值为int类型的最大值，是二叉搜索树，最大二叉搜索树头节点为null
            return null;
        }

        Data leftData = recurse(subTreeRoot.left);
        Data rightData = recurse(subTreeRoot.right);

        int leftTreeMaxValue = leftData == null ? Integer.MIN_VALUE : leftData.maxValue;
        int rightTreeMaxValue = rightData == null ? Integer.MIN_VALUE : rightData.maxValue;
        int leftTreeMinValue = leftData == null ? Integer.MAX_VALUE : leftData.minValue;
        int rightTreeMinValue = rightData == null ? Integer.MAX_VALUE : rightData.minValue;
        boolean leftTreeIsBinarySearchTree = leftData == null || leftData.isBinarySearchTree;
        boolean rightTreeIsBinarySearchTree = rightData == null || rightData.isBinarySearchTree;
        Node leftTreeBiggestHeader = leftData == null ? null : leftData.biggestBinarySearchTreeHeader;
        Node rightTreeBiggestHeader = rightData == null ? null : rightData.biggestBinarySearchTreeHeader;
        int leftTreeBiggestSize = leftData == null ? 0 : leftData.biggestBinarySearchTreeSize;
        int rightTreeBiggestSize = rightData == null ? 0 : rightData.biggestBinarySearchTreeSize;

        // 当前树的最小值
        int maxValue = Math.max(subTreeRoot.value, Math.max(leftTreeMaxValue, rightTreeMaxValue));
        // 当前树的最大值
        int minValue = Math.min(subTreeRoot.value, Math.min(leftTreeMinValue, rightTreeMinValue));
        // 当前树是否是搜索二叉树
        boolean isSearchTree = leftTreeIsBinarySearchTree && rightTreeIsBinarySearchTree
                && (subTreeRoot.value > leftTreeMaxValue && subTreeRoot.value < rightTreeMinValue);

        // 当前树的最大二叉搜索树的根节点
        Node biggestHeader;
        // 当前树的最大二叉搜索树的节点数量
        int biggestBinarySearchTreeSize;
        if (isSearchTree) {
            // 当前树都是搜索树了，那么当前树的root节点就是最大二叉树的根节点了
            biggestHeader = subTreeRoot;
            // 左右子树也一定都是二叉树，所以最大二叉搜索树的节点数量
            biggestBinarySearchTreeSize = leftTreeBiggestSize + rightTreeBiggestSize + 1;
        } else {
            // 当前树不是二叉搜索树，也就是最大二叉搜索子树和当前节点X无关，
            // 所有以X节点为头节点的树中，最大二叉搜索子树在左右子树中，
            // 当前树最大二叉搜索子树节点数量 = max(左子树节点数, 右子树节点数);
            biggestBinarySearchTreeSize = Math.max(leftTreeBiggestSize, rightTreeBiggestSize);
            // header为节点数大的那个子树的header
            if (leftTreeBiggestSize == biggestBinarySearchTreeSize) {
                biggestHeader = leftTreeBiggestHeader;
            } else {
                biggestHeader = rightTreeBiggestHeader;
            }
        }

        return new Data(maxValue, minValue, isSearchTree, biggestHeader, biggestBinarySearchTreeSize);
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
        // 树中的最大值
        int maxValue;
        // 树中的最小值
        int minValue;
        // 是否是二叉搜索树
        boolean isBinarySearchTree;
        // 最大二叉搜索树的根节点
        Node biggestBinarySearchTreeHeader;
        // 最大二叉搜索树的节点数量
        int biggestBinarySearchTreeSize;

        Data(int maxValue, int minValue, boolean isBinarySearchTree,
             Node biggestBinarySearchTreeHeader, int biggestBinarySearchTreeSize) {
            this.maxValue = maxValue;
            this.minValue = minValue;
            this.isBinarySearchTree = isBinarySearchTree;
            this.biggestBinarySearchTreeHeader = biggestBinarySearchTreeHeader;
            this.biggestBinarySearchTreeSize = biggestBinarySearchTreeSize;
        }
    }

    /* ************************************************************************************************************** */

    /* 对数器 */

    public static Node maxSubBSTHead1(Node head) {
        if (head == null) {
            return null;
        }
        if (getBSTSize(head) != 0) {
            return head;
        }
        Node leftAns = maxSubBSTHead1(head.left);
        Node rightAns = maxSubBSTHead1(head.right);
        return getBSTSize(leftAns) >= getBSTSize(rightAns) ? leftAns : rightAns;
    }

    public static int getBSTSize(Node head) {
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

    public static void in(Node head, ArrayList<Node> arr) {
        if (head == null) {
            return;
        }
        in(head.left, arr);
        arr.add(head);
        in(head.right, arr);
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
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (getBiggestBinarySearchTree(head) != maxSubBSTHead1(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");

        Node root = new Node(71);
        Node node1 = new Node(95);
        Node node2 = new Node(1);
        Node node3 = new Node(97);
        Node node4 = new Node(51);
        Node node5 = new Node(60);
        Node node6 = new Node(49);
        Node node7 = new Node(49);
        root.left = node1;
        root.right = null;
        node1.left = node2;
        node1.right = node3;
        node2.left = node4;
        node2.right = node5;
        node3.left = node6;
        node3.right = node7;

        Node biggestHeader = getBiggestBinarySearchTree(root);
        System.out.println(biggestHeader == null ? null : biggestHeader.value);
        Node maxSubBSTHead1 = maxSubBSTHead1(root);
        System.out.println(maxSubBSTHead1 == null ? null : maxSubBSTHead1.value);

        Node biggestBinarySearchTreeHeader = recurse(root).biggestBinarySearchTreeHeader;
        System.out.println(biggestBinarySearchTreeHeader == null ? null : biggestBinarySearchTreeHeader.value);
    }

}
