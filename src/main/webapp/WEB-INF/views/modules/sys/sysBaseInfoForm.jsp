<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>系统规则管理</title>
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
		function delButtonClick(){
			var sysBaseInfoId = $("#sysBaseInfoId").val();
			if(sysBaseInfoId != null && sysBaseInfoId != ''){
				document.location.href = "${ctx}/sys/sysBaseInfo/delete?id="+sysBaseInfoId;
			}else{
				top.$.jBox.tip("未设置系统规则");
			}
		};
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
<!-- 		<li><a href="${ctx}/sys/sysBaseInfo/">系统规则列表</a></li> -->
		<li class="active"><a href="${ctx}/sys/sysBaseInfo/form?id=${sysBaseInfo.id}">系统规则<shiro:hasPermission name="sys:sysBaseInfo:edit">${not empty sysBaseInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:sysBaseInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="sysBaseInfo" action="${ctx}/sys/sysBaseInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id" id="sysBaseInfoId"/>
<!--  		<input type="hidden" name="id" id="sysBaseInfoId"/> --> 
		<sys:message content="${message}"/>	
		<div class="control-group">
			<label class="control-label">项目编号前缀：</label>
			<div class="controls">
				<form:input path="preProjectno" htmlEscape="false" maxlength="10" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>	
		<div class="control-group">
			<label class="control-label">单位工程编号前缀：</label>
			<div class="controls">
				<form:input path="preUnitprojectno" htmlEscape="false" maxlength="10" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">组卷方式：</label>
			<div class="controls">
				<form:select path="archivesType" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('arc_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">档号前缀：</label>
			<div class="controls">
				<form:input path="archivesPreno" htmlEscape="false" maxlength="10" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">档号起始号：</label>
			<div class="controls">
				<form:input path="archiveBeginno" htmlEscape="false" maxlength="5" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">预验收意见书编号前缀：</label>
			<div class="controls">
				<form:input path="preAcceptancePreno" htmlEscape="false" maxlength="10" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">预验收意见书起始编号：</label>
			<div class="controls">
				<form:input path="preAcceptanceBeginno" htmlEscape="false" maxlength="5" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">移交证明书编号前缀：</label>
			<div class="controls">
				<form:input path="transferPreno" htmlEscape="false" maxlength="10" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">移交证明书起始编号：</label>
			<div class="controls">
				<form:input path="transferBeginno" htmlEscape="false" maxlength="5" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">文件级是否著录：</label>
			<div class="controls">
				<form:select path="fileDes" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">建设工程规划是否著录：</label>
			<div class="controls">
				<form:select path="constructDes" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">建设用地规划是否著录：</label>
			<div class="controls">
				<form:select path="landDes" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">卷内目录规则设置：</label>
			<div class="controls">
				<form:select path="catalog" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getAmsDictList('catalog_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">馆藏档案电子化设置：</label>
			<div class="controls">
				<form:select path="collectionType" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getAmsDictList('collection_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
<!-- 
		<div class="control-group">
			<label class="control-label">扩展1：</label>
			<div class="controls">
				<form:input path="exten1" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">扩展2：</label>
			<div class="controls">
				<form:input path="exten2" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">扩展3：</label>
			<div class="controls">
				<form:input path="exten3" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">扩展4：</label>
			<div class="controls">
				<form:input path="exten4" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">扩展5：</label>
			<div class="controls">
				<form:input path="exten5" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
 -->
		<div class="form-actions">
			<shiro:hasPermission name="sys:sysBaseInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<!-- <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/> -->
			<input id="delButton" class="btn btn-primary" type="button" value="删  除" onclick="return confirmx('确认要删除该系统规则吗？', delButtonClick)"/>
		</div>
	</form:form>
</body>
</html>