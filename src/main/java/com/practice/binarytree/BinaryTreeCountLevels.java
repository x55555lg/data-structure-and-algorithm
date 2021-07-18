package com.practice.binarytree;

/**
 * @author Xulg
 * Created in 2021-05-08 14:01
 */
class BinaryTreeCountLevels {

    public static int countLevels(Node root) {
        if (root == null) {
            return 0;
        }
        int left = countLevels(root.left);
        int right = countLevels(root.right);
        return Math.max(left, right) + 1;
    }

    private static class Node {
        int val;
        Node left, right;
    }
}
