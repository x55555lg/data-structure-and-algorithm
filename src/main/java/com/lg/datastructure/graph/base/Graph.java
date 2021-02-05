package com.lg.datastructure.graph.base;

import java.util.HashMap;
import java.util.HashSet;

/**
 * 数据结构图的表示模型
 * 不熟悉的图结构都可以转化成这种图结构，再用这种图结构去做题
 *
 * @author Xulg
 * Created in 2020-11-17 11:23
 */
@SuppressWarnings({"MapOrSetKeyShouldOverrideHashCodeEquals", "FieldCanBeLocal", "FieldMayBeFinal", "unused"})
public class Graph {

    /**
     * 图中的节点
     * key为节点的编号
     * value为节点
     */
    public HashMap<Integer, Node> nodes;

    /**
     * 图中的边
     */
    public HashSet<Edge> edges;

    public Graph() {
        nodes = new HashMap<>();
        edges = new HashSet<>();
    }

}
