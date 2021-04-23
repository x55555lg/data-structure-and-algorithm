package com.lg.datastructure.binarysearchtree;

/**
 * 自平衡的搜索二叉树
 *
 * @author Xulg
 * Created in 2021-03-15 15:42
 */
public abstract class AbstractSelfBalancedBinarySearchTree extends AbstractBinarySearchTree {

    /*
     *以a节点为中心进行右旋
     *                                a
     *                              /   \
     *                             b     t
     *                           /   \
     *                          c     s
     *                        /   \
     *                       e     d
     *
     *                                 b
     *                              /     \
     *                             c       a
     *                           /   \   /   \
     *                          e     d s     t
     */

    /**
     * 以当前节点为中心左旋
     */
    protected Node rotateLeft(Node node) {
        /*
         *以a节点为中心进行左旋
         *     r
         *      \
         *        a
         *      /   \
         *     s     b
         *         /   \
         *        t     c
         *            /   \
         *           k     w
         *---------------------
         *      r
         *        \
         *          b
         *       /     \
         *      a       c
         *    /   \   /   \
         *   s     t k     w
         */

        // 当前节点的右节点上升
        Node temp = node.right;
        temp.parent = node.parent;

        node.right = temp.left;
        if (node.right != null) {
            node.right.parent = node;
        }

        // 当前节点下降
        temp.left = node;
        node.parent = temp;

        // 当前节点的parent需要指向新的节点
        if (temp.parent == null) {
            root = temp;
        } else {
            if (temp.parent.left == node) {
                node.parent.left = temp;
            } else if (temp.parent.right == node) {
                node.parent.right = temp;
            }
        }
        return temp;
    }

    /**
     * 以当前节点为中心右旋
     */
    protected Node rotateRight(Node node) {
        /*
         *以a节点为中心进行右旋
         *              a
         *            /   \
         *           b     t
         *         /   \
         *        c     s
         *      /   \
         *     e     d
         *----------------------
         *            b
         *         /     \
         *        c       a
         *      /   \   /   \
         *     e     d s     t
         */

        Node temp = node.left;
        temp.parent = node.parent;

        node.left = temp.right;
        if (node.left != null) {
            node.left.parent = node;
        }

        temp.right = node;
        node.parent = temp;

        if (temp.parent == null) {
            root = temp;
        } else {
            if (temp.parent.left == node) {
                temp.parent.left = temp;
            } else if (temp.parent.right == node) {
                temp.parent.right = temp;
            }
        }
        return temp;
    }

}
