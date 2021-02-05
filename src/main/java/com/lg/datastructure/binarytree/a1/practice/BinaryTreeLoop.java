package com.lg.datastructure.binarytree.a1.practice;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 二叉树的遍历
 * 先序   中左右     逆序(中右左)
 * 中序   左中右     逆序(右中左)
 * 后序   左右中     逆序(中右左)
 * 记不住的死记硬背
 *
 * @author Xulg
 * Created in 2021-01-25 16:50
 */
class BinaryTreeLoop {

    /* ****************************************************************************************************************/

    /**
     * 递归方式的先序遍历
     * 中 左 右
     */
    public static void preorderLoopByRecurse(Node root) {
        if (root == null) {
            return;
        }
        System.out.println(root.value);
        preorderLoopByRecurse(root.left);
        preorderLoopByRecurse(root.right);
    }

    /**
     * 递归方式的中序遍历
     * 左 中 右
     */
    public static void middleOrderLoopByRecurse(Node root) {
        if (root == null) {
            return;
        }
        middleOrderLoopByRecurse(root.left);
        System.out.println(root.value);
        middleOrderLoopByRecurse(root.right);
    }

    /**
     * 递归方式的后序遍历
     * 左 右 中
     */
    public static void postOrderLoopByRecurse(Node root) {
        if (root == null) {
            return;
        }
        postOrderLoopByRecurse(root.left);
        postOrderLoopByRecurse(root.right);
        System.out.println(root.value);
    }

    /* ****************************************************************************************************************/

    /**
     * 使用栈实现先序遍历
     * 中 左 右
     */
    public static void preorderLoopByStack(Node root) {
        if (root == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        // 根节点先入栈
        stack.push(root);
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            System.out.println(node.value);
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
    }

    /**
     * 使用栈实现中序遍历
     * 左 中 右
     */
    public static void middleOrderLoopByStack(Node root) {
        if (root == null) {
            return;
        }
        /*
         * 指针指向头节点开始
         * 左节点不断的入栈
         * 出栈后判断右节点
         */
        Stack<Node> stack = new Stack<>();
        Node node = root;
        while (!stack.isEmpty() || node != null) {
            if (node != null) {
                // 节点入栈
                stack.push(node);
                node = node.left;
            } else {
                node = stack.pop();
                System.out.println(node.value);
                // 弹出之后要去看有没有右节点
                node = node.right;
            }
        }
    }

    /**
     * 使用栈实现后序遍历
     * 左 右 中 ==>>> 中右左(即先序)反转下
     */
    public static void postOrderLoopByStack(Node root) {
        if (root == null) {
            return;
        }
        Stack<Node> reverseStack = new Stack<>();
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            reverseStack.add(node);
            if (node.left != null) {
                stack.push(node.left);
            }
            if (node.right != null) {
                stack.push(node.right);
            }
        }
        while (!reverseStack.isEmpty()) {
            System.out.println(reverseStack.pop().value);
        }
    }

    /* ****************************************************************************************************************/

    /**
     * 使用队列实现层级遍历二叉树
     */
    public static void loopByLevel(Node root) {
        if (root == null) {
            return;
        }
        Queue<Node> queue = new LinkedList<>();
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

    /* ****************************************************************************************************************/

    private static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
        }
    }

    /* ****************************************************************************************************************/

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);

        preorderLoopByRecurse(head);
        System.out.println("========");
        middleOrderLoopByRecurse(head);
        System.out.println("========");
        postOrderLoopByRecurse(head);
        System.out.println("========");

        System.out.println("********");

        preorderLoopByStack(head);
        System.out.println("========");

        middleOrderLoopByStack(head);
        System.out.println("========");

        postOrderLoopByStack(head);
        System.out.println("========");

        System.out.println("********");

        loopByLevel(head);
    }
}
