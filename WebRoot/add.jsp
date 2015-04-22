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
	<jsp:include page="common.jsp" />
<script type="text/javascript">
	var time;
	var dialog;
	$("document").ready(function(){
		initParentFileName();
		//对比报文
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
		
		if('${requestScope.messageType}' == '0')
		{
			$("#app").attr("checked",true);
			$("#realEntity").css("color","black");
			$("#expectEntity").css("color","black");
			$("#urlTr").css("display","");
			$("#uriTr").css("display","");
			$("#userTr").css("display","");
		}	
		

		//radio选择事件
		$("input[name='messageType']").click(function()
		{
			if($("input[name='messageType']:checked").val() == 0)
			{
				$("input[name='url']").val('');
				$("input[name='uri']").val('');
				$("input[name='username']").val('');
				$("input[name='password']").val('');
				$("#message").val('');
				$("input[name='module']").val('');
				$("input[name='fileName']").val('');
				$("#parentFileModule").val('');
				$("#childFileModule").val('');
				$("#expectEntity").val('');
				$("#realEntity").html('');
				$("#realEntity").css("color","black");
				$("#expectEntity").css("color","black");
				$("#urlTr").css("display","");
				$("#uriTr").css("display","");
				$("#userTr").css("display","");
			}
			
		});
		
		
		$("#saveAs").click(function(){
			var fileName = prompt("请输入文件名称", "");
		})
		
		$("#save").click(function()
		{
			if($("input[name='messageType']:checked").val() == 0)
			{
				if(!checkApiParam()){
					return;
				}
			}
			
			if('' == $("#parentFileModule").val() || null == $("#parentFileModule").val())
			{
				alert("父模块不能不空");
				return;
			}
			if('' == $("#childFileModule").val() || null == $("#childFileModule").val())
			{
				alert("子模块不能不空");
				return;
			}
			if('' == $("input[name='fileName']").val() || null == $("input[name='fileName']").val())
			{
				alert("保存文件名不能不空");
				$("input[name='fileName']").select();
				return;
			}
			
			$("#addForm").attr("action","<%=request.getContextPath()%>/AddTestServlet");
			$("#addForm").submit();
		});
		
		$("#sendMsg").click(function()
		{
			if($("input[name='messageType']:checked").val() == 0)
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
				
			/* 	if ('' == $("input[name='username']").val() || null == $("input[name='username']").val())
				{
					alert("APP调用账号不能为空");
					$("input[name='username']").select();
					return;
				}
				if ('' == $("input[name='password']").val() || null == $("input[name='password']").val())
				{
					alert("APP调用密码不能为空");
					$("input[name='password']").select();
					return;
				} */
			}
			if('' == $("#message").val() || null == $("#message").val())
			{
				alert("发送报文不能不空");
				$("#message").select();
				return;
			}
			if($("input[name='messageType']:checked").val() == 0)
			{
				$("#addForm").attr("action","<%=request.getContextPath()%>/TestMessageServlet");
				$("#addForm").submit();
			}
		
		});
		
		$("#addParentFileName").click(function()
		{
			dialog = art.dialog({
				title:'新增父模块',
				content: '<input type="text" name="<input type="text" id="parentFile" maxlength="15">&nbsp;<input type="button" onclick="addParentFileName();" value="确定"/>',
				lock : true,
				opacity : 0.5
			});
		});
		
		$("#addChildFileName").click(function()
		{
			dialog = art.dialog({
				title:'新增子模块',
				content: '<input type="text" name="<input type="text" id="childFile" maxlength="15">&nbsp;<input type="button" onclick="addChildFileName();" value="确定"/>',
				lock : true,
				opacity : 0.5
			});
		});
		
		$("#parentFileModule").change(function()
		{
			if(null != $("#parentFileModule").val() && '' != $("#parentFileModule").val())
			{
				$.ajax({
					type :  'post',
					url : '<%=request.getContextPath()%>/LoadDirectoryServlet',
					data : 
					{
						parentFileName : $("#parentFileModule").val()
					},
					cache : false,
					dataType: 'json',
					success : function(data)
					{
						var childFileName = data.childFileName;
						if(null != childFileName && childFileName.length>0)
						{
							$("#childFileModule option").remove();
							$("#childFileModule").append("<option value=''>请选择...</option>");
							for(var i=0;i<childFileName.length;i++)
							{
								$("#childFileModule").append("<option value='"+childFileName[i]+"'>"+childFileName[i]+"</option>");
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
				$("#childFileModule option").remove();
				$("#childFileModule").append("<option value=''>请选择...</option>");
			}
		});
		
		if('${requestScope.parentFileModule}' != null && '${requestScope.parentFileModule}' != '')
		{
			$.ajax({
				type :  'post',
				url : '<%=request.getContextPath()%>/LoadDirectoryServlet',
				data : 
				{
					parentFileName : '${requestScope.parentFileModule}'
				},
				cache : false,
				dataType: 'json',
				success : function(data)
				{
					var childFileName = data.childFileName;
					if(null != childFileName && childFileName.length>0)
					{
						$("#childFileModule option").remove();
						$("#childFileModule").append("<option value=''>请选择...</option>");
						for(var i=0;i<childFileName.length;i++)
						{
							if('${requestScope.childFileModule}' == childFileName[i])
							{
								$("#childFileModule").append("<option value='"+childFileName[i]+"' selected='selected'>"+childFileName[i]+"</option>");
							}	
							else
							{
								$("#childFileModule").append("<option value='"+childFileName[i]+"'>"+childFileName[i]+"</option>");
							}	
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
			$("#childFileModule option").remove();
			$("#childFileModule").append("<option value=''>请选择...</option>");
		}
		
		if('${requestScope.addSuccess}' == '1')
		{
			art.dialog.tips('新增报文成功');
		}	
	});
	
	//新增父模块名称
	function addParentFileName()
	{
		if(null == $("#parentFile").val() || '' == $("#parentFile").val())
		{
			alert("父模块名称不能为空");
			$("#parentFile").select();
			return;
		}	
		$.ajax({
			type :  'post',
			url : '<%=request.getContextPath()%>/AddDirectoryServlet',
			data : 
			{
				parentFileName : $("#parentFile").val()
			},
			cache : false,
			dataType: 'json',
			success : function(data)
			{
				art.dialog.tips('新增成功');
				$("#parentFileModule option").remove();
				$("#parentFileModule").append("<option value=''>请选择...</option>");
				var parentFileName = data.parentFileName;
				if(null != parentFileName && parentFileName.length>0)
				{
					for(var i=0;i<parentFileName.length;i++)
					{
						if($("#parentFile").val() == parentFileName[i])
						{
							$("#parentFileModule").append("<option value='"+parentFileName[i]+"' selected='seleted'>"+parentFileName[i]+"</option>");
						}	
						else
						{
							$("#parentFileModule").append("<option value='"+parentFileName[i]+"'>"+parentFileName[i]+"</option>");
						}	
					}	
				}	
				$("#childFileModule option").remove();
				$("#childFileModule").append("<option value=''>请选择...</option>");
				closeDialog();
			},
			error : function()
			{
				
			}
		});
	}
	
	//新增子模块名称
	function addChildFileName()
	{
		if(null == $("#parentFileModule").val() || '' == $("#parentFileModule").val())
		{
			closeDialog();
			alert("请先选择父模块");
			return;
		}	
		if(null == $("#childFile").val() || '' == $("#childFile").val())
		{
			alert("子模块名称不能为空");
			$("#childFile").select();
			return;
		}	
		$.ajax({
			type :  'post',
			url : '<%=request.getContextPath()%>/AddDirectoryServlet',
			data : 
			{
				parentFileName : $("#parentFileModule").val(),
				childFileName : $("#childFile").val()
			},
			cache : false,
			dataType: 'json',
			success : function(data)
			{
				art.dialog.tips('新增成功');
				$("#childFileModule option").remove();
				$("#childFileModule").append("<option value=''>请选择...</option>");
				var childFileName = data.childFileName;
				if(null != childFileName && childFileName.length>0)
				{
					for(var i=0;i<childFileName.length;i++)
					{
						if($("#childFile").val() == childFileName[i])
						{
							$("#childFileModule").append("<option value='"+childFileName[i]+"' selected='seleted'>"+childFileName[i]+"</option>");
						}	
						else
						{
							$("#childFileModule").append("<option value='"+childFileName[i]+"'>"+childFileName[i]+"</option>");
						}	
					}	
				}	
				closeDialog();
			},
			error : function()
			{
				
			}
		});
	}
	
	function closeDialog()
	{
		dialog.close();
	}
	
	function queryMessage()
	{
		$.ajax
		({
			type :  'post',
			url : '<%=request.getContextPath()%>/QueryResponseServlet',
			data : 
			{
				
			},
			cache : false,
			dataType: 'json',
			success : function(data)
			{
				if(data != '0')
				{
					$("#realEntity").html(data);
					if('' != $("#expectEntity").val())
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
							$("#realEntity").html("<span style='color:red'>"+data+"</span>");
						}
					}
				}	
				else
				{
					clearInterval(time);
				}	
			},
			error : function()
			{
				
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
						if('${requestScope.parentFileModule}' == parentFileName[i])
						{
							$("#parentFileModule").append("<option value='"+parentFileName[i]+"' selected='selected'>"+parentFileName[i]+"</option>");
						}	
						else
						{
							$("#parentFileModule").append("<option value='"+parentFileName[i]+"'>"+parentFileName[i]+"</option>");
						}	
					}	
				}	
			},
			error : function()
			{
				
			}
		});
	}
	
</script>
</head>
<body>
	<jsp:include page="top.jsp"/>
	<jsp:include page="left.jsp"/>
	<form id="addForm" action="<%=request.getContextPath()%>/AutoTestServlet" method="post" style="float: left;">
		<table style="width: 1100px;">
			<tr>
				<td colspan="2" align="center"><span style="color:red;font-size: 16px;">新增测试用例</span></td>
			</tr>
			<tr>
				<td align="right"><span style="color:red;">*</span>测试方式：</td>
				<td>
					<input type="radio" name="messageType" id="app" value="0" checked="checked"/>APP调用&nbsp;					
				</td>
			</tr>
			<tr id="urlTr">
				<td align="right"><span style="color:red;">*</span>url：</td>
				<td><input type="text" name="url" size="40" value="${requestScope.url}"/></td>
			</tr>
			<tr id="uriTr">
				<td align="right"><span style="color:red;">*</span>uri：</td>
				<td><input type="text" name="uri" size="40" value="${requestScope.uri}"/></td>
			</tr>
			<tr id="userTr" style="display: none;">
				<td align="right"><span style="color: red;">*</span>是否允许删除：</td>
				<td><select name="canDelete" id="canDelete">
						<option value="0">否</option>
						<option value="1" <c:if test="${canDelete==1}">selected</c:if>>是</option>
				</select></td>
			</tr>
			
			<tr>
				<td align="right"><span style="color:red;">*</span>发送报文：</td>
				<td><textarea name="message" id="message" cols="130" rows="5" title="可以使用$date$、 $time$ 插入动态的时间">${requestScope.message}</textarea></td>
			</tr>
			
			<tr>
				<td align="right"><span style="color: red;">*</span>报文备注信息：</td>
				<td><textarea name="remark" id="remark" cols="130" rows="5">${requestScope.remark}</textarea></td>
			</tr>
			
			<tr>
				<td align="right"><span style="color:red;">*</span>保存模块：</td>
				<td>
					父模块：<select id="parentFileModule" name="parentFileModule">
						<option value="">请选择...</option>
					</select>
					<input type="button" id="addParentFileName" value="新增"/>
					子模块：<select id="childFileModule" name="childFileModule">
						<option value="">请选择...</option>
					</select>
					<input type="button" id="addChildFileName" value="新增"/>
				</td>
			</tr>
			<tr>
				<td align="right"><span style="color:red;">*</span>保存文件名：</td>
				<td>
					<input type="text" name="fileName" value="${requestScope.fileName}"/>
				</td>
			</tr>
			<tr>
				<td align="right">预期返回报文：</td>
				<td><textarea name="expectEntity" id="expectEntity" cols="130" rows="5">${requestScope.expectEntity}</textarea></td>
			</tr>
			<tr>
				<td align="right" style="width: 100px;">实际返回报文：</td>
				<td style="width: 900px;"><div id="realEntity" style="width: 100%;height: 90px;border: 1px solid rgb(169, 169, 169);word-break:break-all;overflow-y: scroll;">${requestScope.entity}</div></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="button" id="save" value="&nbsp;&nbsp;保&nbsp;&nbsp;&nbsp;存&nbsp;&nbsp;">
					<input type="button" id="sendMsg" value="发送报文"/>					
				<td>
			</tr>
		</table>
	</form>
</body>
</html>