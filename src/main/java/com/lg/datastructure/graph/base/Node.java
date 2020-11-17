package com.lg.datastructure.graph.base;

import java.util.ArrayList;

/**
 * 数据结构图：节点
 *
 * @author Xulg
 * Created in 2020-11-17 11:23
 */
public class Node {

    /**
     * 节点的值|节点的编号
     */
    public int value;

    /**
     * 节点的入度
     */
    public int in;

    /**
     * 节点的出度
     */
    public int out;

    /**
     * 节点的邻接点有哪些
     */
    public ArrayList<Node> nexts;

    /**
     * 从当前节点X出发的，有哪些边
     */
    public ArrayList<Edge> edges;

    public Node(int value) {
        this.value = value;
        in = 0;
        out = 0;
        nexts = new ArrayList<>();
        edges = new ArrayList<>();
    }
}
