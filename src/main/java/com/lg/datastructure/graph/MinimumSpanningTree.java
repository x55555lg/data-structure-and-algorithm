package com.lg.datastructure.graph;

import com.lg.datastructure.graph.base.Edge;
import com.lg.datastructure.graph.base.Graph;
import com.lg.datastructure.graph.base.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * 图的最小生成树
 *
 * @author Xulg
 * Created in 2020-11-18 18:06
 */
@SuppressWarnings("MapOrSetKeyShouldOverrideHashCodeEquals")
class MinimumSpanningTree {

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

    /**
     * Kruskal算法计算图的最小生成树
     */
    public static Set<Edge> kruskal(Graph graph) {
        if (graph == null) {
            return null;
        }

        // 将图的所有节点存入并查集中
        UnionFindSet unionFindSet = new UnionFindSet(new ArrayList<>(graph.nodes.values()));

        // 将图的所有边按照权重从小到大存入小根堆
        PriorityQueue<Edge> edgeSmallHeap = new PriorityQueue<>((e1, e2) -> e1.weight - e2.weight);
        // M条边 O(logM)
        edgeSmallHeap.addAll(graph.edges);

        Set<Edge> minimumSpanningTree = new HashSet<>();
        // M条边 O(logM)
        while (!edgeSmallHeap.isEmpty()) {
            Edge edge = edgeSmallHeap.poll();
            // 如果边的两个节点没有连通，则这个边就是最小生成树的边 O(1)
            if (!unionFindSet.isSameSet(edge.from, edge.to)) {
                minimumSpanningTree.add(edge);
                // 连通边的两个节点
                unionFindSet.union(edge.from, edge.to);
            }
        }
        return minimumSpanningTree;
    }

    @SuppressWarnings("all")
    private static class UnionFindSet {
        private final HashMap<Node, Node> parents;
        private final HashMap<Node, Integer> sizeMap;

        UnionFindSet(List<Node> nodes) {
            parents = new HashMap<>();
            sizeMap = new HashMap<>();
            for (Node node : nodes) {
                parents.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        public boolean isSameSet(Node node1, Node node2) {
            return findFather(node1) == findFather(node2);
        }

        public void union(Node node1, Node node2) {
            Node father1 = findFather(node1);
            Node father2 = findFather(node2);
            if (father1 != father2) {
                int size1 = sizeMap.get(father1);
                int size2 = sizeMap.get(father2);
                if (size1 >= size2) {
                    parents.put(father2, father1);
                    sizeMap.put(father1, size1 + size2);
                    sizeMap.remove(father2);
                } else {
                    parents.put(father1, father2);
                    sizeMap.put(father2, size1 + size2);
                    sizeMap.remove(father1);
                }
            }
        }

        private Node findFather(Node node) {
            ArrayList<Node> path = new ArrayList<>();
            Node father = parents.get(node);
            while (father != node) {
                path.add(node);
                node = father;
                father = parents.get(node);
            }
            // 压缩
            for (Node n : path) {
                parents.put(n, father);
            }
            return father;
        }
    }

    /*
     * Prim算法
     *  实现思路：
     *      1.可以从任意节点出发来寻找最小生成树
     *      2.某个点加入到被选取的点中后，解锁这个点出发的所有新的边
     *      3.在所有解锁的边中选最小的边，然后看看这个边会不会形成环
     *      4.如果会，不要当前边，继续考察剩下解锁的边中最小的边，重复3
     *      5.如果不会，要当前边，将该边的指向点加入到被选取的点中，重复2
     *      6.当所有点都被选取，最小生成树就得到了
     *------------------------------------------------------------------------------------------
     * 举例：
     *  无向图如下：节点①，②，③，④，分析这个图的最小生成树
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
     * 选择①节点出发，初始状态如下：
     *      minimumSpanningTree  {}     最小生成树的边
     *      recordNodeSet        {}     记录解锁过的节点
     *      edgeSmallHeap        {}     按照权重从小到大存放边的小根堆
     *  ①节点不在recordNodeSet集合中，所有还没解锁，记录①节点已解锁
     *          recordNodeSet       { ① }
     *  将从①节点出发的边存入小根堆中
     *          edgeSmallHeap       { 1 }
     *  从小根堆中弹出权重最小的边 edge = {weight=1, from=①, to=②}
     *          edgeSmallHeap       {}
     *  由于edge的to节点②不在recordNodeSet集合中，即②节点还没解锁过啊，保存这个边edge，并且解锁②节点和它的边
     *          minimumSpanningTree { {weight=1, from=①, to=②} }
     *          recordNodeSet       { ①, ② }
     *          edgeSmallHeap       { 2, 3 }
     *------------------------------------------------------------------------------------------------------------------
     *  由于小根堆不为空，继续弹出最小边edge = {weight=2, from=②, to=③}
     *          edgeSmallHeap       { 3 }
     *  由于edge的to节点③不在recordNodeSet集合中，即③节点还没解锁过啊，保存这个边edge，并且解锁③节点和它的边
     *          minimumSpanningTree { {weight=1, from=①, to=②}, {weight=2, from=②, to=③} }
     *          recordNodeSet       { ①, ②, ③ }
     *          edgeSmallHeap       { 3, 50 }
     *------------------------------------------------------------------------------------------------------------------
     *  由于小根堆不为空，继续弹出最小边edge = {weight=3, from=②, to=④}
     *          edgeSmallHeap       { 50 }
     *  由于edge的to节点④不在recordNodeSet集合中，即④节点还没解锁过啊，保存这个边edge，并且解锁④节点和它的边
     *          minimumSpanningTree {
     *                                  {weight=1, from=①, to=②},
     *                                  {weight=2, from=②, to=③},
     *                                  {weight=3, from=②, to=④}
     *                              }
     *          recordNodeSet       { ①, ②, ③, ④ }
     *          edgeSmallHeap       { 50, 100 }
     */

    /**
     * Prim算法计算图的最小生成树
     */
    public static Set<Edge> prim(Graph graph) {
        if (graph == null) {
            return null;
        }
        Set<Edge> minimumSpanningTree = new HashSet<>();
        // 记录被解锁的边，根据边的权重从小到大排列
        PriorityQueue<Edge> edgeSmallHeap = new PriorityQueue<>((e1, e2) -> e1.weight - e2.weight);
        // 记录被解锁的节点
        HashSet<Node> recordNodeSet = new HashSet<>();
        // 随便挑了一个节点开始
        for (Node node : graph.nodes.values()) {
            // 这个点得是没有解锁过的
            if (!recordNodeSet.contains(node)) {
                recordNodeSet.add(node);
                // 解锁从这个点出发的所有边，加入小根堆
                edgeSmallHeap.addAll(node.edges);
                // 在所有解锁的边中选最小的边
                while (!edgeSmallHeap.isEmpty()) {
                    // 弹出权重最小的边
                    Edge edge = edgeSmallHeap.poll();
                    Node toNode = edge.to;
                    if (recordNodeSet.contains(toNode)) {
                        // 这个边的to节点已经被解锁过了，会形成环，判断下一条边
                        System.out.println();
                    } else {
                        // 如果不会，要当前边
                        minimumSpanningTree.add(edge);
                        // 将该边的指向点加入到被选取的点中，即解锁该点
                        recordNodeSet.add(toNode);
                        // 解锁从to节点点出发的所有边，加入小根堆
                        edgeSmallHeap.addAll(toNode.edges);
                    }
                }
            }
        }
        return minimumSpanningTree;
    }

}
