package com.lg.datastructure.binarytree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 序列化|反序列化二叉树
 *
 * @author Xulg
 * Created in 2020-10-30 17:36
 */
class SerializeAndDeserializeBinaryTree {

    /* 先序方式：序列化反序列化二叉树 */

    /**
     * 先序方式序列化二叉树
     */
    public static Queue<Integer> serializeTreeByPreLoop(Node treeRoot) {
        if (treeRoot == null) {
            return null;
        }
        Queue<Integer> serializeQueue = new LinkedList<>();
        doSerializeTreeByPreLoop(treeRoot, serializeQueue);
        return serializeQueue;
    }

    private static void doSerializeTreeByPreLoop(Node node, Queue<Integer> serializeQueue) {
        if (node == null) {
            // 节点为空，就记录null值
            serializeQueue.add(null);
        } else {
            // 记录节点的值
            serializeQueue.add(node.value);
            // 记录左节点
            doSerializeTreeByPreLoop(node.left, serializeQueue);
            // 记录右节点
            doSerializeTreeByPreLoop(node.right, serializeQueue);
        }
    }

    /**
     * 先序方式反序列化二叉树
     */
    public static Node deserializeTreeByPreLoop(Queue<Integer> serializeQueue) {
        if (serializeQueue == null || serializeQueue.isEmpty()) {
            return null;
        }
        return doDeserializeTreeByPreLoop(serializeQueue);
    }

    private static Node doDeserializeTreeByPreLoop(Queue<Integer> serializeQueue) {
        Integer value = serializeQueue.peek();
        if (value == null) {
            return null;
        }
        Node node = new Node(value);
        node.left = doDeserializeTreeByPreLoop(serializeQueue);
        node.right = doDeserializeTreeByPreLoop(serializeQueue);
        return node;
    }

    /* 后序方式：序列化反序列化二叉树 */

    /**
     * 后序方式序列化二叉树
     */
    public static Queue<Integer> serializeTreeByPosLoop(Node treeRoot) {
        if (treeRoot == null) {
            return null;
        }
        Queue<Integer> serializeQueue = new LinkedList<>();
        doSerializeTreeByPosLoop(treeRoot, serializeQueue);
        return serializeQueue;
    }

    private static void doSerializeTreeByPosLoop(Node node, Queue<Integer> serializeQueue) {
        if (node == null) {
            serializeQueue.add(null);
        } else {
            doSerializeTreeByPosLoop(node.left, serializeQueue);
            doSerializeTreeByPosLoop(node.right, serializeQueue);
            serializeQueue.add(node.value);
        }
    }

    /**
     * 后序方式反序列化二叉树
     */
    public static Node deserializeTreeByPosLoop(Queue<Integer> serializeQueue) {
        if (serializeQueue == null || serializeQueue.isEmpty()) {
            return null;
        }
        // 使用栈修改顺序：左右中 -> (中右左)
        Stack<Integer> serializeStack = new Stack<>();
        while (!serializeQueue.isEmpty()) {
            serializeStack.add(serializeQueue.peek());
        }
        return doDeserializeTreeByPosLoop(serializeStack);
    }

    private static Node doDeserializeTreeByPosLoop(Stack<Integer> serializeStack) {
        Integer value = serializeStack.peek();
        if (value == null) {
            return null;
        } else {
            Node node = new Node(value);
            node.right = doDeserializeTreeByPosLoop(serializeStack);
            node.left = doDeserializeTreeByPosLoop(serializeStack);
            return node;
        }
    }

    /* ************************************************************************************************************** */

    private static class Node {
        Integer value;
        Node left;
        Node right;

        Node(Integer value) {
            this.value = value;
        }
    }
}
