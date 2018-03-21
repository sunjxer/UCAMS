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
<%-- 		<li><a href="${ctx}/ams/amsFileInfo/amsUnitProInfoList?id=${amsFileInfo.unitProjectId}">文件列表</a></li>
 --%>		<li class="active"><a href="${ctx}/ams/amsFileInfo/form?id=${amsFileInfo.id}&unitProjectId=${amsFileInfo.unitProjectId}">资料档案<shiro:hasPermission name="ams:amsFileInfo:edit">${not empty amsFileInfo.id?'著录':'添加'}</shiro:hasPermission><shiro:lacksPermission name="ams:amsFileInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="amsFileInfo" action="${ctx}/ams/amsFileInfo/save" method="post" class="form-horizontal" enctype="multipart/form-data">
		<form:hidden path="id"/>
		<form:hidden path="unitProjectId"/>
		<form:hidden path="projectId"/>
		<form:hidden path="recordId"/>
		<form:hidden path="structureJson"/>
		<form:hidden path="filesource"/>
		<form:hidden path="dongle"/>
		<form:hidden path="state"/>
		<sys:message content="${message}"/>	
		<table class="table-form">
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
					value="<fmt:formatDate value="${amsFileInfo.formDate}" pattern="yyyy-MM-dd"/>"
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

		<c:forEach items="${amsFileInfo.amsDesExtendList}" var="amsDesExtend" varStatus="status">
		<c:if test="${status.index%2==0}">
		<tr>
		</c:if>
			<td class="titRight">${amsDesExtend.comments}:</td>
			<td>
				<input type="hidden" id="amsDesExtendList[${status.index}]_id" name="amsDesExtendList[${status.index}].id" value="${amsDesExtend.id}"/>
				<input type="hidden" id="amsDesExtendList[${status.index}]_name" name="amsDesExtendList[${status.index}].name" value="${amsDesExtend.name}"/>
				<c:if test="${amsDesExtend.showType==\"text\"}">
				<input type="text" id="amsDesExtendList[${status.index}]_settings" name="amsDesExtendList[${status.index}].settings" value="${amsDesExtend.settings}" maxlength="100" class="input-xlarge ${not empty amsDesExtend.isNull&&amsDesExtend.isNull==0?'required':''}"/>
				</c:if>
				<c:if test="${amsDesExtend.showType==\"textarea\"}">
				<textarea id="amsDesExtendList[${status.index}]_settings" name="amsDesExtendList[${status.index}].settings" maxlength="300" class="input-xxlarge ${not empty amsDesExtend.isNull&&amsDesExtend.isNull==0?'required':''}">${amsDesExtend.settings}</textarea>		
				</c:if>
				<c:if test="${amsDesExtend.showType==\"select\"}">
				<select id="amsDesExtendList[${status.index}]_settings" name="amsDesExtendList[${status.index}].settings" class=" ${not empty amsDesExtend.isNull&&amsDesExtend.isNull==0?'required':''} input-mini" style="width:95px;*width:75px" data-value="${amsDesExtend.settings}">
					<c:forEach items="${fns:getDictList(amsDesExtend.dictType)}" var="dict">
					<option value="${dict.value}" ${dict.value==amsDesExtend.settings?'selected':''} title="${dict.description}">${dict.label}</option>
					</c:forEach>
				</select>
				</c:if>
				<c:if test="${amsDesExtend.showType==\"date\"}">
				<input id="amsDesExtendList[${status.index}]_settings" name="amsDesExtendList[${status.index}].settings" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate ${not empty amsDesExtend.isNull&&amsDesExtend.isNull==0?'required':''} "
					value="${amsDesExtend.settings}"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				</c:if>
				<c:if test="${amsDesExtend.showType==\"tree\"}">
				<sys:treeselect id="parent"  name="amsDesExtendList[${status.index}].settings"  value="${amsDesExtend.settings}" labelName="${amsDesExtend.settings}" labelValue="${amsDesExtend.settings}"
					title="${amsDesExtend.name}" url="" extId="" cssClass="" allowClear="true"/>
				</c:if>
				<c:if test="${not empty amsDesExtend.isNull&&amsDesExtend.isNull==0}">
				<span class="help-inline"><font color="red">*</font> </span>
				</c:if>
			</td>
		<c:if test="${status.index%2==1}">
		<tr>
		</c:if>
		</c:forEach> 
		<tr>
			<%-- <td class="titRight">归档类别：</td>
			<td >
			<form:select path="recordId" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${amsFileInfo.amsArchiveRulesList}" itemLabel="fileName" itemValue="id" htmlEscape="false"/>
				</form:select>		</td> --%>
			<td class="titRight">文件：</td>
			<td colspan="3"><input id="q2" type="file" name="file" onchange="filefujianChange(this);"  accept="application/pdf,image/tiff,image/jpeg" /></td>
				
		</tr>
		<tr>
			<td class="titRight">备注信息：</td>
			<td colspan="3">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
		</td>
		</tr>
		</table>
		<div class="form-actions">
			<shiro:hasPermission name="ams:amsFileInfo:edit">
			<c:if test="${amsFileInfo.state==0|| empty amsFileInfo.state}">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
			</c:if>
			
			&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>