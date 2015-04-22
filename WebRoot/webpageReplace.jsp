<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>新增报文</title>
<script type="text/javascript" src="js/jquery-1.6.min.js"></script>
<script type="text/javascript"
	src="artDialog4.1.7/artDialog.js?skin=blue"></script>
<script type="text/javascript"
	src="artDialog4.1.7/plugins/iframeTools.js"></script>
<jsp:include page="common.jsp" />
</head>
<body>
	<jsp:include page="top.jsp" />
	<jsp:include page="left.jsp" />
	<form id="addForm"
		action="<%=request.getContextPath()%>/AccountServlet" method="get"
		style="float: left;">
		<table style="width: 1100px;">
			<tr>
				<td colspan="2" align="center"><span
					style="color: red; font-size: 16px;">新增账户</span></td>
			</tr>
			<tr>
				<td align="right"><span style="color: red;">*</span>用户名：</td>
				<td><input name="userName"  id="userName"/></td>
			</tr>

			<tr>
				<td align="right"><span style="color: red;">*</span>密码：</td>
				<td><input name="password" /></td>
			</tr>
			<tr>
				<td align="right"><span style="color: red;">*</span>邮箱：</td>
				<td><input name="email" /></td>
			</tr>


			<tr>
				<td colspan="2" align="center"> <input
					type="submit" id="sendMsg" value="添加账户" />
				<td>
			</tr>
		</table>
	</form>
</body>
</html>