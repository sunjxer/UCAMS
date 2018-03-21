<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>工程项目管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#projectName").focus();
			$("#inputForm").validate({
					rules: {
						projectName: {remote: "${ctx}/ams/amsProjectInfo/checkProjectName?oldProjectName=" + encodeURIComponent('${amsProjectInfo.projectName}')}
					},
					messages: {
						loginName: {remote: "项目名称已存在"}
					},
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
		<li><a href="${ctx}/ams/amsProjectInfo/">工程项目列表</a></li>
		<li class="active"><a href="${ctx}/ams/amsProjectInfo/form?id=${amsProjectInfo.id}">工程项目<shiro:hasPermission name="ams:amsProjectInfo:edit">${not empty amsProjectInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="ams:amsProjectInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="amsProjectInfo" action="${ctx}/ams/amsProjectInfo/saveProjectActivity" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden id="flag" path="act.flag"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">项目类别：</label>
			<div class="controls">
				${fns:getAmsDictLabel(amsProjectInfo.projectType, 'unit_project_type', '')}				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目编号：</label>
			<div class="controls">
				${amsProjectInfo.projectNo}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目名称：</label>
			<div class="controls">
				${amsProjectInfo.projectName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目地点：</label>
			<div class="controls">
				${amsProjectInfo.area.name}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目地址：</label>
			<div class="controls">
				${amsProjectInfo.address}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">立项批准单位：</label>
			<div class="controls">
				${amsProjectInfo.projectApprovalUnit}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">设计单位：</label>
			<div class="controls">
				${amsProjectInfo.designUnit}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">勘察单位：</label>
			<div class="controls">
				${amsProjectInfo.prospectingUnit}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">监理单位：</label>
			<div class="controls">
				${amsProjectInfo.supervisionUnit}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">立项批准文号：</label>
			<div class="controls">
				${amsProjectInfo.approvalNumber}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">立项批准附件：</label>
			<div class="controls">
				<div id='lssl' ><img alt=''  width="10%"  height="10%" src=' ${amsProjectInfo.approvalUrl}' ></div>
			<c:if test='${not empty amsProjectInfo.approvalUrl}'>
				<a  href='javascript:$.jBox.open("iframe:${ctx}/ams/amsProjectInfo/enc?imgpath=${amsProjectInfo.approvalUrl}", "查看附件", 800, 500, { buttons: { "关闭": true} });' >查看附件</a>
			</c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">建设规划许可证号：</label>
			<div class="controls">
				${amsProjectInfo.planningLicenseNumber}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">建设规划许可证附件：</label>
			<div class="controls">
				<div id='lssl' ><img alt=''  width="10%"  height="10%" src=' ${amsProjectInfo.planningLicenseUrl}' ></div>
				<c:if test='${not empty amsProjectInfo.planningLicenseUrl}'>
					<a  href='javascript:$.jBox.open("iframe:${ctx}/ams/amsProjectInfo/enc?imgpath=${amsProjectInfo.planningLicenseUrl}", "查看附件", 800, 500, { buttons: { "关闭": true} });' >查看附件</a>
				</c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">用地规划许可证号：</label>
			<div class="controls">
				${amsProjectInfo.landLicenseNumber}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">用地规划许可证附件：</label>
			<div class="controls">
				<div id='lssl' ><img alt=''  width="10%"  height="10%" src=' ${amsProjectInfo.landLicenseUrl}' ></div>
				<c:if test='${not empty amsProjectInfo.landLicenseUrl}'>
			<a  href='javascript:$.jBox.open("iframe:${ctx}/ams/amsProjectInfo/enc?imgpath=${amsProjectInfo.landLicenseUrl}", "查看附件", 800, 500, { buttons: { "关闭": true} });' >查看附件</a>
			</c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">用地许可证号：</label>
			<div class="controls">
				${amsProjectInfo.landPermitNumber}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">用地许可证附件：</label>
			<div class="controls">
				<div id='lssl' ><img alt=''  width="10%"  height="10%" src=' ${amsProjectInfo.landPermitUrl}' ></div>
				<c:if test='${not empty amsProjectInfo.landPermitUrl}'>
			<a  href='javascript:$.jBox.open("iframe:${ctx}/ams/amsProjectInfo/enc?imgpath=${amsProjectInfo.landPermitUrl}", "查看附件", 800, 500, { buttons: { "关闭": true} });' >查看附件</a>
			</c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开工日期：</label>
			<div class="controls">
				<fmt:formatDate value="${amsProjectInfo.startDate}" pattern="yyyy-MM-dd"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">竣工日期：</label>
			<div class="controls">
				<fmt:formatDate value="${amsProjectInfo.finishDate}" pattern="yyyy-MM-dd"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				${amsProjectInfo.remarks}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">审核意见：</label>
			<div class="controls">
				${amsProjectInfo.opinion}				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">业务指导员：</label>
			<div class="controls">
				<form:input path="businessMan" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保存" onclick="$('#flag').val('yes')"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		<act:histoicFlow procInsId="${amsProjectInfo.act.procInsId}" />
	</form:form>
</body>
</html>