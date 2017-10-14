<%@ page language="java" pageEncoding="utf-8" import="java.sql.*"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
request.setCharacterEncoding("utf-8"); 
response.setCharacterEncoding("utf-8"); 
%>
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
String sql="update liuyanban set " +
   				" biaoti ='" + request.getParameter("biaoti") + "' ," + 
   				" neirong ='" + request.getParameter("neirong") + "' ," + 
   				" shijian ='" + request.getParameter("shijian") + "' ," + 
   				" uid ='" + request.getParameter("uid") + "' ," + 
   				" xingming ='" + request.getParameter("xingming") +  "' where id=" + request.getParameter("id");
connDbBean.executeUpdate(sql);
out.print("<script>alert('更新成功!=!');location.href='liuyanban_list.jsp';</script>");
%>
</body>
</html>