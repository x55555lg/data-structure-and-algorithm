package com.lg.datastructure.binarysearchtree.avltree;

import com.lg.datastructure.binarysearchtree.AbstractSelfBalancedBinarySearchTree;

/**
 * AVL树
 *
 * @author Xulg
 * Created in 2021-03-16 9:46
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
class AVLTree extends AbstractSelfBalancedBinarySearchTree {

    @Override
    public Node insert(int element) {
        AVLNode insertNode = (AVLNode) super.insert(element);
        // 重新平衡二叉树
        rebalance(insertNode);
        return insertNode;
    }

    @Override
    public Node delete(int element) {
        Node deleteNode = super.search(element);
        if (deleteNode == null) {
            return null;
        }
        Node replacedNode = super.delete(element);
        if (replacedNode == null) {
            // 被删除的节点没有孩子节点，直接从被删除节点的父节点开始重新平衡
            recomputeHeight((AVLNode) deleteNode.parent);
            rebalance((AVLNode) deleteNode.parent);
        } else {
            // 被删除节点有左孩子
            // 被删除节点有右孩子
            // 被删除节点有左右孩子
        }
        return replacedNode;
    }

    @Override
    protected Node createNode(int value, Node parent, Node left, Node right) {
        return new AVLNode(value, parent, left, right);
    }

    /**
     * 重新平衡二叉树
     *
     * @param node the node
     */
    private void rebalance(AVLNode node) {
        while (node != null) {
            AVLNode parent = (AVLNode) node.parent;
            // 计算parent为根节点的树的左右子树高度差
            int diff = ((AVLNode) node.right).height - ((AVLNode) node.left).height;
            if (diff == 2) {
                // 右子树比左子树高2
                // RL
                // RR

            } else if (diff == -2) {
                // 左子树比右子树高2
                // LR
                // LL

            } else {
                // 左右子树高度差小于2
                // 重新计算节点的高度
                updateHeight(node);
            }
            // 继续往上
            node = parent;
        }
    }

    private Node avlRotateLeft(AVLNode avlNode) {
        Node temp = super.rotateRight(avlNode);
        updateHeight((AVLNode) temp.left);
        updateHeight((AVLNode) temp);
        return temp;
    }

    private Node avlRotateRight(AVLNode avlNode) {
        Node temp = super.rotateRight(avlNode);
        updateHeight((AVLNode) temp.right);
        updateHeight((AVLNode) temp);
        return temp;
    }

    /**
     * 计算节点的高度
     */
    private void updateHeight(AVLNode node) {
        int leftHeight = (node.left == null) ? -1 : ((AVLNode) node.left).height;
        int rightHeight = (node.right == null) ? -1 : ((AVLNode) node.right).height;
        node.height = Math.max(leftHeight, rightHeight) + 1;
    }

    /**
     * 从当前节点开始往上，计算各个节点的树高
     */
    private void recomputeHeight(AVLNode node) {
        while (node != null) {
            node.height = maxHeight((AVLNode) node.left, (AVLNode) node.right) + 1;
            node = (AVLNode) node.parent;
        }
    }

    /**
     * 计算最大树高，如果没有则返回-1
     */
    @SuppressWarnings("ConstantConditions")
    private static int maxHeight(AVLNode node1, AVLNode node2) {
        if (node1 != null && node2 != null) {
            return Math.max(node1.height, node2.height);
        } else if (node1 == null && node2 != null) {
            return node2.height;
        } else if (node1 != null && node2 == null) {
            return node1.height;
        } else {
            assert node1 == null && node2 == null;
            return -1;
        }
    }

    private static class AVLNode extends Node {
        // 树的高度
        private int height;

        AVLNode(Integer value, Node parent, Node left, Node right) {
            super(value, parent, left, right);
        }
    }

}
