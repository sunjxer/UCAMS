<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>工程专业配置记录管理</title>
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
			//方案类型变更控制
			refreshPlanFlag(); //初始化
			$("#programType").change(function(){
				refreshPlanFlag();
			});
		});
		
		function addRow(list, idx, tpl, row){
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
			});
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("value").split(',');
				for (var i=0; i<ss.length; i++){
					$(this).attr("checked","checked");
					//不选中
					if("1" == ss[i]){	
						$(this).attr("checked","checked");
					}
					//选中
					if("0" == ss[i]){
						$(this).removeAttr("checked");
					}
				}
			});
		}
		function delRow(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			if (id.val() == ""){
				$(obj).parent().parent().remove();
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
			}
		}	
		function checkBoxchange(obj){
			if(obj.checked){
				$('#'+obj.name).val("1")
			}else{
				$('#'+obj.name).val("0")
			}
		}
		function refreshPlanFlag(){
			if($("#programType").val()==1){
				$("#unitProjectType").show();
			}else if($("#programType").val()==0){
				$("#unitProjectType").show();
			}
			else{
				$("#unitProjectType").hide();
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/ams/amsDesProgram/">工程专业配置记录列表</a></li>
		<li class="active"><a href="${ctx}/ams/amsDesProgram/form?id=${amsDesProgram.id}">工程专业配置记录<shiro:hasPermission name="ams:amsDesProgram:edit">${not empty amsDesProgram.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="ams:amsDesProgram:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="amsDesProgram" action="${ctx}/ams/amsDesProgram/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">方案类型：</label>
			<div class="controls">
				<form:select id="programType" path="programType" class="input-medium">
					<form:options items="${fns:getAmsDictList('ams_plan_type')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
			</div>
		</div>
		<div class="control-group"  id="unitProjectType">
			<label class="control-label">单位工程类型：</label>
			<div class="controls">
				<form:select  path="unitProjectType" class="input-medium">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getAmsDictList('unit_project_type')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="200" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">描述：</label>
			<div class="controls">
				<form:input path="comments" htmlEscape="false" maxlength="500" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否启用：</label>
			<div class="controls">
				<form:select path="useable">
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> “是”代表启用，“否”则表示不启用</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
			<div class="control-group">
				<label class="control-label">工程专业配置内容表：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>录入项名称</th>
								<th>描述</th>
								<th>字段类型</th>
								<th>字段长度</th>
								<th>录入数据类型</th>	
								<th>下拉框类型</th>
								<th>可空</th>
								<th>排序（升序）</th>
								<th>备注信息</th>
								<shiro:hasPermission name="ams:amsDesProgram:edit"><th width="10" >&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="amsDesExtendList">
						</tbody>
						<shiro:hasPermission name="ams:amsDesProgram:edit"><tfoot>
							<tr><td colspan="11"><a href="javascript:" onclick="addRow('#amsDesExtendList', amsDesExtendRowIdx, amsDesExtendTpl);amsDesExtendRowIdx = amsDesExtendRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="amsDesExtendTpl">//<!--
						<tr id="amsDesExtendList{{idx}}">
							<td class="hide">
								<input id="amsDesExtendList{{idx}}_id" name="amsDesExtendList[{{idx}}].id" type="hidden" value="{{row.id}}" />
								<input id="amsDesExtendList{{idx}}_delFlag" name="amsDesExtendList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="amsDesExtendList{{idx}}_name" name="amsDesExtendList[{{idx}}].name" type="text" value="{{row.name}}" maxlength="500" class="input-small abc required"/>
							</td>
							<td>
								<input id="amsDesExtendList{{idx}}_comments" name="amsDesExtendList[{{idx}}].comments" type="text" value="{{row.comments}}" maxlength="100" class="input-small userName required"/>
							</td>
							<td>
								<select id="amsDesExtendList{{idx}}_columnType" name="amsDesExtendList[{{idx}}].columnType" class="required input-mini" style="width:95px;*width:75px" data-value="{{row.columnType}}">
									<c:forEach items="${columnTypeList}" var="dict">
									  <option value="${dict.value}" ${dict.value==column.javaType?'selected':''} title="${dict.description}">${dict.label}</option>
									</c:forEach>
								</select>
							</td>
							<td>
								<input id="amsDesExtendList{{idx}}_columnLength" name="amsDesExtendList[{{idx}}].columnLength" type="text" value="{{row.columnLength}}" maxlength="100" class="input-small number required"/>
							</td>
							<td>
								<select id="amsDesExtendList{{idx}}_showType" name="amsDesExtendList[{{idx}}].showType" class="required input-mini" style="width:95px;*width:75px" data-value="{{row.showType}}">
									<c:forEach items="${selectsList}" var="dict">
									  <option value="${dict.value}" ${dict.value==column.javaType?'selected':''} title="${dict.description}">${dict.label}</option>
									</c:forEach>
								</select>
							</td>
							<td>
								<input id="amsDesExtendList{{idx}}_dictType" name="amsDesExtendList[{{idx}}].dictType" type="text" value="{{row.dictType}}" maxlength="200" class="input-small "/>
							</td>
							<td>
								<input id="amsDesExtendList{{idx}}_isNullShow" name="amsDesExtendList{{idx}}_isNull" type="checkbox"  onclick="javascript:checkBoxchange(this)" value="{{row.isNull}}"  maxlength="200" class="input-small "/>
								<input id="amsDesExtendList{{idx}}_isNull" name="amsDesExtendList[{{idx}}].isNull" type="hidden" value="{{row.isNull}}" />
							</td>
							<td>
								<input id="amsDesExtendList{{idx}}_sort" name="amsDesExtendList[{{idx}}].sort" type="text" value="{{row.sort}}" class="input-small number required"/>
							</td>
							<td>						
							    <input id="amsDesExtendList{{idx}}_remarks" name="amsDesExtendList[{{idx}}].remarks" type="text" value="{{row.remarks}}" class="input-small required"/>
							</td>
							<shiro:hasPermission name="ams:amsDesProgram:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#amsDesExtendList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var amsDesExtendRowIdx = 0, amsDesExtendTpl = $("#amsDesExtendTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(amsDesProgram.amsDesExtendList)};
							for (var i=0; i<data.length; i++){
								addRow('#amsDesExtendList', amsDesExtendRowIdx, amsDesExtendTpl, data[i]);
								amsDesExtendRowIdx = amsDesExtendRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
		<div class="form-actions">
			<shiro:hasPermission name="ams:amsDesProgram:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>