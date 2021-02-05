package com.lg.datastructure.binarytree.a1.practice;

import org.apache.commons.lang3.StringUtils;

import java.util.Stack;

/**
 * 打印整颗二叉树
 *
 * @author Xulg
 * Created in 2021-01-25 16:53
 */
class PrintWholeBinaryTree {

    /*
     * 如何设计一个打印整颗二叉树的打印函数
     * 二叉树如下：
     *		            ①
     *	            ╱     ╲
     *	         ╱	          ╲
     *         ②	             ③
     *      ╱	  ╲	       ╱  ╲
     *   ╱	         ╲     ╱	      ╲
     * ④			  ⑤  ⑥		    ⑦
     * 打印：
     *                            ↓7777↓
     *                  ↓3333
     *                            ↑6666↑
     *          H1111H
     *                            ↓5555↓
     *                  ↑2222↑
     *                            ↑4444↑
     */

    public static void print(Node root) {
        if (root == null) {
            return;
        }

        /* 中序遍历的逆序 右中左 */

        Stack<Node> stack = new Stack<>();
        Node node = root;
        while (!stack.isEmpty() || node != null) {
            if (node != null) {
                stack.push(node);
                node = node.right;
            } else {
                node = stack.pop();
                if (node == root) {
                    System.out.println("H" + StringUtils.repeat(String.valueOf(node.value), 1) + "H");
                } else {
                    System.out.println(StringUtils.repeat(String.valueOf(node.value), 1));
                }
                node = node.left;
            }
        }
    }

    public static void print2(Node root) {
        doPrint(root, root, 1, "H");
    }

    private static void doPrint(Node root, Node node, int trimSize, String tag) {
        if (node == null) {
            return;
        }
        doPrint(root, node.right, trimSize * 2, "↓");
        if (node == root) {
            System.out.println(tag + StringUtils.repeat(String.valueOf(node.value), 1) + tag);
        } else {
            System.out.println(StringUtils.repeat("    ", trimSize) + tag + StringUtils.repeat(String.valueOf(node.value), 1) + tag);
        }
        doPrint(root, node.left, trimSize * 2, "↑");
    }

    private static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
        }
    }

    public static void main(String[] args) {
        {
            Node head = new Node(1);
            head.left = new Node(2);
            head.right = new Node(3);
            head.left.left = new Node(4);
            head.left.right = new Node(5);
            head.right.left = new Node(6);
            head.right.right = new Node(7);
            print2(head);
        }

        {
            Node head = new Node(1);
            head.left = new Node(-222222222);
            head.right = new Node(3);
            head.left.left = new Node(Integer.MIN_VALUE);
            head.right.left = new Node(55555555);
            head.right.right = new Node(66);
            head.left.left.right = new Node(777);
            print2(head);

            head = new Node(1);
            head.left = new Node(2);
            head.right = new Node(3);
            head.left.left = new Node(4);
            head.right.left = new Node(5);
            head.right.right = new Node(6);
            head.left.left.right = new Node(7);
            print2(head);

            head = new Node(1);
            head.left = new Node(1);
            head.right = new Node(1);
            head.left.left = new Node(1);
            head.right.left = new Node(1);
            head.right.right = new Node(1);
            head.left.left.right = new Node(1);
            print2(head);
        }
    }
}
