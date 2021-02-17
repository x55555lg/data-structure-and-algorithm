package com.lg.datastructure.graph.a2.practice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Kruskal算法-求最小生成树
 *
 * @author Xulg
 * Created in 2021-02-07 21:04
 */
@SuppressWarnings("MapOrSetKeyShouldOverrideHashCodeEquals")
class Kruskal {

    /*
     * Kruskal算法
     *  实现思路：
     *      需要使用并查集这个工具，所谓的环，就是看两个节点在并查集中是否连通
     *      1.总是从权重最小的边开始考虑，依次考察权重依次变大的边(按照权重从小到大的顺序访问边)
     *      2.当前的边要么进入最小生成树的集合，要么丢弃
     *      3.如果当前的边进入最小生成树的集合中不会形成环，就要当前边
     *      4.如果当前的边进入最小生成树的集合中会形成环，就不要当前边
     *      5.考察完所有边之后，最小生成树的集合也得到了
     */

    /**
     * 克鲁斯卡尔算法求最小生成树
     * 时间复杂度 O(M*logM) M为图的边数量
     *
     * @param graph the graph
     * @return the minimum spanning tree
     */
    public static Set<Edge> getMinimumSpanningTree(Graph graph) {
        if (graph == null || graph.edges.isEmpty()) {
            return null;
        }

        HashSet<Edge> minSpanningTree = new HashSet<>();

        // 所有节点加入并查集
        UnionFindSet<Node> unionFindSet = new UnionFindSet<>(graph.nodes.values());

        // 所有边加入小根堆，按照权重小到大排列
        PriorityQueue<Edge> smallHeap = new PriorityQueue<>((e1, e2) -> e1.weight - e2.weight);
        // M条边 入堆操作O(logM) 总代价O(M*logM)
        smallHeap.addAll(graph.edges);

        // M条边 总代价O(M*logM)
        while (!smallHeap.isEmpty()) {
            // 出堆操作O(logM)
            Edge edge = smallHeap.poll();
            // 两个节点必须是没有连通的
            // 并查集操作O(1)
            if (!unionFindSet.isSameSet(edge.from, edge.to)) {
                minSpanningTree.add(edge);
                unionFindSet.union(edge.from, edge.to);
            }
        }

        // return the minimum spanning tree
        return minSpanningTree;
    }

    private static class UnionFindSet<V> {
        private final HashMap<V, Node<V>> valueNodeMap = new HashMap<>();
        private final HashMap<Node<V>, Node<V>> parentMap = new HashMap<>();
        private final HashMap<Node<V>, Integer> sizeMap = new HashMap<>();

        UnionFindSet(Collection<V> vs) {
            for (V v : vs) {
                Node<V> node = new Node<>(v);
                valueNodeMap.put(v, node);
                parentMap.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        public boolean isSameSet(V x, V y) {
            if (x == null || y == null) {
                return false;
            }
            Node<V> nodeX = valueNodeMap.get(x);
            Node<V> nodeY = valueNodeMap.get(y);
            if (nodeX == null || nodeY == null) {
                return false;
            }
            return findFather(nodeX) == findFather(nodeY);
        }

        public void union(V x, V y) {
            if (x == null || y == null) {
                return;
            }
            Node<V> nodeX = valueNodeMap.get(x);
            Node<V> nodeY = valueNodeMap.get(y);
            if (nodeX == null || nodeY == null) {
                return;
            }

            Node<V> fatherX = findFather(nodeX);
            Node<V> fatherY = findFather(nodeY);
            if (fatherX == fatherY) {
                return;
            }

            int sizeX = sizeMap.get(fatherX);
            int sizeY = sizeMap.get(fatherY);

            Node<V> bigger = sizeX >= sizeY ? fatherX : fatherY;
            Node<V> smaller = bigger == fatherX ? fatherY : fatherX;

            parentMap.put(smaller, bigger);
            sizeMap.put(bigger, sizeX + sizeY);
            sizeMap.remove(smaller);
        }

        private Node<V> findFather(Node<V> node) {
            Queue<Node<V>> path = new LinkedList<>();
            while (parentMap.get(node) != node) {
                path.add(node);
                node = parentMap.get(node);
            }
            while (!path.isEmpty()) {
                parentMap.put(path.poll(), node);
            }
            return node;
        }

        private static class Node<V> {
            V value;

            Node(V v) {
                this.value = v;
            }
        }
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
