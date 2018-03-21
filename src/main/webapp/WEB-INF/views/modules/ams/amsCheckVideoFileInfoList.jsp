<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>文件管理</title>
	<meta name="decorator" content="default"/>
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
	<ul class="nav nav-tabs">
		 <li ><a href="${ctx}/ams/amsCheckVideoFile/amsProjectInfoVideoList?id=${amsFileInfo.projectId}">声像档案列表</a></li>
		 <li class="active">
			<a href="${ctx}/ams/amsCheckVideoFile/fileInfoList?projectId=${amsFileInfo.projectId}&recordId=${amsFileInfo.recordId}">声像文件列表</a>
		</li>
	</ul>
	<form:form id="searchForm" modelAttribute="amsFileInfo" action="${ctx}/ams/amsCheckVideoFile/fileInfoList?projectId=${amsFileInfo.projectId}&recordId=${amsFileInfo.recordId}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>文件名：</label>
				<form:input path="fileName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>文件题名</th>
				<th>文件名</th>
				<th>文图号</th>
				<th>责任者</th>
				<th>形成日期</th>
				<!-- <th>文件份数</th>
				<th>文件类型</th> -->
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
				<td>
					${amsFileInfo.fileName}
				</td>
				<td>${amsFileInfo.fileNo}</td>
				<td>${amsFileInfo.author}</td>
				<td><fmt:formatDate value="${amsFileInfo.formDate}" pattern="yyyy-MM-dd"/></td>
				<td>${amsFileInfo.remarks}</td>
    			<a href="${ctx}/ams/amsFileInfo/formViewVideo?id=${amsFileInfo.id}&projectId=${amsFileInfo.projectId}&recordId=${amsFileInfo.recordId}">${not empty amsFileInfo.id?'查看与打印':''}</a>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>