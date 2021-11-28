package com.lg.leetcode.other.practice1;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xulg
 * Description: leetcode_109
 * Created in 2021-05-28 10:42
 */
@SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
class ConvertSortedListToBinarySearchTree {
    /*
     *  Given the head of a singly linked list where elements are sorted in
     * ascending order, convert it to a height balanced BST.
     *  For this problem, a height-balanced binary tree is defined as a
     * binary tree in which the depth of the two subtrees of every node
     * never differ by more than 1.
     *
     * Example 1:
     * Input: head = [-10,-3,0,5,9]
     * Output: [0,-3,9,-10,null,5]
     * Explanation: One possible answer is [0,-3,9,-10,null,5], which represents
     * the shown height balanced BST.
     *
     * Example 2:
     * Input: head = []
     * Output: []
     *
     * Example 3:
     * Input: head = [0]
     * Output: [0]
     *
     * Example 4:
     * Input: head = [1,3]
     * Output: [3,1]
     *
     * Constraints:
     * The number of nodes in head is in the range [0, 2 * 104].
     * -105 <= Node.val <= 105
     */

    public static TreeNode sortedListToBST(ListNode head) {
        return null;
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) { this.val = val; }
    }

    private static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val) { this.val = val; }
    }

    public static void main(String[] args) {
        System.out.println(sortedListToBST(null));
    }
}
