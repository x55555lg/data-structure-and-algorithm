package com.practice.graph;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Xulg
 * Created in 2021-05-12 11:08
 */
class Graph {

    /**
     * 值-节点映射表
     */
    final HashMap<Integer, Node> nodes;

    /**
     * 图中所有的边
     */
    final HashSet<Edge> edges;

    Graph() {
        nodes = new HashMap<>();
        edges = new HashSet<>();
    }
}
