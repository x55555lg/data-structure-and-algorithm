package com.lg.datastructure.graph.a3.practice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Xulg
 * Created in 2021-02-15 22:33
 */
class Prim {

    // TODO: 2021/2/15 忘了，看视频去

    public static Set<Edge> getMinSpanTree(Graph graph) {
        if (graph == null) {
            return null;
        }

        HashSet<Edge> minimumSpanTree = new HashSet<>();

        HashSet<Node> alreadySelectedNodes = new HashSet<>();

        return minimumSpanTree;
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
