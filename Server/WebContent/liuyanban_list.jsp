<%@ page language="java" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ page language="java" import="java.sql.*"%>
<jsp:useBean id="connDbBean" scope="page" class="com.changshuai.db.db" />
<html>
<head>
<base href="<%=basePath%>">
<title>评论记录</title>
<LINK href="inc/style.css" type=text/css rel=stylesheet>
</head>
<body>
	<p>已有评论记录列表：</p>
	<!-- <form name="form1" id="form1" method="post" action="">
		搜索: &nbsp; 标题：<input name="biaoti" type="text" id="biaoti" /> <input
			type="submit" name="Submit" value="查找" />
	</form>
 -->	<table width="100%" border="1" align="center" cellpadding="3"
		cellspacing="1" bordercolor="d3eaef" style="border-collapse: collapse">
		<tr>
			<td width="30" align="center" bgcolor="d3eaef">序号</td>
			<td bgcolor='d3eaef' width="200">标题</td>
			<td bgcolor='d3eaef' width="500">内容</td>
			<td bgcolor='d3eaef' width="100">日期</td>
			<td bgcolor='d3eaef' width="100">用户id</td>
			<!-- <td bgcolor='d3eaef' width="100" style="hidden">用户姓名</td> -->
			<!-- <td width="60" align="center" bgcolor="d3eaef">操作</td>
 -->		</tr>
		<%
			  String sql="";
			  sql="select * from   liuyanban  where 1=1";
					  if(request.getParameter("biaoti")=="" ||request.getParameter("biaoti")==null ){
					  } else {
					  	sql=sql+" and biaoti like '%"+new String(request.getParameter("biaoti").getBytes("8859_1"))+"%'";
					  }
			 sql=sql+" order by id desc";
			 ResultSet RS_result=connDbBean.executeQuery(sql);
			 String id="";								
			 int i=0;
			 while(RS_result.next()){
				 i=i+1;
				 id=RS_result.getString("id");
		%>
		<tr>
			<td width="30" align="center"><%=i %></td>
			<td><%=RS_result.getString("biaoti")%></td>
			<td><%=RS_result.getString("neirong")%></td>
			<td><%=RS_result.getString("shijian")%></td>
			<td><%=RS_result.getString("uid")%></td>
			<%-- <td><%=RS_result.getString("xingming")%></td> --%>
			<%-- <td width="60" align="center"><a
				href="liuyanban_updt.jsp?id=<%=id%>">修改</a> <a
				href="del.jsp?id=<%=id %>&tablename=liuyanban"
				onClick="return confirm('真的要删除？')">删除</a></td>
 --%>		</tr>
		<%
  			}
   		%>
	</table>
	<br> 以上数据共<%=i %>条,
	<a style="cursor: hand" onClick="javascript:window.print();">打印本页</a>
</body>
</html>

