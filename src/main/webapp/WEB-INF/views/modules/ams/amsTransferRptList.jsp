<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>移交检查报告管理</title>
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
		<li class="active"><a href="${ctx}/ams/amsTransferRpt/">移交检查报告列表</a></li>
		<shiro:hasPermission name="ams:amsTransferRpt:edit"><li><a href="${ctx}/ams/amsTransferRpt/form">移交检查报告添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="amsTransferRpt" action="${ctx}/ams/amsTransferRpt/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>移交id：</label>
				<form:input path="transferId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="ams:amsTransferRpt:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="amsTransferRpt">
			<tr>
				<td><a href="${ctx}/ams/amsTransferRpt/form?id=${amsTransferRpt.id}">
					<fmt:formatDate value="${amsTransferRpt.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${amsTransferRpt.remarks}
				</td>
				<shiro:hasPermission name="ams:amsTransferRpt:edit"><td>
    				<a href="${ctx}/ams/amsTransferRpt/form?id=${amsTransferRpt.id}">修改</a>
					<a href="${ctx}/ams/amsTransferRpt/delete?id=${amsTransferRpt.id}" onclick="return confirmx('确认要删除该移交检查报告吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>