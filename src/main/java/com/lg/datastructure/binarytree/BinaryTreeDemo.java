package com.lg.datastructure.binarytree;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 二叉树
 *
 * @author Xulg
 * Created in 2020-10-28 19:04
 */
@SuppressWarnings("AlibabaRemoveCommentedCode")
class BinaryTreeDemo {

    /*
     *二叉树结构：
     *  class Node<V> {
     *      private V val;
     *      private Node<V> left;
     *      private Node<V> right;
     *  }
     *
     *完全二叉树
     * 最后一层的叶子结点都连续靠左排列，最后一层的子节点得连续，不能断开
     * 除了最后一层，其他层的节点个数达到最大
     *
     *满二叉树
     * 除了叶子节点，每个节点都有左右子节点，这种二叉树为满二叉树
     * 满二叉树有如下特性：
     *      二叉树高度为H，节点数量为N，2^H -1 = N
     *
     *平衡二叉树
     * 左子树是平衡的
     * 右子树是平衡的
     * 左右子树的高度相差不超过1
     *
     *二叉搜索树(BinarySearchTree，BST)
     * 对于任意节点X都要满足：
     *      X节点的值都要大于其左子树中任意节点的值
     *      X节点的值都要小于右子树中任意节点的值
     *
     *二叉树的子树
     * 从某个节点作为头节点开始一直到树的末端，就算一颗子树
     *		            ①
     *	            ╱     ╲
     *	         ╱	          ╲
     *         ②	            ③
     *      ╱	  ╲
     *   ╱	         ╲
     * ④			  ⑤
     * 子树：④    ⑤    ③
     * 子树：②④⑤
     * 子树：①②③④⑤
     * 总共有5颗子树
     *
     *二叉树的遍历
     *		            ①
     *	            ╱     ╲
     *	         ╱	          ╲
     *         ②	             ③
     *      ╱	  ╲	       ╱  ╲
     *   ╱	         ╲     ╱	      ╲
     * ④			  ⑤  ⑥		    ⑦
     * 先序遍历
     *      任何子树的遍历顺序都是：先头节点，再左节点，最后右节点
     *       头 左 右
     *       遍历结果：1   2   4   5   3   6   7
     * 中序遍历
     *      任何子树的遍历顺序都是：先左节点，再头节点，最后右节点
     *       左 头 右
     *       遍历结果：4   2   5   1   6   3   7
     * 后序遍历
     *      任何子树的遍历顺序都是：先左节点，再右节点，最后头节点
     *       左 右 头
     *       遍历结果：4   5   2   6   7   3   1
     *
     *递归方式遍历二叉树
     *  void loopByRecurse(Node tree) {
     *      if (tree == null) {
     *          return;
     *      }
     *      // 先序：在这里处理节点的值就是先序
     *      loopByRecurse(tree.left);
     *      // 中序：在这里处理节点的值就是中序
     *      loopByRecurse(tree.right);
     *      // 后序：在这里处理节点的值就是后序
     *  }
     * 递归序：
     *		                ①
     *	                 ╱     ╲
     *	              ╱	       ╲
     *             ②	              ③
     *          ╱	  ╲	        ╱  ╲
     *       ╱	         ╲      ╱	       ╲
     *     ④			   ⑤  ⑥		     ⑦
     *  先序遍历，节点的经过顺序：
     *      ①②④④④②⑤⑤⑤②①③⑥⑥⑥③⑦⑦⑦③①
     *      ↑↑↑      ↑        ↑↑      ↑
     *      节点第1次出现的时候，就是可以处理打印节点值的时候
     *  中序遍历，节点的经过顺序：
     *      ①②④④④②⑤⑤⑤②①③⑥⑥⑥③⑦⑦⑦③①
     *            ↑  ↑  ↑    ↑    ↑  ↑  ↑
     *      节点第2次出现的时候，就是可以处理打印节点值的时候
     *  后序遍历，节点的经过顺序：
     *      ①②④④④②⑤⑤⑤②①③⑥⑥⑥③⑦⑦⑦③①
     *              ↑      ↑↑        ↑      ↑↑↑
     *      节点第3次出现的时候，就是可以处理打印节点值的时候
     *
     *非递归方式遍历二叉树
     *  https://www.cnblogs.com/hengzhezou/p/11027190.html
     *  使用栈遍历二叉树
     *
     *按层遍历二叉树
     *  使用队列
     *      loopByLevel();
     *  输出顺序
     *      ①②③④⑤⑥⑦
     *
     *计算二叉树层数
     *  使用hash表
     *
     *计算二叉树最大层宽(哪层节点数最多，节点数是多少)
     *  层宽：即这层有多少个节点
     *  使用hash表
     *  不使用hash表
     *
     *二叉树的序列化和反序列化
     *  序列化
     *   使用数组存储，如果子节点不存在，则存储null
     *		                ①
     *	                 ╱     ╲
     *	              ╱	       ╲
     *             ②	              ③
     *          ╱	  ╲            ╱   ╲
     *       ╱	         ╲       null    null
     *    null             ④
     *                   ╱   ╲
     *                 null   null
     *     按照先序存储
     *     [①, ②, null, ④, null, null, ③, null, null]
     *  反序列化
     *   将数组中存储的二叉树，反序列化成一颗二叉树
     * 注意：二叉树可以通过先序、后序、按层遍历的方式序列化和反序列化，
     *       但是不能使用中序实现序列化和反序列化。因为不同的两棵树，可
     *       能得到同样的中序序列。
     *                 ②        ①
     *              ╱             ╲
     *            ①                 ②
     *            a树              b树
     *          这两棵树的中序遍历结果一样，都是{null, ①, null, ②, null}
     */

