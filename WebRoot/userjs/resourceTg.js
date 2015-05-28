var datagrid;
var rowEditor = undefined;
var ajaxServlet = _contextPath + "/ResourceTgAjaxServlet";
$(function() {
	var tgId = $("#tgId").val();
	datagrid = $("#dg")
			.datagrid(
					{
						url : ajaxServlet,// 加载的URL
						title : "推广网站资源管理",
						columns : [ [ // 每个列具体内容
								{
									field : 'id',
									title : 'id',
									width : 100
								},
								/*
								 * { field : 'tgId', title : '推广网站Id', width :
								 * 100, formatter : function(value, rec) { if
								 * (typeof value == "undefined" || value == "") {
								 * return tgId; } return value; } },
								 */{
									field : 'resourceDomain',
									title : '资源名称',
									width : 100
								},
								{
									field : 'resourceUrl',
									title : '资源地址',
									width : 100,
									formatter : function(value, rec) {
										var html = "<a href='" + value
												+ "' target='_blank' title='"
												+ value + "'>" + value + "</a>"
										return html;

									}
								},
								{
									field : 'resourceTypeName',
									title : '资源类型',
									width : 100,
									align : "center"
								},
								{
									field : 'category',
									title : '资源类别',
									width : 100,
									align : "center"
								},
								{
									field : 'registerState',
									title : '是否可以注册',
									width : 80,
									align : "center",

									formatter : function(value, rec) {
										if (value == 1) {
											return "是";
										}
										if (typeof value == "undefined"
												|| value == "") {
											return "";
										}
										return "否";

									}

								}, {
									field : 'account',
									title : '账号',
									width : 100,
									editor : {
										type : 'validatebox',
										options : {
										// required : true
										}
									}
								}, {
									field : 'password',
									title : '密码',
									width : 100,
									editor : {
										type : 'validatebox'

									}
								}, {
									field : 'email',
									title : '邮箱',
									width : 100,
									editor : {
										type : 'validatebox'

									}
								} ] ]

					});

});