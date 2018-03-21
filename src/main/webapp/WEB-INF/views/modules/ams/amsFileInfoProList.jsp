<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>文件管理</title>
<meta name="decorator" content="default" />
<%@include file="/WEB-INF/views/include/treeview.jsp"%>
<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
			var data = ${fns:toJson(list)}, ids = [], rootIds = [];
			for (var i=0; i<data.length; i++){
				ids.push(data[i].id);
			}
			ids = ',' + ids.join(',') + ',';
			for (var i=0; i<data.length; i++){
				//alert(ids.indexOf(','+data[i].parentId+',')+data[i].name);
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
			$(":radio[aa='record']").click(function(){
				$("#searchForm").submit();
			});
			
		});
		/* 
		if(idSelect==2){
			if(row.isHaveFile==''){
				addRow(list, tpl, data, row.id);
			}
			if(row.isHaveFile==undefined){ */
		function addRow(list, tpl, data, pid, root){
			var idSelect=$("#ifUpload").val();
			for (var i=0; i<data.length; i++){
				var row = data[i];
				if ((${fns:jsGetVal('row.parentId')}) == pid){
					/* $("#a"+pid).attr({ style: "display:none;"});
					$("#"+pid).attr({ style: "background-color:#f9f9f9;"}); */
					if(idSelect==2){
						if(row.isHaveFile==''||row.isHaveFile==undefined){
							$(list).append(Mustache.render(tpl, {
								dict: {
										blank123:0}, pid: (root?0:pid), row: row
								}));
								addRow(list, tpl, data, row.id);
							}
					}else{
						$(list).append(Mustache.render(tpl, {dict: {blank123:0}, pid: (root?0:pid), row: row}));
							addRow(list, tpl, data, row.id);
					}
					if(row.isHaveFile!=''&&row.isHaveFile!=undefined){//有文件变色
					$("#"+row.id).attr({ style: "background-color:red;"});
					}
					if(row.isEndChild==1){//是末节点显示文件管理
					$("#a"+row.id).attr({ style: "display:block;"});
					}else{
						$("#a"+row.id).attr({ style: "display:none;"});
					}
				}
			}
		}
	</script>
<script type="text/javascript">

function page(n,s){
	$("#pageNo").val(n);
	$("#pageSize").val(s);
	$("#searchForm").submit();
	return false;
}
</script>

</head>
<body>
		<c:if test="${empty amsProjectInfo.id}">
		<%-- <label class="control-label"> ${message}</label> --%>
		<script type="text/javascript">top.$.jBox.tip("项目不存在！");
		</script>
		</c:if>
		<c:if test="${not empty amsProjectInfo.id}">
		<ul class="nav nav-tabs">
		 <li class="active"><a href="${ctx}/ams/amsFileInfo/amsProjectInfoList?id=${amsProjectInfo.id}">项目信息</a></li>
		 <%-- <shiro:hasPermission name="ams:amsFileInfo:edit"><li><a href="${ctx}/ams/amsFileInfo/form?projectId=${amsProjectInfo.id}">资料档案添加</a></li></shiro:hasPermission> --%>
<%-- 	 	 <shiro:hasPermission name="ams:amsFileInfo:edit"><li><a href="${ctx}/ams/amsFileInfo/form?projectId=${amsProjectInfo.id}&recordId=-1">声像档案添加</a></li></shiro:hasPermission>
 --%>	 	</ul>
		<sys:message content="${message}" />
		<br/>
		<div class="accordion-group">
			<div class="accordion-inner">
					<form:form modelAttribute="amsProjectInfo" class="form-horizontal">
						<input type="hidden" id="id" value="${id}">
						<table class="table-form">
						<tr>
						<td align="center" colspan="6"><b>项目基本信息</b></td>
						</tr>
						<tr>
						<td class="tit" width="15%">项目名称：</td>
						<td colspan="2">${amsProjectInfo.projectName} </td>
						<td class="tit" width="15%">项目地址：</td>
						<td colspan="2">${amsProjectInfo.address} </td>
						</tr>
						<tr>
						<td class="tit" width="15%">项目编号：</td>
						<td >${amsProjectInfo.projectNo}  </td>
						<td class="tit" width="15%">项目类别：</td>
						<td>${fns:getDictLabel(amsProjectInfo.projectType, 'unit_project_type', '')}</td>
						<td class="tit" width="15%">用地许可证号：</td>
						<td>${amsProjectInfo.landLicenseNumber}</td>
						</tr>
						</table>
					</form:form>
			</div>
		</div>
		<div class="accordion-group">
			<div class="accordion-heading">
				<a class="accordion-toggle" data-toggle="collapse"
					data-parent="#accordion2" href="#collapseOne"> 点击查看-单位工程信息 </a>
			</div>
			<div id="collapseOne" class="accordion-body collapse in">
				<div class="accordion-heading">
					<table id="contentTable"
						class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th>单位工程编号</th>
								<th>单位工程名称</th>
								<th>单位工程类型</th>
								<th>施工许可证号</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${page.list}" var="unitProject">
								<tr>
									<td>${unitProject.unitProjectNo}</td>
									<td>${unitProject.unitProjectName}</td>
									<td>${fns:getDictLabel(unitProject.unitProjectType, 'unit_project_type', '')}</td>
									<td>${unitProject.constructionPermitNumber}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<div class="pagination">${page}</div>
				</div>
			</div>
		</div>
		<div class="accordion-group">
			<div class="accordion-heading">
				<a class="accordion-toggle" data-toggle="collapse"
					data-parent="#accordion3" href="#collapseTwo"> 点击查看-文件信息列表 </a>
			</div>
		<div id="collapseTwo" class="accordion-body collapse in ">
		<div class="accordion-inner">
		<form:form id="searchForm" modelAttribute="amsFileInfo" action="${ctx}/ams/amsFileInfo/amsProjectInfoList?id=${amsProjectInfo.id}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label style="margin-left:-80px;">文件题名：</label>
				<form:input path="fileName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li>
				是否上传：<form:select id="ifUpload" path="exten5" class="input-medium required">
					<form:option value="0"  label="全部"/>
					<form:option value="1"  label="已上传"/>
					<form:option value="2"  label="未上传"/> 
				</form:select> 	
			</li>
			<%-- <li>
			<label style="margin-left:-40px;">资料档案</label><form:radiobutton path="recordId"  checked="checked" value="0" />
			</li>
			<li>
			<label style="margin-left:-40px;">声像档案</label><form:radiobutton path="recordId" aa="record" value="-1" />
			</li> --%>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<table id="treeTable" class="table  table-bordered table-condensed">
		<thead>
			<tr>
				<th>类别编码</th>
				<th>名称</th>
				<th>备注信息</th>
				<th width="5%">文件管理</th>
			</tr>
		</thead>
		<tbody id="treeTableList"></tbody>
	</table>
	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.id}}" pId="{{pid}}"   >
			<td>
				{{row.code}}
			</td>
			<td>
				{{row.name}}
			</td>
			
			<td>
				{{row.remarks}}
			</td>
			<shiro:hasPermission name="ams:amsFileInfo:edit">
			<td>
				<a style="display:none" id="a{{row.id}}" href="${ctx}/ams/amsFileInfo/proList?projectId=${amsProjectInfo.id}&recordId={{row.id}}">文件管理</a> 
			</td>
			</shiro:hasPermission>
		</tr>
	</script></div>
	</div>
	</div>
	</c:if> 
</body>
</html>