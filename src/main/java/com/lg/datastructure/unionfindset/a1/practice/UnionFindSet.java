package com.lg.datastructure.unionfindset.a1.practice;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 并查集
 *
 * @author Xulg
 * Created in 2021-01-30 13:49
 */
@SuppressWarnings({"MapOrSetKeyShouldOverrideHashCodeEquals"})
class UnionFindSet<V> {

    /*
     * 解决连通性问题的利器
     *  1.有若干个样本a，b，c，d...类型假设是V。
     *  2.在并查集中一开始认为每个样本都在单独的集合里。
     *  3.用户可以在任何时候调用如下两个方法：
     *      boolean isSameSet(V x, V y);
     *          查询样本x和样本y是否属于一个集合，即x样本，y样本是否连通
     *      void union(V x, V y);
     *          把x和y各自所在集合的所有样本合并成一个集合
     *  4.isSameSet()和union()方法的代价越低越好
     *并查集的特性
     * 1）每个节点都有一条往上指的指针
     * 2）节点a往上找到的头节点，叫做a所在集合的代表节点
     * 3）查询x和y是否属于同一个集合，就是看看找到的代表节点是不是一个
     * 4）把x和y各自所在集合的所有点合并成一个集合，只需要小集合的代表点挂在大集合的代表点的下方即可
     *-------------------------------------------------------------------
     *并查集的优化
     * 1）节点往上找代表点的过程，把沿途的链变成扁平的
     * 2）小集合挂在大集合的下面
     * 3）如果方法调用很频繁，那么单次调用的代价为O(1)，两个方法都如此
     */

    /**
     * 每个节点所在样本的代表节点(代表节点的代表节点是它自己本身)
     */
    private final HashMap<Node<V>, Node<V>> nodeParentMap = new HashMap<>();

    /**
     * 值对应哪个节点
     */
    private final HashMap<V, Node<V>> valueNodeMap = new HashMap<>();

    /**
     * 每个样本的长度
     * key为样本的代表节点 value为样本大小
     */
    private final HashMap<Node<V>, Integer> sizeMap = new HashMap<>();

    public UnionFindSet(Collection<V> values) {
        for (V val : values) {
            Node<V> node = new Node<>(val);
            valueNodeMap.put(val, node);
            // 初始状态下，样本的代表节点就是自己
            nodeParentMap.put(node, node);
            // 样本的大小为1
            sizeMap.put(node, 1);
        }
    }

    /**
     * 样本x和样本y是否属于一个集合，即x样本，y样本是否连通
     */
    public boolean isSameSet(V x, V y) {
        if (x == null || y == null) {
            return false;
        }

        // 取值对应的节点
        Node<V> nodeX = valueNodeMap.get(x);
        Node<V> nodeY = valueNodeMap.get(y);

        if (nodeX == null || nodeY == null) {
            // 样本不在集合中
            return false;
        }

        // 两个样本的父节点是否一样的
        return findFather(nodeX) == findFather(nodeY);
    }

    /**
     * 查找节点所在样本的代表节点
     */
    private Node<V> findFather(Node<V> node) {
        if (false) {
            Node<V> p = null;
            while (true) {
                Node<V> parentNode = nodeParentMap.get(node);
                if (parentNode == node) {
                    // 自己指向自己了，是样本的代表节点了
                    p = parentNode;
                    break;
                } else {
                    // 继续向上找
                    node = parentNode;
                }
            }
            System.out.println(p.value);
        }
        if (false) {
            while (true) {
                if (node == nodeParentMap.get(node)) {
                    break;
                } else {
                    node = nodeParentMap.get(node);
                }
            }
            while (node != nodeParentMap.get(node)) {
                node = nodeParentMap.get(node);
            }
        }

        // 往上寻找的过程中，记录沿途经过的节点，这些节点的最终父节点都是一样的
        Queue<Node<V>> path = new LinkedList<>();
        // 从当前节点开始，不停的往上找，直到这个节点是自己指向自己的为止
        while (node != nodeParentMap.get(node)) {
            path.add(node);
            node = nodeParentMap.get(node);
        }
        // 此时node就是样本的代表节点

        // 优化：沿途的这些节点都指向最终父节点
        while (!path.isEmpty()) {
            nodeParentMap.put(path.poll(), node);
        }
        return node;
    }

    /**
     * 把x和y各自所在集合的所有样本合并成一个集合
     */
    public void union(V x, V y) {
        if (x == null || y == null) {
            return;
        }

        // 取值对应的节点
        Node<V> nodeX = valueNodeMap.get(x);
        Node<V> nodeY = valueNodeMap.get(y);
        if (nodeX == null || nodeY == null) {
            // 样本不在集合中
            return;
        }

        // 查询各自样本的代表节点
        Node<V> fatherX = findFather(nodeX);
        Node<V> fatherY = findFather(nodeY);

        if (fatherX == fatherY) {
            // 2个样本已经在一个集合中了
            return;
        }

        // 获取2个样本的大小
        int sizeX = sizeMap.get(fatherX);
        int sizeY = sizeMap.get(fatherY);

        // 大样本的代表节点
        Node<V> bigger = (sizeX >= sizeY) ? fatherX : fatherY;
        // 小样本的代表节点
        Node<V> smaller = (bigger == fatherX) ? fatherY : fatherX;

        // 小样本挂到大样本上面 小样本的代表节点指向大样本的代表节点
        // 这里间接的将小样本中的子节点都指向了大样本的代表节点，所以不需要遍历一边节点来修改小样本中的节点指向
        nodeParentMap.put(smaller, bigger);
        // 更新大样本的大小 = 大样本原来的大小 + 小样本的大小
        sizeMap.put(bigger, sizeX + sizeY);
        // 小样本的大小信息不需要了
        sizeMap.remove(smaller);
    }

    private static class Node<V> {
        V value;

        Node(V value) {
            this.value = value;
        }
    }

}
