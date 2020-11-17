package com.lg.algorithm.greedyalgorithm.practice;

import cn.hutool.core.util.ArrayUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 利用贪心算法求解的题目
 *
 * @author Xulg
 * Created in 2020-11-12 10:00
 */
class MaximumProfits {

    /*
     * 输入：正数数组costs[]，正数数组profits[]，正数K，正数M
     *  costs[i]    表示i号项目的花费
     *  profits[i]  表示i号项目在扣除花费之后还能挣到的钱(利润)
     *  K表示你只能串行的最多做K个项目，即你最多只能做K个项目
     *  M表示你初始的资金
     * 说明：每做完一个项目，马上获得收益，可以支持你去做下一个项目。不能并行的做项目。
     * 输出：你最后获得的最大钱数
     *--------------------------------------------------------------------------------
     * 暴力解法思路：
     *  排列组合每一种投资组合
     * 贪心解法思路：
     *  每一次都选择自己能投资下的，收益最高的项目
     *      1.先把所有项目按照成本大小存入小根堆
     *      2.从小根堆中获取可以投资的项目，将这些项目按收益大小存入大根堆
     *      3.获取大根堆的堆顶。这个项目就是当前可以投资的，收益最大的项目
     */

    /* 暴力解法 */

    public static int violence(int[] costs, int[] profits, int k, int m) {
        if (costs == null || costs.length == 0
                || profits == null || profits.length == 0) {
            return m;
        }
        assert costs.length == profits.length;
        Project[] projects = new Project[costs.length];
        for (int i = 0; i < costs.length; i++) {
            projects[i] = new Project(costs[i], profits[i]);
        }
        List<List<Project>> all = new ArrayList<>();
        recurse(projects, new ArrayList<>(), k, m, all);
        if (all.isEmpty()) {
            return m;
        }
        // 找出收益最大的组合
        List<Integer> list = new ArrayList<>();
        for (List<Project> path : all) {
            int profitSum = m;
            for (Project project : path) {
                profitSum += project.profit;
            }
            list.add(profitSum);
        }
        int max = list.get(0);
        for (Integer profit : list) {
            max = Math.max(max, profit);
        }
        return max;
    }

    private static void recurse(Project[] remainProjects, List<Project> path,
                                int remainK, int money, List<List<Project>> all) {
        // base case: 没有剩余可用的项目了或者投资次数没有了
        if (remainProjects.length == 0 || remainK == 0) {
            all.add(path);
        } else {
            for (int i = 0; i < remainProjects.length; i++) {
                if (remainProjects[i].cost <= money) {
                    List<Project> newPath = new ArrayList<>(path);
                    newPath.add(remainProjects[i]);

                    int newRemainK = remainK - 1;
                    int newMoney = money + remainProjects[i].profit;
                    Project[] newRemainProjects = ArrayUtil.remove(remainProjects, i);

                    recurse(newRemainProjects, newPath, newRemainK, newMoney, all);
                }
            }
            // base case: 没有可以投资的项目么？
            if (!path.isEmpty()) {
                all.add(path);
            }
        }
    }

    /* 贪心解法 */

    public static int greedy(int[] costs, int[] profits, int k, int m) {
        if (costs == null || costs.length == 0
                || profits == null || profits.length == 0) {
            return m;
        }
        assert costs.length == profits.length;
        // 将所有的项目按照成本从小到大存入小根堆
        PriorityQueue<Project> smallHeap = new PriorityQueue<>((p1, p2) -> p1.cost - p2.cost);
        for (int i = 0; i < costs.length; i++) {
            smallHeap.add(new Project(costs[i], profits[i]));
        }
        // 所有的项目按照收益从大到小排列存入大根堆
        PriorityQueue<Project> bigHeap = new PriorityQueue<>((p1, p2) -> p2.profit - p1.profit);
        // 总共可以投资k次
        for (int time = 0; time < k; time++) {
            // 从小根堆中弹出所有可以投资的项目，存入到大根堆中
            while (!smallHeap.isEmpty()) {
                // 手头的钱够投资这个项目，就弹出存入大根堆
                if (smallHeap.peek().cost <= m) {
                    bigHeap.add(smallHeap.poll());
                } else {
                    // 堆顶的项目是成本最小的都投资不了，那就没了
                    break;
                }
            }
            // 这里表示没有项目可以投资啊，那么手头的钱还是m
            if (bigHeap.isEmpty()) {
                return m;
            }
            // 大根堆的堆顶就是可以投资的最大收益的项目
            Project maxProfitProject = bigHeap.poll();
            // 手头的钱加上项目的利润
            m = m + maxProfitProject.profit;
        }
        return m;
    }

    /* ****************************************************************************************************************/

    /* 对数器 */

    private static Project[] generateRandomProjects(int projectSize, int maxCost, int minCost,
                                                    int maxProfit, int minProfit) {
        Project[] ans = new Project[(int) ((projectSize + 1) * Math.random())];
        for (int i = 0; i < ans.length; i++) {
            int cost = RandomUtils.nextInt(minCost, maxCost + 1);
            int profit = RandomUtils.nextInt(minProfit, maxProfit + 1);
            ans[i] = new Project(cost, profit);
        }
        return ans;
    }

    /* ****************************************************************************************************************/

    private static class Project {
        private final int cost;
        private final int profit;

        Project(int cost, int profit) {
            this.cost = cost;
            this.profit = profit;
        }

        public int getCost() {
            return cost;
        }

        public int getProfit() {
            return profit;
        }
    }

    /* ****************************************************************************************************************/

    public static void main(String[] args) {
        int arrLen = 6;
        int minCost = 1;
        int maxCost = 10;
        int minProfit = 1;
        int maxProfit = 10;
        int testTimes = 10000000;
        for (int time = 0; time < testTimes; time++) {
            int k = RandomUtils.nextInt(1, arrLen);
            int m = RandomUtils.nextInt(minCost, maxCost + 1);
            Project[] projects = generateRandomProjects(arrLen, maxCost, minCost, maxProfit, minProfit);
            int[] costs = new int[projects.length];
            int[] profits = new int[projects.length];
            for (int i = 0; i < profits.length; i++) {
                costs[i] = projects[i].cost;
                profits[i] = projects[i].profit;
            }
            int r1 = violence(costs, profits, k, m);
            int r2 = greedy(costs, profits, k, m);
            int r3 = findMaximizedCapital(k, m, profits, costs);
            if (!(r1 == r2 && r2 == r3)) {
                System.err.println("k=" + k + ", m=" + m + ", projects=" + JSON.toJSONString(projects)
                        + ", r1=" + r1 + ", r2=" + r2 + ", r3=" + r3);
            }
        }
        System.out.println("finish!!!");
    }

    /**
     * 视频中的写法
     */
    @SuppressWarnings("all")
    public static int findMaximizedCapital(int K, int W, int[] Profits, int[] Capital) {
        PriorityQueue<Project> minCostQ = new PriorityQueue<>((o1, o2) -> o1.cost - o2.cost);
        PriorityQueue<Project> maxProfitQ = new PriorityQueue<>((o1, o2) -> o2.profit - o1.profit);
        for (int i = 0; i < Profits.length; i++) {
            minCostQ.add(new Project(Capital[i], Profits[i]));
        }
        for (int i = 0; i < K; i++) {
            while (!minCostQ.isEmpty() && minCostQ.peek().cost <= W) {
                maxProfitQ.add(minCostQ.poll());
            }
            if (maxProfitQ.isEmpty()) {
                return W;
            }
            W += maxProfitQ.poll().profit;
        }
        return W;
    }
}
