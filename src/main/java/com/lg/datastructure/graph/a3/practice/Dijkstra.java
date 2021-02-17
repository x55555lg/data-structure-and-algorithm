package com.lg.datastructure.graph.a3.practice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Xulg
 * Created in 2021-02-15 21:42
 */
class Dijkstra {

    /*
     * 求图的最小距离
     */

    public static Map<Node, Integer> getMinDistance(Graph graph, Node sourceNode) {
        if (graph == null || sourceNode == null) {
            return null;
        }

        // sourceNode到各个节点的距离
        HashMap<Node, Integer> distanceMap = new HashMap<>(16);
        distanceMap.put(sourceNode, 0);

        // 已选择过的节点
        HashSet<Node> selectedNodes = new HashSet<>();

        Node minNode = getUnselectedAndMinimumDistanceNode(distanceMap, selectedNodes);
        while (minNode != null) {
            int distance = distanceMap.get(minNode);
            for (Edge edge : minNode.edges) {
                assert edge.from == minNode;
                Node toNode = edge.to;
                if (distanceMap.containsKey(toNode)) {
                    distanceMap.put(toNode, Math.min(distance + edge.weight, distanceMap.get(toNode)));
                } else {
                    distanceMap.put(toNode, distance + edge.weight);
                }
            }
            selectedNodes.add(minNode);
            minNode = getUnselectedAndMinimumDistanceNode(distanceMap, selectedNodes);
        }
        return distanceMap;
    }

    private static Node getUnselectedAndMinimumDistanceNode(HashMap<Node, Integer> distanceMap, HashSet<Node> selectedNodes) {
        int minDistance = Integer.MAX_VALUE;
        Node minNode = null;
        for (Entry<Node, Integer> entry : distanceMap.entrySet()) {
            Node node = entry.getKey();
            int distance = entry.getValue();
            if (!selectedNodes.contains(node) && distance < minDistance) {
                minDistance = distance;
                minNode = node;
            }
        }
        return minNode;
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