    /* ************************************************************************************************************** */

    /* 递归遍历二叉树 */

    /**
     * 先序遍历二叉树
     */
    public static void preLoopByRecurse(Node root) {
        // 先序遍历二叉树
        if (root == null) {
            return;
        }
        System.out.println(root.value);
        preLoopByRecurse(root.left);
        preLoopByRecurse(root.right);
    }

    /**
     * 中序遍历二叉树
     */
    public static void inLoopByRecurse(Node root) {
        if (root == null) {
            return;
        }
        inLoopByRecurse(root.left);
        System.out.println(root.value);
        inLoopByRecurse(root.right);
    }

    /**
     * 后续遍历二叉树
     */
    public static void posLoopByRecurse(Node root) {
        if (root == null) {
            return;
        }
        posLoopByRecurse(root.left);
        posLoopByRecurse(root.right);
        System.out.println(root.value);
    }

    /* ************************************************************************************************************** */

    /* 非递归遍历二叉树 */

    /**
     * 先序遍历二叉树
     */
    public static void preLoopByStack(Node root) {
        if (root == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        // 先压入根节点
        stack.add(root);
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            System.out.println(node.value);
            // 先压入右节点
            if (node.right != null) {
                stack.add(node.right);
            }
            // 再压入右节点
            if (node.left != null) {
                stack.add(node.left);
            }
        }
    }

