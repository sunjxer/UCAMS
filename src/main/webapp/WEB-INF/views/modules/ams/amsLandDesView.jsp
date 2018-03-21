<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>文件管理</title>
<meta name="decorator" content="default" />
<%@include file="/WEB-INF/views/include/treeview.jsp"%>
<script type="text/javascript">
$(document).ready(function() {
	/* $("#landArea").bind("keyup",function(){  
        $("landArea").val($("landArea").val().replace(/[^\-?\d.]/g,''));  
    }); */
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
	<c:if test="${empty amsLandDes.id}">
	<%-- <label class="control-label"> ${message}</label> --%>
	<script type="text/javascript">top.$.jBox.tip("案卷不存在！");
	</script>
	</c:if>
	<c:if test="${not empty amsLandDes.id}">
	 <ul class="nav nav-tabs">
	 	<li class="active"><a href="${ctx}/ams/amsArchivesInfo/amsLandDesList?id=${amsLandDes.id}">建设用地规划</a></li>
	 	<shiro:hasPermission name="ams:amsArchivesInfo:edit">
	 	<li><a href="${ctx}/ams/amsArchivesInfo/lanList?id=${amsLandDes.id}&projectId=${amsLandDes.projectId}">文件列表</a></li>
	 	</shiro:hasPermission>
	</ul>
	<sys:message content="${message}" />
		<div class="accordion-group">
				<div class="accordion-inner">
					<form:form modelAttribute="amsLandDes" class="form-horizontal" action="${ctx}/ams/amsArchivesInfo/saveLan"  method="post">
						<sys:message content="${message}" />
						<form:input type="hidden" path="id" value="${amsLandDes.id}"/>
						<form:input type="hidden" path="projectId" value="${amsLandDes.projectId}"/>
						<form:input type="hidden" path="fileId" value="${amsLandDes.fileId}"/>
						<form:input type="hidden" path="descriptionJson" value="${amsLandDes.descriptionJson}"/>
						<table class="table-form">
						<tr>
							<td align="center" colspan="4" width="20%"><b>建设用地规划著录信息</b></td>
						</tr>
						<tr>
							<td class="tit" width="20%">用地项目名称:</td>
							<td colspan="3">
							<form:input path="projectName" htmlEscape="false" maxlength="50" value="${amsLandDes.projectName}"/>
							 </td>
						</tr>
						<tr>
							<td class="tit" width="20%">征地位置:</td>
							<td colspan="3">
							<form:input path="address" htmlEscape="false" maxlength="50" value="${amsLandDes.address} "/>
							</td>
						</tr>
						<tr>
							<td class="tit" width="20%">用地单位:</td>
							<td>
							<form:input path="landUseUnit" htmlEscape="false" maxlength="50" value="${amsLandDes.landUseUnit} "/>
							</td>
							<td class="tit" width="20%">被征单位:</td>
							<td>
							<form:input path="expropriatedUnit" htmlEscape="false" maxlength="50" value="${amsLandDes.expropriatedUnit} "/>
							</td>							
						</tr>
						<tr>
							<td class="tit" width="20%">地形图号:</td>
							<td>
							<form:input path="topographicMap" htmlEscape="false" maxlength="50" value="${amsLandDes.topographicMap} "/>
							</td>
							<td class="tit" width="20%">用地分类:</td>
							<td>
							<form:input path="landType" htmlEscape="false" maxlength="50" value="${amsLandDes.landType} "/>
							</td>
						</tr>
						<tr>
							<td class="tit" width="20%">征拨分类:</td>
							<td>
							<form:input path="expropriation" htmlEscape="false" maxlength="50" value="${amsLandDes.expropriation} "/>
							</td>
							<td class="tit" width="20%">原土地分类:</td>
							<td>
							<form:input path="originalLandType" htmlEscape="false" maxlength="50" value="${amsLandDes.originalLandType} "/>
							</td>
						</tr>
						<tr>
							<td class="tit" width="20%">批准时间:</td>
							<td>							
							<input name="approvalDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
								value="<fmt:formatDate value="${amsLandDes.approvalDate}" pattern="yyyy-MM-dd"/>"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
							<%-- <form:input path="approvalDate" htmlEscape="false" maxlength="50" value="${amsLandDes.approvalDate} "/> --%>
							</td>
							<td class="tit" width="20%">用地面积:</td>
							<td>
							<form:input  path="landArea"  htmlEscape="false" maxlength="10" value="${amsLandDes.landArea} " onkeyup="value=value.replace(/[^\d^\.]+/g,'')"/>㎡
							</td>
						</tr>
						<tr>
							<td class="tit" width="20%">备注:</td>
							<td colspan="3">
							<form:textarea path="remarks" rows="5" maxlength="200" cssStyle="width:500px" value="${amsLandDes.remarks}"/>
							</td>
						</tr>						
						</table>
						<table class="table-form">
						<tr>
							<td align="center" colspan="4" width="20%"><b>建设用地规划著录扩展信息</b></td>
						</tr>
						<c:forEach items="${amsLandDes.amsDesExtendList}" var="amsDesExtend" varStatus="status">
							<c:if test="${status.index%2==0}">
							<tr>
							</c:if>
								<td class="tit">${amsDesExtend.comments}：</td>
								<td>
									<input type="hidden" id="amsDesExtendList[${status.index}]_id" name="amsDesExtendList[${status.index}].id" value="${amsDesExtend.id}"/>
									<input type="hidden" id="amsDesExtendList[${status.index}]_name" name="amsDesExtendList[${status.index}].name" value="${amsDesExtend.name}"/>
									<c:if test="${amsDesExtend.showType==\"text\"}">
									<input type="text" id="amsDesExtendList[${status.index}]_settings" name="amsDesExtendList[${status.index}].settings" value="${amsDesExtend.settings}" maxlength="100" class="input-xlarge ${amsDesExtend.isNull==1?'required':''}"/>
									</c:if>
									<c:if test="${amsDesExtend.showType==\"textarea\"}">
									<textarea id="amsDesExtendList[${status.index}]_settings" name="amsDesExtendList[${status.index}].settings" maxlength="300" class="input-xxlarge ${amsDesExtend.isNull==1?'required':''}">${amsDesExtend.settings}</textarea>		
									</c:if>
									<c:if test="${amsDesExtend.showType==\"select\"}">
									<select id="amsDesExtendList[${status.index}]_settings" name="amsDesExtendList[${status.index}].settings" class=" ${amsDesExtend.isNull==1?'required':''} input-mini" style="width:95px;*width:75px" data-value="${amsDesExtend.settings}">
										<c:forEach items="${fns:getDictList(amsDesExtend.dictType)}" var="dict">
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
									<c:if  test="${amsDesExtend.showType==\"number\"}">
											 <input type="number" id="amsDesExtendList[${status.index}]_settings" name="amsDesExtendList[${status.index}].settings" maxlength="10" value="${amsDesExtend.settings}"/>
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
						<div class="form-actions">
							<shiro:hasPermission name="ams:amsArchivesInfo:edit">
								<input id="btnSubmit" class="btn btn-primary" type="submit" value="保存" />&nbsp;
								<%-- <c:if test="${not empty testAudit.id}">
									<input id="btnSubmit2" class="btn btn-inverse" type="submit" value="销毁申请" onclick="$('#flag').val('no')"/>&nbsp;
								</c:if> --%>
							</shiro:hasPermission>
							<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
						</div>
					</form:form>
				</div>
		</div>
		
	</c:if>
</body>
</html>