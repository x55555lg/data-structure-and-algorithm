package com.lg.datastructure.binarytree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 遍历二叉树
 *
 * @author Xulg
 * Created in 2020-10-30 17:33
 */
@SuppressWarnings("DuplicatedCode")
class BinaryTreeLoop {

    /*
     * 先序
     *  递归
     *  非递归
     *      一个栈实现
     *
     * 中序
     *  递归
     *  非递归
     *      一个栈实现
     *
     * 后序
     *  递归
     *  非递归
     *      一个栈实现(实现太骚)
     *      两个栈实现 ==> 左右中 = reverse(中右左)
     *
     * 层级遍历
     *  一个队列实现
     *
     *记不住就死背!!!!!!
     */

    /* ************************************************************************************************************** */

    /**
     * 递归
     * 先序遍历
     */
    public static void preLoopByRecurse(Node root) {
        if (root == null) {
            return;
        }
        System.out.println(root.value);
        preLoopByRecurse(root.left);
        preLoopByRecurse(root.right);
    }

    /**
     * 递归
     * 中序遍历
     */
    public static void inLoopByRecurse(Node root) {
        if (root == null) {
            return;
        }
        inLoopByRecurse(root.left);
        System.out.println(root.value);
        inLoopByRecurse(root.right);
    }

    /**
     * 递归
     * 后序遍历
     */
    public static void posLoopByRecurse(Node root) {
        if (root == null) {
            return;
        }
        posLoopByRecurse(root.left);
        posLoopByRecurse(root.right);
        System.out.println(root.value);
    }

    /* ************************************************************************************************************** */

    /**
     * 非递归
     * 先序遍历
     */
    public static void preLoopByStack(Node root) {
        if (root == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        // 头节点先入栈
        stack.add(root);
        while (!stack.isEmpty()) {
            // 弹出中间节点
            Node node = stack.pop();
            System.out.println(node.value);
            // 右节点先入栈，弹出的时候后弹
            if (node.right != null) {
                stack.add(node.right);
            }
            // 左节点后入栈，弹出的时候先弹
            if (node.left != null) {
                stack.add(node.left);
            }
        }
    }

    /**
     * 非递归
     * 中序遍历
     */
    public static void inLoopByStack(Node root) {
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
        // 栈不为空或者节点存在
        while (!stack.isEmpty() || node != null) {
            if (node != null) {
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
     * 非递归
     * 后序遍历
     * 后续遍历：左右中 = reverse(中右左)
     */
    public static void posLoopByTwoStack(Node root) {
        if (root == null) {
            return;
        }
        Stack<Node> helper = new Stack<>();
        Stack<Node> stack = new Stack<>();
        stack.add(root);
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            helper.add(node);
            if (node.left != null) {
                stack.add(node.left);
            }
            if (node.right != null) {
                stack.add(node.right);
            }
        }
        while (!helper.isEmpty()) {
            System.out.println(helper.pop().value);
        }
    }

    /**
     * 非递归
     * 后序遍历
     * 一个栈实现后序遍历
     */
    public static void posLoopByOneStack(Node root) {
        if (root == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        Node node;
        while (!stack.isEmpty()) {
            node = stack.peek();
            // 弹出节点的左节点存在，且当前节点不是弹出节点的左右节点
            if (node.left != null && root != node.left && root != node.right) {
                stack.push(node.left);
            }
            // 弹出节点的右节点存在，切当前节点不是弹出节点的右节点
            else if (node.right != null && root != node.right) {
                stack.push(node.right);
            }
            // ...
            else {
                System.out.print(stack.pop().value);
                root = node;
            }
        }
    }

    /* ************************************************************************************************************** */

    /**
     * 按层遍历
     */
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

    /* ************************************************************************************************************** */

    private static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
        }
    }

    /* ************************************************************************************************************** */

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

        preLoopByRecurse(root);
        inLoopByRecurse(root);
        posLoopByRecurse(root);

        levelLoop(root);

        preLoopByStack(root);
        inLoopByStack(root);
        posLoopByTwoStack(root);
    }
}
