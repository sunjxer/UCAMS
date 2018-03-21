<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>责任主体信息管理</title>
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
		<li class="active"><a href="${ctx}/ams/amsUnitDetailinfo/">责任主体信息列表</a></li>
		<shiro:hasPermission name="ams:amsUnitDetailinfo:edit"><li><a href="${ctx}/ams/amsUnitDetailinfo/form">责任主体信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="amsUnitDetailinfo" action="${ctx}/ams/amsUnitDetailinfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>社会信用代码：</label>
				<form:input path="unitCreditCode" htmlEscape="false" maxlength="22" class="input-medium"/>
			</li>
			<li><label>单位资质等级：</label>
				<form:select path="qualifications" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('unit_detail_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>责任主体主键</th>
				<th>社会信用代码</th>
				<th>单位地址</th>
				<th>单位资质等级</th>
				<th>单位资质等级_其它</th>
				<th>单位资质证书号</th>
				<th>项目负责人</th>
				<th>法人</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="ams:amsUnitDetailinfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="amsUnitDetailinfo">
			<tr>
				<td><a href="${ctx}/ams/amsUnitDetailinfo/form?id=${amsUnitDetailinfo.id}">
					${amsUnitDetailinfo.unitId}
				</a></td>
				<td>
					${amsUnitDetailinfo.unitCreditCode}
				</td>
				<td>
					${amsUnitDetailinfo.address}
				</td>
				<td>
					${fns:getDictLabel(amsUnitDetailinfo.qualifications, 'unit_detail_level', '')}
				</td>
				<td>
					${amsUnitDetailinfo.qualificationsOther}
				</td>
				<td>
					${amsUnitDetailinfo.qualificationsNumber}
				</td>
				<td>
					${amsUnitDetailinfo.responsiblePerson}
				</td>
				<td>
					${amsUnitDetailinfo.legalPerson}
				</td>
				<td>
					<fmt:formatDate value="${amsUnitDetailinfo.updateDate}" pattern="yyyy-MM-dd "/>
				</td>
				<td>
					${amsUnitDetailinfo.remarks}
				</td>
				<shiro:hasPermission name="ams:amsUnitDetailinfo:edit"><td>
    				<a href="${ctx}/ams/amsUnitDetailinfo/form?id=${amsUnitDetailinfo.id}">修改</a>
					<a href="${ctx}/ams/amsUnitDetailinfo/delete?id=${amsUnitDetailinfo.id}" onclick="return confirmx('确认要删除该责任主体信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>