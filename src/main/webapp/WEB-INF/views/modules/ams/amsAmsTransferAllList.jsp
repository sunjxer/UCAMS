<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>预验收管理</title>
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
						<td>${amsUnitDetailinfo.responsiblePersonId} </td>
						</tr> --%>
						</table>
					</form:form>
			</div>
		</div>
	</div>
</body>
</html>