package com.lg.leetcode.topinterview.practice;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * @author Xulg
 * Created in 2021-03-09 13:53
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
class KthSmallestElementInBST {

    /*
     * Given the root of a binary search tree, and an integer k, return the kth (1-indexed) smallest element in the tree.
     *
     * Example 1:
     * Input: root = [3,1,4,null,2], k = 1
     * Output: 1
     *
     * Example 2:
     * Input: root = [5,3,6,2,4,null,null,1], k = 3
     * Output: 3
     *
     * Constraints:
     *  The number of nodes in the tree is n.
     *  1 <= k <= n <= 104
     *  0 <= Node.val <= 104
     *
     * Follow up:
     *  If the BST is modified often (i.e., we can do insert and delete operations) and you need to
     *  find the kth smallest frequently, how would you optimize?
     *
     * Definition for a binary tree node.
     * public class TreeNode {
     *     int val;
     *     TreeNode left;
     *     TreeNode right;
     *     TreeNode() {}
     *     TreeNode(int val) { this.val = val; }
     *     TreeNode(int val, TreeNode left, TreeNode right) {
     *         this.val = val;
     *         this.left = left;
     *         this.right = right;
     *     }
     * }
     *----------------------------------------------------------------------------------------------
     */

    public static int kthSmallest(TreeNode root, int k) {
        List<Integer> list = PostOrderTraverse.serialize(root);
        int size = 0;
        for (Integer val : list) {
            if (val != null) {
                size++;
            }
        }
        int[] arr = new int[size];
        int idx = 0;
        for (Integer val : list) {
            if (val != null) {
                arr[idx++] = val;
            }
        }
        return BFPRT.findKthSmallest(arr, k);
    }

    /**
     * BFPRT算法
     */
    private static class Solution1 {
        public static int kthSmallest(TreeNode root, int k) {
            List<Integer> list = serialize(root);
            int[] arr = new int[list.size()];
            int idx = 0;
            for (Integer val : list) {
                if (val != null) {
                    arr[idx++] = val;
                }
            }
            return findKthSmallest(arr, 0, idx - 1, k);
        }

        public static int findKthSmallest(int[] arr, int left, int right, int k) {
            return bfprt(arr, left, right, k - 1);
        }

        private static int bfprt(int[] arr, int left, int right, int index) {
            if (left == right) {
                assert right == index;
                return arr[index];
            }
            int pivot = getMedianOfMedians(arr, left, right);
            int[] partition = partition(arr, left, right, pivot);
            int start = partition[0], end = partition[1];
            if (index >= start && index <= end) {
                return arr[index];
            } else if (index < start) {
                return bfprt(arr, left, start - 1, index);
            } else {
                return bfprt(arr, end + 1, right, index);
            }
        }

        private static int getMedianOfMedians(int[] arr, int left, int right) {
            int size = right - left + 1;
            int offset = size % 5 == 0 ? 0 : 1;
            int[] medians = new int[size / 5 + offset];
            for (int group = 0; group < medians.length; group++) {
                int start = left + group * 5;
                int end = Math.min(start + 4, right);
                insertionSort(arr, start, end);
                medians[group] = arr[(start + end) >> 1];
            }
            return bfprt(medians, 0, medians.length - 1, medians.length >> 1);
        }

        private static void insertionSort(int[] arr, int start, int end) {
            for (int i = start; i <= end; i++) {
                for (int j = i; j > start; j--) {
                    if (arr[j - 1] > arr[j]) {
                        swap(arr, j - 1, j);
                    }
                }
            }
        }

        private static int[] partition(int[] arr, int left, int right, int pivot) {
            if (left > right) {
                return new int[]{-1, -1};
            }
            if (left == right) {
                return new int[]{left, right};
            }
            int less = left - 1;
            int more = right + 1;
            for (int idx = left; idx < more; ) {
                if (arr[idx] > pivot) {
                    swap(arr, idx, --more);
                } else if (arr[idx] < pivot) {
                    swap(arr, idx++, ++less);
                } else {
                    idx++;
                }
            }
            if (less + 1 > more - 1) {
                return new int[]{-1, -1};
            } else {
                return new int[]{less + 1, more - 1};
            }
        }

