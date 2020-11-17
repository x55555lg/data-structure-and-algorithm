package com.lg.algorithm.greedyalgorithm.practice;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import org.apache.commons.collections4.ListUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

/**
 * 利用贪心算法求解的题目
 *
 * @author Xulg
 * Created in 2020-11-09 18:06
 */
class ArrangeMeeting {

    /*
     * 一些项目要占用一个会议室宣讲，会议室不能同时容纳两个项目的宣讲。
     * 给你每一个项目的开始时间和结束时间，你来安排宣讲的日程，要求会议
     * 室进行的宣讲的场次最多，返回最多的宣讲场次。
     *-----------------------------------------------------------------
     * 暴力解法
     * 贪心解法
     *  结束时间早的排在前面，这样可以最多的安排会议。
     *  根据结束时间小的在前对所有会议排序。
     */

    /* 暴力解法 */

    public static int violence(Project[] projects) {
        if (projects == null || projects.length == 0) {
            return 0;
        }
        return recurse(projects, 0, 0);
    }

    private static int recurse(Project[] projects, int alreadyArrangeCount, int timeline) {
        if (projects.length == 0) {
            return alreadyArrangeCount;
        } else {
            int max = alreadyArrangeCount;
            for (int idx = 0; idx < projects.length; idx++) {
                if (projects[idx].startTime >= timeline) {
                    Project[] remainProjects = ArrayUtil.remove(projects, idx);
                    int count = recurse(remainProjects, alreadyArrangeCount + 1, projects[idx].endTime);
                    max = Math.max(max, count);
                }
            }
            return max;
        }
    }

    /* 贪心解法 */

    public static int greedy(Project[] projects) {
        if (projects == null || projects.length == 0) {
            return 0;
        }
        // 按照结束时间早的排前面的规则排序
        Arrays.sort(projects, new Comparator<Project>() {
            @Override
            public int compare(Project p1, Project p2) {
                return p1.endTime - p2.endTime;
            }
        });
        // 当前的时间点，从0点开始
        int timeline = 0;
        // 会议安排次数
        int count = 0;
        for (Project project : projects) {
            // 会议开始时间得在当前时间之后可以被安排进去啊
            if (project.startTime >= timeline) {
                // 安排次数加1
                count++;
                // 当前时间来到了这个会议的结束时间，后面安排的会议得在这个时间之后
                timeline = project.endTime;
            }
        }
        return count;
    }

    /* ****************************************************************************************************************/

    /* 对数器 */

    public static Project[] generatePrograms(int programSize, int timeMax) {
        Project[] ans = new Project[(int) (Math.random() * (programSize + 1))];
        for (int i = 0; i < ans.length; i++) {
            int r1 = (int) (Math.random() * (timeMax + 1));
            int r2 = (int) (Math.random() * (timeMax + 1));
            if (r1 == r2) {
                ans[i] = new Project(r1, r1 + 1);
            } else {
                ans[i] = new Project(Math.min(r1, r2), Math.max(r1, r2));
            }
        }
        return ans;
    }

    /* ****************************************************************************************************************/

    private static class Project {
        int startTime;
        int endTime;

        Project(int startTime, int endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }
    }

    /* ****************************************************************************************************************/

    public static void main(String[] args) {
        int programSize = 12;
        int timeMax = 20;
        int timeTimes = 1000000;
        for (int i = 0; i < timeTimes; i++) {
            Project[] programs = generatePrograms(programSize, timeMax);
            int r1 = greedy(programs);
            int r2 = violence(programs);
            if (r2 != r1) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");

        Project project1 = new Project(4, 10);
        Project project2 = new Project(12, 20);
        Project project3 = new Project(2, 18);
        Project[] projects = {project1, project2, project3};
        int violence = violence(projects);
        System.out.println(violence);

        List<List<Project>> list = aaaaaaaaaa(projects);
        for (List<Project> ps : list) {
            for (Project project : ps) {
                System.out.print(project.startTime + "~" + project.endTime + ", ");
            }
            System.out.println();
        }
    }

    public static List<List<Project>> aaaaaaaaaa(Project[] projects) {
        if (projects == null || projects.length == 0) {
            return new ArrayList<>(0);
        }
        List<List<Project>> list = new ArrayList<>();
        ArrayList<Project> path = new ArrayList<>();
        process(projects, new HashSet<>(), path, list);
        return list;
    }

    @SuppressWarnings("all")
    private static void process(Project[] projects, HashSet<Project> alreadyUsed,
                                List<Project> path, List<List<Project>> all) {
        // base case
        if (projects.length == alreadyUsed.size()) {
            // 对排列组合结果path进行判定是否符合预期结果
            // 删除时间不符的会议
            HashSet<Project> needDelete = new HashSet<>();
            for (int i = 0; i < path.size(); i++) {
                for (int j = i + 1; j < path.size(); j++) {
                    if (path.get(i).endTime > path.get(j).startTime) {
                        needDelete.add(path.get(j));
                    }
                }
            }
            if (!needDelete.isEmpty()) {
                for (Project project : needDelete) {
                    path.remove(project);
                }
            }
            if (!path.isEmpty()) {
                // 这种排列有没有添加过了
                boolean isDistinct = true;
                for (List<Project> projectList : all) {
                    if (ListUtils.isEqualList(path, projectList)) {
                        isDistinct = false;
                        break;
                    }
                    if (path.get(0).equals(projectList.get(0))) {
                        isDistinct = false;
                        break;
                    }
                }
                if (isDistinct) {
                    all.add(path);
                }
            }
        } else {
            for (Project project : projects) {
                if (!alreadyUsed.contains(project)) {
                    alreadyUsed.add(project);

                    ArrayList<Project> newPath = new ArrayList<>(path);
                    newPath.add(project);

                    process(projects, alreadyUsed, newPath, all);
                    alreadyUsed.remove(project);
                }
            }
        }
    }
}
