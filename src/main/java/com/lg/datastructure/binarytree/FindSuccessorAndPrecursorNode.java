package com.lg.datastructure.binarytree;

/**
 * 查找后继|前驱节点
 *
 * @author Xulg
 * Created in 2020-11-02 10:04
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
     * 查找后继节点
     */
    public static Node findSuccessorNode(Node node) {
        if (node == null) {
            return null;
        }
        if (node.right != null) {
            // X节点有右子节点，那么从右子节点开始的最左子节点就是X节点的后继节点
            node = node.right;
            while (node.left != null) {
                node = node.left;
            }
            return node;
        } else {
            // 如果X节点没有右子节点，那么查看X节点是左子节点还是右子节点。
            Node parent = node.parent;
            while (parent != null) {
                // 如果X是左子节点，那么X的父节点就是X节点的后继节点；
                if (parent.left == node) {
                    return parent;
                } else {
                    // 如果X是右子节点，那么查看X的父节点是左子节点还是右子节点
                    node = parent;
                    parent = node.parent;
                }
            }
            return null;
        }
    }

    /**
     * 查找前驱节点
     */
    public static Node findPrecursorNode(Node node) {
        if (node == null) {
            return null;
        }
        /*
         * 1.如果X节点有左子节点，那么从左子节点开始的最右子节点就是X节点的前驱节点。
         * 2.如果X节点没有左子节点，那么查看X节点是左子节点还是右子节点。
         *   如果X是右子节点，那么X的父节点就是X节点的前驱节点；
         *   如果X是左子节点，那么查看X的父节点是左子节点还是右子节点，循环一直找到节点是作为右节点为止。
         */
        if (node.left != null) {
            // 如果X节点有左子节点，那么从左子节点开始的最右子节点就是X节点的前驱节点。
            node = node.left;
            while (node.right != null) {
                node = node.right;
            }
            return node;
        } else {
            // 如果X节点没有左子节点，那么查看X节点是左子节点还是右子节点。
            Node parent = node.parent;
            while (parent != null) {
                if (parent.left == node) {
                    // 如果X是左子节点，那么查看X的父节点是左子节点还是右子节点
                    node = parent;
                    parent = node.parent;
                } else {
                    // 如果X是右子节点，那么X的父节点就是X节点的前驱节点；
                    return parent;
                }
            }
            return null;
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

    public static void main(String[] args) {
        Node head = new Node(6);
        head.parent = null;
        head.left = new Node(3);
        head.left.parent = head;
        head.left.left = new Node(1);
        head.left.left.parent = head.left;
        head.left.left.right = new Node(2);
        head.left.left.right.parent = head.left.left;
        head.left.right = new Node(4);
        head.left.right.parent = head.left;
        head.left.right.right = new Node(5);
        head.left.right.right.parent = head.left.right;
        head.right = new Node(9);
        head.right.parent = head;
        head.right.left = new Node(8);
        head.right.left.parent = head.right;
        head.right.left.left = new Node(7);
        head.right.left.left.parent = head.right.left;
        head.right.right = new Node(10);
        head.right.right.parent = head.right;

        Node test = head.left.left;
        System.out.println(test.value + " next: " + findSuccessorNode(test).value);
        test = head.left.left.right;
        System.out.println(test.value + " next: " + findSuccessorNode(test).value);
        test = head.left;
        System.out.println(test.value + " next: " + findSuccessorNode(test).value);
        test = head.left.right;
        System.out.println(test.value + " next: " + findSuccessorNode(test).value);
        test = head.left.right.right;
        System.out.println(test.value + " next: " + findSuccessorNode(test).value);
        test = head;
        System.out.println(test.value + " next: " + findSuccessorNode(test).value);
        test = head.right.left.left;
        System.out.println(test.value + " next: " + findSuccessorNode(test).value);
        test = head.right.left;
        System.out.println(test.value + " next: " + findSuccessorNode(test).value);
        test = head.right;
        System.out.println(test.value + " next: " + findSuccessorNode(test).value);
        // 10's next is null
        test = head.right.right;
        System.out.println(test.value + " next: " + findSuccessorNode(test));
    }
}
