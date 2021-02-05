package com.lg.datastructure.binarytree.a1.practice.dp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Xulg
 * Created in 2021-01-25 17:59
 */
class FindTreeMaxDistance {

    /*
     * 给定一颗二叉树的头节点head，任何两个节点之间都存在距离，返回整颗二叉树的最大距离？
     * 两节点之间的距离：从节点1走到节点2需要走多少步
     * 例如如下的二叉树：
     *		            ①
     *	            ╱     ╲
     *	         ╱	          ╲
     *         ②	             ③
     *      ╱	  ╲	       ╱  ╲
     *   ╱	         ╲     ╱	      ╲
     * ④			  ⑤  ⑥		    ⑦
     * 节点④到节点⑤的距离：④ ② ⑤ 距离为3
     * 节点④到节点⑦的距离：④ ② ① ③ ⑦ 距离为5
     *------------------------------------------------------------------------------------
     * 思路：从head头节点X开始
     *  最大距离和X节点无关，最大距离 = max(左子树最大距离，右子树最大距离);
     *  最大距离和X节点有关，最大距离 = 左子树高度 + 1 + 右子树高度;
     */

    public static int getMaxDistance(Node root) {
        return recurse(root).maxDistance;
    }

    private static Data recurse(Node subTreeRoot) {
        if (subTreeRoot == null) {
            return new Data(0, 0);
        }

        Data leftData = recurse(subTreeRoot.left);
        Data rightData = recurse(subTreeRoot.right);

        // 最大距离和X节点无关，最大距离 = max(左子树最大距离，右子树最大距离);
        int distance1 = Math.max(leftData.maxDistance, rightData.maxDistance);
        // 最大距离和X节点有关，最大距离 = 左子树高度 + 1 + 右子树高度;
        int distance2 = leftData.high + rightData.high + 1;

        // 最大距离
        int maxDistance = Math.max(distance1, distance2);
        // 树高
        int high = Math.max(leftData.high, rightData.high) + 1;

        return new Data(high, maxDistance);
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
        // 树高
        int high;
        // 最大距离
        int maxDistance;

        Data(int high, int maxDistance) {
            this.high = high;
            this.maxDistance = maxDistance;
        }
    }

    /* ************************************************************************************************************** */

    /* 对数器 start */

    /**
     * 暴力方法获取最大距离
     */
    public static int getMaxDistanceForCompare(Node head) {
        if (head == null) {
            return 0;
        }
        ArrayList<Node> arr = getPreList(head);
        HashMap<Node, Node> parentMap = getParentMap(head);
        int max = 0;
        for (int i = 0; i < arr.size(); i++) {
            for (int j = i; j < arr.size(); j++) {
                max = Math.max(max, distance(parentMap, arr.get(i), arr.get(j)));
            }
        }
        return max;
    }

    private static ArrayList<Node> getPreList(Node head) {
        ArrayList<Node> arr = new ArrayList<>();
        fillPreList(head, arr);
        return arr;
    }

    private static void fillPreList(Node head, ArrayList<Node> arr) {
        if (head == null) {
            return;
        }
        arr.add(head);
        fillPreList(head.left, arr);
        fillPreList(head.right, arr);
    }

    private static HashMap<Node, Node> getParentMap(Node head) {
        HashMap<Node, Node> map = new HashMap<>();
        map.put(head, null);
        fillParentMap(head, map);
        return map;
    }

    private static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    private static void fillParentMap(Node head, HashMap<Node, Node> parentMap) {
        if (head.left != null) {
            parentMap.put(head.left, head);
            fillParentMap(head.left, parentMap);
        }
        if (head.right != null) {
            parentMap.put(head.right, head);
            fillParentMap(head.right, parentMap);
        }
    }

    private static int distance(HashMap<Node, Node> parentMap, Node o1, Node o2) {
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
        Node lowestAncestor = cur;
        cur = o1;
        int distance1 = 1;
        while (cur != lowestAncestor) {
            cur = parentMap.get(cur);
            distance1++;
        }
        cur = o2;
        int distance2 = 1;
        while (cur != lowestAncestor) {
            cur = parentMap.get(cur);
            distance2++;
        }
        return distance1 + distance2 - 1;
    }

    private static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    /* 对数器 end */

    /* ************************************************************************************************************** */

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (getMaxDistance(head) != getMaxDistanceForCompare(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
