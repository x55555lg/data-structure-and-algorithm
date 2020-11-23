package com.lg.datastructure.graph;

import com.lg.datastructure.graph.base.Edge;
import com.lg.datastructure.graph.base.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 有向图中单个源点到其他顶点的最短路径
 *
 * @author Xulg
 * Created in 2020-11-20 9:47
 */
@SuppressWarnings({"MapOrSetKeyShouldOverrideHashCodeEquals", "AlibabaCollectionInitShouldAssignCapacity"})
class MinimumDistance2 {

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
     *      创建一个自定义的小根堆，按照from节点到to节点的距离从小到大排序，
     *      初始状态下只有一条记录，from节点到A节点的距离为0
     *          NodeHeap nodeHeap = new NodeHeap();
     *              ArrayList<Node> table;                  存储节点的数组
     *                  table           { A }
     *              HashMap<Node, Integer> nodeIndexMap     节点在堆中的位置，如果value为-1表示这个节点已经选择过了
     *                  nodeIndexMap    { {A, 0} }
     *              HashMap<Node, Integer> distanceMap;     源点到各个节点的最小距离表，key为某个to节点，
     *                                                      value为from节点到to节点的距离
     *                  distanceMap     { {A: 0} }
     *========================================================================================
     * 1.从小根堆中弹出没记录过的距离最小的节点，只有一个A节点，拿出A节点
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

    /**
     * 有向图中源点from到其他顶点的最短路径
     *
     * @param from 源点
     * @return 源点到各个点的距离表 key: toNode   value: distance
     */
    public static Map<Node, Integer> dijkstra(Node from) {
        if (from == null) {
            return null;
        }

        NodeHeap nodeHeap = new NodeHeap();
        nodeHeap.addOrUpdateOrIgnore(from, 0);

        HashMap<Node, Integer> nodeDistanceMap = new HashMap<>();
        while (!nodeHeap.isEmpty()) {
            // 从距离表中拿出没记录过的距离最小的节点
            NodeDistanceData nodeDistanceData = nodeHeap.pop();
            Node minNode = nodeDistanceData.toNode;
            // 获取from节点到minNode节点的距离
            int distance = nodeDistanceData.distance;
            for (Edge edge : minNode.edges) {
                Node toNode = edge.to;
                // 计算from节点到toNode节点的距离 = 获取from节点到minNode节点的距离 + minNode节点到toNode节点的距离
                int distanceBetweenFromAndTo = distance + edge.weight;
                nodeHeap.addOrUpdateOrIgnore(toNode, distanceBetweenFromAndTo);
            }
            // 存入结果
            nodeDistanceMap.put(minNode, distance);
        }
        return nodeDistanceMap;
    }

    @SuppressWarnings("FieldCanBeLocal")
    private static class NodeDistanceData {
        // 目的节点
        private final Node toNode;
        private final int distance;

        NodeDistanceData(Node toNode, int distance) {
            this.toNode = toNode;
            this.distance = distance;
        }
    }

    /**
     * 实现数据修改了可调整的小根堆
     */
    private static class NodeHeap {
        // 堆顶索引位置
        private static final int HEAP_TOP_INDEX = 0;
        // 堆存放数据的地方
        private final ArrayList<Node> table;
        // 节点和节点的索引位置，key为节点，value为节点在堆中的索引位置
        // 如果value为-1表示这个节点已经选择过了，不需要处理了
        private final HashMap<Node, Integer> nodeIndexMap;
        // 下一个存储数据的index位置
        private int count;
        // 源点到各个节点的最小距离表
        // key为某个节点， value 从源节点出发到该节点的目前最小距离
        private final HashMap<Node, Integer> distanceMap;

        NodeHeap() {
            table = new ArrayList<>();
            nodeIndexMap = new HashMap<>();
            distanceMap = new HashMap<>();
            count = 0;
        }

        public boolean isEmpty() {
            return count == 0;
        }

