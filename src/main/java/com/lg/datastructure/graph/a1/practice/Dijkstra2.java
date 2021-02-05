package com.lg.datastructure.graph.a1.practice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * 使用堆进行了优化
 *
 * @author Xulg
 * Created in 2021-02-04 10:02
 */
@SuppressWarnings({"MapOrSetKeyShouldOverrideHashCodeEquals", "MismatchedQueryAndUpdateOfCollection"})
class Dijkstra2 {

    /*
     * Dijkstra算法
     * 算最短路径的，算法解决的是有向图中单个源接点到其他顶点的最短路径问题
     * 所谓的最短是指权重和最小
     *  1.Dijkstra算法必须指定一个源点
     *  2.生成一个源点到各个点的最小距离表，一开始只有一条记录，即原点到
     *    自己的最小距离为0，源点到其他所有点的最小距离都为正无穷大
     *  3.从距离表中拿出没拿过记录里的最小记录，通过这个点发出的边，更新
     *    源点到各个点的最小距离表，不断重复这一步
     *  4.源点到所有的点记录如果都被拿过一遍，过程停止，最小距离表得到了
     */

    public static Map<Node, Integer> minDistance(Graph graph, Node sourceNode) {
        if (graph == null || sourceNode == null) {
            return null;
        }

        HashMap<Node, Integer> distanceMap = new HashMap<>(16);
        distanceMap.put(sourceNode, 0);

        // 根据距离构建小根堆
        InternalSmallHeap smallHeap = new InternalSmallHeap(distanceMap);
        while (!smallHeap.isEmpty()) {
            Node minNode = smallHeap.pop();
            // 获取源节点到最小距离节点的距离
            int distance = distanceMap.get(minNode);
            // 求(源节点)到(最小节点的各个边的to节点)的距离
            for (Edge edge : minNode.edges) {
                assert edge.from == minNode;
                Node toNode = edge.to;
                int distanceBetweenSourceAndTo = distance + edge.weight;
                if (distanceMap.containsKey(toNode)) {
                    // 更新最小距离
                    distanceMap.put(toNode, Math.min(distanceBetweenSourceAndTo, distanceMap.get(toNode)));
                } else {
                    distanceMap.put(toNode, distanceBetweenSourceAndTo);
                }
                // 动态调整堆
                smallHeap.adjust(toNode);
            }
        }
        return distanceMap;
    }

    private static Node getMinDistanceAndUnselectedNode(InternalSmallHeap smallHeap) {
        return smallHeap.isEmpty() ? null : smallHeap.pop();
    }

    /* ****************************************************************************************************************/
    /* ****************************************************************************************************************/
    /* ****************************************************************************************************************/
    /* ****************************************************************************************************************/
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

    /* ****************************************************************************************************************/
    /* ****************************************************************************************************************/
    /* ****************************************************************************************************************/
    /* ****************************************************************************************************************/
    /* ****************************************************************************************************************/

    private static class InternalSmallHeap {
        static final int HEAP_TOP_IDX = 0;
        ArrayList<Node> table;
        int count;
        HashMap<Node, Integer> distanceMap;
        // 各个节点对应的位置，-1表示节点已经选择过了
        HashMap<Node, Integer> nodeIndexMap;

        InternalSmallHeap(HashMap<Node, Integer> distanceMap) {
            this.distanceMap = distanceMap;
            table = new ArrayList<>();
            nodeIndexMap = new HashMap<>();
            for (Node node : distanceMap.keySet()) {
                push(node);
            }
        }

        public void addOrUpdateOrIgnore(Node node, int sourceNodeToNodeDistance) {
            if (node == null) {
                return;
            }

            if (nodeIndexMap.containsKey(node) || nodeIndexMap.get(node) == -1) {
                // 已经加入过这个结果或者这个节点已经选择过了
                return;
            }

            if (nodeIndexMap.containsKey(node)) {
                // update
                int minDistance = Math.min(distanceMap.get(node), sourceNodeToNodeDistance);
                distanceMap.put(node, minDistance);
                // heapify
                int idx = nodeIndexMap.get(node);
                heapifyInsert(idx);
                heapify(nodeIndexMap.get(node), count);
            } else {
                // add
                int idx = count;
                table.add(idx, node);
                nodeIndexMap.put(node, idx);
                distanceMap.put(node, sourceNodeToNodeDistance);
                count++;
                // heapify
                heapifyInsert(idx);
            }
        }

