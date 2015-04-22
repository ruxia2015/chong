<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>成功</title>
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
	<div style="width: 60%;padding-left: 35%; padding-top: 10%">
		<table width="45%" height="164" border="0" cellpadding="0" cellspacing="0" style="border: 1px solid #CCCCCC">
	      <tr height="10">
	        <td height="12" colspan="2"  style="background-color: #EEF2FB;border-bottom:1px solid #CCCCCC"><h3>&nbsp;系统提示</h3></td>
	      </tr>
	      <tr height="154">
	      	<td style="text-align: right;width:120px; "><img src="<%=request.getContextPath()%>/images/succeed.png" style="vertical-align: middle;"></td>
	        <td height="35" style="color:gray;text-align: left; ">
	        	操作成功!
	        </td>
	      </tr>
	    </table>	
	</div>		    	
</body>
</html>