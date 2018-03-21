<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>用户管理</title>
<meta name="decorator" content="default" />
<%@include file="/WEB-INF/views/include/treeview.jsp"%>
<script src="${ctxStatic}/jquery-validation/1.14.0/localization/messages_zh.min.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function(){	
		//详细信息点击事件
		var $m_btn = $('#y-modalBtnAdd');  //y-modalBtnAdd是button的id
	    var $modal = $('#y-myModalAdd');  //y-myModalAdd是弹出的遮罩层的id，通过这两个id进行绑定
	    $m_btn.on('click', function(){
	        var roleId = $("#roleId").val();
			if(roleId !='' && roleId != null){
		        $.ajax({
	                url:"${ctx}/sys/resBody/getReuseInfo",
	                type:"post",
	                data:{
	                	roleId : roleId
	                },
	                success:function(data){
	                	 if(data.success && data.obj !=null){
								
		                        if(typeof data.obj.unitCreditCode != 'undefined'&&data.obj.unitCreditCode != ''&&data.obj.unitCreditCode != null ){
		                        	$('#unitCreditCode').html("<input type='text'  name='unitCreditCode' maxlength='18' value="+data.obj.unitCreditCode+">");
								}else{
									$('#unitCreditCode').html("<input type='text' maxlength='18' name='unitCreditCode' value=''/>");	
								}if(typeof data.obj.address != 'undefined'&&data.obj.address != ''&&data.obj.address != null ){
									$('#address').html("<input type='text' name='address' maxlength='20' value="+data.obj.address+">");
								}else{
									$('#address').html("<input type='text' name='address' maxlength='20' value=''>");	
								}if(typeof data.obj.qualifications != 'undefined'&&data.obj.qualifications != ''&&data.obj.qualifications != null ){
									$('#qualifications').html("<input id ='s2'  type='text'  required  name='qualifications' maxlength='20' value="+data.obj.qualifications+">");
								}else{
									$('#qualifications').html("<input id='s2'  type='text'  required  maxlength='20' name='qualifications' value=''>");								
								}if(typeof data.obj.responsiblePerson != 'undefined'&&data.obj.responsiblePerson != ''&&data.obj.responsiblePerson != null ){
									$('#responsiblePerson').html("<input type='text' maxlength='20' name='responsiblePerson' value="+data.obj.responsiblePerson+" >");								
								}else{
									$('#responsiblePerson').html("<input type='text'  maxlength='20' name='responsiblePerson' value=''>");								
								}if(typeof data.obj.qualificationsNumber != 'undefined'&&data.obj.qualificationsNumber != ''&&data.obj.qualificationsNumber != null){
									$('#qualificationsNumber').html("<input type='text' maxlength='20'  name='qualificationsNumber' value="+data.obj.qualificationsNumber+">");
								}else{
									$('#qualificationsNumber').html("<input type='text' maxlength='20' name='qualificationsNumber' value=''>"); 
								}if(typeof data.obj.legalPerson != 'undefined'&&data.obj.legalPerson != ''&&data.obj.legalPerson != null ){
									 $('#legalPerson').html("<input name='legalPerson' maxlength='20' type='text' value="+data.obj.legalPerson+">"); 
								}else{
									 $('#legalPerson').html("<input name='legalPerson' maxlength='20' type='text' value=''>"); 
								}
														
								$('#unitDateInfoId').val(data.obj.id);
								$modal.modal({backdrop:'static'});
		                    	
								}else{
		                    	top.$.jBox.tip("该责任主体无详细信息");
		                    }
	                } 
	             })
			}else{
				top.$.jBox.tip("请在左侧选择一个责任主体单位");
			}
	    });
		//组织机构树
		var setting = {check:{enable:true,nocheckInherit:true},view:{selectedMulti:false},
				data:{simpleData:{enable:true}},callback:{beforeClick:function(id, node){
					tree.checkNode(node, !node.checked, true, true);
					return false;
				}}};
		
		// 用户-机构
		var zNodes2=[
				<c:forEach items="${officeList}" var="office">{id:"${office.id}", pId:"${not empty office.parent?office.parent.id:0}", name:"${office.name}"},
	            </c:forEach>];
		// 初始化树结构
		var tree2 = $.fn.zTree.init($("#officeTree"), setting, zNodes2);
		// 不选择父节点
		tree2.setting.check.chkboxType = { "Y" : "ps", "N" : "s" };
		// 默认选择节点
	    var ids2 = "${role.officeIds}".split(",");
		for(var i=0; i<ids2.length; i++) {
			var node = tree2.getNodeByParam("id", ids2[i]);
			try{tree2.checkNode(node, true, false);}catch(e){}
		} 
		// 默认展开全部节点
		tree2.expandAll(true);
		
		$("#delResBodyRole").click(function(){
			 if(confirm('确定要删除该行信息?')){
				var roleId = $("#roleId").val();
				var sysData = $('#sysData').val();
				if(roleId !='' && roleId != null){
					window.location.href="${ctx}/sys/resBody/delete_role?id=" + roleId+ "&sysData=" + sysData; 
				}else{
					top.$.jBox.tip("请在右侧选择一个责任主体单位");
				}
			 }
		});
		
		$("#editResBodyRole").click(function(){
			if(confirm('确定要修改该单位信息?')){
				var ids2 = [],  nodes2 = tree2.getCheckedNodes(true);
				var roleId = $("#roleId").val();
				for(var i=0; i<nodes2.length; i++) {
					ids2.push(nodes2[i].id);
				}
				$("#officeIds").val(ids2);
				loading('正在提交，请稍等...');
				if(roleId !='' && roleId != null){
					$("#editRoleForm").submit();
				}else{
					top.$.jBox.tip("请在右侧选择一个责任主体单位");
				}
			}
		});
	});
	function page(n, s) {
		if (n)
			$("#pageNo").val(n);
		if (s)
			$("#pageSize").val(s);
		var roleId = $("#roleId").val();
		if(roleId != null){
			$("#searchForm").attr("action", "${ctx}/sys/resBody/list?role.id="+roleId);
		}else{
			$("#searchForm").attr("action", "${ctx}/sys/resBody/list");
		}
		$("#searchForm").submit();
		return false;
	}
	
