<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>新增报文</title>

<jsp:include page="common.jsp" />
	


<script type="text/javascript">
 	$(function() {
		$("#myTable").tablesorter();
		
/* 		$("#page").pagination("onSelectPage",function(pageNumber,pageSize){
			alert("ass");
			
		}); */		
		 
		$('#page').pagination({ 
			total:100, 
			pageSize:20 ,
			pageNumber:3,
			onChangePageSize : function(pageSize) {
		    $("#pageSize").val(pageSize);
			                      alert(pageSize);
			                  },
			                  onSelectPage:function(pageIndex){
			                      alert(pageIndex);
			                      
			                      $("#currentPage").val(pageIndex);
			                  }
			             
		}); 
		
	}); 
 	
 	
 	function changePage(page_index, jq){
 		alert(page_index+"    " +jq);
 	}
 	
 	function query(){
 		
 	}
 	
</script>

</head>
<body style="padding:40px;">

<jsp:include page="top.jsp"/>
<div style="float:left;width:260px;">
	<jsp:include page="left.jsp"/>
</div>
<div style="float:left;width:auto;border:1px solid #95B8E7;min-width:800px;">
	<form id="addForm"
		action="<%=request.getContextPath()%>/QueryResourcetServlet" method="get"
		style="">
		<table class="tablesorter tablesorter-blue" role="grid" id="myTable">
			<thead>
				<tr role="row" class="tablesorter-headerRow">
					<th data-column="0"
						class="tablesorter-header tablesorter-headerAsc" tabindex="0"
						scope="col" role="columnheader" aria-disabled="false"
						unselectable="on" aria-sort="ascending"
						aria-label="First Name: Ascending sort applied, activate to apply a descending sort"
						style="-webkit-user-select: none;"><div
							class="tablesorter-header-inner">ID</div></th>
					<th data-column="1"
						class="tablesorter-header tablesorter-headerUnSorted" tabindex="0"
						scope="col" role="columnheader" aria-disabled="false"
						unselectable="on" aria-sort="none"
						aria-label="Last Name: No sort applied, activate to apply an ascending sort"
						style="-webkit-user-select: none;"><div
							class="tablesorter-header-inner">域名</div></th>
					<th data-column="2"
						class="tablesorter-header tablesorter-headerUnSorted" tabindex="0"
						scope="col" role="columnheader" aria-disabled="false"
						unselectable="on" aria-sort="none"
						aria-label="Age: No sort applied, activate to apply an ascending sort"
						style="-webkit-user-select: none;"><div
							class="tablesorter-header-inner">地址</div></th>
					<th data-column="3"
						class="tablesorter-header tablesorter-headerDesc" tabindex="0"
						scope="col" role="columnheader" aria-disabled="false"
						unselectable="on" aria-sort="descending"
						aria-label="Total: Descending sort applied, activate to apply an ascending sort"
						style="-webkit-user-select: none;"><div
							class="tablesorter-header-inner">类型</div></th>
					<th data-column="4"
						class="tablesorter-header tablesorter-headerUnSorted" tabindex="0"
						scope="col" role="columnheader" aria-disabled="false"
						unselectable="on" aria-sort="none"
						aria-label="Discount: No sort applied, activate to apply an ascending sort"
						style="-webkit-user-select: none;"><div
							class="tablesorter-header-inner">访问状态</div></th>
					<th data-column="5"
						class="tablesorter-header tablesorter-headerUnSorted" tabindex="0"
						scope="col" role="columnheader" aria-disabled="false"
						unselectable="on" aria-sort="none"
						aria-label="Date: No sort applied, activate to apply an ascending sort"
						style="-webkit-user-select: none;"><div
							class="tablesorter-header-inner">注册状态</div></th>
							<th data-column="5"
						class="tablesorter-header tablesorter-headerUnSorted" tabindex="0"
						scope="col" role="columnheader" aria-disabled="false"
						unselectable="on" aria-sort="none"
						aria-label="Date: No sort applied, activate to apply an ascending sort"
						style="-webkit-user-select: none;"><div
							class="tablesorter-header-inner">其他的状态</div></th>
							<th data-column="5"
						class="tablesorter-header tablesorter-headerUnSorted" tabindex="0"
						scope="col" role="columnheader" aria-disabled="false"
						unselectable="on" aria-sort="none"
						aria-label="Date: No sort applied, activate to apply an ascending sort"
						style="-webkit-user-select: none;"><div
							class="tablesorter-header-inner">备注信息</div></th>
				</tr>
			</thead>
			<tbody aria-live="polite" aria-relevant="all">			
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
				
			</tbody>
		</table>
		<div class="easyui-panel" >
		<div class="easyui-pagination" id="page" data-options="
					total:114,
					layout:['list','sep','first','prev','links','next','last','sep','refresh']">
		</div>
		
		<input type="hidden1" value="" name="currentPage" id="currentPage"/>
		<input type="hidden1" value="" name="pageSize" id="pageSize"/>
	</div>
		
	</form>
	</div>
	
</body>
</html>