<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>组卷管理</title>
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
		<li>
		<c:if test="${not empty amsArchivesInfo.projectId and empty amsArchivesInfo.unitProjectId}">
			<a href="${ctx}/ams/amsArchivesInfo/amsProjectInfoList?id=${amsArchivesInfo.projectId}">项目信息</a>			
		</c:if>
		<c:if test="${not empty amsArchivesInfo.unitProjectId}">
			<a href="${ctx}/ams/amsArchivesInfo/amsUnitProInfoList?id=${amsArchivesInfo.unitProjectId}">单位工程信息</a>			
		</c:if>
		</li>
		<li class="active"><a href="${ctx}/ams/amsArchivesInfo/form?id=${amsArchivesInfo.id}">案卷<shiro:hasPermission name="ams:amsArchivesInfo:edit">${not empty amsArchivesInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="ams:amsArchivesInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="amsArchivesInfo" action="${ctx}/ams/amsArchivesInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="projectId"/>
		<form:hidden path="unitProjectId"/>
		<%-- <form:hidden path="exten1"/> --%>
		<sys:message content="${message}"/>
		<table class="table-form">
						<tr>
							<td align="center" colspan="4" width="20%"><b>案卷基本信息</b></td>
						</tr>						
						<tr>
							<td class="tit" width="20%">案卷题名:</td>
							<td colspan="3">
							<form:input path="archivesName" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
							<span class="help-inline"><font color="red">*</font> </span>
							</td>
						</tr>
						<tr>
							<td class="tit" width="20%">编制单位:</td>
							<td>
							<form:input path="makeUnit" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
							<span class="help-inline"><font color="red">*</font> </span>
							</td>
							<td class="tit" width="20%">档号:</td>
							<td>
							<form:input path="archivesCode" htmlEscape="false" maxlength="50" class="input-xlarge "/>		
							</td>							
						</tr>
						<tr>
							<td class="tit" width="20%">数量:</td>
							<td>
							<form:input path="filesCount" htmlEscape="false" maxlength="9" class="input-xlarge required" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
							<span class="help-inline"><font color="red">*</font> </span>
							</td>
							<td class="tit" width="20%">载体类型:</td>
							<td>
							<form:select path="carrierType" class="input-xlarge required">
								<form:option value="" label=""/>
								<form:options items="${fns:getDictList('carrier_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
							</form:select>
							<span class="help-inline"><font color="red">*</font> </span>
							</td>
						</tr>
						<tr>
							<td class="tit" width="20%">卷内文件起始时间:</td>
							<td>
							<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
								value="<fmt:formatDate value="${amsArchivesInfo.startDate}" pattern="yyyy-MM-dd"/>"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
							</td>
							<td class="tit" width="20%">卷内文件终止时间:</td>
							<td>
							<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
								value="<fmt:formatDate value="${amsArchivesInfo.endDate}" pattern="yyyy-MM-dd"/>"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
							</td>
						</tr>
						<tr>
							<td class="tit" width="20%">立卷人:</td>
							<td>
							<form:input path="author" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
							<span class="help-inline"><font color="red">*</font> </span>
							</td>
							<td class="tit" width="20%">立卷日期:</td>
							<td>
							<input name="makeDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
								value="<fmt:formatDate value="${amsArchivesInfo.makeDate}" pattern="yyyy-MM-dd"/>"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
							</td>
						</tr>
						<tr>
							<td class="tit" width="20%">审核人:</td>
							<td>
							<form:input path="auditor" htmlEscape="false" maxlength="100" class="input-xlarge "/>
							</td>
							<td class="tit" width="20%">审核日期:</td>
							<td>
							<input name="auditDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
								value="<fmt:formatDate value="${amsArchivesInfo.auditDate}" pattern="yyyy-MM-dd"/>"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>		
							</td>
						</tr>
						<tr>
							<td class="tit" width="20%">保管期限:</td>
							<td>
							<form:select path="storagePeriod" class="input-xlarge required">
								<form:option value="" label=""/>
								<form:options items="${fns:getDictList('storage_period')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
							</form:select>
							<span class="help-inline"><font color="red">*</font> </span>
							</td>
							<td class="tit" width="20%">密级:</td>
							<td>
							<form:select path="degreeSecrets" class="input-xlarge required">
								<form:option value="" label=""/>
								<form:options items="${fns:getDictList('degree_secrets')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
							</form:select>		
							<span class="help-inline"><font color="red">*</font> </span>
							</td>
						</tr>
						<tr>
							<td class="tit" width="20%">主题词:</td>
							<td colspan="3">
							<form:input path="mainTitle" htmlEscape="false" maxlength="200" class="input-xlarge required"/>
							<span class="help-inline"><font color="red">*</font> </span>
							</td>
						</tr>
						<tr>
							<td class="tit" width="20%">组卷情况说明:</td>
							<td colspan="3">
							<form:input path="archivesExplain" htmlEscape="false" maxlength="500" class="input-xlarge "/>
							</td>
						</tr>
						<tr>
							<td class="tit" width="20%">备注:</td>
							<td colspan="3">
							<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
							</td>
						</tr>						
						</table>
						<table class="table-form">
						<tr>
							<td align="center" colspan="4" width="20%"><b>案卷著录扩展信息</b></td>
						</tr>
						<c:forEach items="${amsArchivesInfo.amsDesExtendList}" var="amsDesExtend" varStatus="status">
							<c:if test="${status.index%2==0}">
							<tr>
							</c:if>
								<td class="tit">${amsDesExtend.comments}：</td>
								<td>
									<input type="hidden" id="amsDesExtendList[${status.index}]_id" name="amsDesExtendList[${status.index}].id" value="${amsDesExtend.id}"/>
									<input type="hidden" id="amsDesExtendList[${status.index}]_name" name="amsDesExtendList[${status.index}].name" value="${amsDesExtend.name}"/>
									<c:if test="${amsDesExtend.showType==\"text\"}">
									<input type="text" id="amsDesExtendList[${status.index}]_settings" name="amsDesExtendList[${status.index}].settings" value="${amsDesExtend.settings}" maxlength="100" class="input-xlarge ${amsDesExtend.isNull==1?'required':''}"/>
									</c:if>
									<c:if test="${amsDesExtend.showType==\"textarea\"}">
									<textarea id="amsDesExtendList[${status.index}]_settings" name="amsDesExtendList[${status.index}].settings" maxlength="300" class="input-xxlarge ${amsDesExtend.isNull==1?'required':''}">${amsDesExtend.settings}</textarea>		
									</c:if>
									<c:if test="${amsDesExtend.showType==\"select\"}">
									<select id="amsDesExtendList[${status.index}]_settings" name="amsDesExtendList[${status.index}].settings" class=" ${amsDesExtend.isNull==1?'required':''} input-mini" style="width:95px;*width:75px" data-value="${amsDesExtend.settings}">
										<c:forEach items="${fns:getDictList(amsDesExtend.dictType)}" var="dict">
										<option value="${dict.value}" ${dict.value==amsDesExtend.settings?'selected':''} title="${dict.description}">${dict.label}</option>
										</c:forEach>
									</select>
									</c:if>
									<c:if test="${amsDesExtend.showType==\"date\"}">
									<input id="amsDesExtendList[${status.index}]_settings" name="amsDesExtendList[${status.index}].settings" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate ${amsDesExtend.isNull==1?'required':''} "
										value="${amsDesExtend.settings}"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
									</c:if>
									<c:if test="${amsDesExtend.showType==\"tree\"}">
									<sys:treeselect id="parent"  name="amsDesExtendList[${status.index}].settings"  value="${amsDesExtend.settings}" labelName="${amsDesExtend.settings}" labelValue="${amsDesExtend.settings}"
										title="${amsDesExtend.name}" url="" extId="" cssClass="" allowClear="true"/>
									</c:if>
									<c:if  test="${amsDesExtend.showType==\"number\"}">
											 <input type="number" id="amsDesExtendList[${status.index}]_settings" name="amsDesExtendList[${status.index}].settings" maxlength="10" value="${amsDesExtend.settings}"/>
									</c:if>
									<c:if test="${amsDesExtend.isNull==1}">
									<span class="help-inline"><font color="red">*</font> </span>
									</c:if>
								</td>
							<c:if test="${status.index%2==1}">
							<tr>
							</c:if>
						</c:forEach>
						</table>		
		<div class="form-actions">
		<shiro:hasPermission name="ams:amsArchivesInfo:edit">
			<c:if test="${amsArchivesInfo.amsAcceptance.status == null || amsArchivesInfo.amsAcceptance.status == ''}">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</c:if>	
			<c:if test="${amsArchivesInfo.amsAcceptance.status == 1 || amsArchivesInfo.amsAcceptance.status == 2 || amsArchivesInfo.amsAcceptance.status == 3}">			
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" disabled="disabled" title="已提交预验收，无法修改案卷基本信息"/>&nbsp;
			</c:if>	
		</shiro:hasPermission>	
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>			
		</div>
	</form:form>
</body>
</html>