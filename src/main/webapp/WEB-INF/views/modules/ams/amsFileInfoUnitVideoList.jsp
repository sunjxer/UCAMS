<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>文件管理</title>
<meta name="decorator" content="default" />
<%@include file="/WEB-INF/views/include/treeview.jsp"%>
<script type="text/javascript">
$(document).ready(function() {
	$(":radio[aa='record']").click(function(){
		$("#searchForm").submit();
	});
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
	<c:if test="${empty amsUnitProInfo.id}">
	<%-- <label class="control-label"> ${message}</label> --%>
	<script type="text/javascript">top.$.jBox.tip("单位工程不存在！");
	</script>
	</c:if>
	<c:if test="${not empty amsUnitProInfo.id}">
	 <ul class="nav nav-tabs">
	 <li class="active"><a href="${ctx}/ams/amsFileInfo/amsUnitProInfoList?id=${unitProjectId}&recordId=-1">声像文件列表</a></li>
	 <shiro:hasPermission name="ams:amsFileInfo:edit"><li><a href="${ctx}/ams/amsFileInfo/form?unitProjectId=${unitProjectId}&recordId=-1">声像档案添加</a></li></shiro:hasPermission>
	 </ul>
	<sys:message content="${message}" />
		<div class="accordion-group">
				<div class="accordion-inner">
					<form:form modelAttribute="amsUnitProInfo" class="form-horizontal">
						<sys:message content="${message}" />
						<input type="hidden" id="id" value="${id}">
						<table class="table-form">
						<tr>
						<td align="center" colspan="6"><b>单位工程基本信息</b></td>
						</tr>
						<tr>
						<td class="tit" width="15%">单位工程名称：</td>
						<td colspan="5">${amsUnitProInfo.unitProjectName} </td>
						
						</tr>
						<tr>
						<td class="tit" width="15%">单位工程编号：</td>
						<td colspan="5">${amsUnitProInfo.unitProjectNo} </td>
						</tr>
						<tr>
						<td class="tit" width="15%">施工许可证号：</td>
						<td colspan="5">${amsUnitProInfo.constructionPermitNumber}</td>
						</tr>
						</table>
					</form:form>
				</div>
			<!-- </div> -->
		</div>
		<div class="accordion-group">
			<div class="accordion-heading">
				<a class="accordion-toggle" data-toggle="collapse"
					data-parent="#accordion2" href="#collapseOne"> 点击查看-声像档案信息列表 </a>
			</div>
		<div id="collapseOne" class="accordion-body collapse in">
		<div class="accordion-inner">
		<form:form id="searchForm" modelAttribute="amsFileInfo" action="${ctx}/ams/amsFileInfo/amsUnitProInfoList?id=${unitProjectId}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>文件名：</label>
				<form:input path="fileName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li>
			<label>资料档案</label><form:radiobutton path="recordId" aa="record"  checked="checked" value="0" />
			</li>
			<li>
			<label>声像档案</label><form:radiobutton path="recordId" value="-1" />
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>文件名</th>
				<th>文图号</th>
				<th>责任者</th>
				<th>形成日期</th>
				<!-- <th>文件份数</th>
				<th>文件类型</th> -->
				<th>备注信息</th>
				<shiro:hasPermission name="ams:amsFileInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="amsFileInfo">
			<tr>
				<td>
					${amsFileInfo.fileName}
				</td>
				<td>${amsFileInfo.fileNo}</td>
				<td>${amsFileInfo.author}</td>
				<td><fmt:formatDate value="${amsFileInfo.formDate}" pattern="yyyy-MM-dd"/></td>
				<%-- <td>${amsFileInfo.filecount}</td>
				<td>${fns:getDictLabel(amsFileInfo.fileType, 'file_type', '')}</td> --%>
				<td>${amsFileInfo.remarks}</td>
				<shiro:hasPermission name="ams:amsFileInfo:edit"><td>
    				<a href="${ctx}/ams/amsFileInfo/form?id=${amsFileInfo.id}&unitProjectId=${unitProjectId}&recordId=${amsFileInfo.recordId}">${not empty amsFileInfo.id?'著录':''}</a>
					<a href="${ctx}/ams/amsFileInfo/delete?id=${amsFileInfo.id}&recordId=${amsFileInfo.recordId}" onclick="return confirmx('确认要删除该文件吗？', this.href)">${not empty amsFileInfo.id&&empty amsFileInfo1.groupId?'删除':''}</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	</div>
	</div>
	</div>
	</c:if>
</body>
</html>