<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>项目信息</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
$(document).ready(function() {
	var $m_btn = $('#y-modalBtnAdd');  //y-modalBtnAdd是button的id
    var $modal = $('#y-myModalAdd');  //y-myModalAdd是弹出的遮罩层的id，通过这两个id进行绑定
    $("#showImg_approval").click(function(){ 
    	var fileUrl = $("#showImg_approval").attr('fileUrl');  //文件路径
    	$('#imgUrl').attr('src',fileUrl);
    	$modal.modal({backdrop: 'static'});
    })
    $("#showImg_planningLicenseUrl").click(function(){ 
		var fileUrl = $("#showImg_planningLicenseUrl").attr('fileUrl');  //文件路径
		$('#imgUrl').attr('src',fileUrl);
		$modal.modal({backdrop: 'static'});
	}) 

	$("#showImg_landLicenseUrl").click(function(){ 
		var fileUrl = $("#showImg_landLicenseUrl").attr('fileUrl');  //文件路径
		$('#imgUrl').attr('src',fileUrl);
		$modal.modal({backdrop: 'static'});
	}) 
	$("#showImg_landPermitUrl").click(function(){ 
		var fileUrl = $("#showImg_landPermitUrl").attr('fileUrl');  //文件路径
		$('#imgUrl').attr('src',fileUrl);
		$modal.modal({backdrop: 'static'});
	}) 
	
});
function page(n,s){
	$("#pageNo").val(n);
	$("#pageSize").val(s);
	$("#searchForm").submit();
	return false;
}

</script>

