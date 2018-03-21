<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>字典管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#value").focus();
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
			
			//初始化类别选择框
			refreshParentFlag();
			//父类别列表控制
			$("#parentFlag").change(function(){
				refreshParentFlag();
			});
			//分类标记控制
			$("#haveType").change(function(){
				refreshParentFlag();
			});
			
		});
		function refreshParentFlag(){
			if($("#parentFlag").val()==0){
				$("#haveFenl").show();
				if($("#haveType").val() != -1){
					$("#haveTypeFlag").show();
				}else{
					$("#haveTypeFlag").hide();
				}
				
			}else{
				$("#haveFenl").hide();
				$("#haveTypeFlag").hide();
			}
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/ams/amsDict/">字典列表</a></li>
		<li class="active"><a href="${ctx}/ams/amsDict/form?id=${dict.id}">字典<shiro:hasPermission name="ams:amsDict:edit">${not empty dict.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="ams:amsDict:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="amsDict" action="${ctx}/ams/amsDict/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input type="hidden"  id="" value="${dict.id}">
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">键值:</label>
			<div class="controls">
				<form:input path="value" htmlEscape="false" maxlength="50" class="required "/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">标签:</label>
			<div class="controls">
				<form:input path="label" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">类型:</label>
			<div class="controls">
				<form:input path="type" htmlEscape="false" maxlength="50" class="required abc"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否为父级:</label>
			<div class="controls">
				<form:select path="parentFlag"  id="parentFlag">
						<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group" id="haveFenl" >
			<label class="control-label">选择父级:</label>
			<div class="controls">
				<form:select path="amsDict"  class="input-medium" id="haveType">
						<form:option value="-1" label="无父级"/>
						<form:options items="${fns:getParentAmsDictList('')}" itemLabel="label" itemValue="id" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group" id="haveTypeFlag" >
			<label class="control-label">分类标记:</label>
			<div class="controls">
				<form:input path="typeFlag" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline"> 分类标记是辅助 类型分类查询的标记 ，以 a,b,c,d 英文逗号方式分隔，可以为空 *</span>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" >
			<label class="control-label">描述:</label>
			<div class="controls">
				<form:input path="description" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序:</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="11" class="required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="ams:amsDict:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>