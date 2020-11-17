package com.lg.datastructure.graph.base;

/**
 * 数据结构图：边
 *
 * @author Xulg
 * Created in 2020-11-17 13:48
 */
@SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal"})
public class Edge {

    /**
     * 边的开始节点，边从哪个节点出发的
     */
    public Node from;

    /**
     * 边的截止节点，边指向哪个节点的
     */
    public Node to;

    /**
     * 边的权重
     */
    public int weight;

    public Edge(Node from, Node to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
}
