package com.lg.leetcode.topinterview.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * @author Xulg
 * Created in 2021-02-24 13:29
 */
class SerializeAndDeserializeBinaryTree {

    /*
     *  Serialization is the process of converting a data structure or object into a sequence of bits so that
     * it can be stored in a file or memory buffer, or transmitted across a network connection link to be
     * reconstructed later in the same or another computer environment.
     *  Design an algorithm to serialize and deserialize a binary tree. There is no restriction on how your
     * serialization/deserialization algorithm should work. You just need to ensure that a binary tree can
     * be serialized to a string and this string can be deserialized to the original tree structure.
     * Clarification:
     *      The input/output format is the same as how LeetCode serializes a binary tree.
     *      You do not necessarily need to follow this format, so please be creative and come up with different approaches yourself.
     * Definition for a binary tree node.
     * public class TreeNode {
     *     int val;
     *     TreeNode left;
     *     TreeNode right;
     *     TreeNode(int x) { val = x; }
     * }
     */

    public static String serialize(TreeNode root) {
        return PreOrder.serialize(root);
    }

    public static TreeNode deserialize(String data) {
        return PreOrder.deserialize(data);
    }

    /**
     * 先序方式序列化反序列化
     */
    private static class PreOrder {

        public static String serialize(TreeNode root) {
            if (root == null) {
                return null;
            }
            List<Integer> data = new ArrayList<>();
            recurseForSerialize(root, data);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < data.size(); i++) {
                if (i == data.size() - 1) {
                    sb.append(data.get(i));
                } else {
                    sb.append(data.get(i)).append(",");
                }
            }
            return sb.toString();
        }

        private static void recurseForSerialize(TreeNode treeNode, List<Integer> data) {
            if (treeNode == null) {
                data.add(null);
            } else {
                data.add(treeNode.val);
                recurseForSerialize(treeNode.left, data);
                recurseForSerialize(treeNode.right, data);
            }
        }

        public static TreeNode deserialize(String data) {
            if (data == null || data.length() == 0) {
                return null;
            }
            Queue<String> queue = new LinkedList<>(Arrays.asList(data.split(",")));
            return recurseForDeserialize(queue);
        }

        private static TreeNode recurseForDeserialize(Queue<String> queue) {
            String str = queue.poll();
            if ("null".equals(str) || str == null) {
                return null;
            } else {
                TreeNode treeNode = new TreeNode(Integer.parseInt(str));
                treeNode.left = recurseForDeserialize(queue);
                treeNode.right = recurseForDeserialize(queue);
                return treeNode;
            }
        }

    }

    /**
     * 后序方式序列化反序列化
     */
    private static class PostOrder {

        public static String serialize(TreeNode root) {
            if (root == null) {
                return null;
            }
            List<Integer> data = new ArrayList<>();
            recurseForSerialize(root, data);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < data.size(); i++) {
                if (i == data.size() - 1) {
                    sb.append(data.get(i));
                } else {
                    sb.append(data.get(i)).append(",");
                }
            }
            return sb.toString();
        }

        private static void recurseForSerialize(TreeNode treeNode, List<Integer> data) {
            if (treeNode == null) {
                data.add(null);
            } else {
                recurseForSerialize(treeNode.left, data);
                recurseForSerialize(treeNode.right, data);
                data.add(treeNode.val);
            }
        }

        public static TreeNode deserialize(String data) {
            if (data == null || data.length() == 0) {
                return null;
            }
            Queue<String> queue = new LinkedList<>(Arrays.asList(data.split(",")));
            Stack<String> stack = new Stack<>();
            // 左右中 -> 中右左
            while (!queue.isEmpty()) {
                stack.push(queue.poll());
            }
            return recurseForDeserialize(stack);
        }

        private static TreeNode recurseForDeserialize(Stack<String> stack) {
            String str = stack.pop();
            if ("null".equals(str) || str == null) {
                return null;
            } else {
                TreeNode treeNode = new TreeNode(Integer.parseInt(str));
                treeNode.right = recurseForDeserialize(stack);
                treeNode.left = recurseForDeserialize(stack);
                return treeNode;
            }
        }

    }

    /**
     * 层级方式序列化反序列化
     */
    private static class LevelOrder {

        public static String serialize(TreeNode root) {
            if (root == null) {
                return null;
            }
            List<Integer> data = new ArrayList<>();
            Queue<TreeNode> queue = new LinkedList<>();
            queue.add(root);
            while (!queue.isEmpty()) {
                TreeNode treeNode = queue.poll();
                data.add(treeNode == null ? null : treeNode.val);
                if (treeNode != null) {
                    queue.add(treeNode.left);
                    queue.add(treeNode.right);
                }
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < data.size(); i++) {
                if (i == data.size() - 1) {
                    sb.append(data.get(i));
                } else {
                    sb.append(data.get(i)).append(",");
                }
            }
            return sb.toString();
        }

        public static TreeNode deserialize(String data) {
            if (data == null) {
                return null;
            }
            Queue<String> dataQueue = new LinkedList<>(Arrays.asList(data.split(",")));
            String rootVal = dataQueue.poll();
            TreeNode root = ("null".equals(rootVal) || rootVal == null) ? null : new TreeNode(Integer.parseInt(rootVal));

            Queue<TreeNode> queue = new LinkedList<>();
            if (root != null) {
                queue.add(root);
            }

            while (!queue.isEmpty()) {
                TreeNode treeNode = queue.poll();
                String leftVal = dataQueue.poll();
                String rightVal = dataQueue.poll();
                TreeNode leftNode = ("null".equals(leftVal) || leftVal == null) ? null
                        : new TreeNode(Integer.parseInt(leftVal));
                TreeNode rightNode = ("null".equals(rightVal) || rightVal == null) ? null
                        : new TreeNode(Integer.parseInt(rightVal));
                treeNode.left = leftNode;
                treeNode.right = rightNode;
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

        TreeNode(int x) {
            val = x;
        }
    }

    /* ************************************************************************************************************** */

    public static TreeNode generateNode(String val) {
        if (val == null) {
            return null;
        }
        return new TreeNode(Integer.parseInt(val));
    }

    // for test
    public static TreeNode generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static TreeNode generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        TreeNode head = new TreeNode((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    // for test
    public static boolean isSameValueStructure(TreeNode head1, TreeNode head2) {
        if (head1 == null && head2 != null) {
            return false;
        }
        if (head1 != null && head2 == null) {
            return false;
        }
        if (head1 == null && head2 == null) {
            return true;
        }
        if (head1.val != head2.val) {
            return false;
        }
        return isSameValueStructure(head1.left, head2.left)
                && isSameValueStructure(head1.right, head2.right);
    }

    /* ************************************************************************************************************** */

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = null;

        String serialize = LevelOrder.serialize(root);

        {
            int maxLevel = 5;
            int maxValue = 100;
            int testTimes = 1000000;
            System.out.println("test begin");
            for (int i = 0; i < testTimes; i++) {
                TreeNode head = generateRandomBST(maxLevel, maxValue);
                String pre = PreOrder.serialize(head);
                String pos = PostOrder.serialize(head);
                String level = LevelOrder.serialize(head);
                TreeNode preBuild = PreOrder.deserialize(pre);
                TreeNode posBuild = PostOrder.deserialize(pos);
                TreeNode levelBuild = LevelOrder.deserialize(level);
                if (!isSameValueStructure(preBuild, posBuild)
                        || !isSameValueStructure(posBuild, levelBuild)) {
                    System.out.println("Oops!");
                }
            }
            System.out.println("test finish!");
        }
    }
}
