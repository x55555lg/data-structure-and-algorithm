package com.lg.datastructure.graph.a2.practice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Xulg
 * Created in 2021-02-07 20:07
 */
@SuppressWarnings({"MapOrSetKeyShouldOverrideHashCodeEquals", "ForLoopReplaceableByForEach", "MismatchedQueryAndUpdateOfCollection"})
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

        for (int i = 0; i < matrix.length; i++) {
            int[] info = matrix[i];
            int weight = info[0];
            int from = info[1];
            int to = info[2];

            if (!graph.nodes.containsKey(from)) {
                graph.nodes.put(from, new Node(from));
            }
            if (!graph.nodes.containsKey(to)) {
                graph.nodes.put(to, new Node(to));
            }
            Node fromNode = graph.nodes.get(from);
            Node toNode = graph.nodes.get(to);

            fromNode.nexts.add(toNode);
            fromNode.out++;
            toNode.in++;

            Edge edge = new Edge(fromNode, toNode, weight);

            fromNode.edges.add(edge);

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
