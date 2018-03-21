<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>类别管理</title>
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
				//$(".selector").val("1");
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
	<!-- 模态弹出窗内容 -->
	<div class="modal" id="y-myModalAdd"  style="display: none ; width: 50%">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">
	          <span aria-hidden="true">×</span>
	        </button>
	        <h4 class="modal-title">归档一览表导入</h4>
	      </div>
	      <div class="modal-body" style="height: 40%">
	       <form id="importForm" action="${ctx}/ams/amsGenre/import" method="post" enctype="multipart/form-data"
					class="" style="text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
			<table>
				<tr>
					<td class="tit" width="20%">基础类型:</td>
					<td colspan="3"  style="text-align: left;">
						<select  class="input-medium required" id="unitProjectType" name="unitProjectType">
							<c:forEach items="${fns:getAmsDictList('unit_project_type')}" var="dict">
								<option value ="${dict.value}">${dict.label}</option>
							</c:forEach>
						</select> 
						<span class="help-inline"><font color="red">*</font> </span>
					</td>
				</tr>
				<tr>
				<td class="tit" width="20%">导入文件:</td>
				<td colspan="3"> 
					<br/>
					<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>
				 </td>
				</tr>
				</table>
				<div style="float: right; padding-right: 10px">
		      		<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
					<a href='javascript:downloadFile("${ctxFixed}/amsGenreExcelModel.xlsx");'>下载模板</a>
				</div>
				</form>
	     	</div>
		  </div>
	    </div>
	  </div>
	
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/ams/amsGenre/list">类别列表</a></li>
		<li class="active"><a href="${ctx}/ams/amsGenre/form?id=${amsGenre.id}&parent.id=${amsGenreparent.id}">类别<shiro:hasPermission name="ams:amsGenre:edit">${not empty amsGenre.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="ams:amsGenre:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="amsGenre" action="${ctx}/ams/amsGenre/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">类型：</label>
			<div class="controls">
				<select id="typeSeclect"  name="flag" class="input-medium">
					<option id="type00" value="0">归档表目录添加</option>
					<option id="type01" value="1">基础类型添加</option>
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
				<!-- <span class="help-inline"><font color="red">*</font> “是”代表此账号允许登录，“否”则表示此账号不允许登录</span> -->
			</div>
		</div>
		<div class="control-group"  id=parentDiv>
			<label class="control-label">上级类别：</label>
			<div class="controls">
				<sys:treeselect id="parent" name="parent.id" value="${amsGenre.parent.id}" labelName="parent.name" labelValue="${amsGenre.parent.name}"
					title="类别" url="/ams/amsGenre/treeData" extId="${amsGenre.id}" cssClass=" input-xlarge required" allowClear="true"/>
					<span class="help-inline"><font color="red">*</font> </span>
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
				<form:input path="code" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
				<span class="help-inline">例如: A1-1-3 </span>
			</div>
		</div>
		<div class="control-group"  id="createUnitDiv">
			<label class="control-label">生成单位：</label>
			<div class="controls">
				<%--<form:input path="roleType" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline" title="activiti有3种预定义的组类型：security-role、assignment、user 如果使用Activiti Explorer，需要security-role才能看到manage页签，需要assignment才能claim任务">
					工作流组用户组类型（工作流组用户组类型（监理单位：assignment、建设单位：security-role、施工单位：user）</span> --%>
				<form:select path="createUnit" class="input-medium">
					<form:option value="-1"  label="不设置生成单位"/>
					<form:option value="security-role">建设单位</form:option>
					<form:option value="assignment">监理单位</form:option>
					<form:option value="user">施工单位</form:option>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="ams:amsGenre:edit">
			<input id="btnImport" class="btn btn-primary" type="button" value="导入"/>&nbsp;
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>