<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>文件管理</title>
<meta name="decorator" content="default" />
<%@include file="/WEB-INF/views/include/treeview.jsp"%>
<style type="text/css">
		.ztree {overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;}
	</style>
<script type="text/javascript">
	
$(document).ready(function() {
		var $m_btn = $('#y-modalBtnAdd');  //y-modalBtnAdd是button的id
	    var $modal = $('#y-myModalAdd');  //y-myModalAdd是弹出的遮罩层的id，通过这两个id进行绑定
	    $m_btn.on('click', function(){
	    	//设置form的样式
	    	$('#left').width($('.modal').width()/3 - 50);
	    	$('#resBodyContent').width($('.modal').width() - $('#left').width() - 5);
	    	$('#resBodyContent').height($('.modal').height() - 45);
	    	$('#left').height($('#resBodyContent').height());
	        $modal.modal({backdrop: 'static'});
	    });
	    $("#saveSort").click(function(){
	    	loading('正在提交，请稍等...');
	    	$("#saveSortForm").submit();
	    	
	    });
	    $("#catalog").click(function(){
	    	var id = $("#amsId").val();
	    	var catalogType = $("#catalogType").val();
	    	location.href = "${ctx}/ams/amsArchivesInfo/catalog?id="+id+"&catalogType="+catalogType;
	    })
});
function page(n,s){
	$("#pageNo").val(n);
	$("#pageSize").val(s);
	$("#searchForm").submit();
	return false;
}
function changeSort(t,oper){
    var data_tr=$(t).parent().parent(); //获取到触发的tr
    var amsArchivesInfoId = $("#amsArchivesInfoId").val();
    var amsArchivesInfoProjectId = $("#amsArchivesInfoProjectId").val();
    var catalogType = $("#catalogType").val();
        if(oper=="MoveUp"){    //向上移动
           if($(data_tr).prev().html()==null){ //获取tr的前一个相同等级的元素是否为空
               top.$.jBox.tip("已经是最顶部了!");
               return;
           }{
        		var prevId = $(data_tr).prev().children("#tdId").text(); //上一行数据ID
        		var thisId = $(data_tr).children("#tdId").text() //当前行数据ID
        		$.ajax({
        	        url:'${ctx}/ams/amsArchivesInfo/sortCheck',  //异步修改数据
        	        data:{'thisId':thisId,'prevId':prevId,'catalogType':catalogType},
        	        async:false,
        	        success:function(data){
        	        	 if(data ="success"){
        	        		 $("#btnSubmit1").click();
        	        		// $(data_tr).insertBefore($(data_tr).prev()); //将本身插入到目标tr的前面 
        	        	 }else{
        	        		 top.$.jBox.tip("移动失败");
        	        	 }
        	        },
        	        error:function(){
        	        	top.$.jBox.tip("系统错误！");
        	        }
        	    });
           }
           }else{
                 if($(data_tr).next().html()==null){
                 top.$.jBox.tip("已经是最低部了!");
                 return;
             }{
            	 var nextId = $(data_tr).next().children("#tdId").text(); //上一行数据ID
            	var thisId = $(data_tr).children("#tdId").text() //当前行数据ID
            	 $.ajax({
            	        url:'${ctx}/ams/amsArchivesInfo/sortCheck',  //异步修改数据
            	        data:{'thisId':thisId,'nextId':nextId,'catalogType':catalogType},
            	        async:false,
            	        success:function(data){
            	        	  if(data ="success"){
            	        		  $("#btnSubmit1").click();
	            	        		// $(data_tr).insertAfter($(data_tr).next()); //将本身插入到目标tr的后面 
	            	        	 }else{
	            	        		 top.$.jBox.tip("移动失败");
	            	        	 }
            	        },
            	        error:function(){
            	        	top.$.jBox.tip("系统错误！");
            	        }
            	    });
             }
           }
}


function closeWin(t){
	var $modal = $('#y-myModalAdd');
	var id = $("#amsId").val();
	var projectId = $("#projectId").val();
	location.href = "${ctx}/ams/amsArchivesInfo/list?id="+id+"&projectId="+projectId;
}



</script>

