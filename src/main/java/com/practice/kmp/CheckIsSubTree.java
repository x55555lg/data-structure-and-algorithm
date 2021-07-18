package com.practice.kmp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Xulg
 * Created in 2021-05-08 16:04
 */
class CheckIsSubTree {

    /*
     * 给定2颗树bigger，smaller。bigger做头节点的树，其中是否有某棵子树的结构，
     * 是和smaller为头的树，完全一样的
     *--------------------------------------------------------
     * 暴力解
     *========================================================
     * KMP解
     */

    @SuppressWarnings("ConstantConditions")
    private static class BruteForce {
        public static boolean isSubTree(Node bigTree, Node smallTree) {
            if (bigTree == null && smallTree == null) {
                return true;
            }
            if (bigTree == null && smallTree != null) {
                return false;
            }
            if (bigTree != null && smallTree == null) {
                return true;
            }
            assert bigTree != null && smallTree != null;
            if (isSameTreeStruct(bigTree, smallTree)) {
                // 树结构一样的
                return true;
            }
            // 看大树的左子树，右子树，小树的关系
            return isSubTree(bigTree.left, smallTree) || isSubTree(bigTree.right, smallTree);
        }

        private static boolean isSameTreeStruct(Node tree1, Node tree2) {
            if (tree1 == null && tree2 == null) {
                return true;
            }
            if (tree1 == null && tree2 != null) {
                return false;
            }
            if (tree1 != null && tree2 == null) {
                return false;
            }
            assert tree1 != null && tree2 != null;
            if (tree1.value != tree2.value) {
                return false;
            }
            boolean isLeftSame = isSameTreeStruct(tree1.left, tree2.left);
            boolean isRightSame = isSameTreeStruct(tree1.right, tree2.right);
            return isLeftSame && isRightSame;
        }
    }

    @SuppressWarnings("ConstantConditions")
    private static class CheckByKMP {
        public static boolean isSubTree(Node bigTree, Node smallTree) {
            if (bigTree == null && smallTree == null) {
                return true;
            }
            if (bigTree == null && smallTree != null) {
                return false;
            }
            if (bigTree != null && smallTree == null) {
                return true;
            }
            assert bigTree != null && smallTree != null;
            // 二叉树序列化为字符串
            Integer[] bigTreeStr = PreOrderSerialize.serialize(bigTree);
            Integer[] smallTreeStr = PreOrderSerialize.serialize(smallTree);
            // 使用KMP算法比较
            return KMP.indexOf(bigTreeStr, smallTreeStr) != -1;
        }

        private static class PreOrderSerialize {
            public static Integer[] serialize(Node tree) {
                if (tree == null) {
                    return null;
                }
                LinkedList<Integer> data = new LinkedList<>();
                recurse(tree, data);
                return data.toArray(new Integer[0]);
            }

            private static void recurse(Node node, Queue<Integer> data) {
                if (node == null) {
                    data.add(null);
                    return;
                }
                data.add(node.value);
                recurse(node.left, data);
                recurse(node.right, data);
            }
        }

        private static class KMP {
            public static int indexOf(Integer[] str, Integer[] match) {
                if (str == null || match == null || str.length < match.length) {
                    return -1;
                }
                int[] next = getNext(match);
                int strIdx = 0, matchIdx = 0;
                while (strIdx < str.length && matchIdx < match.length) {
                    if (isEquals(str[strIdx], match[matchIdx])) {
                        strIdx++;
                        matchIdx++;
                    } else {
                        if (next[matchIdx] == -1) {
                            assert matchIdx == 0;
                            strIdx++;
                        } else {
                            matchIdx = next[matchIdx];
                        }
                    }
                }
                return matchIdx == match.length ? strIdx - matchIdx : -1;
            }

            private static int[] getNext(Integer[] match) {
                if (match.length == 0) {
                    return new int[0];
                }
                if (match.length == 1) {
                    return new int[]{-1};
                }
                if (match.length == 2) {
                    return new int[]{-1, 0};
                }
                int[] next = new int[match.length];
                next[0] = -1;
                next[1] = 0;
                int cn = next[1];
                for (int idx = 2; idx < match.length; ) {
                    if (isEquals(match[idx - 1], match[cn])) {
                        next[idx] = cn + 1;
                        cn = next[idx];
                        idx++;
                    } else {
                        if (cn > 0) {
                            cn = next[cn];
                        } else {
                            next[idx++] = 0;
                        }
                    }
                }
                return next;
            }

            private static boolean isEquals(Integer data1, Integer data2) {
                if (data1 == null && data2 == null) {
                    return true;
                }
                if (data1 == null && data2 != null) {
                    return false;
                }
                if (data1 != null && data2 == null) {
                    return false;
                }
                return data1.equals(data2);
            }
        }

    }

    /* ****************************************************************************************************************/

    private static class Node {
        private final int value;
        private Node left;
        private Node right;

        Node(int value) {
            this.value = value;
        }
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
        Node head = new Node((int) (Math.random() * maxValue));
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
        if (head1.value != (head2.value)) {
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
            boolean r1 = BruteForce.isSubTree(big, small);
            boolean r2 = CheckByKMP.isSubTree(big, small);
            if (!(ans1 == ans2 && ans2 == r1 && r1 == r2)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish!");
    }
}
