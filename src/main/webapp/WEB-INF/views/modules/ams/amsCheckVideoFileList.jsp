<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>文件管理</title>
<meta name="decorator" content="default" />
<%@include file="/WEB-INF/views/include/treeview.jsp"%>
<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
			var data = ${fns:toJson(list)}, ids = [], rootIds = [];
			for (var i=0; i<data.length; i++){
				ids.push(data[i].id);
			}
			ids = ',' + ids.join(',') + ',';
			for (var i=0; i<data.length; i++){
				//alert(ids.indexOf(','+data[i].parentId+',')+data[i].name);
				if (ids.indexOf(','+data[i].parentId+',') == -1){
					if ((','+rootIds.join(',')+',').indexOf(','+data[i].parentId+',') == -1){
						rootIds.push(data[i].parentId);
					}
				}
			}
			
			for (var i=0; i<rootIds.length; i++){
				addRow("#treeTableList", tpl, data, rootIds[i], true);
			}
			$("#treeTable").treeTable({expandLevel : 5});
			$(":radio[aa='record']").click(function(){
				$("#searchForm").submit();
			});
			
			
		});
		
		function checkFile(data){
			 $.ajax({
				 type:"POST",                      
				 url:"${ctx}/ams/amsCheckVideoFile/checking",          
				 data:{ 
					 projectId:$('#projectId').val(),
					 recordId:$(data).attr('recordId')
					 },   
				 dataType:"json",                
				 success:function(data){   
					 if(data.msg){
						 $('#messageInfo').html("该目录下无声像文件 !!");
						 $('#messageBox').addClass('alert alert-error hide');
						 $('#messageBox').show();
					 }else{
						 if(data.obj){
							 var message = "检测报告内容:";
							 for(var i=0;i<data.obj.length;i++){
								 message += "<br>" + data.obj[i];
							 }
							 $('#messageInfo').html(message);
							 $('#messageBox').addClass('alert alert-error hide');
							 $('#messageBox').show();
						 }else{
							 $('#messageInfo').html("检测通过 !!");
							 $('#messageBox').addClass('alert alert-success hide');
							 $('#messageBox').show();
						 }
					 }
				 }
			 }) 
		}
		
		function addRow(list, tpl, data, pid, root){
			var idSelect=$("#ifUpload").val();
			for (var i=0; i<data.length; i++){
				var row = data[i];
				if ((${fns:jsGetVal('row.parentId')}) == pid){
					/* $("#a"+pid).attr({ style: "display:none;"});
					$("#"+pid).attr({ style: "background-color:#f9f9f9;"}); */
					if(idSelect==2){
						if(row.isHaveFile==''||row.isHaveFile==undefined){
							$(list).append(Mustache.render(tpl, {
								dict: {
										blank123:0}, pid: (root?0:pid), row: row
								}));
								addRow(list, tpl, data, row.id);
							}
					}else{
						$(list).append(Mustache.render(tpl, {dict: {blank123:0}, pid: (root?0:pid), row: row}));
							addRow(list, tpl, data, row.id);
					}
					if(row.isHaveFile!=''&&row.isHaveFile!=undefined){//有文件变色
					$("#"+row.id).attr({ style: "background-color:red;"});
					}
					if(row.isEndChild==1){//是末节点显示文件管理
					$("#a_"+row.id).attr({ style: "display:block;"});
					$("#c_"+row.id).attr({ style: "display:block;"});
					}else{
						$("#a_"+row.id).attr({ style: "display:none;"});
						$("#c_"+row.id).attr({ style: "display:none;"});
					}
				}
			}
		}
	</script>
<script type="text/javascript">

function page(n,s){
	$("#pageNo").val(n);
	$("#pageSize").val(s);
	$("#searchForm").submit();
	return false;
}
</script>

</head>
<body>
		<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/ams/amsFileInfo/amsProjectInfoList?id=${amsProjectInfo.id}">声像档案列表-sunjx-未测试</a></li>
		</ul>
		<br/>
		<input id="projectId" name="projectId" type="hidden" value="${amsProjectInfo.id}" >
		<div class="accordion-group">
			<div class="accordion-inner">
					<form:form modelAttribute="amsProjectInfo" class="form-horizontal">
						<input type="hidden" id="id" value="${id}">
						<table class="table-form">
						<tr>
						<td align="center" colspan="6"><b>项目基本信息</b></td>
						</tr>
						<tr>
						<td class="tit" width="15%">项目名称：</td>
						<td colspan="2">${amsProjectInfo.projectName} </td>
						<td class="tit" width="15%">项目地址：</td>
						<td colspan="2">${amsProjectInfo.address} </td>
						</tr>
						<tr>
						<td class="tit" width="15%">项目编号：</td>
						<td >${amsProjectInfo.projectNo}  </td>
						<td class="tit" width="15%">项目类别：</td>
						<td>${fns:getDictLabel(amsProjectInfo.projectType, 'unit_project_type', '')}</td>
						<td class="tit" width="15%">用地许可证号：</td>
						<td>${amsProjectInfo.landLicenseNumber}</td>
						</tr>
						</table>
					</form:form>
			</div>
		</div>
		<div class="accordion-inner">
		<form:form id="searchForm" modelAttribute="amsFileInfo" action="${ctx}/ams/amsCheckVideoFile/amsProjectInfoVideoList?id=${amsProjectInfo.id}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label style="margin-left:-80px;">文件题名：</label>
				<form:input path="fileName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li> &nbsp;&nbsp;&nbsp;
			  是否上传：<form:select id="ifUpload" path="exten5" class="input-medium required">
					<form:option value="0"  label="全部"/>
					<form:option value="1"  label="已上传"/>
					<form:option value="2"  label="未上传"/> 
				</form:select> 	
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
		</form:form>
	<div id="messageBox" style="display: none;">
	<button onclick="$('#messageBox').hide()" class="close">×</button>
	<div id="messageInfo"></div>
	</div> 	
	<table id="treeTable" class="table  table-bordered table-condensed">
		<thead>
			<tr>
				<th>类别编码</th>
				<th>名称</th>
				<th>备注信息</th>
				<th>文件检查</th>
				<th>查看文件</th>
			</tr>
		</thead>
		<tbody id="treeTableList"></tbody>
	</table>
	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.id}}" pId="{{pid}}"   >
			<td>
				{{row.code}}
			</td>
			<td>
				{{row.name}}
			</td>
			
			<td>
				{{row.remarks}}
			</td>
			<td>
				<a style="display:none" id="c_{{row.id}}" onclick="checkFile(this)" recordId="{{row.id}}">开始检查</a>
			</td>
			<td>
				<a style="display:none"  id="a_{{row.id}}" href="${ctx}/ams/amsCheckVideoFile/fileInfoList?projectId=${amsProjectInfo.id}&recordId={{row.id}}">文件管理</a> 
			</td>
		</tr>
	</script></div>

</body>
</html>