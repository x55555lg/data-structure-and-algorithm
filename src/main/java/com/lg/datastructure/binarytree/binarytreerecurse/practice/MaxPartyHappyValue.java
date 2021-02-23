package com.lg.datastructure.binarytree.binarytreerecurse.practice;

import java.util.ArrayList;
import java.util.List;

/**
 * 派对的最大快乐值
 *
 * @author Xulg
 * Created in 2020-11-03 18:13
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
     *------------------------------------------------------------------------------
     * 思路：对于任何的节点X
     *               X
     *      a        b        c
     *    1 2 3    4 5 6    7 8 9
     * 节点X是否参与
     *      如果节点X参与：
     *          节点X子树的快乐值 = 节点X的快乐值 + a子树头节点不参与时的快乐值
     *                              + b子树头节点不参与时的快乐值
     *                              + c子树头节点不参与时的快乐值
     *                              + ...;
     *      如果节点X不参与：
     *          节点X子树的快乐值 = max(a子树的头节点参与时快乐值, a子树的头节点不参与时快乐值)
     *                              + max(b子树的头节点参与时快乐值, a子树的头节点不参与时快乐值)
     *                              + max(c子树的头节点参与时快乐值, a子树的头节点不参与时快乐值)
     *                              + ...;
     *      对上面2种情况下的值取最大值
     */

    public static int getMaxHappyValue(Employee boss) {
        if (boss == null) {
            return 0;
        }
        Info info = doGetHappy(boss);
        return Math.max(info.headerJoinHappy, info.headerNotJoinComingHappy);
    }

    private static Info doGetHappy(Employee employee) {
        if (employee.subordinates.isEmpty()) {
            // 员工没有下属，说明已经到达树的最下边叶子节点了
            return new Info(employee.happy, 0);
        }
        // 当前节点X参与时，快乐值 = 当前节点的快乐值 + 各个子树头节点不参与时的快乐值
        // 当前节点X参与时的快乐值
        int headerJoinHappy = employee.happy;
        // 当前节点X不参与时的快乐值
        int headerNotJoinComingHappy = 0;
        for (Employee subordinate : employee.subordinates) {
            // 子树的快乐值信息
            Info subordinateInfo = doGetHappy(subordinate);
            // 当前节点X参与时，快乐值 = 当前节点的快乐值 + 各个子树头节点不参与时的快乐值;
            headerJoinHappy += subordinateInfo.headerNotJoinComingHappy;
            // 当前节点X不参与时，快乐值 = 各个子树的max(头节点不参与时的快乐值, 头节点参与时的快乐值);
            headerNotJoinComingHappy += Math.max(subordinateInfo.headerJoinHappy, subordinateInfo.headerNotJoinComingHappy);
        }
        return new Info(headerJoinHappy, headerNotJoinComingHappy);
    }

    /* ************************************************************************************************************** */

    private static class Employee {
        // 这个员工可以带来的快乐值
        int happy;
        // 这个员工有哪些直接下级
        List<Employee> subordinates;

        public Employee(int happy) {
            this.happy = happy;
            subordinates = new ArrayList<>();
        }
    }

    private static class Info {
        // 头节点参与的快乐值
        int headerJoinHappy;
        // 头节点不参与的快乐值
        int headerNotJoinComingHappy;

        Info(int headerJoinHappy, int headerNotJoinComingHappy) {
            this.headerJoinHappy = headerJoinHappy;
            this.headerNotJoinComingHappy = headerNotJoinComingHappy;
        }
    }

    /* ************************************************************************************************************** */

    /* 对数器 */

    public static int maxHappy(Employee boss) {
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
            int p1 = cur.happy;
            int p2 = 0;
            for (Employee next : cur.subordinates) {
                p1 += process1(next, true);
                p2 += process1(next, false);
            }
            return Math.max(p1, p2);
        }
    }

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
            if (getMaxHappyValue(boss) != maxHappy(boss)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