        public void push(Node node) {
            assert node != null : "what the hell?";

            if (nodeIndexMap.containsKey(node) && nodeIndexMap.get(node) == -1) {
                // 已经选择过的节点已经不需要了
                return;
            }

            // 加入堆数组中
            int index = count;
            table.add(index, node);

            // 记录节点在哪个位置
            nodeIndexMap.put(node, index);
            count++;

            // 向上堆化操作
            heapifyInsert(index);
        }

        public Node pop() {
            if (count <= 0) {
                throw new IllegalStateException("堆已经空了");
            }

            Node minNode = table.get(HEAP_TOP_IDX);

            // 交换第一个和最后一个元素的位置
            swap(HEAP_TOP_IDX, count - 1);
            // 删除最后一个元素(其实就是原来的堆顶元素)
            table.remove(count - 1);

            // 数量减1
            count--;

            // 从上到下堆化操作
            heapify(HEAP_TOP_IDX, count);

            // 标记节点已经选过了
            nodeIndexMap.put(minNode, -1);

            return minNode;
        }

        /**
         * 动态调整堆
         */
        public void adjust(Node node) {
            // 获取节点所在位置
            int idx = nodeIndexMap.get(node);

            // -1表示已经选过的节点
            if (idx == -1) {
                return;
            }

            heapifyInsert(idx);
            heapify(idx, count);
        }

        /**
         * 从index位置开始向下堆化操作
         */
        @SuppressWarnings("SameParameterValue")
        private void heapify(int index, int heapSize) {
            // 左子节点的位置
            int leftIdx = 2 * index + 1;
            // 左子节点不能越界
            while (leftIdx < heapSize) {
                // 右子节点的位置
                int rightsIdx = leftIdx + 1;

                int minSubIdx;
                if (rightsIdx < heapSize) {
                    // 右子节点没有越界，看左右子节点哪个小
                    Integer leftDistance = distanceMap.get(table.get(leftIdx));
                    Integer rightDistance = distanceMap.get(table.get(rightsIdx));
                    minSubIdx = leftDistance <= rightDistance ? leftIdx : rightsIdx;
                } else {
                    // 右子节点已越界了，最小子节点就是左子节点
                    minSubIdx = leftIdx;
                }

                // 父节点的距离
                Integer parentDistance = distanceMap.get(table.get(index));
                // 最小子节点的距离
                Integer minSubDistance = distanceMap.get(table.get(minSubIdx));

                // 父节点和最小子节点比较
                int minIndex = parentDistance > minSubDistance ? minSubIdx : index;
                if (minIndex == index) {
                    // 说明还是父节点最小，已经堆化完成
                    break;
                }

                // 交换父子节点位置
                swap(index, minIndex);

                // 继续往下看
                index = minIndex;
                leftIdx = 2 * index + 1;
            }
        }

        public boolean isEmpty() {
            return count == 0;
        }

        /**
         * 从index位置向上堆化
         */
        private void heapifyInsert(int index) {
            while (index > 0) {
                // 根据子节点位置算出父节点位置
                int parentIdx = (index - 1) / 2;
                // 子节点的距离
                Integer subDistance = distanceMap.get(table.get(index));
                // 父节点的距离
                Integer parentDistance = distanceMap.get(table.get(parentIdx));
                // 子节点大于父节点，不符合小根堆，进行交换
                if (subDistance > parentDistance) {
                    swap(index, parentIdx);
                }
                // 继续往上看
                index = parentIdx;
            }
        }

        private void swap(int i, int j) {
            Node nodeI = table.get(i);
            Node nodeJ = table.get(j);
            table.set(i, nodeJ);
            table.set(j, nodeI);
            nodeIndexMap.put(nodeI, j);
            nodeIndexMap.put(nodeJ, i);
        }

    }
}
