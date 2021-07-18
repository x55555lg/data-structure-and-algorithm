package com.practice.binarytree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author Xulg
 * Created in 2021-05-08 10:06
 */
class SerializeAndDeserializeBinaryTree {

    private static class PreOrderSerializeAndDeserialize {
        public static Queue<Integer> serialize(Node root) {
            if (root == null) {
                return null;
            }
            Queue<Integer> data = new LinkedList<>();
            recurseSerialize(root, data);
            return data;
        }

        private static void recurseSerialize(Node node, Queue<Integer> data) {
            if (node == null) {
                data.add(null);
                return;
            }
            data.add(node.value);
            recurseSerialize(node.left, data);
            recurseSerialize(node.right, data);
        }

        public static Node deserialize(Queue<Integer> data) {
            if (data == null) {
                return null;
            }
            return recurseDeserialize(data);
        }

        private static Node recurseDeserialize(Queue<Integer> data) {
            Integer value = data.poll();
            if (value == null) {
                return null;
            }
            Node node = new Node(value);
            node.left = recurseDeserialize(data);
            node.right = recurseDeserialize(data);
            return node;
        }
    }

    private static class PostOrderSerializeAndDeserialize {
        public static Queue<Integer> serialize(Node root) {
            if (root == null) {
                return null;
            }
            Queue<Integer> data = new LinkedList<>();
            recurseSerialize(root, data);
            return data;
        }

        private static void recurseSerialize(Node node, Queue<Integer> data) {
            if (node == null) {
                data.add(null);
                return;
            }
            recurseSerialize(node.left, data);
            recurseSerialize(node.right, data);
            data.add(node.value);
        }

        public static Node deserialize(Queue<Integer> data) {
            if (data == null) {
                return null;
            }
            Stack<Integer> stack = new Stack<>();
            while (!data.isEmpty()) {
                stack.add(data.poll());
            }
            return recurseDeserialize(stack);
        }

        private static Node recurseDeserialize(Stack<Integer> data) {
            Integer value = data.pop();
            if (value == null) {
                return null;
            }
            Node node = new Node(value);
            node.right = recurseDeserialize(data);
            node.left = recurseDeserialize(data);
            return node;
        }

    }

    private static class LevelOrderSerializeAndDeserialize {
        public static Queue<Integer> serialize(Node root) {
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

        public static Node deserialize(Queue<Integer> data) {
            if (data == null || data.isEmpty()) {
                return null;
            }

            // 根节点
            Integer rootVal = data.poll();
            Node root = rootVal == null ? null : new Node(rootVal);

            Queue<Node> queue = new LinkedList<>();
            if (root != null) {
                queue.add(root);
            }
            while (!queue.isEmpty()) {
                Node node = queue.poll();
                Integer leftVal = data.poll();
                Integer rightVal = data.poll();
                Node left = leftVal == null ? null : new Node(leftVal);
                Node right = rightVal == null ? null : new Node(rightVal);
                node.left = left;
                node.right = right;
                if (left != null) {
                    queue.add(left);
                }
                if (right != null) {
                    queue.add(right);
                }
            }
            return root;
        }
    }

    /* ****************************************************************************************************************/

    private static class Node {
        private int value;
        private Node left;
        private Node right;

        Node(int value) {
            this.value = value;
        }
    }

    /* ****************************************************************************************************************/

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            Queue<Integer> pre = PreOrderSerializeAndDeserialize.serialize(head);
            Queue<Integer> pos = PostOrderSerializeAndDeserialize.serialize(head);
            Queue<Integer> level = LevelOrderSerializeAndDeserialize.serialize(head);
            Node preBuild = PreOrderSerializeAndDeserialize.deserialize(pre);
            Node posBuild = PostOrderSerializeAndDeserialize.deserialize(pos);
            Node levelBuild = LevelOrderSerializeAndDeserialize.deserialize(level);
            if (!isSameValueStructure(preBuild, posBuild)
                    || !isSameValueStructure(posBuild, levelBuild)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish!");
    }


    /* ************************************************************************************************************** */

    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

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

}
