package com.lg.datastructure.unionfindset.a1.practice;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * @author Xulg
 * Created in 2021-01-30 15:35
 */
class CountStudents {

    /*
     * 使用并查集解决问题
     *  学生有3个属性，身份证，B站账号id，github账号id。任意2个学生，如果
     * 他们的省份证，B站账号id，github账号id中的任意一个相等，就算是同一个学生。
     * 求：给你一大堆的学生实例，总共有多少个人？
     *-------------------------------------------------------------------------
     */

    /**
     * 暴力解
     */
    public static int countByViolence(List<Student> students) {
        if (students == null || students.isEmpty()) {
            return 0;
        }

        // TODO: 2021/1/31 写的是错误的

        if (false) {
            List<Student> already = new ArrayList<>();
            int count = 0;
            for (Student student : students) {
                boolean isNotSame = true;
                for (Student another : already) {
                    if (another.idCard.equals(student.idCard)
                            || another.bilibiliAccountId.equals(student.bilibiliAccountId)
                            || another.githubAccountId.equals(student.githubAccountId)) {
                        isNotSame = false;
                        break;
                    }
                }
                if (isNotSame) {
                    count++;
                }
                already.add(student);
            }
            return count;
        }
        ArrayList<Student> list = CollectionUtil.distinct(students);
        return list.size();
    }

//    /**
//     * 使用并查集解
//     */
//    public static int countByUnionFindSet2(List<Student> students) {
//        if (students == null || students.isEmpty()) {
//            return 0;
//        }
//        HashMap<String, Queue<Student>> idCardIndexMap = new HashMap<>(16);
//        HashMap<String, Queue<Student>> bilibiliAccountIdIndexMap = new HashMap<>(16);
//        HashMap<String, Queue<Student>> githubAccountIdIndexMap = new HashMap<>(16);
//        UnionFindSet<Student> unionFindSet = new UnionFindSet<>(students);
//        for (Student student : students) {
//            // idCard
//            if (idCardIndexMap.containsKey(student.idCard)) {
//                Queue<Student> existStudents = idCardIndexMap.get(student.idCard);
//                for (Student existStudent : existStudents) {
//                    // 之前出现过这个身份证了，进行合并样本操作
//                    unionFindSet.union(student, existStudent);
//                }
//            } else {
//                // 没出现过这个身份证，记录下来
//                if (idCardIndexMap.get(student.idCard) == null) {
//                    Queue<Student> queue = new LinkedList<>();
//                    queue.add(student);
//                    idCardIndexMap.put(student.idCard, queue);
//                } else {
//                    idCardIndexMap.get(student.idCard).add(student);
//                }
//            }
//            // bilibiliAccountId
//            if (bilibiliAccountIdIndexMap.containsKey(student.bilibiliAccountId)) {
//                Queue<Student> existStudents = bilibiliAccountIdIndexMap.get(student.bilibiliAccountId);
//                for (Student existStudent : existStudents) {
//                    // 之前出现过这个哔哩哔哩账号了，进行合并样本操作
//                    unionFindSet.union(student, existStudent);
//                }
//            } else {
//                // 没出现过这个哔哩哔哩账号，记录下来
//                if (bilibiliAccountIdIndexMap.get(student.bilibiliAccountId) == null) {
//                    Queue<Student> queue = new LinkedList<>();
//                    queue.add(student);
//                    bilibiliAccountIdIndexMap.put(student.bilibiliAccountId, queue);
//                } else {
//                    bilibiliAccountIdIndexMap.get(student.bilibiliAccountId).add(student);
//                }
//            }
//            // githubAccountId
//            if (githubAccountIdIndexMap.containsKey(student.githubAccountId)) {
//                Queue<Student> existStudents = githubAccountIdIndexMap.get(student.githubAccountId);
//                for (Student existStudent : existStudents) {
//                    // 之前出现过这个GitHub账号了，进行合并样本操作
//                    unionFindSet.union(student, existStudent);
//                }
//            } else {
//                // 没出现过这个GitHub账号，记录下来
//                if (githubAccountIdIndexMap.get(student.githubAccountId) == null) {
//                    Queue<Student> queue = new LinkedList<>();
//                    queue.add(student);
//                    githubAccountIdIndexMap.put(student.githubAccountId, queue);
//                } else {
//                    githubAccountIdIndexMap.get(student.githubAccountId).add(student);
//                }
//            }
//        }
//
//        // 向并查集询问，合并之后，还有多少个样本？
//        return unionFindSet.size();
//    }

