package com.practice2.unionfindset;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author Xulg
 * @since 2021-10-19 15:41
 * Description: 并查集
 */
@SuppressWarnings("MapOrSetKeyShouldOverrideHashCodeEquals")
class UnionFindSet {

    private static class UnionFindSetImpl<V> {
        private final HashMap<V, Node<V>> nodeMap;
        private final HashMap<Node<V>, Node<V>> parentMap;
        private final HashMap<Node<V>, Integer> sizeMap;

        UnionFindSetImpl(Collection<V> list) {
            nodeMap = new HashMap<>();
            parentMap = new HashMap<>();
            sizeMap = new HashMap<>();
            for (V val : list) {
                Node<V> node = new Node<>(val);
                nodeMap.put(val, node);
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
            if (nodeX == null || nodeY == null) {
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
            Node<V> small = bigger == parentX ? parentY : parentX;
            parentMap.put(small, bigger);
            sizeMap.put(bigger, sizeX + sizeY);
            sizeMap.remove(small);
        }

        public boolean isSameSet(V x, V y) {
            if (x == null || y == null) {
                return false;
            }
            Node<V> nodeX = nodeMap.get(x);
            Node<V> nodeY = nodeMap.get(y);
            if (nodeX == null || nodeY == null) {
                return false;
            }
            return findParent(nodeX) == findParent(nodeY);
        }

        public int size() {
            return sizeMap.size();
        }

        private Node<V> findParent(Node<V> node) {
            LinkedList<Node<V>> path = new LinkedList<>();
            Node<V> parent = node;
            while (parent != parentMap.get(parent)) {
                path.add(parent);
                parent = parentMap.get(parent);
            }
            while (!path.isEmpty()) {
                parentMap.put(path.poll(), parent);
            }
            return parent;
        }

        private static class Node<V> {
            private final V val;

            Node(V val) {
                this.val = val;
            }
        }
    }


}
