<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>离线文件检查</title>
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
					window.location.href="${ctx}/ams/amsInterface/export";
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			}
			return false;
			}
			//$.jBox.open(states, "提交订单", 450, "auto");
			$.jBox.open( "iframe:${ctx}/ams/amsInterface/report","检查报告信息", 900, "auto",{iframeScrolling:"auto" ,buttons:{ '导出': 2, '保存': 1, '关闭': 0 },submit:submit});
		 }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="#">离线文件检查</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="projectInfoForm" action="${ctx}/ams/amsInterface/saveOfflineProjectInfo" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table-form">
			<tr>
				<td class="tit" colspan="4"><b>工程项目基本信息</b>&nbsp;&nbsp;&nbsp;&nbsp;<a  href='javascript:reportDo()' >查看检查报告</a></td>
			</tr>
			<tr>
				<td class="tit">项目名称:</td>
				<td colspan="3">
					${projectInfoForm.projectName}
				</td>
			</tr>
		</table>
	<div id="content" class="row-fluid"  style="height:350px">
		<div id="left" class="accordion-group" style="margin-left: 5px; width:18%;height:98%">
			<div class="accordion-heading">
		    	<a class="accordion-toggle"><span style="margin-left: 10px">单位工程</span></a>
		    </div>
			<div id="ztree" class="ztree"></div>
		</div>
		
		<div id="right"  style="margin-left: 5px; width:80%;height:98%">
			<iframe id="resBodyContent" src="" width="100%" height="100%" frameborder="0"></iframe>
		</div>
	</div>
		
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="同意" />&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>	
		
	<script type="text/javascript">
	
		var setting = {data:{simpleData:{enable:true,idKey:"id",pIdKey:"pId",rootPId:'0'}},
			callback:{onClick:function(event, treeId, treeNode){
					var id = treeNode.pId == '0' ? '' :treeNode.pId;
					if(id != null && id != ""){
							$('#resBodyContent').attr("src","${ctx}/ams/amsInterface/archivesList?unitProjectId="+treeNode.id+"&projectId=${amsAcceptance.project.id}&acceptanceId=${amsAcceptance.id}");	
					}else{
						/* $('#resBodyContent').attr("src","${ctx}/ams/amsAcceptance/allList"); */
						top.$.jBox.tip("请在左侧选择一个单位工程");
					}
				}
			}
		};
		
		function refreshTree(){
			$.getJSON("${ctx}/ams/amsInterface/treeDataAcc?id=${projectInfoForm.id}&a="+Math.random(),function(data){
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
	</form:form>
</body>
</html>