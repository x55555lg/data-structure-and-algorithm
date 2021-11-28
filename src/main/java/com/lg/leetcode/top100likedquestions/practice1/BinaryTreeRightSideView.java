package com.lg.leetcode.top100likedquestions.practice1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * @author Xulg
 * Created in 2021-03-11 9:36
 */
@SuppressWarnings("MapOrSetKeyShouldOverrideHashCodeEquals")
class BinaryTreeRightSideView {

    /*
     * Given the root of a binary tree, imagine yourself standing on the right side of it,
     * return the values of the nodes you can see ordered from top to bottom.
     *
     * Example 1:
     *		            ①
     *	            ╱     ╲
     *	         ╱	          ╲
     *         ②	            ③
     *      	  ╲               ╲
     *   	         ╲               ╲
     *  			  ⑤                ④
     * Input: root = [1,2,3,null,5,null,4]
     * Output: [1,3,4]
     *
     * Example 2:
     *		    ①
     *	           ╲
     *	              ╲
     *                  ③
     * Input: root = [1,null,3]
     * Output: [1,3]
     *
     * Example 3:
     * Input: root = []
     * Output: []
     *
     * Constraints:
     *  The number of nodes in the tree is in the range [0, 100].
     *  -100 <= Node.val <= 100
     *-----------------------------------------------------------
     * 字节跳动面试题
     */

    public static List<Integer> rightSideView(TreeNode root) {
        return null;
    }

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);
        TreeNode node6 = new TreeNode(6);
        TreeNode node7 = new TreeNode(7);
        root.left = node2;
        root.right = node3;
        node2.left = node4;
        node2.right = node5;
        node3.left = node6;
        node3.right = node7;
    }

}
