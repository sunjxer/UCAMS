<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>工程项目管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		  $(document).ready(function() {
			 $.ajax({
			        type: "post",			     
			        url: '${ctx}/ams/amsProjectInfo/getAddress',
			        success: function (data) {
			            $("#address").val(data);
			        }
			 });			   
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
		  function close_help1(){  document.getElementById("s1").style.display="none";}
		  function close_help2(){  document.getElementById("s2").style.display="none";}
		  function close_help3(){  document.getElementById("s3").style.display="none";}
		  function close_help4(){  document.getElementById("s4").style.display="none";}
		     
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/ams/amsProjectInfo/">工程项目列表</a></li>
		<li class="active"><a href="${ctx}/ams/amsProjectInfo/form?id=${amsProjectInfo.id}">工程项目<shiro:hasPermission name="ams:amsProjectInfo:edit">${not empty amsProjectInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="ams:amsProjectInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="amsProjectInfo" action="${ctx}/ams/amsProjectInfo/save" method="post" class="form-horizontal"  enctype="multipart/form-data" >
		<form:hidden path="id"/>
		<form:hidden path="businessMan"/>
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
				<form:select path="projectType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getAmsDictList('unit_project_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<input id="business" value="${amsProjectInfo.businessMan}" type="hidden">
		<div class="control-group">
			<label class="control-label">项目名称：</label>
			<div class="controls">
				<input id="oldProjectName" name="oldProjectName" type="hidden" value="${amsProjectInfo.projectName}">
				<form:input path="projectName" htmlEscape="false" maxlength="30" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目地点：</label>
			<div class="controls">
				<sys:treeselect id="local" name="local" value="${amsProjectInfo.local}" labelName="" labelValue="${amsProjectInfo.area.name}"
					title="区域" url="/sys/area/treeData"  cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目地址：</label>
			<div class="controls">
				<form:input path="address" id="address" htmlEscape="false" maxlength="30" class="input-xlarge  required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">立项批准单位：</label>
			<div class="controls">
				<form:input path="projectApprovalUnit" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">设计单位：</label>
			<div class="controls">
				<form:input path="designUnit" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">勘察单位：</label>
			<div class="controls">
				<form:input path="prospectingUnit" htmlEscape="false" maxlength="30" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">监理单位：</label>
			<div class="controls">
				<form:input path="supervisionUnit" htmlEscape="false" maxlength="30" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">立项批准文号：</label>
			<div class="controls">
				<form:input path="approvalNumber" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">立项批准附件：</label>
			<c:if test='${not empty amsProjectInfo.projectName}'>
				<div id='s1' style="margin-left:180px;"><img alt=''  width="10%"  height="10%" src=' ${amsProjectInfo.approvalUrl}'></div> 
			</c:if>
			<div class="controls">
				<input type="file" name="file"  accept="image/jpeg,image/tiff" onclick="close_help1();"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">建设规划许可证号：</label>
			<div class="controls">
				<form:input path="planningLicenseNumber" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
			<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">建设规划许可证附件：</label>
			<c:if test='${not empty amsProjectInfo.projectName}'>
				<div id='s2' style="margin-left:180px;" ><img alt=''  width="10%"  height="10%" src='${amsProjectInfo.planningLicenseUrl}'></div>
			</c:if> 
			<div class="controls">
				<input type="file" name="file" accept="image/jpeg,image/tiff" onclick="close_help2();"/>
			</div> 
		</div>
		<div class="control-group">
			<label class="control-label">用地规划许可证号：</label>
			 <div class="controls">
				<form:input path="landLicenseNumber" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div> 
		</div>
		<div class="control-group">
			<label class="control-label">用地规划许可证附件：</label>
			<c:if test='${not empty amsProjectInfo.projectName}'>
				 <div id='s3' style="margin-left:180px;" ><img alt=''  width="10%"  height="10%" src='${amsProjectInfo.landPermitUrl}' ></div>
			</c:if> 
			<div class="controls">
				<input type="file" name="file" accept="image/jpeg,image/tiff" onclick="close_help3();"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">用地许可证号：</label>
			<div class="controls">
				<form:input path="landPermitNumber" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group" >
			<label class="control-label">用地许可证附件：</label>
			<c:if test='${not empty amsProjectInfo.projectName}'>
				 <div id='s4' style="margin-left:180px;" ><img alt=''  width="10%"  height="10%" src='${amsProjectInfo.landPermitUrl}' onclick="ImageSuofang(true)"></div>
			</c:if> 
			<div class="controls">
  				<input type="file" name="file"  accept="image/jpeg,image/tiff" id="uploadFile" onclick="close_help4();"/>
			</div>
		</div>
	<div class="control-group">
			<label class="control-label">开工日期：</label>
			<div class="controls">
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${amsProjectInfo.startDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					<span class="help-inline"><font color="red">*</font> </span>
		</div>
	</div>
	
		<div class="control-group">
			<label class="control-label">竣工日期：</label>
			<div class="controls">
			
				<input name="finishDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${amsProjectInfo.finishDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
	
		<div class="control-group" style="display: none;">
			<label class="control-label">著录扩展数据：</label>
			<div class="controls">
				<form:input path="descriptionJson" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
				<c:forEach items="${amsProjectInfo.amsDesExtendList}" var="amsDesExtend" varStatus="status">
		<div class="control-group">
			<label class="control-label">${amsDesExtend.comments}：</label>
			<div class="controls">
				<input type="hidden" id="amsDesExtendList[${status.index}]_id" name="amsDesExtendList[${status.index}].id" value="${amsDesExtend.id}"/>
				<input type="hidden" id="amsDesExtendList[${status.index}]_name" name="amsDesExtendList[${status.index}].name" value="${amsDesExtend.name}"/>
				<c:if test="${amsDesExtend.showType==\"text\"}">
				<input type="text" id="amsDesExtendList[${status.index}]_settings" name="amsDesExtendList[${status.index}].settings" value="${amsDesExtend.settings}" maxlength="100" class="input-small ${amsDesExtend.isNull==1?'required':''}"/>
				</c:if>
				<c:if test="${amsDesExtend.showType==\"textarea\"}">
				<textarea id="amsDesExtendList[${status.index}]_settings" name="amsDesExtendList[${status.index}].settings" maxlength="300" class="input-xxlarge required">${amsDesExtend.settings}</textarea>		
				</c:if>
				<c:if test="${amsDesExtend.showType==\"select\"}">
				<select id="amsDesExtendList[${status.index}]_settings" name="amsDesExtendList[${status.index}].settings" class=" ${amsDesExtend.isNull==1?'required':''} input-mini" style="width:95px;*width:75px" data-value="${amsDesExtend.settings}">
					<c:forEach items="${fns:getDictList('file_type')}" var="dict">
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
				<c:if test="${amsDesExtend.isNull==1}">
				<span class="help-inline"><font color="red">*</font> </span>
				</c:if>
			</div>
		</div> 
		</c:forEach> 
		<div class="form-actions">
			<c:if test="${amsProjectInfo.act.taskDefKey=='edit'}">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="重新提交" onclick="$('#flag').val('yes')"/>&nbsp;
			</c:if>
			<c:if test="${amsProjectInfo.act.taskDefKey!='edit'}">
			<shiro:hasPermission name="ams:amsProjectInfo:edit"><input class="btn btn-primary" type="submit" value="保 存" id="uploadBtn"/>  <input type="button" id="checkBtn" onclick="checksql()" class="btn btn-sm btn-danger" value="check" style="display:none;"/>&nbsp;</shiro:hasPermission>
				</c:if> 
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
		<c:if test='${not empty amsProjectInfo.projectName}'>
		<act:histoicFlow procInsId="${amsProjectInfo.act.procInsId}" />
		</c:if>
		<!-- 模态弹出窗内容 -->
	<div class="modal" id="y-myModalAdd" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true" style="display: none;">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">
	          <span aria-hidden="true">×</span>
	        </button>
	        <h4 class="modal-title">图片预览</h4>
	      </div>
	      <div class="modal-body" >
				<img id="imgUrl" width="100%"  height="100%" >
	      </div>
	    </div>
	  </div>
	</div>
</body>
</html>