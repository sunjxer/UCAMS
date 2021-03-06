<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>声像档案归档目录</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
			var data = ${fns:toJson(list)}, ids = [], rootIds = [];
			console.log(data);
			for (var i=0; i<data.length; i++){
				ids.push(data[i].id);
			}
			ids = ',' + ids.join(',') + ',';
			for (var i=0; i<data.length; i++){
				if (ids.indexOf(','+data[i].parentId+',') == -1){
					if ((','+rootIds.join(',')+',').indexOf(','+data[i].parentId+',') == -1){
						rootIds.push(data[i].parentId);
					}
				}
			}
			for (var i=0; i<rootIds.length; i++){
				addRow("#treeTableList", tpl, data, rootIds[i], true);
			}
			$("#treeTable").treeTable({expandLevel : 5});
		});
		function addRow(list, tpl, data, pid, root){
			for (var i=0; i<data.length; i++){
				var row = data[i];
				if ((${fns:jsGetVal('row.parentId')}) == pid){
					$(list).append(Mustache.render(tpl, {
						dict: {
							blank123:0,
							createUnit: getAmsDictLabel(${fns:toJson(fns:getAmsDictList('ams_generate_unit'))},row.createUnit)
						},
						pid: (root?0:pid), 
						row: row
					}));
					addRow(list, tpl, data, row.id);
				}
			}
			if(row.isEndChild==1){//根节点取消添加下级目录操作
				$("#a"+row.id).attr({style: "display:none;"});
			}
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/ams/amsArchiveMenu/list">类别列表</a></li>
		<shiro:hasPermission name="ams:amsArchiveMenu:edit"><li><a href="${ctx}/ams/amsArchiveMenu/form">类别添加</a></li></shiro:hasPermission>
	</ul>
	<sys:message content="${message}"/>
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="text-align:center;">名称 </th>
				<th width="10%" style="text-align:center;">编码</th>
				<th style="text-align:center;">更新时间</th>
				<th width="25%" style="text-align:center;">备注信息</th>
				
				<shiro:hasPermission name="ams:amsArchiveMenu:edit"><th style="text-align:center;">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody id="treeTableList"></tbody>
	</table>
	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.id}}" pId="{{pid}}">
			<td>
					<a href="${ctx}/ams/amsArchiveMenu/form?id={{row.id}}">
						{{row.name}}
					</a>
			</td>
			
			<td>
				{{row.code}}
			</td>
			<td>
				{{row.updateDate}}
			
			</td>
			<td>
				{{row.remarks}}
			</td>
			
			<shiro:hasPermission name="ams:amsArchiveMenu:edit"><td>
				<a href="${ctx}/ams/amsArchiveMenu/form?id={{row.id}}">修改</a>
				<a href="${ctx}/ams/amsArchiveMenu/delete?id={{row.id}}" onclick="return confirmx('确认要删除该类别及所有子类别吗？', this.href)">删除</a>
			
			
 				<a style="display:block" id="a{{row.id}}" href="${ctx}/ams/amsArchiveMenu/form?parent.id={{row.id}}">添加下级类别</a> 
		
			</td></shiro:hasPermission>
		</tr>
	</script>
</body>
</html>