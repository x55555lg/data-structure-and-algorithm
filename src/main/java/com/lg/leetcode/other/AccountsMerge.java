package com.lg.leetcode.other;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.TreeSet;

/**
 * @author Xulg
 * Description: leetcode_721
 * Created in 2021-05-29 12:28
 */
@SuppressWarnings("MapOrSetKeyShouldOverrideHashCodeEquals")
class AccountsMerge {
    /*
     *  Given a list of accounts where each element accounts[i] is a list of strings, where the first
     * element accounts[i][0] is a name, and the rest of the elements are emails representing emails
     * of the account.
     *  Now, we would like to merge these accounts. Two accounts definitely belong to the same person
     * if there is some common email to both accounts. Note that even if two accounts have the same name,
     * they may belong to different people as people could have the same name. A person can have any number
     * of accounts initially, but all of their accounts definitely have the same name.
     *  After merging the accounts, return the accounts in the following format: the first element of each
     * account is the name, and the rest of the elements are emails in sorted order. The accounts themselves
     * can be returned in any order.
     *
     * Example 1:
     * Input: accounts = [
     *      ["John","johnsmith@mail.com","john_newyork@mail.com"],
     *      ["John","johnsmith@mail.com","john00@mail.com"],
     *      ["Mary","mary@mail.com"],
     *      ["John","johnnybravo@mail.com"]
     * ]
     * Output: [
     *      ["John","john00@mail.com","john_newyork@mail.com","johnsmith@mail.com"],
     *      ["Mary","mary@mail.com"],
     *      ["John","johnnybravo@mail.com"]
     * ]
     * Explanation:
     * The first and third John's are the same person as they have the common email "johnsmith@mail.com".
     * The second John and Mary are different people as none of their email addresses are used by other accounts.
     * We could return these lists in any order, for example the answer [['Mary', 'mary@mail.com'], ['John', 'johnnybravo@mail.com'],
     * ['John', 'john00@mail.com', 'john_newyork@mail.com', 'johnsmith@mail.com']] would still be accepted.
     *
     * Example 2:
     * Input: accounts = [
     *      ["Gabe","Gabe0@m.co","Gabe3@m.co","Gabe1@m.co"],
     *      ["Kevin","Kevin3@m.co","Kevin5@m.co","Kevin0@m.co"],
     *      ["Ethan","Ethan5@m.co","Ethan4@m.co","Ethan0@m.co"],
     *      ["Hanzo","Hanzo3@m.co","Hanzo1@m.co","Hanzo0@m.co"],
     *      ["Fern","Fern5@m.co","Fern1@m.co","Fern0@m.co"]
     * ]
     * Output: [
     *      ["Ethan","Ethan0@m.co","Ethan4@m.co","Ethan5@m.co"],
     *      ["Gabe","Gabe0@m.co","Gabe1@m.co","Gabe3@m.co"],
     *      ["Hanzo","Hanzo0@m.co","Hanzo1@m.co","Hanzo3@m.co"],
     *      ["Kevin","Kevin0@m.co","Kevin3@m.co","Kevin5@m.co"],
     *      ["Fern","Fern0@m.co","Fern1@m.co","Fern5@m.co"]
     * ]
     *
     * Constraints:
     * 1 <= accounts.length <= 1000
     * 2 <= accounts[i].length <= 10
     * 1 <= accounts[i][j] <= 30
     * accounts[i][0] consists of English letters.
     * accounts[i][j] (for j > 0) is a valid email.
     */

    private static class UnionFindSolution {
        public static List<List<String>> accountsMerge(List<List<String>> accounts) {
            if (accounts == null || accounts.isEmpty()) {
                return new ArrayList<>(0);
            }

            // preprocess account list, let code more readable
            List<User> users = new ArrayList<>(accounts.size());
            for (List<String> account : accounts) {
                String name = account.get(0);
                List<String> emails = account.subList(1, account.size());
                users.add(new User(name, emails));
            }

            HashMap<String, User> emailMap = new HashMap<>(users.size() * 4 / 3 + 1);
            UnionFindSet<User> unionFindSet = new UnionFindSet<>(users);
            for (User user : users) {
                for (String email : user.emails) {
                    if (emailMap.containsKey(email)) {
                        // 当前用户的邮箱和之前某个用户的邮箱一样，进行合并
                        unionFindSet.union(user, emailMap.get(email));
                    } else {
                        emailMap.put(email, user);
                    }
                }
            }

            // merge
            HashMap<User, List<User>> parentMap = new HashMap<>(unionFindSet.sizeMap.size() * 4 / 3 + 1);
            for (User user : users) {
                User parent = unionFindSet.findParent(user);
                if (parentMap.containsKey(parent)) {
                    parentMap.get(parent).add(user);
                } else {
                    List<User> subs = new LinkedList<>();
                    subs.add(user);
                    parentMap.put(parent, subs);
                }
            }
            List<List<String>> answer = new ArrayList<>();
            for (User parent : parentMap.keySet()) {
                List<User> subs = parentMap.get(parent);
                TreeSet<String> emails = new TreeSet<>();
                for (User sub : subs) {
                    emails.addAll(sub.emails);
                }
                LinkedList<String> info = new LinkedList<>();
                info.addFirst(parent.name);
                info.addAll(emails);
                answer.add(info);
            }
            return answer;
        }

        private static class User {
            private final String name;
            private final TreeSet<String> emails;

