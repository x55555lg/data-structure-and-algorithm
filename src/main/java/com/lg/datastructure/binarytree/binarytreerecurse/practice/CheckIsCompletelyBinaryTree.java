package com.lg.datastructure.binarytree.binarytreerecurse.practice;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 二叉树的递归套路练习
 *
 * @author Xulg
 * Created in 2020-11-05 15:54
 */
class CheckIsCompletelyBinaryTree {

    /*
     * 给定一颗二叉树的头节点head，返回这颗二叉树是不是完全二叉树？
     *-------------------------------------------------------------
     * 思路一：
     *  按照宽度遍历二叉树，进行情况判断：
     *      （1）如果节点X只有右节点，没有左节点，那么一定不是完全二叉树。
     *		                            ①
     *	                            ╱     ╲
     *	                         ╱	          ╲
     *                         ②	            ③
     *                       	  ╲
     *                   	         ╲
     *                   			   ⑤
     *      （2）如果遍历的过程中遇到了节点X的左右节点不全，进行标记，后续
     *           的节点必须是叶子节点，否则就不是完全二叉树。
     *		                            ①
     *	                            ╱     ╲
     *	                         ╱	          ╲
     *                         ②	            ③
     *                      ╱                ╱
     *                   ╱                ╱
     *                  ④               ⑤
     * 思路二：
     *      对于任意的头节点X，节点X的左子树右子树有如下的判断情况：
     *       第一种情况：X节点所在的树不是满二叉树，需要判断左右子树
     *           左子树             右子树                   什么情况
     *          满二叉树           满二叉树                 左子树高度比右子树高度大1时，X为头节点的树才是完全二叉树
     *          完全二叉树         满二叉树                 左子树高度比右子树高度大1时，X为头节点的树才是完全二叉树
     *          满二叉树           完全二叉树               左右子树高度相等时，X为头节点的树才是完全二叉树
     *          完全二叉树         完全二叉树               X为头节点的树一定不是完全二叉树
     *       第二种情况：X节点所在的树是满二叉树，判断依据是左子树是满二叉树，右子树是满二叉树，左右子树高度相等
     *      所以，必须知道子树的信息：高度，是否满二叉树，是否完全二叉树
     */

    /**
     * 判断是否是完全二叉树
     * 使用二叉树的宽度遍历
     */
    public static boolean isCompletelyBinaryTree1(Node root) {
        if (root == null) {
            return true;
        }
        // 是否出现了某个节点的左右节点不全的情况
        boolean isChildIncomplete = false;
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            // 节点X只有右节点，没有左节点，那么一定不是完全二叉树
            if (node.left == null && node.right != null) {
                return false;
            }
            // 已经出现了某个节点的左右节点不全
            if (isChildIncomplete) {
                // 这个节点必须是叶子节点
                if (node.left != null || node.right != null) {
                    return false;
                }
            }
            // 这个节点的左右节点不全了，进行标记
            if (node.left == null || node.right == null) {
                isChildIncomplete = true;
            }
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
        return true;
    }

    /**
     * 判断是否是完全二叉树
     * 使用二叉树的递归套路
     */
    public static boolean isCompletelyBinaryTree2(Node root) {
        return doCheckIsCompletelyBinaryTree(root).isCompletely;
    }

