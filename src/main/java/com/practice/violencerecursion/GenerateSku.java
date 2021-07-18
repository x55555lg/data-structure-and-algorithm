package com.practice.violencerecursion;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Xulg
 * Description: 规格规格值SKU排列组合
 * Created in 2021-05-19 17:34
 */
class GenerateSku {

    private static class BruteForce {
        public static List<List<String>> cartesianProduct(List<List<String>> candidates) {
            if (candidates == null || candidates.isEmpty()) {
                return new ArrayList<>(0);
            }
            List<List<String>> result = new ArrayList<>();
            recurse(0, new ArrayList<>(), result, candidates);
            return result;
        }

        private static void recurse(int curIdx, List<String> path, List<List<String>> result, List<List<String>> candidates) {
            if (curIdx == candidates.size()) {
                result.add(path);
                return;
            }
            for (String specValue : candidates.get(curIdx)) {
                List<String> newPath = new ArrayList<>(path);
                newPath.add(specValue);
                recurse(curIdx + 1, newPath, result, candidates);
            }
        }
    }

    private static class Compare {
        public static <T> List<List<T>> cartesianProduct(List<List<T>> sets) {
            if (sets == null || sets.size() == 0) {
                return new ArrayList<>(0);
            }
            int total = 1;
            //声明进位指针cIndex
            int cIndex = sets.size() - 1;
            //声明counterMap(角标 - counter)
            int[] counterMap = new int[sets.size()];
            for (int i = 0; i < sets.size(); i++) {
                counterMap[i] = 0;
                total *= (sets.get(i) == null || sets.get(i).size() == 0 ? 1 : sets.get(i).size());
            }
            List<List<T>> rt = new ArrayList<>(total);
            //开始求笛卡尔积
            while (cIndex >= 0) {
                List<T> element = new ArrayList<>(sets.size());
                for (int j = 0; j < sets.size(); j++) {
                    List<T> set = sets.get(j);
                    //忽略空集
                    if (set != null && set.size() > 0) {
                        element.add(set.get(counterMap[j]));
                    }
                    //从末位触发指针进位
                    if (j == sets.size() - 1) {
                        if (set == null || ++counterMap[j] > set.size() - 1) {
                            //重置指针
                            counterMap[j] = 0;
                            //进位
                            int cidx = j;
                            while (--cidx >= 0) {
                                //判断如果刚好前一位也要进位继续重置指针进位
                                if (sets.get(cidx) == null || ++counterMap[cidx] > sets.get(cidx).size() - 1) {
                                    counterMap[cidx] = 0;
                                    continue;
                                }
                                break;
                            }
                            if (cidx < cIndex) {
                                //移动进位指针
                                cIndex = cidx;
                            }
                        }
                    }
                }
                if (element.size() > 0) {
                    rt.add(element);
                }
            }
            return rt;
        }
    }

    public static void main(String[] args) {
        List<String> sex = Arrays.asList("男", "女");
        List<String> color = Arrays.asList("黑", "红", "白");
        List<String> size = Arrays.asList("大", "中", "小");

        List<List<String>> list = new ArrayList<>();
        list.add(sex);
        list.add(color);
        list.add(size);
        List<List<String>> cartesianProduct = Compare.cartesianProduct(list);
        System.out.println(JSON.toJSONString(cartesianProduct, true));

        List<List<String>> lists = BruteForce.cartesianProduct(list);
        System.out.println(JSON.toJSONString(lists, true));

        Assert.isTrue(cartesianProduct.equals(lists));

        SpecNameAndValues e1 = new SpecNameAndValues(new SpecName(1, "性别"),
                Arrays.asList(
                        new SpecValue(1, "男"),
                        new SpecValue(2, "女")
                )
        );
        SpecNameAndValues e2 = new SpecNameAndValues(new SpecName(2, "颜色"),
                Arrays.asList(
                        new SpecValue(3, "黑"),
                        new SpecValue(4, "红"),
                        new SpecValue(5, "白")
                )
        );
        SpecNameAndValues e3 = new SpecNameAndValues(new SpecName(3, "尺寸"),
                Arrays.asList(
                        new SpecValue(6, "大"),
                        new SpecValue(7, "中"),
                        new SpecValue(8, "小")
                )
        );

        List<SpecNameAndValues> candidates = new ArrayList<>();
        candidates.add(e1);
        candidates.add(e2);
        candidates.add(e3);
        // 笛卡尔积
        List<Permutation> permutations = SkuUtil.generateSkuPermutations(candidates);
        List<ItemSku> skuList = SkuUtil.generateSku(candidates);
        for (ItemSku sku : skuList) {
            System.out.println(JSON.toJSONString(sku));
        }
    }

