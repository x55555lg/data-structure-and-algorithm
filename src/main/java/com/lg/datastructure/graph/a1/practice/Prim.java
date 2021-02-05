package com.lg.datastructure.graph.a1.practice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * @author Xulg
 * Created in 2021-02-03 14:18
 */
@SuppressWarnings({"MapOrSetKeyShouldOverrideHashCodeEquals"})
class Prim {

    /*
     * Prim算法-最小生成树
     *  实现思路：
     *      1.可以从任意节点出发来寻找最小生成树
     *      2.某个点加入到被选取的点中后，解锁这个点出发的所有新的边
     *      3.在所有解锁的边中选最小的边，然后看看这个边会不会形成环
     *      4.如果会，不要当前边，继续考察剩下解锁的边中最小的边，重复3
     *      5.如果不会，要当前边，将该边的指向点加入到被选取的点中，重复2
     *      6.当所有点都被选取，最小生成树就得到了
     */

    /**
     * Prim(普里姆)算法求图的最小生成树
     *
     * @param graph the graph
     * @return the minimum tree
     */
    public static Set<Edge> prim(Graph graph) {
        if (graph == null) {
            return null;
        }
        if (graph.nodes.isEmpty() || graph.edges.isEmpty()) {
            return new HashSet<>(0);
        }

        // 最小生成树的集合
        HashSet<Edge> minimumTree = new HashSet<>();
        // 按照边的权重从小到大排列，堆顶永远是权重最小的边
        PriorityQueue<Edge> smallHeap = new PriorityQueue<>((e1, e2) -> e1.weight - e2.weight);
        // 记录已经解锁过的节点
        HashSet<Node> recordedNodes = new HashSet<>();

        // loop all nodes
        for (Node node : graph.nodes.values()) {
            if (recordedNodes.contains(node)) {
                // 已解锁过的节点忽略
                continue;
            }
            // 标记当前节点已解锁过了
            recordedNodes.add(node);
            // 将解锁的节点的边都加入堆中，选择权重最小的边
            smallHeap.addAll(node.edges);
            // 在所有解锁的边中选最小的边
            while (!smallHeap.isEmpty()) {
                Edge edge = smallHeap.poll();
                Node toNode = edge.to;
                // 不会形成环
                if (!recordedNodes.contains(toNode)) {
                    minimumTree.add(edge);
                    // 将该边的指向点加入到被选取的点中，即解锁该点
                    recordedNodes.add(toNode);
                    // 解锁从to节点出发的所有边，加入小根堆
                    smallHeap.addAll(toNode.edges);
                }
            }
        }
        return minimumTree;
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

        Node(int val) {
            value = val;
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
