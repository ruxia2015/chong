<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>新增报文</title>




 <script type="text/javascript" src="js/jquery-1.6.min.js"></script>
<link rel="stylesheet" href="tablesort/css/theme.blue.min.css">		

<link rel="stylesheet" type="text/css" href="css/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="css/themes/icon.css">

<script type="text/javascript" src="tablesort/js/jquery.tablesorter.js"></script>
<script type="text/javascript"
	src="tablesort/js/jquery.tablesorter.widgets.js"></script>	 
	
	<script type="text/javascript" src="js/jquery.easyui.min.js"></script>


<script type="text/javascript">
/* 	$(function() {
		$("#myTable").tablesorter();

		
	}); */
</script>

</head>
<body>
<h2>Basic Tabs</h2>
	<p>Click tab strip to swap tab panel content.</p>

		<div style="margin:20px 0 10px 0;"></div>
	<div class="easyui-tabs" style="width:700px;height:250px">
		<div title="About" style="padding:10px">
			<p style="font-size:14px">jQuery EasyUI framework helps you build your web pages easily.</p>
			<ul>
				<li>easyui is a collection of user-interface plugin based on jQuery.</li>
				<li>easyui provides essential functionality for building modem, interactive, javascript applications.</li>
				<li>using easyui you don't need to write many javascript code, you usually defines user-interface by writing some HTML markup.</li>
				<li>complete framework for HTML5 web page.</li>
				<li>easyui save your time and scales while developing your products.</li>
				<li>easyui is very easy but powerful.</li>
			</ul>
		</div>
		<div title="My Documents" style="padding:10px">
			<ul class="easyui-tree" data-options="url:'tree_data1.json',method:'get',animate:true"></ul>
		</div>
		<div title="Help" data-options="iconCls:'icon-help',closable:true" style="padding:10px">
			This is the help content.
		</div>
	</div>



	<%-- 	<jsp:include page="top.jsp" />
	<jsp:include page="left.jsp" /> --%>
	<form id="addForm"
		action="<%=request.getContextPath()%>/AccountServlet" method="get"
		style="float: left;">

		<table class="tablesorter tablesorter-blue" role="grid" id="myTable">
			<thead>
				<tr role="row" class="tablesorter-headerRow">
					<th data-column="0"
						class="tablesorter-header tablesorter-headerAsc" tabindex="0"
						scope="col" role="columnheader" aria-disabled="false"
						unselectable="on" aria-sort="ascending"
						aria-label="First Name: Ascending sort applied, activate to apply a descending sort"
						style="-webkit-user-select: none;"><div
							class="tablesorter-header-inner">First Name</div></th>
					<th data-column="1"
						class="tablesorter-header tablesorter-headerUnSorted" tabindex="0"
						scope="col" role="columnheader" aria-disabled="false"
						unselectable="on" aria-sort="none"
						aria-label="Last Name: No sort applied, activate to apply an ascending sort"
						style="-webkit-user-select: none;"><div
							class="tablesorter-header-inner">Last Name</div></th>
					<th data-column="2"
						class="tablesorter-header tablesorter-headerUnSorted" tabindex="0"
						scope="col" role="columnheader" aria-disabled="false"
						unselectable="on" aria-sort="none"
						aria-label="Age: No sort applied, activate to apply an ascending sort"
						style="-webkit-user-select: none;"><div
							class="tablesorter-header-inner">Age</div></th>
					<th data-column="3"
						class="tablesorter-header tablesorter-headerDesc" tabindex="0"
						scope="col" role="columnheader" aria-disabled="false"
						unselectable="on" aria-sort="descending"
						aria-label="Total: Descending sort applied, activate to apply an ascending sort"
						style="-webkit-user-select: none;"><div
							class="tablesorter-header-inner">Total</div></th>
					<th data-column="4"
						class="tablesorter-header tablesorter-headerUnSorted" tabindex="0"
						scope="col" role="columnheader" aria-disabled="false"
						unselectable="on" aria-sort="none"
						aria-label="Discount: No sort applied, activate to apply an ascending sort"
						style="-webkit-user-select: none;"><div
							class="tablesorter-header-inner">Discount</div></th>
					<th data-column="5"
						class="tablesorter-header tablesorter-headerUnSorted" tabindex="0"
						scope="col" role="columnheader" aria-disabled="false"
						unselectable="on" aria-sort="none"
						aria-label="Date: No sort applied, activate to apply an ascending sort"
						style="-webkit-user-select: none;"><div
							class="tablesorter-header-inner">Date</div></th>
				</tr>
			</thead>
			<tbody aria-live="polite" aria-relevant="all">
				<tr role="row">
					<td>Bruce</td>
					<td>Almighty</td>
					<td>45</td>
					<td class="discount">$153.19</td>
					<td>44%</td>
					<td>Jan 18, 2001 9:12 AM</td>
				</tr>
				<tr role="row">
					<td>John</td>
					<td>Hood</td>
					<td>33</td>
					<td class="discount">$19.99</td>
					<td>25%</td>
					<td>Dec 10, 2002 5:14 AM</td>
				</tr>
				<tr role="row">
					<td>Clark</td>
					<td>Kent</td>
					<td>18</td>
					<td class="discount">$15.89</td>
					<td>44%</td>
					<td>Jan 12, 2003 11:14 AM</td>
				</tr>
				<tr role="row">
					<td>Bruce</td>
					<td>Evans</td>
					<td>22</td>
					<td class="discount">$13.19</td>
					<td>11%</td>
					<td>Jan 18, 2007 9:12 AM</td>
				</tr>
				<tr role="row">
					<td>Peter</td>
					<td>Parker</td>
					<td>28</td>
					<td class="discount">$9.99</td>
					<td>20%</td>
					<td>Jul 6, 2006 8:14 AM</td>
				</tr>
			</tbody>
		</table>
	</form>
</body>
</html>