    private static class SkuUtil {
        public static List<ItemSku> generateSku(List<SpecNameAndValues> candidates) {
            List<Permutation> permutations = generateSkuPermutations(candidates);
            // 生成sku列表
            return permutations.stream().map(permutation -> {
                LinkedHashMap<String, String> specNameValue = new LinkedHashMap<>();
                LinkedHashMap<Integer, Integer> specNameIdValueId = new LinkedHashMap<>();
                for (SpecPermutation specPermutation : permutation.specPermutations) {
                    specNameValue.put(specPermutation.specName, specPermutation.specValue);
                    specNameIdValueId.put(specPermutation.specNameId, specPermutation.specValueId);
                }
                List<Integer> specValueIds = permutation.specPermutations.stream()
                        .map(SpecPermutation::getSpecValueId).collect(Collectors.toList());
                ItemSku itemSku = new ItemSku();
                itemSku.setSpecValueIds(specValueIds);
                itemSku.setSpecValueIdPath(StringUtils.join(specValueIds, ","));
                itemSku.setSpecNameValue(specNameValue);
                itemSku.setSpecNameValueJson(JSON.toJSONString(specNameValue));
                itemSku.setSpecNameIdValueId(specNameIdValueId);
                itemSku.setSpecNameIdValueIdJson(JSON.toJSONString(specNameIdValueId));
                return itemSku;
            }).collect(Collectors.toList());
        }

        public static List<Permutation> generateSkuPermutations(List<SpecNameAndValues> candidates) {
            if (candidates == null || candidates.isEmpty()) {
                return new ArrayList<>(0);
            }
            List<Permutation> result = new ArrayList<>();
            recurse(0, new Permutation(), result, candidates);
            return result;
        }

        private static void recurse(int curIdx, Permutation path,
                                    List<Permutation> result,
                                    List<SpecNameAndValues> candidates) {
            if (curIdx == candidates.size()) {
                result.add(path);
                return;
            }
            SpecNameAndValues specNameAndValues = candidates.get(curIdx);
            SpecName specName = specNameAndValues.specName;
            for (SpecValue specValue : specNameAndValues.specValues) {
                SpecPermutation specPermutation = new SpecPermutation(specName.specNameId, specName.specName, specValue.specValueId, specValue.specValue);
                Permutation newPath = new Permutation(path.specPermutations);
                newPath.specPermutations.add(specPermutation);
                recurse(curIdx + 1, newPath, result, candidates);
            }
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class SpecNameAndValues {
        private SpecName specName;
        private List<SpecValue> specValues;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class SpecName {
        private Integer specNameId;
        private String specName;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class SpecValue {
        private Integer specValueId;
        private String specValue;
    }

    @Data
    private static class Permutation {
        private final List<SpecPermutation> specPermutations;

        Permutation() {
            specPermutations = new ArrayList<>();
        }

        Permutation(List<SpecPermutation> another) {
            specPermutations = new ArrayList<>();
            specPermutations.addAll(another);
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class SpecPermutation {
        private Integer specNameId;
        private String specName;
        private Integer specValueId;
        private String specValue;
    }

    @Data
    private static class ItemSku {
        /**
         * 规格id字符串
         * 1,2,3,4,5
         */
        private String specValueIdPath;

        /**
         * 规格id列表
         */
        private List<Integer> specValueIds;

        /**
         * 规格名称和值的json串
         * { "尺码":"38","颜色":"黑色" }
         */
        private String specNameValueJson;

        /**
         * 规格名称和规格值
         * { "尺码":"38", "颜色":"黑色" }
         */
        private LinkedHashMap<String, String> specNameValue;

        /**
         * 规格id和值id的json串
         * { "123":"131", "321":"432" }
         */
        private String specNameIdValueIdJson;

        /**
         * 规格id和值id的json串
         * { 123: 131, 321: 432 }
         */
        private LinkedHashMap<Integer, Integer> specNameIdValueId;
    }

}
