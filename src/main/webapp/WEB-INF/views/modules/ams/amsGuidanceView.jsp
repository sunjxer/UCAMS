<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>业务指导管理</title>
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
		<li><a href="${ctx}/ams/amsGuidance/">业务指导列表</a></li>
		<li class="active"><a href="${ctx}/ams/amsGuidance/form?id=${amsGuidance.id}">业务指导<shiro:hasPermission name="ams:amsGuidance:edit">${not empty amsGuidance.id?'查看':'添加'}</shiro:hasPermission><shiro:lacksPermission name="ams:amsGuidance:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="amsGuidance" action="${ctx}/ams/amsGuidance/save" method="post" class="form-horizontal">

		<form:hidden path="id"/>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden id="flag" path="act.flag"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">项目名称：</label>
			<div class="controls">
                ${amsGuidance.project.name}                       
			</div>
		</div>	
		<div class="control-group">
			<label class="control-label">项目地址：</label>
			<div class="controls">
				${amsGuidance.address}				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">期望指导日期：</label>
			<div class="controls">
				<input name="expectDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${amsGuidance.expectDate}" pattern="yyyy-MM-dd"/>"
					/>				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系人：</label>
			<div class="controls">
				${amsGuidance.liaisons}				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系电话：</label>
			<div class="controls">
				${amsGuidance.telphone}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">申请内容：</label>
			<div class="controls">
				${amsGuidance.content}				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">批复意见：</label>
			<div class="controls">
				${amsGuidance.opinion}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">指导内容：</label>
			<div class="controls">
				${amsGuidance.guidance}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">指导日期：</label>
			<div class="controls">
				<input name="expectDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${amsGuidance.guidanceDate}" pattern="yyyy-MM-dd"/>"
					/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				${amsGuidance.remarks}
			</div>
		</div>
<%-- 		<act:histoicFlow procInsId="${testAudit.act.procInsId}" /> --%>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
 		<c:if test="${not empty amsGuidance.id}">
			<act:histoicFlow procInsId="${amsGuidance.procInsId}" />
		</c:if> 
	</form:form>
</body>
</html>