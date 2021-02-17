package com.lg.algorithm.kmp.practice;

import java.util.ArrayList;

/**
 * 给定2颗树bigger，smaller，判断smaller是否是bigger的子树
 *
 * @author Xulg
 * Created in 2021-02-05 11:20
 */
@SuppressWarnings({"AlibabaLowerCamelCaseVariableNaming", "ConstantConditions", "DuplicatedCode"})
class CheckIsSubTree {

    /*
     * 给定2颗树bigger，smaller。bigger做头节点的树，其中是否有某棵子树的结构，是和smaller为头的树，完全一样的
     *--------------------------------------------------------
     * 暴力解
     *========================================================
     * KMP解
     */

    /* ****************************************************************************************************************/

    /**
     * 暴力解判断smaller是否是bigger的一颗子树
     * 时间复杂度O(N*M)  N为bigger树的节点数，M为smaller树的节点数
     *
     * @param bigger  树的头节点
     * @param smaller 树的头节点
     * @return true|false
     */
    public static boolean isSubTreeByViolence(Node bigger, Node smaller) {
        if (bigger == null && smaller == null) {
            // 都为null，肯定是一样的
            return true;
        }
        if (bigger == null) {
            // bigger树为null，没有子树
            return false;
        }
        if (smaller == null) {
            // smaller树为null，null是bigger树的子树
            return true;
        }
        // 两颗树是否相等
        if (isSameTreeStruct(bigger, smaller)) {
            return true;
        }
        // smaller是否是bigger的左子树的子树
        boolean left = isSubTreeByViolence(bigger.left, smaller);
        // smaller是否是bigger的右子树的子树
        boolean right = isSubTreeByViolence(bigger.right, smaller);
        return left || right;
    }

    /**
     * 判断两颗树结构是否一样的
     * 1.两个树的每个节点值都一样
     * 2.两个树的左节点，右节点是否都一样
     *
     * @param tree1 树头节点
     * @param tree2 树头节点
     * @return true|false
     */
    private static boolean isSameTreeStruct(Node tree1, Node tree2) {
        if (tree1 == null && tree2 == null) {
            // 两颗树都是null，那么就是相等的树
            return true;
        }
        if (tree1 == null && tree2 != null) {
            // 一颗树为null，一颗树非null，结构一定不相等
            return false;
        }
        if (tree1 != null && tree2 == null) {
            // 一颗树为null，一颗树非null，结构一定不相等
            return false;
        }
        if (!tree1.value.equals(tree2.value)) {
            // 当前两颗树的节点的值不相等，结构一定不一样
            return false;
        }
        // 两颗树的左节点是否相等
        boolean left = isSameTreeStruct(tree1.left, tree2.left);
        // 两颗树的右节点是否相等
        boolean right = isSameTreeStruct(tree1.right, tree2.right);
        // 如果两颗树的值相等，左树相等，右树相等，那么就是相等的树
        return left && right;
    }

    /* ****************************************************************************************************************/

    /**
     * KMP算法判断smaller是否是bigger的一颗子树
     * 时间复杂度O(N)  N为bigger树的节点数
     *
     * @param bigger  树的头节点
     * @param smaller 树的头节点
     * @return true|false
     */
    public static boolean isSubTreeByKMP(Node bigger, Node smaller) {
        if (bigger == null && smaller == null) {
            // 都为null，肯定是一样的
            return true;
        }
        if (bigger == null) {
            // bigger树为null，没有子树
            return false;
        }
        if (smaller == null) {
            // smaller树为null，null是bigger树的子树
            return true;
        }

        // 先将二叉树先序方式序列化为数组
        ArrayList<String> biggerList = serializeByPreLoop(bigger);
        String[] str = new String[biggerList.size()];
        for (int i = 0; i < biggerList.size(); i++) {
            str[i] = biggerList.get(i);
        }
        // 先将二叉树先序方式序列化为数组
        ArrayList<String> smallerList = serializeByPreLoop(smaller);
        String[] match = new String[smallerList.size()];
        for (int i = 0; i < smallerList.size(); i++) {
            match[i] = smallerList.get(i);
        }

        // 使用KMP算法比较
        return indexOf(str, match) != -1;
    }

    private static int indexOf(String[] str, String[] match) {
        if (str == null || match == null || match.length < 1 || str.length < match.length) {
            return -1;
        }

        // 获取match串的next数组
        int[] nextArray = getNextArray(match);

        int strIdx = 0;
        int matchIdx = 0;
        while (strIdx < str.length && matchIdx < match.length) {
            if (isEquals(str[strIdx], match[matchIdx])) {
                strIdx++;
                matchIdx++;
            } else {
                if (nextArray[matchIdx] == -1) {
                    assert matchIdx == 0 : "why matchIdx != 0";
                    strIdx++;
                } else {
                    // jump 加速
                    matchIdx = nextArray[matchIdx];
                }
            }
        }
        return matchIdx == match.length ? strIdx - matchIdx : -1;
    }

    /**
     * 先序遍历方式序列化二叉树
     */
    private static ArrayList<String> serializeByPreLoop(Node bigger) {
        ArrayList<String> list = new ArrayList<>();
        serializeRecurse(bigger, list);
        return list;
    }

    private static void serializeRecurse(Node node, ArrayList<String> list) {
        if (node == null) {
            list.add(null);
            return;
        }
        list.add(node.value);
        serializeRecurse(node.left, list);
        serializeRecurse(node.right, list);
    }

