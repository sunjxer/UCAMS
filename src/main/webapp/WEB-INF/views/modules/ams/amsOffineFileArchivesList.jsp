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
	<div class="accordion-inner">
	<form:form id="searchForm" action="${ctx}/ams/amsInterface/archivesList" method="post" class="breadcrumb form-search">
		<input id="unitProjectId" name="unitProjectId" type="hidden" value="${unitProjectId}"/>
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
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
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="amsArchivesInfo">
			<tr>
				<td><a href="${ctx}/ams/amsInterface/toPreviewFile?groupId=${amsArchivesInfo.id}" target="_Blank">
					${amsArchivesInfo.archivesName}
				</a></td>
				<td>${amsArchivesInfo.makeUnit}</td>
				<td>${fns:getDictLabel(amsArchivesInfo.carrierType, 'carrier_type', '')}</td>				
				<td>${amsArchivesInfo.author}</td>
				<td><fmt:formatDate value="${amsArchivesInfo.makeDate}" pattern="yyyy-MM-dd"/></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	</div>
</body>
</html>