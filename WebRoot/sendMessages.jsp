<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>批量执行报文</title>
<script type="text/javascript" src="js/jquery-1.6.min.js"></script>
<script type="text/javascript">
	function execute()
	{
		var zTree = $.fn.zTree.getZTreeObj("ztree");
		var nodes = zTree.getCheckedNodes(true);
		if(nodes.length < 1)
		{
			alert("请勾选需要执行的报文");
			return;
		}	
		cutover('#failTable','1');
		$("#failResultList tr").remove();
		$("#successResultList tr").remove();
		$("#failResult tr").remove();
		$("#successResult tr").remove();
		$("#count").hide();
		$("#cutover").hide();
		$("#execute").hide();
		$("#progressBar").show();
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
		$("#sum").html(arr.length);
		$.ajax
		({
			type :  'post',
			url : '<%=request.getContextPath()%>/SendMessageServlet',
			data : 
			{
				"filePaths[]" : arr
			},
			cache : false,
			dataType: 'json',
			success : function(data)
			{
				$("#execute").show();
				$("#progressBar").hide();
				$("#failResult").append("<thead>"
    			+ "<tr bgcolor=\"#f6fafb\" style=\"height: 25px;line-height: 25px;\">"
    			+ "<td colspan=\"2\" align=\"center\"><span style=\"color:red;\">失败报文列表</span></td>"
    		    + "</tr>"
    		    + "<tr bgcolor=\"#f6fafb\" style=\"height: 20px;line-height: 20px;\">"
    			+ "<td align=\"center\">请求消息类型</td>"
    			+ "<td align=\"center\">报文文件路径</td>"
    			+ "</tr>"
    			+ "</thead>");
				$("#successResult").append("<thead>"
    			+ "<tr bgcolor=\"#f6fafb\" style=\"height: 25px;line-height: 25px;\">"
    			+ "<td colspan=\"2\" align=\"center\"><span style=\"color:red;\">成功报文列表</span></td>"
    			+ "</tr>"
    			+ "<tr bgcolor=\"#f6fafb\" style=\"height: 20px;line-height: 20px;\">"
    			+ "<td align=\"center\">请求消息类型</td>"
    			+ "<td align=\"center\">报文文件路径</td>"
    			+ "</tr>"
    			+ "</thead>");
				if(data != null)
				{
					$("#successCount").html(data.successResultList.length);
					$("#failedCount").html(data.failedResultList.length);
					$("#count").show();
					$("#cutover").show();
					var failedResultList = data.failedResultList;
					var successResultList = data.successResultList;
					if(null != failedResultList && failedResultList.length > 0)
					{
						for(var i=0;i<failedResultList.length;i++)
						{
							$("#failResult").show();
							$("#failResult").append("<tbody><tr bgcolor=\"#f6fafb\"  style=\"height: 20px;line-height: 20px;\">"
							+ "<td align=\"center\">"+failedResultList[i].msgType+"</td>"
							+ "<td align=\"center\">"+failedResultList[i].filePath+"</td>"
							+ "</tr></tbody>"
							);
							
							$("#failResultList").append("<tbody id=\"body"+i+"\"><tr>"
					    			+ "<td style=\"vertical-align:middle;color: #2366A8;\">模块："+failedResultList[i].filePath+"<br>发送报文:</td>"
					    			+ "</tr>"
					    			+ "<tr>"
					    			+ "<td><div id=\"message"+i+"\" style=\"width: 1141px;height: auto;margin-bottom:10px;\"></div></td>"
					    			+ "</tr>"
									+"<tr>"
					    			+ "<td style=\"vertical-align:middle;color: #2366A8;\">预期返回报文：</td>"
					    			+ "</tr>"
					    			+ "<tr>"
					    			+ "<td><div id=\"expectEntity"+i+"\" style=\"width: 1141px;height: auto;margin-bottom:10px;\"></div></td>"
					    			+ "</tr>"
					    			+ "<tr>"
					    			+ "<td style=\"vertical-align:middle;color: #2366A8;\">实际返回报文：<br></td>"
					    			+ "</tr>"
									+ "<tr>"
					    			+ "<td><div id=\"realEntity"+i+"\" style=\"width: 1141px;height: auto;color:red;\"></div><div style=\"border:none;border-top:#999 dashed 1px;margin-top:20px;margin-bottom:20px;\"></div></td>"
					    			+ "</tr></tbody>"
					    			);
							$("#failResultList #message"+i).html(failedResultList[i].message);
							$("#failResultList #expectEntity"+i).html(failedResultList[i].expectEntity);
							$("#failResultList #realEntity"+i).html(failedResultList[i].errorMessage);
						}
					}	
					
					if(null != successResultList && successResultList.length > 0)
					{
						for(var j=0;j<successResultList.length;j++)
						{
							$("#successResult").append("<tbody><tr bgcolor=\"#f6fafb\"  style=\"height: 20px;line-height: 20px;\">"
							+ "<td align=\"center\">"+successResultList[j].msgType+"</td>"
							+ "<td align=\"center\">"+successResultList[j].filePath+"</td>"
							+ "</tr></tbody>"
							);
							
							$("#successResultList").append("<tbody id=\"body"+j+"\"><tr>"
					    			+ "<td style=\"vertical-align:middle;color: #2366A8;\">模块："+successResultList[j].filePath+"<br>发送报文:</td>"
					    			+ "</tr>"
					    			+ "<tr>"
					    			+ "<td><div id=\"message"+j+"\" style=\"width: 1141px;height: auto;margin-bottom:10px;\"></div></td>"
					    			+ "</tr>"
									+"<tr>"
					    			+ "<td style=\"vertical-align:middle;color: #2366A8;\">预期返回报文：</td>"
					    			+ "</tr>"
					    			+ "<tr>"
					    			+ "<td><div id=\"expectEntity"+j+"\" style=\"width: 1141px;height: auto;margin-bottom:10px;\"></div></td>"
					    			+ "</tr>"
					    			+ "<tr>"
					    			+ "<td style=\"vertical-align:middle;color: #2366A8;\">实际返回报文：<br></td>"
					    			+ "</tr>"
									+ "<tr>"
					    			+ "<td><div id=\"realEntity"+j+"\" style=\"width: 1141px;height: auto;color:green;\"></div><div style=\"border:none;border-top:#999 dashed 1px;margin-top:20px;margin-bottom:20px;\"></div></td>"
					    			+ "</tr></tbody>"
					    			);
							$("#successResultList #message"+j).html(successResultList[j].message);
							$("#successResultList #expectEntity"+j).html(successResultList[j].expectEntity);
							$("#successResultList #realEntity"+j).html(successResultList[j].realEntity);
						}
					}	
				}	
			},
			error : function()
			{
				
			}
		});	
	}
	
	function cutover(obj,index)
	{
		$('a').removeClass('messageState');
		$(obj).addClass('messageState');
		if(index == '1')
		{
			$("#failResult").show();
			$("#failResultList").hide();
			$("#successResult").hide();
			$("#successResultList").hide();
		}
		if(index == '2')
		{
			$("#failResult").hide();
			$("#failResultList").show();
			$("#successResult").hide();
			$("#successResultList").hide();
		}
		if(index == '3')
		{
			$("#failResult").hide();
			$("#failResultList").hide();
			$("#successResult").show();
			$("#successResultList").hide();
		}
		if(index == '4')
		{
			$("#failResult").hide();
			$("#failResultList").hide();
			$("#successResult").hide();
			$("#successResultList").show();
		}
	}
