<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>工程项目管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			var $m_btn = $('#y-modalBtnAdd');  //y-modalBtnAdd是button的id
		    var $modal = $('#y-myModalAdd');  //y-myModalAdd是弹出的遮罩层的id，通过这两个id进行绑定
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
		
		$("#showImg_approval").click(function(){ 
			var fileUrl = $("#showImg_approval").attr('fileUrl');  //文件路径
			$('#imgUrl').attr('src',fileUrl);
			$modal.modal({backdrop: 'static'});
		}) 
		$("#showImg_planningLicenseUrl").click(function(){ 
			var fileUrl = $("#showImg_planningLicenseUrl").attr('fileUrl');  //文件路径
			$('#imgUrl').attr('src',fileUrl);
			$modal.modal({backdrop: 'static'});
		}) 

		$("#showImg_landLicenseUrl").click(function(){ 
			var fileUrl = $("#showImg_landLicenseUrl").attr('fileUrl');  //文件路径
			$('#imgUrl').attr('src',fileUrl);
			$modal.modal({backdrop: 'static'});
		}) 
		$("#showImg_landPermitUrl").click(function(){ 
			var fileUrl = $("#showImg_landPermitUrl").attr('fileUrl');  //文件路径
			$('#imgUrl').attr('src',fileUrl);
			$modal.modal({backdrop: 'static'});
		})

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
			<div id='lssl' ><img alt=''  width="10%"  height="10%" src='${amsProjectInfo.approvalUrl}' ></div>
			<c:if test='${not empty amsProjectInfo.approvalUrl}'>
				<a href="#" id="showImg_approval"  fileUrl="${amsProjectInfo.approvalUrl}">查看附件</a>
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
			<div id='lssl' ><img alt=''  width="10%"  height="10%" src='${amsProjectInfo.planningLicenseUrl}' ></div>
				<c:if test='${not empty amsProjectInfo.planningLicenseUrl}'>
				<a href="#" id="showImg_planningLicenseUrl"  fileUrl="${amsProjectInfo.planningLicenseUrl}">查看附件</a>
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
			<div id='lssl' ><img alt=''  width="10%"  height="10%" src='${amsProjectInfo.landLicenseUrl}' ></div>
				<c:if test='${not empty amsProjectInfo.landLicenseUrl}'>
					<a href="#" id="showImg_landLicenseUrl"  fileUrl="${amsProjectInfo.landLicenseUrl}">查看附件</a>
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
			<div id='lssl' ><img alt=''  width="10%"  height="10%" src='${amsProjectInfo.landPermitUrl}' ></div>
			<c:if test='${not empty amsProjectInfo.landPermitUrl}'>
				<a href="#" id="showImg_landPermitUrl"  fileUrl="${amsProjectInfo.landPermitUrl}">查看附件</a>
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
			<label class="control-label">业务指导员：</label>
			<div class="controls" class="input-xxlarge required" >
				<sys:treeselect id="businessMan" name="businessMan" value="${amsProjectInfo.user.id}" labelName="user.name" labelValue="${amsProjectInfo.user.name}" 
							title="用户" url="/sys/office/userTree?type=3" cssClass="recipient" cssStyle="width:150px" 
							allowClear="true" notAllowSelectParent="true" smallBtn="false" /> 
							<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">审核意见：</label>
			<div class="controls">				
				<form:textarea path="opinion" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="受理" onclick="$('#flag').val('yes')"/>&nbsp;
			<input id="btnSubmit" class="btn btn-inverse" type="submit" value="驳回" onclick="$('#flag').val('no')"/>&nbsp;
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