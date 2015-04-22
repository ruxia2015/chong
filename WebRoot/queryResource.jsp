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
<div style="float:left;width:auto;border:1px solid #95B8E7;min-width:800px;">
	<form id="addForm"
		action="<%=request.getContextPath()%>/AccountServlet" method="get"
		style="float: left;">
<table id="dg" class="easyui-datagrid" title="资源管理" style="width:700px;height:1200px;"
>
		<thead>
			<tr>
				<th data-options="field:'id',width:80">ID</th>
				<th data-options="field:'domain',width:250,editor:'textbox'">域名</th>
				<th data-options="field:'url',width:250,editor:'textbox'">地址</th>
				<th data-options="field:'type',width:80">类型</th>
				<th data-options="field:'accessState',width:80">访问状态</th>
				<th data-options="field:'registerState',width:80">注册状态</th>
				<th data-options="field:'otherState',width:80">其他的状态</th>
				<th data-options="field:'remark',width:80">备注信息</th>
			</tr>
		</thead>
				<c:forEach items="${resourceList}" var ="item">
				<tr role="row">
					<td>${item.id }</td>
					<td>${item.domain }</td>
					<td><a href="${item.url }" title="${item.url }" target="_blank"><div style="width:250px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;">${item.url }</div></a></td>
					<td>${item.type }</td>
					<td>
					<c:choose>
						<c:when test="${ item.accessState eq '1'}">可以访问</c:when>
						<c:when test="${ item.accessState eq '2'}">不可以访问</c:when>
					<c:otherwise>等待验证</c:otherwise>
					</c:choose>
					</td>
					<td>
					<c:choose>
						<c:when test="${ item.registerState eq '1'}">可以注册</c:when>
						<c:when test="${ item.registerState eq '2'}">不可以注册</c:when>
					<c:otherwise>等待验证</c:otherwise>
					</c:choose>
					</td>
					<td>${item.otherState }</td>
					<td>${item.remark }</td>
				</tr>				
				</c:forEach>

		
	</table>

	<!-- <div id="tb" style="height:auto">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">添加</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()">删除</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accept()">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="reject()">取消</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="getChanges()">GetChanges</a>
	</div> -->
	</form>
	</div>
	<!-- <script type="text/javascript">
		var editIndex = undefined;
		
		function loadData(){
			
		}
		
		
		
		function endEditing(){
			if (editIndex == undefined){return true}
			if ($('#dg').datagrid('validateRow', editIndex)){
				//var ed = $('#dg').datagrid('getEditor', {index:editIndex,field:'productid'});
				//var productname = $(ed.target).combobox('getText');
			//	$('#dg').datagrid('getRows')[editIndex]['productname'] = productname;
				$('#dg').datagrid('endEdit', editIndex);
				editIndex = undefined;
				return true;
			} else {
				return false;
			}
		}
		function onClickRow(index){
			if (editIndex != index){
				if (endEditing()){
					$('#dg').datagrid('selectRow', index)
							.datagrid('beginEdit', index);
					editIndex = index;
				} else {
					$('#dg').datagrid('selectRow', editIndex);
				}
			}
		}
		function append(){
			if (endEditing()){
				$('#dg').datagrid('appendRow',{status:'P'});
				editIndex = $('#dg').datagrid('getRows').length-1;
				$('#dg').datagrid('selectRow', editIndex)
						.datagrid('beginEdit', editIndex);
			}
		}
		function removeit(){
			if (editIndex == undefined){return}
			$('#dg').datagrid('cancelEdit', editIndex)
					.datagrid('deleteRow', editIndex);
			editIndex = undefined;
		}
		function accept(){
			if (endEditing()){
				$('#dg').datagrid('acceptChanges');
			}
		}
		function reject(){
			$('#dg').datagrid('rejectChanges');
			editIndex = undefined;
		}
		function getChanges(){
			var rows = $('#dg').datagrid('getChanges');
			alert(rows.length+' rows are changed!');
		}
	</script> -->
</body>
</html>