<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>文件管理</title>
<meta name="decorator" content="default" />
<%@include file="/WEB-INF/views/include/treeview.jsp"%>
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
	<sys:message content="${message}" />
	<div class="accordion" id="accordion2">
		
		<div class="accordion-group">
				<div class="accordion-inner">
					<form:form modelAttribute="amsUnitDetailinfo" class="form-horizontal">
						<sys:message content="${message}" />
						<input type="hidden" id="id" value="${id}">
						<br>
						<table class="table-form">
						<tr>
						<td align="center" colspan="4" width="20%"><b>单位基本信息</b></td>
						</tr>
						<tr>
						<td class="tit" width="20%">单位名称：</td>
						<td colspan="3">${fns:getUser().name}  </td>
						</tr>
						<tr>
						<td class="tit" width="20%">社会信用代码：</td>
						<td colspan="3">${amsUnitDetailinfo.unitCreditCode}  </td>
						</tr>
						<tr>
						<td class="tit" width="20%">单位地址：</td>
						<td colspan="3">${amsUnitDetailinfo.address}  </td>
						</tr>
						<tr>
						<td class="tit" width="20%">单位资质等级：</td>
						<td>${amsUnitDetailinfo.qualifications} </td>
						<td class="tit" width="20%">单位资质证书号：</td>
						<td>${amsUnitDetailinfo.qualificationsNumber} </td>
						<%-- <td class="tit" width="20%">单位资质等级_其它：</td>
						<td>${amsUnitDetailinfo.qualificationsOther} </td> --%>
						</tr>
						<tr>
						<td class="tit" width="20%">项目负责人：</td>
						<td>${amsUnitDetailinfo.responsiblePerson} </td>
						<td class="tit" width="20%">法人：</td>
						<td>${amsUnitDetailinfo.legalPerson} </td>
						</tr>
						<%-- <tr>
			            <td class="tit" width="20%">身份证号：</td>
						<td>${amsUnitDetailinfo.responsiblePersonId} </td> 、
						</tr> --%>
						</table>
					</form:form>
			</div>
		</div>
		<div class="accordion-group">
			<div class="accordion-heading">
				<a class="accordion-toggle" data-toggle="collapse"
					data-parent="#accordion2" href="#collapseOne"> 点击查看-项目信息列表 </a>
			</div>
			<div id="collapseOne" class="accordion-body collapse in">
				<div class="accordion-inner">
				<form:form id="searchForm"  action="${ctx}/ams/amsFileInfo/businessList" method="post" class="breadcrumb form-search">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				</form:form>
					<sys:message content="${message}" />
					<table id="contentTable"
						class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th>项目编号</th>
								<th>项目名称</th>
								<th>项目地址</th>
								<th>项目类别</th>
								<th>用地规划许可证号</th> 
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${page.list}" var="project">
								<tr>
									<td>${project.projectNo}</td>
									<td>${project.projectName}</td>
									<td>${project.address}</td>
									<td>${fns:getDictLabel(project.projectType, 'unit_project_type', '')}</td>
									<td>${project.landLicenseNumber}</td>
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