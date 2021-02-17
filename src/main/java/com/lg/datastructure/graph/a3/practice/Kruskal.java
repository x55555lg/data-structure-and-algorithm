package com.lg.datastructure.graph.a3.practice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * @author Xulg
 * Created in 2021-02-15 21:01
 */
class Kruskal {

    /*
     * 克鲁斯卡尔算法求图的最小生成树
     */

    public static Set<Edge> getMinimumSpanTree(Graph graph) {
        if (graph == null) {
            return null;
        }
        if (graph.edges.isEmpty() || graph.nodes.isEmpty()) {
            return new HashSet<>(0);
        }

        HashSet<Edge> minimumSpanTree = new HashSet<>();

        // create the union find set
        UnionFindSet<Node> unionFindSet = new UnionFindSet<>(graph.nodes.values());

        // 所有的边入小根堆，按照权重小到大排列
        PriorityQueue<Edge> smallHeap = new PriorityQueue<>((e1, e2) -> e1.weight - e2.weight);
        smallHeap.addAll(graph.edges);

        while (!smallHeap.isEmpty()) {
            Edge edge = smallHeap.poll();
            if (!unionFindSet.isSameSet(edge.from, edge.to)) {
                minimumSpanTree.add(edge);
                unionFindSet.union(edge.from, edge.to);
            }
        }
        return minimumSpanTree;
    }

    /* ****************************************************************************************************************/

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

    @SuppressWarnings("MapOrSetKeyShouldOverrideHashCodeEquals")
    private static class UnionFindSet<V> {
        HashMap<V, ValueNode<V>> nodeMap = new HashMap<>();
        HashMap<ValueNode<V>, ValueNode<V>> parentMap = new HashMap<>();
        HashMap<ValueNode<V>, Integer> sizeMap = new HashMap<>();

        UnionFindSet(Collection<V> vs) {
            for (V v : vs) {
                ValueNode<V> node = new ValueNode<>(v);
                nodeMap.put(v, node);
                parentMap.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        public boolean isSameSet(V x, V y) {
            if (x == null || y == null) {
                return false;
            }
            ValueNode<V> nodeX = nodeMap.get(x);
            ValueNode<V> nodeY = nodeMap.get(y);
            if (nodeX == null || nodeY == null) {
                return false;
            }
            return findFather(nodeX) == findFather(nodeY);
        }

        public void union(V x, V y) {
            if (x == null || y == null) {
                return;
            }
            ValueNode<V> nodeX = nodeMap.get(x);
            ValueNode<V> nodeY = nodeMap.get(y);
            if (nodeX == null || nodeY == null) {
                return;
            }
            ValueNode<V> fatherX = findFather(nodeX);
            ValueNode<V> fatherY = findFather(nodeY);
            if (fatherX == fatherY) {
                return;
            }
            int sizeX = sizeMap.get(nodeX);
            int sizeY = sizeMap.get(nodeY);
            ValueNode<V> bigger = (sizeX >= sizeY) ? fatherX : fatherY;
            ValueNode<V> smaller = (bigger == fatherX) ? fatherY : fatherX;

            parentMap.put(smaller, bigger);
            sizeMap.put(bigger, sizeX + sizeY);
            sizeMap.remove(smaller);
        }

        private ValueNode<V> findFather(ValueNode<V> node) {
            Queue<ValueNode<V>> path = new LinkedList<>();
            while (node != parentMap.get(node)) {
                path.add(node);
                node = parentMap.get(node);
            }
            while (!path.isEmpty()) {
                parentMap.put(path.poll(), node);
            }
            return node;
        }

        private static class ValueNode<V> {
            V value;

            ValueNode(V v) {
                value = v;
            }
        }
    }

}