    /**
     * 使用并查集解
     */
    public static int countByUnionFindSet(List<Student> students) {
        if (students == null || students.isEmpty()) {
            return 0;
        }
        HashMap<String, Student> idCardIndexMap = new HashMap<>(16);
        HashMap<String, Student> bilibiliAccountIdIndexMap = new HashMap<>(16);
        HashMap<String, Student> githubAccountIdIndexMap = new HashMap<>(16);
        UnionFindSet<Student> unionFindSet = new UnionFindSet<>(students);
        for (Student student : students) {
            // idCard
            if (idCardIndexMap.containsKey(student.idCard)) {
                Student existStudent = idCardIndexMap.get(student.idCard);
                // 之前出现过这个身份证了，进行合并样本操作
                unionFindSet.union(student, existStudent);
            } else {
                // 没出现过这个身份证，记录下来
                idCardIndexMap.put(student.idCard, student);
            }
            // bilibiliAccountId
            if (bilibiliAccountIdIndexMap.containsKey(student.bilibiliAccountId)) {
                Student existStudent = bilibiliAccountIdIndexMap.get(student.bilibiliAccountId);
                // 之前出现过这个哔哩哔哩账号了，进行合并样本操作
                unionFindSet.union(student, existStudent);
            } else {
                // 没出现过这个哔哩哔哩账号，记录下来
                bilibiliAccountIdIndexMap.put(student.bilibiliAccountId, student);
            }
            // githubAccountId
            if (githubAccountIdIndexMap.containsKey(student.githubAccountId)) {
                Student existStudent = githubAccountIdIndexMap.get(student.githubAccountId);
                // 之前出现过这个GitHub账号了，进行合并样本操作
                unionFindSet.union(student, existStudent);
            } else {
                // 没出现过这个GitHub账号，记录下来
                githubAccountIdIndexMap.put(student.githubAccountId, student);
            }
        }

        // 向并查集询问，合并之后，还有多少个样本？
        return unionFindSet.size();
    }

    private static class Student {
        private String idCard;
        private String bilibiliAccountId;
        private String githubAccountId;

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getBilibiliAccountId() {
            return bilibiliAccountId;
        }

        public void setBilibiliAccountId(String bilibiliAccountId) {
            this.bilibiliAccountId = bilibiliAccountId;
        }

        public String getGithubAccountId() {
            return githubAccountId;
        }

        public void setGithubAccountId(String githubAccountId) {
            this.githubAccountId = githubAccountId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Student student = (Student) o;
            return idCard.equals(student.idCard) || bilibiliAccountId.equals(student.bilibiliAccountId)
                    || githubAccountId.equals(student.githubAccountId);
        }

        @Override
        public int hashCode() {
            int result = idCard != null ? idCard.hashCode() : 0;
            result = 31 * result + (bilibiliAccountId != null ? bilibiliAccountId.hashCode() : 0);
            result = 31 * result + (githubAccountId != null ? githubAccountId.hashCode() : 0);
            return result;
        }
    }

    /**
     * 并查集简单实现
     */
    @SuppressWarnings({"MapOrSetKeyShouldOverrideHashCodeEquals", "DuplicatedCode"})
    private static class UnionFindSet<V> {

        // 值和节点映射关系
        private final HashMap<V, Node<V>> valueNodeMap = new HashMap<>();

        // 节点所在样本的代表节点是哪个
        private final HashMap<Node<V>, Node<V>> nodeParentMap = new HashMap<>();

