<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>业务管理档案案卷文件管理</title>
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
		<c:if test="${formView!=1}">
			<li><a href="${ctx}/ams/amsWorkArchivesFiles/list?groupId=${amsWorkArchivesFiles.groupId}">档案案卷文件列表</a></li>
			<li class="active"><a href="${ctx}/ams/amsWorkArchivesFiles/form?id=${amsWorkArchivesFiles.id}&groupId=${amsWorkArchivesFiles.groupId}">档案案卷文件<shiro:hasPermission name="ams:amsWorkArchivesFiles:edit">${not empty amsWorkArchivesFiles.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="ams:amsWorkArchivesFiles:edit">查看</shiro:lacksPermission></a></li>
		</c:if>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="amsWorkArchivesFiles" action="${ctx}/ams/amsWorkArchivesFiles/save" method="post" class="form-horizontal" enctype="multipart/form-data">
		<form:hidden path="id"/>
		<form:hidden path="groupId"/>
		<sys:message content="${message}"/>		
		<table class="table-form">
			<tr>
				<td align="center" colspan="4" width="20%"><b>文件基本信息</b></td>
			</tr>
			<tr>
				<td class="titRight">文件名：</td>
				<td>
				<form:input path="fileName" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
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
					value="<fmt:formatDate value="${amsWorkArchivesFiles.formDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
				</td>
			</tr>
			<tr>
				<td class="titRight">文件份数：</td>
				<td>
				<form:input path="filecount" htmlEscape="false" maxlength="9"   class="input-xlarge  digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
				</td>

			<td class="titRight">文件格式：</td>

				<td>
				<form:select id="q1" path="fileType" class="input-xlarge required">
					
					<form:options  items="${fns:getDictList('file_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>	
 					
 			</form:select> 
 			
				<span class="help-inline"><font color="red">*</font> </span>

				</td>
		</tr>	

		<tr>
			<td class="titRight">文件：</td>
			
			<c:if test="${empty amsWorkArchivesFiles.filePath}">
				<td colspan="3"><input id="q2" type="file" name="file"  accept="image/png,application/pdf,application/msword,application/vnd.ms-works,application/vnd.ms-excel,image/tiff,image/jpeg" /></td>
			</c:if>
			<c:if test="${not empty amsWorkArchivesFiles.filePath && viewForm!=1}">
				<!-- iframe的src设置预览文件地址?file=地址 -->
				<c:if test="${amsWorkArchivesFiles.fileType==2}">
					<td colspan="3">
					<input id="q2" type="file" name="file" value="${amsWorkArchivesFiles.filePath}" accept="image/png,application/pdf,application/msword,application/vnd.ms-works,application/vnd.ms-excel,image/tiff,image/jpeg" />
					<img alt="" src="${amsWorkArchivesFiles.filePath}">
					</td>
				</c:if>
				<c:if test="${amsWorkArchivesFiles.fileType==1}">
					<td colspan="3">
						<input id="q2" type="file" name="file" value="${amsWorkArchivesFiles.filePath}" accept="image/png,application/pdf,application/msword,application/vnd.ms-works,application/vnd.ms-excel,image/tiff,image/jpeg" />
		 				<embed title="${amsWorkArchivesFiles.fileName}" class="kv-preview-data file-zoom-detail" src="${amsWorkArchivesFiles.filePath}" type="application/pdf" style="width: 98%; height: 600px; min-height: 480px;" internalinstanceid="67">
		 			</td>
		 		</c:if>
	 		</c:if>
			<c:if test="${not empty amsWorkArchivesFiles.filePath && viewForm==1}">
				<!-- iframe的src设置预览文件地址?file=地址 -->
				<c:if test="${amsWorkArchivesFiles.fileType==2}">
					<td colspan="3">
					<img alt="" src="${amsWorkArchivesFiles.filePath}">
					</td>
				</c:if>
				<c:if test="${amsWorkArchivesFiles.fileType==1}">
					<td colspan="3">
		 				<embed title="${amsWorkArchivesFiles.fileName}" class="kv-preview-data file-zoom-detail" src="${amsWorkArchivesFiles.filePath}" type="application/pdf" style="width: 98%; height: 600px; min-height: 480px;" internalinstanceid="67">
		 			</td>
		 		</c:if>
	 		</c:if>
		</tr>

		<tr>
			<td class="titRight">备注信息：</td>
			<td colspan="3">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
		</td>
		</tr>
		</table>
	
		<div class="form-actions">
			<c:if test="${formView!=1}">
				<shiro:hasPermission name="ams:amsWorkArchivesFiles:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			</c:if>	
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>