package com.lg.datastructure.binarytree.a1.practice;

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

    /* ****************************************************************************************************************/

    /* 后序方式的序列化，反序列化 */

    /**
     * 先序方式序列化二叉树
     * 递归方式
     */
    public static Queue<Integer> serializeByPreorderLoop1(Node root) {
        if (root == null) {
            return null;
        }
        Queue<Integer> data = new LinkedList<>();
        recurseForSerializeByPreOrderLoop(root, data);
        return data;
    }

    private static void recurseForSerializeByPreOrderLoop(Node node, Queue<Integer> data) {
        if (node == null) {
            data.add(null);
        } else {
            data.add(node.value);
            recurseForSerializeByPreOrderLoop(node.left, data);
            recurseForSerializeByPreOrderLoop(node.right, data);
        }
    }

    /**
     * 先序方式序列化二叉树
     * 非递归方式
     */
    public static Queue<Integer> serializeByPreorderLoop2(Node root) {
        if (root == null) {
            return null;
        }
        Queue<Integer> data = new LinkedList<>();
        Stack<Node> stack = new Stack<>();
        stack.add(root);
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            data.add(node == null ? null : node.value);
            if (node != null) {
                stack.push(node.right);
                stack.push(node.left);
            }
        }
        return data;
    }

    /**
     * 先序方式反序列化二叉树
     * 递归方式
     */
    public static Node deserializeByPreorderLoop(Queue<Integer> data) {
        if (data == null || data.isEmpty()) {
            return null;
        }
        return recurseForDeserializeByPreorderLoop(data);
    }

    private static Node recurseForDeserializeByPreorderLoop(Queue<Integer> data) {
        Integer value = data.poll();
        if (value == null) {
            return null;
        } else {
            Node node = new Node(value);
            Node left = recurseForDeserializeByPreorderLoop(data);
            Node right = recurseForDeserializeByPreorderLoop(data);
            node.left = left;
            node.right = right;
            return node;
        }
    }

    /* ****************************************************************************************************************/

    /* 中序方式不能进行序列化和反序列化，因为使用中序方式之后，反序列化具有二义性 */

    public static void inOrderLoop_(Node root) {
        if (root == null) {
            return;
        }
        recurseInOrderLoop(root);
    }

    private static void recurseInOrderLoop(Node node) {
        if (node == null) {
            return;
        }
        recurseInOrderLoop(node.left);
        System.out.println(node.value);
        recurseInOrderLoop(node.right);
    }

    public static void inOrderLoop(Node root) {
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

    /* ****************************************************************************************************************/

    /* 后序方式的序列化，反序列化 */

    /**
     * 后序方式序列化二叉树
     * 非递归方式
     */
    public static Queue<Integer> serializeByPostOrderLoop1(Node root) {
        if (root == null) {
            return null;
        }
        Stack<Node> reverseStack = new Stack<>();
        Stack<Node> stack = new Stack<>();
        stack.add(root);
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            reverseStack.add(node);
            if (node != null) {
                stack.add(node.left);
                stack.add(node.right);
            }
        }
        Queue<Integer> data = new LinkedList<>();
        while (!reverseStack.isEmpty()) {
            Node node = reverseStack.pop();
            data.add(node == null ? null : node.value);
        }
        return data;
    }

    /**
     * 后序方式序列化二叉树
     * 递归方式
     */
    public static Queue<Integer> serializeByPostOrderLoop2(Node root) {
        if (root == null) {
            return null;
        }
        Queue<Integer> data = new LinkedList<>();
        recurseForSerializeByPostOrderLoop(root, data);
        return data;
    }

    private static void recurseForSerializeByPostOrderLoop(Node node, Queue<Integer> data) {
        if (node == null) {
            data.add(null);
        } else {
            recurseForSerializeByPostOrderLoop(node.left, data);
            recurseForSerializeByPostOrderLoop(node.right, data);
            data.add(node.value);
        }
    }

    /**
     * 后序方式反序列化二叉树
     * 递归方式
     */
    public static Node deserializeByPostOrderLoop(Queue<Integer> data) {
        if (data == null || data.isEmpty()) {
            return null;
        }
        // 使用栈修改顺序：左右中 -> (中右左)
        Stack<Integer> stack = new Stack<>();
        while (!data.isEmpty()) {
            stack.add(data.poll());
        }
        return recurseForDeserializeByPostOrderLoop(stack);
    }

    private static Node recurseForDeserializeByPostOrderLoop(Stack<Integer> data) {
        Integer value = data.pop();
        if (value == null) {
            return null;
        } else {
            Node right = recurseForDeserializeByPostOrderLoop(data);
            Node left = recurseForDeserializeByPostOrderLoop(data);
            Node node = new Node(value);
            node.right = right;
            node.left = left;
            return node;
        }

    }

    /* ****************************************************************************************************************/

    /* 层级遍历方式的序列化，反序列化 */

    /**
     * 层级遍历方式序列化二叉树
     */
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

    /**
     * 层级遍历方式反序列化二叉树
     */
    public static Node deserializeTreeByLevelLoop(Queue<Integer> data) {
        if (data == null || data.isEmpty()) {
            return null;
        }

        // 建立头节点
        Integer rootNodeValue = data.poll();
        Node root = rootNodeValue == null ? null : new Node(rootNodeValue);

        // 头节点入队
        Queue<Node> queue = new LinkedList<>();
        if (root != null) {
            queue.add(root);
        }

        while (!queue.isEmpty()) {
            Node node = queue.poll();
            Integer leftNodeValue = data.poll();
            if (leftNodeValue != null) {
                node.left = new Node(leftNodeValue);
                queue.add(node.left);
            }
            Integer rightNodeValue = data.poll();
            if (rightNodeValue != null) {
                node.right = new Node(rightNodeValue);
                queue.add(node.right);
            }
        }
        return root;
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
        if (head1.value != head2.value) {
            return false;
        }
        return isSameValueStructure(head1.left, head2.left)
                && isSameValueStructure(head1.right, head2.right);
    }

    /* ************************************************************************************************************** */

    public static void main(String[] args) {
        {
            Node head = new Node(1);
            head.left = new Node(2);
            head.right = new Node(3);
            head.left.left = new Node(4);
            head.left.right = new Node(5);
            head.right.left = new Node(6);
            head.right.right = new Node(7);
            Queue<Integer> data = serializeByPreorderLoop2(head);
            System.out.println(data);

            System.out.println(serializeByPreorderLoop1(head));
        }

        {
            Node head = new Node(1);
            head.left = new Node(2);
            head.right = new Node(3);
            head.left.left = new Node(4);
            head.left.right = null;
            head.right.left = new Node(6);
            head.right.right = new Node(7);
            Queue<Integer> data = serializeByPreorderLoop2(head);
            System.out.println(data);

            System.out.println(serializeByPreorderLoop1(head));
        }

        {
            Node head = new Node(1);
            head.left = new Node(2);
            head.right = new Node(3);
            head.left.left = new Node(4);
            head.left.right = new Node(5);
            head.right.left = new Node(6);
            head.right.right = new Node(7);
            Queue<Integer> data = serializeByPostOrderLoop1(head);
            System.out.println(data);
            Queue<Integer> data2 = serializeByPostOrderLoop2(head);
            System.out.println(data2);
        }

        {
            Node head = new Node(1);
            head.left = new Node(2);
            head.right = new Node(3);
            head.left.left = new Node(4);
            head.left.right = new Node(5);
            head.right.left = new Node(6);
            head.right.right = new Node(7);
            Queue<Integer> data = serializeByLevelLoop(head);
            System.out.println(data);
        }

        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            Queue<Integer> pre = serializeByPreorderLoop1(head);
            Queue<Integer> pos = serializeByPostOrderLoop1(head);
            Queue<Integer> level = serializeByLevelLoop(head);
            Node preBuild = deserializeByPreorderLoop(pre);
            Node posBuild = deserializeByPostOrderLoop(pos);
            Node levelBuild = deserializeTreeByLevelLoop(level);
            if (!isSameValueStructure(preBuild, posBuild)
                    || !isSameValueStructure(posBuild, levelBuild)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish!");
    }
}
