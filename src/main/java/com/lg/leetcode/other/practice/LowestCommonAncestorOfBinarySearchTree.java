package com.lg.leetcode.other.practice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Xulg
 * Description: leetcode_235
 * Created in 2021-05-25 13:47
 */
class LowestCommonAncestorOfBinarySearchTree {

    /*
     * Given a binary search tree (BST), find the lowest common ancestor (LCA) of two given nodes in the BST.
     * According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between two
     * nodes p and q as the lowest node in T that has both p and q as descendants (where we allow a node to be
     * a descendant of itself).”
     *
     * Example 1:
     * Input: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 8
     * Output: 6
     * Explanation: The LCA of nodes 2 and 8 is 6.
     *
     * Example 2:
     * Input: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 4
     * Output: 2
     * Explanation: The LCA of nodes 2 and 4 is 2, since a node can be a descendant of itself according to the LCA definition.
     *
     * Example 3:
     * Input: root = [2,1], p = 2, q = 1
     * Output: 2
     *
     * Constraints:
     * The number of nodes in the tree is in the range [2, 105].
     * -109 <= Node.val <= 109
     * All Node.val are unique.
     * p != q
     * p and q will exist in the BST.
     */

    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        return TreeDP.lowestCommonAncestor(root, p, q);
    }

    /**
     * 通用解法
     */
    private static class TreeDP {
        public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            return recurse(root, p, q).ancestor;
        }

        private static Data recurse(TreeNode node, TreeNode p, TreeNode q) {
            if (node == null) {
                return new Data(null, false, false);
            }
            Data left = recurse(node.left, p, q);
            Data right = recurse(node.right, p, q);
            boolean findP = left.findP || right.findP || node == p;
            boolean findQ = left.findQ || right.findQ || node == q;
            TreeNode ancestor;
            if (node == p || node == q) {
                // 当前节点就是p或q
                ancestor = node;
            } else {
                if (left.findP && left.findQ) {
                    ancestor = left.ancestor;
                } else if (right.findP && right.findQ) {
                    ancestor = right.ancestor;
                } else if (left.findP && right.findQ) {
                    ancestor = node;
                } else if (left.findQ && right.findP) {
                    ancestor = node;
                } else {
                    ancestor = null;
                }
            }
            return new Data(ancestor, findP, findQ);
        }

        private static class Data {
            TreeNode ancestor;
            boolean findP, findQ;

            Data(TreeNode ancestor, boolean findP, boolean findQ) {
                this.ancestor = ancestor;
                this.findP = findP;
                this.findQ = findQ;
            }
        }
    }

    /**
     * 搜索二叉树特殊解法
     */
    private static class Best {
        public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            if (root == null) {
                return null;
            }
            if (root.val > p.val && root.val > q.val) {
                return lowestCommonAncestor(root.left, p, q);
            } else if (root.val < p.val && root.val < q.val) {
                return lowestCommonAncestor(root.right, p, q);
            } else {
                return root;
            }
        }
    }

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) { val = x; }
    }


    /* ************************************************************************************************************** */

    /* 对数器 */

    public static TreeNode lowestAncestor(TreeNode head, TreeNode o1, TreeNode o2) {
        if (head == null) {
            return null;
        }
        // key的父节点是value
        HashMap<TreeNode, TreeNode> parentMap = new HashMap<>();
        parentMap.put(head, null);
        fillParentMap2(head, parentMap);
        HashSet<TreeNode> o1Set = new HashSet<>();
        TreeNode cur = o1;
        o1Set.add(cur);
        while (parentMap.get(cur) != null) {
            cur = parentMap.get(cur);
            o1Set.add(cur);
        }
        cur = o2;
        while (!o1Set.contains(cur)) {
            cur = parentMap.get(cur);
        }
        return cur;
    }

    private static void fillParentMap2(TreeNode head, HashMap<TreeNode, TreeNode> parentMap) {
        if (head.left != null) {
            parentMap.put(head.left, head);
            fillParentMap2(head.left, parentMap);
        }
        if (head.right != null) {
            parentMap.put(head.right, head);
            fillParentMap2(head.right, parentMap);
        }
    }

    public static TreeNode generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    public static TreeNode generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        TreeNode head = new TreeNode((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static TreeNode pickRandomOne(TreeNode head) {
        if (head == null) {
            return null;
        }
        ArrayList<TreeNode> arr = new ArrayList<>();
        fillPrelist(head, arr);
        int randomIndex = (int) (Math.random() * arr.size());
        return arr.get(randomIndex);
    }

    @SuppressWarnings("all")
    public static void fillPrelist(TreeNode head, ArrayList<TreeNode> arr) {
        if (head == null) {
            return;
        }
        arr.add(head);
        fillPrelist(head.left, arr);
        fillPrelist(head.right, arr);
    }

    /* ************************************************************************************************************** */

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            TreeNode head = generateRandomBST(maxLevel, maxValue);
            TreeNode o1 = pickRandomOne(head);
            TreeNode o2 = pickRandomOne(head);
            if (lowestAncestor(head, o1, o2) != TreeDP.lowestCommonAncestor(head, o1, o2)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
