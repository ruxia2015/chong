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

<script type="text/javascript">

function search() {
    var queryParams = $('#dg').datagrid('options').queryParams;
    queryParams.domain = $("#query_domain").val();
    queryParams.type = $("#query_type").combobox("getValue");
    $("#dg").datagrid('reload');
}

function exportData(){
	var domain = $("#query_domain").val();
	var type = $("#query_type").combobox("getValue");
	url = _contextPath+'/ResourceServlet/m/export.action?domain='+domain+"&type="+type;
 	 window.open(url);
}

</script>

</head>
<body>
<jsp:include page="top.jsp"/>
<div style="float:left;width:260px;">
	<jsp:include page="left.jsp"/>
</div>
<div style="float:left;width:auto;border:1px solid #95B8E7;min-width:800px;">
	<form id="addForm"
		action="<%=request.getContextPath()%>/AccountServlet" method="get"
		style="float: left;">
	<div id="tb" style="padding:5px;height:auto">
		<div>
			域名: <input class="easyui-textbox" style="width:80px" id="query_domain" >
			类型: 
			<select class="easyui-combobox" panelHeight="auto" style="width:100px" id="query_type">
				<option value="" >===请选择===</option>
				<c:forEach items="${resourceTypeList}" var="item">
					<option value="${item.id }">${item.name }</option>
				</c:forEach>
			</select>			
			<a href="javascript:search()" class="easyui-linkbutton"    iconCls="icon-search" onclick="">Search</a>
			<a href="javascript:exportData()" class="easyui-linkbutton"    iconCls="icon-save" onclick="">Export</a>
		</div>
	</div>
	
		<table id="dg" class="easyui-datagrid" title="资源管理" style="width:700px;height:750px;">		
	</table>

	</form>
	</div>

</body>
</html>