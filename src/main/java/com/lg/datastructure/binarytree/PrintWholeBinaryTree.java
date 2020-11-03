package com.lg.datastructure.binarytree;

import cn.hutool.core.util.StrUtil;

/**
 * 打印整个二叉树
 *
 * @author Xulg
 * Created in 2020-11-02 9:24
 */
class PrintWholeBinaryTree {

    /*
     * 如何设计一个打印整颗二叉树的打印函数
     * 二叉树如下：
     *		            ①
     *	            ╱     ╲
     *	         ╱	          ╲
     *         ②	             ③
     *      ╱	  ╲	       ╱  ╲
     *   ╱	         ╲     ╱	      ╲
     * ④			  ⑤  ⑥		    ⑦
     * 打印：
     *                            ↓7777↓
     *                  ↓3333
     *                            ↑6666↑
     *          H1111H
     *                            ↓5555↓
     *                  ↑2222↑
     *                            ↑4444↑
     *=======================================
     * 思路：
     *      打印顺序：右中左，即中序遍历的逆序
     */

    /**
     * 打印整颗二叉树
     * 注：打印出来的结果，顺时针旋转90°就是树的形状
     */
    public static void printTree(Node treeRoot) {
        if (treeRoot == null) {
            return;
        }
        System.out.println("Binary Tree:");
        doPrint(treeRoot, 0, "H", 17);
    }

    private static void doPrint(Node node, int nodeLevel, String mark, int length) {
        if (node == null) {
            return;
        }
        doPrint(node.right, nodeLevel + 1, "↓", 17);

        // 打印当前节点
        String value = mark + node.value + mark;
        // 前面补充点空格
        String content = StrUtil.repeat(" ", length * nodeLevel) + value;
        System.out.println(content);

        doPrint(node.left, nodeLevel + 1, "↑", 17);
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

    /* ************************************************************************************************************** */

    public static void main(String[] args) {
        Node tree = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        Node node7 = new Node(7);
        tree.left = node2;
        tree.right = node3;
        node2.left = node4;
        node2.right = node5;
        node3.left = node6;
        node3.right = node7;

        printTree(tree);
    }
}
