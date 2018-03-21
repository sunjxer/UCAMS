<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>业务管理档案案卷管理</title>
	<meta name="decorator" content="default"/>
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
			
			$("#archivesTypeButton, #archivesType").click(function(){
				// 是否限制选择，如果限制，设置为disabled
				if ($("#archivesTypeButton").hasClass("disabled")){
					return true;
				}
				// 正常打开	
				top.$.jBox.open("iframe:${ctx}/tag/treeselect?url="+encodeURIComponent("/ams/amsWorkArchives/treeData")+"&module=&checked=&extId=&isAll=", "选择类别", 300, 420, {
					ajaxData:{selectIds: $("archivesType").val()},buttons:{"确定":"ok", "清除":"clear", "关闭":true}, submit:function(v, h, f){
						if (v=="ok"){
							var tree = h.find("iframe")[0].contentWindow.tree;//h.find("iframe").contents();
							var ids = [], names = [], nodes = [];
							if ("" == "true"){
								nodes = tree.getCheckedNodes(true);
							}else{
								nodes = tree.getSelectedNodes();
							}
							for(var i=0; i<nodes.length; i++) {//
								if (nodes[i].isParent){
									top.$.jBox.tip("不能选择父节点（"+nodes[i].name+"）请重新选择。");
									return false;
								}//
								ids.push(nodes[i].id);
								names.push(nodes[i].name);//
								break; // 如果为非复选框选择，则返回第一个选择  
							}
							//$("#checkUserId").val(ids.join(",").replace(/u_/ig,""));
							$("#archivesType").val(names.join(","));
						}//
						else if (v=="clear"){
							$("#archivesType").val("");
							//$("#checkUserName").val("");
		                }//
						if(typeof userTreeselectCallBack == 'function'){
							userTreeselectCallBack(v, h, f);
						}
					}, loaded:function(h){
						$(".jbox-content", top.document).css("overflow-y","hidden");
					}
				});
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/ams/amsWorkArchives/list">业务管理档案案卷列表</a></li>
		<li class="active"><a href="${ctx}/ams/amsWorkArchives/form?id=${amsWorkArchives.id}">业务管理档案案卷<shiro:hasPermission name="ams:amsWorkArchives:edit">${not empty amsWorkArchives.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="ams:amsWorkArchives:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="amsWorkArchives" action="${ctx}/ams/amsWorkArchives/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<table class="table-form">
			<tr>
				<td align="center" colspan="4" width="20%"><b>案卷基本信息</b></td>
			</tr>
			
			<tr>
				<td class="tit" width="15%">案卷题名:</td>
				<td colspan="3">
					<form:input path="archivesName" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</td>
			</tr>
			
			<tr>
				<td class="tit" width="15%">案卷类型:</td>
				<td>
					<div class="input-append">
					<input id="archivesType" name="exten1" readonly="readonly" type="text" value="${amsWorkArchives.exten1}" data-msg-required=""
						class="input-xlarge required" style="width:150px"/>
						<a id="archivesTypeButton" href="javascript:" class="btn  " style="">&nbsp;<i class="icon-search"></i>&nbsp;</a>
					</div>
					<span class="help-inline"><font color="red">*</font> </span>
					<%-- <sys:treeselect id="" name="" value="${amsWorkArchives.exten1}" labelName="exten1" labelValue="${amsWorkArchives.exten1}"
						title="类别" url="/ams/amsUcArchiveMenu/treeData" extId="${amsWorkArchives.exten1}" cssClass=" input-xlarge required" allowClear="true"/>
					<span class="help-inline"><font color="red">*</font> </span> --%>
				</td>
				<td class="tit" width="15%">年度:</td>
				<td>
					<form:input path="year" htmlEscape="false" maxlength="11" class="input-xlarge required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</td>	
			</tr>
			
			<tr>
				<td class="tit" width="15%">编制单位:</td>
				<td>
					<c:if test="${not empty makeUnitList}">
						<form:select path="makeUnit" class="required input-xlarge">
							<form:options items="${makeUnitList}" itemLabel="makeUnit" itemValue="makeUnit" htmlEscape="false"/>
						</form:select>
					</c:if>
					<c:if test="${empty makeUnitList}">
						<form:input path="makeUnit" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
					</c:if>
					<span class="help-inline"><font color="red">*</font> </span>
				</td>
				<td class="tit" width="15%">档号:</td>
				<td>
					<form:input path="archivesCode" htmlEscape="false" maxlength="50" class="input-xlarge "/>		
				</td>							
			</tr>
			
			<tr>
				<td class="tit" width="15%">库房ID:</td>
				<td>
					<form:input path="warehouseId" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</td>
			</tr>
			
			<tr>
				<td class="tit" width="15%">列:</td>
				<td>
					<form:input path="cell" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</td>
				<td class="tit" width="15%">柜:</td>
				<td>
					<form:input path="cabinet" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</td>
			</tr>
			
			<tr>
				<td class="tit" width="15%">层:</td>
				<td>
					<form:input path="layer" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</td>
			</tr>
			
			<tr>
				<td class="tit" width="15%">案卷内文件数量:</td>
				<td>
					<form:input path="filesCount" htmlEscape="false" maxlength="9" class="input-xlarge required" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</td>
				<td class="tit" width="15%">载体类型:</td>
				<td>
					<form:select path="carrierType" class="input-xlarge required">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('carrier_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
					<span class="help-inline"><font color="red">*</font> </span>
				</td>
			</tr>
			
			<tr>
				<td class="tit" width="15%">卷内文件起始时间:</td>
				<td>
					<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
						value="<fmt:formatDate value="${amsArchivesInfo.startDate}" pattern="yyyy-MM-dd"/>"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				</td>
				<td class="tit" width="15%">卷内文件终止时间:</td>
				<td>
					<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
						value="<fmt:formatDate value="${amsArchivesInfo.endDate}" pattern="yyyy-MM-dd"/>"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				</td>
			</tr>
			
			<tr>
				<td class="tit" width="15%">移交日期:</td>
				<td>
					<input name="makeDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
						value="<fmt:formatDate value="${amsWorkArchives.makeDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</td>
			</tr>
			
			<tr>
				<td class="tit" width="15%">保管期限:</td>
				<td>
					<form:select path="storagePeriod" class="input-xlarge required">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('storage_period')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
					<span class="help-inline"><font color="red">*</font> </span>
				</td>
				<td class="tit" width="15%">密级:</td>
				<td>
					<form:select path="degreeSecrets" class="input-xlarge required">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('degree_secrets')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>		
					<span class="help-inline"><font color="red">*</font> </span>
				</td>
			</tr>
		<tr>
			<td class="tit" width="15%">主题词:</td>
			<td colspan="3">
				<form:input path="mainTitle" htmlEscape="false" maxlength="200" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</td>
		</tr>
		
		<tr>
			<td class="tit" width="15%">组卷情况说明:</td>
			<td colspan="3">
				<form:input path="archivesExplain" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</td>
		</tr>
		
		<tr>
			<td class="tit" width="15%">扩展信息Json:</td>
			<td colspan="3">
				<form:input path="archivesJson" htmlEscape="false" class="input-xlarge "/>
			</td>
		</tr>
		
		<tr>
			<td class="tit" width="20%">备注信息:</td>
			<td colspan="3">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</td>
		</tr>
		</table>
		<div class="form-actions">
			<c:if test="${formView!=1}">
				<shiro:hasPermission name="ams:amsWorkArchives:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>