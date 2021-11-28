package com.practice2.binarytree.dp;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xulg
 * @since 2021-10-17 21:24
 */
class MaxPartyHappyValue {
    /*
     * 派对的最大快乐值
     *  公司的每个员工都符合Employee类的描述。整个公司的人员结构可以看作是一颗
     * 标准的，没有环的多叉树。树的头节点是公司唯一的老板。除老板之外的每个员工
     * 都有唯一的直接上级。叶子节点是没有任何下属的基层员工(subordinates列表为空)，
     * 除基层员工之外，每个员工都有一个或多个直接下级。
     *  这个公司现在要举办派对，你可以决定哪些员工来，哪些员工不来，规则如下：
     *      1.如果某个员工来了，那么这个员工的所有直接下级都不能来
     *      2.派对的整体快乐值是所有到场员工快乐值的累加
     *      3.你的目标是让派对的整体快乐值尽量大
     *  给定一颗多叉树的头节点boss，请返回派对的最大快乐值
     */

    private static class DP {
        public static int calcMaxHappyValue(Employee boss) {
            Data data = recurse(boss);
            return Math.max(data.yesJoin, data.noJoin);
        }

        private static Data recurse(Employee employee) {
            if (employee == null) {
                return new Data(0, 0);
            }
            if (employee.subordinates.isEmpty()) {
                // 没有下属的员工，直接参与
                return new Data(employee.happyValue, 0);
            }
            // 当前员工参与的快乐值
            int yesHappy = employee.happyValue;
            // 当前员工不参与的快乐值
            int noHappy = 0;
            for (Employee subordinate : employee.subordinates) {
                // 看下属参不参与的情况
                Data subData = recurse(subordinate);
                // 当前员工参与了，就要加上下属不参与时的快乐值
                yesHappy += subData.noJoin;
                // 当前员工没参与，就要加上下属参与不参与两种情况下的最大值
                noHappy += Math.max(subData.yesJoin, subData.noJoin);
            }
            return new Data(yesHappy, noHappy);
        }

        private static class Data {
            private int yesJoin;
            private int noJoin;

            Data(int yesJoin, int noJoin) {
                this.yesJoin = yesJoin;
                this.noJoin = noJoin;
            }
        }
    }


    private static class Employee {
        private int happyValue;
        private List<Employee> subordinates;

        Employee(int happyValue) {
            this.happyValue = happyValue;
            this.subordinates = new ArrayList<>();
        }
    }

    private static class Compare {
        public static int getMaxHappy(Employee boss) {
            if (boss == null) {
                return 0;
            }
            return process1(boss, false);
        }

        /**
         * 当前来到的节点叫cur，
         * up表示cur的上级是否来，
         * 该函数含义：
         * 如果up为true，表示在cur上级已经确定来，的情况下，cur整棵树能够提供最大的快乐值是多少？
         * 如果up为false，表示在cur上级已经确定不来，的情况下，cur整棵树能够提供最大的快乐值是多少？
         */
        public static int process1(Employee cur, boolean up) {
            // 如果cur的上级来的话，cur没得选，只能不来
            if (up) {
                int ans = 0;
                for (Employee next : cur.subordinates) {
                    ans += process1(next, false);
                }
                return ans;
            }
            // 如果cur的上级不来的话，cur可以选，可以来也可以不来
            else {
                int p1 = cur.happyValue;
                int p2 = 0;
                for (Employee next : cur.subordinates) {
                    p1 += process1(next, true);
                    p2 += process1(next, false);
                }
                return Math.max(p1, p2);
            }
        }
    }


    /* ************************************************************************************************************** */

    /* 对数器 */

    private static Employee genarateBoss(int maxLevel, int maxNexts, int maxHappy) {
        if (Math.random() < 0.02) {
            return null;
        }
        Employee boss = new Employee((int) (Math.random() * (maxHappy + 1)));
        generateNexts(boss, 1, maxLevel, maxNexts, maxHappy);
        return boss;
    }

    private static void generateNexts(Employee e, int level, int maxLevel, int maxNexts, int maxHappy) {
        if (level > maxLevel) {
            return;
        }
        int nextsSize = (int) (Math.random() * (maxNexts + 1));
        for (int i = 0; i < nextsSize; i++) {
            Employee next = new Employee((int) (Math.random() * (maxHappy + 1)));
            e.subordinates.add(next);
            generateNexts(next, level + 1, maxLevel, maxNexts, maxHappy);
        }
    }

    /* ************************************************************************************************************** */

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxNext = 7;
        int maxHappy = 100;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            Employee boss = genarateBoss(maxLevel, maxNext, maxHappy);
            if (DP.calcMaxHappyValue(boss) != Compare.getMaxHappy(boss)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