            User(String name, List<String> emails) {
                this.name = name;
                this.emails = new TreeSet<>(emails);
            }
        }

        private static class UnionFindSet<V> {
            private final HashMap<V, V> indexMap;
            private final HashMap<V, V> parentMap;
            private final LinkedHashMap<V, Integer> sizeMap;

            UnionFindSet(List<V> vs) {
                int size = vs.size() * 4 / 3 + 1;
                indexMap = new HashMap<>(size);
                parentMap = new HashMap<>(size);
                sizeMap = new LinkedHashMap<>(size);
                for (V v : vs) {
                    indexMap.put(v, v);
                    parentMap.put(v, v);
                    sizeMap.put(v, 1);
                }
            }

            public boolean isSameSet(V x, V y) {
                if (x == null || y == null) {
                    return false;
                }
                V nodeX = indexMap.get(x);
                V nodeY = indexMap.get(y);
                if (nodeX == null || nodeY == null) {
                    return false;
                }
                V parentX = findParent(nodeX);
                V parentY = findParent(nodeY);
                return parentX == parentY;
            }

            public void union(V x, V y) {
                if (x == null || y == null) {
                    return;
                }
                V nodeX = indexMap.get(x);
                V nodeY = indexMap.get(y);
                if (nodeX == null || nodeY == null) {
                    return;
                }
                V parentX = findParent(nodeX);
                V parentY = findParent(nodeY);
                if (parentX == parentY) {
                    return;
                }
                int sizeX = sizeMap.get(parentX);
                int sizeY = sizeMap.get(parentY);

                V big = sizeX > sizeY ? parentX : parentY;
                V small = big == parentX ? parentY : parentX;

                parentMap.put(small, big);
                sizeMap.put(big, sizeX + sizeY);
                sizeMap.remove(small);
            }

            public V findParent(V v) {
                Queue<V> path = new LinkedList<>();
                while (parentMap.get(v) != v) {
                    path.add(v);
                    v = parentMap.get(v);
                }
                while (!path.isEmpty()) {
                    parentMap.put(path.poll(), v);
                }
                return v;
            }
        }
    }

    public static void main(String[] args) {
        /*
         * Output: [
         *      ["John","john00@mail.com","john_newyork@mail.com","johnsmith@mail.com"],
         *      ["Mary","mary@mail.com"],
         *      ["John","johnnybravo@mail.com"]
         * ]
         */
        List<List<String>> accounts = new ArrayList<>();
        accounts.add(new ArrayList<>(Arrays.asList("John", "johnsmith@mail.com", "john_newyork@mail.com")));
        accounts.add(new ArrayList<>(Arrays.asList("John", "johnsmith@mail.com", "john00@mail.com")));
        accounts.add(new ArrayList<>(Arrays.asList("Mary", "mary@mail.com")));
        accounts.add(new ArrayList<>(Arrays.asList("John", "johnnybravo@mail.com")));
        List<List<String>> merge = UnionFindSolution.accountsMerge(accounts);
        for (List<String> list : merge) {
            System.out.println(list);
        }

        accounts = new ArrayList<>();
        accounts.add(new ArrayList<>(Arrays.asList("Fern", "Fern8@m.co", "Fern9@m.co")));
        accounts.add(new ArrayList<>(Arrays.asList("Fern", "Fern7@m.co", "Fern8@m.co")));
        accounts.add(new ArrayList<>(Arrays.asList("Fern", "Fern4@m.co", "Fern5@m.co")));
        accounts.add(new ArrayList<>(Arrays.asList("Fern", "Fern10@m.co", "Fern11@m.co")));
        accounts.add(new ArrayList<>(Arrays.asList("Fern", "Fern9@m.co", "Fern10@m.co")));
        accounts.add(new ArrayList<>(Arrays.asList("Fern", "Fern6@m.co", "Fern7@m.co")));
        accounts.add(new ArrayList<>(Arrays.asList("Fern", "Fern1@m.co", "Fern2@m.co")));
        accounts.add(new ArrayList<>(Arrays.asList("Fern", "Fern11@m.co", "Fern12@m.co")));
        accounts.add(new ArrayList<>(Arrays.asList("Fern", "Fern3@m.co", "Fern4@m.co")));
        accounts.add(new ArrayList<>(Arrays.asList("Fern", "Fern2@m.co", "Fern3@m.co")));
        accounts.add(new ArrayList<>(Arrays.asList("Fern", "Fern5@m.co", "Fern6@m.co")));
        accounts.add(new ArrayList<>(Arrays.asList("Fern", "Fern0@m.co", "Fern1@m.co")));
        merge = UnionFindSolution.accountsMerge(accounts);
        for (List<String> list : merge) {
            System.out.println(list);
        }

        accounts = new ArrayList<>();
        accounts.add(new ArrayList<>(Arrays.asList("David", "David0@m.co", "David1@m.co")));
        accounts.add(new ArrayList<>(Arrays.asList("David", "David3@m.co", "David4@m.co")));
        accounts.add(new ArrayList<>(Arrays.asList("David", "David4@m.co", "David5@m.co")));
        accounts.add(new ArrayList<>(Arrays.asList("David", "David2@m.co", "David3@m.co")));
        accounts.add(new ArrayList<>(Arrays.asList("David", "David1@m.co", "David2@m.co")));
        merge = UnionFindSolution.accountsMerge(accounts);
        for (List<String> list : merge) {
            System.out.println(list);
        }
    }
}
