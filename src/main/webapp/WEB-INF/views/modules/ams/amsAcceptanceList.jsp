<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>预验收管理</title>
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
		<li class="active"><a href="${ctx}/ams/amsAcceptance/list?project.id=${amsAcceptance.project.id }&type=${type}">${"0".equals(type)?'预验收列表':'声像资料预验收列表 '}</a></li>
		<shiro:hasPermission name="ams:amsAcceptance:edit"><li><a href="${ctx}/ams/amsAcceptance/form?project.id=${amsAcceptance.project.id }&type=${type}">${"0".equals(type)?'预验收添加':'声像资料预验收添加 '}</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="amsAcceptance" action="${ctx}/ams/amsAcceptance/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<form:hidden path="project.id"/>
			<%-- <li><label>项目名称：</label>
				<form:input path="project.projectName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>建设单位：</label>
				<form:input path="role.name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li> --%>
			<li><label>申请日期：</label>
				<input name="beginPreAcceptanceApplyDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${amsAcceptance.beginPreAcceptanceApplyDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
				<input name="endPreAcceptanceApplyDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${amsAcceptance.endPreAcceptanceApplyDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>责任主体</th>
				<th>项目名称</th>
				<th>申请日期</th>
				<th>状态</th>
				<th>备注信息</th>
				<shiro:hasPermission name="ams:amsAcceptance:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="amsAcceptance">
			<tr>
				<td>
					${amsAcceptance.role.name}
					
				</td>
				<td>
					${amsAcceptance.project.projectName}
				</td>
				<td>
					<fmt:formatDate value="${amsAcceptance.preAcceptanceApplyDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${amsAcceptance.status=='0'?"草稿":amsAcceptance.status=='-2'?"批复驳回":amsAcceptance.status=='1'?"已提交":amsAcceptance.status=='2'?"批复通过":amsAcceptance.status=='3'?"预验收通过":amsAcceptance.status=='-3'?"预验收驳回":""}
				</td>
				<td>
					${amsAcceptance.remarks}
				</td>
				<shiro:hasPermission name="ams:amsAcceptance:edit"><td>
    				
    				<c:if test="${amsAcceptance.status=='0'}">
    				<a href="${ctx}/ams/amsAcceptance/form?id=${amsAcceptance.id}">修改</a>
    				<a href="${ctx}/ams/amsAcceptance/delete?id=${amsAcceptance.id}" onclick="return confirmx('确认要删除该预验收吗？', this.href)">删除</a>
    				</c:if>
    				<c:if test="${amsAcceptance.status!='0'}">
    				<a href="${ctx}/ams/amsAcceptance/form?id=${amsAcceptance.id}">查看</a>
    				</c:if>
					
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>