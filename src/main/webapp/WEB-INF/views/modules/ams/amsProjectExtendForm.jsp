<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>专业记载信息</title>
	<meta name="decorator" content="default"/>
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/ams/amsProjectInfo/">工程项目列表</a></li>
		<li class="active"><a href="${ctx}/ams/amsProjectInfo/majorForm?id=${amsProjectInfo.id}&&status=${amsUnitProInfo.amsAcceptance.status}">项目信息拓展</a> </li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="amsProjectInfo" action="${ctx}/ams/amsProjectInfo/amsProjectExtendSave" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">单位工程:</label>
			<div class="controls">
				<span class="help-inline">${amsProjectInfo.projectName}</span>
			</div>
		</div>
		
		<c:forEach items="${amsProjectInfo.amsDesExtendList}" var="amsDesExtend" varStatus="status">
		<div class="control-group">
			<label class="control-label">${amsDesExtend.comments}:</label>
			<div class="controls">
				<input type="hidden" id="amsDesExtendList[${status.index}]_id" name="amsDesExtendList[${status.index}].id" value="${amsDesExtend.id}"/>
				<input type="hidden" id="amsDesExtendList[${status.index}]_name" name="amsDesExtendList[${status.index}].name" value="${amsDesExtend.name}"/>
				
				<c:if  test="${amsDesExtend.showType==\"text\"}">
				
				<input type="text" id="amsDesExtendList[${status.index}]_settings" name="amsDesExtendList[${status.index}].settings" value="${amsDesExtend.settings}" maxlength="100" class="input-small ${amsDesExtend.isNull==1?'required':''}"/>
				</c:if>
				
				<c:if  test="${amsDesExtend.showType==\"textarea\"}">
				 <textarea id="amsDesExtendList[${status.index}]_settings" name="amsDesExtendList[${status.index}].settings" maxlength="300" class="input-xxlarge" class="input-small${amsDesExtend.isNull==1?'required':''}">${amsDesExtend.settings}</textarea> 		
				</c:if>
				
				<c:if  test="${amsDesExtend.showType==\"number\"}">
						 <input type="number" id="amsDesExtendList[${status.index}]_settings" name="amsDesExtendList[${status.index}].settings" maxlength="10" value="${amsDesExtend.settings}"/>
				</c:if>
				
				<c:if test="${amsDesExtend.showType==\"select\"}">
				<select id="amsDesExtendList[${status.index}]_settings" name="amsDesExtendList[${status.index}].settings" class=" ${amsDesExtend.isNull==1?'required':''} input-mini" style="width:95px;*width:75px" data-value="${amsDesExtend.settings}">
					<c:forEach items="${fns:getDictList('file_type')}" var="dict">
					<option value="${dict.value}" ${dict.value==amsDesExtend.settings?'selected':''} title="${dict.description}">${dict.label}</option>
					</c:forEach>
				</select>
				</c:if>
				
				<c:if test="${amsDesExtend.showType==\"date\"}">
				<input id="amsDesExtendList[${status.index}]_settings" name="amsDesExtendList[${status.index}].settings" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate ${amsDesExtend.isNull==1?'required':''}"
					value="${amsDesExtend.settings}"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				</c:if>
				
				<c:if test="${amsDesExtend.showType==\"tree\"}">
				<sys:treeselect id="parent"  name="amsDesExtendList[${status.index}].settings"  value="${amsDesExtend.settings}" labelName="${amsDesExtend.settings}" labelValue="${amsDesExtend.settings}"
					title="${amsDesExtend.name}" url="" extId="" cssClass="" allowClear="true"/>
				</c:if>
				
				<c:if test="${amsDesExtend.isNull==1}">
				<span class="help-inline"><font color="red">*</font> </span>
				</c:if>
			</div>
		</div> 
		</c:forEach> 
		<div class="form-actions">
			<shiro:hasPermission name="ams:amsFileInfo:edit">
			<c:if test="${status == ''||status == null ||status == 0  || status == -2|| status == -3 }">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
			</c:if>
			&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>