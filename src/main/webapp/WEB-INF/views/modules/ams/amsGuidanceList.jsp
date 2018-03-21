<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>业务指导管理</title>
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
		<li class="active"><a href="${ctx}/ams/amsGuidance/">业务指导列表</a></li>
		<shiro:hasPermission name="ams:amsGuidance:edit"><li><a href="${ctx}/ams/amsGuidance/form">业务指导添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="amsGuidance" action="${ctx}/ams/amsGuidance/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>项目名称：</label>
				<form:input path="project.name" htmlEscape="false"  class="input-medium"/>
			</li>
			<li><label>项目地址：</label>
				<form:input path="address" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>期望日期：</label>
				<input name="expectDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${amsGuidance.expectDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<%-- <li><label>指导日期：</label>
				<input name="guidanceDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${amsGuidance.guidanceDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li> --%>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>项目名称</th>
				<th>项目地址</th>
				<th>期望指导日期</th>
				<th>联系人</th>
				<th>联系电话</th>
				<th>指导日期</th>
				
				<th>备注信息</th>
				<%-- <shiro:hasPermission name="ams:amsGuidance:edit"><th>操作</th></shiro:hasPermission> --%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="amsGuidance">
			<tr>
				<td><a href="${ctx}/ams/amsGuidance/form?id=${amsGuidance.id}">
					${amsGuidance.project.name}
				</a></td>
				<td>
					${amsGuidance.address}
				</td>
				<td>
					<fmt:formatDate value="${amsGuidance.expectDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${amsGuidance.liaisons}
				</td>
				<td>
					${amsGuidance.telphone}
				</td>
				<td>
					<fmt:formatDate value="${amsGuidance.guidanceDate}" pattern="yyyy-MM-dd"/>
				</td>				
				<td>
					${amsGuidance.remarks}
					${amsGuidance.act.status}
				</td>
				<%-- <shiro:hasPermission name="ams:amsGuidance:edit"><td>
				<c:if test="${empty amsGuidance.procInsId || amsGuidance.procInsId eq ''} ">
				<a href="${ctx}/ams/amsGuidance/form?id=${amsGuidance.id}&procInsId=${amsGuidance.procInsId}">修改</a>
				</c:if>
				<c:if test="${amsGuidance.act.status eq 'finish' }">
				
				<a href="${ctx}/ams/amsGuidance/delete?id=${amsGuidance.id}" onclick="return confirmx('确认要删除该业务指导吗？', this.href)">删除</a>
				</c:if>
				</td>
				</shiro:hasPermission> --%>
				
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>