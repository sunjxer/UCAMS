<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>建设工程规划管理</title>
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
		<li class="active"><a href="${ctx}/ams/amsConstructDes/">建设工程规划列表</a></li>
		<shiro:hasPermission name="ams:amsConstructDes:edit"><li><a href="${ctx}/ams/amsConstructDes/form">建设工程规划添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="amsConstructDes" action="${ctx}/ams/amsConstructDes/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>工程名称：</label>
				<form:input path="projectName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>用地许可证号：</label>
				<form:input path="landPermitNumber" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>工程名称</th>
				<th>工程地点</th>
				<th>建设单位</th>
				<th>立项批准单位</th>
				<th>设计单位</th>
				<th>施工单位</th>
				<th>立项批准文号</th>
				<th>规划许可证号</th>
				<th>用地规划许可证号</th>
				<th>用地许可证号</th>
				<th>地形图号</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="ams:amsConstructDes:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="amsConstructDes">
			<tr>
				<td><a href="${ctx}/ams/amsConstructDes/form?id=${amsConstructDes.id}">
					${amsConstructDes.projectName}
				</a></td>
				<td>
					${amsConstructDes.address}
				</td>
				<td>
					${amsConstructDes.constructionUnit}
				</td>
				<td>
					${amsConstructDes.projectApprovalUnit}
				</td>
				<td>
					${amsConstructDes.designUnit}
				</td>
				<td>
					${amsConstructDes.prospectingUnit}
				</td>
				<td>
					${amsConstructDes.approvalNumber}
				</td>
				<td>
					${amsConstructDes.planningLicenseNumber}
				</td>
				<td>
					${amsConstructDes.landLicenseNumber}
				</td>
				<td>
					${amsConstructDes.landPermitNumber}
				</td>
				<td>
					${amsConstructDes.topographicMap}
				</td>
				<td>
					<fmt:formatDate value="${amsConstructDes.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${amsConstructDes.remarks}
				</td>
				<shiro:hasPermission name="ams:amsConstructDes:edit"><td>
    				<a href="${ctx}/ams/amsConstructDes/form?id=${amsConstructDes.id}">修改</a>
					<a href="${ctx}/ams/amsConstructDes/delete?id=${amsConstructDes.id}" onclick="return confirmx('确认要删除该建设工程规划吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>