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
 * Created in 2021-02-15 13:35
 */
class BinaryTreeLoop2 {

    /* 先序 中左右 */

    public static void preLoop1(Node root) {
        if (root == null) {
            return;
        }
        preRecursion(root);
    }

    private static void preRecursion(Node node) {
        if (node == null) {
            return;
        }
        System.out.println(node.value);
        preRecursion(node.left);
        preRecursion(node.right);
    }

    public static void preLoop2(Node root) {
        if (root == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        stack.add(root);
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

    /* 中序 左中右 */

    public static void inLoop1(Node root) {
        if (root == null) {
            return;
        }
        inRecursion(root);
    }

    private static void inRecursion(Node node) {
        if (node == null) {
            return;
        }
        preRecursion(node.left);
        System.out.println(node.value);
        preRecursion(node.right);
    }

    public static void inLoop2(Node root) {
        if (root == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        Node node = root;
        while (!stack.isEmpty() || node != null) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else {
                Node temp = stack.pop();
                System.out.println(temp.value);
                node = temp.right;
            }
        }
    }

    /* 后序 左右中 */

    public static void postLoop1(Node root) {
        if (root == null) {
            return;
        }
        postRecursion(root);
    }

    private static void postRecursion(Node node) {
        if (node == null) {
            return;
        }
        preRecursion(node.left);
        preRecursion(node.right);
        System.out.println(node.value);
    }

    public static void postLoop2(Node root) {
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
            Node node = reverseStack.pop();
            System.out.println(node.value);
        }
    }

    /* 按层遍历 */

    public static void levelLoop(Node root) {
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

    /* **********************************************************************************************************/

    private static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
        }
    }

}