</head>
<body>
	<div class="accordion" id="accordion2">
		<%-- <c:if test="${empty amsProjectInfo.id}">
		<label class="control-label"> ${message}</label>
		<script type="text/javascript">top.$.jBox.tip("项目不存在！");
		</script>
		</c:if>
		<c:if test="${not empty amsProjectInfo.id}"> --%>
		<ul class="nav nav-tabs">
		<input type="hidden" id="amsId" value="${amsArchivesInfo.id}"/>
		<input type="hidden" id="projectId" value="${amsArchivesInfo.projectId}"/>
		<input type="hidden" id="catalogType" value="${amsArchivesInfo.catalogType}"/>
		 	<li><a href="${ctx}/ams/amsArchivesInfo/amsArchivesInfoList?id=${amsArchivesInfo.id}">案卷信息</a></li>
			<shiro:hasPermission name="ams:amsArchivesInfo:edit">
			<li class="active"><a href="${ctx}/ams/amsArchivesInfo/list?id=${amsArchivesInfo.id}&projectId=${amsArchivesInfo.projectId}">文件列表</a></li>
			</shiro:hasPermission>
		</ul>
		<sys:message content="${message}" />
		<br/>
		<div class="accordion-group">
			<div class="accordion-heading">
				<a class="accordion-toggle" data-toggle="collapse"
					data-parent="#accordion2" href="#collapseOne"> 已组卷文件 </a>
			</div>
			<div id="collapseOne" class="accordion-body collapse in ">
				<div class="accordion-inner">
				<form:form id="searchForm" modelAttribute="amsArchivesInfo" action="${ctx}/ams/amsArchivesInfo/list?id=${amsArchivesInfo.id}&projectId=${amsArchivesInfo.projectId}&type=in" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${pageIn.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${pageIn.pageSize}"/>
					<ul class="ul-form">
						<li><label>文件名：</label>
							<form:input path="amsFileInfo.fileName" htmlEscape="false" maxlength="100" class="input-medium"/>
						</li>
						<li class="btns"><input id="btnSubmit1" class="btn btn-primary" type="submit" value="查询"/></li>
						<c:if test="${amsArchivesInfo.amsAcceptance.status == 0 }">
							<li class="btns"><input  class="btn btn-primary" type="button" value="组卷"  id="y-modalBtnAdd"/></li>
							<c:if test="${amsArchivesInfo.catalogType == 0 || amsArchivesInfo.catalogType == '' || amsArchivesInfo.catalogType == null}">
							<li class="btns"><input  class="btn btn-primary" type="button" value="保存排序"  id="saveSort" /></li>
							</c:if>
						</c:if>
						<c:if test="${amsArchivesInfo.amsAcceptance.status != 0 }">
							<li class="btns"><input  class="btn btn-primary" type="button" value="组卷"  id="y-modalBtnAdd" disabled="disabled" title="预验收中，不可组卷"/></li>
							<c:if test="${amsArchivesInfo.catalogType == 0 || amsArchivesInfo.catalogType == ''|| amsArchivesInfo.catalogType == null}">
							<li class="btns"><input  class="btn btn-primary" type="button" value="保存排序"  id="saveSort" disabled="disabled" title="预验收中，不可排序"/></li>
							</c:if>
						</c:if>
						<li class="btns"><input  class="btn btn-primary" type="button" value="卷内目录"  id="catalog"/></li>
						<!-- <button class="btn btn-info" type="button" id="y-modalBtnAdd" > <label >添加</label></button> -->
						<li class="clearfix"></li>
					</ul>
				</form:form>
				<form:form id="saveSortForm" modelAttribute="amsArchivesInfo" action="${ctx}/ams/amsArchivesInfo/saveSort?id=${amsArchivesInfo.id}&projectId=${amsArchivesInfo.projectId}" method="post" class="breadcrumb form-search">
					<table id="contentTable"
						class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th>序号</th>
								<th>文件题名</th>
								<th>文件名</th>
								<th>文（图）号</th>
								<th>实体页数</th>								
								<th>文件类型</th>
								<th>页号</th>
								<c:if test="${amsArchivesInfo.catalogType == 0 || amsArchivesInfo.catalogType == ''|| amsArchivesInfo.catalogType == null}">
								<th>排序</th>
								</c:if>
								<th>移动</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${pageIn.list}" var="amsFileInfo" varStatus="status">
								<tr>
									<td id="tdId" style="display: none">${amsFileInfo.amsArchivesFiles.id}</td>
									<td>${status.index +1}</td>
									<td>${amsFileInfo.amsGenre.name }</td>
									<td>
									<a href="${ctx}/ams/amsFileInfo/formView?id=${amsFileInfo.id}&unitProjectId=${amsFileInfo.unitProjectId}&recordId=${amsFileInfo.recordId}">
										${amsFileInfo.fileName}
									</a>
									
									</td>
									<td>${amsFileInfo.fileNo}</td>
										<td>${amsFileInfo.filecount}</td>									
										<td>${fns:getDictLabel(amsFileInfo.fileType, 'file_type', '')}</td>
										<td>
										<!--  ${amsFileInfo.amsArchivesFiles.pageCount}-->
										${amsFileInfo.amsArchivesFiles.startPage}--${amsFileInfo.amsArchivesFiles.endPage }
										</td>
							<c:if test="${amsArchivesInfo.amsAcceptance.status == 0 }">
								<c:if test="${amsArchivesInfo.catalogType == 0 || amsArchivesInfo.catalogType == ''|| amsArchivesInfo.catalogType == null}">
									<td>
										 <input type="hidden" name="ids" value="${amsFileInfo.amsArchivesFiles.id}"/>
										 <input type="hidden" name="nums" value="${amsFileInfo.filecount}"/>
									     <input name="sorts" type="text" value="${amsFileInfo.amsArchivesFiles.sort}" style="width:50px;margin:0;padding:0;text-align:center;">
									</td>
								</c:if>
									<td>
										<a href="javascript:void(0)"  id = "sortUp" onclick="changeSort(this,'MoveUp')">上移</a>
										<a href="javascript:void(0)"  id = "sortDown" onclick="changeSort(this,'MoveDown')">下移</a>
									</td>
									<shiro:hasPermission name="ams:amsFileInfo:edit">
									<td>
										<a href="${ctx}/ams/amsArchivesInfo/deleteFile?id=${amsFileInfo.amsArchivesFiles.id}&archId=${amsArchivesInfo.id}" onclick="return confirmx('确认要删除该文件吗？', this.href)">${not empty amsFileInfo.amsArchivesFiles.id?'从案卷删除':''}</a>
									</td>
									</shiro:hasPermission>
							</c:if>
							<c:if test="${amsArchivesInfo.amsAcceptance.status != 0 }">
								<c:if test="${amsArchivesInfo.catalogType == 0 || amsArchivesInfo.catalogType == ''|| amsArchivesInfo.catalogType == null}">
									<td>
										 <input type="hidden" name="ids" value="${amsFileInfo.amsArchivesFiles.id}"/>
										 <input type="hidden" name="nums" value="${amsFileInfo.filecount}"/>
									     <input name="sorts" type="text" value="${amsFileInfo.amsArchivesFiles.sort}" style="width:50px;margin:0;padding:0;text-align:center;" disabled="disabled">
									</td>
								</c:if>
									<td>
										<a href="javascript:return false;"  id = "sortUp" style="opacity:0.2">上移</a>
										<a href="javascript:return false;"  id = "sortDown" style="opacity:0.2">下移</a>
									</td>
									<shiro:hasPermission name="ams:amsFileInfo:edit">
									<td>
										<a href="javascript:return false;" style="opacity:0.2">${not empty amsFileInfo.amsArchivesFiles.id?'从案卷删除':''}</a>
									</td>
									</shiro:hasPermission>
							</c:if>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</form:form>
					<div class="pagination">${pageIn}</div>
				</div>
			</div>
		</div>
	</div>
		<!-- 模态弹出窗内容 -->
	<div class="modal" id="y-myModalAdd" tabindex="-1" role="dialog"  style="width:  80% !important; height:80%; left: 30% !important;display: none"
			aria-labelledby="mySmallModalLabel" aria-hidden="true" >
	  <div class="modal-dialog" >
	    <div class="modal-content" >
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" >
	          <span aria-hidden="true"><a href="#" onclick="closeWin(this);">×</a></span>
	        </button>
	        <h4 class="modal-title">&nbsp;</h4>
	      </div>
	      <div id="content" class="row-fluid">
				<div id="left" class="accordion-group">
					<div class="accordion-heading">
				    	<a class="accordion-toggle"><span style="margin-left: 30px">文件类型</span><i class="icon-refresh " style="margin-left: 30px"  onclick="refreshTree();"></i></a>
				    </div>
					<div id="ztree" class="ztree" style="height:400px;"></div>
				</div>
				<!-- <div id="openClose" class="close">&nbsp;</div> -->
				<div id="right" style="height: auto">
					<iframe id="resBodyContent"   frameborder="0"  src="${ctx}/ams/amsArchivesInfo/amsAllFileList?treeId=null&id=${amsArchivesInfo.id}"></iframe>
				</div>
			</div>
 	     <!--  <div class="modal-footer">
	        <button  type="button" class="btn btn-primary" onclick="closeWin(this);">关闭</button>
	      </div>  -->
	    </div>
	  </div>
	</div>
	<script type="text/javascript">
	
	var setting = {data:{simpleData:{enable:true,idKey:"id",pIdKey:"pId",rootPId:'0'}},
			callback:{onClick:function(event, treeId, treeNode){
					var id = treeNode.pId == '0' ? '' :treeNode.pId;
					var ids=$('#amsId').val();
					var exten1 = $('#exten1').val();
					if(exten1== 1){
						$('#resBodyContent').attr("src","${ctx}/ams/amsArchivesInfo/amsAllFileList?treeId=-1&id="+ids);
					}else{
						if(id != null && id != ""){
							if(treeNode.id.indexOf("$END$")>-1){
								
								var str="&id="+ids;
								$('#resBodyContent').attr("src","${ctx}/ams/amsArchivesInfo/amsAllFileList?treeId="+treeNode.id.substring(0,treeNode.id.length-5)+str);
							}
							/* else{
								$('#resBodyContent').attr("src","${ctx}/ams/amsArchivesInfo/amsAllFileList?treeId=-1&id="+ids);
							} */
							
						}
					}
					/* else{
						 $('#resBodyContent').attr("src","${ctx}/ams/amsArchivesInfo/amsAllFileList?treeId=-1&id="+ids); 

					} */
				}
			}
		};
		
		function refreshTree(){
			$.getJSON("${ctx}/ams/amsArchivesInfo/recordTreeData?id=${amsArchivesInfo.id}&projectId=${amsArchivesInfo.projectId}",function(data){
				$.fn.zTree.init($("#ztree"), setting, data).expandAll(true);
			});
		}
		refreshTree();
		 
	</script>
</body>
</html>