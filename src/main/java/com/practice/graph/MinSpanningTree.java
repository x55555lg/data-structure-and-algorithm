package com.practice.graph;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * 图的最小生成树
 *
 * @author Xulg
 * Created in 2021-05-12 15:53
 */
class MinSpanningTree {

    /*
     * Kruskal算法
     *  实现思路：
     *      需要使用并查集这个工具，所谓的环，就是看两个节点在并查集中是否连通
     *      1.总是从权重最小的边开始考虑，依次考察权重依次变大的边(按照权重从小到大的顺序访问边)
     *      2.当前的边要么进入最小生成树的集合，要么丢弃
     *      3.如果当前的边进入最小生成树的集合中不会形成环，就要当前边
     *      4.如果当前的边进入最小生成树的集合中会形成环，就不要当前边
     *      5.考察完所有边之后，最小生成树的集合也得到了
     *------------------------------------------------------------------------------------------
     * 举例：
     *  有向图如下：节点①，②，③，④，分析这个图的最小生成树
     *                           ╭─╮
     *                           │1 │
     *                         ↙╰─╯↖
     *                    1 ╱            ╲100
     *                   ╱                  ╲
     *          ╭─╮↙          3             ↖╭─╮
     *          │2 │一一一一一一→→一一一一一→│4 │
     *          ╰─╯↘                        ↗╰─╯
     *                   ╲                  ╱
     *                   2  ╲            ╱50
     *                         ↘╭─╮↗
     *                           │3 │
     *                           ╰─╯
     * 将图中的所有节点加入并查集中，初始状态下，每个节点都是一个单独的集合
     *      unionFindSet
     *              {{①}, {②}, {③}, {④}}
     * 将图中的所有边加入小根堆，按照边的权重从小到大排序，堆顶永远是权重最小的边
     *      edgeSmallHeap
     *              {1, 2, 3, 50, 100}
     * 依次从小根堆中弹出边进行判断
     *  第1条边
     *      minimumSpanningTree  {}
     *      unionFindSet         {{①}, {②}, {③}, {④}}
     *      edgeSmallHeap        {1, 2, 3, 50, 100}
     *                            ↑
     *      权重为1，边的节点是①节点，②节点。
     *      ①节点，②节点在并查集中没有连通，所以保留这个边，在并查集中合并①②节点
     *          minimumSpanningTree {1}
     *          unionFindSet        {{①,②}, {③}, {④}}
     *  第2条边
     *      minimumSpanningTree  {1}
     *      unionFindSet         {{①,②}, {③}, {④}}
     *      edgeSmallHeap        {1, 2, 3, 50, 100}
     *                               ↑
     *      权重为2，边的节点是②节点，③节点。
     *      ②节点，③节点在并查集中没有连通，所以保留这个边，在并查集中合并②③节点
     *          minimumSpanningTree {1, 2}
     *          unionFindSet        {{①,②,③}, {④}}
     *  第3条边
     *      minimumSpanningTree  {1, 2}
     *      unionFindSet         {{①,②,③}, {④}}
     *      edgeSmallHeap        {1, 2, 3, 50, 100}
     *                                  ↑
     *      权重为3，边的节点是②节点，④节点。
     *      ②节点，④节点在并查集中没有连通，所以保留这个边，在并查集中合并②④节点
     *          minimumSpanningTree {1, 2, 3}
     *          unionFindSet        {{①,②,③,④}}
     *  第4条边
     *      minimumSpanningTree  {1, 2, 3}
     *      unionFindSet         {{①,②,③,④}}
     *      edgeSmallHeap        {1, 2, 3, 50, 100}
     *                                     ↑
     *      权重为50，边的节点是③节点，④节点。
     *      ③节点，④节点在并查集中已经连通，舍弃这条边
     *  第5条边
     *      minimumSpanningTree  {1, 2, 3}
     *      unionFindSet         {{①,②,③,④}}
     *      edgeSmallHeap        {1, 2, 3, 50, 100}
     *                                         ↑
     *      权重为50，边的节点是①节点，④节点。
     *      ①节点，④节点在并查集中已经连通，舍弃这条边
     * 最终结果：
     *      最小生成树：minimumSpanningTree  {1, 2, 3}
     *          权重和为6
     *          3条边，满足n个节点，n-1条边的性质
     *          最小生成树如下：
     *                           ╭─╮
     *                           │1 │
     *                         ↙╰─╯
     *                    1 ╱
     *                   ╱
     *          ╭─╮↙          3               ╭─╮
     *          │2 │一一一一一一→→一一一一一→│4 │
     *          ╰─╯↘                          ╰─╯
     *                   ╲
     *                   2  ╲
     *                         ↘╭─╮
     *                           │3 │
     *                           ╰─╯
     */

