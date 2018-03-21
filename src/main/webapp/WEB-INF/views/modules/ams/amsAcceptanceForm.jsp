<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page import="java.util.*" %>
<html>
<head>
	<title>预验收管理</title>
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
		<li><a href="${ctx}/ams/amsAcceptance/list?project.id=${amsAcceptance.project.id }&type=${amsAcceptance.type}">预验收列表</a></li>
		<li class="active"><a href="${ctx}/ams/amsAcceptance/form?id=${amsAcceptance.id}&project.id=${amsAcceptance.project.id }&type=${amsAcceptance.type}">预验收<shiro:hasPermission name="ams:amsAcceptance:edit">${not empty amsAcceptance.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="ams:amsAcceptance:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="amsAcceptance" action="${ctx}/ams/amsAcceptance/save" method="post" class="form-horizontal">
		<form:hidden path="project.id"/>
		<form:hidden path="type"/>
		<form:hidden path="id"/>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden id="flag" path="act.flag"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">预验收申请日期：</label>
			<div class="controls">
			<c:if test="${empty amsAcceptance.id}">
				<input name="preAcceptanceApplyDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate  value="<%=new Date()%>" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</c:if>
			<c:if test="${not empty amsAcceptance.id}">
				<input name="preAcceptanceApplyDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${amsAcceptance.preAcceptanceApplyDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</c:if>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">预验收申请人：</label>
			<div class="controls">
			<sys:treeselect id="user" name="user.id" value="${amsAcceptance.user.id}" labelName="user.name" labelValue="${amsAcceptance.user.name}" 
							title="用户" url="/sys/office/userTree?type=3" cssClass="required recipient" cssStyle="width:150px" 
							allowClear="true" notAllowSelectParent="true" smallBtn="false"/> 
<%-- 				<form:input path="preAcceptanceApplicant" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
 --%>				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<c:if test="${0==amsAcceptance.type}"> 
		<div class="control-group">
		<div style="text-align:center;border-top: 1px solid #eeeeee;background-color: #f5f5f5; padding: 8px 8px 4px 8px;">
		<b>项目单位工程列表</b>
		</div>
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="5%">选择</th>
				<th>单位工程编号</th>
				<th>单位工程名称</th>
				<th>单位工程类型</th>
				<!-- <th>规划许可证号</th> -->
				<th>施工许可证号</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${amsAcceptance.amsUnitProInfoList}" var="amsUnitProInfo">
			<tr>
				<td>
				<form:checkbox path="projectString" checked="${amsUnitProInfo.exten5}" value="${amsUnitProInfo.id}"/>
				</td>
				<td>
					${amsUnitProInfo.unitProjectNo}
				</td>
				<td>
					${amsUnitProInfo.unitProjectName}
				</td>
				<td>
					${fns:getAmsDictLabel(amsUnitProInfo.unitProjectType, 'unit_project_type', '')}
				</td>
				<%-- <td>
					${amsUnitProInfo.planningLicenseNumber}
				</td> --%>
				<td>
					${amsUnitProInfo.constructionPermitNumber}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
		</div>
		
		<div class="control-group">
		<div style="text-align:center;border-top: 1px solid #eeeeee;background-color: #f5f5f5; padding: 8px 8px 4px 8px;">
		<b>建设工程规划</b>
		</div>
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="5%">选择</th>
				<th>工程名称</th>
				<th>工程地点</th>
				<th>建设单位</th>
				<th>立项批准单位</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${amsAcceptance.amsConstructDesList}" var="amsConstructDes">
			<tr>
				<td>
				<form:checkbox path="accString" checked="${amsConstructDes.exten5}" value="${amsConstructDes.id}"/>
				</td>
				<td>
					${amsConstructDes.projectName}
				</td>
				<td>
					${amsConstructDes.address}
				</td>
				<td>
					${amsConstructDes.constructionUnit}
				</td>
				<td>
					${amsConstructDes.projectApprovalUnit}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
		</div>
		
		<div class="control-group">
		<div style="text-align:center;border-top: 1px solid #eeeeee;background-color: #f5f5f5; padding: 8px 8px 4px 8px;">
		<b>建设用地规划</b>
		</div>
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="5%">选择</th>
				<th>工程名称</th>
				<th>工程地点</th>
				<th>用地单位</th>
				<th>被征单位</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${amsAcceptance.amsLandDesList}" var="amsLandDes">
			<tr>
				<td>
				<form:checkbox path="lanString" checked="${amsLandDes.exten5}" value="${amsLandDes.id}"/>
				</td>
				<td>
					${amsLandDes.projectName}
				</td>
				<td>
					${amsLandDes.address}
				</td>
				<td>
					${amsLandDes.landUseUnit}
				</td>
				<td>
					${amsLandDes.expropriatedUnit}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
		</div>
		
		<div class="control-group">
		<div style="text-align:center;border-top: 1px solid #eeeeee;background-color: #f5f5f5; padding: 8px 8px 4px 8px;">
		<b>项目案卷列表</b>
		</div>
		<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="5%">选择</th>
				<th>案卷题名</th>
				<th>编制单位</th>
				<th>载体类型</th>				
				<th>立卷人</th>
				<th>立卷日期</th>
				<!-- <th>顺序号</th> -->
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${amsAcceptance.amsArchivesInfoList}" var="amsArchivesInfo">
			<tr>
				<td>
				<form:checkbox path="archiverString"  checked="${amsArchivesInfo.exten5}"  name="exten2" value="${amsArchivesInfo.id}"/>
				</td>
				<td>
					${amsArchivesInfo.archivesName}
				</td>
				<td>${amsArchivesInfo.makeUnit}</td>
				<td>${fns:getDictLabel(amsArchivesInfo.carrierType, 'carrier_type', '')}</td>				
				<td>${amsArchivesInfo.author}</td>
				<td><fmt:formatDate value="${amsArchivesInfo.makeDate}" pattern="yyyy-MM-dd"/></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
		</div>
		</c:if>
		<c:if test="${1==amsAcceptance.type}"> 
		<div class="control-group">
		<div style="text-align:center;border-top: 1px solid #eeeeee;background-color: #f5f5f5; padding: 8px 8px 4px 8px;">
		<b>归档目录</b>
		</div>
		<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="5%">选择</th>
				<th>归档类别</th>
				<!-- <th>编制单位</th>
				<th>载体类型</th>				
				<th>立卷人</th>
				<th>立卷日期</th> -->
				<!-- <th>顺序号</th> -->
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${amsAcceptance.videoMenuList}" var="amsArchivesInfo">
			<tr>
				<td>
				<form:checkbox path="videoMenuString"  checked="${amsArchivesInfo.exten5}"  name="exten2" value="${amsArchivesInfo.id}"/>
				</td>
				<td>
					${amsArchivesInfo.name}
				</td>
				<%-- <td>${amsArchivesInfo.makeUnit}</td>
				<td>${fns:getDictLabel(amsArchivesInfo.carrierType, 'carrier_type', '')}</td>				
				<td>${amsArchivesInfo.author}</td>
				<td><fmt:formatDate value="${amsArchivesInfo.makeDate}" pattern="yyyy-MM-dd"/></td> --%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
		</div>
		</c:if>
		<div class="form-actions">
			<shiro:hasPermission name="ams:amsAcceptance:edit">
			<c:if test="${empty amsAcceptance.procInsId}">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保存草稿"/>&nbsp;
			</c:if>
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保存并提交申请" onclick="$('#flag').val('yes')"/>&nbsp;
				<c:if test="${amsAcceptance.status==-2}">
					<input id="btnSubmit2" class="btn btn-inverse" type="submit" value="销毁申请" onclick="$('#flag').val('no')"/>&nbsp;
				</c:if>
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		<c:if test="${not empty amsAcceptance.procInsId}">
			<act:histoicFlow procInsId="${amsAcceptance.act.procInsId}" />
		</c:if>
	</form:form>
</body>
</html>