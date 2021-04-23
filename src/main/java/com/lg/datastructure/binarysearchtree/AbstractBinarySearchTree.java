package com.lg.datastructure.binarysearchtree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 二叉搜索树
 *
 * @author Xulg
 * Created in 2021-03-15 9:09
 */
@SuppressWarnings({"ConstantConditions", "IfStatementWithIdenticalBranches"})
public abstract class AbstractBinarySearchTree {

    /**
     * 搜索树的根节点
     */
    protected Node root;

    /**
     * 二叉树的大小
     */
    protected int size;

    protected Node createNode(int value, Node parent, Node left, Node right) {
        return new Node(value, parent, left, right);
    }

    /**
     * Finds a node with concrete value. If it is not found then null is
     * returned.
     *
     * @param element Element value.
     * @return Node with value provided, or null if not found.
     */
    public Node search(int element) {
        Node node = root;
        while (node != null && node.value != null) {
            if (node.value < element) {
                node = node.left;
            } else if (node.value > element) {
                node = node.right;
            } else {
                // node.value == element;
                return node;
            }
        }
        return null;
    }

    /**
     * Insert new element to tree.
     *
     * @param element Element to insert.
     */
    public Node insert(int element) {
        if (root == null) {
            root = createNode(element, null, null, null);
            size++;
            return root;
        } else {
            // 找到可以插入的位置
            Node parentNode = null;
            Node node = root;
            while (node != null) {
                parentNode = node;
                if (element < node.value) {
                    node = node.left;
                } else if (element > node.value) {
                    node = node.right;
                } else {
                    assert element == node.value;
                    // 值相等了，进行替换操作
                    node.value = element;
                    return node;
                }
            }
            // 创建新节点
            Node insertedNode = createNode(element, parentNode, null, null);
            if (parentNode.value > element) {
                parentNode.left = insertedNode;
            } else {
                // parentNode.value < element
                parentNode.right = insertedNode;
            }
            size++;
            return insertedNode;
        }
    }

    /**
     * Removes element if node with such value exists.
     *
     * @param element Element value to remove.
     * @return New node that is in place of deleted node. Or null if element for
     * delete was not found.
     */
    public Node delete(int element) {
        // 查询到待删除的节点
        Node deleteNode = search(element);
        if (deleteNode == null) {
            return null;
        }
        return this.delete(deleteNode);
    }

    /**
     * 删除给定的节点
     *
     * @param deleteNode the delete node
     * @return the replace node
     */
    protected Node delete(Node deleteNode) {
        if (deleteNode == null) {
            return null;
        }
        Node replaceNode;
        if (deleteNode.left == null && deleteNode.right == null) {
            // 被删除节点没有子节点，可以直接删除
            replaceNode = transplant(deleteNode, null);
        } else if (deleteNode.left == null && deleteNode.right != null) {
            // 被删除节点只有右孩子，用右孩子去替换删除节点的环境
            replaceNode = transplant(deleteNode, deleteNode.right);
        } else if (deleteNode.left != null && deleteNode.right == null) {
            // 被删除节点只有左孩子，用左孩子去替换删除节点的环境
            replaceNode = transplant(deleteNode, deleteNode.left);
        } else {
            // 被删除节点既有左孩子、又有右孩子，用删除节点的后继节点顶替该节点
            // 后继节点 = 当前节点的右子节点的最左子节点
            Node minimumNode = getMinimum(deleteNode.right);
            if (minimumNode.parent == deleteNode) {
                // 后继节点就是删除节点的右孩子
                transplant(deleteNode, minimumNode);
                // 删除节点的左孩子挂到替换节点的左孩子位置
                minimumNode.left = deleteNode.left;
                minimumNode.left.parent = minimumNode;
            } else {
                transplant(minimumNode, minimumNode.right);
                minimumNode.right = deleteNode.right;
                minimumNode.right.parent = minimumNode;

                transplant(deleteNode, minimumNode);
                // 删除节点的左孩子挂到替换节点的左孩子位置
                minimumNode.left = deleteNode.left;
                minimumNode.left.parent = minimumNode;
            }
            replaceNode = minimumNode;
        }
        size--;
        // 移除删除节点的引用
        deleteNode.left = null;
        deleteNode.right = null;
        deleteNode.parent = null;
        return replaceNode;
    }

    /**
     * 用replaceNode来替换sourceNode的环境
     *
     * @param sourceNode  the source node
     * @param replaceNode the replace node
     * @return the replace node
     */
    private Node transplant(Node sourceNode, Node replaceNode) {
        // sourceNode的父节点指向replaceNode
        if (sourceNode.parent == null) {
            root = replaceNode;
        } else if (sourceNode.parent.left == sourceNode) {
            sourceNode.parent.left = replaceNode;
        } else if (sourceNode.parent.right == sourceNode) {
            sourceNode.parent.right = replaceNode;
        }
        // deleteNode是root节点的时候，replaceNode就是null，
        // 所以需要判断是否为null
        if (replaceNode != null) {
            replaceNode.parent = sourceNode.parent;
        }
        // 返回替换节点
        return replaceNode;
    }

    protected Node getMinimum(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    protected Node getMaximum(Node node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    /* ****************************************************************************************************************/

    /**
     * 中序打印二叉树
     */
    public void printTreeInOrder() {
        if (root == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        Node node = root;
        while (!stack.isEmpty() || node != null) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else {
                Node temp = stack.pop();
                if (temp.value != null) {
                    System.out.println(temp.value);
                }
                node = temp.right;
            }
        }
    }

    /**
     * 先序打印二叉树
     */
    public void printTreePreOrder() {
        if (root == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        stack.add(root);
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            if (node.value != null) {
                System.out.println(node.value);
            }
            if (node.right != null) {
                stack.add(node.right);
            }
            if (node.left != null) {
                stack.add(node.left);
            }
        }
    }


    /**
     * 后序打印二叉树
     */
    public void printTreePostOrder() {
        if (root == null) {
            return;
        }
        Stack<Node> helper = new Stack<>();
        Stack<Node> stack = new Stack<>();
        stack.add(root);
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            helper.add(node);
            if (node.left != null) {
                stack.add(node.left);
            }
            if (node.right != null) {
                stack.add(node.right);
            }
        }
        while (!helper.isEmpty()) {
            Node node = helper.pop();
            if (node.value != null) {
                System.out.println(node.value);
            }
        }
    }

    /**
     * 层级打印二叉树
     */
    public void printTreeLevelOrder() {
        if (root == null) {
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (node.value != null) {
                System.out.println(node.value);
            }
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
    }

    /* ****************************************************************************************************************/

    public static class Node {
        public Integer value;
        public Node parent;
        public Node left;
        public Node right;

        public Node(Integer value, Node parent, Node left, Node right) {
            this.value = value;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

        /**
         * 节点是否是叶子节点
         *
         * @return is leaf node
         */
        public boolean isLeaf() {
            return left == null && right == null;
        }

        /**
         * 是否是左子节点
         */
        public boolean isLeft() {
            return parent.left == this;
        }

        /**
         * 是否是左子节点
         */
        public boolean isRight() {
            return parent.right == this;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((value == null) ? 0 : value.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            Node other = (Node) obj;
            if (value == null) {
                return other.value == null;
            } else {
                return value.equals(other.value);
            }
        }
    }

}
