package com.lg.datastructure.binarytree.binarytreerecurse.practice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * 二叉树的递归套路练习
 *
 * @author Xulg
 * Created in 2020-11-05 17:39
 */
@SuppressWarnings("MapOrSetKeyShouldOverrideHashCodeEquals")
class GetCommonAncestor {

    /*
     * 给定一颗二叉树的头节点head，和另外两个节点a和b，a和b节点一定在二叉树上，请返回a和b的最低公共祖先？
     * 公共祖先：a，b节点向上走，第一个相遇的点就是最低公共祖先。
     *-------------------------------------------------------------------------
     * 思路一
     *      1.记录二叉树每个节点对应的父节点，使用hash表记录
     *      2.从a节点开始往上，记录所有遇到的父节点，使用HashSet记录
     *      3.从b节点开始往上，判断遇到的父节点有没有在a的父节点集合汇总出现过，
     *        如果出现了，那么那个节点就是最低的公共祖先节点
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

    /**
     * 查找a，b节点的公共祖先
     * 验证过了是对的
     */
    public static Node getCommonAncestor1(Node root, Node a, Node b) {
        if (root == null) {
            return null;
        }
        // 记录每个节点对应的父节点
        HashMap<Node, Node> nodeParentIndexMap = new HashMap<>();
        // 根节点的父节点是null
        nodeParentIndexMap.put(root, null);
        // 记录每个节点的父节点是谁
        fillParentMap(root, nodeParentIndexMap);

        // 从a节点开始往上，记录所有遇到的父节点
        HashSet<Node> parentAs = new HashSet<>();
        Node parentA = a;
        while (parentA != null) {
            parentAs.add(parentA);
            parentA = nodeParentIndexMap.get(parentA);
        }

        // 从b节点开始往上，判断遇到的父节点有没有在a的父节点集合汇总出现过
        // 如果出现了，那么那个节点就是最低的公共祖先节点
        Node parentB = b;
        while (parentB != null) {
            if (parentAs.contains(parentB)) {
                return parentB;
            }
            parentB = nodeParentIndexMap.get(parentB);
        }
        return null;
    }

    private static void fillParentMap(Node node, HashMap<Node, Node> nodeParentIndexMap) {
        if (node == null) {
            return;
        }
        if (node.left != null) {
            nodeParentIndexMap.put(node.left, node);
        }
        if (node.right != null) {
            nodeParentIndexMap.put(node.right, node);
        }
        fillParentMap(node.left, nodeParentIndexMap);
        fillParentMap(node.right, nodeParentIndexMap);
    }

    /**
     * 查找a，b节点的公共祖先
     */
    public static Node getCommonAncestor2(Node root, Node a, Node b) {
        return doGetCommonAncestor(root, a, b).commonAncestor;
    }

    @SuppressWarnings("ConstantConditions")
    private static Info doGetCommonAncestor(Node node, Node a, Node b) {
        if (node == null) {
            return new Info(false, false, null);
        }
        Info leftInfo = doGetCommonAncestor(node.left, a, b);
        Info rightInfo = doGetCommonAncestor(node.right, a, b);
        // 当前树上是否有a节点 = 左树有a || 右树有a || 当前节点就是a
        boolean findA = leftInfo.findA || rightInfo.findA || (node == a);
        // 当前树上是否有b节点 = 左树有b || 右树有b || 当前节点就是b
        boolean findB = leftInfo.findB || rightInfo.findB || (node == b);

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
        Node commonAncestor = null;
        // 当前节点X既不是a节点，也不是b
        if (node != a && node != b) {
            // a节点，b节点都在左子树上
            if (leftInfo.findA && leftInfo.findB) {
                commonAncestor = leftInfo.commonAncestor;
            }
            // a节点，b节点都在右子树上
            if (rightInfo.findA && rightInfo.findB) {
                commonAncestor = rightInfo.commonAncestor;
            }
            // a节点，b节点分别在两颗子树上
            if (leftInfo.findA && rightInfo.findB
                    || leftInfo.findB && rightInfo.findA) {
                commonAncestor = node;
            }
        }
        // 当前节点X是z，b节点中的一个
        else if (node == a || node == b) {
            commonAncestor = node;
        }
        // 当前节点X即是a节点又是b节点
        else if (node == a && node == b) {
            commonAncestor = node;
        }
        // 没有了
        else {
            commonAncestor = null;
        }

        return new Info(findA, findB, commonAncestor);
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

    private static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
        }
    }

    private static class Info {
        // 是否包含有a节点
        boolean findA;
        // 是否包含有b节点
        boolean findB;
        // 最低公共祖先节点
        Node commonAncestor;

        Info(boolean findA, boolean findB, Node commonAncestor) {
            this.findA = findA;
            this.findB = findB;
            this.commonAncestor = commonAncestor;
        }
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
            if (lowestAncestor(head, o1, o2) != getCommonAncestor2(head, o1, o2)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
