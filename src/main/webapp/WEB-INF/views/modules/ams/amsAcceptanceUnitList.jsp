<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>文件管理</title>
<meta name="decorator" content="default" />
<%@include file="/WEB-INF/views/include/treeview.jsp"%>
<script type="text/javascript">
$(document).ready(function() {
	//$("#name").focus();
	$("#inputForm").validate({
		submitHandler: function(form){
			loading('正在提交，请稍等...');
			form.submit();
		},
		errorContainer: "#messageBox",
		errorPlacement: function(error, element) {
			$("#messageBox").text("输入有误，请先更正。");
			if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
				error.appendTo(element.parent().parent());
			} else {
				error.insertAfter(element);
			}
		}
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
		<div class="accordion-group">
				<div class="accordion-inner">
					<form:form  id="inputForm"  modelAttribute="amsUnitProInfo" action="${ctx}/ams/record/saveUnit" method="post" class="form-horizontal" >
						<input type="hidden" id="id" value="${id}">
						<form:hidden path="id"/>
						<table class="table-form">
						<tr>
						<td align="center" colspan="4" width="20%"><b>单位工程基本信息</b></td>
						</tr>
						<tr>
						<td class="tit" width="20%">单位工程名称:</td>
						<td colspan="3">${amsUnitProInfo.unitProjectName} </td>
						</tr>
						<tr>
						<td class="tit" width="20%">单位工程编号:</td>
						<td colspan="3">${amsUnitProInfo.unitProjectNo} </td>
						</tr>
						<tr>
						<%-- <td class="tit" width="20%">规划许可证号:</td>
						<td>${amsUnitProInfo.planningLicenseNumber} </td> --%>
						<td class="tit" width="20%">施工许可证号:</td>
						<td>${amsUnitProInfo.constructionPermitNumber}</td>
						</tr>
						<c:forEach items="${amsUnitProInfo.amsDesExtendList}" var="amsDesExtend" varStatus="status">
		<c:if test="${status.index%2==0}">
		<tr>
		</c:if>
		<td class="tit">${amsDesExtend.comments}：</td>
		<td>
				<input type="hidden" id="amsDesExtendList[${status.index}]_id" name="amsDesExtendList[${status.index}].id" value="${amsDesExtend.id}"/>
				<input type="hidden" id="amsDesExtendList[${status.index}]_name" name="amsDesExtendList[${status.index}].name" value="${amsDesExtend.name}"/>
				<c:if test="${amsDesExtend.showType==\"text\"}">
				${amsDesExtend.settings}
				</c:if>
				<c:if test="${amsDesExtend.showType==\"textarea\"}">
				${amsDesExtend.settings}	
				</c:if>
				<c:if test="${amsDesExtend.showType==\"select\"}">
				<select id="amsDesExtendList[${status.index}]_settings" name="amsDesExtendList[${status.index}].settings" class=" ${amsDesExtend.isNull==1?'required':''} input-mini" style="width:95px;*width:75px" data-value="${amsDesExtend.settings}">
					<c:forEach items="${fns:getDictList('file_type')}" var="dict">
					<option value="${dict.value}" ${dict.value==amsDesExtend.settings?'selected':''} title="${dict.description}">${dict.label}</option>
					</c:forEach>
				</select>
				</c:if>
				<c:if test="${amsDesExtend.showType==\"date\"}">
				${amsDesExtend.settings}
				</c:if>
			</td>
		<c:if test="${status.index%2==1}">
		<tr>
		</c:if>
		</c:forEach> 
		</table>
		</form:form>
		</div>
			<!-- </div> -->
		</div>
	</c:if>
</body>
</html>