        private static void swap(int[] arr, int i, int j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }

        private static List<Integer> serialize(TreeNode root) {
            if (root == null) {
                return null;
            }
            List<Integer> list = new ArrayList<>();
            recurseForSerialize(root, list);
            return list;
        }

        private static void recurseForSerialize(TreeNode treeNode, List<Integer> list) {
            if (treeNode == null) {
                list.add(null);
            } else {
                list.add(treeNode.val);
                recurseForSerialize(treeNode.left, list);
                recurseForSerialize(treeNode.right, list);
            }
        }

    }

    /**
     * 中序遍历解决
     * 最优
     */
    private static class Solution2 {

        public static int kthSmallest(TreeNode root, int k) {
            int count = 0;
            Stack<TreeNode> stack = new Stack<>();
            TreeNode node = root;
            while (!stack.isEmpty() || node != null) {
                if (node != null) {
                    stack.add(node);
                    node = node.left;
                } else {
                    TreeNode treeNode = stack.pop();
                    if (++count == k) {
                        // 第k小
                        return treeNode.val;
                    }
                    node = treeNode.right;
                }
            }
            throw new Error();
        }

    }

    private static class QuickSortIdea {

        public static int findKthSmallest(int[] arr, int k) {
            return recurse(arr, 0, arr.length - 1, k - 1);
        }

        private static int recurse(int[] arr, int left, int right, int index) {
            if (left == right) {
                assert right == index;
                return arr[index];
            }
            int pivot = arr[left + (int) (Math.random() * (right - left + 1))];
            int[] partition = partition(arr, left, right, pivot);
            int start = partition[0], end = partition[1];
            if (index >= start && index <= end) {
                return arr[index];
            } else if (index < start) {
                return recurse(arr, left, start - 1, index);
            } else {
                return recurse(arr, end + 1, right, index);
            }
        }

        private static int[] partition(int[] arr, int left, int right, int pivot) {
            if (left > right) {
                return new int[]{-1, -1};
            }
            if (left == right) {
                return new int[]{left, right};
            }
            int less = left - 1;
            int more = right + 1;
            for (int idx = left; idx < more; ) {
                if (arr[idx] > pivot) {
                    swap(arr, idx, --more);
                } else if (arr[idx] < pivot) {
                    swap(arr, idx++, ++less);
                } else {
                    idx++;
                }
            }
            if (less + 1 > more - 1) {
                return new int[]{-1, -1};
            } else {
                return new int[]{less + 1, more - 1};
            }
        }

