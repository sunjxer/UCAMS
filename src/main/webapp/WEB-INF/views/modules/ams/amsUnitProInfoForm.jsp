<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>单位工程管理</title>
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
		<li><a href="${ctx}/ams/amsUnitProInfo/?projectId=${amsUnitProInfo.projectId}">单位工程列表</a></li>
		<li class="active"><a href="${ctx}/ams/amsUnitProInfo/form?id=${amsUnitProInfo.id}">单位工程<shiro:hasPermission name="ams:amsUnitProInfo:edit">${not empty amsUnitProInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="ams:amsUnitProInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="amsUnitProInfo" action="${ctx}/ams/amsUnitProInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="projectId"/>
		<sys:message content="${message}"/>		
		<div class="control-group" style="display: none;">
			<label class="control-label">单位工程编号：</label>
			<div class="controls">
				<form:input path="unitProjectNo" readonly="true"  htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
			<div class="control-group">
			<label class="control-label">单位工程类型：</label>
			<div class="controls">
				<form:select path="unitProjectType" class="input-xlarge required">
					<form:options items="${fns:getAmsDictList('unit_project_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位工程名称：</label>
			<div class="controls">
				<form:input path="unitProjectName" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">土地出让年限：</label>
			<div class="controls">
				<form:input path="landLeasingPeriod" htmlEscape="false"  maxlength="5" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
	
	
		<div class="control-group" style="display: none;">
			<label class="control-label">规划许可证号：</label>
			<div class="controls">
				<form:input path="planningLicenseNumber" htmlEscape="false" maxlength="20" class="input-xlarge  required"/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">施工许可证号：</label>
			<div class="controls">
				<form:input path="constructionPermitNumber" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开工日期：</label>
			<div class="controls">
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate  required"
					value="<fmt:formatDate value="${amsUnitProInfo.startDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">竣工日期：</label>
			<div class="controls">
				<input name="finishDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate  "
					value="<fmt:formatDate value="${amsUnitProInfo.finishDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">设计单位：</label>
			<div class="controls">
				<form:input path="designUnit" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">勘察单位：</label>
			<div class="controls">
				<form:input path="explorationUnit" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">施工单位：</label>
			<div class="controls">
				<form:input path="contractor" htmlEscape="false" maxlength="50" disabled="true" class="input-xlarge required"/>
				<!-- <span class="help-inline"><font color="red">*</font> </span> -->
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">监理单位：</label>
			<div class="controls">
				<form:input path="supervisionUnit" htmlEscape="false" maxlength="50" disabled="true" class="input-xlarge required"/>
				<!-- <span class="help-inline"><font color="red">*</font> </span> -->
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">X坐标：</label>
			<div class="controls">
				<form:input path="abscissa" htmlEscape="false" maxlength="12" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">Y坐标：</label>
			<div class="controls">
				<form:input path="ordinate" htmlEscape="false" maxlength="12" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
		 <c:if test="${amsUnitProInfo.amsAcceptance.status == null ||amsUnitProInfo.amsAcceptance.status == 0  || amsUnitProInfo.amsAcceptance.status == -2|| amsUnitProInfo.amsAcceptance.status == -3 }">
			<shiro:hasPermission name="ams:amsUnitProInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>