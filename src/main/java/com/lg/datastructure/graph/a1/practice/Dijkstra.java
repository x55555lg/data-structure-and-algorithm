package com.lg.datastructure.graph.a1.practice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Xulg
 * Created in 2021-02-03 15:09
 */
@SuppressWarnings("MapOrSetKeyShouldOverrideHashCodeEquals")
class Dijkstra {

    /*
     * Dijkstra算法
     * 算最短路径的，算法解决的是有向图中单个源接点到其他顶点的最短路径问题
     * 所谓的最短是指权重和最小
     *  1.Dijkstra算法必须指定一个源点
     *  2.生成一个源点到各个点的最小距离表，一开始只有一条记录，即原点到
     *    自己的最小距离为0，源点到其他所有点的最小距离都为正无穷大
     *  3.从距离表中拿出没拿过记录里的最小记录，通过这个点发出的边，更新
     *    源点到各个点的最小距离表，不断重复这一步
     *  4.源点到所有的点记录如果都被拿过一遍，过程停止，最小距离表得到了
     */

    public static Map<Node, Integer> minDistance(Node sourceNode) {
        if (sourceNode == null) {
            return null;
        }

        /*
         * 从源节点点出发到所有点的最小距离表
         *      key:     从源节点出发到达的目的节点
         *      value:   从源节点出发到达目的节点的最小距离
         * 初始状态只有一条数据，即源节点到自己的距离为0
         * 如果在表中但没有T节点的记录，含义是从源节点出发到T这个点的距离为正无穷
         */
        HashMap<Node, Integer> distanceMap = new HashMap<>(16);
        distanceMap.put(sourceNode, 0);

        // 记录表，记录哪些节点选择过了
        HashSet<Node> selectedNodes = new HashSet<>();

        // 第一次获取的一定是源节点
        Node minNode = getMinDistanceAndUnselectedNode(distanceMap, selectedNodes);
        while (minNode != null) {
            // 获取源节点到minNode节点的距离
            int distance = distanceMap.get(minNode);
            for (Edge edge : minNode.edges) {
                assert minNode == edge.from : "what the hell?";
                /*
                 * minNode节点就是边的from节点，即minNode == edge.from
                 * 源节点到toNode节点的距离 = 源节点到minNode节点的距离 + minNode节点到toNode节点的距离
                 * minNode节点到toNode节点的距离就是边的权重
                 * source->to = source->from + from->to
                 */
                Node toNode = edge.to;
                int sourceToDistance = distance + edge.weight;
                if (distanceMap.containsKey(toNode)) {
                    // 距离表中已经有toNode点的距离信息了，则取小的那个距离保持
                    distanceMap.put(toNode, Math.min(distanceMap.get(toNode), sourceToDistance));
                } else {
                    distanceMap.put(toNode, sourceToDistance);
                }
            }
            // 标记这个节点已经选择过了
            selectedNodes.add(minNode);
            // 重新获取一个未选择过的最小距离节点
            minNode = getMinDistanceAndUnselectedNode(distanceMap, selectedNodes);
        }

        return distanceMap;
    }

    private static Node getMinDistanceAndUnselectedNode(HashMap<Node, Integer> distanceMap,
                                                        HashSet<Node> selectedNodes) {
        // 挑选出没被选则过，距离最小的节点
        Node minNode = null;
        Integer minDistance = Integer.MAX_VALUE;
        for (Entry<Node, Integer> entry : distanceMap.entrySet()) {
            Node node = entry.getKey();
            Integer distance = entry.getValue();
            if (!selectedNodes.contains(node)) {
                if (distance < minDistance) {
                    minNode = node;
                    minDistance = distance;
                }
            }
        }
        return minNode;
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
