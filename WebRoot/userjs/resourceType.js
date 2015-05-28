var datagrid;
var rowEditor = undefined;
var ajaxServlet = _contextPath + "/ResourceTypeAjaxServlet";
$(function() {
	datagrid = $("#dg").datagrid({
		url : ajaxServlet,// 加载的URL
		title : "资源类型管理",
		columns : [ [ // 每个列具体内容
		{
			field : 'id',
			title : 'id',
			width : 100
		}, {
			field : 'name',
			title : '类型名称',
			width : 100,
			editor : {
				type : 'validatebox',
				options : {
					required : true
				}
			}
		}, {
			field : 'remark',
			title : '备注信息',
			width : 100,
			editor : {
				type : 'validatebox'

			}
		} ] ]

	});

})