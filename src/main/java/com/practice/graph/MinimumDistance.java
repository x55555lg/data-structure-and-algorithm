package com.practice.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

/**
 * 有向图中单个源点到其他顶点的最短路径
 *
 * @author Xulg
 * Created in 2021-05-12 21:10
 */
class MinimumDistance {

    /*
     *Dijkstra算法
     * 算最短路径的，算法解决的是有向图中单个源接点到其他顶点的最短路径问题
     *  1.Dijkstra算法必须指定一个源点
     *  2.生成一个源点到各个点的最小距离表，一开始只有一条记录，即原点到
     *    自己的最小距离为0，源点到其他所有点的最小距离都为正无穷大
     *  3.从距离表中拿出没拿过记录里的最小记录，通过这个点发出的边，更新
     *    源点到各个点的最小距离表，不断重复这一步
     *  4.源点到所有的点记录如果都被拿过一遍，过程停止，最小距离表得到了
     *---------------------------------------------------------------------
     * 有向图如下：from点为A节点，求A到各个节点的最小路径
     *                           ╭─╮
     *                           │D │
     *                         ↗╰─╯↘
     *                    6 ↗    2↑     ↘4
     *                   ↗        ↑        ↘
     *          ╭─╮↗   7     ╭─╮   23    ↘╭─╮
     *          │A │ →→→→→│C │→→→→→ │E │
     *          ╰─╯↘         ╰─╯         ↗╰─╯
     *                   ↘       2↑        ↗
     *                   1  ↘     ↑     ↗170
     *                         ↘╭─╮↗
     *                           │B │
     *                           ╰─╯
     * 初始状态下：
     *      从from点出发到所有点的最小距离表，初始状态下只有一条记录，from节点到A节点的距离为0
     *       HashMap<Node, Integer> nodeDistanceMap
     *          { {A:0} }
     *      记录哪些节点选择过了，初始状态下为空集合
     *       HashSet<Node> selectedNodeSet
     *          {}
     *========================================================================================
     * 1.从距离表中拿出没记录过的距离最小的节点，只有一个A节点，拿出A节点
     * 2.从距离表中拿到from节点到A节点的记录，int distance = nodeDistanceMap.get(A);
     *      这里的话，distance = 0;
     * 3.遍历A节点的所有边：
     *          A→→6→→D
     *              from节点到D节点的距离 = from点到A的距离 + A点到D点的距离
     *                                    = 0 + 6;
     *                                    = 6;
     *              因为D点在距离表nodeDistanceMap中不存在，存入，表示from点到D点的距离为6
     *                  nodeDistanceMap.put(D, 6);
     *          A→→7→→C
     *              from节点到C节点的距离 = from点到A的距离 + A点到C点的距离
     *                                    = 0 + 7;
     *                                    = 7;
     *              因为C点在距离表nodeDistanceMap中不存在，存入，表示from点到C点的距离为7
     *                  nodeDistanceMap.put(C, 7);
     *          A→→1→→B
     *              from节点到B节点的距离 = from点到A的距离 + A点到B点的距离
     *                                    = 0 + 1;
     *                                    = 1;
     *              因为B点在距离表nodeDistanceMap中不存在，存入，表示from点到B点的距离为1
     *                  nodeDistanceMap.put(B, 1);
     * 4.将A点加入记录表，表示A点已经选择过了，selectedNodeSet.add(A);
     * 5.此时集合中情况如下：
     *      nodeDistanceMap
     *          { {A:0}, {D:6}, {C:7}, {B:1} }
     *      selectedNodeSet
     *          { A }
     *========================================================================================
     * 1.从距离表中拿出没记录过的距离最小的节点，弹出B节点
     * 2.从距离表中拿到from节点到B节点的记录，int distance = nodeDistanceMap.get(B);
     *      这里的话，distance = 1;
     * 3.遍历B节点的所有边：
     *          B→→2→→C
     *              from节点到C节点的距离 = from点到B的距离 + B点到C点的距离
     *                                    = 1 + 2;
     *                                    = 3;
     *              因为C点在距离表nodeDistanceMap中已存在且距离为7，因为3 < 7，所以更新from点到C点的距离为3
     *                  nodeDistanceMap.put(C, 3);
     *          B→→170→→E
     *              from节点到E节点的距离 = from点到B的距离 + B点到E点的距离
     *                                    = 1 + 170;
     *                                    = 171;
     *              因为E点在距离表nodeDistanceMap中不存在，存入，表示from点到E点的距离为171
     *                  nodeDistanceMap.put(E, 171);
     * 4.将B点加入记录表，表示B点已经选择过了，selectedNodeSet.add(B);
     * 5.此时集合中情况如下：
     *      nodeDistanceMap
     *          { {A:0}, {D:6}, {C:3}, {B:1}, {E:171} }
     *      selectedNodeSet
     *          { A, B }
     *========================================================================================
     * 1.从距离表中拿出没记录过的距离最小的节点，弹出C节点
     * 2.从距离表中拿到from节点到B节点的记录，int distance = nodeDistanceMap.get(C);
     *      这里的话，distance = 3;
     * 3.遍历C节点的所有边：
     *          C→→2→→D
     *              from节点到D节点的距离 = from点到C的距离 + C点到D点的距离
     *                                    = 3 + 2;
     *                                    = 5;
     *              因为D点在距离表nodeDistanceMap中已存在且距离为6，因为5 < 6，所以更新from点到D点的距离为5
     *                  nodeDistanceMap.put(D, 5);
     *          C→→23→→E
     *              from节点到E节点的距离 = from点到C的距离 + C点到E点的距离
     *                                    = 3 + 23;
     *                                    = 26;
     *              因为E点在距离表nodeDistanceMap中已存在且距离为171，因为26 < 171，所以更新from点到E点的距离为26
     *                  nodeDistanceMap.put(E, 26);
     * 4.将C点加入记录表，表示C点已经选择过了，selectedNodeSet.add(C);
     * 5.此时集合中情况如下：
     *      nodeDistanceMap
     *          { {A:0}, {D:5}, {C:3}, {B:1}, {E:26} }
     *      selectedNodeSet
     *          { A, B, C }
     *========================================================================================
     * 1.从距离表中拿出没记录过的距离最小的节点，弹出D节点
     * 2.从距离表中拿到from节点到D节点的记录，int distance = nodeDistanceMap.get(D);
     *      这里的话，distance = 5;
     * 3.遍历D节点的所有边：
     *          D→→4→→E
     *              from节点到E节点的距离 = from点到D的距离 + D点到E点的距离
     *                                    = 5 + 4;
     *                                    = 9;
     *              因为E点在距离表nodeDistanceMap中已存在且距离为26，因为9 < 26，所以更新from点到E点的距离为9
     *                  nodeDistanceMap.put(E, 9);
     * 4.将D点加入记录表，表示D点已经选择过了，selectedNodeSet.add(D);
     * 5.此时集合中情况如下：
     *      nodeDistanceMap
     *          { {A:0}, {D:5}, {C:3}, {B:1}, {E:9} }
     *      selectedNodeSet
     *          { A, B, C, D }
     *========================================================================================
     * 1.从距离表中拿出没记录过的距离最小的节点，弹出E节点
     * 2.从距离表中拿到from节点到E节点的记录，int distance = nodeDistanceMap.get(E);
     *      这里的话，distance = 9;
     * 3.遍历E节点的所有边：E节点没有边，直接跳过
     * 4.将E点加入记录表，表示E点已经选择过了，selectedNodeSet.add(E);
     * 5.此时集合中情况如下：
     *      nodeDistanceMap
     *          { {A:0}, {D:5}, {C:3}, {B:1}, {E:9} }
     *      selectedNodeSet
     *          { A, B, C, D, E }
     *========================================================================================
     * 经过上面的步骤获得from点到各个点的最小距离表：
     *      { {A:0}, {D:5}, {C:3}, {B:1}, {E:9} }
     *          from点到A点最小距离：0
     *          from点到D点最小距离：5
     *          from点到C点最小距离：3
     *          from点到B点最小距离：1
     *          from点到E点最小距离：9
     */

