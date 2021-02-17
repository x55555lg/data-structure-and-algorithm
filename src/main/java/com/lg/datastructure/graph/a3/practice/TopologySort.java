package com.lg.datastructure.graph.a3.practice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Xulg
 * Created in 2021-02-15 21:27
 */
class TopologySort {

    public static List<Node> sort(Graph graph) {
        if (graph == null) {
            return null;
        }
        if (graph.nodes.isEmpty()) {
            return new ArrayList<>();
        }

        List<Node> list = new ArrayList<>();

        // 记录每个节点的入度值
        HashMap<Node, Integer> nodeInDegreeMap = new HashMap<>(16);
        Queue<Node> queue = new LinkedList<>();
        for (Node node : graph.nodes.values()) {
            nodeInDegreeMap.put(node, node.in);
            if (node.in == 0) {
                queue.add(node);
            }
        }
        while (!queue.isEmpty()) {
            // 弹出入度为0的节点
            Node node = queue.poll();
            // 加入排序结果中
            list.add(node);
            // 删除节点，该节点的邻接点的入度减1
            for (Node nextNode : node.nexts) {
                nodeInDegreeMap.put(nextNode, nodeInDegreeMap.get(nextNode) - 1);
                if (nodeInDegreeMap.get(nextNode) == 0) {
                    // 队列中加入入度为0的节点
                    queue.add(nextNode);
                }
            }
        }
        return list;
    }

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
