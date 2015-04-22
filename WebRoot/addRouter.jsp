<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>建立路由器连接</title>
<script type="text/javascript" src="js/jquery-1.6.min.js"></script>
<script type="text/javascript">
	$("document").ready(function(){
		$("#addConnection").click(function()
		{
			if('' == $("input[name='ip']").val() || null == $("input[name='ip']").val())
			{
				alert("ip不能不空");
				$("input[name='ip']").select();
				return;
			}
			if('' == $("input[name='point']").val() || null == $("input[name='point']").val())
			{
				alert("point不能不空");
				$("input[name='point']").select();
				return;
			}
			if('' == $("input[name='userId']").val() || null == $("input[name='userId']").val())
			{
				alert("userId不能不空");
				$("input[name='userId']").select();
				return;
			}
			if('' == $("input[name='devSn']").val() || null == $("input[name='devSn']").val())
			{
				alert("设备不能不空");
				$("input[name='devSn']").select();
				return;
			}
			if('' == $("input[name='mac']").val() || null == $("input[name='mac']").val())
			{
				alert("MAC地址不能不空");
				$("input[name='mac']").select();
				return;
			}
			$("#addForm").attr("action","<%=request.getContextPath()%>/ClientOneNetServlet");
			$("#addForm").submit();
		});
		
		$("#sendMsg").click(function()
		{
			if('' == $("input[name='url']").val() || null == $("input[name='url']").val())
			{
				alert("url不能不空");
				$("input[name='url']").select();
				return;
			}
			if('' == $("input[name='uri']").val() || null == $("input[name='uri']").val())
			{
				alert("uri不能不空");
				$("input[name='uri']").select();
				return;
			}
			if('' == $("#message").val() || null == $("#message").val())
			{
				alert("发送报文不能不空");
				$("#message").select();
				return;
			}
			$("#addForm").attr("action","<%=request.getContextPath()%>/AutoTestServlet");
			$("#addForm").submit();
		});
		
		$("#stopConnection").click(function()
		{
			$("#addForm").attr("action","<%=request.getContextPath()%>/StopClientOneNetServlet");
			$("#addForm").submit();	
		});
	});
</script>
<style type="text/css">
body {
	margin:0px;
	padding:0px;
	font-size: 12px;
}
</style>
</head>
<body>
	<jsp:include page="top.jsp"/>
	<jsp:include page="left.jsp"></jsp:include>
	<form id="addForm" action="<%=request.getContextPath()%>/AutoTestServlet" method="post">
		<table>
			<tr>
				<td colspan="2" align="center"><span style="color:red;font-size: 16px;">连接路由器</span></td>
			</tr>
			<tr>
				<td colspan="2" align="center" width="200px;">
					<c:if test="${errorCode == '1'}">
						<span style="color:red;">建立连接成功</span>
					</c:if>
					<c:if test="${errorCode == '0'}">
						<span style="color:red;">建立连接失败，失败原因：${errorMessage}</span>
					</c:if>
				</td>
			</tr>
			<tr>
				<td align="right"><span style="color:red;">*</span>ip：</td>
				<td><input type="text" name="ip" size="40" value="${ip}"/></td>
			</tr>
			<tr>
				<td align="right"><span style="color:red;">*</span>端口号：</td>
				<td><input type="text" name="point" size="40" value="${point}"/></td>
			</tr>
			<tr>
				<td align="right"><span style="color:red;">*</span>userId：</td>
				<td><input type="text" name="userId" size="40" value="${userId}"/></td>
			</tr>
			<tr>
				<td align="right"><span style="color:red;">*</span>设备sn：</td>
				<td><input type="text" name="devSn" size="40" value="${devSn}"/></td>
			</tr>
			<tr>
				<td align="right"><span style="color:red;">*</span>MAC地址：</td>
				<td><input type="text" name="mac" size="40" value="${mac}"/></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<c:choose>
						<c:when test="${errorCode == '1'}">
							<input type="button" id="stopConnection" value="断开连接">
						</c:when>
						<c:otherwise>
							<input type="button" id="addConnection" value="建立连接">
						</c:otherwise>
					</c:choose>
				<td>
			</tr>
		</table>
	</form>
</body>
</html>