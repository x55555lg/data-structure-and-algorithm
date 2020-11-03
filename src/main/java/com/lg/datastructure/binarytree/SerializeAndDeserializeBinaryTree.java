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

    // TODO 2020/11/3 以后用到了再来学几遍

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
        } else {
            Node node = new Node(value);
            node.left = doDeserializeTreeByPreLoop(serializeQueue);
            node.right = doDeserializeTreeByPreLoop(serializeQueue);
            return node;
        }
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

    /* 按层遍历方式：序列化反序列化二叉树 */

    /**
     * 按层遍历方式序列化二叉树
     */
    public static Queue<Integer> serializeTreeByLevelLoop(Node treeRoot) {
        Queue<Integer> serializeQueue = new LinkedList<>();
        if (treeRoot == null) {
            serializeQueue.add(null);
            return serializeQueue;
        }
        // 加入头节点的值
        serializeQueue.add(treeRoot.value);
        // 使用队列遍历树
        Queue<Node> queue = new LinkedList<>();
        // 头节点入队
        queue.add(treeRoot);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (node.left != null) {
                queue.add(node.left);
                serializeQueue.add(node.left.value);
            } else {
                serializeQueue.add(null);
            }
            if (node.right != null) {
                queue.add(node.right);
                serializeQueue.add(node.right.value);
            } else {
                serializeQueue.add(null);
            }
        }
        return serializeQueue;
    }

    /**
     * 按层遍历方式反序列化二叉树
     */
    public static Node deserializeTreeByLevelLoop(Queue<Integer> serializeQueue) {
        if (serializeQueue == null || serializeQueue.isEmpty()) {
            return null;
        }
        // 创建树的根节点
        Integer rootValue = serializeQueue.poll();
        Node root = (rootValue == null) ? null : new Node(rootValue);

        // 加入头节点
        Queue<Node> queue = new LinkedList<>();
        if (root != null) {
            queue.add(root);
        }
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            Integer leftValue = serializeQueue.poll();
            Integer rightValue = serializeQueue.poll();
            if (leftValue != null) {
                node.left = new Node(leftValue);
                queue.add(node.left);
            }
            if (rightValue != null) {
                node.right = new Node(rightValue);
                queue.add(node.right);
            }
        }
        return root;
    }

    /* ************************************************************************************************************** */

    public static Node generateNode(String val) {
        if (val == null) {
            return null;
        }
        return new Node(Integer.valueOf(val));
    }

    // for test
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    // for test
    public static boolean isSameValueStructure(Node head1, Node head2) {
        if (head1 == null && head2 != null) {
            return false;
        }
        if (head1 != null && head2 == null) {
            return false;
        }
        if (head1 == null && head2 == null) {
            return true;
        }
        if (!head1.value.equals(head2.value)) {
            return false;
        }
        return isSameValueStructure(head1.left, head2.left)
                && isSameValueStructure(head1.right, head2.right);
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

    /* ************************************************************************************************************** */

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            Queue<Integer> pre = serializeTreeByPreLoop(head);
            Queue<Integer> pos = serializeTreeByPosLoop(head);
            Queue<Integer> level = serializeTreeByLevelLoop(head);
            Node preBuild = deserializeTreeByPreLoop(pre);
            Node posBuild = deserializeTreeByPosLoop(pos);
            Node levelBuild = deserializeTreeByLevelLoop(level);
            if (!isSameValueStructure(preBuild, posBuild)
                    || !isSameValueStructure(posBuild, levelBuild)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish!");
    }

}
