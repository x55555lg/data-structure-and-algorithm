package com.lg.datastructure.binarytree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 序列化|反序列化二叉树
 *
 * @author Xulg
 * Created in 2020-10-30 17:36
 */
class SerializeAndDeserializeBinaryTree {

    /* 先序方式：序列化反序列化二叉树 */

    /**
     * 先序方式反序列化二叉树
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
