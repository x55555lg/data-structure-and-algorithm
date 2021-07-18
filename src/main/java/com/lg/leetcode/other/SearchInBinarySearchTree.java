package com.lg.leetcode.other;

/**
 * @author Xulg
 * Description: leetcode_700
 * Created in 2021-05-31 15:20
 */
class SearchInBinarySearchTree {
    /*
     *  You are given the root of a binary search tree (BST) and an integer val.
     * Find the node in the BST that the node's value equals val and return the
     * subtree rooted with that node. If such a node does not exist, return null.
     *
     * Example 1:
     * Input: root = [4,2,7,1,3], val = 2
     * Output: [2,1,3]
     *
     * Example 2:
     * Input: root = [4,2,7,1,3], val = 5
     * Output: []
     *
     * Constraints:
     * The number of nodes in the tree is in the range [1, 5000].
     * 1 <= Node.val <= 107
     * root is a binary search tree.
     * 1 <= val <= 107
     */

    public static TreeNode searchBST(TreeNode root, int val) {
        return Loop.searchBST(root, val);
    }

    private static class Loop {
        public static TreeNode searchBST(TreeNode root, int val) {
            if (root == null) {
                return null;
            }
            TreeNode current = root;
            while (current != null) {
                if (current.val < val) {
                    current = current.right;
                } else if (current.val > val) {
                    current = current.left;
                } else {
                    // current.val == val;
                    return current;
                }
            }
            return null;
        }
    }

    private static class Recurse {
        public static TreeNode searchBST(TreeNode root, int val) {
            return recurse(root, val);
        }

        private static TreeNode recurse(TreeNode treeNode, int val) {
            if (treeNode == null) {
                return null;
            }
            if (treeNode.val < val) {
                return recurse(treeNode.right, val);
            } else if (treeNode.val > val) {
                return recurse(treeNode.left, val);
            } else {
                // treeNode.val == val;
                return treeNode;
            }
        }
    }

    private static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val) { this.val = val; }
    }
}
