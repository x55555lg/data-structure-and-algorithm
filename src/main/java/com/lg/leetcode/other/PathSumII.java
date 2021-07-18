package com.lg.leetcode.other;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Xulg
 * Description: leetcode_113
 * Created in 2021-05-28 13:49
 */
class PathSumII {

    /*
     *  Given the root of a binary tree and an integer targetSum, return
     * all root-to-leaf paths where each path's sum equals targetSum.
     *  A leaf is a node with no children.
     *
     * Example 1:
     * Input: root = [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22
     * Output: [[5,4,11,2],[5,8,4,5]]
     *
     * Example 2:
     * Input: root = [1,2,3], targetSum = 5
     * Output: []
     *
     * Example 3:
     * Input: root = [1,2], targetSum = 0
     * Output: []
     *
     * Constraints:
     * The number of nodes in the tree is in the range [0, 5000].
     * -1000 <= Node.val <= 1000
     * -1000 <= targetSum <= 1000
     */

    public static List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        return DFS2.pathSum(root, targetSum);
    }

    /**
     * DFS深度优先遍历
     */
    private static class DFS1 {
        public static List<List<Integer>> pathSum(TreeNode root, int targetSum) {
            List<List<Integer>> result = new ArrayList<>();
            ArrayList<Integer> path = new ArrayList<>();
            recurse(root, path, 0, result, targetSum);
            return result;
        }

        private static void recurse(TreeNode node, ArrayList<Integer> path, int pathSum,
                                    List<List<Integer>> result, int targetSum) {
            if (node == null) {
                return;
            }
            // base case
            if (node.left == null && node.right == null) {
                // 节点node已经是叶子节点了，DFS遍历到最底部了
                if (pathSum + node.val == targetSum) {
                    ArrayList<Integer> newPath = new ArrayList<>(path);
                    newPath.add(node.val);
                    result.add(newPath);
                }
                return;
            }

            ArrayList<Integer> newPath = new ArrayList<>(path);
            newPath.add(node.val);

            // 向左
            recurse(node.left, newPath, pathSum + node.val, result, targetSum);

            // 向右
            recurse(node.right, newPath, pathSum + node.val, result, targetSum);
        }
    }

    /**
     * DFS深度优先遍历 + 回溯算法 + 使用LinkedList来存储path信息
     */
    private static class DFS2 {
        public static List<List<Integer>> pathSum(TreeNode root, int targetSum) {
            List<List<Integer>> result = new ArrayList<>();
            LinkedList<Integer> path = new LinkedList<>();
            recurse(root, path, 0, result, targetSum);
            return result;
        }

        private static void recurse(TreeNode node, LinkedList<Integer> path, int pathSum,
                                    List<List<Integer>> result, int targetSum) {
            if (node == null) {
                return;
            }

            // 路径中加入当前节点的值
            path.add(node.val);
            // 路径累加和
            pathSum += node.val;

            // base case 节点node已经是叶子节点了，DFS遍历到最底部了
            if (node.left == null && node.right == null) {
                if (pathSum == targetSum) {
                    // 符合目标和，加入结果集
                    result.add(new ArrayList<>(path));
                }
            } else {
                // 向左
                recurse(node.left, path, pathSum, result, targetSum);
                // 向右
                recurse(node.right, path, pathSum, result, targetSum);
            }
            // 回溯，移除最后一个元素
            path.removeLast();
        }
    }

    private static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val) { this.val = val; }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(4);
        root.right = new TreeNode(8);
        root.left.left = new TreeNode(11);
        root.left.left.left = new TreeNode(7);
        root.left.left.right = new TreeNode(2);
        root.right.left = new TreeNode(13);
        root.right.right = new TreeNode(4);
        root.right.right.left = new TreeNode(5);
        root.right.right.right = new TreeNode(1);
        List<List<Integer>> list = DFS1.pathSum(root, 22);
        System.out.println(list);
        list = DFS2.pathSum(root, 22);
        System.out.println(list);
    }
}
