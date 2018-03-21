<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>业务管理档案案卷文件管理</title>
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
		<li class="active"><a href="${ctx}/ams/amsWorkArchivesFiles/list?groupId=${amsArchivesInfo.id}">档案案卷文件列表</a></li>
		<shiro:hasPermission name="ams:amsWorkArchivesFiles:edit"><li><a href="${ctx}/ams/amsWorkArchivesFiles/form?groupId=${amsArchivesInfo.id}">档案案卷文件添加</a></li></shiro:hasPermission>
	</ul>
	<div class="accordion" id="accordion2">
		
		<div class="accordion-group">
				<div class="accordion-inner">
					<form:form modelAttribute="amsArchivesInfo" class="form-horizontal">
						<input type="hidden" id="id" value="${id}">
						<table class="table-form">
						<tr>
							<td align="center" colspan="4" width="20%"><b>案卷基本信息</b></td>
						</tr>
						<tr>
							<td class="tit" width="20%">案卷题名:</td>
							<td colspan="3">${amsArchivesInfo.archivesName} </td>
						</tr>
						<tr>
							<td class="tit" width="20%">编制单位:</td>
							<td colspan="3">${amsArchivesInfo.makeUnit} </td>
						</tr>
						<tr>
							<td class="tit" width="20%">载体类型:</td>
							<td>${fns:getDictLabel(amsArchivesInfo.carrierType, 'carrier_type', '')}</td>
							<td class="tit" width="20%">数量:</td>
							<td>${amsArchivesInfo.filesCount}  </td>							
						</tr>
						<tr>
							<td class="tit" width="20%">卷内文件起始时间:</td>
							<td><fmt:formatDate value="${amsArchivesInfo.startDate}" pattern="yyyy-MM-dd"/></td>
							<td class="tit" width="20%">卷内文件终止时间:</td>
							<td><fmt:formatDate value="${amsArchivesInfo.endDate}" pattern="yyyy-MM-dd"/></td>
						</tr>
						<tr>
							<td class="tit" width="20%">保管期限:</td>
							<td>${fns:getDictLabel(amsArchivesInfo.storagePeriod, 'storage_period', '')}</td>
							<td class="tit" width="20%">密级:</td>
							<td>${fns:getDictLabel(amsArchivesInfo.degreeSecrets, 'degree_secrets', '')}</td>
						</tr>
						<tr>
							<td class="tit" width="20%">主题词:</td>
							<td colspan="3">${amsArchivesInfo.mainTitle} </td>
						</tr>
						<tr>
							<td class="tit" width="20%">附注:</td>
							<td colspan="3">${amsArchivesInfo.archivesExplain} </td>
						</tr>
						</table>
					</form:form>
			</div>
		</div>
		
		<div class="accordion-group">
			<div class="accordion-heading">
				<a class="accordion-toggle" data-toggle="collapse"
					data-parent="#accordion2" href="#collapseOne"> 点击查看-预验收列表 </a>
			</div>
			<div id="collapseOne" class="accordion-body collapse in">
				<div class="accordion-inner">
					<form:form id="searchForm" modelAttribute="amsWorkArchivesFiles" action="${ctx}/ams/amsWorkArchivesFiles/list?groupId=${amsArchivesInfo.id}" method="post" class="breadcrumb form-search">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<ul class="ul-form">
						<li><label>文件名：</label>
							<form:input path="fileName" htmlEscape="false" maxlength="50" class="input-medium"/>
						</li>
							<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
							<li class="clearfix"></li>
						</ul>
					</form:form>
					<sys:message content="${message}"/>
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th>文件名</th>
								<th>文图号</th>
								<th>责任人</th>
								<th>文件类型</th>
								<th>文件份数</th>
								<th>形成日期</th>
								<th>备注信息</th>
								<shiro:hasPermission name="ams:amsWorkArchivesFiles:edit"><th>操作</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody>
						<c:forEach items="${page.list}" var="amsWorkArchivesFiles">
							<tr>
								<td>
									${amsWorkArchivesFiles.fileName}
								</td>
								<td>
									${amsWorkArchivesFiles.fileNo}
								</td>
								<td>
									${amsWorkArchivesFiles.author}
								</td>
								<td>
									${fns:getDictLabel(amsWorkArchivesFiles.fileType,'file_type','无')}
								</td>
								<td>
									${amsWorkArchivesFiles.filecount}
								</td>
								<td><a href="${ctx}/ams/amsWorkArchivesFiles/viewForm?id=${amsWorkArchivesFiles.id}">
									<fmt:formatDate value="${amsWorkArchivesFiles.formDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
								</a></td>
								<td>
									${amsWorkArchivesFiles.remarks}
								</td>
								<shiro:hasPermission name="ams:amsWorkArchivesFiles:edit"><td>
				    				<a href="${ctx}/ams/amsWorkArchivesFiles/form?id=${amsWorkArchivesFiles.id}">修改</a>
									<a href="${ctx}/ams/amsWorkArchivesFiles/delete?id=${amsWorkArchivesFiles.id}" onclick="return confirmx('确认要删除该业务管理档案案卷文件吗？', this.href)">删除</a>
								</td></shiro:hasPermission>
							</tr>
						</c:forEach>
						</tbody>
					</table>
					<div class="pagination">${page}</div>
					</div>
			</div>
		</div>
	</div>
</body>
</html>