package com.lg.datastructure.graph.a1.practice;

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
 * Created in 2021-02-03 11:17
 */
@SuppressWarnings("all")
class Kruskal {

    /*
     * Kruskal算法-最小生成树
     *  实现思路：
     *      需要使用并查集这个工具
     *      1.总是从权重最小的边开始考虑，依次考察权重依次变大的边(按照权重从小到大的顺序访问边)
     *      2.当前的边要么进入最小生成树的集合，要么丢弃
     *      3.如果当前的边进入最小生成树的集合中不会形成环，就要当前边
     *          所谓的环就是边的节点在并查集中是否连通的
     *      4.如果当前的边进入最小生成树的集合中会形成环，就不要当前边
     *      5.考察完所有边之后，最小生成树的集合也得到了
     */

    /**
     * Kruskal(克鲁斯卡尔)算法求图的最小生成树
     *
     * @param graph the graph
     * @return the minimum tree
     */
    public static Set<Edge> kruskal(Graph graph) {
        if (graph == null) {
            return null;
        }
        if (graph.nodes.isEmpty() || graph.edges.isEmpty()) {
            return new HashSet<>(0);
        }

        // 最小树集合
        HashSet<Edge> minimumTree = new HashSet<>();

        // 将所有节点加入并查集
        UnionFindSet<Node> unionFindSet = new UnionFindSet<>(graph.nodes.values());

        // 将所有的边入堆，按照边的权重从小到大排列，堆顶永远是权重最小的边
        PriorityQueue<Edge> smallHeap = new PriorityQueue<>((e1, e2) -> e1.weight - e2.weight);
        // 时间复杂度O(M*logM) M为边的数量
        for (Edge edge : graph.edges) {
            smallHeap.add(edge);
        }

        // 时间复杂度O(M*logM) M为边的数量
        while (!smallHeap.isEmpty()) {
            // 从堆中弹出权重最小的边
            Edge edge = smallHeap.poll();
            // 如果边的两个节点没有连通，则这个边就是最小生成树的边
            if (!unionFindSet.isSameSet(edge.from, edge.to)) {
                minimumTree.add(edge);
                // 连通2个节点
                unionFindSet.union(edge.from, edge.to);
            }
        }

        return minimumTree;
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

    private static class UnionFindSet<V> {
        final HashMap<V, Node<V>> nodesMap = new HashMap<>();
        final HashMap<Node<V>, Node<V>> nodeParentMap = new HashMap<>();
        final HashMap<Node<V>, Integer> sizeMap = new HashMap<>();

        UnionFindSet(Collection<V> collection) {
            assert collection != null;
            for (V v : collection) {
                Node<V> node = new Node<>(v);
                nodesMap.put(v, node);
                nodeParentMap.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        public boolean isSameSet(V x, V y) {
            if (x == null || y == null) {
                return false;
            }
            Node<V> nodeX = nodesMap.get(x);
            Node<V> nodeY = nodesMap.get(y);
            if (nodeX == null || nodeY == null) {
                return false;
            }
            return findFather(nodeX) == findFather(nodeY);
        }

        public void union(V x, V y) {
            if (x == null || y == null) {
                return;
            }

            Node<V> nodeX = nodesMap.get(x);
            Node<V> nodeY = nodesMap.get(y);
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

            nodeParentMap.put(smaller, bigger);
            sizeMap.put(bigger, sizeX + sizeY);
            sizeMap.remove(smaller);
        }

        public int size() {
            return sizeMap.size();
        }

        private Node<V> findFather(Node<V> node) {
            assert node != null : "node can not been null";
            Queue<Node<V>> path = new LinkedList<>();
            while (nodeParentMap.get(node) != node) {
                path.add(node);
                node = nodeParentMap.get(node);
            }
            while (!path.isEmpty()) {
                nodeParentMap.put(path.poll(), node);
            }
            return node;
        }

        private static class Node<V> {
            V value;

            Node(V val) {
                this.value = val;
            }
        }
    }

}
