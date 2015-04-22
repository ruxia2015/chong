<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>新增报文</title>
<script type="text/javascript" src="js/jquery-1.6.min.js"></script>
<script type="text/javascript" src="artDialog4.1.7/artDialog.js?skin=blue"></script>
<script type="text/javascript" src="artDialog4.1.7/plugins/iframeTools.js"></script>
<script type="text/javascript">


$("document").ready(function(){
	$("#save").click(function(){
		var zTree = $.fn.zTree.getZTreeObj("ztree");
		var nodes = zTree.getCheckedNodes(true);
		if(nodes.length < 1)
		{
			alert("请勾选需要批量修改的报文");
			return;
		}	

	
	var arr = new Array();
	for(var i=0;i<nodes.length;i++)
	{
		if(nodes[i].level == 2)
		{
			var childFileName = nodes[i].name;
			var parentsFileName = nodes[i].getParentNode().name;
			var grandParentsFileName = nodes[i].getParentNode().getParentNode().name;
			arr.push(grandParentsFileName+"/"+parentsFileName+"/"+childFileName);
		}
	}
	
	var url = $("#url").val();
	var uri = $("#uri").val();
	var token = $("#token").val();
	
	
	
	$.ajax
	({
		type :  'post',
		url : '<%=request.getContextPath()%>/BatchEditParam',
		data : 
		{
			"url":url,
			"uri":uri,
			"canDelete":$("#canDelete").val(),
			"token":token,
			"userName":$("#userName").val(),
			"phoneNo":$("#phoneNo").val(),
			"otherKey":$("#otherKey").val(),
			"otherValue":$("#otherValue").val(),
			"removeKey":$("#removeKey").val(),
			"expectEntity":$("#expectEntity").val(),			
			"filePaths[]" : arr			
			
		},
		cache : false,
		dataType: 'json',
		success : function(data)
		{
			art.dialog.tips('批量修改报文成功');
			
		},
		error : function()
		{
			
		}
	});	
	
	
	});
})


	
</script>
</head>
<body>
<jsp:include page="top.jsp"/>
	<jsp:include page="left.jsp"></jsp:include>
	<form id="addForm" action="<%=request.getContextPath()%>/BatchEditParam" method="post" style="float: left;">
		<table style="width: 1000px;">
			<tr>
				<td colspan="2" align="center"><span style="color:red;font-size: 16px;">批量修改部分值（不填不改）</span></td>
			</tr>
			
			<tr id="urlTr">
				<td align="right"><span style="color:red;"></span>url：</td>
				<td><input type="text" name="url" id="url" size="40" value=""/></td>
			</tr>
			<tr id="uriTr">
				<td align="right"><span style="color:red;"></span>uri：</td>
				<td><input type="text" name="uri" id="uri" size="40" value=""/></td>
			</tr>
			<tr id="uriTr">
				<td align="right"><span style="color:red;"></span>是否允许删除：</td>
				<td><select name="canDelete" id="canDelete">
						<option value=""></option>
						<option value="0">否</option>
						<option value="1">是</option>
				</select>
				</td>
			</tr>
			<tr id="uriTr">
				<td align="right"><span style="color:red;"></span>expectEntity(期望结果)：</td>
				<td><input type="text" name="expectEntity" id="expectEntity" size="40" value=""/>（清空填*）</td>
			</tr>
			
			
	<tr>
		<td colspan="2"><hr/></td>
		</tr>
			<tr>
				<td align="right"><span style="color:red;"></span>token：</td>				
				<td><input type="text" name="token" id="token" size="40" value=""/>&nbsp;&nbsp;有该值则更改</td>
			</tr>	
			
				<tr>
				<td align="right"><span style="color:red;"></span>用户（userName）：</td>				
				<td><input type="text" name="userName" id="userName" size="40" value=""/>&nbsp;&nbsp;有该值则更改</td>
			</tr>	
			
				<tr>
				<td align="right"><span style="color:red;"></span>phoneNo：</td>				
				<td><input type="text" name="phoneNo" id="phoneNo" size="40" value=""/>&nbsp;&nbsp;有该值则更改</td>
			</tr>	
			
		<tr>
		<td colspan="2"><hr/><br/>msgtype和版本信息不能修改</td>
		</tr>
			<tr>
				<td align="right"><span style="color:red;"></span>其他message中的Key：</td>				
				<td>  <input type="text" name="otherKey" id="otherKey" size="40" value=""/></td>
			</tr>	
			<tr>
				<td align="right"><span style="color:red;"></span>其他message中的Value：</td>				
				<td>  <input type="text" name="otherValue" id="otherValue" size="40" value=""/></td>
			</tr>	
			<tr>
		<td colspan="2"><br/><hr/><br/></td>
		</tr>
			<tr>
				<td align="right"><span style="color:red;"></span>移除message中的Key：</td>				
				<td>  <input type="text" name="removeKey" id="removeKey" size="40" value="" disabled="disabled"/></td>
			</tr>	
			<tr>
				<td colspan="2" align="center">
					<input type="button" id="save"  value="&nbsp;&nbsp;更&nbsp;&nbsp;&nbsp;新&nbsp;&nbsp;">
					
				<td>
			</tr>
			
			
		</table>
	</form>
</body>
</html>