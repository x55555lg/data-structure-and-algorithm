package com.lg.leetcode.topinterview.practice1;

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
        return null;
    }

    public static TreeNode deserialize(String data) {
        return null;
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
    }
}
