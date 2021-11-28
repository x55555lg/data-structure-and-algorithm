package com.practice2.kmp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Xulg
 * @since 2021-10-26 9:55
 */
@SuppressWarnings("ConstantConditions")
class CheckIsSubTree {

    /*
     * 给定2颗树bigger，smaller。bigger做头节点的树，其中是否有某棵子树的结构，
     * 是和smaller为头的树，完全一样的
     *--------------------------------------------------------
     * 暴力解
     *========================================================
     * KMP解
     */

    private static class CheckIsSubTreeByBruteForce {
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
            if (isSameTree(bigTree, smallTree)) {
                return true;
            }
            boolean left = isSubTree(bigTree.left, smallTree);
            boolean right = isSubTree(bigTree.right, smallTree);
            return left || right;
        }

        private static boolean isSameTree(Node t1, Node t2) {
            if (t1 == null && t2 == null) {
                return true;
            }
            if (t1 == null && t2 != null) {
                return false;
            }
            if (t1 != null && t2 == null) {
                return false;
            }
            if (t1.val != t2.val) {
                return false;
            }
            boolean isLeftSame = isSameTree(t1.left, t2.left);
            boolean isRightSame = isSameTree(t1.right, t2.right);
            return isLeftSame && isRightSame;
        }
    }

    private static class CheckIsSubTreeByKMP {

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
            List<Integer> bigData = PreOrderSerialize.serialize(bigTree);
            List<Integer> smallData = PreOrderSerialize.serialize(smallTree);
            return KMP.indexOf(bigData, smallData) != -1;
        }

        private static class KMP {
            public static int indexOf(List<Integer> str, List<Integer> match) {
                if (str == null || match == null || str.size() < match.size()) {
                    return -1;
                }
                int[] nextArray = getNextArray(match);
                int strIdx = 0, matchIdx = 0;
                while (strIdx < str.size() && matchIdx < match.size()) {
                    if (isEquals(str.get(strIdx), match.get(matchIdx))) {
                        strIdx++;
                        matchIdx++;
                    } else {
                        if (nextArray[matchIdx] == -1) {
                            assert matchIdx == 0;
                            strIdx++;
                        } else {
                            matchIdx = nextArray[matchIdx];
                        }
                    }
                }
                return matchIdx == match.size() ? strIdx - matchIdx : -1;
            }

            private static int[] getNextArray(List<Integer> chars) {
                if (chars.size() == 0) {
                    return new int[0];
                }
                if (chars.size() == 1) {
                    return new int[]{-1};
                }
                if (chars.size() == 2) {
                    return new int[]{-1, 0};
                }
                int[] next = new int[chars.size()];
                next[0] = -1;
                next[1] = 0;
                int cn = next[1];
                for (int idx = 2; idx < chars.size(); ) {
                    if (isEquals(chars.get(idx - 1), chars.get(cn))) {
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

            private static boolean isEquals(Integer v1, Integer v2) {
                if (v1 == null && v2 == null) {
                    return true;
                }
                if (v1 == null && v2 != null) {
                    return false;
                }
                if (v1 != null && v2 == null) {
                    return false;
                }
                return v1.equals(v2);
            }
        }

        private static class PreOrderSerialize {
            public static List<Integer> serialize(Node tree) {
                if (tree == null) {
                    return null;
                }
                List<Integer> data = new LinkedList<>();
                recurseSerialize(tree, data);
                return data;
            }

            public static Node deserialize(List<Integer> data) {
                if (data == null) {
                    return null;
                }
                return recurseDeserialize(new LinkedList<>(data));
            }

            private static void recurseSerialize(Node node, List<Integer> data) {
                if (node == null) {
                    data.add(null);
                    return;
                }
                data.add(node.val);
                recurseSerialize(node.left, data);
                recurseSerialize(node.right, data);
            }

            private static Node recurseDeserialize(Queue<Integer> data) {
                Integer val = data.poll();
                if (val == null) {
                    return null;
                }
                Node node = new Node(val);
                node.left = recurseDeserialize(data);
                node.right = recurseDeserialize(data);
                return node;
            }
        }
    }

    private static class Node {
        int val;
        Node left, right;

        Node(int val) { this.val = val; }
    }

    /* ****************************************************************************************************************/

    // for test
    static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    /* ****************************************************************************************************************/

    private static class Compare1 {

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
            if (head1.val != (head2.val)) {
                return false;
            }
            // head1.value == head2.value
            return isSameValueStructure(head1.left, head2.left)
                    && isSameValueStructure(head1.right, head2.right);
        }

    }

    private static class Compare2 {
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
                ans.add(String.valueOf(head.val));
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
    }

    /* ****************************************************************************************************************/
    /* ****************************************************************************************************************/
    /* ****************************************************************************************************************/
    /* ****************************************************************************************************************/
    /* ****************************************************************************************************************/

    public static void main(String[] args) {
        int bigTreeLevel = 7;
        int smallTreeLevel = 4;
        int nodeMaxValue = 5;
        int testTimes = 1000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            Node big = generateRandomBST(bigTreeLevel, nodeMaxValue);
            Node small = generateRandomBST(smallTreeLevel, nodeMaxValue);
            boolean ans1 = Compare1.containsTree1(big, small);
            boolean ans2 = Compare2.containsTree2(big, small);
            boolean r1 = CheckIsSubTreeByBruteForce.isSubTree(big, small);
            boolean r2 = CheckIsSubTreeByKMP.isSubTree(big, small);
            if (!(ans1 == ans2 && ans2 == r1 && r1 == r2)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish!");
    }
}
