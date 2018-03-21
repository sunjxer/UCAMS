<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>声像档案管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			var $m_btn = $('#y-modalBtnAdd');  //y-modalBtnAdd是button的id
		    var $modal = $('#y-myModalAdd');  //y-myModalAdd是弹出的遮罩层的id，通过这两个id进行绑定
			$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
			//初始化表单(显隐)
			if($('#code').val() == -1){ //编码为-1时,该数据为基础类型
				$('#type01').attr('selected','selected');
				
			}
			
			refreshParentFlag();
			//父类别列表控制
			$("#typeSeclect").change(function(){
				refreshParentFlag();
			});
			
			$("#unitProjectType").change(function(){
				var name = $("#unitProjectType").find("option:selected").text();
				//复制给name
				$('#name').val(name);
			});
			$("#btnImport").click(function(){
				/* $.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true}, 
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"}); */
				$modal.modal({backdrop: 'static'});
			});
		});
		
		function refreshParentFlag(){
			if($("#typeSeclect").val()==1){
				$("#parentDiv").hide();
				$("#codeDiv").hide();
				$("#createUnitDiv").hide();
				$('#idDiv').hide();
				$('#typeDiv').show();
			}else{
				$("#parentDiv").show();
				$("#codeDiv").show();
				$("#createUnitDiv").show();
				$('#typeDiv').hide();
				$('#idDiv').show();
			}
		}
		
		function downloadFile(url) {
			try {
				var elemIF = document.createElement("iframe");
				elemIF.src = url;
				elemIF.style.display = "none";
				document.body.appendChild(elemIF);
			} catch (e) {

			}
		}
	</script>
</head>
<body>
	
	
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/ams/amsArchiveMenu/list">类别列表</a></li>
		<li class="active"><a href="${ctx}/ams/amsArchiveMenu/form?id=${amsArchiveMenu.id}&parent.id=${amsArchiveMenuparent.id}">类别<shiro:hasPermission name="ams:amsArchiveMenu:edit">${not empty amsArchiveMenu.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="ams:amsArchiveMenu:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="amsArchiveMenu" action="${ctx}/ams/amsArchiveMenu/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
	 	<div class="control-group" >
			<label class="control-label">类型：</label>
			<div class="controls">
				<select id="typeSeclect"  name="flag" class="input-medium">
					<option id="type00" value="0">声像档案类型</option>
					<option id="type01" value="1">工程类型添加</option>
				</select>
			</div>
		</div>
		
		<div class="control-group" id="typeDiv">
			<label class="control-label">基础类型：</label>
			<div class="controls">
				<form:select id="unitProjectType" path="type" class="input-medium required">
					<form:option value=""  label="请选择"/>
					<form:options items="${fns:getAmsDictList('unit_project_type')}"
						itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select> 
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div> 
		<div class="control-group"  id=parentDiv>
			<label class="control-label">上级类别：</label>
			<div class="controls">
				<sys:treeselect id="parent" name="parent.id" value="${amsArchiveMenu.parent.id}" labelName="parent.name" labelValue="${amsArchiveMenu.parent.name}"
					title="类别" url="/ams/amsArchiveMenu/treeData" extId="${amsArchiveMenu.id}" cssClass=" input-xlarge required" allowClear="true"/>
					<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" >
			<label class="control-label">是否为最后子节点：</label>
			<div class="controls">
				<form:select id="isEndChild" path="isEndChild" class="input-medium required">
					<form:option value=""  label="请选择"/>
					<form:options items="${fns:getDictList('yes_no')}"
						itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select> 
			</div>
		</div>
		
		<div class="control-group" id ="idDiv">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-xlarge required "/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group"  id="codeDiv">
			<label class="control-label">编码：</label>
			<div class="controls">
				<form:input path="code" htmlEscape="false" maxlength="100" class="input-xlarge "/>
				<span class="help-inline"><font color="red">*</font> </span>
				<span class="help-inline"></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="ams:amsArchiveMenu:edit">
			<!-- <input id="btnImport" class="btn btn-primary" type="button" value="导入"/>&nbsp; -->
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>