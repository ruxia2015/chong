	var datagrid;
	var rowEditor=undefined;
	$(function(){
		var stateData = [
					      {id:"",text:"请选择"},
					      {id:"1",text:"是"},
					      {id:"0",text:"否"}
					      ];
		datagrid=$("#dg").datagrid({
			url:_contextPath+"/ResourceAjaxServlet",//加载的URL
		    isField:"id",
			pagination:true,//显示分页
			pageSize:5,//分页大小
			pageList:[5,10,15,20],//每页的个数
			fit:true,//自动补全
			fitColumns:true,
			iconCls:"icon-save",//图标
			title:"资源管理",
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
		              {field:'domain',title:'domain',width:100,editor : {
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
						{field:'type',title:'type',width:100,editor : {
							type : 'combobox',
							options : {
								valueField:'id',textField:'name',
								url:_contextPath+"/ResourceTypeAjaxServlet"
							}
						}},  
						{field:'accessState',title:'accessState',width:100,editor : {
							type : 'combobox',
							options : {
								valueField:'id',textField:'text',
								data:stateData
							}
							
						},
						formatter : function(value, rec) {
							if (value == 1) {
								return "是";
							}if(  typeof value =="undefined" ||value=="" ){
								return "";
							}
							return "否";

						}},  
						{field:'registerState',title:'registerState',width:100,editor : {
							type : 'combobox',
							options : {
								valueField:'id',textField:'text',
								data:stateData
							}},
							formatter : function(value, rec) {
								if (value == 1) {
									return "是";
								}if(  typeof value =="undefined" ||value=="" ){
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
								}if(  typeof value =="undefined" ||value=="" ){
									return "";
								}
								return "否";

							}
						},  
		              {field:'remark',title:'remark',width:100,editor : {
							type : 'validatebox'
							
						}}   
		          ]],
			toolbar:[              //工具条
			        {text:"增加",iconCls:"icon-add",handler:function(){//回调函数
			        	if(rowEditor==undefined)
						{
			        		datagrid.datagrid('insertRow',{//如果处于未被点击状态，在第一行开启编辑
				        		index: 0,	
				        		row: {
				        		}
				        	});
			        		rowEditor=0;
				        	datagrid.datagrid('beginEdit',rowEditor);//没有这行，即使开启了也不编辑
				        	
						}
			        

			        }},
			        {text:"删除",iconCls:"icon-remove",handler:function(){
			        	var rows=datagrid.datagrid('getSelections');
			  
			        	if(rows.length<=0)
			        	{
			        		$.messager.alert('警告','您没有选择','error');
			        	}
			        	else if(rows.length>1)
			        	{
			        		$.messager.alert('警告','不支持批量删除','error');
			        	}
			        	else
			        	{
			        		$.messager.confirm('确定','您确定要删除吗',function(t){
			        			if(t)
			        			{
			        				
			        				$.ajax({
			        					url : _contextPath+'/ResourceAjaxServlet/m/delete.action',
			        					data : {"id":rows[0].id},
			        					dataType : 'json',
			        					success : function(r) {
			        						if (r.success) {
			        							datagrid.datagrid('acceptChanges');
			        							$.messager.show({
			        								msg : r.msg,
			        								title : '成功'
			        							});
			        							editRow = undefined;
			        							datagrid.datagrid('reload');
			        						} else {
			        							/*datagrid.datagrid('rejectChanges');*/
			        							datagrid.datagrid('beginEdit', editRow);
			        							$.messager.alert('错误', r.msg, 'error');
			        						}
			        						datagrid.datagrid('unselectAll');
			        					}
			        				});
			        			
			        			}
			        		})
			        	}
			        	
			        	
			        }},
			        {text:"修改",iconCls:"icon-edit",handler:function(){
			        	var rows=datagrid.datagrid('getSelections');
			        	if(rows.length==1)
			        	{
			        		if(rowEditor==undefined)
							{
			        			var index=datagrid.datagrid('getRowIndex',rows[0]);
			        			 rowEditor=index;
			        			datagrid.datagrid('unselectAll');
					        	datagrid.datagrid('beginEdit',index);
					        	
							}
			        	}
			        }},
			        {text:"查询",iconCls:"icon-search",handler:function(){}},
			        {text:"保存",iconCls:"icon-save",handler:function(){
			        	
			        	datagrid.datagrid('endEdit',rowEditor);
			        	rowEditor=undefined;
			        }},
			        {text:"取消编辑",iconCls:"icon-redo",handler:function(){
			        	rowEditor=undefined;
			        	datagrid.datagrid('rejectChanges')
			        }}
			        ],
			onAfterEdit:function(rowIndex, rowData, changes){
				var inserted = datagrid.datagrid('getChanges', 'inserted');
				var updated = datagrid.datagrid('getChanges', 'updated');
				if (inserted.length < 1 && updated.length < 1) {
					editRow = undefined;
					datagrid.datagrid('unselectAll');
					return;
				}

				var url = '';
				if (inserted.length > 0) {
					url = _contextPath+'/ResourceAjaxServlet/m/add.action';
				}
				if (updated.length > 0) {
					url = _contextPath+'/ResourceAjaxServlet/m/update.action';
				}

				$.ajax({
					url : url,
					data :{"bean": JSON.stringify(rowData),"id":rowData.id},
					dataType : 'json',
					success : function(r) {
						if (r.successCode==0) {
							datagrid.datagrid('acceptChanges');
							$.messager.show({
								msg : "tianjiachenggong",
								title : '成功'
							});
							editRow = undefined;
							datagrid.datagrid('reload');
						} else {
							/*datagrid.datagrid('rejectChanges');*/
							datagrid.datagrid('beginEdit', editRow);
							$.messager.alert('错误', r.msg, 'error');
						}
						datagrid.datagrid('unselectAll');
					}
				});
				
			},
			onDblClickCell:function(rowIndex, field, value){
				if(rowEditor==undefined)
				{
		        	datagrid.datagrid('beginEdit',rowIndex);
		        	rowEditor=rowIndex;
				}
				
			}
		});
		$("#search").click(function(){
			datagrid.datagrid('load',{
				text:$("#text").val()
			});

		});
		
	})