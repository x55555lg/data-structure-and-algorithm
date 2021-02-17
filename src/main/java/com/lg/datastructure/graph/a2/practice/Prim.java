package com.lg.datastructure.graph.a2.practice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * @author Xulg
 * Created in 2021-02-07 21:43
 */
@SuppressWarnings({"MapOrSetKeyShouldOverrideHashCodeEquals", "MismatchedQueryAndUpdateOfCollection"})
class Prim {

    /*
     * Prim算法
     *  实现思路：
     *      1.可以从任意节点出发来寻找最小生成树
     *      2.某个点加入到被选取的点中后，解锁这个点出发的所有新的边
     *      3.在所有解锁的边中选最小的边，然后看看这个边会不会形成环
     *      4.如果会，不要当前边，继续考察剩下解锁的边中最小的边，重复3
     *      5.如果不会，要当前边，将该边的指向点加入到被选取的点中，重复2
     *      6.当所有点都被选取，最小生成树就得到了
     */

    public static Set<Edge> getMinimumSpanningTree(Graph graph) {
        if (graph == null) {
            return null;
        }

        HashSet<Edge> minimumSpanningTree = new HashSet<>();

        // 所有边入小根堆，按权重从小到大排序
        PriorityQueue<Edge> smallHeap = new PriorityQueue<>((e1, e2) -> e1.weight - e2.weight);

        // 记录已解锁的节点
        HashSet<Node> alreadySelectedNodes = new HashSet<>();

        for (Node node : graph.nodes.values()) {
            if (!alreadySelectedNodes.contains(node)) {
                // 标记节点已选过
                alreadySelectedNodes.add(node);
                // 节点的所有邻接边入堆
                smallHeap.addAll(node.edges);

                while (!smallHeap.isEmpty()) {
                    Edge edge = smallHeap.poll();
                    Node toNode = edge.to;
                    if (!alreadySelectedNodes.contains(toNode)) {
                        alreadySelectedNodes.add(toNode);
                        minimumSpanningTree.add(edge);
                        smallHeap.addAll(toNode.edges);
                    }
                }
            }
        }

        return minimumSpanningTree;
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
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
