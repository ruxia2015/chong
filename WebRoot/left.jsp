<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>  
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%>  
  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">  
<html>  
  <head>  
    <base href="<%=basePath%>">  
      
    <title>My JSP 'ztreedemo.jsp' starting page</title>  
      
    <meta http-equiv="pragma" content="no-cache">  
    <meta http-equiv="cache-control" content="no-cache">  
    <meta http-equiv="expires" content="0">      
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">  
    <meta http-equiv="description" content="This is my page">  
 <%--    <link rel="stylesheet" href="<%=basePath%>css/zTreeStyle/zTreeStyle.css" type="text/css">  
    <script type="text/javascript" src="<%=basePath%>js/jquery-1.4.4.min.js"></script>  
    <script type="text/javascript" src="<%=basePath%>js/jquery.ztree.core-3.5.min.js"></script>  
    <script type="text/javascript" src="<%=basePath%>js/jquery.ztree.exedit-3.5.min.js"></script>  
    <script type="text/javascript" src="<%=basePath%>js/jquery.ztree.excheck-3.5.min.js"></script>  
    <script type="text/javascript" src="<%=basePath%>js/jquery.ztree.exhide-3.5.min.js"></script>  
    
    <link rel="stylesheet" type="text/css" href="css/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="css/themes/icon.css"> --%>

	<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
	

	
   <style type="text/css">
   	a{text-decoration:none;
   	  	color: blue;   	  
   	};
   	a:hover {
   		color:green;
   	}
	
	a:LINK {
		color:orange;
	}
   
   </style>
   
   
   <script type="text/javascript">
   
   $.ajax({
		url : "<%=request.getContextPath()%>/TgAjaxServlet",
		data :null,
		dataType : 'json',
		success : function(r) {
				var obj = 	r;
				if(obj!=null){
					for(var i=0;i<obj.length;i++){
						var con = ' <p> <a href="javascript:void(0)" onclick="openTgResource(\''+obj[i].domain+'\',\''+obj[i].id+'\')">'+obj[i].domain+'</a></p>';
						$("#tgs").append(con);
					}
				}


		}
	});
   
   
   	function openTgResource(tagName,tgId){   
   		if(document.getElementById("tgTabs")==null){
   			window.open("<%=request.getContextPath()%>/queryTgResourceMain.jsp" );   			
   		}else{
   			var url ="<%=request.getContextPath()%>/ResourceTgServlet?tgId="+tgId;  
   			var name = '推广_'+tagName;
   			if($('#tgTabs').tabs("exists",name)){
   				//$("#tagTabs").tabs("select",name);
   			}else{
   		    	$('#tgTabs').tabs('add',{
   					title: name,
   					content: '<iframe width="100%" height="100%" src="'+url+'"/>',
   					closable: true
   				});
   			}
	    	
   		}
   	}
   
   </script>
    
  <body>  
   	<div class="easyui-accordion" data-options="multiple:true" style="width:250px;height1:300px;">
		<div title="设置"  style="overflow:auto;padding:10px;" >
		   <a href="<%=request.getContextPath() %>/ResourceTypeServlet">资源类型管理</a>		   
		</div>
		<div title="资源管理" style="padding:10px;" >
		<p>
		  <a href="<%=request.getContextPath() %>/ResourceServlet/m/toAdd.action">添加资源</a>
		</p>
		<p>
		  <a href="<%=request.getContextPath() %>/ResourceServlet">资源管理</a>
		</p>
		</div>
		<div title="推广网站管理" style="padding:10px;">

		<div>
		  <a href="<%=request.getContextPath() %>/TgServlet">推广网站管理</a>
		</div>
		</div>
		<div title="资源账号管理" style="padding:10px;" data-options="selected:true" id="tgs">

		</div>

	</div>
  </body>  
</html>  