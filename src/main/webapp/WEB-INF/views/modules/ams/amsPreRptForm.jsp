<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>检查报告管理</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/common/report.css" rel="stylesheet" />
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
		<li><a href="${ctx}/ams/amsPreRpt/">报表方案列表</a></li>
		<li class="active"><a href="${ctx}/ams/amsPreRpt/form?id=${amsPreRpt.id}">报表方案<shiro:hasPermission name="ams:amsPreRpt:edit">${not empty amsPreRpt.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="ams:amsPreRpt:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="amsPreRpt" action="${ctx}/ams/amsPreRpt/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">方案名称：</label>
			<div class="controls">
				<form:input path="errorType" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
				<span class="help-inline" title="方案名称需要简单描述方案用途">
					方案名称需要简单描述方案用途</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">方案类型：</label>
			<div class="controls">
				<form:select path="" class="input">
					<form:options items="${fns:getAmsDictList('ams_test_line')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
				<span class="help-inline" title="该类型分类是以报表类型进行区分">
					该类型分类是以报表类型进行区分</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否配图：</label>
			<div class="controls">
				<form:select path="">
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
				<span class="help-inline" title="该类型分类是以报表类型进行区分">
					是:  统计式报表带有统计图  - 否: 统计式报表不带统计图 </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否启用：</label>
			<div class="controls">
				<form:select path="">
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
				<span class="help-inline" title="该类型分类是以报表类型进行区分">
					是:  启用方案  - 否:  禁用方案 </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目级：</label>
			<div class="controls">
				<input id="screahX" class="btn icon-plus" type="button" value="＋ 搜索项-添加 "/>
				<div style="width:  666px">
				<span class="label  span_add"  >项目名称 &nbsp;<a href="#" style="font-size: 16px;color: white;">×</a></span>
				</div>
			</div>
			<div class="line_05">———————————————————————————————————————————————————————————————————————————————————</div>
			<div class="controls">
				<input id="screahX" class="btn" type="button" value="＋ 显示项-添加 "/>
				<div style="width:  666px">
				<span class="label span_add"  >项目名称&nbsp; <a href="#" style="font-size: 16px;color: white;">×</a></span>
				</div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位工程级：</label>
			<div class="controls">
				<input id="screahX" class="btn" type="button" value="＋ 搜索项-添加 "/>
			</div>
			<div class="line_05">———————————————————————————————————————————————————————————————————————————————————</div>
			<div class="controls">
				<input id="screahX" class="btn" type="button" value="＋ 显示项-添加 "/>
				<div style="width:  666px">
				<span class="label  span_add"  >单位工程名称&nbsp; <a href="#" style="font-size: 16px;color: white;">×</a></span>
				</div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">案卷级：</label>
			<div class="controls">
				<input id="screahX" class="btn" type="button" value="＋ 搜索项-添加 "/>
				<div style="width:  666px">
				<span class="label  span_add" >案卷名称&nbsp; <a href="#" style="font-size: 16px">×</a></span>
				</div>
			</div>
			<div class="line_05">———————————————————————————————————————————————————————————————————————————————————</div>
			<div class="controls">
				<input id="screahX" class="btn" type="button" value="＋ 显示项-添加 "/>
				<div style="width:  666px">
				<span class="label span_add" >文件URL&nbsp; <a href="#" style="font-size: 16px;color: white;">×</a></span>
				</div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="ams:amsPreRpt:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>