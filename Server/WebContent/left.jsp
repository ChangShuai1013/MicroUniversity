<%@ page contentType="text/html;charset=utf-8" language="java"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<LINK href="css/admin.css" type="text/css" rel="stylesheet">
<SCRIPT language=javascript>
	function expand(el) {
		childObj = document.getElementById("child" + el);
		if (childObj.style.display == 'none') {
			childObj.style.display = 'block';
		} else {
			childObj.style.display = 'none';
		}
		return;
	}
</SCRIPT>
</HEAD>
<BODY>
	<TABLE cellSpacing=0 cellPadding=0 width=150 border=0>
		<TR height=22>
			<TD style="PADDING-LEFT: 30px" background=images/menu_bt.jpg><A
				class=menuParent onclick=expand(2) href="javascript:void(0);">商品管理</A></TD>
		</TR>
		<TR height=4>
			<TD></TD>
		</TR>
	</TABLE>
	<TABLE id=child2 style="DISPLAY: none" cellSpacing=0 cellPadding=0
		width=150 border=0>
		<TR height=20>
			<TD align=middle width=30><IMG height=9
				src="images/menu_icon.gif" width=9></TD>
			<TD><A class=menuChild href="shopsEdit.jsp" target=main>新增商品</A>
			</TD>
		</TR>
		<TR height=20>
			<TD align=middle width=30><IMG height=9
				src="images/menu_icon.gif" width=9></TD>
			<TD><A class=menuChild href="shopList.do" target=main>显示所有</A></TD>
		</TR>
	</TABLE>
	<TABLE cellSpacing=0 cellPadding=0 width=150 border=0>
		<TR height=22>
			<TD style="PADDING-LEFT: 30px" background=images/menu_bt.jpg><A
				class=menuParent onclick=expand(4) href="javascript:void(0);">订单管理</A></TD>
		</TR>
		<TR height=4>
			<TD></TD>
		</TR>
	</TABLE>
	<TABLE id=child4 style="DISPLAY: none" cellSpacing=0 cellPadding=0
		width=150 border=0>
		<TR height=20>
			<TD align=middle width=30><IMG height=9
				src="images/menu_icon.gif" width=9></TD>
			<TD><A class=menuChild href="order.do" target=main>订单管理</A></TD>
		</TR>
	</TABLE>
	<TABLE cellSpacing=0 cellPadding=0 width=150 border=0>
		<TR height=22>
			<TD style="PADDING-LEFT: 30px" background=images/menu_bt.jpg><A
				class=menuParent onclick=expand(6) href="javascript:void(0);">评论管理</A></TD>
		</TR>
		<TR height=4>
			<TD></TD>
		</TR>
	</TABLE>
	<TABLE id=child6 style="DISPLAY: none" cellSpacing=0 cellPadding=0
		width=150 border=0>
		<TR height=20>
			<TD align=middle width=30><IMG height=9
				src="images/menu_icon.gif" width=9></TD>
			<TD><A class=menuChild href="liuyanban_list.jsp" target=main>评论管理</A>
			</TD>
		</TR>
	</TABLE>
</BODY>
</HTML>


