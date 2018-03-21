<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>移交案卷管理</title>
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
		<li class="active"><a href="${ctx}/ams/amsTransferArchives/">移交案卷列表</a></li>
		<shiro:hasPermission name="ams:amsTransferArchives:edit"><li><a href="${ctx}/ams/amsTransferArchives/form">移交案卷添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="amsTransferArchives" action="${ctx}/ams/amsTransferArchives/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>项目主键</th>
				<th>移交主键</th>
				<th>预验收主键</th>
				<th>单位工程主键</th>
				<th>案卷主键</th>
				<th>备注信息</th>
				<shiro:hasPermission name="ams:amsTransferArchives:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="amsTransferArchives">
			<tr>
				<td><a href="${ctx}/ams/amsTransferArchives/form?id=${amsTransferArchives.id}">
					${amsTransferArchives.projectid}
				</a></td>
				<td>
					${amsTransferArchives.transferId}
				</td>
				<td>
					${amsTransferArchives.acceptanceId}
				</td>
				<td>
					${amsTransferArchives.unitProjectId}
				</td>
				<td>
					${amsTransferArchives.archiveId}
				</td>
				<td>
					${amsTransferArchives.remarks}
				</td>
				<shiro:hasPermission name="ams:amsTransferArchives:edit"><td>
    				<a href="${ctx}/ams/amsTransferArchives/form?id=${amsTransferArchives.id}">修改</a>
					<a href="${ctx}/ams/amsTransferArchives/delete?id=${amsTransferArchives.id}" onclick="return confirmx('确认要删除该移交案卷吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>