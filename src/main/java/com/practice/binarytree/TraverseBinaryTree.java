package com.practice.binarytree;

import java.util.LinkedList;
import java.util.Stack;

/**
 * 遍历二叉树
 * 先序
 * 中旭
 * 后续
 * 层级
 *
 * @author Xulg
 * Created in 2021-04-30 10:10
 */
class TraverseBinaryTree {

    /**
     * 先序
     * 递归
     */
    private static class PreOrderByRecurse {
        public static void traverse(Node root) {
            if (root == null) {
                return;
            }
            System.out.println(root.value);
            traverse(root.left);
            traverse(root.right);
        }
    }

    /**
     * 中序
     * 递归
     */
    private static class InOrderByRecurse {
        public static void traverse(Node root) {
            if (root == null) {
                return;
            }
            traverse(root.left);
            System.out.println(root.value);
            traverse(root.right);
        }
    }

    /**
     * 后序
     * 递归
     */
    private static class PostOrderByRecurse {
        public static void traverse(Node root) {
            if (root == null) {
                return;
            }
            traverse(root.left);
            traverse(root.right);
            System.out.println(root.value);
        }
    }

    /**
     * 先序
     * 栈
     */
    private static class PreOrderByStack {
        public static void traverse(Node root) {
            if (root == null) {
                return;
            }
            Stack<Node> stack = new Stack<>();
            stack.push(root);
            while (!stack.isEmpty()) {
                Node node = stack.pop();
                System.out.println(node.value);
                if (node.right != null) {
                    stack.add(node.right);
                }
                if (node.left != null) {
                    stack.add(node.left);
                }
            }
        }
    }

    /**
     * 中序
     * 递归
     */
    private static class InOrderByStack {
        public static void traverse(Node root) {
            if (root == null) {
                return;
            }
            Stack<Node> stack = new Stack<>();
            Node node = root;
            while (!stack.isEmpty() || node != null) {
                if (node != null) {
                    stack.add(node);
                    node = node.left;
                } else {
                    Node temp = stack.pop();
                    System.out.println(temp.value);
                    node = temp.right;
                }
            }
        }
    }

    /**
     * 后序
     * 栈
     */
    private static class PostOrderByStack {
        public static void traverse(Node root) {
            if (root == null) {
                return;
            }
            Stack<Node> help = new Stack<>();
            Stack<Node> stack = new Stack<>();
            stack.add(root);
            while (!stack.isEmpty()) {
                Node node = stack.pop();
                help.add(node);
                if (node.left != null) {
                    stack.add(node.left);
                }
                if (node.right != null) {
                    stack.add(node.right);
                }
            }
            while (!help.isEmpty()) {
                Node node = help.pop();
                System.out.println(node.value);
            }
        }
    }

    /**
     * 层级遍历
     * 队列
     */
    private static class LevelOrderByQueue {
        public static void traverse(Node root) {
            if (root == null) {
                return;
            }
            LinkedList<Node> queue = new LinkedList<>();
            queue.add(root);
            while (!queue.isEmpty()) {
                Node node = queue.poll();
                System.out.println(node.value);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
        }
    }

    /* ****************************************************************************************************************/

    private static class Node {
        private int value;
        private Node left;
        private Node right;

        Node(int value) {
            this.value = value;
        }
    }

    /* ****************************************************************************************************************/

    public static void main(String[] args) {
        Node root = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        Node node7 = new Node(7);
        root.left = node2;
        root.right = node3;
        node2.left = node4;
        node2.right = node5;
        node3.left = node6;
        node3.right = node7;

        PreOrderByRecurse.traverse(root);
        PreOrderByStack.traverse(root);

        InOrderByRecurse.traverse(root);
        InOrderByStack.traverse(root);

        PostOrderByRecurse.traverse(root);
        PostOrderByStack.traverse(root);

        LevelOrderByQueue.traverse(root);
    }

}
