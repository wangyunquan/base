package com.buswe.dhtcrawler.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.buswe.dhtcrawler.db.luncene.LuceneManager;

public class CreateLuceneServlet extends HttpServlet {
	private static final long serialVersionUID = 5355659034287728426L;
	private static final String CREATEINDEX = "createIndex";
	private static final AtomicBoolean starting = new AtomicBoolean(false);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String action = request.getParameter("action");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()//
				+ path + request.getServletPath();
		System.out.println(basePath);
		if (action == null || action.trim().length() == 0) {
			out.print("创建索引：" + basePath + "?action=" + CREATEINDEX);
		} else if (action.equals(CREATEINDEX)) {// 创建索引
			if (!starting.get()) {
				try {
					starting.set(true);
					out.print("开始创建：--");
					LuceneManager.getInstance().createIndex();
					out.print("创建成功：--");
				} catch (Exception e) {
					out.print("创建失败：请重试--");
					e.printStackTrace();
				}
				starting.set(false);
			}
		}
		out.flush();
		out.close();
	}
}
