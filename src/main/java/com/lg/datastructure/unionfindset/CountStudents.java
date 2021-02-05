package com.lg.datastructure.unionfindset;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * 并查集的练习
 *
 * @author Xulg
 * Created in 2020-11-15 22:40
 */
class CountStudents {

    /*
     * 使用并查集解决问题
     *  学生有3个属性，省份证，B站账号id，github账号id。任意2个学生，如果
     * 他们的省份证，B站账号id，github账号id中的任意一个相等，就算是同一个学生。
     * 求：给你一大堆的学生实例，总共有多少个人？
     *-------------------------------------------------------------------------
     */

    public static int violence(List<Student> students) {
        // TODO: 2021/1/30 这个解法错误的
        if (students == null || students.isEmpty()) {
            return 0;
        }
        int count = 0;
        for (Student student : students) {
            // 查找有没有和这个人重复的
            boolean isNotSameStudent = true;
            for (Student anotherStudent : students) {
                if (student != anotherStudent) {
                    if (student.idCard.equals(anotherStudent.idCard)
                            || student.bilibiliAccountId.equals(anotherStudent.bilibiliAccountId)
                            || student.githubAccountId.equals(anotherStudent.githubAccountId)) {
                        isNotSameStudent = false;
                        break;
                    }
                }
            }
            // 没有重复，人数加1
            if (isNotSameStudent) {
                count++;
            }
        }
        return count;
    }

    public static int countTotalStudent(List<Student> students) {
        if (students == null || students.isEmpty()) {
            return 0;
        }
        UnionFindSet<Student> studentUnionFindSet = new UnionFindSet<>(students);
        HashMap<String, Student> idCardMap = new HashMap<>();
        HashMap<String, Student> bilibiliAccountIdMap = new HashMap<>();
        HashMap<String, Student> githubAccountIdMap = new HashMap<>();
        for (Student student : students) {
            if (idCardMap.containsKey(student.idCard)) {
                studentUnionFindSet.union(student, idCardMap.get(student.idCard));
            } else {
                idCardMap.put(student.idCard, student);
            }
            if (bilibiliAccountIdMap.containsKey(student.bilibiliAccountId)) {
                studentUnionFindSet.union(student, bilibiliAccountIdMap.get(student.bilibiliAccountId));
            } else {
                bilibiliAccountIdMap.put(student.bilibiliAccountId, student);
            }
            if (githubAccountIdMap.containsKey(student.githubAccountId)) {
                studentUnionFindSet.union(student, githubAccountIdMap.get(student.githubAccountId));
            } else {
                githubAccountIdMap.put(student.githubAccountId, student);
            }
        }
        return studentUnionFindSet.getSetCount();
    }

    private static class Student {
        // 身份证
        private final String idCard;
        // b站账号id
        private final String bilibiliAccountId;
        // github账号id
        private final String githubAccountId;

        Student(String idCard, String bilibiliAccountId, String githubAccountId) {
            this.idCard = idCard;
            this.bilibiliAccountId = bilibiliAccountId;
            this.githubAccountId = githubAccountId;
        }
    }

    /* ****************************************************************************************************************/

    @SuppressWarnings("all")
    private static class UnionFindSet<V> {
        private final HashMap<V, Node<V>> nodes = new HashMap<>();
        private final HashMap<Node<V>, Node<V>> parents = new HashMap<>();
        private final HashMap<Node<V>, Integer> sizeMap = new HashMap<>();

        public UnionFindSet(List<V> values) {
            for (V value : values) {
                Node<V> node = new Node<>(value);
                nodes.put(value, node);
                parents.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        public boolean isSameSet(V a, V b) {
            if (!nodes.containsKey(a) || !nodes.containsKey(b)) {
                return false;
            }
            Node<V> nodeA = nodes.get(a);
            Node<V> nodeB = nodes.get(b);
            Node<V> fatherA = findFather(nodeA);
            Node<V> fatherB = findFather(nodeB);
            return fatherA == fatherB;
        }

        public void union(V a, V b) {
            if (!nodes.containsKey(a) || !nodes.containsKey(b)) {
                return;
            }
            Node<V> nodeA = nodes.get(a);
            Node<V> nodeB = nodes.get(b);
            Node<V> fatherA = findFather(nodeA);
            Node<V> fatherB = findFather(nodeB);
            if (fatherA == fatherB) {
                return;
            }
            int sizeA = sizeMap.get(nodeA);
            int sizeB = sizeMap.get(nodeB);
            if (sizeA >= sizeB) {
                parents.put(nodeB, nodeA);
                sizeMap.put(nodeA, sizeA + sizeB);
                sizeMap.remove(nodeB);
            } else {
                parents.put(nodeA, nodeB);
                sizeMap.put(nodeB, sizeB + sizeA);
                sizeMap.remove(nodeA);
            }
        }

        public int getSetCount() {
            return sizeMap.size();
        }

        private Node<V> findFather(Node<V> current) {
            Stack<Node<V>> path = new Stack<>();
            Node<V> father = parents.get(current);
            while (father != current) {
                path.push(current);
                current = father;
                father = parents.get(current);
            }
            // 压缩
            while (!path.isEmpty()) {
                parents.put(path.pop(), father);
            }
            return father;
        }

        private static class Node<V> {
            private V value;

            Node(V value) {
                this.value = value;
            }
        }
    }
}
