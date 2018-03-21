<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>预验收管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
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
			
			$(obj).parent().parent().remove();
			
		}
	</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="amsAcceptance" action="${ctx}/ams/amsInterface/saveReport" method="post" class="form-horizontal">
		<sys:message content="${message}"/>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="15%">错误类型</th>
				<th>错误内容</th>
				<th width="5%">状态<!-- 【0-正常错误，1-忽略(不打印)，2-录入】 --></th>
				<shiro:hasPermission name="ams:amsOfflineFile:upload"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		
		<tbody id="offlineFileReport">
		<tfoot>
			<tr><td colspan="11"><a href="javascript:" onclick="addRow('#offlineFileReport', amsPreRptRowIdx, amsPreRptTpl);amsPreRptRowIdx = amsPreRptRowIdx + 1;" class="btn">新增</a></td></tr>
		</tfoot>
	</table>
	<script type="text/template" id="amsPreRptTpl">
						<tr id="offlineFileReport{{idx}}">
							<td  width="15%">
								<select name="amsPreRptList[{{idx}}].errorType" class="required input-mini" style="width:120px;" data-value="{{row.errorType}}">
									<c:forEach items="${fns:getDictList('rptType')}" var="dict">
									  <option value="${dict.value}" ${dict.value==column.errortype?'selected':''} title="${dict.description}">${dict.label}</option>
									</c:forEach>
								</select>
							</td>
							<td>
								<input name="amsPreRptList[{{idx}}].error" type="text"  style="width:500px;" value="{{row.error}}" maxlength="500" class="input-small required"/>
							</td>
							
							<td>
								<select name="amsPreRptList[{{idx}}].state" class="required input-mini" style="width:90px;*width:75px" data-value="{{row.state}}">
									<c:forEach items="${fns:getDictList('rptState')}" var="dict">
									  <option value="${dict.value}" ${dict.value==column.state?'selected':''} title="${dict.description}">${dict.label}</option>
									</c:forEach>
								</select>
							</td>
							<td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#offlineFileReport{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td>
						</tr>
					</script>
					<script type="text/javascript">
						var amsPreRptRowIdx = 0, amsPreRptTpl = $("#amsPreRptTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(offlineFileReport)};
							for (var i=0; i<data.length; i++){
								addRow('#offlineFileReport', amsPreRptRowIdx, amsPreRptTpl, data[i]);
								amsPreRptRowIdx = amsPreRptRowIdx + 1;
							}
						});
					</script>
	</form:form>
</body>
</html>