package com.lg.datastructure.binarytree;

/**
 * 二叉树
 *
 * @author Xulg
 * Created in 2020-10-28 19:04
 */
@SuppressWarnings("all")
class BinaryTreeDemo {

    /*
     *二叉树结构：
     *  class Node<V> {
     *      private V val;
     *      private Node<V> left;
     *      private Node<V> right;
     *  }
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
     *  先序遍历下面的二叉树
     *		                ①
     *	                 ╱     ╲
     *	              ╱	       ╲
     *             ②	              ③
     *          ╱	  ╲	        ╱  ╲
     *       ╱	         ╲      ╱	       ╲
     *     ④			   ⑤  ⑥		     ⑦
     *  节点的经过顺序：
     *      ①②④④④②⑤⑤⑤②①③⑥⑥⑥③⑦⑦⑦③①
     *      ↑↑↑      ↑        ↑↑      ↑
     *      节点第一次出现的时候，就是可以处理打印节点值的时候
     *
     *非递归方式遍历二叉树
     *  使用栈遍历二叉树
     */

    /* 递归遍历二叉树 */

    /**
     * 先序遍历二叉树
     */
    public static void preLoopByRecurse(Node tree) {
        if (tree == null) {
            return;
        }
        System.out.println(tree.value);
        preLoopByRecurse(tree.left);
        preLoopByRecurse(tree.right);
    }

    /**
     * 中序遍历二叉树
     */
    public static void inLoopByRecurse(Node tree) {
        if (tree == null) {
            return;
        }
        inLoopByRecurse(tree.left);
        System.out.println(tree.value);
        inLoopByRecurse(tree.right);
    }

    /**
     * 后续遍历二叉树
     */
    public static void posLoopByRecurse(Node tree) {
        if (tree == null) {
            return;
        }
        posLoopByRecurse(tree.left);
        posLoopByRecurse(tree.right);
        System.out.println(tree.value);
    }

    /* 非递归遍历二叉树 */

    /* ************************************************************************************************************** */

    private static class Node {
        private int value;
        private Node left;
        private Node right;
    }
}
