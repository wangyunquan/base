package com.buswe.dhtcrawler.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.buswe.dhtcrawler.AppManager;
import com.buswe.dhtcrawler.Key;
import com.buswe.dhtcrawler.Node;
import com.buswe.dhtcrawler.bittorrentkad.KadNet;

public class StartServiceServlet extends HttpServlet {
	private static final long serialVersionUID = 5355659034287728426L;
	public static final List<KadNet> kadNets = new ArrayList<KadNet>();
	private static final String START = "start", STOP = "stop", STATE = "state";

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
			out.print("启动：" + basePath+"?action=start");
			out.println("<br>");
			out.print("停止：" + basePath+"?action=stop");
			out.println("<br>");
			out.print("查看状态：" + basePath+"?action=state");
		} else if (action.equals(START)) {// 启动
			if (!checkIsStarting()) {
				out.println("开始启动.....");
				start();
				out.println("启动完成");
			} else {
				out.println("已经启动的");
			}
		} else if (action.equals(STOP)) {// 停止
			out.println("开始停止.....");
			stop();
			out.println("停止完成");
		} else if (action.equals(STATE)) {// 查询状态
			out.println(checkIsStarting() ? "运行中..." : "已停止");
		}
		out.flush();
		out.close();
	}

	private boolean checkIsStarting() {
		if (kadNets != null && kadNets.size() > 0) {
			for (KadNet kadNet : kadNets) {
				if (kadNet.isStarting()) {
					return true;
				}
			}
		}
		return false;
	}

	private void start() {
		InetSocketAddress[] BOOTSTRAP_NODES = { //
		new InetSocketAddress("router.bittorrent.com", 6881), //
				new InetSocketAddress("dht.transmissionbt.com", 6881),//
				new InetSocketAddress("router.utorrent.com", 6881), };

		int size = 3;
		try {
			for (int i = 0; i < size; i++) {
				AppManager.init();// 1---
				Key key = AppManager.getKeyFactory().generate();
				Node localNode = new Node(key).setInetAddress(InetAddress.getByName("0.0.0.0")).setPoint(20200 + i);// 这里注意InetAddress.getLocalHost();为空

				KadNet kadNet = new KadNet(null, localNode);
				kadNet.join(BOOTSTRAP_NODES).create();

				kadNets.add(kadNet);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("启动了吗" + checkIsStarting());
	}

	private void stop() {
		if (kadNets != null && kadNets.size() > 0) {
			for (KadNet kadNet : kadNets) {
				if (kadNet.isStarting()) {
					kadNet.shutdown();
				}
			}
			kadNets.removeAll(kadNets);
		}
	}

	@Override
	public void init() throws ServletException {
		super.init();
	}
}
