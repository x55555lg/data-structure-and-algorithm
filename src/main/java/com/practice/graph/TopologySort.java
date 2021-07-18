package com.practice.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Xulg
 * Created in 2021-05-12 15:16
 */
class TopologySort {

    /*
     *有向无环图如下：
     *      ③→⑤→⑦→⑨
     *          ↑
     *          ④←⑧
     * 实现思路：
     *      1.在图中找到所有入度为0的点输出
     *      2.把所有的入度为0的点从图中删除，继续寻找入度为0的点输出，周而复始
     *      3.图的所有点都被删除后，依次输出的顺序就是拓扑排序
     *------------------------------------------------------------------------
     */

    public static List<Node> sort(Graph graph) {
        if (graph == null) {
            return null;
        }

        // 拓扑排序的结果，依次加入result
        ArrayList<Node> result = new ArrayList<>();

        // 存入度为0的节点
        Queue<Node> queue = new LinkedList<>();
        // 记录每个节点的剩余入度
        HashMap<Node, Integer> remainInMap = new HashMap<>(
                graph.nodes.values().size() * 4 / 3 + 1);
        for (Node node : graph.nodes.values()) {
            if (node.in == 0) {
                queue.add(node);
            }
            remainInMap.put(node, node.in);
        }
        // 删除入度为0的节点
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            result.add(node);
            // 被删节点的所有邻接节点的入度都要减1
            for (Node nextNode : node.nexts) {
                int in = remainInMap.get(nextNode) - 1;
                remainInMap.put(nextNode, in);
                if (in == 0) {
                    // 减完之后入度为0，加入队列
                    queue.add(nextNode);
                }
            }
        }
        return result;
    }
}
