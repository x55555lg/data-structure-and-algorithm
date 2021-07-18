package com.practice.binarytree.dp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Xulg
 * Description: 查询两个节点的最低公共祖先
 * Created in 2021-05-18 11:17
 */
class GetCommonAncestor {

    /*
     * 给定一颗二叉树的头节点head，和另外两个节点a和b，a和b节点一定在
     * 二叉树上，请返回a和b的最低公共祖先？
     * 公共祖先：a，b节点向上走，第一个相遇的点就是最低公共祖先。
     *
     * 思路一
     *      1.记录二叉树每个节点对应的父节点，使用hash表记录
     *      2.从a节点开始往上，记录所有遇到的父节点，使用HashSet记录
     *      3.从b节点开始往上，判断遇到的父节点有没有在a的父节点集合汇总出现过，
     *        如果出现了，那么那个节点就是最低的公共祖先节点
     *
     * 思路二
     *      对于任意的头节点X，左右子树
     *          (1).节点X不是a，b节点，即X节点既不是a节点，也不是b节点
     *              a，b节点在同一颗子树上面
     *                  子树的头节点就是最低的公共祖先
     *              a，b节点在分别在左右子树上面
     *                  节点X就是最低的公共祖先
     *          (2).节点X是a，b节点中的一个，即X节点可能是a节点，也可能是b节点
     *              节点X就是最低的公共祖先
     *          (3).节点X即是a节点，又是b节点，即X，a，b是同一个节点
     *              节点X就是最低的公共祖先
     */

    @SuppressWarnings("MapOrSetKeyShouldOverrideHashCodeEquals")
    private static class Solution1 {
        public static Node getCommonAncestor(Node root, Node a, Node b) {
            // 记录每个节点的父节点
            HashMap<Node, Node> parentMap = new HashMap<>(16);
            parentMap.put(root, null);
            fillParent(root, parentMap);

            // 从a节点开始往上，记录所有遇到的父节点，使用HashSet记录
            HashSet<Node> parentPath = new HashSet<>();
            Node parent = a;
            while (parent != null) {
                parentPath.add(parent);
                parent = parentMap.get(parent);
            }

            // 从b节点开始往上，判断遇到的父节点有没有在a的父节点集合汇总出现过，
            // 如果出现了，那么那个节点就是最低的公共祖先节点
            parent = b;
            while (parent != null) {
                if (parentPath.contains(parent)) {
                    return parent;
                }
                parent = parentMap.get(parent);
            }

            return null;
        }

        private static void fillParent(Node node, HashMap<Node, Node> parentMap) {
            if (node == null) {
                return;
            }
            if (node.left != null) {
                parentMap.put(node.left, node);
            }
            if (node.right != null) {
                parentMap.put(node.right, node);
            }
            fillParent(node.left, parentMap);
            fillParent(node.right, parentMap);
        }
    }

    private static class Solution2 {
        public static Node getCommonAncestor(Node root, Node a, Node b) {
            if (root == null || a == null || b == null) {
                return null;
            }
            return recurse(root, a, b).ancestor;
        }

        private static Data recurse(Node node, Node a, Node b) {
            if (node == null) {
                return new Data(false, false, null);
            }
            Data leftData = recurse(node.left, a, b);
            Data rightData = recurse(node.right, a, b);
            boolean findA = leftData.findA || rightData.findA || node == a;
            boolean findB = leftData.findB || rightData.findB || node == b;
            Node ancestor;
            if (node == a || node == b) {
                ancestor = node;
            } else if (leftData.findA && leftData.findB) {
                ancestor = leftData.ancestor;
            } else if (rightData.findA && rightData.findB) {
                ancestor = rightData.ancestor;
            } else {
                ancestor = node;
            }
            return new Data(findA, findB, ancestor);
        }

        private static class Data {
            boolean findA;
            boolean findB;
            Node ancestor;

            Data(boolean findA, boolean findB, Node ancestor) {
                this.findA = findA;
                this.findB = findB;
                this.ancestor = ancestor;
            }
        }
    }

    private static class Compare {
        public static Node getCommonAncestor(Node head, Node o1, Node o2) {
            if (head == null) {
                return null;
            }
            // key的父节点是value
            HashMap<Node, Node> parentMap = new HashMap<>();
            parentMap.put(head, null);
            fillParentMap2(head, parentMap);
            HashSet<Node> o1Set = new HashSet<>();
            Node cur = o1;
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

        private static void fillParentMap2(Node head, HashMap<Node, Node> parentMap) {
            if (head.left != null) {
                parentMap.put(head.left, head);
                fillParentMap2(head.left, parentMap);
            }
            if (head.right != null) {
                parentMap.put(head.right, head);
                fillParentMap2(head.right, parentMap);
            }
        }
    }

    private static class Node {
        int val;
        Node left, right;

        Node(int val) {
            this.val = val;
        }
    }


    /* ************************************************************************************************************** */

    /* 对数器 */

    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static Node pickRandomOne(Node head) {
        if (head == null) {
            return null;
        }
        ArrayList<Node> arr = new ArrayList<>();
        fillPrelist(head, arr);
        int randomIndex = (int) (Math.random() * arr.size());
        return arr.get(randomIndex);
    }

    @SuppressWarnings("all")
    public static void fillPrelist(Node head, ArrayList<Node> arr) {
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
            Node head = generateRandomBST(maxLevel, maxValue);
            Node a = pickRandomOne(head), b = pickRandomOne(head);
            Node r0 = Compare.getCommonAncestor(head, a, b);
            Node r1 = Solution1.getCommonAncestor(head, a, b);
            Node r2 = Solution2.getCommonAncestor(head, a, b);
            if (!(r0 == r1 && r1 == r2)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
