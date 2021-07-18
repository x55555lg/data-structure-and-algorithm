package com.practice.graph;

import java.util.ArrayList;

/**
 * @author Xulg
 * Created in 2021-05-12 11:02
 */
class Node {

    /**
     * 节点的值
     * 节点的唯一标识
     */
    int value;

    /**
     * 入度
     */
    int in;

    /**
     * 出度
     */
    int out;

    /**
     * 邻接节点列表
     * 当前节点出发，能走到的节点有哪些
     */
    final ArrayList<Node> nexts;

    /**
     * 从当前节点出发能到的边有哪些
     */
    final ArrayList<Edge> edges;

    Node(int value) {
        this.value = value;
        in = 0;
        out = 0;
        nexts = new ArrayList<>();
        edges = new ArrayList<>();
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