</script>
<style type="text/css">
body {
	margin:0px;
	padding:0px;
	font-size: 12px;
}

a
{
	outline:none;
	text-decoration: none;
	color: #2366A8;
}

a:hover
{
	text-decoration: underline;
	color: red;
}

.messageState
{
	border: 1px solid #C8DCF0;
	background-color: #f6fafb;
	padding: 6px;
}
</style>
</head>
<body>
	<jsp:include page="top.jsp"/>
	<jsp:include page="left.jsp"></jsp:include>
	<p>
	&nbsp;&nbsp;<a href="javascript:void(0);" onclick="execute()" id="execute" style="border: 1px solid #C8DCF0;padding: 6px;border-radius: 4px;">执行</a><img alt="" src="<%=request.getContextPath()%>/images/progressBar.gif" id="progressBar" style="display: none;">
    <br>
    <div style="width: 99.9%;background-color: #f6fafb;border: 1px solid #C8DCF0;height: 25px;line-height: 25px;display: none;margin-top:13.5px;margin-left: 20%;" id="count">
     	&nbsp;&nbsp;执行结果：共发送&nbsp;<span style="color:red;" id="sum"></span>&nbsp;条报文，成功&nbsp;<span style="color:red;" id="successCount"></span>&nbsp;条,失败条数&nbsp;<span style="color:red;" id="failedCount"></span>&nbsp;条
    </div>
    <p>
    <div id="cutover" style="display: none;margin-left: 20%;">
    &nbsp;<a href="javascript:void(0);" onclick="cutover(this,'1');" class="messageState" style="border: 1px solid #C8DCF0;padding: 6px;border-radius: 4px;" id="failTable">失败列表</a>
    &nbsp;<a href="javascript:void(0);" onclick="cutover(this,'2');" style="border: 1px solid #C8DCF0;padding: 6px;border-radius: 4px;">失败列表详情</a>
    &nbsp;<a href="javascript:void(0);" onclick="cutover(this,'3');" style="border: 1px solid #C8DCF0;padding: 6px;border-radius: 4px;">成功列表</a>
    &nbsp;<a href="javascript:void(0);" onclick="cutover(this,'4');" style="border: 1px solid #C8DCF0;padding: 6px;border-radius: 4px;">成功列表详情</a>
    </div>
    <p>
    <table id="failResult" style="display: none;background-color: #C8DCF0;width: 60%;margin-left: 400px;" cellspacing="1" cellpadding="0" border="0">
    </table>
    
    <table id="successResult" style="display: none;background-color: #C8DCF0;width: 60%;margin-left: 400px;" cellspacing="1" cellpadding="0" border="0">
    </table>
    
    <table id="failResultList" style="display: none;">
    
    </table>
    <table id="successResultList" style="display: none;">
    
    </table>
</body>
</html>