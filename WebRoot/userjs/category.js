	var datagrid;
	var rowEditor=undefined;
	var ajaxServlet = _contextPath+"/CategoryAjaxServlet";
	$(function(){
		datagrid=$("#dg").datagrid({
			url:ajaxServlet,//加载的URL
		   
			title:"网站分类（齿科类）",
			columns:[[      //每个列具体内容
		              {
		            	  field:'id',
		            	  title:'id',
		            	  width:100,

		           	 },   
		              {field:'category',title:'网站分类',width:100,editor : {
							type : 'validatebox',
							options : {
								//required : true
							}
						}},   
		              {field:'remark',title:'备注',width:100,editor : {
							type : 'validatebox'
							
						}}   
		          ]]
		});
		
	});