        // 每个样本的大小 sizeMap.size()就是有多少个样本 key为样本的代表节点 value为样本大小
        private final HashMap<Node<V>, Integer> sizeMap = new HashMap<>();

        UnionFindSet(Collection<V> list) {
            for (V value : list) {
                Node<V> node = new Node<>(value);
                valueNodeMap.put(value, node);
                nodeParentMap.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        public boolean isSameSet(V x, V y) {
            if (x == null || y == null) {
                return false;
            }
            Node<V> nodeX = valueNodeMap.get(x);
            Node<V> nodeY = valueNodeMap.get(y);
            if (nodeX == null || nodeY == null) {
                return false;
            }
            Node<V> fatherX = findFather(nodeX);
            Node<V> fatherY = findFather(nodeY);
            return fatherX == fatherY;
        }

        public void union(V x, V y) {
            if (x == null || y == null) {
                return;
            }

            Node<V> nodeX = valueNodeMap.get(x);
            Node<V> nodeY = valueNodeMap.get(y);
            if (nodeX == null || nodeY == null) {
                return;
            }

            Node<V> fatherX = findFather(nodeX);
            Node<V> fatherY = findFather(nodeY);
            if (fatherX == fatherY) {
                return;
            }

            int sizeX = sizeMap.get(fatherX);
            int sizeY = sizeMap.get(fatherY);
            Node<V> bigger = sizeX >= sizeY ? fatherX : fatherY;
            Node<V> smaller = bigger == fatherX ? fatherY : fatherX;

            // union operation
            nodeParentMap.put(smaller, bigger);
            sizeMap.put(bigger, sizeX + sizeY);
            sizeMap.remove(smaller);
        }

        public int size() {
            return sizeMap.size();
        }

        private Node<V> findFather(Node<V> node) {
            assert node != null : "node can not been null";
            Queue<Node<V>> path = new LinkedList<>();
            while (node != nodeParentMap.get(node)) {
                path.add(node);
                node = nodeParentMap.get(node);
            }
            while (!path.isEmpty()) {
                nodeParentMap.put(path.poll(), node);
            }
            return node;
        }

        private static class Node<V> {
            V value;

            Node(V v) {
                value = v;
            }
        }

    }

    /* ************************************************************************************************************** */

    /* 对数器 */

    private static List<Student> generateTestData(int maxSize) {
        if (maxSize <= 0) {
            return new ArrayList<>(0);
        }

        HashSet<String> idCards = new HashSet<>();
        HashSet<String> bilibiliAccountIds = new HashSet<>();
        HashSet<String> githubAccountIds = new HashSet<>();

        //int maxLimit = RandomUtil.randomInt(3, 5);
        int maxLimit = maxSize * 10;
        for (int count = RandomUtil.randomInt(maxLimit); count >= 0; ) {
            String idCard = RandomUtil.randomStringUpper(10);
            if (!idCards.contains(idCard)) {
                idCards.add(idCard);
                count--;
            }
        }
        for (int count = RandomUtil.randomInt(maxLimit); count >= 0; ) {
            String bilibiliAccountId = RandomUtil.randomStringUpper(10);
            if (!bilibiliAccountIds.contains(bilibiliAccountId)) {
                bilibiliAccountIds.add(bilibiliAccountId);
                count--;
            }
        }
        for (int count = RandomUtil.randomInt(maxLimit); count >= 0; ) {
            String githubAccountId = RandomUtil.randomStringUpper(10);
            if (!githubAccountIds.contains(githubAccountId)) {
                githubAccountIds.add(githubAccountId);
                count--;
            }
        }

        List<Student> list = new ArrayList<>(maxSize);
        for (int i = 0; i < maxSize; i++) {
            Student student = new Student();
            student.idCard = CollectionUtil.get(idCards, RandomUtil.randomInt(idCards.size()));
            student.bilibiliAccountId = CollectionUtil.get(bilibiliAccountIds, RandomUtil.randomInt(bilibiliAccountIds.size()));
            student.githubAccountId = CollectionUtil.get(githubAccountIds, RandomUtil.randomInt(githubAccountIds.size()));
            list.add(student);
        }
        return list;
    }

    /* ************************************************************************************************************** */

    public static void main(String[] args) {
        int times = 1000000;
        boolean success = true;
        for (int time = 0; time < times; time++) {
            List<Student> students = generateTestData(RandomUtil.randomInt(0, 1000));
            //int r1 = countByUnionFindSet2(students);
            int r2 = countByUnionFindSet(students);
            int r3 = mergeUsers(students);
            if (!(r2 == r3)) {
                System.out.println("Oops " + " r2=" + r2 + " r3=" + r3);
                success = false;
                System.out.println(JSON.toJSONString(students, true));
                break;
            }
        }
        System.out.println("finish! " + (success ? "Nice!" : "Fucking fucked"));
    }

    public static int mergeUsers(List<Student> users) {
        UnionSet<Student> unionFind = new UnionSet<>(users);
        HashMap<String, Student> mapA = new HashMap<>();
        HashMap<String, Student> mapB = new HashMap<>();
        HashMap<String, Student> mapC = new HashMap<>();
        for (Student user : users) {
            if (mapA.containsKey(user.idCard)) {
                unionFind.union(user, mapA.get(user.idCard));
            } else {
                mapA.put(user.idCard, user);
            }
            if (mapB.containsKey(user.bilibiliAccountId)) {
                unionFind.union(user, mapB.get(user.bilibiliAccountId));
            } else {
                mapB.put(user.bilibiliAccountId, user);
            }
            if (mapC.containsKey(user.githubAccountId)) {
                unionFind.union(user, mapC.get(user.githubAccountId));
            } else {
                mapC.put(user.githubAccountId, user);
            }
        }
        // 向并查集询问，合并之后，还有多少个集合？
        return unionFind.getSetNum();
    }

    @SuppressWarnings("all")
    public static class UnionSet<V> {
        public HashMap<V, Node<V>> nodes;
        public HashMap<Node<V>, Node<V>> parents;
        public HashMap<Node<V>, Integer> sizeMap;

        public UnionSet(List<V> values) {
            nodes = new HashMap<>();
            parents = new HashMap<>();
            sizeMap = new HashMap<>();
            for (V cur : values) {
                Node<V> node = new Node<>(cur);
                nodes.put(cur, node);
                parents.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        // 从点cur开始，一直往上找，找到不能再往上的代表点，返回
        public Node<V> findFather(Node<V> cur) {
            Stack<Node<V>> path = new Stack<>();
            while (cur != parents.get(cur)) {
                path.push(cur);
                cur = parents.get(cur);
            }
            // cur头节点
            while (!path.isEmpty()) {
                parents.put(path.pop(), cur);
            }
            return cur;
        }

        public boolean isSameSet(V a, V b) {
            if (!nodes.containsKey(a) || !nodes.containsKey(b)) {
                return false;
            }
            return findFather(nodes.get(a)) == findFather(nodes.get(b));
        }

        public void union(V a, V b) {
            if (!nodes.containsKey(a) || !nodes.containsKey(b)) {
                return;
            }
            Node<V> aHead = findFather(nodes.get(a));
            Node<V> bHead = findFather(nodes.get(b));
            if (aHead != bHead) {
                int aSetSize = sizeMap.get(aHead);
                int bSetSize = sizeMap.get(bHead);
                Node<V> big = aSetSize >= bSetSize ? aHead : bHead;
                Node<V> small = big == aHead ? bHead : aHead;
                parents.put(small, big);
                sizeMap.put(big, aSetSize + bSetSize);
                sizeMap.remove(small);
            }
        }


        public int getSetNum() {
            return sizeMap.size();
        }

        private static class Node<V> {
            V value;

            Node(V v) {
                value = v;
            }
        }

    }
}
