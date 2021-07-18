package com.practice.binarytree.dp;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Xulg
 * Description: 检测是否是完全二叉树
 * Created in 2021-05-18 9:36
 */
class CheckIsCompletelyBinaryTree {

    /*
     * 给定一颗二叉树的头节点head，返回这颗二叉树是不是完全二叉树？
     *------------------------------------------------------------
     *完全二叉树：
     *  方式一
     *      1.节点X只有右节点，没有左节点，那么一定不是完全二叉树
     *      2.如果遍历过程中遇到了节点X的左右节点不全，进行标记，
     *        后续的节点必须是叶子节点，否则就不是完全二叉树。
     *  方式二
     */

    @SuppressWarnings("ConstantConditions")
    private static class Solution1 {
        public static boolean isCompletely(Node root) {
            if (root == null) {
                return true;
            }
            // 同一层的节点是否连续
            boolean isNotContinueInOneLevel = false;
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);
            while (!queue.isEmpty()) {
                Node node = queue.poll();
                if (node.left == null && node.right != null) {
                    // 节点X只有右节点，没有左节点，那么一定不是完全二叉树
                    return false;
                }
                if (isNotContinueInOneLevel) {
                    // 出现了不连续的情况
                    if (node.left != null || node.right != null) {
                        // 子节点不为空，那么一定不是完全二叉树
                        return false;
                    }
                }
                if (node.left == null || node.right == null) {
                    // 出现了不连续的情况
                    isNotContinueInOneLevel = true;
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
    }

    private static class Solution2 {
        public static boolean isCompletely(Node root) {
            return recurse(root).isCompletely;
        }

        private static Data recurse(Node node) {
            if (node == null) {
                return new Data(0, 0, true, true);
            }
            Data leftData = recurse(node.left);
            Data rightData = recurse(node.right);
            int height = Math.max(leftData.height, rightData.height) + 1;
            int size = leftData.size + rightData.size + 1;
            //等价写法
            boolean isFull = leftData.isFull && rightData.isFull && leftData.height == rightData.height;
            //boolean isFull = ((1 << height) - 1) == size;
            //boolean isFull = Math.pow(2, height) - 1 == size;

            // 当前节点X所在的树是否是完全二叉树
            boolean isCompletely;
            if (isFull) {
                // 当前节点X所在的树是满二叉树，那么一定是完全二叉树
                isCompletely = true;
            } else {
                /* 当前节点X所在的树不是满二叉树 */
                if (!leftData.isCompletely || !rightData.isCompletely) {
                    // 左右子树有一个不是完全二叉树，则肯定不是
                    isCompletely = false;
                } else if (leftData.isFull && rightData.isFull) {
                    // 左右子树都是满二叉树，左子树高度比右子树高度大1时
                    isCompletely = (leftData.height - rightData.height == 1);
                } else if (leftData.isFull && rightData.isCompletely) {
                    // 左子树是满二叉树，右子树是完全二叉树，必须左右子树高度相等时
                    isCompletely = leftData.height == rightData.height;
                } else if (leftData.isCompletely && rightData.isFull) {
                    // 左子树是完全二叉树，右子树是满二叉树，必须是左子树高度比右子树高度大1时
                    isCompletely = (leftData.height - rightData.height == 1);
                } else {
                    // 其余情况，当前树都是不完全二叉树
                    isCompletely = false;
                }
            }
            return new Data(height, size, isCompletely, isFull);
        }

        private static class Data {
            int height;
            int size;
            boolean isCompletely;
            boolean isFull;

            Data(int height, int size, boolean isCompletely, boolean isFull) {
                this.height = height;
                this.size = size;
                this.isCompletely = isCompletely;
                this.isFull = isFull;
            }
        }
    }

    private static class Compare1 {

        public static boolean isCompletely(Node head) {
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
    }

    private static class Compare2 {
        public static boolean isCompletely(Node head) {
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
                    if (leftInfo.isCompletely && rightInfo.isFull
                            && leftInfo.treeHigh == rightInfo.treeHigh + 1) {
                        isCBT = true;
                    }
                    if (leftInfo.isFull && rightInfo.isFull
                            && leftInfo.treeHigh == rightInfo.treeHigh + 1) {
                        isCBT = true;
                    }
                    if (leftInfo.isFull && rightInfo.isCompletely
                            && leftInfo.treeHigh == rightInfo.treeHigh) {
                        isCBT = true;
                    }
                }
            }
            return new Info(height, isFull, isCBT);
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
    }

    /* ************************************************************************************************************** */

    private static class Node {
        int val;
        Node left, right;

        Node(int val) {
            this.val = val;
        }
    }

    /* ************************************************************************************************************** */

    /* 对数器 */

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
            boolean r0 = Compare1.isCompletely(head);
            boolean r1 = Compare2.isCompletely(head);
            boolean r2 = Solution1.isCompletely(head);
            boolean r3 = Solution2.isCompletely(head);
            if (!(r0 == r1 && r1 == r2 && r2 == r3)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}