    private static Info doCheckIsCompletelyBinaryTree(Node node) {
        if (node == null) {
            return new Info(0, true, true);
        }
        // 左右子树的信息
        Info leftInfo = doCheckIsCompletelyBinaryTree(node.left);
        Info rightInfo = doCheckIsCompletelyBinaryTree(node.right);

        // 当前树的高度
        int treeHigh = Math.max(leftInfo.treeHigh, rightInfo.treeHigh) + 1;
        // 当前树是否是满二叉树 = 左子树是满二叉树 and 右子树是满二叉树 and 左右子树高度相等
        boolean isFull = leftInfo.isFull && rightInfo.isFull
                && (leftInfo.treeHigh == rightInfo.treeHigh);
        // 当前树是否是完全二叉树
        boolean isCompletely;

        if (isFull) {
            // 当前树是满二叉树了，那么就一定是完全二叉树了啊
            isCompletely = true;
        } else {
            /* 当前的树不是满二叉树了 */
            if (leftInfo.isCompletely && rightInfo.isFull) {
                // 左子树是完全二叉树，右子树是满二叉树，只有左子树高度比右子树高度大1时，X为头节点的树才是完全二叉树
                isCompletely = leftInfo.treeHigh - 1 == rightInfo.treeHigh;
            } else if (leftInfo.isFull && rightInfo.isCompletely) {
                // 左子树是满二叉树，右子树是完全二叉树，只有左右子树高度相等时，X为头节点的树才是完全二叉树
                isCompletely = leftInfo.treeHigh == rightInfo.treeHigh;
            } else if (leftInfo.isFull && rightInfo.isFull) {
                // 当前树不是满二叉树，但左右子树都是满二叉树，左子树高度比右子树高度大1时，X为头节点的树才是完全二叉树
                isCompletely = leftInfo.treeHigh - 1 == rightInfo.treeHigh;
            } else {
                // 其余情况，当前树都是不完全二叉树
                isCompletely = false;
            }
        }
        // 返回当前树的信息
        return new Info(treeHigh, isFull, isCompletely);
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
        // 树的高度
        int treeHigh;
        // 是否是满二叉树
        boolean isFull;
        // 是否是完全二叉树
        boolean isCompletely;

        Info(int treeHigh, boolean isFull, boolean isCompletely) {
            this.treeHigh = treeHigh;
            this.isFull = isFull;
            this.isCompletely = isCompletely;
        }
    }

    /* ************************************************************************************************************** */

    /* 对数器 */

    public static boolean isCBT1(Node head) {
        if (head == null) {
            return true;
        }
        LinkedList<Node> queue = new LinkedList<>();
        // 是否遇到过左右两个孩子不双全的节点
        boolean leaf = false;
        Node l = null;
        Node r = null;
        queue.add(head);
        while (!queue.isEmpty()) {
            head = queue.poll();
            l = head.left;
            r = head.right;
            if (
                // 如果遇到了不双全的节点之后，又发现当前节点不是叶节点
                    (leaf && (l != null || r != null)) || (l == null && r != null)
            ) {
                return false;
            }
            if (l != null) {
                queue.add(l);
            }
            if (r != null) {
                queue.add(r);
            }
            if (l == null || r == null) {
                leaf = true;
            }
        }
        return true;
    }

    public static boolean isCBT2(Node head) {
        if (head == null) {
            return true;
        }
        return process(head).isCompletely;
    }

    private static Info process(Node X) {
        if (X == null) {
            return new Info(0, true, true);
        }
        Info leftInfo = process(X.left);
        Info rightInfo = process(X.right);

        int height = Math.max(leftInfo.treeHigh, rightInfo.treeHigh) + 1;


        boolean isFull = leftInfo.isFull
                && rightInfo.isFull
                && leftInfo.treeHigh == rightInfo.treeHigh;

        boolean isCBT = false;
        if (isFull) {
            isCBT = true;
        } else { // 以x为头整棵树，不满
            if (leftInfo.isCompletely && rightInfo.isCompletely) {
                if (leftInfo.isCompletely
                        && rightInfo.isFull
                        && leftInfo.treeHigh == rightInfo.treeHigh + 1) {
                    isCBT = true;
                }
                if (leftInfo.isFull
                        &&
                        rightInfo.isFull
                        && leftInfo.treeHigh == rightInfo.treeHigh + 1) {
                    isCBT = true;
                }
                if (leftInfo.isFull
                        && rightInfo.isCompletely && leftInfo.treeHigh == rightInfo.treeHigh) {
                    isCBT = true;
                }
            }
        }
        return new Info(height, isFull, isCBT);
    }

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

    /* ************************************************************************************************************** */

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (isCompletelyBinaryTree2(head) != isCompletelyBinaryTree1(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
