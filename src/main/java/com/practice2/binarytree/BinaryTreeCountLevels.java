package com.practice2.binarytree;

/**
 * @author Xulg
 * @since 2021-10-14 16:38
 * Description: 统计二叉树的层数
 */
public class BinaryTreeCountLevels {

    private static class Recurse {
        public static int countLevels(Node root) {
            if (root == null) {
                return 0;
            }
            // 左子树的层数
            int left = countLevels(root.left);
            // 右子树的层数
            int right = countLevels(root.right);
            return Math.max(left, right) + 1;
        }
    }

    private static class Node {
        private Integer value;
        private Node left, right;

        Node(Integer value) {
            this.value = value;
        }

        Node(Integer value, Node left, Node right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }
}
