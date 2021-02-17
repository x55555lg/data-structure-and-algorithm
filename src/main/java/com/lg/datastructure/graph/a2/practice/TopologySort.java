package com.lg.datastructure.graph.a2.practice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Xulg
 * Created in 2021-02-07 20:36
 */
@SuppressWarnings({"MapOrSetKeyShouldOverrideHashCodeEquals", "MismatchedQueryAndUpdateOfCollection"})
class TopologySort {

    public static List<Node> sort(Graph graph) {
        if (graph == null || graph.nodes.isEmpty()) {
            return null;
        }

        List<Node> sortedNodes = new ArrayList<>();

        // 记录每个节点的入度值
        HashMap<Node, Integer> nodeInMap = new HashMap<>(16);
        // 入度为0的节点进队列
        Queue<Node> queue = new LinkedList<>();
        for (Node node : graph.nodes.values()) {
            if (node.in == 0) {
                queue.add(node);
            }
            nodeInMap.put(node, node.in);
        }

        while (!queue.isEmpty()) {
            Node node = queue.poll();
            // node的入度已为0,加入结果列表
            sortedNodes.add(node);
            // node的所有邻接点的入度都要减1
            for (Node nextNode : node.nexts) {
                nodeInMap.put(nextNode, nodeInMap.get(nextNode) - 1);
                if (nodeInMap.get(nextNode) == 0) {
                    queue.add(nextNode);
                }
            }
        }

        return sortedNodes;
    }

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
