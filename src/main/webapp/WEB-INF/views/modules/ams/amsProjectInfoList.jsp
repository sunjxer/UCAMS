<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>工程项目管理</title>
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
		<li class="active"><a href="${ctx}/ams/amsProjectInfo/">工程项目列表</a></li>
		<shiro:hasPermission name="ams:amsProjectInfo:edit"><li><a href="${ctx}/ams/amsProjectInfo/form">工程项目添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="amsProjectInfo" action="${ctx}/ams/amsProjectInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>项目类别：</label>
				<form:select path="projectType" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getAmsDictList('unit_project_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>项目名称：</label>
				<form:input path="projectName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>用地许可证号：</label>
				<form:input path="landPermitNumber" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>审核状态：</label>
				<form:select path="checkStatus" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getAmsDictList('project_checkStatus')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>业务指导员：</label>
				<form:input path="businessMan" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>建设规划许可证号：</label>
				<form:input path="planningLicenseNumber" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
			<c:if test="${jsUnitId==''}">
				<th>建设单位</th>
			</c:if>
				<th>项目类别</th>
				<th>项目名称</th>
				<th>用地许可证号</th>
				<th>建设规划许可证号</th>
				<th>开工日期</th>
				<th>竣工日期</th>
				<th>业务指导员</th>
				<th>审核状态</th>
				<shiro:hasPermission name="ams:amsProjectInfo:edit"><th>操作</th></shiro:hasPermission>
				<th>拓展信息</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="amsProjectInfo">
			<tr>
			<c:if test="${jsUnitId==''}">
				<td>
					${amsProjectInfo.role.name}
				</td>
			</c:if>
				<td>
					${fns:getAmsDictLabel(amsProjectInfo.projectType, 'unit_project_type', '')}
				</td>
				<td><a href="${ctx}/ams/amsProjectInfo/form?id=${amsProjectInfo.id}">
					${amsProjectInfo.projectName}</a>
				</td>
				<td>
					${amsProjectInfo.landPermitNumber}
				</td>
				<td>
					${amsProjectInfo.planningLicenseNumber}
				</td>
				<td>
					<fmt:formatDate value="${amsProjectInfo.startDate}" pattern="yyyy-MM-dd "/>
				</td>
				<td>
					<fmt:formatDate value="${amsProjectInfo.finishDate}" pattern="yyyy-MM-dd "/>
				</td>
				<td>
					${amsProjectInfo.user.name}
				</td>
				<td>
					${fns:getAmsDictLabel(amsProjectInfo.checkStatus, 'project_checkStatus', '')}	
				</td>
				<shiro:hasPermission name="ams:amsProjectInfo:edit"><td>
    				<c:if test="${amsProjectInfo.checkStatus =='0'}">
    					<a href="${ctx}/ams/amsProjectInfo/form?id=${amsProjectInfo.id}">等待审核,查看</a>
    				</c:if>
    				<c:if test="${amsProjectInfo.checkStatus =='2'}">
    				驳回
    				</c:if>
    				<c:if test="${amsProjectInfo.checkStatus =='1'}">
    				通过审核
    				</c:if>
				</td></shiro:hasPermission>
				<td>
				<a href="${ctx}/ams/amsProjectInfo/majorForm?id=${amsProjectInfo.id}&&status=${amsUnitProInfo.amsAcceptance.status}">项目信息拓展</a> 
				</td>
				
			
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>