<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>推广网站资源账户管理</title>

<jsp:include page="common.jsp" />
<script type="text/javascript" src="<%=request.getContextPath()%>/userjs/resourceTg.js" ></script>
</head>
<body>
<%-- <jsp:include page="top.jsp"/>
<div style="float:left;width:260px;">
	<jsp:include page="left.jsp"/>
</div> --%>

<input type="hidden" id="tgId" value="${tgId }"/>
<div style="float:left;width:auto;border:0px solid #95B8E7;min-width:800px;">
	<form id="addForm"
		action="<%=request.getContextPath()%>/AccountServlet" method="get"
		style="float: left;">
<table id="dg" class="easyui-datagrid" title="资源管理" style="width:900px;height:700px;">	


		
	</table>


	</form>
	</div>

</body>
</html>