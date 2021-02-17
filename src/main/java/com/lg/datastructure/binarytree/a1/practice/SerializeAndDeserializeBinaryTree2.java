package com.lg.datastructure.binarytree.a1.practice;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 序列化|反序列化二叉树
 *
 * @author Xulg
 * Created in 2021-02-05 14:40
 */
@SuppressWarnings("AlibabaCommentsMustBeJavadocFormat")
class SerializeAndDeserializeBinaryTree2 {

    /* 先序 */

    // 先序序列化二叉树
    public static Queue<Integer> serializeByPreLoop(Node root) {
        if (root == null) {
            return null;
        }
        Queue<Integer> data = new LinkedList<>();
        serializeByPreLoopRecurse(root, data);
        return data;
    }

    private static void serializeByPreLoopRecurse(Node node, Queue<Integer> data) {
        if (node == null) {
            data.add(null);
            return;
        }
        data.add(node.value);
        serializeByPreLoopRecurse(node.left, data);
        serializeByPreLoopRecurse(node.right, data);
    }

    // 先序反序列化二叉树
    public static Node deserializeByPreLoop(Queue<Integer> data) {
        if (data == null || data.isEmpty()) {
            return null;
        }
        return deserializeByPreLoopRecurse(data);
    }

    private static Node deserializeByPreLoopRecurse(Queue<Integer> data) {
        Integer value = data.poll();
        if (value == null) {
            return null;
        }
        Node node = new Node(value);
        node.left = deserializeByPreLoopRecurse(data);
        node.right = deserializeByPreLoopRecurse(data);
        return node;
    }

    /* 后序 */

    // 后序序列化二叉树
    public static Queue<Integer> serializeByPostLoop(Node root) {
        if (root == null) {
            return null;
        }
        Queue<Integer> data = new LinkedList<>();
        serializeByPostLoopRecurse(root, data);
        return data;
    }

    private static void serializeByPostLoopRecurse(Node node, Queue<Integer> data) {
        if (node == null) {
            data.add(null);
            return;
        }
        serializeByPostLoopRecurse(node.left, data);
        serializeByPostLoopRecurse(node.right, data);
        data.add(node.value);
    }

    // 后序反序列化二叉树
    public static Node deserializeByPostLoop(Queue<Integer> data) {
        if (data == null || data.isEmpty()) {
            return null;
        }
        Stack<Integer> stack = new Stack<>();
        // 使用栈修改顺序：左右中 -> (中右左)
        while (!data.isEmpty()) {
            stack.add(data.poll());
        }
        return deserializeByPostLoopRecurse(stack);
    }

    private static Node deserializeByPostLoopRecurse(Stack<Integer> data) {
        Integer value = data.pop();
        if (value == null) {
            return null;
        }
        Node node = new Node(value);
        node.right = deserializeByPostLoopRecurse(data);
        node.left = deserializeByPostLoopRecurse(data);
        return node;
    }

    /* 层级 */

    // 层级遍历序列化二叉树
    public static Queue<Integer> serializeByLevelLoop(Node root) {
        if (root == null) {
            return null;
        }
        Queue<Integer> data = new LinkedList<>();
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            data.add(node == null ? null : node.value);
            if (node != null) {
                queue.add(node.left);
                queue.add(node.right);
            }
        }
        return data;
    }

    // 层级遍历反序列化二叉树
    public static Node deserializeByLevelLoop(Queue<Integer> data) {
        if (data == null || data.isEmpty()) {
            return null;
        }

        Integer rootValue = data.poll();
        Node root = (rootValue == null) ? null : new Node(rootValue);

        Queue<Node> queue = new LinkedList<>();
        if (root != null) {
            queue.add(root);
        }
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            Integer leftValue = data.poll();
            if (leftValue != null) {
                Node left = new Node(leftValue);
                node.left = left;
                queue.add(left);
            }
            Integer rightValue = data.poll();
            if (rightValue != null) {
                Node right = new Node(rightValue);
                node.right = right;
                queue.add(right);
            }
        }

        return root;
    }

    /* ****************************************************************************************************************/
    /* ****************************************************************************************************************/
    /* ****************************************************************************************************************/

    private static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
        }
    }
}
