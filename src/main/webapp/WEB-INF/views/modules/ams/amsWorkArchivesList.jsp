<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>业务管理档案案卷管理</title>
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
		<li class="active"><a href="${ctx}/ams/amsWorkArchives/list">业务管理档案案卷列表</a></li>
		<shiro:hasPermission name="ams:amsWorkArchives:edit"><li><a href="${ctx}/ams/amsWorkArchives/form">业务管理档案案卷添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="amsWorkArchives" action="${ctx}/ams/amsWorkArchives/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>编制单位：</label>
				<form:input path="makeUnit" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>案卷类型：</label>
				<sys:treeselect id="" name="" value="${amsWorkArchives.exten1}" labelName="exten1" labelValue="${amsWorkArchives.exten1}"
					title="类别" url="/ams/amsWorkArchives/treeData" extId="${amsWorkArchives.exten1}" cssClass=" input-xlarge" allowClear="true"/>
			</li>
			<li><label>移交日期：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${amsWorkArchives.startDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${amsWorkArchives.endDate}" pattern="yyyy-MM-dd"/>"
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
				<th>案卷题名</th>
				<th>案卷类型</th>
				<th>编制单位</th>
				<th>年度</th>
				<th>移交日期</th>
				<th>备注信息</th>
				<shiro:hasPermission name="ams:amsWorkArchives:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="amsWorkArchives">
			<tr>
				<td>
					${amsWorkArchives.archivesName}
				</td>
				<td>
					${amsWorkArchives.exten1}
				</td>
				<td>
					${amsWorkArchives.makeUnit}
				</td>
				<td>
					${amsWorkArchives.year}
				</td>
				<td><a href="${ctx}/ams/amsWorkArchives/form?id=${amsWorkArchives.id}&formView=1">
					<fmt:formatDate value="${amsWorkArchives.makeDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${amsWorkArchives.remarks}
				</td>
				<shiro:hasPermission name="ams:amsWorkArchives:edit"><td>
					<%-- <a href="${ctx}/ams/amsWorkArchives/formMain?id=${amsWorkArchives.id}">著录</a> --%>
    				<a href="${ctx}/ams/amsWorkArchives/form?id=${amsWorkArchives.id}">修改</a>
					<a href="${ctx}/ams/amsWorkArchives/delete?id=${amsWorkArchives.id}" onclick="return confirmx('确认要删除该业务管理档案案卷吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>