    private static int[] getNextArray(String[] match) {
        assert match != null && match.length > 0;
        if (match.length == 1) {
            return new int[]{-1};
        }
        if (match.length == 2) {
            return new int[]{-1, 0};
        }

        int[] next = new int[match.length];
        next[0] = -1;
        next[1] = 0;

        int index = 2;
        int cn = next[1];
        while (index < match.length) {
            if (isEquals(match[index - 1], match[cn])) {
                // 等价写法
                // next[index] = cn + 1;
                next[index] = next[index - 1] + 1;
                cn = next[index];
                index++;
            } else {
                if (cn > 0) {
                    // jump
                    cn = next[cn];
                } else {
                    next[index++] = 0;
                }
            }
        }
        return next;
    }

    private static boolean isEquals(String data1, String data2) {
        if (data1 == null && data2 == null) {
            return true;
        }
        if (data1 == null && data2 != null) {
            return false;
        }
        if (data1 != null && data2 == null) {
            return false;
        }
        // data1 != null && data2 != null;
        return data1.equals(data2);
    }

    /* ****************************************************************************************************************/

    // for test
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node(String.valueOf((int) (Math.random() * maxValue)));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    // big做头节点的树，其中是否有某棵子树的结构，是和small为头的树，完全一样的
    public static boolean containsTree1(Node big, Node small) {
        if (small == null) {
            return true;
        }
        // small != null
        if (big == null) {
            return false;
        }
        // big！=null  small!=null
        if (isSameValueStructure(big, small)) {
            return true;
        }
        return containsTree1(big.left, small) || containsTree1(big.right, small);
    }

    // head1为头的树，是否在结构对应上，完全和head2一样
    private static boolean isSameValueStructure(Node head1, Node head2) {
        if (head1 == null && head2 != null) {
            return false;
        }
        if (head1 != null && head2 == null) {
            return false;
        }
        if (head1 == null && head2 == null) {
            return true;
        }
        if (!head1.value.equals(head2.value)) {
            return false;
        }
        // head1.value == head2.value
        return isSameValueStructure(head1.left, head2.left)
                && isSameValueStructure(head1.right, head2.right);
    }

    public static boolean containsTree2(Node big, Node small) {
        if (small == null) {
            return true;
        }
        if (big == null) {
            return false;
        }
        ArrayList<String> b = preSerial(big);
        ArrayList<String> s = preSerial(small);
        String[] str = new String[b.size()];
        for (int i = 0; i < str.length; i++) {
            str[i] = b.get(i);
        }
        String[] match = new String[s.size()];
        for (int i = 0; i < match.length; i++) {
            match[i] = s.get(i);
        }
        return getIndexOf(str, match) != -1;
    }

    private static ArrayList<String> preSerial(Node head) {
        ArrayList<String> ans = new ArrayList<>();
        pres(head, ans);
        return ans;
    }

    private static void pres(Node head, ArrayList<String> ans) {
        if (head == null) {
            ans.add(null);
        } else {
            ans.add(String.valueOf(head.value));
            pres(head.left, ans);
            pres(head.right, ans);
        }
    }

    private static int getIndexOf(String[] str1, String[] str2) {
        if (str1 == null || str2 == null || str1.length < 1 || str1.length < str2.length) {
            return -1;
        }
        int x = 0;
        int y = 0;
        int[] next = getNextArray2(str2);
        while (x < str1.length && y < str2.length) {
            if (isEqual(str1[x], str2[y])) {
                x++;
                y++;
            } else if (next[y] == -1) {
                x++;
            } else {
                y = next[y];
            }
        }
        return y == str2.length ? x - y : -1;
    }

    private static int[] getNextArray2(String[] ms) {
        if (ms.length == 1) {
            return new int[]{-1};
        }
        int[] next = new int[ms.length];
        next[0] = -1;
        next[1] = 0;
        int i = 2;
        int cn = 0;
        while (i < next.length) {
            if (isEqual(ms[i - 1], ms[cn])) {
                next[i++] = ++cn;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                next[i++] = 0;
            }
        }
        return next;
    }

    private static boolean isEqual(String a, String b) {
        if (a == null && b == null) {
            return true;
        } else {
            if (a == null || b == null) {
                return false;
            } else {
                return a.equals(b);
            }
        }
    }

    /* ****************************************************************************************************************/
    /* ****************************************************************************************************************/
    /* ****************************************************************************************************************/

    private static class Node {
        String value;
        Node left;
        Node right;

        Node(String value) {
            this.value = value;
        }
    }

    /* ****************************************************************************************************************/
    /* ****************************************************************************************************************/
    /* ****************************************************************************************************************/

    public static void main(String[] args) {
        int bigTreeLevel = 7;
        int smallTreeLevel = 4;
        int nodeMaxValue = 5;
        int testTimes = 100000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            Node big = generateRandomBST(bigTreeLevel, nodeMaxValue);
            Node small = generateRandomBST(smallTreeLevel, nodeMaxValue);
            boolean ans1 = containsTree1(big, small);
            boolean ans2 = containsTree2(big, small);
            boolean r1 = isSubTreeByViolence(big, small);
            boolean r2 = isSubTreeByKMP(big, small);
            if (!(ans1 == ans2 && ans2 == r1 && r1 == r2)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish!");
    }
}
