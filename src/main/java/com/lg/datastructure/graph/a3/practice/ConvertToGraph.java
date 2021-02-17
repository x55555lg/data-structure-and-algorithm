package com.lg.datastructure.graph.a3.practice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Xulg
 * Created in 2021-02-15 20:26
 */
@SuppressWarnings("MapOrSetKeyShouldOverrideHashCodeEquals")
class ConvertToGraph {

    /**
     * 如下的图表示结构：
     * [
     * [7, 5, 4],
     * [4, 1, 3],
     * [3, 2, 3],
     * [8, 2, 4]
     * ]
     * 值表示节点编号，每个子数组从左到右分别表示[weight, from, to]，比如，
     * [7, 5, 4]  weight = 7    fromNode = 5    toNode = 4
     *
     * @param matrix 图矩阵
     * @return the graph
     */
    public static Graph convert(int[][] matrix) {
        if (matrix == null) {
            return null;
        }
        Graph graph = new Graph();
        for (int[] info : matrix) {
            int fromVal = info[1], toVal = info[2], weight = info[0];
            // 将节点加入图中
            if (!graph.nodes.containsKey(fromVal)) {
                graph.nodes.put(fromVal, new Node(fromVal));
            }
            // 将节点加入图中
            if (!graph.nodes.containsKey(toVal)) {
                graph.nodes.put(toVal, new Node(toVal));
            }
            Node fromNode = graph.nodes.get(fromVal);
            Node toNode = graph.nodes.get(toVal);
            Edge edge = new Edge(fromNode, toNode, weight);
            // from节点的邻接点增加
            fromNode.nexts.add(toNode);
            // from节点出发的边增加
            fromNode.edges.add(edge);
            // from节点的出度增加
            fromNode.out++;
            // to节点的入度增加
            toNode.in++;
            // 边加入图中
            graph.edges.add(edge);
        }
        return graph;
    }

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