        public void addOrUpdateOrIgnore(Node toNode, int distance) {
            if (toNode == null) {
                return;
            }
            if (nodeIndexMap.containsKey(toNode) && nodeIndexMap.get(toNode) == -1) {
                // 这个节点已经选择过了，忽略
                return;
            }

            if (nodeIndexMap.containsKey(toNode)) {
                // 更新
                int minDistance = Math.min(distance, distanceMap.get(toNode));
                distanceMap.put(toNode, minDistance);

                // 从修改位置开始堆化
                int idx = nodeIndexMap.get(toNode);
                // 向上堆化
                heapifyInsert(idx);
                // 向下堆化，这里其实不需要向下堆化，因为这个位置后面根本没有节点了
                heapify(idx, count);
            } else {
                // 将数据加入堆中
                int idx = count;
                table.add(idx, toNode);
                nodeIndexMap.put(toNode, idx);
                distanceMap.put(toNode, distance);
                // 堆的个数+1
                count++;

                // 从idx位置开始，向上进行堆化操作
                heapifyInsert(idx);
            }
        }

        public NodeDistanceData pop() {
            if (count == 0) {
                return null;
            }

            Node minNode = table.get(HEAP_TOP_INDEX);

            // 将堆中的最后一个元素挪到堆顶位置
            int lastIdx = count - 1;
            swap(HEAP_TOP_INDEX, lastIdx);

            // 标记这个节点已经选择过了
            nodeIndexMap.put(minNode, -1);
            // 移除无用的信息
            Integer distance = distanceMap.remove(minNode);
            table.remove(lastIdx);
            count--;

            // 从上到下堆化操作
            heapify(HEAP_TOP_INDEX, count);

            return new NodeDistanceData(minNode, distance);
        }

        private void heapifyInsert(int idx) {
            // 向上看到根节点为止
            while (idx > 0) {
                // 计算父节点的index位置
                int parentIdx = (idx - 1) / 2;
                // 如果子节点的值大于父节点的值，要保证是小根堆，进行交换
                if (distanceMap.get(table.get(idx)) > distanceMap.get(table.get(parentIdx))) {
                    swap(idx, parentIdx);
                }
                // 看父节点是什么情况
                idx = parentIdx;
            }
        }

        private void heapify(int parentIdx, int heapSize) {
            while (true) {
                // 左子节点的位置
                int leftIdx = 2 * parentIdx + 1;
                // 右子节点的位置
                int rightIdx = leftIdx + 1;

                // 左子节点的位置越界了，右子节点肯定也越界了，结束流程
                if (leftIdx >= heapSize) {
                    break;
                }

                // 取左右节点中值小的那个
                int smallIdx;
                if (rightIdx < heapSize) {
                    int leftDistance = distanceMap.get(table.get(leftIdx));
                    int rightDistance = distanceMap.get(table.get(rightIdx));
                    smallIdx = leftDistance < rightDistance ? leftIdx : rightIdx;
                } else {
                    // 右子节点已经越界，所有左子节点就是小的那个
                    smallIdx = leftIdx;
                }

                // 父节点和小的子节点比较
                int oldParentIdx = parentIdx;
                if (distanceMap.get(table.get(parentIdx))
                        > distanceMap.get(table.get(smallIdx))) {
                    // 父节点大于子节点，进行交换
                    swap(parentIdx, smallIdx);
                    // 下一个父节点
                    parentIdx = smallIdx;
                }
                if (oldParentIdx == parentIdx) {
                    // 父节点没有变化，说明父节点已经是最小的了，不需要再进行交换了
                    break;
                }
            }
        }

        private void swap(int idx1, int idx2) {
            Node node1 = table.get(idx1);
            Node node2 = table.get(idx2);
            table.set(idx1, node2);
            table.set(idx2, node1);
            nodeIndexMap.put(node1, idx2);
            nodeIndexMap.put(node2, idx1);
        }
    }
}