    private static class Dijkstra {
        public static HashMap<Node, Integer> getMinDistance(Graph graph, Node source) {
            if (source == null) {
                return null;
            }

            /*
             *距离表
             * source节点到目的节点的距离
             * A    1   表示source节点到A的距离是1
             * B    2   表示source节点到B的距离是2
             */
            HashMap<Node, Integer> distanceMap = new HashMap<>(16);
            // source -> source的距离为0
            distanceMap.put(source, 0);
            // 记录已访问的节点
            HashSet<Node> alreadyVisited = new HashSet<>();

            // 取距离最小的节点
            Node minNode = getMinDistanceAndUnselectedNode(distanceMap, alreadyVisited);
            while (minNode != null) {
                for (Edge edge : minNode.edges) {
                    assert edge.from == minNode;
                    Node toNode = edge.to;
                    // distance(source -> toNode) = distance(source -> minNode) + distance(minNode -> toNode);
                    int distanceBetweenSourceAndTo = distanceMap.get(minNode) + edge.weight;
                    // 更新source -> toNode的距离
                    if (distanceMap.containsKey(toNode)) {
                        // 取最小的距离
                        int minDistance = Math.min(distanceMap.get(toNode), distanceBetweenSourceAndTo);
                        distanceMap.put(toNode, minDistance);
                    } else {
                        distanceMap.put(toNode, distanceBetweenSourceAndTo);
                    }
                }
                // 标记访问过了
                alreadyVisited.add(minNode);
                // 取下一个距离最小的节点
                minNode = getMinDistanceAndUnselectedNode(distanceMap, alreadyVisited);
            }
            return distanceMap;
        }

        private static Node getMinDistanceAndUnselectedNode(HashMap<Node, Integer> distanceMap,
                                                            HashSet<Node> alreadyVisited) {
            // 挑选出没被选则过，距离最小的节点
            Node minNode = null;
            int minDistance = Integer.MAX_VALUE;
            for (Entry<Node, Integer> entry : distanceMap.entrySet()) {
                Node destNode = entry.getKey();
                int distance = entry.getValue();
                // 未选择过且距离最小
                if (!alreadyVisited.contains(destNode) && distance < minDistance) {
                    minNode = destNode;
                    minDistance = distance;
                }
            }
            return minNode;
        }

    }
}
