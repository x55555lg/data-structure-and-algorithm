package com.lg.datastructure.binarytree.a1.practice.dp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Xulg
 * Created in 2021-01-25 18:00
 */
class GetCommonAncestor {

    /*
     * 给定一颗二叉树的头节点head，和另外两个节点a和b，a和b节点一定在二叉树上，请返回a和b的最低公共祖先？
     * 公共祖先：a，b节点向上走，第一个相遇的点就是最低公共祖先。
     */

    public static Node getCommonAncestor(Node root, Node a, Node b) {
        if (root == null) {
            return null;
        }
        return recurse(root, a, b).commonAncestor;
    }

    private static Data recurse(Node subTreeRoot, Node a, Node b) {
        if (subTreeRoot == null) {
            return new Data(false, false, null);
        }

        Data leftData = recurse(subTreeRoot.left, a, b);
        Data rightData = recurse(subTreeRoot.right, a, b);

        // 是否找到了a节点
        boolean findA = leftData.findA || rightData.findA || subTreeRoot == a;
        // 是否找到了b节点
        boolean findB = leftData.findB || rightData.findB || subTreeRoot == b;

        /*
         *在当前树上，a，b节点的最低公共祖先节点
         * (1).节点X不是a，b节点，即X节点既不是a节点，也不是b节点
         *      a，b节点在同一颗子树上面
         *          子树的头节点就是最低的公共祖先
         *      a，b节点在分别在左右子树上面
         *          节点X就是最低的公共祖先
         * (2).节点X是a，b节点中的一个，即X节点可能是a节点，也可能是b节点
         *      节点X就是最低的公共祖先
         * (3).节点X即是a节点，又是b节点，即X，a，b是同一个节点
         *      节点X就是最低的公共祖先
         */

        // 公共祖先
        Node commonAncestor;

        // 当前树的根节点不是a，b节点
        if (subTreeRoot != a && subTreeRoot != b) {
            if (leftData.findA && leftData.findB) {
                // a，b节点都在左子树上，那么公共祖先就是左子树的公共祖先
                commonAncestor = leftData.commonAncestor;
            } else if (rightData.findA && rightData.findB) {
                // a，b节点都在右子树上，那么公共祖先就是右子树的公共祖先
                commonAncestor = rightData.commonAncestor;
            } else {
                // a，b节点分别在左，右子树上，那么公共祖先就是当前树的root节点
                commonAncestor = subTreeRoot;
            }
        }
        // 当前树的根节点是a节点
        else if (subTreeRoot == a && subTreeRoot != b) {
            commonAncestor = subTreeRoot;
        }
        // 当前树的根节点是b节点
        else if (subTreeRoot != a && subTreeRoot == b) {
            commonAncestor = subTreeRoot;
        }
        // 当前树的根节点既是a节点又是b节点
        else if (subTreeRoot == a && subTreeRoot == b) {
            commonAncestor = subTreeRoot;
        }
        // 没有情况了
        else {
            commonAncestor = null;
        }

        // 当前树的信息
        return new Data(findA, findB, commonAncestor);
    }

    private static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
        }
    }

    private static class Data {
        // 树上是否有节点a
        boolean findA;
        // 树上是否有节点b
        boolean findB;
        // 公共祖先
        Node commonAncestor;

        Data(boolean findA, boolean findB, Node commonAncestor) {
            this.findA = findA;
            this.findB = findB;
            this.commonAncestor = commonAncestor;
        }
    }

    /* ************************************************************************************************************** */

    /* 对数器 */

    public static Node lowestAncestor(Node head, Node o1, Node o2) {
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

    @SuppressWarnings("all")
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    @SuppressWarnings("all")
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
            Node o1 = pickRandomOne(head);
            Node o2 = pickRandomOne(head);
            if (lowestAncestor(head, o1, o2) != getCommonAncestor(head, o1, o2)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
