<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>文件管理</title>
<meta name="decorator" content="default" />
<%@include file="/WEB-INF/views/include/treeview.jsp"%>
<script type="text/javascript">
function page(n,s){
	$("#pageNo").val(n);
	$("#pageSize").val(s);
	$("#searchForm").submit();
	return false;
}
</script>
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
		function addRow(list, tpl, data, pid, root){
			for (var i=0; i<data.length; i++){
				var row = data[i];
				var idSelect=$("#ifUpload").val();
				if ((${fns:jsGetVal('row.parentId')}) == pid){
					/* $("#a"+pid).attr({ style: "display:none;"});
					$("#"+pid).attr({ style: "background-color:#f9f9f9;"});
					$(list).append(Mustache.render(tpl, {
						dict: {
						blank123:0}, pid: (root?0:pid), row: row
					}));
					addRow(list, tpl, data, row.id);
					if(row.isHaveFile!=''&&row.isHaveFile!=undefined){
						$("#"+row.id).attr({ style: "background-color:red;"});
					} */
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
					}
				}
			}
		}
	</script>

</head>
<body>
	<c:if test="${empty amsUnitProInfo.id}">
	<%-- <label class="control-label"> ${message}</label> --%>
	<script type="text/javascript">top.$.jBox.tip("单位工程不存在！");
	</script>
	</c:if>
	<c:if test="${not empty amsUnitProInfo.id}">
	 <ul class="nav nav-tabs">
	 <li class="active"><a href="${ctx}/ams/amsFileInfo/amsUnitProInfoList?id=${unitProjectId}">文件列表</a></li>
<%-- 	 <shiro:hasPermission name="ams:amsFileInfo:edit"><li><a href="${ctx}/ams/amsFileInfo/form?unitProjectId=${unitProjectId}">资料档案添加</a></li></shiro:hasPermission>
 <shiro:hasPermission name="ams:amsFileInfo:edit"><li><a href="${ctx}/ams/amsFileInfo/form?unitProjectId=${unitProjectId}&recordId=-1">声像档案添加</a></li></shiro:hasPermission>
 --%>		 </ul>
	<sys:message content="${message}" />
		<div class="accordion-group">
				<div class="accordion-inner">
					<form:form modelAttribute="amsUnitProInfo" class="form-horizontal">
						<sys:message content="${message}" />
						<input type="hidden" id="id" value="${id}">
						<table class="table-form">
						<tr>
						<td align="center" colspan="6"><b>单位工程基本信息</b></td>
						</tr>
						<tr>
						<td class="tit" width="15%">单位工程名称：</td>
						<td colspan="5">${amsUnitProInfo.unitProjectName} </td>
						
						</tr>
						<tr>
						<td class="tit" width="15%">单位工程编号：</td>
						<td colspan="5">${amsUnitProInfo.unitProjectNo} </td>
						</tr>
						<tr>
						<td class="tit" width="15%">施工许可证号：</td>
						<td colspan="5">${amsUnitProInfo.constructionPermitNumber}</td>
						</tr>
						</table>
					</form:form>
				</div>
			<!-- </div> -->
		</div>
		<div class="accordion-group">
			<div class="accordion-heading">
				<a class="accordion-toggle" data-toggle="collapse"
					data-parent="#accordion2" href="#collapseOne"> 点击查看-文件信息列表 </a>
			</div>
		<div id="collapseOne" class="accordion-body collapse in">
		<div class="accordion-inner">
		<form:form id="searchForm" modelAttribute="amsFileInfo" action="${ctx}/ams/amsFileInfo/amsUnitProInfoList?id=${unitProjectId}" method="post" class="breadcrumb form-search">
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
			<label  style="margin-left:-40px;">资料档案</label><form:radiobutton path="recordId" checked="checked" value="0" />
			</li>
			<li>
			<label style="margin-left:-40px;">声像档案</label><form:radiobutton path="recordId" aa="record"  value="-1" />
			</li> --%>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<%-- <sys:message content="${message}" />
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>文件题名</th>
				<th>文件名</th>
				<th>文图号</th>
				<th>责任者</th>
				<th>形成日期</th>
				<th>文件份数</th>
				<th>文件类型</th>
				<th>备注信息</th>
				<shiro:hasPermission name="ams:amsFileInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="amsFileInfo">
			<tr>
				<td>
					${amsFileInfo.recordFileName}
				</td>
				<td><a href="${ctx}/ams/amsFileInfo/form?id=${amsFileInfo.id}">
					${amsFileInfo.fileName}
				</a></td>
				<td>${amsFileInfo.fileNo}</td>
				<td>${amsFileInfo.author}</td>
				<td><fmt:formatDate value="${amsFileInfo.formDate}" pattern="yyyy-MM-dd"/></td>
				<td>${amsFileInfo.filecount}</td>
				<td>${fns:getDictLabel(amsFileInfo.fileType, 'file_type', '')}</td>
				<td>${amsFileInfo.remarks}</td>
				<shiro:hasPermission name="ams:amsFileInfo:edit"><td>
    				<a href="${ctx}/ams/amsFileInfo/form?id=${amsFileInfo.id}&unitProjectId=${unitProjectId}&recordId=${amsFileInfo.recordId}">${not empty amsFileInfo.id?'著录':'添加'}</a>
    				<a href="${ctx}/ams/amsFileInfo/formView?id=${amsFileInfo.id}&unitProjectId=${unitProjectId}&recordId=${amsFileInfo.recordId}">${not empty amsFileInfo.id?'查看与打印':''}</a>
					<a href="${ctx}/ams/amsFileInfo/delete?id=${amsFileInfo.id}" onclick="return confirmx('确认要删除该文件吗？', this.href)">${not empty amsFileInfo.id?'删除':''}</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination"><sys:message content="${message}"/> --%>
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
		<tr id="{{row.id}}" pId="{{pid}}" >
			<td>
				{{row.code}}
				{{row.remarks}}
			</td>
			<td>
				{{row.name}}
			</td>
			
			<td>
				{{row.remarks}}
			</td>
			<shiro:hasPermission name="ams:amsFileInfo:edit">
			<td>
				<a style="display:none" id="a{{row.id}}" href="${ctx}/ams/amsFileInfo/list?unitProjectId=${unitProjectId}&recordId={{row.id}}">文件管理</a> 
			</td>
			</shiro:hasPermission>
		</tr>
	</script></div>
	<%-- <div class="pagination">${page}</div> --%>
	</div>
	</div>
	</c:if>
</body>
</html>