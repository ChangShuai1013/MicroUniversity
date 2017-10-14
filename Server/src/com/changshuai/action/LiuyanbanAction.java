package com.changshuai.action;

 
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.changshuai.dao.LiuyanbanDao;
import com.changshuai.model.Liuyanban;

import org.json.JSONObject;
import org.json.JSONException;


@SuppressWarnings("serial")
public class LiuyanbanAction extends HttpServlet {
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String method = req.getParameter("method");
		LiuyanbanDao dao = new LiuyanbanDao();
		if (method.equals("list")) {
			String type = req.getParameter("type");
		    if (type != null && type.equals("json")) {
		    	JSONArray jsonArray  =  JSONArray.fromObject( dao.getAll(req.getParameter("id") , req.getParameter("f") )  );
		    	resp.setCharacterEncoding("gb2312");
		    	try {
		    		resp.getWriter().write(jsonArray.toString());
		    	} catch (IOException e) {
		    		e.printStackTrace();
		    	}
		    	return ;		    	
			} else {
				getAll(req, dao);
				req.getRequestDispatcher("/liuyanbanlist.jsp").forward(req, resp);
			}
		} else if (method.equals("goUpdate") ){
			String id = req.getParameter("id");
			req.setAttribute("map", dao.getObjectById(id) ); 
			req.getRequestDispatcher("/liuyanban_update.jsp").forward(req, resp);	    
		} else if (method.equals("update") ){
			String id = req.getParameter("id");
			Liuyanban liuyanban  = dao.getObjectById(id) ;
			liuyanban.biaoti = req.getParameter("biaoti") ; 
			liuyanban.neirong = req.getParameter("neirong") ; 
			liuyanban.shijian = req.getParameter("shijian") ; 
			liuyanban.uid = req.getParameter("uid") ; 
//			liuyanban.xingming = req.getParameter("xingming") ; 
			dao.update( req.getParameter("id") , liuyanban ) ;  
			getAllForUpdate(req, dao);
			req.getRequestDispatcher("/liuyanbanlist.jsp").forward(req, resp);
		} else if (method.equals("saveJson")) {
			String result = "" ;
			String parameter = req.getParameter("liuyanban");
			try {
				JSONObject jsonObject = new JSONObject(parameter);  
			   	Liuyanban object  = new Liuyanban();  
				object.biaoti = jsonObject.getString("biaoti") ; 
				object.neirong = jsonObject.getString("neirong") ; 
				object.shijian = jsonObject.getString("shijian") ; 
				object.uid = jsonObject.getString("uid") ; 
//				object.xingming = jsonObject.getString("xingming") ; 
			   	int i = dao.save( object ) ;
				if (i > 0) { 
					result = "SUCCESS";
				} else {
					result = "ERROR";
				} 
			} catch (JSONException e) {
				e.printStackTrace();
			}
			resp.getWriter().println(result);
			return ;
		} else if (method.equals("save")) { 
		   	Liuyanban object  = new Liuyanban(); 
			object.biaoti = req.getParameter("biaoti") ; 
			object.neirong = req.getParameter("neirong") ; 
			object.shijian = req.getParameter("shijian") ; 
			object.uid = req.getParameter("uid") ; 
//			object.xingming = req.getParameter("xingming") ; 
		   	dao.save( object ) ;
		   	resp.setCharacterEncoding("utf-8");
	    	try {
	    		resp.getWriter().write( "1");
	    	} catch (IOException e) {
	    		e.printStackTrace();
	    	}
		} else if (method.equals("del")) {
			String id = req.getParameter("id");
			dao.delete(id);
			getAll(req, dao);
			req.getRequestDispatcher("/news.do?method=list").forward(req, resp);
		}
	}

	private void getAllForUpdate(HttpServletRequest req, LiuyanbanDao dao) {
		List list = dao.getAll( req.getParameter("id")  , req.getParameter("t")  );
		req.setAttribute("list", list); 
	}
	
	private void getAll(HttpServletRequest req, LiuyanbanDao dao) {
		List list = dao.getAll( req.getParameter("id")  , req.getParameter("t")  );
		req.setAttribute("list", list); 
	}
  
	
}
