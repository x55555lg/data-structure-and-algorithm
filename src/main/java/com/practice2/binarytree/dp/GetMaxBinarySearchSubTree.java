package com.practice2.binarytree.dp;

import java.util.ArrayList;

/**
 * @author Xulg
 * @since 2021-10-17 20:28
 */
class GetMaxBinarySearchSubTree {

    /*
     * 给定一个二叉树的头节点head。返回这颗二叉树中最大的二叉搜索子树的头节点
     */

    private static class DP {
        public static Node findMaxSubBinarySearchTreeHead(Node root) {
            return recurse(root).maxSubBSTHead;
        }

        public static int findMaxSubBinarySearchTreeSize(Node root) {
            return recurse(root).maxSubBSTSize;
        }

        private static Data recurse(Node node) {
            if (node == null) {
                return new Data(null, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
            }
            Data leftData = recurse(node.left);
            Data rightData = recurse(node.right);
            int maxValue = Math.max(node.value, Math.max(leftData.maxValue, rightData.maxValue));
            int minValue = Math.min(node.value, Math.min(leftData.minValue, rightData.minValue));
            // 当前节点X为根的树是否是搜索二叉树 = 节点X的左子节点是左边搜索二叉树的头 && 节点X的右子节点是右边搜索二叉树的头
            //                                      && 左子树的最大值小于节点X的值，右子树的最小值大于节点X的值
            boolean isBST = (leftData.maxSubBSTHead == node.left)
                    && (rightData.maxSubBSTHead == node.right)
                    && (leftData.maxValue < node.value && node.value < rightData.minValue);
            Node maxSubBinarySearchTreeHead;
            int maxSubBinarySearchTreeSize;
            if (isBST) {
                // 节点X为根的树就是最大搜索二叉树
                maxSubBinarySearchTreeHead = node;
                maxSubBinarySearchTreeSize = leftData.maxSubBSTSize + rightData.maxSubBSTSize + 1;
            } else {
                // 在左右子树上面
                maxSubBinarySearchTreeSize = Math.max(leftData.maxSubBSTSize, rightData.maxSubBSTSize);
                if (maxSubBinarySearchTreeSize == leftData.maxSubBSTSize) {
                    maxSubBinarySearchTreeHead = leftData.maxSubBSTHead;
                } else if (maxSubBinarySearchTreeSize == rightData.maxSubBSTSize) {
                    maxSubBinarySearchTreeHead = rightData.maxSubBSTHead;
                } else {
                    throw new Error();
                }
            }
            return new Data(maxSubBinarySearchTreeHead, maxSubBinarySearchTreeSize, maxValue, minValue);
        }

        private static class Data {
            private Node maxSubBSTHead;
            private int maxSubBSTSize;
            private int maxValue;
            private int minValue;

            Data(Node maxSubBSTHead, int maxSubBSTSize, int maxValue, int minValue) {
                this.maxSubBSTHead = maxSubBSTHead;
                this.maxSubBSTSize = maxSubBSTSize;
                this.maxValue = maxValue;
                this.minValue = minValue;
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
            Node h1 = DP.findMaxSubBinarySearchTreeHead(head);
            int s0 = Compare.getMaxSubBinarySearchTreeSize(head);
            int s1 = DP.findMaxSubBinarySearchTreeSize(head);
            if (!(h0 == h1) || !(s0 == s1)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}
