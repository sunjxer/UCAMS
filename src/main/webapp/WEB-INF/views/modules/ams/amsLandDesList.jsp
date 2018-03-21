<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>建设用地规划管理</title>
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
		<li class="active"><a href="${ctx}/ams/amsLandDes/">建设用地规划列表</a></li>
		<shiro:hasPermission name="ams:amsLandDes:edit"><li><a href="${ctx}/ams/amsLandDes/form">建设用地规划添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="amsLandDes" action="${ctx}/ams/amsLandDes/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用地项目名称：</label>
				<form:input path="projectName" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>项目主键</th>
				<th>用地项目名称</th>
				<th>征地位置</th>
				<th>被征单位</th>
				<th>地形图号</th>
				<th>征拨分类</th>
				<th>批准时间</th>
				<th>用地面积</th>
				<th>备注信息</th>
				<shiro:hasPermission name="ams:amsLandDes:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="amsLandDes">
			<tr>
				<td><a href="${ctx}/ams/amsLandDes/form?id=${amsLandDes.id}">
					${amsLandDes.projectId}
				</a></td>
				<td>
					${amsLandDes.projectName}
				</td>
				<td>
					${amsLandDes.address}
				</td>
				<td>
					${amsLandDes.expropriatedUnit}
				</td>
				<td>
					${amsLandDes.topographicMap}
				</td>
				<td>
					${amsLandDes.expropriation}
				</td>
				<td>
					<fmt:formatDate value="${amsLandDes.approvalDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${amsLandDes.landArea}
				</td>
				<td>
					${amsLandDes.remarks}
				</td>
				<shiro:hasPermission name="ams:amsLandDes:edit"><td>
    				<a href="${ctx}/ams/amsLandDes/form?id=${amsLandDes.id}">修改</a>
					<a href="${ctx}/ams/amsLandDes/delete?id=${amsLandDes.id}" onclick="return confirmx('确认要删除该建设用地规划吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>