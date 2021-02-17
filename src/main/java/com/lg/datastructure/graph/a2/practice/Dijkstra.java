package com.lg.datastructure.graph.a2.practice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Xulg
 * Created in 2021-02-07 21:44
 */
@SuppressWarnings("MapOrSetKeyShouldOverrideHashCodeEquals")
class Dijkstra {

    public static Map<Node, Integer> getMinimumDistance(Node sourceNode) {
        if (sourceNode == null) {
            return null;
        }

        // 记录源节点到各个节点的距离
        HashMap<Node, Integer> distanceMap = new HashMap<>(16);
        distanceMap.put(sourceNode, 0);

        // 已选择过的节点
        HashSet<Node> alreadySelectedNodes = new HashSet<>();

        Node minimumNode = getMinimumAndUnselectedNode(distanceMap, alreadySelectedNodes);
        while (minimumNode != null) {
            // 源节点到最小节点的距离
            int distance = distanceMap.get(minimumNode);

            for (Edge edge : minimumNode.edges) {
                Node toNode = edge.to;
                // source->to = source->from + edit.weight
                int distanceBetweenSourceAndDest = distance + edge.weight;
                if (distanceMap.containsKey(toNode)) {
                    distanceBetweenSourceAndDest = Math.min(distanceBetweenSourceAndDest, distanceMap.get(toNode));
                }
                distanceMap.put(toNode, distanceBetweenSourceAndDest);
            }

            // 标记已选过
            alreadySelectedNodes.add(minimumNode);
            // check next one
            minimumNode = getMinimumAndUnselectedNode(distanceMap, alreadySelectedNodes);
        }

        return distanceMap;
    }

    // O(N) N为节点数
    private static Node getMinimumAndUnselectedNode(HashMap<Node, Integer> distanceMap,
                                                    HashSet<Node> alreadySelectedNodes) {
        Node minimumNode = null;
        int minimumDistance = Integer.MAX_VALUE;
        for (Entry<Node, Integer> entry : distanceMap.entrySet()) {
            // 目的节点
            Node destNode = entry.getKey();
            // 源节点到目的节点的距离
            int distance = entry.getValue();
            if (!alreadySelectedNodes.contains(destNode) && distance < minimumDistance) {
                minimumNode = destNode;
                minimumDistance = distance;
            }
        }
        return minimumNode;
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
