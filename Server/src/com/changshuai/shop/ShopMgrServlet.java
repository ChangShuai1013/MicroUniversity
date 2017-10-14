package com.changshuai.shop;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.changshuai.dao.ShopMgrDao;

import net.sf.json.JSONArray;

@SuppressWarnings("serial")
public class ShopMgrServlet extends HttpServlet {

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ShopMgrDao shopMgrDao = new ShopMgrDao();
		String type = req.getParameter("type");
		if (req.getParameter("method") != null) {
			if (req.getParameter("method").equals("del")) {
				String id = req.getParameter("id");
				shopMgrDao.delete(id);
			} else if (req.getParameter("method").equals("search")) {
				JSONArray jsonArray = JSONArray.fromObject(shopMgrDao.search(req.getParameter("search_name")));
				resp.setCharacterEncoding("gb2312");
				System.out.println("jsonArray.toString()====" + jsonArray.toString());
				resp.getWriter().write(jsonArray.toString());
				return;
			}
		}
		if (type != null) {
			JSONArray jsonArray = null;
			jsonArray = JSONArray.fromObject(shopMgrDao.getAll());
			resp.setCharacterEncoding("gb2312");
			resp.getWriter().write(jsonArray.toString());
		} else {
			if (req.getParameter("all") != null && !"null".equals(req.getParameter("all"))) {
				req.setAttribute("all", "1");
				req.setAttribute("list", shopMgrDao.getAll());
			} else {
				req.setAttribute("all", "1");
				req.setAttribute("list", shopMgrDao.getAll());
			}
			req.getRequestDispatcher("/shopsList.jsp").forward(req, resp);
		}

	}

}
