package com.lg.datastructure.graph;

import com.lg.datastructure.graph.base.Graph;
import com.lg.datastructure.graph.base.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 图的拓扑排序
 *
 * @author Xulg
 * Created in 2020-11-18 17:32
 */
@SuppressWarnings("MapOrSetKeyShouldOverrideHashCodeEquals")
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

    public static List<Node> sortedTopology(Graph graph) {
        if (graph == null) {
            return null;
        }

        /*
         * 记录每个节点的剩余的入度是多少的，第一次加入节点的时候，节点的剩余入度就是节点的初始入度值
         * key：  节点本身
         * value：节点的剩余入度
         */
        HashMap<Node, Integer> nodeInDegreeIndexMap = new HashMap<>(graph.nodes.size() * 4 / 3 + 1);

        /*
         * 记录入度为0的节点有哪些啊
         */
        Queue<Node> zeroInDegreeSet = new LinkedList<>();

        // 遍历图的所有节点，将节点的入度值记录下来
        for (Node node : graph.nodes.values()) {
            // 记录节点的入度是多少啊
            nodeInDegreeIndexMap.put(node, node.in);
            // 如果节点的入度为0则记录
            if (node.in == 0) {
                zeroInDegreeSet.add(node);
            }
        }

        // 拓扑排序的结果，依次加入result
        ArrayList<Node> result = new ArrayList<>();

        /*
         * 删除这些入度为0的节点
         * 这里的删除不能修改传入的图，所谓的删除只会操作节点的入度值
         * 把一个节点从图中删除，那么这个删除节点的每个邻接点的入度都需要减少1
         */
        while (!zeroInDegreeSet.isEmpty()) {
            Node node = zeroInDegreeSet.poll();
            // 加入排序结果集中
            result.add(node);
            // 被删节点的邻接节点的入度减少
            for (Node next : node.nexts) {
                // 更新节点的入度
                int remainInDegree = nodeInDegreeIndexMap.get(next) - 1;
                nodeInDegreeIndexMap.put(next, remainInDegree);
                // 如果入度为0了则记录，后面需要删除节点
                if (remainInDegree == 0) {
                    zeroInDegreeSet.add(next);
                }
            }
        }
        return result;
    }

}
