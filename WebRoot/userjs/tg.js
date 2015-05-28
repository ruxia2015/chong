	var datagrid;
	var ajaxServlet = _contextPath+"/TgAjaxServlet";
	var rowEditor=undefined;
	$(function(){
		datagrid=$("#dg").datagrid({
			url:_contextPath+"/TgAjaxServlet",//加载的URL
			title:"推广网站管理（所要推广的网站）",
			columns:[[      //每个列具体内容
		              {
		            	  field:'id',
		            	  title:'id',
		            	  width:100,

		           	 },   
		              {field:'domain',title:'推广的网站',width:100,editor : {
							type : 'validatebox',
							options : {
								//required : true
							}
						}},   
						
			              {field:'categoryIds',title:'网站类别',width:100
							}, 
		              {field:'remark',title:'备注',width:100,editor : {
							type : 'validatebox'
							
						}}   
		          ]]
		});
			
		
	});