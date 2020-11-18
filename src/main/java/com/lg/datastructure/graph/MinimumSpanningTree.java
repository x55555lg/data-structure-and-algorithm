package com.lg.datastructure.graph;

import com.lg.datastructure.graph.base.Graph;
import com.lg.datastructure.graph.base.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
     *      需要使用并查集这个工具
     *      1.总是从权重最小的边开始考虑，依次考察权重依次变大的边(按照权重从小到大的顺序访问边)
     *      2.当前的边要么进入最小生成树的集合，要么丢弃
     *      3.如果当前的边进入最小生成树的集合中不会形成环，就要当前边
     *      4.如果当前的边进入最小生成树的集合中会形成环，就不要当前边
     *      5.考察完所有边之后，最小生成树的集合也得到了
     */

    /**
     * Kruskal算法计算图的最小生成树
     */
    public static void kruskal(Graph graph) {
        
    }

    @SuppressWarnings("all")
    private static class UnionFindSet {
        private final HashMap<Node, Node> parents;
        private final HashMap<Node, Integer> sizeMap;

        UnionFindSet(List<Node> nodes) {
            parents = new HashMap<>();
            sizeMap = new HashMap<>();
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
}
