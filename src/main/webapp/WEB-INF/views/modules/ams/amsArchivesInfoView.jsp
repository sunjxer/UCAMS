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
	<c:if test="${empty amsArchivesInfo.id}">
	<%-- <label class="control-label"> ${message}</label> --%>
	<script type="text/javascript">top.$.jBox.tip("案卷不存在！");
	</script>
	</c:if>
	<c:if test="${not empty amsArchivesInfo.id}">
	 <ul class="nav nav-tabs">
	 	<li class="active"><a href="${ctx}/ams/amsArchivesInfo/amsArchivesInfoList?id=${amsArchivesInfo.id}">案卷信息</a></li>
	 	<shiro:hasPermission name="ams:amsArchivesInfo:edit">
	 	<li><a href="${ctx}/ams/amsArchivesInfo/list?id=${amsArchivesInfo.id}&projectId=${amsArchivesInfo.projectId}">文件列表</a></li>
	 	</shiro:hasPermission>
		<%-- <shiro:hasPermission name="ams:amsArchivesInfo:edit">
		<li><a href="${ctx}/ams/amsArchivesInfo/formAdd?unitProjectId=${amsUnitProInfo.id}&projectId=${amsUnitProInfo.projectId}">案卷添加</a></li></shiro:hasPermission> --%>
	</ul>
	<sys:message content="${message}" />
		<div class="accordion-group">
				<div class="accordion-inner">
					<form:form modelAttribute="amsArchivesInfo" class="form-horizontal">
						<sys:message content="${message}" />
						<input type="hidden" id="id" value="${id}">
						<table class="table-form">
						<tr>
							<td align="center" colspan="4" width="20%"><b>案卷基本信息</b></td>
						</tr>
						<tr>
							<td class="tit" width="20%">案卷题名:</td>
							<td colspan="3">${amsArchivesInfo.archivesName} </td>
						</tr>
						<tr>
							<td class="tit" width="20%">编制单位:</td>
							<td colspan="3">${amsArchivesInfo.makeUnit} </td>
						</tr>
						<tr>
							<td class="tit" width="20%">载体类型:</td>
							<td>${fns:getDictLabel(amsArchivesInfo.carrierType, 'carrier_type', '')}</td>
							<td class="tit" width="20%">数量:</td>
							<td>${amsArchivesInfo.filesCount}  </td>							
						</tr>
						<tr>
							<td class="tit" width="20%">卷内文件起始时间:</td>
							<td><fmt:formatDate value="${amsArchivesInfo.startDate}" pattern="yyyy-MM-dd"/></td>
							<td class="tit" width="20%">卷内文件终止时间:</td>
							<td><fmt:formatDate value="${amsArchivesInfo.endDate}" pattern="yyyy-MM-dd"/></td>
						</tr>
						<tr>
							<td class="tit" width="20%">保管期限:</td>
							<td>${fns:getDictLabel(amsArchivesInfo.storagePeriod, 'storage_period', '')}</td>
							<td class="tit" width="20%">密级:</td>
							<td>${fns:getDictLabel(amsArchivesInfo.degreeSecrets, 'degree_secrets', '')}</td>
						</tr>
						<tr>
							<td class="tit" width="20%">主题词:</td>
							<td colspan="3">${amsArchivesInfo.mainTitle} </td>
						</tr>
						<tr>
							<td class="tit" width="20%">附注:</td>
							<td colspan="3">${amsArchivesInfo.archivesExplain} </td>
						</tr>
						<tr>
							<td class="tit" width="20%">立卷人:</td>
							<td>${amsArchivesInfo.author} </td>
							<td class="tit" width="20%">立卷日期:</td>
							<td><fmt:formatDate value="${amsArchivesInfo.makeDate}" pattern="yyyy-MM-dd"/></td>
						</tr>
						<tr>
							<td class="tit" width="20%">审核人:</td>
							<td>${amsArchivesInfo.auditor} </td>
							<td class="tit" width="20%">审核日期:</td>
							<td><fmt:formatDate value="${amsArchivesInfo.auditDate}" pattern="yyyy-MM-dd"/></td>
						</tr>
						</table>
						<a href="${ctx}/ams/amsArchivesInfo/exploadCover?id=${amsArchivesInfo.id}">导出</a>
					</form:form>
				</div>
			<!-- </div> -->
		</div>
		<%--<div class="accordion-group">
			<div class="accordion-heading">
				<a class="accordion-toggle" data-toggle="collapse"
					data-parent="#accordion2" href="#collapseOne"> 点击查看-案卷信息列表 </a>
			</div>
		<div id="collapseOne" class="accordion-body collapse in">
		<div class="accordion-inner">
		<form:form id="searchForm" modelAttribute="amsArchivesInfo" action="${ctx}/ams/amsArchivesInfo/amsUnitProInfoList?id=${unitProjectId}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>文件题名：</label>
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
		<c:forEach items="${page.list}" var="amsArchivesInfo">
			<tr>
				<td><a href="${ctx}/ams/amsArchivesInfo/form?id=${amsArchivesInfo.id}">
					${amsArchivesInfo.archivesName}
				</a></td>
				<td>${amsArchivesInfo.makeUnit}</td>
				<td>${fns:getDictLabel(amsArchivesInfo.carrierType, 'file_type', '')}</td>				
				<td>${amsArchivesInfo.author}</td>
				<td><fmt:formatDate value="${amsArchivesInfo.makeDate}" pattern="yyyy-MM-dd"/></td>
				<td>${fns:getDictLabel(amsArchivesInfo.fileType, 'file_type', '')}</td>
				<td>${amsArchivesInfo.remarks}</td>
				<shiro:hasPermission name="ams:amsArchivesInfo:edit"><td>
    				<a href="${ctx}/ams/amsArchivesInfo/form?id=${amsArchivesInfo.id}&unitProjectId=${unitProjectId}&recordId=${amsArchivesInfo.recordId}">${not empty amsArchivesInfo.id?'著录':'添加'}</a>
					<a href="${ctx}/ams/amsArchivesInfo/delete?id=${amsArchivesInfo.id}" onclick="return confirmx('确认要删除该文件吗？', this.href)">${not empty amsArchivesInfo.id?'删除':''}</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	</div>
	</div>
	</div> --%>
	</c:if>
</body>
</html>