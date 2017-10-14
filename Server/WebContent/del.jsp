<%@ page language="java" pageEncoding="gb2312"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ page language="java" import="java.sql.*"%>
<jsp:useBean id="connDbBean" scope="page" class="com.changshuai.db.db" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'adminyanzheng.jsp' starting page</title>


</head>

<body>
	<%
		String id = request.getParameter("id");
		String tablename = request.getParameter("tablename");

		String sql = "delete from " + tablename + " where id=" + id + "";

		connDbBean.executeUpdate(sql);
		out.print("<script>alert('É¾³ý³É¹¦!!');location.href='" + request.getHeader("Referer") + "';</script>");
	%>
</body>
</html>

