package org.konkakjb.text;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Text3 {
	// public static void main(String[] args) {
	//
	// for (int i = 0; i <= 65535; i++) {
	// System.out.print((char) i + "  ");
	// if (i % 10 == 0)
	// System.out.println();
	// }
	// }
	public static void main(String[] args) {
		// int a = (int) (4 * Math.pow(16, 3) + 14 * Math.pow(16, 2)); // 汉字ASCII码值
		// int b = (int) (9 * Math.pow(16, 3) + 15 * Math.pow(16, 2) + 10 * Math.pow(16, 1)) + 5; // 汉字ASCII码值
		// int j = 0;
		// for (int i = a; i <= b; i++) {
		// j++;
		// System.out.print((char) i + "\t"); // ASCII码转换字符（汉字）
		// if (j % 10 == 0) {
		// System.out.println();
		// j = 0;
		// }
		// }
		// // new StringBuilder().append(c)
		// System.out.println(a);
		// System.out.println(b);

		try {
			text();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Document doc = Jsoup.connect("http://www.jb51.net")
	// .data("query", "Java")
	// .userAgent("Mozilla")
	// .cookie("auth", "token")
	// .timeout(3000)
	// .post();.header("referer", "www.baidu.com")
	public static void text() throws IOException {
		// http://www.btcherry.com/search?keyword=%E9%B8%AD%E7%8E%8B
//		String baseurl = "http://www.btcherry.com/search?keyword=%E9%B8%AD%E7%8E%8B&p=%1$d";
		String baseurl = "http://www.btcherry.com/search?p=%1$d&keyword=%2$s";
		for (int i = 1; i <= 100; i++) {
			String url = String.format(baseurl, i,"22");
//			String url = String.format(baseurl, Integer.valueOf(i));
			Document doc = Jsoup.connect(url).userAgent("Mozilla").timeout(10000).get();

			Elements links = doc.getElementsByAttribute("data-hash");
			for (Element element1 : links) {
				System.out.println(element1.attr("data-hash"));
			}
			// String.format(url, 1);
		}
	}
}
