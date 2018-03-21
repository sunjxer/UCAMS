<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>业务管理档案案卷管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
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
	<style type="text/css">
		.ztree {overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;}
	</style>
</head>
<body>
	<div id="content" class="row-fluid">
		<div id="left" class="accordion-group">
			<div class="accordion-heading">
		    	<a class="accordion-toggle"><span style="margin-left: 30px">案卷管理</span><i style="margin-left: 30px" class="icon-refresh"  onclick="refreshTree();"></i></a>
		    </div>
			<div id="ztree" class="ztree"></div>
		</div>
		<div id="openClose" class="close">&nbsp;</div>
		<div id="right">
			<iframe id="resBodyContent" src="${ctx}/ams/amsWorkArchives/list" width="100%" height="91%" frameborder="0"></iframe>
		</div>
	</div>
	<script type="text/javascript">
	
		var setting = {data:{simpleData:{enable:true,idKey:"id",pIdKey:"pId",rootPId:'0'}},
			callback:{onClick:function(event, treeId, treeNode){
					var id = treeNode.pId == '0' ? '' :treeNode.pId;
					var l = treeNode.level;
					//编制单位
					var makeUnit="";
					//小类
					var exten1="";
					//案卷名
					var archivesName ="";//str1.substring(0,str1.indexOf("["));
					//年度
					var year = "";//str1.substring(str1.indexOf("[")+1,str1.indexOf("]"));
					//文件
					var fileName="";
					//通过上面的字段查询sql获取achivesId作为file表中的groupId（案卷Id）
				 	//找到根节点,找到当前节点所有的name值一一对应
					var  nodeList = [];
					nodeList.push(treeNode);
				 	var tempnode;
	                if(treeNode.level!=0){
	                    for(var i=0;i< l;i++){
	                        if(i==0){
	                            tempnode=treeNode.getParentNode();
	                        }else{
	                            tempnode = tempnode.getParentNode();
	                        }
	                        nodeList.push(tempnode);
	                    }
	                    //alert(tempnode.name);
	                }
	                //当前节点所有的name值一一对应
	                var nl=nodeList.length;
	                if(nl>0){makeUnit=nodeList[nl-1].name;}
	                if(nl>1){exten1=nodeList[nl-2].name;}
	                if(nl>2){
	                	 var archives=nodeList[nl-3].name;
	             		year=archives.substring(archives.indexOf("[")+1,archives.indexOf("]"));
	             		archivesName=archives.substring(0,archives.indexOf("["));
	                }
	                if(nl>3){fileName=nodeList[nl-4].name;}
	                
					if((id != null && id != "")||(makeUnit!=null&&makeUnit!="")){
							$('#resBodyContent').attr("src","${ctx}/ams/amsWorkArchives/list?makeUnit="+makeUnit+"&exten1="+exten1+"&archivesName="+archivesName+"&year="+year+"&amsWorkArchivesFiles.fileName="+fileName);	
					}else{
						$('#resBodyContent').attr("src","${ctx}/ams/amsWorkArchives/list");
					}
				}
			}
		};
		
		function refreshTree(){
			$.getJSON("${ctx}/ams/amsWorkArchives/archiveTreeData?a="+Math.random(),function(data){
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
			frameObj.height(strs[0] - 15);
			var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
			$("#right").width($("#content").width()- leftWidth - $("#openClose").width() -5);
			$(".ztree").width(leftWidth - 10).height(frameObj.height() - 46);
		}
	</script>
	<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
	<sys:message content="${message}"/>
</body>
</html>