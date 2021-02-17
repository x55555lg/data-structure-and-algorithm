package com.lg.datastructure.binarytree.a1.practice;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 序列化|反序列化二叉树
 *
 * @author Xulg
 * Created in 2021-02-15 13:58
 */
class SerializeAndDeserializeBinaryTree3 {

    /* 先序 序列化反序列化 */

    public static Queue<Integer> preSerialize(Node root) {
        if (root == null) {
            return null;
        }
        Queue<Integer> data = new LinkedList<>();
        preSerializeRecursion(root, data);
        return data;
    }

    private static void preSerializeRecursion(Node node, Queue<Integer> data) {
        if (node == null) {
            data.add(null);
        } else {
            data.add(node.value);
            preSerializeRecursion(node.left, data);
            preSerializeRecursion(node.right, data);
        }
    }

    public static Node preDeserialize(Queue<Integer> data) {
        if (data == null) {
            return null;
        }
        return preDeserializeRecursion(data);
    }

    private static Node preDeserializeRecursion(Queue<Integer> data) {
        Integer value = data.poll();
        if (value == null) {
            return null;
        } else {
            Node node = new Node(value);
            node.left = preDeserializeRecursion(data);
            node.right = preDeserializeRecursion(data);
            return node;
        }
    }

    /* 后序 序列化反序列化 */

    public static Queue<Integer> postSerialize(Node root) {
        if (root == null) {
            return null;
        }
        Queue<Integer> data = new LinkedList<>();
        postSerializeRecursion(root, data);
        return data;
    }

    private static void postSerializeRecursion(Node node, Queue<Integer> data) {
        if (node == null) {
            data.add(null);
        } else {
            preSerializeRecursion(node.left, data);
            preSerializeRecursion(node.right, data);
            data.add(node.value);
        }
    }

    public static Node postDeserialize(Queue<Integer> data) {
        if (data == null) {
            return null;
        }
        Stack<Integer> stack = new Stack<>();
        while (!data.isEmpty()) {
            stack.add(data.poll());
        }
        return postDeserializeRecursion(stack);
    }

    private static Node postDeserializeRecursion(Stack<Integer> data) {
        Integer value = data.pop();
        if (value == null) {
            return null;
        } else {
            Node node = new Node(value);
            node.right = postDeserializeRecursion(data);
            node.left = postDeserializeRecursion(data);
            return node;
        }
    }

    /* 层级 序列化反序列化 */

    public static Queue<Integer> levelSerialize(Node root) {
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

    public static Node levelDeserialize(Queue<Integer> data) {
        if (data == null) {
            return null;
        }

        // 二叉树的根节点
        Integer rootValue = data.poll();
        Node root = (rootValue == null) ? null : new Node(rootValue);

        Queue<Node> queue = new LinkedList<>();
        if (root != null) {
            queue.add(root);
        }
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            Integer leftVal = data.poll();
            if (leftVal != null) {
                node.left = new Node(leftVal);
                queue.add(node.left);
            }
            Integer rightVal = data.poll();
            if (rightVal != null) {
                node.right = new Node(rightVal);
                queue.add(node.right);
            }
        }
        return root;
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
