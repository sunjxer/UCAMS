<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page import="java.util.*" %>
<html>
<head>
	<title>移交管理</title>
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
		<li><a href="${ctx}/ams/amsTransfer/list?project.id=${amsTransfer.project.id}&type=${amsTransfer.type}">移交列表</a></li>
		<li class="active"><a href="${ctx}/ams/amsTransfer/form?id=${amsTransfer.id}&project.id=${amsTransfer.project.id}&type=${amsTransfer.type}">移交<shiro:hasPermission name="ams:amsTransfer:edit">${not empty amsTransfer.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="ams:amsTransfer:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="amsTransfer" action="${ctx}/ams/amsTransfer/save" method="post" class="form-horizontal">
		<form:hidden path="project.id"/>
		<form:hidden path="id"/>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden id="flag" path="act.flag"/>
		<form:hidden path="type"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">移交申请日期：</label>
			<div class="controls">
			<c:if test="${empty amsTransfer.id}"><input name="transferApplicatonDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="<%=new Date()%>" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</c:if>
			<c:if test="${not empty amsTransfer.id}"><input name="transferApplicatonDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${amsTransfer.transferApplicatonDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">预计移交日期：</label>
			<div class="controls">
				<input name="estimateTransferDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${amsTransfer.estimateTransferDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">移交申请人：</label>
			<div class="controls">
					<sys:treeselect id="user" name="user.id" value="${amsTransfer.user.id}" labelName="user.name" labelValue="${amsTransfer.user.name}" 
							title="用户" url="/sys/office/userTree?type=3" cssClass="required recipient" cssStyle="width:150px" 
							allowClear="true" notAllowSelectParent="true" smallBtn="false"/> 
<%-- 				<form:input path="preAcceptanceApplicant" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
 --%>				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">移交申请人联系电话：</label>
			<div class="controls">
				<form:input path="transferApplicantPhone" htmlEscape="false" maxlength="20" class="input-xlarge phone"/>
			</div>
		</div>
	
		<div class="control-group">
			<label class="control-label">移交申请内容：</label>
			<div class="controls">
				<form:textarea path="transferApplication" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
	<div class="control-group">
		<div style="text-align:center;border-top: 1px solid #eeeeee;background-color: #f5f5f5; padding: 8px 8px 4px 8px;">
		<b>项目预验收列表</b>
		</div>
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="5%">选择</th>
				<th>预验收申请日期</th>
				<th>预验收批复意见</th>
				<th>预验收意见书编号</th>
				<th>备注</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${amsTransfer.amsAcceptancesList}" var="amsAcceptances">
			<tr>
				<td>
				<form:checkbox path="acceptsString" checked="${amsAcceptances.exten5}" value="${amsAcceptances.id}"/>
				</td>
				<td>
					<fmt:formatDate value="${amsAcceptances.preAcceptanceApplyDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${amsAcceptances.preAcceptanceApprovalOpinions}
				</td>
				<td>
					${amsAcceptances.preAcceptanceNo}
				</td>	
				<td>
					${amsAcceptances.remarks}
				</td>
				
			</tr>
		</c:forEach>
		</tbody>
	</table>
</div>
		<div class="form-actions">
		<shiro:hasPermission name="ams:amsTransfer:edit">
		<c:if test="${empty amsTransfer.procInsId}">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保存草稿"/>&nbsp;
		</c:if>
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保存并提交申请" onclick="$('#flag').val('yes')"/>&nbsp;
				<c:if test="${amsTransfer.status==-2}">
					<input id="btnSubmit2" class="btn btn-inverse" type="submit" value="销毁申请" onclick="$('#flag').val('no')"/>&nbsp;
				</c:if>
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		<c:if test="${not empty amsTransfer.procInsId}">
		<act:histoicFlow procInsId="${amsTransfer.act.procInsId}" />
		</c:if>
	</form:form>
</body>
</html>