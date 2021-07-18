package com.practice.graph;

/**
 * @author Xulg
 * Created in 2021-05-12 11:06
 */
class Edge {

    /**
     * 边的开始节点，边从哪个节点出发的
     */
    Node from;

    /**
     * 边的截止节点，边指向哪个节点的
     */
    Node to;

    /**
     * 边的权重
     */
    int weight;

    Edge(Node from, Node to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
