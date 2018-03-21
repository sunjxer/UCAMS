<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>归档一览表设置</title>
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
		<li><a href="${ctx}/ams/amsArchiveRules/list">归档文件列表</a></li>
		<li class="active"><a href="${ctx}/ams/amsArchiveRules/form?id=${amsArchiveRules.id}">归档文件<shiro:hasPermission name="ams:amsArchiveRules:edit">${not empty amsArchiveRules.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="ams:amsArchiveRules:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="amsArchiveRules" action="${ctx}/ams/amsArchiveRules/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<div class="control-group">
			<label class="control-label">归属类别：</label>
			<div class="controls">
                <sys:treeselect id="amsGenre" name="amsGenre.id"  value="${amsArchiveRules.amsGenre.id}" labelName="amsGenre.name" labelValue="${amsArchiveRules.amsGenre.name}"
					title="类别" url="/ams/amsGenre/treeData" cssClass="required"/>
					<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位工程类型:</label>
			<div class="controls">
                <sys:treeselect id="amsUserDict" name="amsUserDict.id" value="${amsArchiveRules.amsUserDict.id}" labelName="amsUserDict.name" labelValue="${amsArchiveRules.amsUserDict.name}" 
				title="单位工程类型" url="/ams/amsDesProgram/treeData?type=1" cssClass="input-small" allowClear="true"/>
					<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="fileName" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">生成单位：</label>
			<div class="controls">
				<%--<form:input path="roleType" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline" title="activiti有3种预定义的组类型：security-role、assignment、user 如果使用Activiti Explorer，需要security-role才能看到manage页签，需要assignment才能claim任务">
					工作流组用户组类型（工作流组用户组类型（监理单位：assignment、建设单位：security-role、施工单位：user）</span> --%>
				<form:select path="createUnit" class="input-medium">
					<form:option value="security-role">建设单位</form:option>
					<form:option value="assignment">监理单位</form:option>
					<form:option value="user">施工单位</form:option>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="ams:amsArchiveRules:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>