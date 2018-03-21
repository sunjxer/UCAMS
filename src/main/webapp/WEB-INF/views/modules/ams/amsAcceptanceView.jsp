<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>预验收管理</title>
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
			$.jBox.open( "iframe:${ctx}/ams/amsAcceptance/report?id="+id,"检查报告信息", 900, "auto",{iframeScrolling:"auto" ,buttons:{ '导出': 2, '保存': 1, '关闭': 0 },submit:submit});
		 }
		function proDetail(ids){
			var id=$("#projectId").val();
			$.jBox.open( "iframe:${ctx}/ams/amsProjectInfo/amsAcceptanceProDetail?id="+id,"项目信息", 900, 500,{iframeScrolling:"auto" ,buttons:{'关闭': 0 }});
		}
		function openArcview(id){
			$.jBox.open( "iframe:${ctx}/ams/amsArchivesInfo/amsAcceptanceArcView?id="+id,"案卷信息", 800, "auto",{iframeScrolling:"auto" ,buttons:{'关闭': 0 }});
		}
		function openUnitProview(id){
			$.jBox.open( "iframe:${ctx}/ams/amsUnitProInfo/amsUnitProInfoView?id="+id,"单位工程信息", 800, "auto",{iframeScrolling:"auto" ,buttons:{'关闭': 0 }});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		
		<li class="active"><a href="${ctx}/ams/amsAcceptance/form?id=${amsAcceptance.id}&project.id=${amsAcceptance.project.id }">预验收<shiro:hasPermission name="ams:amsAcceptance:edit">查看</shiro:hasPermission><shiro:lacksPermission name="ams:amsAcceptance:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="amsAcceptance" action="${ctx}/ams/amsAcceptance/saveAcceptance" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<input type="hidden" id="projectId" value="${amsAcceptance.project.id}"/>
		<form:hidden id="flag" path="act.flag"/>
		<sys:message content="${message}"/>
		<table class="table-form">
			<tr>
				<td class="tit" colspan="4"><b>预验收基本信息</b><c:if test="${amsAcceptance.act.taskDefKey=='accept' }">&nbsp;&nbsp;&nbsp;&nbsp;<a  href='javascript:reportDo()' >查看检查报告</a></c:if></td>
			</tr>
			<tr>
				<td class="tit">项目名称:</td>
				<td colspan="3">
				<a  href='javascript:proDetail()' >	${amsAcceptance.project.projectName}</a>
				</td>
			</tr>
			<tr>	
				<td class="tit">申请日期：</td>
				<td>
				<fmt:formatDate value="${amsAcceptance.preAcceptanceApplyDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td class="tit">申请人：</td>
				<td>
					${amsAcceptance.user.name}
				</td>
			</tr>
			<tr>	
				<td class="tit">申请批复人：</td>
				<td>
				${amsAcceptance.user2.name}	
				</td>
				<td class="tit">申请批复意见：</td>
				<td>
				${amsAcceptance.preAcceptanceApprovalOpinions}
				</td>
			</tr>
			<tr>	
				<td class="tit">审批人：</td>
				<td>
				${amsAcceptance.user3.name}	
				</td>
				<td class="tit">预验收意见书编号：</td>
				<td>
				${amsAcceptance.preAcceptanceNo}
				</td>
			</tr>
			 
	
		</table>
	<c:if test="${amsAcceptance.type=='0'}">
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
	<c:if test="${1==amsAcceptance.type}"> 
		<div class="control-group">
		<div style="text-align:center;border-top: 1px solid #eeeeee;background-color: #f5f5f5; padding: 8px 8px 4px 8px;">
		<b>归档目录</b>
		</div>
		<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>归档类别</th>
				<!-- <th>编制单位</th>
				<th>载体类型</th>				
				<th>立卷人</th>
				<th>立卷日期</th> -->
				<!-- <th>顺序号</th> -->
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${amsAcceptance.videoMenuList}" var="amsArchivesInfo">
			<tr>
				<td>
					${amsArchivesInfo.name}
				</td>
				<%-- <td>${amsArchivesInfo.makeUnit}</td>
				<td>${fns:getDictLabel(amsArchivesInfo.carrierType, 'carrier_type', '')}</td>				
				<td>${amsArchivesInfo.author}</td>
				<td><fmt:formatDate value="${amsArchivesInfo.makeDate}" pattern="yyyy-MM-dd"/></td> --%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
		</div>
		</c:if>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>	
		
	<script type="text/javascript">
	
		var setting = {data:{simpleData:{enable:true,idKey:"id",pIdKey:"pId",rootPId:'0'}},
			callback:{onClick:function(event, treeId, treeNode){
					var id = treeNode.pId == '0' ? '' :treeNode.pId;
					if(treeNode.id== "CON"){
						$('#resBodyContent').attr("src","${ctx}/ams/amsAcceptance/construtDes?projectId=${amsAcceptance.project.id}");	
					}else if(treeNode.id== "LAN"){
						$('#resBodyContent').attr("src","${ctx}/ams/amsAcceptance/lanDes?projectId=${amsAcceptance.project.id}");	
					}else if(id != null && id != ""){
							$('#resBodyContent').attr("src","${ctx}/ams/amsAcceptance/archivesList?unitProjectId="+treeNode.id+"&projectId=${amsAcceptance.project.id}&acceptanceId=${amsAcceptance.id}");	
					}else{
						/* $('#resBodyContent').attr("src","${ctx}/ams/amsAcceptance/allList"); */
						top.$.jBox.tip("请在左侧选择一个单位工程");
					}
				}
			}
		};
		
		function refreshTree(){
			$.getJSON("${ctx}/ams/amsAcceptance/treeDataAcc?id=${amsAcceptance.id}&a="+Math.random(),function(data){
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
	<c:if test="${not empty amsAcceptance.act.procInsId}">
			 <act:histoicFlow procInsId="${amsAcceptance.act.procInsId}" /> 
	</c:if>
	</form:form>
</body>
</html>