    private static class Kruskal {

        public static Set<Edge> getMinSpanningTree(Graph graph) {
            if (graph == null) {
                return null;
            }
            // 所有的边加入小根堆，按照权重从小到大排列
            PriorityQueue<Edge> queue = new PriorityQueue<>((e1, e2) -> e1.weight - e2.weight);
            queue.addAll(graph.edges);

            // 并查集
            UnionFindSet<Node> unionFindSet = new UnionFindSet<>(graph.nodes.values());

            // 最小生成树
            HashSet<Edge> minSpanningTree = new HashSet<>();

            while (!queue.isEmpty()) {
                // 弹出权重最小的边
                Edge edge = queue.poll();
                // 没有连通则要这个边
                if (!unionFindSet.isSameSet(edge.from, edge.to)) {
                    minSpanningTree.add(edge);
                    // 设置连通
                    unionFindSet.union(edge.from, edge.to);
                }
            }
            return minSpanningTree;
        }

        private static class UnionFindSet<V> {
            private final HashMap<V, Node<V>> nodeMap;
            private final HashMap<Node<V>, Node<V>> parentMap;
            private final HashMap<Node<V>, Integer> sizeMap;

            UnionFindSet(Collection<V> vs) {
                nodeMap = new HashMap<>(vs.size() * 4 / 3 + 1);
                parentMap = new HashMap<>(vs.size() * 4 / 3 + 1);
                sizeMap = new HashMap<>(vs.size() * 4 / 3 + 1);
                for (V v : vs) {
                    Node<V> node = new Node<>(v);
                    nodeMap.put(v, node);
                    parentMap.put(node, node);
                    sizeMap.put(node, 1);
                }
            }

            public void union(V x, V y) {
                if (x == null || y == null) {
                    return;
                }
                Node<V> nodeX = nodeMap.get(x);
                Node<V> nodeY = nodeMap.get(y);
                if (nodeX == null || nodeY == null) {
                    return;
                }
                Node<V> parentX = findParent(nodeX);
                Node<V> parentY = findParent(nodeY);
                if (parentX == parentY) {
                    return;
                }
                int sizeX = sizeMap.get(parentX);
                int sizeY = sizeMap.get(parentY);
                Node<V> bigger = sizeX > sizeY ? parentX : parentY;
                Node<V> smaller = bigger == parentX ? parentY : parentX;
                parentMap.put(smaller, bigger);
                sizeMap.put(bigger, sizeX + sizeY);
                sizeMap.remove(smaller);
            }

            public boolean isSameSet(V x, V y) {
                if (x == null || y == null) {
                    return false;
                }
                Node<V> nodeX = nodeMap.get(x);
                Node<V> nodeY = nodeMap.get(y);
                if (nodeX == null || nodeY == null) {
                    return false;
                }
                Node<V> parentX = findParent(nodeX);
                Node<V> parentY = findParent(nodeY);
                return parentX == parentY;
            }

            public int size() {
                return sizeMap.size();
            }

            private Node<V> findParent(Node<V> node) {
                Queue<Node<V>> path = new LinkedList<>();
                while (node != parentMap.get(node)) {
                    path.add(node);
                    node = parentMap.get(node);
                }
                while (!path.isEmpty()) {
                    parentMap.put(path.poll(), node);
                }
                return node;
            }

            private static class Node<V> {
                V val;

                Node(V val) {
                    this.val = val;
                }

                @Override
                public boolean equals(Object o) {
                    if (this == o) {
                        return true;
                    }
                    if (o == null || getClass() != o.getClass()) {
                        return false;
                    }
                    Node<?> node = (Node<?>) o;
                    return Objects.equals(val, node.val);
                }

                @Override
                public int hashCode() {
                    return val != null ? val.hashCode() : 0;
                }
            }
        }
    }

}
