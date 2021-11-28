package com.practice2.binarytree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author Xulg
 * @since 2021-10-14 14:20
 */
class SerializeDeserializeBinaryTree {

    /**
     * 先序遍历
     */
    private static class PreOrder {
        public static Queue<Integer> serialize1(Node root) {
            if (root == null) {
                return null;
            }
            LinkedList<Integer> list = new LinkedList<>();
            Stack<Node> stack = new Stack<>();
            stack.add(root);
            while (!stack.isEmpty()) {
                Node treeNode = stack.pop();
                list.add(treeNode == null ? null : treeNode.value);
                if (treeNode != null) {
                    stack.add(treeNode.right);
                    stack.add(treeNode.left);
                }
            }
            return list;
        }

        public static Queue<Integer> serialize2(Node root) {
            if (root == null) {
                return null;
            }
            LinkedList<Integer> list = new LinkedList<>();
            serializeRecurse(root, list);
            return list;
        }

        private static void serializeRecurse(Node node, LinkedList<Integer> data) {
            if (node == null) {
                data.add(null);
                return;
            }
            data.add(node.value);
            serializeRecurse(node.left, data);
            serializeRecurse(node.right, data);
        }

        public static Node deserialize(Queue<Integer> data) {
            if (data == null || data.isEmpty()) {
                return null;
            }
            return deserializeRecurse(data);
        }

        private static Node deserializeRecurse(Queue<Integer> data) {
            Integer value = data.poll();
            if (value == null) {
                return null;
            }
            Node treeNode = new Node(value);
            treeNode.left = deserializeRecurse(data);
            treeNode.right = deserializeRecurse(data);
            return treeNode;
        }
    }

    /**
     * 中序遍历
     */
    private static class InOrder {
        public static void traverse1(Node root) {
            if (root == null) {
                return;
            }
            traverse1(root.left);
            System.out.println(root.value);
            traverse1(root.right);
        }

        public static void traverse2(Node root) {
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
    }

    /**
     * 后序遍历
     */
    private static class PostOrder {
        public static Queue<Integer> serialize1(Node root) {
            if (root == null) {
                return null;
            }
            Stack<Integer> helper = new Stack<>();
            Stack<Node> stack = new Stack<>();
            stack.add(root);
            while (!stack.isEmpty()) {
                Node node = stack.pop();
                helper.add(node == null ? null : node.value);
                if (node != null) {
                    stack.add(node.left);
                    stack.add(node.right);
                }
            }
            LinkedList<Integer> list = new LinkedList<>();
            while (!helper.isEmpty()) {
                Integer value = helper.pop();
                list.add(value);
            }
            return list;
        }

        public static Queue<Integer> serialize2(Node root) {
            if (root == null) {
                return null;
            }
            LinkedList<Integer> list = new LinkedList<>();
            serializeRecurse(root, list);
            return list;
        }

        private static void serializeRecurse(Node node, LinkedList<Integer> data) {
            if (node == null) {
                data.add(null);
                return;
            }
            serializeRecurse(node.left, data);
            serializeRecurse(node.right, data);
            data.add(node.value);
        }

        public static Node deserialize(Queue<Integer> data) {
            if (data == null || data.isEmpty()) {
                return null;
            }
            Stack<Integer> stack = new Stack<>();
            while (!data.isEmpty()) {
                stack.add(data.poll());
            }
            return deserializeRecurse(stack);
        }

        private static Node deserializeRecurse(Stack<Integer> data) {
            Integer value = data.pop();
            if (value == null) {
                return null;
            }
            Node node = new Node(value);
            node.right = deserializeRecurse(data);
            node.left = deserializeRecurse(data);
            return node;
        }
    }

    /**
     * 层级遍历
     */
    private static class LevelOrder {
        public static Queue<Integer> serialize(Node root) {
            if (root == null) {
                return null;
            }
            LinkedList<Integer> data = new LinkedList<>();
            LinkedList<Node> queue = new LinkedList<>();
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

            Integer rootValue = data.poll();
            Node root = rootValue == null ? null : new Node(rootValue);

            LinkedList<Node> queue = new LinkedList<>();
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

    private static class Node {
        private int value;
        private Node left, right;

        Node(int value) {
            this.value = value;
        }

        Node(int value, Node left, Node right) {
            this.value = value;
            this.left = left;
            this.right = right;
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
            Queue<Integer> pre1 = PreOrder.serialize1(head);
            Queue<Integer> pos1 = PostOrder.serialize1(head);
            Queue<Integer> pre2 = PreOrder.serialize2(head);
            Queue<Integer> pos2 = PostOrder.serialize2(head);
            Queue<Integer> level = LevelOrder.serialize(head);
            Node preBuild1 = PreOrder.deserialize(pre1);
            Node posBuild1 = PostOrder.deserialize(pos1);
            Node preBuild2 = PreOrder.deserialize(pre2);
            Node posBuild2 = PostOrder.deserialize(pos2);
            Node levelBuild = LevelOrder.deserialize(level);
            if (!isSameValueStructure(preBuild1, posBuild1)
                    || !isSameValueStructure(posBuild1, levelBuild)
                    || !isSameValueStructure(preBuild2, posBuild2)
                    || !isSameValueStructure(posBuild2, levelBuild)) {
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
