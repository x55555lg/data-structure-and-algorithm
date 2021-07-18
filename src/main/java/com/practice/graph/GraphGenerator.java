package com.practice.graph;

/**
 * @author Xulg
 * Created in 2021-05-12 11:37
 */
class GraphGenerator {

    /*
     * 将如下这种图结构转成自己的图结构
     * [ [7, 5, 4], [4, 1, 3], [3, 2, 3], [8, 2, 4] ]
     * 每个子数组从左到右分别表示[weight, from, to]
     * 比如：[7, 0, 4]  weight = 7    fromNode = 5    toNode = 4
     */

    public static Graph createGraph(Integer[][] matrix) {
        if (matrix == null) {
            return null;
        }
        if (matrix.length == 0) {
            return new Graph();
        }
        Graph graph = new Graph();
        for (Integer[] info : matrix) {
            int weight = info[0], fromValue = info[1], toValue = info[2];
            if (!graph.nodes.containsKey(fromValue)) {
                graph.nodes.put(fromValue, new Node(fromValue));
            }
            if (!graph.nodes.containsKey(toValue)) {
                graph.nodes.put(toValue, new Node(toValue));
            }
            Node fromNode = graph.nodes.get(fromValue);
            Node toNode = graph.nodes.get(toValue);
            // from节点出度增加
            fromNode.out++;
            // to节点入度增加
            toNode.in++;
            // from节点的邻接节点是to节点
            fromNode.nexts.add(toNode);

            // 创建边
            Edge edge = new Edge(fromNode, toNode, weight);
            // from节点可以到的边
            fromNode.edges.add(edge);
            // 图中加入节点，边
            graph.edges.add(edge);
        }
        return graph;
    }

}
