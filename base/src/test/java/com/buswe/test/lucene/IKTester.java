package com.buswe.test.lucene;


import java.util.List;

import com.buswe.moudle.cms.lucene.SearchHelper;

import junit.framework.Assert;

/**
 * 测试 IK 分词器
 * User: Winter Lau
 * Date: 13-1-10
 * Time: 上午11:48
 */
public class IKTester {

    @org.junit.Test
    public void test_highlight() throws Exception {
        String text = "SQL server 是最好的 数据库 应用服务器";
        Assert.assertEquals("<span class=\"highlight\">SQL</span> <span class=\"highlight\">server</span> 是最好的 数据库 应用服务器", SearchHelper.highlight(text, "sql server"));

    }

    @Test
    public void test_split() throws Exception {
        String text = "android 刷机";
        long ct = System.currentTimeMillis();
        List<String> stopWords = SearchHelper.splitKeywords(text);
        Assert.assertEquals("android", stopWords.get(0));
        Assert.assertEquals("刷机", stopWords.get(1));
        Assert.assertTrue((System.currentTimeMillis() - ct) < 1200);
    }


}
