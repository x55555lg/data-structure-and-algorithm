package com.lg.datastructure.graph.a1.practice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 图的拓扑排序
 *
 * @author Xulg
 * Created in 2021-02-03 10:11
 */
@SuppressWarnings("MapOrSetKeyShouldOverrideHashCodeEquals")
class TopologySort {

    /*
     *有向无环图的拓扑排序
     * 前提：必须是有向无环图
     * 应用：事件安排，编译顺序
     * 什么是拓扑排序：
     *          ③→⑤→⑦→⑨
     *              ↑
     *              ④←⑧
     *      事件③的执行前提是事件⑤已经执行，而事件⑤又依赖事件⑦的执行......
     *      拓扑排序可以将这些事件进行排序，哪个先执行哪个后执行。
     * 实现思路：
     *      1.在图中找到所有入度为0的点输出
     *      2.把所有的入度为0的点从图中删除，继续寻找入度为0的点输出，周而复始
     *      3.图的所有点都被删除后，依次输出的顺序就是拓扑排序
     */

    public static List<Node> sort(Graph graph) {
        if (graph == null) {
            return null;
        }
        if (graph.nodes.isEmpty()) {
            return new ArrayList<>(0);
        }

        // 存放排序后节点的列表
        ArrayList<Node> sortedNodes = new ArrayList<>();

        /*
         * 记录每个节点的剩余的入度是多少的，第一次加入节点的时候，节点的剩余入度就是节点的初始入度值
         * key：  节点本身
         * value：节点的剩余入度
         */
        HashMap<Node, Integer> nodeInMap = new HashMap<>(16);

        // 记录入度为0的节点有哪些
        Queue<Node> queue = new LinkedList<>();

        for (Node node : graph.nodes.values()) {
            // key为节点 value为节点的入度
            nodeInMap.put(node, node.in);
            // 入度为0的节点加入队列
            if (node.in == 0) {
                queue.add(node);
            }
        }

        // 遍历所有入度为0的节点
        while (!queue.isEmpty()) {
            // 弹出入度为0的节点
            Node node = queue.poll();
            // 加入结果中
            sortedNodes.add(node);
            // 遍历弹出节点的所有邻接点
            for (Node nextNode : node.nexts) {
                // 更新邻节点的入度信息(即入度减1)，如果入度为0了加入队列
                int inDegree = nodeInMap.get(nextNode) - 1;
                nodeInMap.put(nextNode, inDegree);
                if (inDegree == 0) {
                    queue.add(nextNode);
                }
            }
        }
        return sortedNodes;
    }

    /* ****************************************************************************************************************/
    /* ****************************************************************************************************************/
    /* ****************************************************************************************************************/

    private static class Node {
        int value;
        int in;
        int out;
        ArrayList<Node> nexts;
        ArrayList<Edge> edges;

        Node(int value) {
            this.value = value;
            in = 0;
            out = 0;
            nexts = new ArrayList<>();
            edges = new ArrayList<>();
        }
    }

    private static class Edge {
        Node from;
        Node to;
        int weight;

        Edge(Node from, Node to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }

    private static class Graph {
        HashMap<Integer, Node> nodes;
        HashSet<Edge> edges;

        Graph() {
            nodes = new HashMap<>();
            edges = new HashSet<>();
        }
    }

}
