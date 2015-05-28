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

	<div class="easyui-panel" title="添加资源" style="width:1000px">
		<div style="padding:10px 60px 20px 60px">
	    <form id="ff" method="post" action="<%=request.getContextPath()%>/ResourceServlet/m/add.action">
	    <div style="color:red">${message }</div>
	    	<table cellpadding="5">
	    		<tr>
	    			<td>资源类型：</td>
	    			<td>
	    				<select class="easyui-combobox" name="resourceType" panelHeight="auto">
	    					<option value="">==请选择==</option>
	    					<c:forEach items="${resourceTypes }" var="item">
	    						<option value="${item.id }">${item.name}</option>
	    					</c:forEach>
	    				</select>
	    			</td>
	    		</tr>
	    		
	    		<tr>
	    			<td>资源类别：</td>
	    			<td>
	    				<select class="easyui-combobox" name="categoryId" panelHeight="auto">
	    					<option value="">==请选择==</option>
	    					<c:forEach items="${categoryList }" var="item">
	    						<option value="${item.id }">${item.category}</option>
	    					</c:forEach>
	    				</select>
	    			</td>
	    		</tr>
	    		
	    		<tr>
	    			<td>是否覆盖：</td>
	    			<td><input class="easyui-checkbox" type="checkbox" name="override" data-options="required:true,validType:'email'"></input>
	    			（如果域名存在，是否将其覆盖为选中的资源类型？）
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>资源：</td>
	    			<td><input class="easyui-textbox" name="urls" data-options="multiline:true" style="height:160px;width:500px;"></input>
	    			<br/><br/>一行代表一个资源
	    			</td>
	    		</tr>	    		
	    		
	    		
	    	</table>
	    </form>
	    <div style="text-align:center;padding:5px">
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">Submit</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">Clear</a>
	    </div>
	    </div>
	</div>
	<script>
		function submitForm(){
			$('#ff').submit();
		}
		function clearForm(){
			$('#ff').form('clear');
		}
	</script>
</body>


</body>
</html>