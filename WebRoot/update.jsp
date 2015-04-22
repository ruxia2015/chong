<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>更新报文</title>
<script type="text/javascript" src="js/jquery-1.6.min.js"></script>
<script type="text/javascript"
	src="artDialog4.1.7/artDialog.js?skin=blue"></script>
<script type="text/javascript"
	src="artDialog4.1.7/plugins/iframeTools.js"></script>
<jsp:include page="common.jsp" />
<script type="text/javascript">
	var time;
	$("document").ready(function(){
		initParentFileName();
		if('${requestScope.messageType}' == '0')
		{
			$("#urlTr").css("display","");
			$("#uriTr").css("display","");

			$("#userTr").css("display","");
		}
		
		
		
		if('' != $("#expectEntity").val() && '' != $("#realEntity").html())
		{
			var temp = "";
			var realEntity = $("#realEntity").html().split(",");
			var expectEntity = $("#expectEntity").val().split(",");
			if(realEntity.length == expectEntity.length)
			{
				for(var i=0;i<realEntity.length;i++)
				{
					for(var j=i;j<expectEntity.length;j++)
					{
						if(realEntity[i] == expectEntity[j])
						{
							if(j == expectEntity.length-1)
							{
								temp += "<span style='color:green'>"+realEntity[j]+"</span>";
							}
							else
							{
								temp += "<span style='color:green'>"+realEntity[j]+",</span>";
							}
						}	
						else
						{
							if(realEntity[i].indexOf("msgSeq") != -1 || realEntity[i].indexOf("time") != -1)
							{
								temp += "<span style='color:green'>"+realEntity[j]+",</span>";
							}
							else
							{
								if(j == expectEntity.length-1)
								{
									temp += "<span style='color:red'>"+realEntity[j]+"</span>";
								}
								else
								{
									temp += "<span style='color:red'>"+realEntity[j]+",</span>";
								}	
							}
						}	
						break;
					}	
				}	
				$("#realEntity").html('');
				$("#realEntity").html(temp);
			}	
			else
			{
				$("#realEntity").html('');
				$("#realEntity").html("<span style='color:red'>"+'${requestScope.entity}'+"</span>");
			}
		}	
		$("#save").click(function()
		{
			if('${requestScope.messageType}' == '0')
			{
				if(!checkApiParam()){
					return;
				}
			}
			
			if('' == $("#newGrandParentsFileName").val() || null == $("#newGrandParentsFileName").val())
			{
				alert("父模块不能为空");
				return;
			}
			
			if('' == $("#newParentsFileName").val() || null == $("#newParentsFileName").val())
			{
				alert("子模块不能空");
				return;
			}
			$("#addForm").attr("action","<%=request.getContextPath()%>/UpdateFileServlet");
			$("#addForm").submit();
		});
		
		$("#sendMsg").click(function()
		{
			if('${requestScope.messageType}' == '0')
			{
				if(!checkApiParam()){
					return;
				}	
			}
			
			if($("input[name='messageType']").val() == '0')
			{
				$("#addForm").attr("action","<%=request.getContextPath()%>/AutoTestServlet");
				$("#addForm").submit();
			}	
			
		});
		
		$("#saveAs").click(function(){
			if('${requestScope.messageType}' == '0')
			{
				if(!checkApiParam()){
					return;
				}		
			
			}
			
			var fileName = prompt("请输入文件名称", "异常测试");
			
			if(fileName==null){
				return;
			}
			
			if(fileName=='' )
			{
				alert("保存文件名不能不空");
				$("input[name='fileName']").select();
				return;
			}
			
			$("#fileName").val(fileName);
			$("#parentFileModule").val($("#newGrandParentsFileName").val());
			$("#childFileModule").val($("#newParentsFileName").val());
			
			$("#addForm").attr("action","<%=request.getContextPath()%>/AddTestServlet");
			$("#addForm").submit();
			
		});
			
		
		
		
		
		$("#delete").click(function()
		{
			if(confirm("确定要删除该条报文吗？"))
			{
				$("#addForm").attr("target","_top");
				$("#addForm").attr("action","<%=request.getContextPath()%>/DeleteFileServlet");
				$("#addForm").submit();
			}
		});
		
		$("#newGrandParentsFileName").change(function()
		{
			if(null != $("#newGrandParentsFileName").val() && '' != $("#newGrandParentsFileName").val())
			{
				$.ajax({
					type :  'post',
					url : '<%=request.getContextPath()%>/LoadDirectoryServlet',
					data : 
					{
						parentFileName : $("#newGrandParentsFileName").val()
					},
					cache : false,
					dataType: 'json',
					success : function(data)
					{
						var newParentsFileName = data.childFileName;
						if(null != newParentsFileName && newParentsFileName.length>0)
						{
							$("#newParentsFileName option").remove();
							$("#newParentsFileName").append("<option value=''>请选择...</option>");
							for(var i=0;i<newParentsFileName.length;i++)
							{
								$("#newParentsFileName").append("<option value='"+newParentsFileName[i]+"'>"+newParentsFileName[i]+"</option>");
							}
						}
					},
					error : function()
					{
						
					}
				});
			}	
			else
			{
				$("#newParentsFileName option").remove();
				$("#newParentsFileName").append("<option value=''>请选择...</option>");
			}
		});
		
		if('${requestScope.updateSuccess}' == '1')
		{
			art.dialog.tips('修改报文成功');
		}
	});
	
	function queryMessage()
	{
		$.ajax
		({
			type :  'post',
			url : '<%=request.getContextPath()%>/QueryResponseServlet',
					data : {

					},
					cache : false,
					dataType : 'json',
					success : function(data) {
						if (data != '0') {
							$("#realEntity").html(data);
							if ('' != $("#expectEntity").val()) {
								var temp = "";
								var realEntity = $("#realEntity").html().split(
										",");
								var expectEntity = $("#expectEntity").val()
										.split(",");
								if (realEntity.length == expectEntity.length) {
									for (var i = 0; i < realEntity.length; i++) {
										for (var j = i; j < expectEntity.length; j++) {
											if (realEntity[i] == expectEntity[j]) {
												if (j == expectEntity.length - 1) {
													temp += "<span style='color:green'>"
															+ realEntity[j]
															+ "</span>";
												} else {
													temp += "<span style='color:green'>"
															+ realEntity[j]
															+ ",</span>";
												}
											} else {
												if (realEntity[i]
														.indexOf("msgSeq") != -1
														|| realEntity[i]
																.indexOf("time") != -1) {
													temp += "<span style='color:green'>"
															+ realEntity[j]
															+ ",</span>";
												} else {
													if (j == expectEntity.length - 1) {
														temp += "<span style='color:red'>"
																+ realEntity[j]
																+ "</span>";
													} else {
														temp += "<span style='color:red'>"
																+ realEntity[j]
																+ ",</span>";
													}
												}
											}
											break;
										}
									}
									$("#realEntity").html('');
									$("#realEntity").html(temp);
								} else {
									$("#realEntity").html('');
									$("#realEntity").html(
											"<span style='color:red'>" + data
													+ "</span>");
								}
							}
						} else {
							clearInterval(time);
						}
					},
					error : function() {

					}
				});
	}
	
	//加载父目录
	function initParentFileName()
	{
		$.ajax({
			type :  'post',
			url : '<%=request.getContextPath()%>/LoadDirectoryServlet',
			data : 
			{
			},
			cache : false,
			dataType: 'json',
			success : function(data)
			{
				var parentFileName = data.parentFileName;
				if(null != parentFileName && parentFileName.length>0)
				{
					for(var i=0;i<parentFileName.length;i++)
					{
						if('${requestScope.grandParentsFileName}' == parentFileName[i])
						{
							$("#newGrandParentsFileName").append("<option value='"+parentFileName[i]+"' selected='selected'>"+parentFileName[i]+"</option>");
						}	
						else
						{
							$("#newGrandParentsFileName").append("<option value='"+parentFileName[i]+"'>"+parentFileName[i]+"</option>");
						}	
					}	
				}	
			},
			error : function()
			{
				
			}
		});
	}
	
	//加载子目录
	if('${requestScope.grandParentsFileName}' != null && '${requestScope.grandParentsFileName}' != '')
	{
		$.ajax({
			type :  'post',
			url : '<%=request.getContextPath()%>/LoadDirectoryServlet',
					data : {
						parentFileName : '${requestScope.grandParentsFileName}'
					},
					cache : false,
					dataType : 'json',
					success : function(data) {
						var childFileName = data.childFileName;
						if (null != childFileName && childFileName.length > 0) {
							$("#newParentsFileName option").remove();
							$("#newParentsFileName").append(
									"<option value=''>请选择...</option>");
							for (var i = 0; i < childFileName.length; i++) {
								if ('${requestScope.parentsFileName}' == childFileName[i]) {
									$("#newParentsFileName").append(
											"<option value='"+childFileName[i]+"' selected='selected'>"
													+ childFileName[i]
													+ "</option>");
								} else {
									$("#newParentsFileName").append(
											"<option value='"+childFileName[i]+"'>"
													+ childFileName[i]
													+ "</option>");
								}
							}
						}
					},
					error : function() {

					}
				});
	} else {
		$("#newParentsFileName option").remove();
		$("#newParentsFileName").append("<option value=''>请选择...</option>");
	}
