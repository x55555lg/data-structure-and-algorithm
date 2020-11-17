package com.lg.datastructure.graph;

import com.lg.datastructure.graph.base.Edge;
import com.lg.datastructure.graph.base.Graph;
import com.lg.datastructure.graph.base.Node;

/**
 * 将别的图结构转成自己的图结构
 *
 * @author Xulg
 * Created in 2020-11-17 18:13
 */
class GraphGenerator {

    /**
     * 将如下这种图结构转成自己的图结构
     * [ [7, 5, 4], [4, 1, 3], [3, 2, 3], [8, 2, 4] ]
     * 每个子数组从左到右分别表示[weight, from, to]
     * 比如：[7, 0, 4]  weight = 7    fromNode = 5    toNode = 4
     */
    @SuppressWarnings({"ForLoopReplaceableByForEach", "AlibabaRemoveCommentedCode", "MapOrSetKeyShouldOverrideHashCodeEquals"})
    public static Graph createGraph(Integer[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return null;
        }
        Graph graph = new Graph();
        for (int i = 0; i < matrix.length; i++) {
            Integer fromNodeValue = matrix[i][1];
            Integer toNodeValue = matrix[i][2];
            Integer weight = matrix[i][0];

            // 创建from节点和to节点，图中已存在这个节点则不创建了
            if (!graph.nodes.containsKey(fromNodeValue)) {
                graph.nodes.put(fromNodeValue, new Node(fromNodeValue));
            }
            if (!graph.nodes.containsKey(toNodeValue)) {
                graph.nodes.put(toNodeValue, new Node(toNodeValue));
            }

            // 拿到from节点和to节点
            Node fromNode = graph.nodes.get(fromNodeValue);
            Node toNode = graph.nodes.get(toNodeValue);

            /*
             * 修改from节点的出度，邻接点信息
             *  出度：  因为from节点指向to节点，所以from节点出度加1
             *  邻接点：to节点就是from节点的邻接点
             */
            fromNode.out++;
            fromNode.nexts.add(toNode);

            /*
             * 修改to节点的入度
             *  入度：  因为from节点指向to节点，所有to节点的入度加1
             */
            toNode.in++;

            // 创建from节点和to节点的边关系
            Edge edge = new Edge(fromNode, toNode, weight);

            // 将边加入到from节点，因为这个边是从from节点出发的
            fromNode.edges.add(edge);

            // 将边加入到图中
            graph.edges.add(edge);
        }
        return graph;
    }
}
