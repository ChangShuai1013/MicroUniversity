<%@ page contentType="text/html;charset=utf-8" language="java"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>官方二手店管理系统</title>
<LINK href="css/admin.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/js.js"></script>
<%@ include file="commons/taglibs.jsp"%>
</head>
<%
	if (request.getAttribute("s") != null) {
%>
<script type="text/javascript">
	alert('用户名或者密码错误!');
</script>
<%
	}
%>
<BODY onload=document.LogonForm.name.focus();>
	<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%"
		bgColor=#002779 border=0>
		<TR>
			<TD align=middle>
				<div align="center">
					<div
						style="font-family: 宋体; color: #FFFFFF; filter: Glow(Color = #000000, Strength = 2); WIDTH: 100%; FONT-WEIGHT: bold; FONT-SIZE: 19pt; margin-top: 5pt">
						<div align="center" class="STYLE5">
							<br /> 官方二手店管理系统 <br /> <br /> <br />
						</div>
					</div>
					<form id="LogonForm" method="post" action="login.do">

						<TABLE cellSpacing=0 cellPadding=0 width=468 bgColor=#ffffff
							border=0 height=200px>
							<TR>
								<TD align=middle>
									<TABLE cellSpacing=0 cellPadding=0 width=230 border=0>
										<FORM name=form1 action=? method=post>
											<TR height=5>
												<TD width=5></TD>
												<TD width=56></TD>
												<TD></TD>
											</TR>
											<TR height=36>
												<TD></TD>
												<TD>用户名</TD>
												<TD><INPUT
													style="BORDER-RIGHT: #000000 1px solid; BORDER-TOP: #000000 1px solid; BORDER-LEFT: #000000 1px solid; BORDER-BOTTOM: #000000 1px solid"
													maxLength=30 size=24 name='name' dataType="Require"
													msg="不能为空"></TD>
											</TR>
											<TR height=36>
												<TD>&nbsp;</TD>
												<TD>密 码</TD>
												<TD><INPUT
													style="BORDER-RIGHT: #000000 1px solid; BORDER-TOP: #000000 1px solid; BORDER-LEFT: #000000 1px solid; BORDER-BOTTOM: #000000 1px solid"
													type=password name='password' maxLength=30 size=24
													dataType="Require" msg="不能为空"> <input type='hidden'
													name='type' value="1"></TD>
											</TR>
											<TR height=5>
												<TD colSpan=3></TD>
											</TR>
											<TR>
												<TD>&nbsp;</TD>
												<TD>&nbsp;</TD>
												<TD><INPUT type=image height=18 width=70
													src="images/bt_login.gif"></TD>
											</TR>
										</FORM>
									</TABLE>
								</TD>
							</TR>
						</TABLE>
						<script>
							function form_submit(){
									LogonForm.submit(); 		
							}	
							function form_reset(){
								LogonForm.reset();
							}
						</script>
</body>
</html>

