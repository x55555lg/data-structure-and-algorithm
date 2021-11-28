package com.practice2.binarytree.dp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Xulg
 * @since 2021-10-17 19:37
 */
@SuppressWarnings("MapOrSetKeyShouldOverrideHashCodeEquals")
public class GetCommonAncestor {

    /*
     * 给定一颗二叉树的头节点head，和另外两个节点a和b，a和b节点一定在
     * 二叉树上，请返回a和b的最低公共祖先？
     * 公共祖先：a，b节点向上走，第一个相遇的点就是最低公共祖先。
     *---------------------------------------------------------------
     *
     *方法一
     *  1.记录二叉树每个节点对应的父节点，使用hash表记录
     *  2.从a节点开始往上，记录所有遇到的父节点，使用HashSet记录
     *  3.从b节点开始往上，判断遇到的父节点有没有在a的父节点集合汇总出现过，
     *    如果出现了，那么那个节点就是最低的公共祖先节点
     */

    private static class Simple {
        public static Node findCommonLowestAncestor(Node root, Node a, Node b) {
            if (root == null) {
                return null;
            }
            HashMap<Node, Node> parentIndexMap = new HashMap<>(16);
            parentIndexMap.put(root, null);
            fillParentByPreOrderLoop(root, parentIndexMap);

            // 从a节点开始往上，记录所有遇到的父节点，使用HashSet记录
            HashSet<Node> aNodeParentPath = new HashSet<>();
            Node parentA = a;
            while (parentA != null) {
                aNodeParentPath.add(parentA);
                parentA = parentIndexMap.get(parentA);
            }

            // 从b节点开始往上，判断遇到的父节点有没有在a的父节点集合汇总出现过，
            // 如果出现了，那么那个节点就是最低的公共祖先节点
            Node parentB = b;
            while (parentB != null) {
                if (aNodeParentPath.contains(parentB)) {
                    return parentB;
                }
                parentB = parentIndexMap.get(parentB);
            }
            return null;
        }

        private static void fillParentByPreOrderLoop(Node node, HashMap<Node, Node> parentIndexMap) {
            if (node == null) {
                return;
            }
            if (node.left != null) {
                parentIndexMap.put(node.left, node);
            }
            if (node.right != null) {
                parentIndexMap.put(node.right, node);
            }
            fillParentByPreOrderLoop(node.left, parentIndexMap);
            fillParentByPreOrderLoop(node.right, parentIndexMap);
        }
    }

    private static class DP {
        public static Node findCommonLowestAncestor(Node root, Node a, Node b) {
            return recurse(root, a, b).commonLowestAncestor;
        }

        private static Data recurse(Node node, Node a, Node b) {
            if (node == null) {
                return new Data(false, false, null);
            }
            Data leftData = recurse(node.left, a, b);
            Data rightData = recurse(node.right, a, b);
            boolean findA = leftData.findA || rightData.findA || node == a;
            boolean findB = leftData.findB || rightData.findB || node == b;
            Node commonLowestAncestor;
            if (node == a || node == b) {
                commonLowestAncestor = node;
            } else if (a == b) {
                commonLowestAncestor = a;
            } else if (leftData.findA && rightData.findB) {
                commonLowestAncestor = node;
            } else if (leftData.findB && rightData.findA) {
                commonLowestAncestor = node;
            } else if (leftData.findA && leftData.findB) {
                commonLowestAncestor = leftData.commonLowestAncestor;
            } else if (rightData.findA && rightData.findB) {
                commonLowestAncestor = rightData.commonLowestAncestor;
            } else {
                commonLowestAncestor = null;
            }
            return new Data(findA, findB, commonLowestAncestor);
        }

        private static class Data {
            private boolean findA;
            private boolean findB;
            private Node commonLowestAncestor;

            Data(boolean findA, boolean findB, Node commonLowestAncestor) {
                this.findA = findA;
                this.findB = findB;
                this.commonLowestAncestor = commonLowestAncestor;
            }
        }
    }

    private static class Node {
        private int value;
        private Node left, right;

        Node(int value) {
            this.value = value;
        }

        Node(int value, Node left, Node right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    /* ************************************************************************************************************** */

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
            Node r1 = DP.findCommonLowestAncestor(head, a, b);
            Node r2 = Simple.findCommonLowestAncestor(head, a, b);
            if (!(r0 == r1 && r1 == r2)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}
