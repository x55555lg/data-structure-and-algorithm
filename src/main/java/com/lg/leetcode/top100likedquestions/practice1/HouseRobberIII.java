package com.lg.leetcode.top100likedquestions.practice1;

/**
 * @author Xulg
 * Created in 2021-03-11 10:48
 */
@SuppressWarnings({"AlibabaClassNamingShouldBeCamel", "MapOrSetKeyShouldOverrideHashCodeEquals"})
class HouseRobberIII {

    /*
     *  The thief has found himself a new place for his thievery again. There is only one entrance to this area,
     * called root.
     *  Besides the root, each house has one and only one parent house. After a tour, the smart thief realized
     * that all houses in this place form a binary tree. It will automatically contact the police if two
     * directly-linked houses were broken into on the same night.
     *  Given the root of the binary tree, return the maximum amount of money the thief can rob without alerting
     * the police.
     *
     * Example 1:
     *		            ③
     *	            ╱     ╲
     *	         ╱	          ╲
     *         ②	            ③
     *      	  ╲               ╲
     *   	         ╲               ╲
     *   	  		  ③                ①
     * Input: root = [3,2,3,null,3,null,1]
     * Output: 7
     * Explanation: Maximum amount of money the thief can rob = 3 + 3 + 1 = 7.
     *
     * Example 2:
     *		            ③
     *	            ╱     ╲
     *	         ╱	          ╲
     *         ④	            ⑤
     *      ╱	  ╲               ╲
     *   ╱	         ╲               ╲
     * ①			   ③               ①
     * Input: root = [3,4,5,1,3,null,1]
     * Output: 9
     * Explanation: Maximum amount of money the thief can rob = 4 + 5 = 9.
     *
     * Constraints:
     *  The number of nodes in the tree is in the range [1, 104].
     *  0 <= Node.val <= 104
     */

    public static int rob(TreeNode root) {
        return 0;
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
    }

}
