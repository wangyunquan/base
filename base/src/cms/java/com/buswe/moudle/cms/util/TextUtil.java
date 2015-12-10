package com.buswe.moudle.cms.util;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class TextUtil {
	/**
	 * 禁止实例化
	 */
	private TextUtil() {
	}
	
	/**
	 * 过滤html标签
	 * @param inputString
	 * @return
	 */
	public static String removeHtmlTagP(String inputString) {  
	    if (inputString == null)  
	        return null;  
	    String htmlStr = inputString; // 含html标签的字符串  
	    String textStr = "";  
	    java.util.regex.Pattern p_script;  
	    java.util.regex.Matcher m_script;  
	    java.util.regex.Pattern p_style;  
	    java.util.regex.Matcher m_style;  
	    java.util.regex.Pattern p_html;  
	    java.util.regex.Matcher m_html;  
	    try {  
	        //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>  
	        String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";   
	        //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>  
	        String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";   
	        String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式  
	        p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);  
	        m_script = p_script.matcher(htmlStr);  
	        htmlStr = m_script.replaceAll(""); // 过滤script标签  
	        p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);  
	        m_style = p_style.matcher(htmlStr);  
	        htmlStr = m_style.replaceAll(""); // 过滤style标签  
	        htmlStr.replace("</p>", "\n");
	        p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);  
	        m_html = p_html.matcher(htmlStr);  
	        htmlStr = m_html.replaceAll(""); // 过滤html标签  
	        textStr = htmlStr;  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }  
	    return textStr;// 返回文本字符串  
	}  
	
	
	/**
	 * 剪切文本。如果进行了剪切，则在文本后加上"..."
	 * 
	 * @param s
	 *            剪切对象。
	 * @param len
	 *            编码小于256的作为一个字符，大于256的作为两个字符。
	 * @return
	 */
	public static String textCut(String s, int len, String append) {
		if (s == null) {
			return null;
		}
		int slen = s.length();
		if (slen <= len) {
			return s;
		}
		// 最大计数（如果全是英文）
		int maxCount = len * 2;
		int count = 0;
		int i = 0;
		for (; count < maxCount && i < slen; i++) {
			if (s.codePointAt(i) < 256) {
				count++;
			} else {
				count += 2;
			}
		}
		if (i < slen) {
			if (count > maxCount) {
				i--;
			}
			if (!StringUtils.isBlank(append)) {
				if (s.codePointAt(i - 1) < 256) {
					i -= 2;
				} else {
					i--;
				}
				return s.substring(0, i) + append;
			} else {
				return s.substring(0, i);
			}
		} else {
			return s;
		}
	}
	
	/**
	 * 文本转html
	 * 
	 * @param txt
	 * @return
	 */
	public static String txt2htm(String txt) {
		if (StringUtils.isBlank(txt)) {
			return txt;
		}
		StringBuilder sb = new StringBuilder((int) (txt.length() * 1.2));
		char c;
		boolean doub = false;
		for (int i = 0; i < txt.length(); i++) {
			c = txt.charAt(i);
			if (c == ' ') {
				if (doub) {
					sb.append(' ');
					doub = false;
				} else {
					sb.append("&nbsp;");
					doub = true;
				}
			} else {
				doub = false;
				switch (c) {
				case '&':
					sb.append("&amp;");
					break;
				case '<':
					sb.append("&lt;");
					break;
				case '>':
					sb.append("&gt;");
					break;
				case '"':
					sb.append("&quot;");
					break;
				case '\n':
					sb.append("<br/>");
					break;
				default:
					sb.append(c);
					break;
				}
			}
		}
		return sb.toString();
	}
	
	public static String htmlCut(String s, int len, String append) {
		String text = removeHtmlTagP(s);
		return textCut(text, len, append);
	}
}
