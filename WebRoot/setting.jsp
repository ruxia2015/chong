<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>资源管理</title>

<jsp:include page="common.jsp" />
<script type="text/javascript" src="<%=request.getContextPath()%>/userjs/resource.js"></script>
</head>
<body>
<jsp:include page="top.jsp"/>
<div style="float:left;width:260px;">
	<jsp:include page="left.jsp"/>
</div>
<body>

	<div class="easyui-panel" title="基本操作" style="width:1000px">
		
		<table >
	    		
	    		<tr height="42px">
	    			<td>备份数据库：</td>
	    			<td>
	    				<input type="button" value="开始执行">
	    			</td>
	    		</tr>
	    		<tr height="42px">
	    			<td>恢复数据库：</td>
	    			<td>
	    				<input type="button" value="开始执行">
	    			</td>
	    		</tr>
	    		
	    		<tr height="42px">
	    			<td>查询资源PR：</td>
	    			<td>
	    				<input type="button" value="开始执行">
	    			</td>
	    		</tr>
	    		
	    		</table>
	
	</div>

</body>


</body>
</html>