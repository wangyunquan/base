package com.buswe.base.utils;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class LuceneUtils {
	
public static final 	Analyzer analyzer = new IKAnalyzer();

public static String toHighlighter(Query query, String fildName,String value) {
	try {
		 SimpleHTMLFormatter simpleHtmlFormatter = new SimpleHTMLFormatter("<font color='red'>", "</font>");
		  //高亮对象   
			Highlighter highlighter = new Highlighter(simpleHtmlFormatter,new QueryScorer(query));   
			String titleHighLight = highlighter.getBestFragment(analyzer,fildName,value);
//			
//		 TokenStream tokenStream1 = new IKAnalyzer().tokenStream(NAME_FIELD, new StringReader(doc.get(NAME_FIELD)));
//		 String highlighterStr = highlighter.getBestFragment(tokenStream1, doc.get(field));
		 return titleHighLight == null ? value: titleHighLight;
	} catch (Exception e) {
		 e.printStackTrace();
	}
	return "";
}
}
