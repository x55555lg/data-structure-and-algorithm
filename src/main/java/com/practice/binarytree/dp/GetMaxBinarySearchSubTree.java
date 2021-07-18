package com.practice.binarytree.dp;

import java.util.ArrayList;

/**
 * @author Xulg
 * Description: 获取二叉树中的最大搜索树
 * Created in 2021-05-18 13:40
 */
@SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
class GetMaxBinarySearchSubTree {

    /*
     * 给定一个二叉树的头节点head。返回这颗二叉树中最大的二叉搜索子树的头节点
     */

    @SuppressWarnings("ConstantConditions")
    private static class Solution {

        public static Node getMaxSubBinarySearchTreeHead(Node root) {
            return recurse(root).maxSubBSTHead;
        }

        public static int getMaxSubBinarySearchTreeSize(Node root) {
            return recurse(root).maxSubBSTSize;
        }

        private static Data recurse(Node node) {
            if (node == null) {
                return new Data(Integer.MAX_VALUE, Integer.MIN_VALUE, 0, null);
            }
            Data leftData = recurse(node.left);
            Data rightData = recurse(node.right);

            // 当前节点X为头的树是否是搜索二叉树
            boolean isBinarySearchTree = (leftData.maxSubBSTHead == node.left)
                    && (rightData.maxSubBSTHead == node.right)
                    && (leftData.maxVal < node.val && node.val < rightData.minVal);
            // 当前节点X为头的树的最小值
            int minVal = Math.min(Math.min(leftData.minVal, rightData.minVal), node.val);
            // 当前节点X为头的树的最大值
            int maxVal = Math.max(Math.max(leftData.maxVal, rightData.maxVal), node.val);

            int maxSubBSTSize;
            Node maxSubBSTHead;
            if (isBinarySearchTree) {
                /*当前节点X为头的树就是一个搜索二叉树*/
                // 最大搜索二叉子树的头就是当前节点X
                maxSubBSTHead = node;
                // 最大搜索子树的节点数量为左右子树节点数之和 + 1
                maxSubBSTSize = leftData.maxSubBSTSize + rightData.maxSubBSTSize + 1;
            } else {
                /*当前节点X为头的树不是一个搜索二叉树，那么最大搜索子树在左右子树上*/
                maxSubBSTSize = Math.max(leftData.maxSubBSTSize, rightData.maxSubBSTSize);
                if (leftData.maxSubBSTSize == maxSubBSTSize) {
                    maxSubBSTSize = leftData.maxSubBSTSize;
                    maxSubBSTHead = leftData.maxSubBSTHead;
                } else if (rightData.maxSubBSTSize == maxSubBSTSize) {
                    maxSubBSTSize = rightData.maxSubBSTSize;
                    maxSubBSTHead = rightData.maxSubBSTHead;
                } else {
                    // unreachable branch
                    maxSubBSTSize = 0;
                    maxSubBSTHead = null;
                }
            }
            return new Data(minVal, maxVal, maxSubBSTSize, maxSubBSTHead);
        }

        private static class Data {
            int minVal;
            int maxVal;
            int maxSubBSTSize;
            Node maxSubBSTHead;

            Data(int minVal, int maxVal, int maxSubBSTSize, Node maxSubBSTHead) {
                this.minVal = minVal;
                this.maxVal = maxVal;
                this.maxSubBSTSize = maxSubBSTSize;
                this.maxSubBSTHead = maxSubBSTHead;
            }
        }
    }

    private static class Compare {

        public static Node getMaxSubBinarySearchTreeHead(Node head) {
            if (head == null) {
                return null;
            }
            if (getBSTSize(head) != 0) {
                return head;
            }
            Node leftAns = getMaxSubBinarySearchTreeHead(head.left);
            Node rightAns = getMaxSubBinarySearchTreeHead(head.right);
            return getBSTSize(leftAns) >= getBSTSize(rightAns) ? leftAns : rightAns;
        }

        public static int getMaxSubBinarySearchTreeSize(Node head) {
            if (head == null) {
                return 0;
            }
            int h = getBSTSize(head);
            if (h != 0) {
                return h;
            }
            return Math.max(getMaxSubBinarySearchTreeSize(head.left), getMaxSubBinarySearchTreeSize(head.right));
        }

        private static int getBSTSize(Node head) {
            if (head == null) {
                return 0;
            }
            ArrayList<Node> arr = new ArrayList<>();
            in(head, arr);
            for (int i = 1; i < arr.size(); i++) {
                if (arr.get(i).val <= arr.get(i - 1).val) {
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
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            Node h0 = Compare.getMaxSubBinarySearchTreeHead(head);
            Node h1 = Solution.getMaxSubBinarySearchTreeHead(head);
            int s0 = Compare.getMaxSubBinarySearchTreeSize(head);
            int s1 = Solution.getMaxSubBinarySearchTreeSize(head);
            if (!(h0 == h1) || !(s0 == s1)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
