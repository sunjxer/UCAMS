<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>文件管理</title>
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
		 function filefujianChange(target) {
			 var files=document.getElementById('q2').files;  
		        var size = files[0].size ; 
		        if(size>10485760){  
		        	 top.$.jBox.tip("附件不能大于10M");
		         target.value="";
		         return
		        }
		      }
	</script>
	
	<style type="text/css">
 td.titRight {background: #fafafa;
	text-align: right;
	padding-right: 2px;
	white-space: nowrap}
</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<%-- <li>
		<c:if test="${not empty amsFileInfo.unitProjectId&&not amsFileInfo.unitProjectId.equals('')}"><a href="${ctx}/ams/amsFileInfo/amsUnitProInfoList?id=${amsFileInfo.unitProjectId}&recordId=${amsFileInfo.recordId}">声像文件列表</a></c:if>
		<c:if test="${empty amsFileInfo.unitProjectId||amsFileInfo.unitProjectId.equals('')}"><a href="${ctx}/ams/amsFileInfo/amsProjectInfoList?id=${amsFileInfo.projectId}&recordId=${amsFileInfo.recordId}">声像文件列表</a></c:if>
		</li> --%>
		<li class="active">
		<a href="${ctx}/ams/amsFileInfo/form?id=${amsFileInfo.id}&projectId=${amsFileInfo.projectId}&unitProjectId=${amsFileInfo.unitProjectId}&recordId=${amsFileInfo.recordId}">声像档案<shiro:hasPermission name="ams:amsFileInfo:edit">${not empty amsFileInfo.id?'著录':'添加'}</shiro:hasPermission><shiro:lacksPermission name="ams:amsFileInfo:edit">查看</shiro:lacksPermission></a>
		</li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="amsFileInfo" action="${ctx}/ams/amsFileInfo/save" method="post" class="form-horizontal"  enctype="multipart/form-data">
		<form:hidden path="id"/>
		<form:hidden path="unitProjectId"/>
		<form:hidden path="projectId"/>
		<form:hidden path="recordId"/>
		<form:hidden path="exten1"/>
		<form:hidden path="structureJson"/>
		<form:hidden path="filePath"/>
		<form:hidden path="filesource"/>
		<form:hidden path="dongle"/>
		<form:hidden path="state"/>
		<sys:message content="${message}"/>	
		<table class="table-form">
			<tr>
				<td class="titRight">文件名：</td>
				<td><form:input path="fileName" htmlEscape="false" maxlength="30" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
				</td>
				<td class="titRight">文图号：</td>
				<td>
				<form:input path="fileNo" htmlEscape="false" maxlength="30" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
				</td>
			</tr>
			<tr>
				<td class="titRight">责任者：</td>
				<td>
				<form:input path="author" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
				</td>
				<td class="titRight">形成日期：</td>
				<td>
				<input name="formDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${amsFileInfo.formDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
				</td>
			</tr>
			<tr>
				<td class="titRight">文件份数：</td>
				<td>
				<form:input path="filecount" htmlEscape="false" maxlength="11"   class="input-xlarge  digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
				</td>
				<td class="titRight">文件类型：</td>
				<td>
				<form:select path="fileType" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('video_file_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
				</td>
			</tr>
		<tr>
			<td class="titRight">文件：</td>
			<td colspan="3"><input id="q2" type="file" name="file"  onchange="filefujianChange(this);" accept="video/mp4,audio/mpeg,image/jpeg" /></td>
		</tr>
		<tr>
			<td class="titRight">备注信息：</td>
			<td colspan="3">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
		</td>
		</tr>
		</table>
		<div class="form-actions">
		<c:if test="${empty amsFileInfo.groupId}">
			<shiro:hasPermission name="ams:amsFileInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
		</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>