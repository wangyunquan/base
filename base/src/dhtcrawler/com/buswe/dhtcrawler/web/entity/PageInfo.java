package com.buswe.dhtcrawler.web.entity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class PageInfo {
	private List<Page> pageList;
	private int currentPage;
	private int totalpage;
	private int params;
	private StringBuffer uri;
	private HttpServletRequest request;
	private String idOffsetParam = "page";

	public String getIdOffsetParam() {
		return idOffsetParam;
	}

	public void setIdOffsetParam(String idOffsetParam) {
		this.idOffsetParam = idOffsetParam;
	}

	public final void addParam(String paramString1, String paramString2) {
		try {
			if (paramString2 != null) {
				paramString1 = URLEncoder.encode(paramString1, "UTF-8");
				paramString2 = URLEncoder.encode(paramString2, "UTF-8");

				this.uri.append(this.params == 0 ? '?' : '&').append(paramString1).append('=').append(paramString2);

				this.params += 1;
			} else {
				String[] arrayOfString = this.request.getParameterValues(paramString1);

				if (arrayOfString != null) {
					paramString1 = URLEncoder.encode(paramString1, "UTF-8");
					int i = 0;
					for (int j = arrayOfString.length; i < j; i++) {
						paramString2 = URLEncoder.encode(arrayOfString[i], "UTF-8");
						this.uri.append(this.params == 0 ? '?' : '&').append(paramString1).append('=').append(paramString2);

						this.params += 1;
					}
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public List<Page> getPageList() {
		return pageList;
	}

	public void setPageList(List<Page> pageList) {
		this.pageList = pageList;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getTotalpage() {
		return totalpage;
	}

	@SuppressWarnings("unused")
	private PageInfo() {
	}

	public PageInfo(int currentPage, int totalpage, String baseUri, HttpServletRequest request) {
		super();
		uri = new StringBuffer(baseUri);
		this.currentPage = currentPage;
		this.totalpage = totalpage;
		this.request = request;
	}

	public final String getOffsetUrl(int paramInt) {
		int i = this.uri.length();
		this.uri.append(this.params == 0 ? '?' : '&').append(this.idOffsetParam).append('=').append(paramInt);

		String str = this.uri.toString();
		this.uri.setLength(i);
		return str;
	}

	public PageInfo generate() {
		pageList = new ArrayList<Page>();
		Page page;
		int start = currentPage - 5 <= 0 ? 1 : currentPage - 5;
		int end;// = start + 9 > totalpage ? totalpage : start + 9;

		if (start + 9 > totalpage) {
			end = totalpage;
			start = totalpage - 9 >= 1 ? totalpage - 9 : 1;
		} else {
			end = start + 9;
		}
		if (start > 1) {
			page = new Page();
			page.setPageNumber(currentPage - 1);
			page.setDispalyName("&lt;");
			page.setUri(getOffsetUrl(currentPage - 1));
			pageList.add(page);
		}
		for (int i = start; i <= end; i++) {
			page = new Page();
			page.setPageNumber(i);
			page.setDispalyName(i + "");
			page.setUri(getOffsetUrl(i));
			pageList.add(page);
		}
		if (totalpage > end) {
			page = new Page();
			page.setPageNumber(currentPage + 1);
			page.setDispalyName("&gt;");
			page.setUri(getOffsetUrl(currentPage + 1));
			pageList.add(page);
		}
		return this;
	}
}
