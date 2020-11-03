package com.lg.datastructure.binarytree.binarytreerecurse.practice;

import javax.annotation.Nullable;
import java.util.ArrayList;

/**
 * 获取二叉树中的最大搜索二叉树的节点数
 *
 * @author Xulg
 * Created in 2020-11-03 11:57
 */
class GetMaxBinarySearchSubTreeSize {

    /*
     * 给定一个二叉树的头节点head。返回这颗二叉树中最大的二叉搜索子树的节点数
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
    public static int getMaxBinarySearchSubTree(Node root) {
        if (root == null) {
            return 0;
        }
        //noinspection ConstantConditions
        return doGetMaxBinarySearchSubTree(root).maxSubBinarySearchTreeSize;
    }

    @Nullable
    private static Info doGetMaxBinarySearchSubTree(Node node) {
        if (node == null) {
            return null;
        }

        // 左子树信息
        Info leftInfo = doGetMaxBinarySearchSubTree(node.left);
        // 右子树信息
        Info rightInfo = doGetMaxBinarySearchSubTree(node.right);

        /*当前这颗树的信息是什么样的呢*/

        // 当前树的最大值 = max(左子树的最大值, 右子树的最大值);
        int maxVal = maxValue(node, leftInfo, rightInfo);
        // 当前树的最大值 = max(左子树的最小值, 右子树的最小值);
        int minVal = minValue(node, leftInfo, rightInfo);
        // 当前树是否是二叉搜索树 = 当前节点value > 左子树最大值 && 当前节点value < 右子树最小值
        boolean isSearchBinaryTree = isSearchBinaryTree(node, leftInfo, rightInfo);

        // 当前树的最大二叉搜索子树
        int treeSize;
        if (isSearchBinaryTree) {
            // 当前树也是二叉搜索树，也就是最大二叉搜索子树和当前节点X有关，
            // 即以X节点为头节点的整颗树都是二叉搜索树啊
            // 当前树最大二叉搜索子树节点数量 = 左子树节点数 + 右子树节点数 + 当前节点1;
            treeSize = (leftInfo == null ? 0 : leftInfo.maxSubBinarySearchTreeSize)
                    + (rightInfo == null ? 0 : rightInfo.maxSubBinarySearchTreeSize)
                    + 1;
        } else {
            // 当前树不是二叉搜索树，也就是最大二叉搜索子树和当前节点X无关，
            // 所有以X节点为头节点的树中，最大二叉搜索子树在左右子树中，
            // 当前树最大二叉搜索子树节点数量 = max(左子树节点数, 右子树节点数);
            treeSize = Math.max(leftInfo == null ? 0 : leftInfo.maxSubBinarySearchTreeSize,
                    rightInfo == null ? 0 : rightInfo.maxSubBinarySearchTreeSize);
        }
        // 当前树的信息啊
        return new Info(treeSize, isSearchBinaryTree, maxVal, minVal);
    }

    private static boolean isSearchBinaryTree(Node node, Info leftInfo, Info rightInfo) {
        /*
         * 当前树是否是二叉搜索树 = 左子树是二叉搜索子树 && 右子树是二叉搜索子树
         *                       && 当前节点value > 左子树最大值
         *                       && 当前节点value < 右子树最小值
         */
        return (leftInfo == null || leftInfo.isSearchBinaryTree)
                && (rightInfo == null || rightInfo.isSearchBinaryTree)
                && (leftInfo == null || node.value > leftInfo.maxVal)
                && (rightInfo == null || node.value < rightInfo.minVal);
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

    private static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
        }
    }

    private static class Info {
        // 最大二叉搜索子树的节点个数
        int maxSubBinarySearchTreeSize;
        // 是否是搜索二叉树
        boolean isSearchBinaryTree;
        // 二叉树中的最大值
        int maxVal;
        // 二叉树中的最小值
        int minVal;

        public Info(int maxSubBinarySearchTreeSize,
                    boolean isSearchBinaryTree,
                    int maxVal, int minVal) {
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
            if (maxSubBSTSize(head) != getMaxBinarySearchSubTree(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
