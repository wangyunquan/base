package com.buswe.base.utils;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

public class KeywordsUtil {

	
	/**
	 * 
	 * @param keyword 源词汇
	 * @param smart 是否智能分词
	 * @return 分词词组(,拼接)
	 */
	public static String getKeywords(String keyword, boolean smart) {
		StringReader reader = new StringReader(keyword);
		IKSegmenter iks = new IKSegmenter(reader, smart);
		StringBuilder buffer = new StringBuilder();
		try {
			Lexeme lexeme;
			while ((lexeme = iks.next()) != null) {
				buffer.append(lexeme.getLexemeText()).append(',');
			}
		} catch (IOException e) {
		}
		//去除最后一个,
		if (buffer.length() > 0) {
			buffer.setLength(buffer.length() - 1);
		}
		return buffer.toString();
	}
	
	
	public static Map getTextDef(String text) throws IOException {
        Map<String, Integer> wordsFren=new HashMap<String, Integer>();
        IKSegmenter ikSegmenter = new IKSegmenter(new StringReader(text), true);
        Lexeme lexeme;
        while ((lexeme = ikSegmenter.next()) != null) {
            if(lexeme.getLexemeText().length()>1){
                if(wordsFren.containsKey(lexeme.getLexemeText())){
                    wordsFren.put(lexeme.getLexemeText(),wordsFren.get(lexeme.getLexemeText())+1);
                }else {
                    wordsFren.put(lexeme.getLexemeText(),1);
                }
            }
        }
        return wordsFren;
    }
	
	public static void sortSegmentResult(Map<String,Integer> wordsFrenMaps,int topWordsCount){
	       String result="";
        List<Map.Entry<String, Integer>> wordFrenList = new ArrayList<Map.Entry<String, Integer>>(wordsFrenMaps.entrySet());
        Collections.sort(wordFrenList, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> obj1, Map.Entry<String, Integer> obj2) {
                return obj2.getValue() - obj1.getValue();
            }
        });
    
     
        for(int i=0;i<topWordsCount&&i<wordFrenList.size();i++){
            Map.Entry<String,Integer> wordFrenEntry=wordFrenList.get(i);
            result=wordFrenEntry+",";
            if(wordFrenEntry.getValue()>1){
                System.out.println(wordFrenEntry.getKey()+"             的次数为"+wordFrenEntry.getValue());
            }
        }
    }
	
	
	public static void main(String args[]) throws IOException {
		 
        String text = "IKAnalyzer是一个开源的，基于java语言开发的轻量级的中文分词工具包。从2006年12月推出1.0版开始，IKAnalyzer已经推出 了3个大版本。最初，它是以开源项目 Lucene为应用主体的，结合词典分词和文法分析算法的中文分词组件。新版本的IKAnalyzer3.0则发展为 面向Java的公用分词组件，独立于Lucene项目，同时提供了对Lucene的默认优化实现。\n" +
                "\n" +
                "IKAnalyzer3.0特性:\n" +
                "\n" +
                "采用了特有的“正向迭代最细粒度切分算法“，具有60万字/秒的高速处理能力。\n" +
                "\n" +
                "采用了多子处理器分析模式，支持：英文字母（IP地址、Email、URL）、数字（日期，常用中文数量词，罗马数字，科学计数法），中文词汇（姓名、地名处理）等分词处理。\n" +
                "\n" +
                "优化的词典存储，更小的内存占用。支持用户词典扩展定义\n" +
                "\n" +
                "针对Lucene全文检索优化的查询分析器IKQueryParser(作者吐血推荐)；采用歧义分析算法优化查询关键字的搜索排列组合，能极大的提高Lucene检索的命中率。";
        int topWordsCount=3;
        Map<String,Integer> wordsFrenMaps=getTextDef(text);
        sortSegmentResult(wordsFrenMaps,topWordsCount);
 
        System.out.println(getKeywords(text,true));
        
        ;
 
    }
}