    /**
     * 中序遍历二叉树
     */
    public static void inLoopByStack(Node root) {
        // TODO 2020/10/30 再学几遍，没懂
        if (root == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        while (!stack.isEmpty() || root != null) {
            if (root != null) {
                stack.push(root);
                root = root.left;
            } else {
                root = stack.pop();
                System.out.println(root.value);
                root = root.right;
            }
        }
    }

    /**
     * 后序遍历二叉树
     */
    public static void posLoopByStack1(Node root) {
        if (root == null) {
            return;
        }
        Stack<Node> stack1 = new Stack<>();
        Stack<Node> stack2 = new Stack<>();
        // 先压入根节点
        stack1.push(root);
        while (!stack1.isEmpty()) {
            root = stack1.pop();
            stack2.push(root);
            if (root.left != null) {
                stack1.add(root.left);
            }
            if (root.right != null) {
                stack1.add(root.right);
            }
        }
        while (!stack2.isEmpty()) {
            System.out.println(stack2.pop().value);
        }
    }

    /**
     * 后序遍历二叉树
     */
    public static void posLoopByStack2(Node tree) {
        // TODO 2020/10/30 再学几遍，没懂
    }

    /* ************************************************************************************************************** */

    /* 按层遍历二叉树 */

    public static void loopByLevel(Node tree) {
        if (tree == null) {
            return;
        }
        Queue<Node> queue = new ArrayDeque<>();
        // 加入根节点先
        queue.add(tree);
        // 循环队列
        while (!queue.isEmpty()) {
            // 弹出队列中的第一个节点
            Node node = queue.poll();
            System.out.println(node.value);
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
    }

    /* ************************************************************************************************************** */

    /* 计算二叉树的层数 */

    @SuppressWarnings("all")
    public static int calcTreeLevelCount(Node root) {
        if (root == null) {
            return 0;
        }
        Queue<Node> queue = new ArrayDeque<>();
        // 队列加入根节点
        queue.add(root);
        // 当前所在的层
        int currentLevel = 1;
        // 层数
        int levelCount = 1;
        // 记录每个节点在哪层，key为节点，value为节点所在的层
        HashMap<Node, Integer> nodeLevelMap = new HashMap<>();
        // 根节点所在的层是1层
        nodeLevelMap.put(root, currentLevel);
        while (!queue.isEmpty()) {
            // 弹出队列中的第一个节点
            Node node = queue.poll();
            // 获取这个节点在哪层啊
            Integer nodeLevel = nodeLevelMap.get(node);

            // 如果节点所在的层和当前层一样，啥也不做
            if (nodeLevel == currentLevel) {
                // 节点所在层和当前层是一样的
            }
            // 节点在的层和当前层不一样了，说明节点是下一层的了，当前层要加1了
            else {
                // 下一层了
                currentLevel++;
                // 层数加1
                levelCount++;
            }
            System.out.println(node.value + "在第" + nodeLevel + "层");

            if (node.left != null) {
                queue.add(node.left);
                // 子节点在哪层 = 父节点层 + 1;
                nodeLevelMap.put(node.left, nodeLevel + 1);
            }
            if (node.right != null) {
                queue.add(node.right);
                // 子节点在哪层 = 父节点层 + 1;
                nodeLevelMap.put(node.right, nodeLevel + 1);
            }
        }
        return levelCount;
    }

    /* ************************************************************************************************************** */

    /* 计算二叉树层中节点数的最大值 */

    @SuppressWarnings("all")
    public static int calcMaxLevelWidth(Node root) {
        if (root == null) {
            return 0;
        }

        Queue<Node> queue = new ArrayDeque<>();
        // 队列加入根节点
        queue.add(root);

        // 当前在统计哪层
        int currentLevel = 1;
        // 当前统计层的宽度
        int currentLevelWidth = 0;
        // 层宽的最大值
        int maxLevelWidth = currentLevelWidth;

        // 记录每个节点在哪层，key为节点，value为节点所在的层
        HashMap<Node, Integer> nodeLevelMap = new HashMap<>();
        // 根节点所在的层是第1层
        nodeLevelMap.put(root, currentLevel);

        while (!queue.isEmpty()) {
            // 弹出队列的第一个节点
            Node node = queue.poll();
            // 节点在哪层啊
            Integer nodeLevel = nodeLevelMap.get(node);

            // 如果节点所在层和当前层一样，那么这层的层宽加1，否则就是下一层了
            if (nodeLevel == currentLevel) {
                // 这层的节点数加1
                currentLevelWidth++;
            } else {
                // 当前层宽度和之前的最大值比较，谁大留谁
                maxLevelWidth = Math.max(maxLevelWidth, currentLevelWidth);
                // 下一层了
                currentLevel++;
                // 新的一层了啊，新层的层宽需要初始化啊
                currentLevelWidth = 1;
            }

            if (node.left != null) {
                queue.add(node.left);
                nodeLevelMap.put(node.left, nodeLevel + 1);
            }
            if (node.right != null) {
                queue.add(node.right);
                nodeLevelMap.put(node.right, nodeLevel + 1);
            }
        }
        // 最后一层的层宽还没参与过比较
        maxLevelWidth = Math.max(maxLevelWidth, currentLevelWidth);
        return maxLevelWidth;
    }

    /* ************************************************************************************************************** */

    /**
     * 使用先序将二叉树序列化，用数组的形式存储
     */
    public static Queue<String> serializeTreeByPreLoop(Node treeRoot) {
        Queue<String> serializeQueue = new LinkedList<>();
        // 先序遍历树
        Stack<Node> stack = new Stack<>();
        stack.add(treeRoot);
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            serializeQueue.add(node == null ? null : String.valueOf(node.value));
            if (node != null) {
                stack.add(node.right);
                stack.add(node.left);
            }
        }
        System.out.println(Arrays.toString(serializeQueue.toArray()));
        return serializeQueue;
    }

    /**
     * 反序列化二叉树，将数组反序列化为二叉树的形式
     */
    public static Node deserializeTree(Queue<String> serializeQueue) {
        // 按照先序反序列化成二叉树
        if (serializeQueue == null || serializeQueue.size() == 0) {
            return null;
        }
        // 第一个节点的值
        String val = serializeQueue.poll();
        if (val == null) {
            // 根节点就是null啊
            return null;
        }
        // 二叉树的根节点
        Node root = new Node(Integer.parseInt(val));
        Node node = root;
        while (!serializeQueue.isEmpty()) {
            String value = serializeQueue.poll();
            node.left = new Node(Integer.parseInt(value));

            node = node.left;
        }
        return root;
    }

    /* ************************************************************************************************************** */
    /* ************************************************************************************************************** */
    /* ************************************************************************************************************** */

    private static class Node {
        private final Integer value;
        private Node left;
        private Node right;

        Node(Integer value) {
            this.value = value;
        }
    }

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

        inLoopByStack(tree);

        System.out.println(calcTreeLevelCount(tree));
        System.out.println(calcMaxLevelWidth(tree));

        /*
         *		                ①
         *	                 ╱     ╲
         *	              ╱	       ╲
         *             ②	              ③
         *          ╱	  ╲            ╱   ╲
         *       ╱	         ╲       null    null
         *    null             ④
         *                   ╱   ╲
         *                 null   null
         */

        Node tree2 = new Node(1);
        tree2.left = node2;
        tree2.right = node3;
        node2.left = null;
        node2.right = node4;
        node3.left = null;
        node3.right = null;
        node4.left = null;
        node4.right = null;
        Queue<String> serializeData = serializeTreeByPreLoop(tree2);
    }
}
