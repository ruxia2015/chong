	var datagrid;
	var rowEditor=undefined;
	var ajaxServlet = _contextPath + "/ResourceAjaxServlet";
	$(function(){
		var stateData = [
					      {id:"",text:"请选择"},
					      {id:"1",text:"是"},
					      {id:"0",text:"否"}
					      ];
		datagrid=$("#dg").datagrid({
			url:ajaxServlet,//加载的URL		   
			title:"资源管理",
			queryParams: {				
			},
			columns:[[      //每个列具体内容
		              {
		            	  field:'id',
		            	  title:'id',
		            	  width:100,
		            	  editor : {//是否可编辑
								type : 'validatebox',
								options : {//必须校验
								//	required : true
								}
							}
		           	 },   
		              {field:'domain',title:'域名',width:100,editor : {
							type : 'validatebox',
							options : {
								//required : true
							}
						}},   
						{field:'url',title:'url',width:100,editor : {
							type : 'validatebox',
							options : {
								//required : true
							}
						}},  
						{field:'type',title:'资源类型',width:100,editor : {
							type : 'combobox',
							options : {
								valueField:'id',textField:'name',
								url:_contextPath+"/ResourceTypeAjaxServlet"
							}
							
						}
						},  
						{field:'categoryId',title:'资源分类',width:100,editor : {
							type : 'combobox',
							options : {
								valueField:'id',textField:'category',
								url:_contextPath+"/CategoryAjaxServlet"
							}
						}			
						
						},  
						{field:'accessState',title:'可否访问',width:100,editor : {
							type : 'combobox',
							options : {
								valueField:'id',textField:'text',
								data:stateData
							}
							
						},

						formatter : function(value, rec) {
							if (value == 1) {
								return "是";
							}else if (value == -1) {
								return "";
							}
							
							if(  typeof value =="undefined" ||value=="" ){
								return "";
							}
							return "否";

						}},  
						{field:'registerState',title:'可否注册',width:100,editor : {
							type : 'combobox',
							options : {
								valueField:'id',textField:'text',
								data:stateData
							}},
							formatter : function(value, rec) {
								if (value == 1) {
									return "是";
								}else if (value == -1) {
									return "";
								}
								if(  typeof value =="undefined" ||value=="" ){
									return "";
								}
								return "否";

							}
						},  
						{field:'otherState',title:'otherState',width:100,editor : {
							type : 'combobox',
							options : {
								valueField:'id',textField:'text',
								data:stateData
							}},
							formatter : function(value, rec) {
								if (value == 1) {
									return "是";
								}else if (value == -1) {
									return "";
								}
								if(  typeof value =="undefined" ||value=="" ){
									return "";
								}
								return "否";

							}
						},  
		              {field:'remark',title:'备注信息',width:100,editor : {
							type : 'validatebox'
							
						}} ,
						{field:'pr',title:'pr',width:100,editor : {
							type : 'validatebox'
							
						}}    
		          ]],
			
		});
		$("#search").click(function(){
			datagrid.datagrid('load',{
				text:$("#text").val()
			});

		});
		
	});