        private static void swap(int[] arr, int i, int j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    private static class BFPRT {

        public static int findKthSmallest(int[] arr, int k) {
            return bfprt(arr, 0, arr.length - 1, k - 1);
        }

        private static int bfprt(int[] arr, int left, int right, int index) {
            if (left == right) {
                assert right == index;
                return arr[index];
            }
            int pivot = getMedianOfMedians(arr, left, right);
            int[] partition = partition(arr, left, right, pivot);
            int start = partition[0], end = partition[1];
            if (index >= start && index <= end) {
                return arr[index];
            } else if (index < start) {
                return bfprt(arr, left, start - 1, index);
            } else {
                return bfprt(arr, end + 1, right, index);
            }
        }

        private static int getMedianOfMedians(int[] arr, int left, int right) {
            int size = right - left + 1;
            int offset = size % 5 == 0 ? 0 : 1;
            int[] medians = new int[size / 5 + offset];
            for (int group = 0; group < medians.length; group++) {
                int start = left + group * 5;
                int end = Math.min(start + 4, right);
                insertionSort(arr, start, end);
                medians[group] = arr[(start + end) >> 1];
            }
            return bfprt(medians, 0, medians.length - 1, medians.length >> 1);
        }

        private static void insertionSort(int[] arr, int start, int end) {
            for (int i = start; i <= end; i++) {
                for (int j = i; j > start; j--) {
                    if (arr[j - 1] > arr[j]) {
                        swap(arr, j - 1, j);
                    }
                }
            }
        }

        private static int[] partition(int[] arr, int left, int right, int pivot) {
            if (left > right) {
                return new int[]{-1, -1};
            }
            if (left == right) {
                return new int[]{left, right};
            }
            int less = left - 1;
            int more = right + 1;
            for (int idx = left; idx < more; ) {
                if (arr[idx] > pivot) {
                    swap(arr, idx, --more);
                } else if (arr[idx] < pivot) {
                    swap(arr, idx++, ++less);
                } else {
                    idx++;
                }
            }
            if (less + 1 > more - 1) {
                return new int[]{-1, -1};
            } else {
                return new int[]{less + 1, more - 1};
            }
        }

        private static void swap(int[] arr, int i, int j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }

    }

    /**
     * 先序
     * 序列化|反序列化
     * 遍历
     */
    private static class PreOrderTraverse {

        public static List<Integer> serialize(TreeNode root) {
            if (root == null) {
                return null;
            }
            List<Integer> list = new ArrayList<>();
            recurseForSerialize(root, list);
            return list;
        }

        private static void recurseForSerialize(TreeNode treeNode, List<Integer> list) {
            if (treeNode == null) {
                list.add(null);
            } else {
                list.add(treeNode.val);
                recurseForSerialize(treeNode.left, list);
                recurseForSerialize(treeNode.right, list);
            }
        }

        public static TreeNode deserialize(List<Integer> list) {
            if (list == null) {
                return null;
            }
            return recurseForDeserialize(new LinkedList<>(list));
        }

        private static TreeNode recurseForDeserialize(Queue<Integer> queue) {
            Integer val = queue.poll();
            if (val == null) {
                return null;
            } else {
                TreeNode treeNode = new TreeNode(val);
                treeNode.left = recurseForDeserialize(queue);
                treeNode.right = recurseForDeserialize(queue);
                return treeNode;
            }
        }

        public static void traverse(TreeNode root) {
            if (root == null) {
                return;
            }
            Stack<TreeNode> stack = new Stack<>();
            stack.add(root);
            while (!stack.isEmpty()) {
                TreeNode treeNode = stack.pop();
                System.out.println(treeNode.val);
                if (treeNode.right != null) {
                    stack.add(treeNode.right);
                }
                if (treeNode.left != null) {
                    stack.add(treeNode.left);
                }
            }
        }

    }

    /**
     * 中序
     * 遍历
     */
    private static class InOrderTraverse {

        public static void traverse(TreeNode root) {
            if (root == null) {
                return;
            }
            Stack<TreeNode> stack = new Stack<>();
            TreeNode node = root;
            while (!stack.isEmpty() || node != null) {
                if (node != null) {
                    stack.add(node);
                    node = node.left;
                } else {
                    TreeNode treeNode = stack.pop();
                    System.out.println(treeNode.val);
                    node = treeNode.right;
                }
            }
        }

    }

    /**
     * 后序
     * 序列化|反序列化
     * 遍历
     */
    private static class PostOrderTraverse {

        public static List<Integer> serialize(TreeNode root) {
            if (root == null) {
                return null;
            }
            List<Integer> list = new ArrayList<>();
            recurseForSerialize(root, list);
            return list;
        }

        private static void recurseForSerialize(TreeNode treeNode, List<Integer> list) {
            if (treeNode == null) {
                list.add(null);
            } else {
                recurseForSerialize(treeNode.left, list);
                recurseForSerialize(treeNode.right, list);
                list.add(treeNode.val);
            }
        }

        public static TreeNode deserialize(List<Integer> list) {
            if (list == null) {
                return null;
            }
            Stack<Integer> stack = new Stack<>();
            stack.addAll(list);
            return recurseForDeserialize(stack);
        }

        private static TreeNode recurseForDeserialize(Stack<Integer> stack) {
            Integer val = stack.pop();
            if (val == null) {
                return null;
            } else {
                TreeNode treeNode = new TreeNode(val);
                treeNode.right = recurseForDeserialize(stack);
                treeNode.left = recurseForDeserialize(stack);
                return treeNode;
            }
        }

        public static void traverse(TreeNode root) {
            if (root == null) {
                return;
            }
            Stack<TreeNode> helper = new Stack<>();
            Stack<TreeNode> stack = new Stack<>();
            stack.add(root);
            while (!stack.isEmpty()) {
                TreeNode treeNode = stack.pop();
                helper.add(treeNode);
                if (treeNode.left != null) {
                    stack.add(treeNode.left);
                }
                if (treeNode.right != null) {
                    stack.add(treeNode.right);
                }
            }
            while (!helper.isEmpty()) {
                TreeNode treeNode = helper.pop();
                System.out.println(treeNode.val);
            }
        }

    }

    /**
     * 层级
     * 序列化|反序列化
     * 遍历
     */
    private static class LevelOrderTraverse {

        public static List<Integer> serialize(TreeNode root) {
            if (root == null) {
                return null;
            }
            List<Integer> list = new ArrayList<>();
            Queue<TreeNode> queue = new LinkedList<>();
            queue.add(root);
            while (!queue.isEmpty()) {
                TreeNode treeNode = queue.poll();
                list.add(treeNode == null ? null : treeNode.val);
                if (treeNode != null) {
                    queue.add(treeNode.left);
                    queue.add(treeNode.right);
                }
            }
            return list;
        }

        public static TreeNode deserialize(List<Integer> list) {
            if (list == null) {
                return null;
            }
            Queue<Integer> data = new LinkedList<>(list);

            // 头节点
            Integer rootVal = data.poll();
            TreeNode root = rootVal == null ? null : new TreeNode(rootVal);
            if (root == null) {
                return null;
            }

            Queue<TreeNode> queue = new LinkedList<>();
            queue.add(root);
            while (!queue.isEmpty()) {
                TreeNode treeNode = queue.poll();
                Integer leftVal = data.poll();
                Integer rightVal = data.poll();

                if (leftVal != null) {
                    treeNode.left = new TreeNode(leftVal);
                    queue.add(treeNode.left);
                }
                if (rightVal != null) {
                    treeNode.right = new TreeNode(rightVal);
                    queue.add(treeNode.right);
                }
            }
            return root;
        }

        public static void traverse(TreeNode root) {
            if (root == null) {
                return;
            }
            Queue<TreeNode> queue = new LinkedList<>();
            queue.add(root);
            while (!queue.isEmpty()) {
                TreeNode treeNode = queue.poll();
                System.out.println(treeNode.val);
                if (treeNode.left != null) {
                    queue.add(treeNode.left);
                }
                if (treeNode.right != null) {
                    queue.add(treeNode.right);
                }
            }
        }

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
        {
            TreeNode root = new TreeNode(3);
            root.left = new TreeNode(1);
            root.right = new TreeNode(4);
            root.left.right = new TreeNode(2);
            int kthSmallest = kthSmallest(root, 1);
            System.out.println(kthSmallest);
        }

        {
            // [5,3,6,2,4,null,null,1]
            // 3
            TreeNode root = new TreeNode(5);
            root.left = new TreeNode(3);
            root.right = new TreeNode(6);
            root.left.left = new TreeNode(2);
            root.left.right = new TreeNode(4);
            root.left.left.left = new TreeNode(1);
            kthSmallest(root, 3);
        }

        {
            TreeNode root = new TreeNode(3);
            root.left = new TreeNode(1);
            root.right = new TreeNode(4);
            root.left.right = new TreeNode(2);
            int kthSmallest = Solution1.kthSmallest(root, 1);
            System.out.println(kthSmallest);
        }

        {
            TreeNode root = new TreeNode(5);
            root.left = new TreeNode(3);
            root.right = new TreeNode(6);
            root.left.left = new TreeNode(2);
            root.left.right = new TreeNode(4);
            root.left.left.left = new TreeNode(1);
            int kthSmallest = Solution1.kthSmallest(root, 3);
            System.out.println(kthSmallest);
        }

        {
            TreeNode root = new TreeNode(3);
            root.left = new TreeNode(1);
            root.right = new TreeNode(4);
            root.left.right = new TreeNode(2);
            int kthSmallest = Solution2.kthSmallest(root, 1);
            System.out.println(kthSmallest);
        }

        {
            TreeNode root = new TreeNode(5);
            root.left = new TreeNode(3);
            root.right = new TreeNode(6);
            root.left.left = new TreeNode(2);
            root.left.right = new TreeNode(4);
            root.left.left.left = new TreeNode(1);
            int kthSmallest = Solution2.kthSmallest(root, 3);
            System.out.println(kthSmallest);
        }
    }

}
