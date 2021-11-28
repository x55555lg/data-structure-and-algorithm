package com.practice2.binarytree;

import java.util.LinkedList;
import java.util.Stack;

/**
 * @author Xulg
 * @since 2021-10-14 9:48
 * Description: 二叉树的遍历
 */
class TraverseBinaryTree {

    /**
     * 先序遍历-递归
     */
    private static class PreOrderLoopByRecurse {
        public static void traverse(TreeNode root) {
            if (root == null) {
                return;
            }
            System.out.println(root.value);
            traverse(root.left);
            traverse(root.right);
        }
    }

    /**
     * 中序遍历-递归
     */
    private static class InOrderLoopByRecurse {
        public static void traverse(TreeNode root) {
            if (root == null) {
                return;
            }
            traverse(root.left);
            System.out.println(root.value);
            traverse(root.right);
        }
    }

    /**
     * 后序遍历-递归
     */
    private static class PostOrderLoopByRecurse {
        public static void traverse(TreeNode root) {
            if (root == null) {
                return;
            }
            traverse(root.left);
            traverse(root.right);
            System.out.println(root.value);
        }
    }

    /**
     * 先序遍历-栈
     */
    private static class PreOrderLoopByStack {
        public static void traverse(TreeNode root) {
            if (root == null) {
                return;
            }
            Stack<TreeNode> stack = new Stack<>();
            stack.push(root);
            while (!stack.isEmpty()) {
                TreeNode treeNode = stack.pop();
                System.out.println(treeNode.value);
                if (treeNode.right != null) {
                    stack.add(treeNode.right);
                }
                if (treeNode.left != null) {
                    stack.add(treeNode.left);
                }
            }
        }
    }

    private static class InOrderLoopByStack {
        public static void traverse(TreeNode root) {
            if (root == null) {
                return;
            }
            Stack<TreeNode> stack = new Stack<>();
            TreeNode treeNode = root;
            while (!stack.isEmpty() || treeNode != null) {
                if (treeNode != null) {
                    stack.push(treeNode);
                    treeNode = treeNode.left;
                } else {
                    TreeNode temp = stack.pop();
                    System.out.println(temp.value);
                    treeNode = temp.right;
                }
            }
        }
    }

    private static class PostOrderLoopByStack {
        public static void traverse(TreeNode root) {
            if (root == null) {
                return;
            }
            Stack<TreeNode> helper = new Stack<>();
            Stack<TreeNode> stack = new Stack<>();
            stack.add(root);
            while (!stack.isEmpty()) {
                TreeNode treeNode = stack.pop();
                helper.add(treeNode);
                if (treeNode.left != null) {
                    stack.add(treeNode.left);
                }
                if (treeNode.right != null) {
                    stack.add(treeNode.right);
                }
            }
            while (!helper.isEmpty()) {
                System.out.println(helper.pop().value);
            }
        }
    }

    /**
     * 层级遍历
     */
    private static class LevelOrderLoop {
        public static void traverse(TreeNode root) {
            if (root == null) {
                return;
            }
            LinkedList<TreeNode> queue = new LinkedList<>();
            queue.add(root);
            while (!queue.isEmpty()) {
                TreeNode treeNode = queue.poll();
                System.out.println(treeNode.value);
                if (treeNode.left != null) {
                    queue.add(treeNode.left);
                }
                if (treeNode.right != null) {
                    queue.add(treeNode.right);
                }
            }
        }
    }

    private static class TreeNode {
        private int value;
        private TreeNode left, right;

        TreeNode(int value) {
            this.value = value;
        }

        TreeNode(int value, TreeNode left, TreeNode right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);
        TreeNode node6 = new TreeNode(6);
        TreeNode node7 = new TreeNode(7);
        root.left = node2;
        root.right = node3;
        node2.left = node4;
        node2.right = node5;
        node3.left = node6;
        node3.right = node7;

        PreOrderLoopByRecurse.traverse(root);
        PreOrderLoopByStack.traverse(root);

        System.out.println("----------------------------");

        InOrderLoopByRecurse.traverse(root);
        InOrderLoopByStack.traverse(root);

        System.out.println("----------------------------");

        PostOrderLoopByRecurse.traverse(root);
        PostOrderLoopByStack.traverse(root);

        System.out.println("----------------------------");

        LevelOrderLoop.traverse(root);
    }

}