/* 	$('#s2').blur(function(){
		if($('#s2').val().length>0){
		$("#span2").empty();
		
		}else{
		$("#span2").html("*，资质等级不能为空").css('color','#930093');
			
		}
		}); */
</script>

</head>
<body>

	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/resBody/list">责任主体</a></li>
		<shiro:hasPermission name="sys:resBodyUser:edit">
			<li><a href="${ctx}/sys/resBody/form_user">用户添加</a></li>
		</shiro:hasPermission>
		<shiro:hasPermission name="sys:resBodyRole:edit">
			<li><a href="${ctx}/sys/resBody/form_role">责任主体添加</a></li>
		</shiro:hasPermission>
	</ul>
	<sys:message content="${message}" />
	<div class="accordion" id="accordion2">
		
		<div class="accordion-group">
			<div class="accordion-heading">
				<a class="accordion-toggle" data-toggle="collapse"
					data-parent="#accordion2" href="#collapseTwo"> 责任主体信息 </a>
			</div>
			<div id="collapseTwo" class="accordion-body collapse in">
				<div class="accordion-inner">
					<form:form id="editRoleForm" modelAttribute="role" class="form-horizontal" action="${ctx}/sys/resBody/save_role" method="post" >
						<sys:message content="${message}" />
						<input type="hidden" id="roleId" name="id" value="${role.id}">
						<input type="hidden" id="sysData" value="${role.sysData}">
						<input type="hidden" id="roleType"  name="roleType" value="${role.roleType}">
						<br>
						<div class="control-group">
							<label class="control-label">详细信息:</label>
							<div class="controls" >
								<a href="#" id="y-modalBtnAdd">点击查看详细信息</a>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">单位名称:</label>
							<div class="controls">
								<input id="oldName" name="oldName" type="hidden"
									value="${role.name}">
								<form:input path="name" htmlEscape="false" maxlength="50"
									class="required" />
								<span class="help-inline"><font color="red">*</font> </span>
							</div>
						</div>
						<%-- <div class="control-group">
							<label class="control-label">英文名称:</label>
							<div class="controls">
								<input id="oldEnname" name="oldEnname" type="hidden"
									value="${role.enname}">
								<form:input path="enname" htmlEscape="false" maxlength="50"
									class="required abc" />
								<span class="help-inline"><font color="red">*</font>
									工作流用户组标识</span>
							</div>
						</div> --%>
						<%-- <div class="control-group">
							<label class="control-label">单位类型:</label>
							<div class="controls">
								
								<form:input path="roleType" htmlEscape="false" maxlength="50" class="required"/>
								<span class="help-inline" title="activiti有3种预定义的组类型：security-role、assignment、user 如果使用Activiti Explorer，需要security-role才能看到manage页签，需要assignment才能claim任务">
									工作流组用户组类型（security-role：管理员、assignment：可进行任务分配、user：普通用户）</span>
								<form:select path="roleType" class="input-medium">
									<form:option value="assignment">监理单位</form:option>
									<form:option value="user">施工单位</form:option>
								</form:select>
							</div>
						</div> --%>
						<div class="control-group">
							<label class="control-label">是否可用:</label>
							<div class="controls">
								<form:select path="useable">
									<form:options items="${fns:getDictList('yes_no')}"
										itemLabel="label" itemValue="value" htmlEscape="false" />
								</form:select>
								<span class="help-inline">“是”代表此数据可用，“否”则表示此数据不可用</span>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">工程授权:</label>
							<div class="controls">
								<div id="officeTree" class="ztree" style="margin-top: 3px; float: left;"></div>
								<form:hidden path="officeIds" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">备注:</label>
							<div class="controls">
								<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/>
							</div>
						</div>
						<shiro:hasPermission name="sys:resBodyRole:edit">
							<div class="breadcrumb form-search">
								<ul class="ul-form" style="margin-left: 100px">
									<li><label><input id="editResBodyRole" class="btn btn-primary" type="button" value="修改" /></label></li>
									<li><label style="width: 50px !important;"><input id="delResBodyRole" class="btn btn-danger " type="button" value="删除" /></label></li>
								</ul>
							</div>
						</shiro:hasPermission>
					</form:form>
				</div>
			</div>
		</div>
		<div class="accordion-group">
			<div class="accordion-heading">
				<a class="accordion-toggle" data-toggle="collapse"
					data-parent="#accordion2" href="#collapseOne"> 用户信息 </a>
			</div>
			<div id="collapseOne" class="accordion-body collapse in">
				<div class="accordion-inner">
					<form:form id="searchForm" modelAttribute="user"
						action="${ctx}/sys/user/list" method="post"
						class="breadcrumb form-search ">
						<input id="pageNo" name="pageNo" type="hidden"
							value="${page.pageNo}" />
						<input id="pageSize" name="pageSize" type="hidden"
							value="${page.pageSize}" />
						<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}"
							callback="page();" />
						<ul class="ul-form">
							<li><label>登录名：</label> <form:input path="loginName"
									htmlEscape="false" maxlength="50" class="input-medium" /></li>
							<li><label>姓&nbsp;&nbsp;&nbsp;名：</label> <form:input
									path="name" htmlEscape="false" maxlength="50"
									class="input-medium" /></li>
							<li class="btns"><input id="btnSubmit"
								class="btn btn-primary" type="submit" value="查询"
								onclick="return page();" />
							<li class="clearfix"></li>
						</ul>
					</form:form>
					<sys:message content="${message}" />
					<table id="contentTable"
						class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<!-- <th>归属公司</th>
								<th>归属部门</th> -->
								<th class="sort-column login_name">登录名</th>
								<th class="sort-column name">姓名</th>
								<th>电话</th>
								<th>手机</th>
								<%--<th>角色</th> --%>
								<shiro:hasPermission name="sys:resBodyUser:edit">
									<th>操作</th>
								</shiro:hasPermission>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${page.list}" var="user">
								<tr>
									<%-- <td>${user.company.name}</td>
									<td>${user.office.name}</td> --%>
									<td><a href="${ctx}/sys/resBody/form_user?id=${user.id}">${user.loginName}</a></td>
									<td>${user.name}</td>
									<td>${user.phone}</td>
									<td>${user.mobile}</td>
									<shiro:hasPermission name="sys:resBodyUser:edit">
										<td><a href="${ctx}/sys/resBody/form_user?id=${user.id}">修改</a>
											<a href="${ctx}/sys/resBody/delete_user?id=${user.id}"
											onclick="return confirmx('确认要删除该用户吗？', this.href)">删除</a></td>
									</shiro:hasPermission>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<div class="pagination">${page}</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 模态弹出窗内容 -->
	<div class="modal" id="y-myModalAdd" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel"  style="display: none">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">
	          <span aria-hidden="true">×</span>
	        </button>
	        <h4 class="modal-title">详细信息</h4>
	      </div>
	      <div class="modal-body">
				<%-- <form  name="form1" id="form1" action="${ctx}/ams/amsUnitDetailinfo/save" method="post" class="form-horizontal"> --%>
				<form:form id="form1" modelAttribute="amsUnitDetailinfo" action="${ctx}/ams/amsUnitDetailinfo/save" method="post" class="form-horizontal">
						<input type="hidden" id="unitDateInfoId" name="id"/> 
						<table class="table-form">
						<!-- <tr>
						<td align="center" colspan="4" width="20%"><b></b></td>
						</tr> -->
						<tr>
						<td class="tit" >社会信用代码:</td>
						<td colspan="3"><label id="unitCreditCode"> </label></td>
						</tr>
						<tr>
						<td class="tit" >单位地址:</td>
						<td colspan="3"> <label id="address"></label> </td>
						</tr>
						<tr>
						<td class="tit" >单位资质等级:</td>
						<td colspan="3"><label id="qualifications"></label><span class="help-inline"><font color="red">*</font> </span></td>
						</tr>
						<tr>
						<td class="tit" >单位资质证书号:</td>
						<td colspan="3"><label id="qualificationsNumber"></label></td>
						</tr>
						<tr>
						<td class="tit" >项目负责人:</td>
						<td><label id="responsiblePerson"></label></td><tr>
						<td class="tit" >法人:</td>
						<td><label id="legalPerson"></label></td>
						
						</table >
						
				  		<input id="yes" class="btn btn-primary"  type="submit" value="保存"  />
				  		<input type="button" class="btn"  value="返回" onclick="javascript:history.go(0);"/>
				</form:form>
	      </div>
	      <div class="modal-footer">&nbsp;</div>
	    </div>
	  </div>
	</div>
</body>
</html>