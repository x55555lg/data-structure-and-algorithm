package com.lg.datastructure.unionfindset;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * 并查集
 *
 * @author Xulg
 * Created in 2020-11-15 20:47
 */
class UnionFind {

    /*
     *并查集
     * 解决连通性问题的利器
     *  1.有若干个样本a，b，c，d...类型假设是V。
     *  2.在并查集中一开始认为每个样本都在单独的集合里。
     *  3.用户可以在任何时候调用如下两个方法：
     *      boolean isSameSet(V x, V y);
     *          查询样本x和样本y是否属于一个集合，即x样本，y样本是否连通
     *      void union(V x, V y);
     *          把x和y各自所在集合的所有样本合并成一个集合
     *  4.isSameSet()和union()方法的代价越低越好
     *-------------------------------------------------------------------
     */

    /*
     * 方法被频繁调用后时间复杂度：O(1)，因为findFather()中的优化
     *                             证明非常复杂，数学博士都不一定看得懂
     */
    @SuppressWarnings("all")
    private static class UnionFindSet<V> {
        // 每个值V对应的节点是谁
        private final HashMap<V, Node<V>> nodes = new HashMap<>();

        // 记录节点的最终父节点是谁
        private final HashMap<Node<V>, Node<V>> parents = new HashMap<>();

        // 每个最终父节点所在的链表的长度
        // 只有一个点是代表点的时候，这里才会有记录
        private final HashMap<Node<V>, Integer> sizeMap = new HashMap<>();

        public UnionFindSet(List<V> values) {
            for (V val : values) {
                Node<V> node = new Node<>(val);
                nodes.put(val, node);
                // 自己指向自己
                parents.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        public boolean isSameSet(V x, V y) {
            if (!nodes.containsKey(x) || !nodes.containsKey(y)) {
                // 值都不在集合中，肯定false啊
                return false;
            }
            // 对应节点
            Node<V> nodeX = nodes.get(x);
            Node<V> nodeY = nodes.get(y);
            // 节点对应的最终父节点
            Node<V> fatherX = findFather(nodeX);
            Node<V> fatherY = findFather(nodeY);
            // 如果它们的最终父节点是一样的，说明在同一个集合中
            return fatherX == fatherY;
        }

        public void union(int x, int y) {
            if (!nodes.containsKey(x) || !nodes.containsKey(y)) {
                // 值都不在集合中啊
                return;
            }

            // 对应节点
            Node nodeX = nodes.get(x);
            Node nodeY = nodes.get(y);

            // 节点对应的最终父节点
            Node<V> fatherX = findFather(nodeX);
            Node<V> fatherY = findFather(nodeY);

            if (fatherX == fatherY) {
                // 它们的最终父节点都一样的，说明早就连通了，
                // 已经不需要进行合并操作了
                return;
            }

            // 长度小的合并到长度大的上面去
            int sizeX = sizeMap.get(fatherX);
            int sizeY = sizeMap.get(fatherY);
            if (sizeX >= sizeY) {
                // x所在样本的长度大于等于y所在样本的长度
                // y的最终父节点挂到x上去
                parents.put(fatherY, fatherX);
                // x样本的长度需要加上y样本的长度
                sizeMap.put(fatherX, sizeX + sizeY);
                // 合并之后不需要记录y所在样本的长度了
                sizeMap.remove(fatherY);
            } else {
                // x所在样本的长度小yx所在样本的长度
                parents.put(fatherX, fatherY);
                // y样本的长度需要加上x样本的长度
                sizeMap.put(fatherY, sizeY + sizeX);
                // 合并之后不需要记录x所在样本的长度了
                sizeMap.remove(fatherX);
            }
        }

        private Node<V> findFather(Node<V> node) {
            // 从当前节点开始，不停的往上找，直到这个节点是自己指向自己的为止
            Node<V> fatherNode = parents.get(node);
            // 往上寻找的过程中，记录沿途经过的节点，这些节点的最终父节点都是一样的
            Stack<Node<V>> path = new Stack<>();
            while (node != fatherNode) {
                path.push(node);
                node = fatherNode;
            }
            // 优化：沿途的这些节点都指向最终父节点
            while (!path.isEmpty()) {
                parents.put(path.pop(), fatherNode);
            }
            return fatherNode;
        }

        private static class Node<V> {
            private V value;

            Node(V value) {
                this.value = value;
            }
        }
    }
}
