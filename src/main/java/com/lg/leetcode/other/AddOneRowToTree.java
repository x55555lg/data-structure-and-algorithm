package com.lg.leetcode.other;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Xulg
 * Description: leetcode_623
 * Created in 2021-05-30 21:50
 */
@SuppressWarnings("MapOrSetKeyShouldOverrideHashCodeEquals")
class AddOneRowToTree {

    /*
     *  Given the root of a binary tree and two integers val and depth,
     * add a row of nodes with value val at the given depth depth.
     *  Note that the root node is at depth 1.
     *  The adding rule is:
     *       Given the integer depth, for each not null tree node cur at
     *      the depth depth - 1, create two tree nodes with value val as
     *      cur's left subtree root and right subtree root. cur's original
     *      left subtree should be the left subtree of the new left subtree
     *      root. cur's original right subtree should be the right subtree of
     *      the new right subtree root.
     *       If depth == 1 that means there is no depth depth - 1 at all, then
     *      create a tree node with value val as the new root of the whole original
     *      tree, and the original tree is the new root's left subtree.
     *
     * Example 1:
     * Input: root = [4,2,6,3,1,5], val = 1, depth = 2
     * Output: [4,1,1,2,null,null,6,3,1,5]
     *
     * Example 2:
     * Input: root = [4,2,null,3,1], val = 1, depth = 3
     * Output: [4,2,null,1,1,3,null,null,1]
     *
     * Constraints:
     * The number of nodes in the tree is in the range [1, 104].
     * The depth of the tree is in the range [1, 104].
     * -100 <= Node.val <= 100
     * -105 <= val <= 105
     * 1 <= depth <= the depth of tree + 1
     *------------------------------------------------------------------------------------------------------------------
     * 在depth层插入
     */

    public static TreeNode addOneRow(TreeNode root, int val, int depth) {
        return LevelOrder.addOneRow(root, val, depth);
    }

    private static class LevelOrder {
        public static TreeNode addOneRow(TreeNode root, int val, int depth) {
            if (root == null || depth < 1) {
                return null;
            }
            if (depth == 1) {
                TreeNode newRoot = new TreeNode(val);
                newRoot.left = root;
                return newRoot;
            } else {
                List<TreeNode> insertPositions = new ArrayList<>();
                int currentDepth = 1;
                HashMap<TreeNode, Integer> depthMap = new HashMap<>();
                depthMap.put(root, 1);
                Queue<TreeNode> queue = new LinkedList<>();
                queue.add(root);
                while (!queue.isEmpty()) {
                    TreeNode node = queue.poll();
                    // 节点所在层
                    int nodeDepth = depthMap.get(node);
                    if (nodeDepth != currentDepth) {
                        // 下一层了已经
                        currentDepth++;
                    }
                    if (currentDepth >= depth) {
                        // it is over
                        break;
                    }
                    // 是目标层，在这层插入节点
                    if (currentDepth == depth - 1) {
                        insertPositions.add(node);
                    }
                    if (node.left != null) {
                        queue.add(node.left);
                        depthMap.put(node.left, nodeDepth + 1);
                    }
                    if (node.right != null) {
                        queue.add(node.right);
                        depthMap.put(node.right, nodeDepth + 1);
                    }
                }
                for (TreeNode node : insertPositions) {
                    TreeNode oldLeft = node.left;
                    node.left = new TreeNode(val);
                    node.left.left = oldLeft;
                    TreeNode oldRight = node.right;
                    node.right = new TreeNode(val);
                    node.right.right = oldRight;
                }
                return root;
            }
        }
    }

    private static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val) { this.val = val; }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(1);
        root.right = new TreeNode(6);
        root.right.left = new TreeNode(5);
        TreeNode treeNode = LevelOrder.addOneRow(root, 1, 2);
        System.out.println(treeNode);

        root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        treeNode = LevelOrder.addOneRow(root, 5, 4);
        System.out.println(treeNode);

        root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(1);
        root.right = new TreeNode(6);
        root.right.left = new TreeNode(5);
        treeNode = LevelOrder.addOneRow(root, 1, 1);
        System.out.println(treeNode);
    }
}