</head>
<body>
		<form:form modelAttribute="amsProjectInfo" action="${ctx}/ams/record/savePro" method="post" class="form-horizontal"  enctype="multipart/form-data">
						<input type="hidden" id="id" value="${id}">
						<form:hidden path="id"/>
						<table class="table-form">
						<tr>
						<td align="center" colspan="4" width="20%"><b>项目基本信息</b></td>
						</tr>
						<tr>
						<td class="tit" width="20%">项目名称:</td>
						<td colspan="3">
						${amsProjectInfo.projectName}
						</tr>
						<tr>
						<td class="tit" width="20%">项目编号:</td>
						<td colspan="3">
						${amsProjectInfo.projectNo}
						</td>
						</tr>
						<tr>
						<td class="tit" width="20%">项目地点:</td>
						 <td >${amsProjectInfo.area.name}</td>
						 <td class="tit" width="20%">项目类别:</td>
						<td>${fns:getDictLabel(amsProjectInfo.projectType, 'unit_project_type', '')}</td>
						<%--  <td >	<sys:treeselect id="local"  name="local" value="${amsProjectInfo.local}" labelName="" labelValue="${amsProjectInfo.area.name}"
						title="区域" url="/sys/area/treeData" cssClass="" allowClear="true" notAllowSelectParent="true"/>
						</td>  --%>
						
						</tr>
						<tr>
						<td class="tit" width="20%">项目地址:</td>
						<td colspan="3">
						${amsProjectInfo.address}
						</td>
						</tr>
						<tr>
						<td class="tit" width="20%">立项批准单位：</td>
						<td>
						${amsProjectInfo.projectApprovalUnit}
						</td>
						<td class="tit" width="20%">设计单位：</td>
						<td>
						${amsProjectInfo.designUnit}
						</td>
						</tr>
						
						<tr>
						<td class="tit" width="20%">勘察单位：</td>
						<td>
						${amsProjectInfo.prospectingUnit}
						</td>
						<td class="tit" width="20%">监理单位：</td>
						<td>
						${amsProjectInfo.supervisionUnit}
						</td>
						</tr>
						<tr>
						<td class="tit">立项批准<br />文号
						</td>
						<td>${amsProjectInfo.approvalNumber}
						</td>
						<td class="tit">建设规划<br />许可证号
						</td>
						<td>${amsProjectInfo.planningLicenseNumber}
						</td>
					</tr>
					<tr>
						<td class="tit">用地规划<br />许可证号
						</td>
						<td>${amsProjectInfo.landLicenseNumber}
						</td>
						<td class="tit">用地许可<br />证号
						</td>
						<td>${amsProjectInfo.landPermitNumber}
						</td>
					</tr>

					<tr>
						<td class="tit">开工日期</td>
						<td><fmt:formatDate value="${amsProjectInfo.startDate}" pattern="yyyy-MM-dd"/>
						</td>
						<td class="tit">竣工日期</td>
						<td><fmt:formatDate value="${amsProjectInfo.finishDate}" pattern="yyyy-MM-dd"/>
						</td>
					</tr>

					
				
				<c:forEach items="${amsProjectInfo.amsDesExtendList}" var="amsDesExtend" varStatus="status">
		<c:if test="${status.index%2==0}">
		<tr>
		</c:if>
		<td class="tit">${amsDesExtend.comments}：</td>
		<td>
				<input type="hidden" id="amsDesExtendList[${status.index}]_id" name="amsDesExtendList[${status.index}].id" value="${amsDesExtend.id}"/>
				<input type="hidden" id="amsDesExtendList[${status.index}]_name" name="amsDesExtendList[${status.index}].name" value="${amsDesExtend.name}"/>
				<c:if test="${amsDesExtend.showType==\"text\"}">
				<input type="text" id="amsDesExtendList[${status.index}]_settings" name="amsDesExtendList[${status.index}].settings" value="${amsDesExtend.settings}" maxlength="100" class="input-small ${amsDesExtend.isNull==1?'required':''}"/>
				</c:if>
				<c:if test="${amsDesExtend.showType==\"textarea\"}">
				<textarea id="amsDesExtendList[${status.index}]_settings" name="amsDesExtendList[${status.index}].settings" maxlength="300" class="input-xxlarge required">${amsDesExtend.settings}</textarea>		
				</c:if>
				<c:if test="${amsDesExtend.showType==\"select\"}">
				<select id="amsDesExtendList[${status.index}]_settings" name="amsDesExtendList[${status.index}].settings" class=" ${amsDesExtend.isNull==1?'required':''} input-mini" style="width:95px;*width:75px" data-value="${amsDesExtend.settings}">
					<c:forEach items="${fns:getDictList('file_type')}" var="dict">
					<option value="${dict.value}" ${dict.value==amsDesExtend.settings?'selected':''} title="${dict.description}">${dict.label}</option>
					</c:forEach>
				</select>
				</c:if>
				<c:if test="${amsDesExtend.showType==\"date\"}">
				<input id="amsDesExtendList[${status.index}]_settings" name="amsDesExtendList[${status.index}].settings" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate ${amsDesExtend.isNull==1?'required':''} "
					value="${amsDesExtend.settings}"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				</c:if>
				<c:if test="${amsDesExtend.showType==\"tree\"}">
				<sys:treeselect id="parent"  name="amsDesExtendList[${status.index}].settings"  value="${amsDesExtend.settings}" labelName="${amsDesExtend.settings}" labelValue="${amsDesExtend.settings}"
					title="${amsDesExtend.name}" url="" extId="" cssClass="" allowClear="true"/>
				</c:if>
				<c:if test="${amsDesExtend.isNull==1}">
				<span class="help-inline"><font color="red">*</font> </span>
				</c:if>
			</td>
		<c:if test="${status.index%2==1}">
		<tr>
		</c:if>
		</c:forEach> 
		</table>   
	</form:form>
	<!-- 模态弹出窗内容 -->
	<div class="modal" id="y-myModalAdd" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true" style="display: none;">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">
	          <span aria-hidden="true">×</span>
	        </button>
	        <h4 class="modal-title">图片预览</h4>
	      </div>
	      <div class="modal-body" >
				<img id="imgUrl" width="100%"  height="100%" >
	      </div>
	    </div>
	  </div>
	</div>
</body>
</html>