<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>推广资源管理</title>

<jsp:include page="common.jsp" />
</head>
<body>
<jsp:include page="top.jsp"/>
<div style="float:left;width:260px;">
	<jsp:include page="left.jsp"/>
</div>
 <div style="float:left;width:auto;border:1px solid #95B8E7;min-width:800px;">
	<div class="easyui-tabs" style="height:1050px;min-width:1000px;max-width:1200px;" id="tgTabs"></div>
 </div>
</body>
</html>