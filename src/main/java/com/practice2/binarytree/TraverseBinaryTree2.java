package com.practice2.binarytree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * @author Xulg
 * @since 2021-11-09 11:18
 * Description: 前中后序遍历二叉树
 */
class TraverseBinaryTree2 {

    private static class PreOrderLoop {
        public static void loopByRecurse(TreeNode root) {
            if (root == null) {
                return;
            }
            System.out.println(root.value);
            loopByRecurse(root.left);
            loopByRecurse(root.right);
        }

        public static void loopByStack(TreeNode root) {
            if (root == null) {
                return;
            }
            Stack<TreeNode> stack = new Stack<>();
            stack.add(root);
            while (!stack.isEmpty()) {
                TreeNode node = stack.pop();
                System.out.println(node.value);
                if (node.right != null) {
                    stack.add(node.right);
                }
                if (node.left != null) {
                    stack.add(node.left);
                }
            }
        }
    }

    private static class InOrderLoop {
        public static void loopByRecurse(TreeNode root) {
            if (root == null) {
                return;
            }
            loopByRecurse(root.left);
            System.out.println(root.value);
            loopByRecurse(root.right);
        }

        public static void loopByStack(TreeNode root) {
            if (root == null) {
                return;
            }
            Stack<TreeNode> stack = new Stack<>();
            TreeNode node = root;
            while (!stack.isEmpty() || node != null) {
                if (node != null) {
                    stack.push(node);
                    node = node.left;
                } else {
                    TreeNode temp = stack.pop();
                    System.out.println(temp.value);
                    node = temp.right;
                }
            }
        }
    }

    private static class PostOrderLoop {
        public static void loopByRecurse(TreeNode root) {
            if (root == null) {
                return;
            }
            loopByRecurse(root.left);
            loopByRecurse(root.right);
            System.out.println(root.value);
        }

        public static void loopByStack(TreeNode root) {
            if (root == null) {
                return;
            }
            Stack<TreeNode> helper = new Stack<>();
            Stack<TreeNode> stack = new Stack<>();
            stack.add(root);
            while (!stack.isEmpty()) {
                TreeNode node = stack.pop();
                helper.add(node);
                if (node.left != null) {
                    stack.add(node.left);
                }
                if (node.right != null) {
                    stack.add(node.right);
                }
            }
            while (!helper.isEmpty()) {
                TreeNode node = helper.pop();
                System.out.println(node.value);
            }
        }
    }

    private static class LevelOrderLoop {
        public static void loop(TreeNode root) {
            if (root == null) {
                return;
            }
            Queue<TreeNode> queue = new LinkedList<>();
            queue.add(root);
            while (!queue.isEmpty()) {
                TreeNode node = queue.poll();
                System.out.println(node.value);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
        }
    }

    /**
     * 之字形遍历打印
     */
    private static class ZigzagLevelOrderLoop {
        public static List<List<Integer>> loop(TreeNode root) {
            if (root == null) {
                return null;
            }

            // 每层的节点
            HashMap<Integer, LinkedList<Integer>> result = new HashMap<>();

            // 每个节点在哪层
            HashMap<TreeNode, Integer> levelMap = new HashMap<>();
            levelMap.put(root, 1);

            // 当前在哪层
            int currentLevel = 1;

            Queue<TreeNode> queue = new LinkedList<>();
            queue.add(root);

            while (!queue.isEmpty()) {
                TreeNode treeNode = queue.poll();
                int nodeLevel = levelMap.get(treeNode);
                if (nodeLevel == currentLevel) {
                    if (!result.containsKey(currentLevel)) {
                        result.put(currentLevel, new LinkedList<>());
                    }
                    if (currentLevel % 2 == 0) {
                        // 偶数层
                        result.get(currentLevel).addFirst(treeNode.value);
                    } else {
                        // 奇数层
                        result.get(currentLevel).addLast(treeNode.value);
                    }
                } else {
                    // 新的一层的节点了
                    currentLevel++;
                    result.put(currentLevel, new LinkedList<>(Collections.singleton(treeNode.value)));
                }
                if (treeNode.left != null) {
                    queue.add(treeNode.left);
                    levelMap.put(treeNode.left, nodeLevel + 1);
                }
                if (treeNode.right != null) {
                    queue.add(treeNode.right);
                    levelMap.put(treeNode.right, nodeLevel + 1);
                }
            }
            return new ArrayList<>(result.values());
        }
    }

    private static class TreeNode {
        private int value;
        private TreeNode left, right;

        TreeNode(int value) {
            this.value = value;
        }
    }
}
