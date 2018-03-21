<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>检查报告管理</title>
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
		<li class="active"><a href="${ctx}/ams/amsPreRpt/">检查报告列表</a></li>
		<shiro:hasPermission name="ams:amsPreRpt:edit"><li><a href="${ctx}/ams/amsPreRpt/form">检查报告添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="amsPreRpt" action="${ctx}/ams/amsPreRpt/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>预验收与移交ID：</label>
				<form:input path="transferId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>0-内容 不全（必填项未填），1-文件数量不符：</label>
				<form:input path="errorType" htmlEscape="false" maxlength="1" class="input-medium"/>
			</li>
			<li><label>位置：</label>
				<form:input path="opersion" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>预验收与移交ID</th>
				<th>0-内容 不全（必填项未填），1-文件数量不符</th>
				<th>错误内容</th>
				<th>位置</th>
				<th>0-正常错误，1-忽略，2-录入；打印报告时不打印状态为1的数据。</th>
				<shiro:hasPermission name="ams:amsPreRpt:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="amsPreRpt">
			<tr>
				<td><a href="${ctx}/ams/amsPreRpt/form?id=${amsPreRpt.id}">
					${amsPreRpt.transferId}
				</a></td>
				<td>
					${amsPreRpt.errorType}
				</td>
				<td>
					${amsPreRpt.error}
				</td>
				<td>
					${amsPreRpt.opersion}
				</td>
				<td>
					${amsPreRpt.state}
				</td>
				<shiro:hasPermission name="ams:amsPreRpt:edit"><td>
    				<a href="${ctx}/ams/amsPreRpt/form?id=${amsPreRpt.id}">修改</a>
					<a href="${ctx}/ams/amsPreRpt/delete?id=${amsPreRpt.id}" onclick="return confirmx('确认要删除该检查报告吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>