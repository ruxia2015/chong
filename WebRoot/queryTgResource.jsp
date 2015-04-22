<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>新增报文</title>

<jsp:include page="common.jsp" />
</head>
<body style="">

	<form id="addForm"
		action="<%=request.getContextPath()%>/AccountServlet" method="get"
		style="padding-bottom:40px;">
	<table id="dg" class="easyui-datagrid" title="资源管理" style="width:100%;height:auto;min-height:800px;"
			data-options="
				iconCls: 'icon-edit',
				singleSelect: true,
				toolbar: '#tb',
				url: 'datagrid_data1.json',
				method: 'get',
				onClickRow: onClickRow,
				pagination:true
			">
		<thead>
			<tr>
				<th data-options="field:'类型',width:80">资源ID</th>
				<th data-options="field:'类型',width:80">域名</th>
				<th data-options="field:'类型',width:80">推广网站</th>
				<th data-options="field:'类型',width:80">资源类型</th>
				<th data-options="field:'类型',width:80">是否可以注册</th>
				<th data-options="field:'类型',width:80">资源ID</th>
				<th data-options="field:'type',width:250,editor:'textbox'">账号</th>
				<th data-options="field:'remark',width:150,editor:'textbox'">密码</th>
				<th data-options="field:'remark',width:250,editor:'textbox'">邮箱</th>
			</tr>
		</thead>
		<tr>
			<td>11</td>
			<td>11</td>
			<td>11</td>
			<td><input type="button"></td>
		</tr>

		
	</table>
	
	<div id="tb" style="padding:2px 5px;">
		Date From: <input class="easyui-datebox" style="width:110px">
		To: <input class="easyui-datebox" style="width:110px">
		Language: 
		<select class="easyui-combobox" panelHeight="auto" style="width:100px">
			<option value="java"></option>
			<option value="c">C</option>
			<option value="basic">Basic</option>
			<option value="perl">Perl</option>
			<option value="python">Python</option>
		</select>
		<a href="#" class="easyui-linkbutton" iconCls="icon-search">Search</a>
	</div>
	
<!-- 	<div id="ft" style="height:auto">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">添加</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()">删除</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accept()">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="reject()">取消</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="getChanges()">GetChanges</a>
	</div> -->
	


	</form>

	<script type="text/javascript">
	$(function(){
		var pager = $('#dg').datagrid().datagrid('getPager');	// get the pager of datagrid
		pager.pagination({
			buttons:[{
				iconCls:'icon-search',
				handler:function(){
					alert('search');
				}
			},{
				iconCls:'icon-edit',
				handler:function(){
					alert('add');
				}
			},{
				iconCls:'icon-save',
				handler:function(){
					alert('edit');
				}
			}]
		});			
	})
</script>

	<script type="text/javascript">


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
	</script>
</body>
</html>