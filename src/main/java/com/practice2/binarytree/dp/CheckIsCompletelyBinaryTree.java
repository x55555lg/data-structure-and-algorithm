package com.practice2.binarytree.dp;

import java.util.LinkedList;

/**
 * @author Xulg
 * @since 2021-10-15 17:26
 */
class CheckIsCompletelyBinaryTree {

    /*
     * 给定一颗二叉树的头节点head，返回这颗二叉树是不是完全二叉树？
     *------------------------------------------------------------
     *完全二叉树：
     *  方式一
     *      1.节点X只有右节点，没有左节点，那么一定不是完全二叉树
     *      2.如果遍历过程中遇到了节点X的左右节点不全，进行标记，
     *        后续的节点必须是叶子节点，否则就不是完全二叉树。
     *  方式二
     *      DP
     */

    private static class LevelLoop {
        public static boolean isCompletelyBinaryTree(Node root) {
            if (root == null) {
                return true;
            }
            LinkedList<Node> queue = new LinkedList<>();
            queue.add(root);
            boolean isBroken = false;
            while (!queue.isEmpty()) {
                Node node = queue.poll();
                if (node.left == null && node.right != null) {
                    // 节点X只有右节点，没有左节点，那么一定不是完全二叉树
                    return false;
                }
                if (isBroken) {
                    // 后续的节点必须是叶子节点，否则就不是完全二叉树。
                    if (node.left != null || node.right != null) {
                        return false;
                    }
                }
                if (node.left == null || node.right == null) {
                    // 如果遍历过程中遇到了节点X的左右节点不全，进行标记
                    isBroken = true;
                }
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            return true;
        }
    }

    private static class DP {
        public static boolean isCompletelyBinaryTree(Node root) {
            return recurse(root).isCBT;
        }

        private static Data recurse(Node node) {
            if (node == null) {
                return new Data(true, true, 0);
            }
            Data leftData = recurse(node.left);
            Data rightData = recurse(node.right);
            int treeHigh = Math.max(leftData.treeHigh, rightData.treeHigh) + 1;
            boolean isFull = leftData.isFull && rightData.isFull && (leftData.treeHigh == rightData.treeHigh);
            // boolean isFull = treeSize == (int) Math.pow(2, treeHigh) - 1;
            boolean isCBT;
            if (isFull) {
                // 当前节点X为根的树是满二叉树
                isCBT = true;
            } else {
                /* 当前节点X所在的树不是满二叉树 */
                if (!leftData.isCBT || !rightData.isCBT) {
                    // 左右子树有一个不是完全二叉树，则肯定不是
                    isCBT = false;
                } else if (leftData.isFull && rightData.isFull) {
                    // 左右子树都是满二叉树，左子树高度比右子树高度大1时
                    isCBT = (leftData.treeHigh - rightData.treeHigh == 1);
                } else if (leftData.isFull && rightData.isCBT) {
                    // 左子树是满二叉树，右子树是完全二叉树，必须左右子树高度相等时
                    isCBT = leftData.treeHigh == rightData.treeHigh;
                } else if (leftData.isCBT && rightData.isFull) {
                    // 左子树是完全二叉树，右子树是满二叉树，必须是左子树高度比右子树高度大1时
                    isCBT = (leftData.treeHigh - rightData.treeHigh == 1);
                } else {
                    // 其余情况，当前树都是不完全二叉树
                    isCBT = false;
                }
            }
            return new Data(isFull, isCBT, treeHigh);
        }

        private static class Data {
            private boolean isFull;
            private boolean isCBT;
            private int treeHigh;

            Data(boolean isFull, boolean isCBT, int treeHigh) {
                this.isFull = isFull;
                this.isCBT = isCBT;
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

}
