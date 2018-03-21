<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>文件管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
	<style type="text/css">
		.ztree {overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;}
	</style>
</head>
<body> 
	<div id="content" class="row-fluid">
		<div id="left" class="accordion-group">
			<div class="accordion-heading">
		    	<a class="accordion-toggle"><span style="margin-left: 30px">案卷管理</span><i class="icon-refresh " style="margin-left: 30px"  onclick="refreshTree();"></i></a>
		    </div>
			<div id="ztree" class="ztree"></div>
		</div>
		<div id="openClose" class="close">&nbsp;</div>
		<div id="right">
			<iframe id="resBodyContent"  width="100%" height="91%" frameborder="0" src="${ctx}/ams/amsArchivesInfo/businessList"></iframe>
		</div>
	</div>
	<script type="text/javascript">
	
		var setting = {data:{simpleData:{enable:true,idKey:"id",pIdKey:"pId",rootPId:'0'}},
			callback:{onClick:function(event, treeId, treeNode){
					var id = treeNode.pId == '0' ? '' :treeNode.pId;
					if(id != null && id != ""){
						if(treeNode.id.indexOf("$PRO$")>-1){
							$('#resBodyContent').attr("src","${ctx}/ams/amsArchivesInfo/amsProjectInfoList?id="+treeNode.id.substring(0,treeNode.id.length-5));	
						}else if(treeNode.id.indexOf("$UNIT$")>-1){
							$('#resBodyContent').attr("src","${ctx}/ams/amsArchivesInfo/amsUnitProInfoList?id="+treeNode.id.substring(0,treeNode.id.length-6));
						}else if(treeNode.id.indexOf("$ARC$")>-1){
							$('#resBodyContent').attr("src","${ctx}/ams/amsArchivesInfo/amsArchivesInfoList?id="+treeNode.id.substring(0,treeNode.id.length-5));
						}else if(treeNode.id.indexOf("$CON$")>-1){
							$('#resBodyContent').attr("src","${ctx}/ams/amsArchivesInfo/amsConstructDesList?id="+treeNode.id.substring(0,treeNode.id.length-5));
						}else if(treeNode.id.indexOf("$LAN$")>-1){
							$('#resBodyContent').attr("src","${ctx}/ams/amsArchivesInfo/amsLandDesList?id="+treeNode.id.substring(0,treeNode.id.length-5));
						}
						
					}else{
						/*  if(treeNode.id.indexOf("$PRO$")>-1){
							$('#resBodyContent').attr("src","${ctx}/ams/amsArchivesInfo/amsProjectInfoList?id="+treeNode.id.substring(0,treeNode.id.length-5));	
						}  */
						 $('#resBodyContent').attr("src","${ctx}/ams/amsArchivesInfo/businessList"); 

						 /*top.$.jBox.tip("请在右侧选择一个工程项目或单位工程"); */
					}
				}
			}
		};
		
		function refreshTree(){
			$.getJSON("${ctx}/ams/amsArchivesInfo/treeData",function(data){
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
	<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
	<sys:message content="${message}"/>
	
</body>
</html>