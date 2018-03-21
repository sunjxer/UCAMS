<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>移交管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<style type="text/css">
		.ztree {overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;}
	</style>
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
		function reportDo(){
			 var id=$("#id").val();
			 var submit=function (v, h, f) {
			 var iframeName = h.children(0).attr("name");
			 var exportForm= window.frames[iframeName].document.getElementById("searchForm");  
			 var saveForm= window.frames[iframeName].document.getElementById("inputForm");  
			 if (v == 0) {
				return true;// close the window
			 }
			else if (v == 1){
				saveForm.submit();
			}
			else if (v == 2){
				top.$.jBox.confirm("确认要导出检查报告吗？","系统提示",function(v,h,f){
				if(v=="ok"){
					exportForm.submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			}
			return false;
			}
			//$.jBox.open(states, "提交订单", 450, "auto");
			$.jBox.open( "iframe:${ctx}/ams/amsTransfer/report?id="+id,"检查报告信息", 900, "auto",{iframeScrolling:"auto" ,buttons:{ '导出': 2, '保存': 1, '关闭': 0 },submit:submit});
		 }
			function openArcview(id){
				$.jBox.open( "iframe:${ctx}/ams/amsArchivesInfo/amsAcceptanceArcView?id="+id,"案卷信息", 800, "auto",{iframeScrolling:"auto" ,buttons:{'关闭': 0 }});
			}
			function openUnitProview(id){
				$.jBox.open( "iframe:${ctx}/ams/amsUnitProInfo/amsUnitProInfoView?id="+id,"单位工程信息", 800, "auto",{iframeScrolling:"auto" ,buttons:{'关闭': 0 }});
			}
			function proDetail(ids){
				var id=$("#projectId").val();
				$.jBox.open( "iframe:${ctx}/ams/amsProjectInfo/amsAcceptanceProDetail?id="+id,"项目信息", 900, 500,{iframeScrolling:"auto" ,buttons:{'关闭': 0 }});
			}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="#">移交审批</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="amsTransfer" action="${ctx}/ams/amsTransfer/saveTransfer" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden id="flag" path="act.flag"/>
		<input type="hidden" id="projectId" value="${amsTransfer.project.id}"/>
		<sys:message content="${message}"/>
		<table class="table-form">
			<tr>
				<td class="tit" colspan="4"><b>基本信息</b><c:if test="${amsTransfer.act.taskDefKey=='transferYJ' }">&nbsp;&nbsp;&nbsp;&nbsp;<a  href='javascript:reportDo()' >查看检查报告</a></c:if></td>
			</tr>
			<tr>
				<td class="tit">项目名称:</td>
				<td colspan="3">
				<a  href='javascript:proDetail()' >	${amsTransfer.project.projectName}</a>
				</td>
			</tr>
			
			<tr>
				<td class="tit">移交申请日期:</td>
				<td >
					<fmt:formatDate value="${amsTransfer.transferApplicatonDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td class="tit">预计移交日期:</td>
				<td >
					<fmt:formatDate value="${amsTransfer.estimateTransferDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
			<tr>
				<td class="tit">移交申请人:</td>
				<td >
				${amsTransfer.user.name}
				</td>
				<td class="tit">联系电话:</td>
				<td >
					${amsTransfer.transferApplicantPhone}
				</td>
			</tr>
			<tr>
				<td class="tit">移交申请内容:</td>
				<td >
				${amsTransfer.transferApplication}
				</td>
				<td class="tit">备注信息:</td>
				<td >
					${amsTransfer.remarks}
				</td>
			</tr>
			
		</table>
		<%-- <div class="control-group">
		<div style="text-align:center;border-top: 1px solid #eeeeee;background-color: #f5f5f5; padding: 8px 8px 4px 8px;">
		<b>项目预验收列表</b>
		</div>
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>预验收申请日期</th>
				<th>预验收批复意见</th>
				<th>预验收意见书编号</th>
				<th>备注</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${amsTransfer.amsAcceptancesList}" var="amsAcceptances">
		<c:if test="${amsAcceptances.exten5.equals(\"true\")}">
			<tr>
				<td>
					<fmt:formatDate value="${amsAcceptances.preAcceptanceApplyDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${amsAcceptances.preAcceptanceApprovalOpinions}
				</td>
				<td>
					${amsAcceptances.preAcceptanceNo}
				</td>	
				<td>
					${amsAcceptances.remarks}
				</td>
			</tr>
		</c:if>
		</c:forEach>
		</tbody>
	</table>
		</div> --%>
	<c:if test="${amsTransfer.type=='0'}">
	<div id="content" class="row-fluid"  style="height:350px">
		<div id="left" class="accordion-group" style="margin-left: 5px; width:18%;height:98%;overflow-y:auto">
			<div class="accordion-heading">
		    	<a class="accordion-toggle"><span style="margin-left: 10px">单位工程</span></a>
		    </div>
			<div id="ztree" class="ztree"></div>
		</div>
		
		<div id="right"  style="margin-left: 5px; width:80%;height:98%">
			<iframe id="resBodyContent" src="" width="100%" height="100%" frameborder="0"></iframe>
		</div>
	</div>
	</c:if>
	<c:if test="${1==amsTransfer.type}"> 
	<div class="control-group">
		<div style="text-align:center;border-top: 1px solid #eeeeee;background-color: #f5f5f5; padding: 8px 8px 4px 8px;">
		<b>项目预验收列表</b>
		</div>
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>预验收申请日期</th>
				<th>预验收批复意见</th>
				<th>预验收意见书编号</th>
				<th>备注</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${amsTransfer.amsAcceptancesList}" var="amsAcceptances">
			<tr>
				
				<td>
					<fmt:formatDate value="${amsAcceptances.preAcceptanceApplyDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${amsAcceptances.preAcceptanceApprovalOpinions}
				</td>
				<td>
					${amsAcceptances.preAcceptanceNo}
				</td>	
				<td>
					${amsAcceptances.remarks}
				</td>
				
			</tr>
		</c:forEach>
		</tbody>
	</table>
</div>
	</c:if>
		<c:if test="${amsTransfer.act.taskDefKey=='transferPF'||amsTransfer.act.taskDefKey=='first' }">
		<div class="control-group">
			<label class="control-label">审批人：</label>
			<div class="controls">
			<sys:treeselect id="user2" name="user2.id" value="${amsTransfer.user2.id}" labelName="user2.name" labelValue="${amsTransfer.user2.name}" 
							title="用户" url="/sys/office/userTree?type=3" cssClass="required recipient" cssStyle="width:150px" 
							allowClear="true" notAllowSelectParent="true" smallBtn="false"/> 
<%-- 				<form:input path="preAcceptanceApplicationApprover" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
 --%>				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">审批意见：</label>
			<div class="controls">
				<form:input path="transferApproval" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		</c:if>
		<c:if test="${amsTransfer.act.taskDefKey=='transferYJ'||amsTransfer.act.taskDefKey=='second' }">
		<div class="control-group">	
			<label class="control-label">审批人：</label>
			<div class="controls">
			<sys:treeselect id="user3" name="user3.id" value="${amsTransfer.user3.id}" labelName="user3.name" labelValue="${amsTransfer.user3.name}" 
							title="用户" url="/sys/office/userTree?type=3" cssClass="required recipient" cssStyle="width:150px" 
							allowClear="true" notAllowSelectParent="true" smallBtn="false"/> 
<%-- 				<form:input path="preAcceptanceApplicationApprover" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
 --%>				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		</c:if>
		
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="同意" onclick="$('#flag').val('yes')"/>&nbsp;
			<input id="btnSubmit" class="btn btn-inverse" type="submit" value="驳回" onclick="$('#flag').val('no')"/>&nbsp;		
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>		
	
	<script type="text/javascript">
	
		var setting = {data:{simpleData:{enable:true,idKey:"id",pIdKey:"pId",rootPId:'0'}},
			callback:{onClick:function(event, treeId, treeNode){
					var id = treeNode.pId == '0' ? '' :treeNode.pId;
					if(treeNode.id== "CON"){
						$('#resBodyContent').attr("src","${ctx}/ams/amsAcceptance/construtDes?projectId=${amsTransfer.project.id}");	
					}else if(treeNode.id== "LAN"){
						$('#resBodyContent').attr("src","${ctx}/ams/amsAcceptance/lanDes?projectId=${amsTransfer.project.id}");	
					}else if(id != null && id != ""){
							$('#resBodyContent').attr("src","${ctx}/ams/amsTransfer/archivesList?unitProjectId="+treeNode.id+"&projectId=${amsTransfer.project.id}&acceptanceId=${amsTransfer.id}");	
					}else{
						/* $('#resBodyContent').attr("src","${ctx}/ams/amsAcceptance/allList"); */
						top.$.jBox.tip("请在左侧选择一个单位工程");
					}
				}
			}
		};
		
		function refreshTree(){
			$.getJSON("${ctx}/ams/amsTransfer/treeDataAcc?id=${amsTransfer.id}&a="+Math.random(),function(data){
				$.fn.zTree.init($("#ztree"), setting, data).expandAll(true);
			});
		}
		refreshTree();
		 
		var leftWidth = 180; // 左侧窗口大小
		var htmlObj = $("html"), mainObj = $("#main");
		var frameObj = $("#left, #openClose, #right, #right iframe");
		function wSize(){
			var strs = getWindowSize().toString().split(",");
			htmlObj.css({"overflow-x":"hidden", "overflow-y":"hidden"});
			mainObj.css("width","auto");
			frameObj.height(strs[0] - 5);
			var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
			$("#right").width($("#content").width()- leftWidth - $("#openClose").width() -5);
			$(".ztree").width(leftWidth - 10).height(frameObj.height() - 46);
		}
	</script>
			<act:histoicFlow procInsId="${amsTransfer.act.procInsId}" />
	</form:form>
</body>
</html>