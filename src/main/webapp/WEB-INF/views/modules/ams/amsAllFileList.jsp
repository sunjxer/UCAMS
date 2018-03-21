<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>文件管理</title>
<meta name="decorator" content="default" />
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
	<div class="accordion" id="accordion2">
		<div class="accordion-group">
			<div class="accordion-heading">
				<a class="accordion-toggle" data-toggle="collapse"
					data-parent="#accordion" href="#collapseTwo"> 未组卷文件 </a>
			</div>
			<div id="collapseTwo" class="accordion-body collapse in ">
				<div class="accordion-inner">
				<form:form id="searchForm" modelAttribute="amsArchivesInfo" action="${ctx}/ams/amsArchivesInfo/amsAllFileList?treeId=${treeId }&id=${amsArchivesInfo.id}" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${pageAll.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${pageAll.pageSize}"/>
					<ul class="ul-form">
						<li><label>文件名：</label>
							<form:input path="amsFileInfo.fileName" htmlEscape="false" maxlength="100" class="input-medium"/>
						</li>
						<li class="btns"><input id="btnSubmit2" class="btn btn-primary" type="submit" value="查询"/></li>
						<li class="clearfix"></li>
					</ul>
				</form:form>
					<table id="contentTable"
						class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th>文件名</th>
								<th>文（图）号</th>
								<c:if test="${amsArchivesInfo.exten1 == 0 || amsArchivesInfo.exten1 == null}">
								<th>实体页数 </th>								
								<th>文件类型</th>
								</c:if>
								<th>操作</th> 
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${pageAll.list}" var="amsFileInfo">
								<tr>
									<td>${amsFileInfo.fileName}</td>
									<td>${amsFileInfo.fileNo}</td>
									<c:if test="${amsArchivesInfo.exten1 == 0 || amsArchivesInfo.exten1 == null}">
									<td>${amsFileInfo.filecount}</td>
									<td>${fns:getDictLabel(amsFileInfo.fileType, 'file_type', '')}</td>
									</c:if>
									<shiro:hasPermission name="ams:amsFileInfo:edit">
									<td>
									<a href="${ctx}/ams/amsArchivesInfo/addFile?fileId=${amsFileInfo.id}&archId=${amsArchivesInfo.id}&treeId=${treeId}&type=${type}">添加到案卷</a>
									</td>
									</shiro:hasPermission>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<div class="pagination">${pageAll}</div>
				</div>
			</div>
		</div>
	</div>
</body>