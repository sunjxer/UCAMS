<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>责任主体信息管理</title>
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
		<li><a href="${ctx}/sys/resBody/list">责任主体</a></li>
		<li class="active"><a href="${ctx}/ams/amsUnitDetailinfo/form?id=${amsUnitDetailinfo.id}">责任主体详细信息<shiro:hasPermission name="ams:amsUnitDetailinfo:edit">${not empty amsUnitDetailinfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="ams:amsUnitDetailinfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="amsUnitDetailinfo" action="${ctx}/ams/amsUnitDetailinfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="unitId"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">社会信用代码：</label>
			<div class="controls">
				<form:input path="unitCreditCode" htmlEscape="false" maxlength="22" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位地址：</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位资质等级：</label>
			<div class="controls">
				<form:select path="qualifications" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('unit_detail_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位资质等级_其它：</label>
			<div class="controls">
				<form:input path="qualificationsOther" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位资质证书号：</label>
			<div class="controls">
				<form:input path="qualificationsNumber" htmlEscape="false" maxlength="30" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目负责人：</label>
			<div class="controls">
				<form:input path="responsiblePerson" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目负责人身份证号：</label>
			<div class="controls">
				<form:input path="responsiblePersonId" htmlEscape="false" maxlength="18" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">法人：</label>
			<div class="controls">
				<form:input path="legalPerson" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="ams:amsUnitDetailinfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>