<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>文件管理</title>
<meta name="decorator" content="default" />
<%@include file="/WEB-INF/views/include/treeview.jsp"%>
<script type="text/javascript">
	
$(document).ready(function() {
	
});
function page(n,s){
	$("#pageNo").val(n);
	$("#pageSize").val(s);
	$("#searchForm").submit();
	return false;
}
</script>

</head>
<body>
	<div class="accordion" id="accordion2">
		<c:if test="${empty amsProjectInfo.id}">
		<%-- <label class="control-label"> ${message}</label> --%>
		<script type="text/javascript">top.$.jBox.tip("项目不存在！");
		</script>
		</c:if>
		<c:if test="${not empty amsProjectInfo.id}">
		<ul class="nav nav-tabs">
		 	<li class="active"><a href="${ctx}/ams/amsArchivesInfo/amsProjectInfoList?id=${id}">项目信息</a></li>
		 	<c:if test="${sysBaseInfo.archivesType==1}">
			<shiro:hasPermission name="ams:amsArchivesInfo:edit"><li><a href="${ctx}/ams/amsArchivesInfo/formAdd?projectId=${amsProjectInfo.id}">案卷添加</a></li></shiro:hasPermission>
			</c:if>
			<%-- <shiro:hasPermission name="ams:amsArchivesInfo:edit"><li><a href="${ctx}/ams/amsArchivesInfo/formAdd?projectId=${amsProjectInfo.id}&exten1=1">声像案卷添加</a></li></shiro:hasPermission> --%>
		</ul>
		<sys:message content="${message}" />
		<br/>
		<%-- <div class="accordion-group">
			<div class="accordion-inner">
					<form:form modelAttribute="amsProjectInfo" class="form-horizontal">
						<input type="hidden" id="id" value="${id}">
						<table class="table-form">
						<tr>
						<td align="center" colspan="4" width="20%"><b>项目基本信息</b></td>
						</tr>
						<tr>
						<td class="tit" width="20%">项目名称:</td>
						<td colspan="3">${amsProjectInfo.projectName} </td>
						</tr>
						<tr>
						<td class="tit" width="20%">项目编号:</td>
						<td colspan="3">${amsProjectInfo.projectNo}  </td>
						</tr>
						<tr>
						<td class="tit" width="20%">项目地址:</td>
						<td colspan="3">${amsProjectInfo.address} </td>
						</tr>
						<tr>
						<td class="tit" width="20%">项目类别:</td>
						<td>${fns:getDictLabel(amsProjectInfo.projectType, 'unit_project_type', '')}</td>
						<td class="tit" width="20%">用地许可证号:</td>
						<td>${amsProjectInfo.landLicenseNumber}</td>
						</tr>
						</table>
					</form:form>
			</div>
		</div> --%>

		<c:if test="${sysBaseInfo.archivesType==1}">
		<div class="accordion-group">
			<div class="accordion-heading">
				<a class="accordion-toggle" data-toggle="collapse"
					data-parent="#accordion2" href="#collapseTwo"> 点击查看-案卷信息列表 </a>
			</div>
		<div id="collapseTwo" class="accordion-body collapse in">
		<div class="accordion-inner">
		<form:form id="searchForm" modelAttribute="amsArchivesInfo" action="${ctx}/ams/amsArchivesInfo/amsProjectInfoList?id=${amsProjectInfo.id}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>案卷题名：</label>
				<form:input path="archivesName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>案卷题名</th>
				<th>编制单位</th>
				<th>载体类型</th>				
				<th>立卷人</th>
				<th>立卷日期</th>
				<!-- <th>顺序号</th> -->
				<shiro:hasPermission name="ams:amsArchivesInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${pageArch.list}" var="amsArchivesInfo">
			<tr>
				<td><a href="${ctx}/ams/amsArchivesInfo/formAdd?id=${amsArchivesInfo.id}&amsAcceptance.status=${amsArchivesInfo.amsAcceptance.status}">
					${amsArchivesInfo.archivesName}
				</a></td>
				<td>${amsArchivesInfo.makeUnit}</td>
				<td>${fns:getDictLabel(amsArchivesInfo.carrierType, 'carrier_type', '')}</td>				
				<td>${amsArchivesInfo.author}</td>
				<td><fmt:formatDate value="${amsArchivesInfo.makeDate}" pattern="yyyy-MM-dd"/></td>
				<%-- <td>${fns:getDictLabel(amsArchivesInfo.fileType, 'file_type', '')}</td>
				<td>${amsArchivesInfo.remarks}</td> --%>
				<shiro:hasPermission name="ams:amsArchivesInfo:edit"><td>
    				<%-- <a href="${ctx}/ams/amsArchivesInfo/form?id=${amsArchivesInfo.id}&unitProjectId=${unitProjectId}&recordId=${amsArchivesInfo.recordId}">${not empty amsArchivesInfo.id?'著录':'添加'}</a> --%>
    				<c:if test="${amsArchivesInfo.amsAcceptance.status == null}">
						<a href="${ctx}/ams/amsArchivesInfo/delete?id=${amsArchivesInfo.id}" onclick="return confirmx('确认要删除该案卷吗？', this.href)">${not empty amsArchivesInfo.id?'删除':''}</a>
					</c:if>
					<c:if test="${amsArchivesInfo.amsAcceptance.status == 1 || amsArchivesInfo.amsAcceptance.status == 2 || amsArchivesInfo.amsAcceptance.status == 3}">
						预验收中
					</c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${pageArch}</div>
	</div>
	</div>
	</div>
	</c:if>
		</c:if> 
	</div>
</body>
</html>