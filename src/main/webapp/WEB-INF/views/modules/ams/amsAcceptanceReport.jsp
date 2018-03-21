<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
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
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出用户数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
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
	</script>
</head>
<body>
	 
	<form:form id="searchForm" modelAttribute="amsAcceptance" action="${ctx}/ams/amsAcceptance/export" method="post" class="breadcrumb form-search ">
				<form:hidden path="id"/>
	</form:form>
	<form:form id="inputForm" modelAttribute="amsAcceptance" action="${ctx}/ams/amsAcceptance/saveRpt" method="post" class="form-horizontal">
		<form:hidden path="project.id"/>
		<form:hidden path="id"/>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden id="flag" path="act.flag"/>
		<sys:message content="${message}"/>		
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="15%">错误类型</th>
				<th>错误内容</th>
				<th width="5%">状态<!-- 【0-正常错误，1-忽略(不打印)，2-录入】 --></th>
				<shiro:hasPermission name="ams:amsPreRpt:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		
		<tbody id="amsPreRptList">
		<tfoot>
			<tr><td colspan="11"><a href="javascript:" onclick="addRow('#amsPreRptList', amsPreRptRowIdx, amsPreRptTpl);amsPreRptRowIdx = amsPreRptRowIdx + 1;" class="btn">新增</a></td></tr>
		</tfoot>
	</table>
	<script type="text/template" id="amsPreRptTpl">//<!--
						<tr id="amsPreRptList{{idx}}">
							<td class="hide">
								<input id="amsPreRptList{{idx}}_id" name="amsPreRptList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="amsPreRptList{{idx}}_delFlag" name="amsPreRptList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td  width="15%"> 
								<select id="amsPreRptList{{idx}}_errorType" name="amsPreRptList[{{idx}}].errorType" class="required input-mini" style="width:120px;" data-value="{{row.errorType}}">
									<c:forEach items="${fns:getDictList('rptType')}" var="dict">
									  <option value="${dict.value}" ${dict.value==column.errorType?'selected':''} title="${dict.description}">${dict.label}</option>
									</c:forEach>
								</select>
							</td>
							<td>
								<input id="amsPreRptList{{idx}}_error" name="amsPreRptList[{{idx}}].error" type="text"  style="width:500px;" value="{{row.error}}" maxlength="500" class="input-small required"/>
							</td>
							
							<td>
								<select id="amsPreRptList{{idx}}_state" name="amsPreRptList[{{idx}}].state" class="required input-mini" style="width:90px;*width:75px" data-value="{{row.state}}">
									<c:forEach items="${fns:getDictList('rptState')}" var="dict">
									  <option value="${dict.value}" ${dict.value==column.state?'selected':''} title="${dict.description}">${dict.label}</option>
									</c:forEach>
								</select>
							</td>
							
							<td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#amsPreRptList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var amsPreRptRowIdx = 0, amsPreRptTpl = $("#amsPreRptTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(amsPreRptList)};
							for (var i=0; i<data.length; i++){
								addRow('#amsPreRptList', amsPreRptRowIdx, amsPreRptTpl, data[i]);
								amsPreRptRowIdx = amsPreRptRowIdx + 1;
							}
						});
					</script>
		<%-- <div class="form-actions">
			<shiro:hasPermission name="ams:amsAcceptance:edit">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保存"/>&nbsp;
			</shiro:hasPermission>
		</div> --%>
	</form:form>
</body>
</html>