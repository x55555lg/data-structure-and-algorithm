package com.lg.datastructure.binarytree.binarytreerecurse.practice;

import javax.annotation.Nullable;
import java.util.ArrayList;

/**
 * 获取二叉树中的最大搜索二叉树的头(根)节点
 *
 * @author Xulg
 * Created in 2020-11-03 11:57
 */
class GetMaxBinarySearchSubTreeHeader {

    /*
     * 给定一个二叉树的头节点head。返回这颗二叉树中最大的二叉搜索子树的头节点
     *------------------------------------------------------------------------
     * 思路：
     *      对于任意节点X
     *      1.先计算左子树的信息，右子树的信息
     *      2.通过左右子树的信息，判断以X节点为头节点的树是否是二叉搜索树
     *      3.如果以X节点为头节点的树不是二叉搜索树，也就是最大二叉搜索子树和X节点无关，
     *        那么最大二叉搜索子树 = max(左子树最大二叉搜索子树，右子树最大二叉搜索子树);
     *      4.如果以X节点为头节点的树是二叉搜索树，也就是最大二叉搜索子树和X节点有关，那
     *        么最大二叉搜索子树 = max(左子树最大二叉搜索子树，右子树最大二叉搜索子树) + X节点;
     */

    /**
     * 获取二叉树中的最大搜索二叉树的头节点
     */
    @SuppressWarnings("ConstantConditions")
    public static Node getMaxBinarySearchSubTree(Node root) {
        if (root == null) {
            return null;
        }
        return doGetMaxBinarySearchSubTree(root).maxSubBinarySearchTreeHeader;
    }

    @Nullable
    private static Info doGetMaxBinarySearchSubTree(Node node) {
        if (node == null) {
            return null;
        }
        // 获取左子树的信息
        Info leftInfo = doGetMaxBinarySearchSubTree(node.left);
        // 获取右子树的信息
        Info rightInfo = doGetMaxBinarySearchSubTree(node.right);

        /*当前这颗树的信息是什么样的呢*/

        // 当前树的最大值 = max(左子树的最大值, 右子树的最大值);
        int maxValue = maxValue(node, leftInfo, rightInfo);
        // 当前树的最大值 = max(左子树的最小值, 右子树的最小值);
        int minValue = minValue(node, leftInfo, rightInfo);
        // 当前树是否是二叉搜索树 = 当前节点value > 左子树最大值 && 当前节点value < 右子树最小值
        boolean isBinarySearchTree = isBinarySearchTree(node, leftInfo, rightInfo);

        // 当前树的最大二叉搜索子树的头节点
        Node header;
        // 当前树的最大二叉搜索子树
        int treeSize;
        if (isBinarySearchTree) {
            // 当前树也是二叉搜索树，也就是最大二叉搜索子树和当前节点X有关，
            // 即以X节点为头节点的整颗树都是二叉搜索树啊
            // 当前树最大二叉搜索子树节点数量 = 左子树节点数 + 右子树节点数 + 当前节点1;
            treeSize = (leftInfo == null ? 0 : leftInfo.maxSubBinarySearchTreeSize)
                    + (rightInfo == null ? 0 : rightInfo.maxSubBinarySearchTreeSize)
                    + 1;

            // X节点就是最大二叉搜索树的头节点
            header = node;
        } else {
            // 当前树不是二叉搜索树，也就是最大二叉搜索子树和当前节点X无关，
            // 所有以X节点为头节点的树中，最大二叉搜索子树在左右子树中，
            // 当前树最大二叉搜索子树节点数量 = max(左子树节点数, 右子树节点数);
            treeSize = Math.max(leftInfo == null ? 0 : leftInfo.maxSubBinarySearchTreeSize,
                    rightInfo == null ? 0 : rightInfo.maxSubBinarySearchTreeSize);

            // header为节点数大的那个子树的header
            if (leftInfo != null && leftInfo.maxSubBinarySearchTreeSize == treeSize) {
                header = leftInfo.maxSubBinarySearchTreeHeader;
            } else if (rightInfo != null && rightInfo.maxSubBinarySearchTreeSize == treeSize) {
                header = rightInfo.maxSubBinarySearchTreeHeader;
            } else {
                header = null;
            }
        }
        return new Info(header, treeSize, isBinarySearchTree, maxValue, minValue);
    }

    private static int maxValue(Node node, Info leftInfo, Info rightInfo) {
        int maxValue = node.value;
        if (leftInfo != null) {
            maxValue = Math.max(maxValue, leftInfo.maxVal);
        }
        if (rightInfo != null) {
            maxValue = Math.max(maxValue, rightInfo.maxVal);
        }
        return maxValue;
    }

    private static int minValue(Node node, Info leftInfo, Info rightInfo) {
        int minValue = node.value;
        if (leftInfo != null) {
            minValue = Math.min(minValue, leftInfo.minVal);
        }
        if (rightInfo != null) {
            minValue = Math.min(minValue, rightInfo.minVal);
        }
        return minValue;
    }

    private static boolean isBinarySearchTree(Node node, Info leftInfo, Info rightInfo) {
        return (leftInfo == null || leftInfo.isSearchBinaryTree)
                && (rightInfo == null || rightInfo.isSearchBinaryTree)
                && (leftInfo == null || node.value > leftInfo.maxVal)
                && (rightInfo == null || node.value < rightInfo.minVal);
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

    private static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
        }
    }

    private static class Info {
        // 最大二叉搜索子树的头节点
        Node maxSubBinarySearchTreeHeader;
        // 最大二叉搜索子树的节点个数
        int maxSubBinarySearchTreeSize;
        // 是否是搜索二叉树
        boolean isSearchBinaryTree;
        // 二叉树中的最大值
        int maxVal;
        // 二叉树中的最小值
        int minVal;

        Info(Node maxSubBinarySearchTreeHeader, int maxSubBinarySearchTreeSize,
             boolean isSearchBinaryTree, int maxVal, int minVal) {
            this.maxSubBinarySearchTreeHeader = maxSubBinarySearchTreeHeader;
            this.maxSubBinarySearchTreeSize = maxSubBinarySearchTreeSize;
            this.isSearchBinaryTree = isSearchBinaryTree;
            this.maxVal = maxVal;
            this.minVal = minVal;
        }
    }

    /* ************************************************************************************************************** */

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (getMaxBinarySearchSubTree(head) != maxSubBSTHead1(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
