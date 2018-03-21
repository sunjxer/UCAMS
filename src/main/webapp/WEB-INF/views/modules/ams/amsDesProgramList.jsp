<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>工程专业配置记录管理</title>
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
		<li class="active"><a href="${ctx}/ams/amsDesProgram/">工程专业配置记录列表</a></li>
		<shiro:hasPermission name="ams:amsDesProgram:edit"><li><a href="${ctx}/ams/amsDesProgram/form">工程专业配置记录添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="amsDesProgram" action="${ctx}/ams/amsDesProgram/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>单位工程类型：</label>
				<form:select path="unitProjectType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getAmsDictList('unit_project_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>方案类型</th>
				<th>单位工程类型</th>
				<th>名称</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<th>运行状态</th>
				<shiro:hasPermission name="ams:amsDesProgram:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="amsDesProgram">
			<tr>
				<td>${fns:getAmsDictLabel(amsDesProgram.programType, 'ams_plan_type', '无')}</td>
				<td>
					${fns:getAmsDictLabel(amsDesProgram.unitProjectType, 'unit_project_type', '')}
				</td>
				<td><a href="${ctx}/ams/amsDesProgram/form?id=${amsDesProgram.id}">
					${amsDesProgram.name}
				</a></td>
				<td>
					<fmt:formatDate value="${amsDesProgram.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${amsDesProgram.remarks}
				</td>
				<td>
				<c:choose> 
				  <c:when test="${amsDesProgram.useable == '0'}">   
				   <label style="color: red">禁用中..</label>
				  </c:when> 
				  <c:when test="${amsDesProgram.useable== '1'}">   
				   <label style="color: green">启用中..</label>
				  </c:when> 
				  <c:otherwise>   
				    error
				  </c:otherwise> 
				</c:choose> 
				</td>
				<shiro:hasPermission name="ams:amsDesProgram:edit"><td>
				<c:choose> 
				  <c:when test="${amsDesProgram.useable == '0'}">   
				  <a href="${ctx}/ams/amsDesProgram/changeAble?id=${amsDesProgram.id}&flag=0"  onclick="return confirmx('该专业下的其他方案讲被置为[ 禁用 ]，确认要启用该方案吗？', this.href)">启用</a>
				  </c:when> 
				  <c:when test="${amsDesProgram.useable== '1'}">   
				    <a href="${ctx}/ams/amsDesProgram/changeAble?id=${amsDesProgram.id}&flag=1">禁用</a>
				  </c:when> 
				  <c:otherwise>   
				    error
				  </c:otherwise> 
				</c:choose> 
    				<a href="${ctx}/ams/amsDesProgram/form?id=${amsDesProgram.id}">修改</a>
					<a href="${ctx}/ams/amsDesProgram/delete?id=${amsDesProgram.id}" onclick="return confirmx('确认要删除该工程专业配置记录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>