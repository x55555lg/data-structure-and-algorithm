package com.lg.datastructure.graph.a1.practice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * 将别的结果转成通用的图结构s
 *
 * @author Xulg
 * Created in 2021-02-02 14:32
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
        if (matrix == null || matrix.length == 0) {
            return null;
        }
        Graph graph = new Graph();
        for (int[] info : matrix) {
            // 边的权重
            int weight = info[0];
            // from节点的编号
            int fromValue = info[1];
            // to节点的编号
            int toValue = info[2];

            // add the from node to the graph if absent
            graph.nodes.putIfAbsent(fromValue, new Node(fromValue, 0, 0));
            // add the to node to the graph if absent
            graph.nodes.putIfAbsent(toValue, new Node(toValue, 0, 0));
            Node fromNode = graph.nodes.get(fromValue);
            Node toNode = graph.nodes.get(toValue);

            //from节点的邻接点加入
            fromNode.nexts.add(toNode);
            // from节点的出度+1
            fromNode.out++;
            // to节点的入度+1
            toNode.in++;

            Edge edge = new Edge(fromNode, toNode, weight);

            // from节点加入边
            fromNode.edges.add(edge);

            // add the edge to the graph
            graph.edges.add(edge);
        }
        return graph;
    }

    private static class Node {
        // 节点的编号|值
        int value;
        // 入度
        int in;
        // 出度
        int out;
        // 邻接节点
        ArrayList<Node> nexts;
        // 从当前节点X出发的，有哪些边
        ArrayList<Edge> edges;

        Node(int value, int in, int out) {
            this.value = value;
            this.in = in;
            this.out = out;
            nexts = new ArrayList<>();
            edges = new ArrayList<>();
        }
    }

    private static class Edge {
        // 边的起点
        Node from;
        // 边的终点
        Node to;
        // 边的权重
        int weight;

        Edge(Node from, Node to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }

    private static class Graph {
        // 所有的节点
        HashMap<Integer, Node> nodes;
        // 所有的边
        HashSet<Edge> edges;

        Graph() {
            nodes = new HashMap<>();
            edges = new HashSet<>();
        }
    }

}