</script>


<style type="text/css">
body {
	margin: 0px;
	padding: 0px;
	font-size: 12px;
	padding: 0px;
}
</style>
</head>
<body>
	<jsp:include page="top.jsp" />
	<jsp:include page="left.jsp"></jsp:include>
	<form id="addForm"
		action="<%=request.getContextPath()%>/AutoTestServlet" method="post">
		<input type="hidden" name="fileName" id="fileName" /> <input
			type="hidden" name="parentFileModule" id="parentFileModule" /> <input
			type="hidden" name="childFileModule" id="childFileModule" /> <input
			type="hidden" name="childFileName"
			value="${requestScope.childFileName}" /> <input type="hidden"
			name="parentsFileName" value="${requestScope.parentsFileName}" /> <input
			type="hidden" name="grandParentsFileName"
			value="${requestScope.grandParentsFileName}" /> <input type="hidden"
			name="messageType" value="${requestScope.messageType}">
		<table style="float: left;" style="width: 1200px;">
			<tr>
				<td colspan="2" align="center"><span
					style="color: red; font-size: 16px;">修改测试用例</span>--<span style="color: green;font-size: 16px;">${childFileName }</span></td>
			</tr>
			<tr id="urlTr" style="display: none;">
				<td align="right"><span style="color: red;">*</span>url：</td>
				<td><input type="text" name="url" size="40"
					value="${requestScope.url}" /></td>
			</tr>
			<tr id="uriTr" style="display: none;">
				<td align="right"><span style="color: red;">*</span>uri：</td>
				<td><input type="text" name="uri" size="40"
					value="${requestScope.uri}" /></td>
			</tr>
			<tr id="userTr" style="display: none;">
				<td align="right"><span style="color: red;">*</span>是否允许删除：</td>
				<td><select name="canDelete" id="canDelete">
						<option value="0">否</option>
						<option value="1" <c:if test="${canDelete==1}">selected</c:if>>是</option>
				</select></td>
			</tr>

			<tr>
				<td align="right"><span style="color: red;">*</span>发送报文：</td>
				<td><textarea name="message" id="message" cols="130" rows="5" title="可以使用$date$、 $time$ 插入动态的时间">${requestScope.message}</textarea></td>
			</tr>
			<tr>
				<td align="right"><span style="color: red;"></span>报文备注信息：</td>
				<td><textarea name="remark" id="remark" cols="130" rows="5">${requestScope.remark}</textarea></td>
			</tr>
			<tr>
				<td align="right"><span style="color: red;">*</span>保存模块：</td>
				<td>父模块：<select id="newGrandParentsFileName"
					name="newGrandParentsFileName">
						<option value="">请选择...</option>
				</select> 子模块：<select id="newParentsFileName" name="newParentsFileName">
						<option value="">请选择...</option>
				</select>
				</td>
			</tr>
			<tr>
				<td align="right">预期返回报文：</td>
				<td><textarea name="expectEntity" id="expectEntity" cols="130"
						rows="5">${requestScope.expectEntity}</textarea></td>
			</tr>
			<tr>
				<td align="right" style="width: 100px;">实际返回报文：</td>
				<td style="width: 900px;"><div id="realEntity"
						style="width: 100%; height: 200px; line-height:22px; padding:5px;  border: 1px solid rgb(169, 169, 169); word-break: break-all; overflow-y: scroll;">${requestScope.entity}</div></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="button" id="save"
					value="&nbsp;&nbsp;修&nbsp;&nbsp;&nbsp;改&nbsp;&nbsp;"> <input
					type="button" id="sendMsg" value="发送报文" /> <input type="button"
					id="saveAs" value="另存为" /> <!-- <input type="button" id="delete" value="&nbsp;&nbsp;删&nbsp;&nbsp;&nbsp;除&nbsp;&nbsp;"/> -->
				<td>
			</tr>
		</table>
	</form>
</body>
</html>