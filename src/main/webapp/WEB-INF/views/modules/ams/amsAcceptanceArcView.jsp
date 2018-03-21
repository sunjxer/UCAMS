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
							<td>${amsArchivesInfo.archivesName} </td>
						
							<td class="tit" width="20%">编制单位:</td>
							<td>${amsArchivesInfo.makeUnit} </td>
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
					</form:form>
				</div>
				<div class="accordion-group">
			<div class="accordion-heading">
				<a class="accordion-toggle" data-toggle="collapse"
					data-parent="#accordion2" href="#collapseOne"> 文件列表 </a>
			</div>
			<div id="collapseOne" class="accordion-body collapse in ">
				<div class="accordion-inner">
				<form:form id="saveSortForm" modelAttribute="amsArchivesInfo" action="${ctx}/ams/amsArchivesInfo/saveSort?id=${amsArchivesInfo.id}&projectId=${amsArchivesInfo.projectId}" method="post" class="breadcrumb form-search">
					<table id="contentTable"
						class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th>序号</th>
								<th>文件名</th>
								<th>文（图）号</th>
								<c:if test="${amsArchivesInfo.exten1.equals('0')}">
								<th>实体页数</th>								
								<th>文件类型</th>
								<th>页号</th>
								</c:if>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${pageIn.list}" var="amsFileInfo" varStatus="status">
								<tr>
									<td id="tdId" style="display: none">${amsFileInfo.amsArchivesFiles.id}</td>
									<td>${status.index +1}</td>
									<td>
									<%-- <a href="${ctx}/ams/amsFileInfo/formView?id=${amsFileInfo.id}&unitProjectId=${amsFileInfo.unitProjectId}&recordId=${amsFileInfo.recordId}">
										${amsFileInfo.fileName}
									</a> --%>
										${amsFileInfo.fileName}
									</td>
									<td>${amsFileInfo.fileNo}</td>
									<c:if test="${amsArchivesInfo.exten1.equals('0')}">
									<td>${amsFileInfo.filecount}</td>									
									<td>${fns:getDictLabel(amsFileInfo.fileType, 'file_type', '')}</td>
									<td>
									${amsFileInfo.amsArchivesFiles.startPage}
									</td>
									</c:if>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</form:form>
				<form:form id="searchForm" modelAttribute="amsArchivesInfo" action="${ctx}/ams/amsArchivesInfo/amsAcceptanceArcView?id=${amsArchivesInfo.id}" method="post" >
		<input id="pageNo" name="pageNo" type="hidden" value="${pageIn.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${pageIn.pageSize}"/>
		
	</form:form>
					<div class="pagination">${pageIn}</div>
				</div>
			</div>
		</div>
		</div>
	</c:if>
</body>
</html>