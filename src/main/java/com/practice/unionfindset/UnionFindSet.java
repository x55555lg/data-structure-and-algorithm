package com.practice.unionfindset;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 并查集
 *
 * @author Xulg
 * Created in 2021-05-12 9:24
 */
@SuppressWarnings("MapOrSetKeyShouldOverrideHashCodeEquals")
class UnionFindSet<V> {

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
        if (nodeX == null | nodeY == null) {
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

        // 小的挂到大的上面
        parentMap.put(smaller, bigger);
        sizeMap.put(bigger, sizeX + sizeY);
        sizeMap.remove(smaller);
    }

    public boolean isSameSet(V x, V y) {
        Node<V> nodeX = nodeMap.get(x);
        Node<V> nodeY = nodeMap.get(y);
        if (nodeX == null | nodeY == null) {
            return false;
        }
        return findParent(nodeX) == findParent(nodeY);
    }

    public int size() {
        return sizeMap.size();
    }

    private Node<V> findParent(Node<V> node) {
        Queue<Node<V>> path = new LinkedList<>();
        while (parentMap.get(node) != node) {
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
    }

    public static void main(String[] args) {

    }
}
