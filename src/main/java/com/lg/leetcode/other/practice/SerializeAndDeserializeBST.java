package com.lg.leetcode.other.practice;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author Xulg
 * Description: leetcode_449
 * Created in 2021-05-25 21:56
 */
class SerializeAndDeserializeBST {

    /*
     *  Serialization is converting a data structure or object into a sequence of
     * bits so that it can be stored in a file or memory buffer, or transmitted
     * across a network connection link to be reconstructed later in the same or
     * another computer environment.
     *
     *  Design an algorithm to serialize and deserialize a binary search tree.
     * There is no restriction on how your serialization/deserialization algorithm
     * should work. You need to ensure that a binary search tree can be serialized
     * to a string, and this string can be deserialized to the original tree structure.
     *
     * The encoded string should be as compact as possible.
     *
     * Example 1:
     * Input: root = [2,1,3]
     * Output: [2,1,3]
     *
     * Example 2:
     * Input: root = []
     * Output: []
     *
     * Constraints:
     * The number of nodes in the tree is in the range [0, 104].
     * 0 <= Node.val <= 104
     * The input tree is guaranteed to be a binary search tree.
     */

    private static class PreOrderCodec {
        public static String serialize(TreeNode root) {
            if (root == null) {
                return null;
            }
            LinkedList<Integer> data = new LinkedList<>();
            recurseBySerialize(root, data);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < data.size(); i++) {
                Integer val = data.get(i);
                if (i == data.size() - 1) {
                    sb.append(val);
                } else {
                    sb.append(val).append(",");
                }
            }
            return sb.toString();
        }

        private static void recurseBySerialize(TreeNode node, Queue<Integer> data) {
            if (node == null) {
                data.add(null);
                return;
            }
            data.add(node.val);
            recurseBySerialize(node.left, data);
            recurseBySerialize(node.right, data);
        }

        public static TreeNode deserialize(String data) {
            if (data == null) {
                return null;
            }
            Queue<String> queue = new LinkedList<>(Arrays.asList(data.split(",")));
            return recurseByDeserialize(queue);
        }

        private static TreeNode recurseByDeserialize(Queue<String> data) {
            String value = data.poll();
            if ("null".equals(value)) {
                return null;
            }
            TreeNode node = new TreeNode(Integer.parseInt(value));
            node.left = recurseByDeserialize(data);
            node.right = recurseByDeserialize(data);
            return node;
        }
    }

    private static class PostOrderCodec {
        public static String serialize(TreeNode root) {
            if (root == null) {
                return null;
            }
            LinkedList<Integer> data = new LinkedList<>();
            recurseBySerialize(root, data);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < data.size(); i++) {
                Integer val = data.get(i);
                if (i == data.size() - 1) {
                    sb.append(val);
                } else {
                    sb.append(val).append(",");
                }
            }
            return sb.toString();
        }

        private static void recurseBySerialize(TreeNode node, Queue<Integer> data) {
            if (node == null) {
                data.add(null);
                return;
            }
            recurseBySerialize(node.left, data);
            recurseBySerialize(node.right, data);
            data.add(node.val);
        }

        public static TreeNode deserialize(String data) {
            if (data == null) {
                return null;
            }
            LinkedList<String> info = new LinkedList<>(Arrays.asList(data.split(",")));
            Stack<Integer> stack = new Stack<>();
            while (!info.isEmpty()) {
                String value = info.poll();
                stack.add("null".equals(value) ? null : Integer.parseInt(value));
            }
            return recurseByDeserialize(stack);
        }

        private static TreeNode recurseByDeserialize(Stack<Integer> stack) {
            Integer value = stack.pop();
            if (value == null) {
                return null;
            }
            TreeNode node = new TreeNode(value);
            node.right = recurseByDeserialize(stack);
            node.left = recurseByDeserialize(stack);
            return node;
        }
    }

    private static class LevelOrderCodec {
        public static String serialize(TreeNode root) {
            if (root == null) {
                return null;
            }
            LinkedList<Integer> data = new LinkedList<>();
            Queue<TreeNode> queue = new LinkedList<>();
            queue.add(root);
            while (!queue.isEmpty()) {
                TreeNode node = queue.poll();
                data.add(node == null ? null : node.val);
                if (node != null) {
                    queue.add(node.left);
                    queue.add(node.right);
                }
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < data.size(); i++) {
                Integer value = data.get(i);
                if (i == data.size() - 1) {
                    sb.append(value);
                } else {
                    sb.append(value).append(",");
                }
            }
            return sb.toString();
        }

        public static TreeNode deserialize(String data) {
            if (data == null) {
                return null;
            }
            Queue<TreeNode> queue = new LinkedList<>();
            LinkedList<String> info = new LinkedList<>(Arrays.asList(data.split(",")));
            String rootValue = info.poll();
            TreeNode root = "null".equals(rootValue) ? null : new TreeNode(Integer.parseInt(rootValue));
            if (root != null) {
                queue.add(root);
            }
            while (!queue.isEmpty()) {
                TreeNode node = queue.poll();
                String leftValue = info.poll();
                String rightValue = info.poll();
                TreeNode leftNode = "null".equals(leftValue) ? null : new TreeNode(Integer.parseInt(leftValue));
                TreeNode rightNode = "null".equals(rightValue) ? null : new TreeNode(Integer.parseInt(rightValue));
                node.left = leftNode;
                node.right = rightNode;
                if (leftNode != null) {
                    queue.add(leftNode);
                }
                if (rightNode != null) {
                    queue.add(rightNode);
                }
            }
            return root;
        }
    }

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int value) { val = value; }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        String serialize = PreOrderCodec.serialize(root);
        System.out.println(serialize);
        TreeNode deserialize = PreOrderCodec.deserialize(serialize);
        System.out.println(deserialize);
    }
}
