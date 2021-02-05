package com.lg.datastructure.binarytree.a1.practice;

/**
 * 查找后继|前驱节点
 *
 * @author Xulg
 * Created in 2021-01-25 16:54
 */
class FindSuccessorAndPrecursorNode {

    /*
     * 二叉树的结构定义如下：
     * class Node {
     *      V value;
     *      Node left;
     *      Node right;
     *      Node parent;
     * }
     * 1.给你一个二叉树中的某个节点，返回该节点的后继节点？
     * 2.给你一个二叉树中的某个节点，返回该节点的前驱节点？
     *
     * 什么是后继节点、前驱节点：
     *  二叉树按照中序遍历后，节点的后面的这个就是节点就是后继节点，
     *  节点的前面的这个就是节点就是前驱节点
     *		           ①
     *	            ╱     ╲
     *	         ╱	          ╲
     *         ②	             ③
     *      ╱	  ╲	       ╱  ╲
     *   ╱	         ╲     ╱	      ╲
     * ④			  ⑤  ⑥		    ⑦
     * 中序遍历结果：4   2   5   1   6   3   7
     * 后继：
     *  节点4的后继节点是2
     *  节点5的后继节点是1
     *  节点2的后继节点是5
     *  节点7的后继节点是null
     * 前驱：
     *  节点4的前驱节点是null
     *  节点5的前驱节点是2
     *  节点2的前驱节点是4
     *  节点7的前驱节点是3
     *-----------------------------------------------------------------
     * 查找后继节点的思路：
     *      发现规律，对于任意的节点X：
     *              1.如果X节点有右子节点，那么从右子节点开始的最左子节点就是X节点的后继节点。
     *              2.如果X节点没有右子节点，那么查看X节点是左子节点还是右子节点。
     *                如果X是左子节点，那么X的父节点就是X节点的后继节点；
     *                如果X是右子节点，那么查看X的父节点是左子节点还是右子节点，循环一直找到节点是作为左节点为止。
     * 查找前驱节点的思路：
     *      发现规律，对于任意的节点X：
     *              1.如果X节点有左子节点，那么从左子节点开始的最右子节点就是X节点的前驱节点。
     *              2.如果X节点没有左子节点，那么查看X节点是左子节点还是右子节点。
     *                如果X是右子节点，那么X的父节点就是X节点的前驱节点；
     *                如果X是左子节点，那么查看X的父节点是左子节点还是右子节点，循环一直找到节点是作为右节点为止。
     */

    /**
     * 查找节点的前驱节点
     */
    public static Node findPrecursorNode(Node node) {
        if (node == null) {
            return null;
        }
        if (node.left == null) {
            // X节点是根节点，又没有左子节点，那么就没有前驱节点
            if (node.parent == null) {
                return null;
            }
            if (node.parent.right == node) {
                // X节点是右子节点，那么X的父节点就是X节点的前驱节点
                return node.parent;
            } else {
                // X是左子节点，那么查看X的父节点是左子节点还是右子节点，循环一直找到节点是作为右节点为止
                Node temp = node.parent;
                while (true) {
                    if (temp == temp.parent.right) {
                        return temp.parent;
                    }
                }
            }
        } else {
            // TODO: 2021/1/27 ......
            return null;
        }
    }

    /**
     * 查找节点的后继节点
     */
    public static void findSuccessorNode(Node node) {
        if (node == null) {
            return;
        }
    }

    private static class Node {
        int value;
        Node left;
        Node right;
        Node parent;

        Node(int value) {
            this.value = value;
        }
    }
}
