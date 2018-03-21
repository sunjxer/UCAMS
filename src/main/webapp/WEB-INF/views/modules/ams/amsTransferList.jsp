<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>移交管理</title>
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
		<li class="active"><a href="${ctx}/ams/amsTransfer/list?project.id=${amsTransfer.project.id}&type=${type}">移交列表</a></li>
		<shiro:hasPermission name="ams:amsTransfer:edit"><li><a href="${ctx}/ams/amsTransfer/form?project.id=${amsTransfer.project.id}&type=${type}">移交添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="amsTransfer" action="${ctx}/ams/amsTransfer/list?project.id=${amsTransfer.project.id }" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="type" name="type" type="hidden" value="${type}"/>
		<ul class="ul-form">
			<li><label>移交申请日期：</label>
				<input name="beginTransferApplicatonDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${amsTransfer.beginTransferApplicatonDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
				<input name="endTransferApplicatonDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${amsTransfer.endTransferApplicatonDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li>
			<label>预计移交日期：</label>
				<input name="beginEstimateTransferDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${amsTransfer.beginEstimateTransferDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
				<input name="endEstimateTransferDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${amsTransfer.endEstimateTransferDate}" pattern="yyyy-MM-dd"/>"
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
				<!-- <th>项目id</th>
				<th>建设单位外键</th> -->
				<th>移交申请日期</th>
				<th>预计移交日期</th>
				<th>移交申请内容</th>
				<th>移交流程状态</th>
				<th>备注信息</th>
				<shiro:hasPermission name="ams:amsTransfer:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="amsTransfer">
			<tr>
			<%-- 	<td><a href="${ctx}/ams/amsTransfer/form?id=${amsTransfer.id}">
					${amsTransfer.project.id}
				</a></td>
				<td>
					${amsTransfer.role.id}
				</td> --%>
				<td>
					<fmt:formatDate value="${amsTransfer.transferApplicatonDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${amsTransfer.estimateTransferDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${amsTransfer.transferApplication}
				</td>
				<td>
					${amsTransfer.status=='0'?"草稿":amsTransfer.status=='-2'?"批复驳回":amsTransfer.status=='1'?"已提交":amsTransfer.status=='2'?"批复通过":amsTransfer.status=='3'?"移交通过":amsTransfer.status=='-3'?"移交驳回":""}
				</td>
				<td>
					${amsTransfer.remarks}
				</td>
				<shiro:hasPermission name="ams:amsTransfer:edit"><td>
				<c:if test="${amsTransfer.status=='0'}">
    				<a href="${ctx}/ams/amsTransfer/form?id=${amsTransfer.id}&project.id=${amsTransfer.project.id }">修改</a>
					<a href="${ctx}/ams/amsTransfer/delete?id=${amsTransfer.id}" onclick="return confirmx('确认要删除该移交吗？', this.href)">删除</a>
				</c:if>
				<c:if test="${amsTransfer.status!='0'}">
				    <a href="${ctx}/ams/amsTransfer/form?id=${amsTransfer.id}&project.id=${amsTransfer.project.id }">查看</a>
				</c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>