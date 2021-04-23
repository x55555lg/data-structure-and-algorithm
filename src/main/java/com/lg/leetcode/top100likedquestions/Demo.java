package com.lg.leetcode.top100likedquestions;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Xulg
 * Created in 2021-02-22 9:25
 */
class Demo {

    /*
     * leetcode的top-100-liked-questions系列题目
     * https://leetcode.com/problemset/top-100-liked-questions/
     */

    /*
     * 不会的题
     */

    public static void main(String[] args) {
        List<String> lines = FileUtil.readLines("C:\\Users\\xulg\\Desktop\\budan", "UTF-8");
        List<String> dataList = lines.stream()
                .map(s -> s.substring(s.indexOf("req:")
                        + "req:".length()).trim())
                //.map(s -> JSON.parseObject(s).getString("data"))
                .collect(Collectors.toList());
        for (String data : dataList) {
            String body = HttpRequest.post("http://m.51jujibao.com/api/gm/yd/notifyOrder")
                    .form("req", data).execute().body();
            System.out.println(body);
        }
    }

}
