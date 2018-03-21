<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>单位工程管理</title>
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
		<%-- <li ><a href="${ctx}/ams/amsProjectInfo/">工程项目列表</a></li> --%>
		<li class="active"><a href="${ctx}/ams/amsUnitProInfo/?projectId=${projectid}">单位工程列表</a></li>
		<shiro:hasPermission name="ams:amsUnitProInfo:edit"><li><a href="${ctx}/ams/amsUnitProInfo/form?projectId=${projectid}">单位工程添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="amsUnitProInfo" action="${ctx}/ams/amsUnitProInfo/list?projectId=${projectid}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>单位工程名称：</label>
				<form:input path="unitProjectName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>单位工程类型：</label>
				<form:select path="unitProjectType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getAmsDictList('unit_project_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>	
			<li style="display: none;"><label>规划许可证号：</label>
				<form:input path="planningLicenseNumber" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>施工许可证号：</label>
				<form:input path="constructionPermitNumber" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit"  class="btn btn-primary" type="submit" value="查询"  /></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>单位工程名称</th>
				<th>单位工程类型</th>
				<th>开工日期</th>
			
				<th style="display: none;">规划许可证号</th>
				<th>施工许可证号</th>
				<th>备注信息${status}</th>
				<shiro:hasPermission name="ams:amsUnitProInfo:edit"><th>操作</th></shiro:hasPermission>
				<th>详细信息</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="amsUnitProInfo">
			<tr>
				<td>
					<c:if test="${amsUnitProInfo.amsAcceptance.status == null ||amsUnitProInfo.amsAcceptance.status == 0  || amsUnitProInfo.amsAcceptance.status == -2|| amsUnitProInfo.amsAcceptance.status == -3 }">
						<a href="${ctx}/ams/amsUnitProInfo/form?id=${amsUnitProInfo.id}">${amsUnitProInfo.unitProjectName}</a>
    				</c:if>
    				<c:if test="${amsUnitProInfo.amsAcceptance.status == 3 || amsUnitProInfo.amsAcceptance.status == 2 || amsUnitProInfo.amsAcceptance.status == 1}">
    				<a href="${ctx}/ams/amsUnitProInfo/form?id=${amsUnitProInfo.id}&amsAcceptance.status=${amsUnitProInfo.amsAcceptance.status}">${amsUnitProInfo.unitProjectName}</a>
    				</c:if>
				</td>
		
				
				<td>
					${fns:getAmsDictLabel(amsUnitProInfo.unitProjectType, 'unit_project_type', '')}
				</td>
				<td>
					<fmt:formatDate value="${amsUnitProInfo.startDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td style="display: none;">
					${amsUnitProInfo.planningLicenseNumber}
				</td>
				<td>
					${amsUnitProInfo.constructionPermitNumber}
				</td>
				<td>
					${amsUnitProInfo.remarks} 
				</td>
				
				<shiro:hasPermission name="ams:amsUnitProInfo:edit"><td>
				
					<c:if test="${amsUnitProInfo.amsAcceptance.status == ''||amsUnitProInfo.amsAcceptance.status == null ||amsUnitProInfo.amsAcceptance.status == 0  || amsUnitProInfo.amsAcceptance.status == -2|| amsUnitProInfo.amsAcceptance.status == -3 }"><a href="${ctx}/ams/amsUnitProInfo/form?id=${amsUnitProInfo.id}" onclick= disabled >修改</a> </c:if>
    				<c:if test="${amsUnitProInfo.amsAcceptance.status == 3 || amsUnitProInfo.amsAcceptance.status == 2 || amsUnitProInfo.amsAcceptance.status == 1}">修改</c:if>
    				
    				
    				 <c:if test="${amsUnitProInfo.amsAcceptance.status == ''||amsUnitProInfo.amsAcceptance.status == null ||amsUnitProInfo.amsAcceptance.status == 0  || amsUnitProInfo.amsAcceptance.status == -2|| amsUnitProInfo.amsAcceptance.status == -3 }"><a href="${ctx}/ams/amsUnitProInfo/delete?id=${amsUnitProInfo.id}" onclick="return confirmx('确认要删除该单位工程吗？', this.href)">删除</a>	</c:if>
	   				 <c:if test="${amsUnitProInfo.amsAcceptance.status == 3 || amsUnitProInfo.amsAcceptance.status == 2 || amsUnitProInfo.amsAcceptance.status == 1}">删除</c:if>
 				</td></shiro:hasPermission>
 				
				<td>
				<a href="${ctx}/ams/amsUnitProInfo/majorForm?id=${amsUnitProInfo.id}&&status=${amsUnitProInfo.amsAcceptance.status}">专业记载</a> 
				</td>
				
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<div class="pagination">${page}</div>
</body>
</html>