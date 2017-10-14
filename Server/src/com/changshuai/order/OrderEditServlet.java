package com.changshuai.order;

 
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.changshuai.dao.OrderDao;
import com.changshuai.dao.ShopMgrDao;
import com.changshuai.model.Shop;

import net.sf.json.JSONArray;



@SuppressWarnings("serial")
public class OrderEditServlet extends HttpServlet {

	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}

	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
 
		OrderDao orderDao = new OrderDao();
 
	 	if ( req.getParameter("method") != null &&  req.getParameter("method").equals("save")  ){
	 			
	 			Order order = new Order(); 
	 			ShopMgrDao shopMgrDao = new ShopMgrDao();
	 			order.setShop_id(  req.getParameter("order.shop_id") ) ;
	 			order.setSeat( req.getParameter("order.seat")  ); 
	 			order.setDesc( req.getParameter("order.shop_name") ) ;
	 			order.address =  req.getParameter("order.address") ;
	 			order.price =  req.getParameter("order.price") ;
	 			orderDao.save(order);
	 			String[] shop_name = req.getParameter("order.shop_name").split(",");
	 			for (String name : shop_name) {
					String[] n = name.split("--");
					try {
						shopMgrDao.delete(Integer.toString(shopMgrDao.getByName(n[0])));
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
	 			resp.setCharacterEncoding("utf-8");
				
		    	try {
		    		resp.getWriter().write( "1");
		    	} catch (IOException e) {
		    		e.printStackTrace();
		    	}
	 	}

	}
 
  
	
}
