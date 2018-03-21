<%@ page contentType="text/html;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>角色管理</title>
	<meta name="decorator" content="default"/>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/> 
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>>
	<script type="text/javascript">
		$(document).ready(function(){
			// dom加载完毕
		    var $m_btn = $('#y-modalBtnAdd');  //y-modalBtnAdd是button的id
		    var $modal = $('#y-myModalAdd');  //y-myModalAdd是弹出的遮罩层的id，通过这两个id进行绑定
		    var isClick = false;  //按钮点击事件标识 
			$("#name").focus();
			$('#oldBodyInfo').hide();
			$("#inputForm").validate({
				rules: {
					//中文验证方式
					name: {
						remote: {
					    url: "${ctx}/sys/resBody/checkName",
					    type: "post",
					    dataType: "json",
					    async : false,
					    contentType: "application/x-www-form-urlencoded; charset=utf-8",
					    data: { 
					    	oldName: function() { return $('#oldName').val()},
					    	name: function() { return $('#name').val() } 
					    },
						success:function(data){
							if(! data.success){ //如果已存在
								//显示输入框后的提示
								$('#oldBodyInfo').show();
								//获取责任主体信息并回显
								if(data.obj !=null){
									$('#unitCreditCode').text(data.obj.unitCreditCode);
									$('#address').text(data.obj.address);
									$('#qualifications').text(data.obj.qualifications);
									$('#responsiblePerson').text(data.obj.responsiblePerson);
									$('#legalPerson').text(data.obj.legalPerson);
									$('#unitDateInfoId').val(data.obj.id);
									//显示责任主体信息
									$modal.modal({backdrop: 'static'});
								}else{
									top.$.jBox.tip("该责任主体已存在,但无详细信息");
								}
							}else{
								$('#oldBodyInfo').hide();
							}
			            }
					  }
					}, 
					enname: {remote: "${ctx}/sys/role/checkEnname?oldEnname=" + encodeURIComponent("${role.enname}")}
				},
				messages: {
					//name: {remote: "单位名已存在"},
					enname: {remote: "英文名已存在"}
				},
				submitHandler: function(form){
					var ids2 = [], nodes2 = tree2.getCheckedNodes(true);
					for(var i=0; i<nodes2.length; i++) {
						ids2.push(nodes2[i].id);
					}
					$("#officeIds").val(ids2);
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
			
			//添加方式控制
			/* refreshResFlag();
			$("#flagSeclect").change(function(){
				refreshResFlag();
			}); */
			$('#oldBodyInfo').click(function(){
				//显示责任主体信息
				$modal.modal({backdrop: 'static'});
			})
			$('#btnSubmit').click(function(){
				top.$.jBox.confirm("确认添加？成功后请刷新右侧树!","系统提示",function(v,h,f){
					if(v=="ok"){
						var ids2 = [], nodes2 = tree2.getCheckedNodes(true);					
						for(var i=0; i<nodes2.length; i++) {
							ids2.push(nodes2[i].id);
						}
						$("#officeIds").val(ids2);
						var data = $("#inputForm").serialize();
						 $.ajax({
			                url:"${ctx}/sys/resBody/save_role",
			                type:"post",
			                data:data,
			                success:function(data){
			                	//跳转到列表页
		                    	location.href = '${ctx}/sys/resBody/list';
		                    	top.$.jBox.tip("请刷新右侧责任主体树!");
			                } 
			            });
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
				 
			})
			$('#reuseInfo').click(function(){
				top.$.jBox.confirm("确认复用？成功后请刷新右侧树!","系统提示",function(v,h,f){
					if(v=="ok"){
				 $.ajax({
		                url:"${ctx}/sys/resBody/reuseInfo",
		                type:"post",
		                data:{
		                	unitDetaInfoId : $('#unitDateInfoId').val(),
		                	resBodyType:  $('#reuseRoleType').val()
		                },
		                success:function(data){
		                    if(data.success){
		                    	$modal.modal('hide');
		                    	//跳转到列表页
		                    	location.href = '${ctx}/sys/resBody/list';
		                    	top.$.jBox.tip("请刷新右侧责任主体树!");
		                    }
		                } 
		            });
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			})
		});
		
		/* function refreshResFlag(){
			if($("#flagSeclect").val()==1){
				$("#addNewRes").hide();
				$("#oldResBodys").show();
			}else{
				$("#addNewRes").show();
				$("#oldResBodys").hide();
			}
		} */
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/resBody/list">责任主体</a></li>
		<li><a href="${ctx}/sys/resBody/form_user?id=${user.id}">用户<shiro:hasPermission name="sys:resBodyUser:edit">${not empty user.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:resBodyUser:edit">查看</shiro:lacksPermission></a></li>
		<li class="active"><a href="${ctx}/sys/resBody/form_role?id=${user.id}">责任主体<shiro:hasPermission name="sys:resBodyRole:edit">${not empty role.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:resBodyRole:edit">查看</shiro:lacksPermission></a></li></ul><br/>
	<form:form id="inputForm" modelAttribute="role"  action="${ctx}/sys/resBody/save_role"  method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="sysData"/>
		<sys:message content="${message}"/>
		<div id="addNewRes">
		<div class="control-group">
			<label class="control-label">单位名称:</label>
			<div class="controls">
				<input id="oldName" name="oldName" type="hidden" value="${role.name}">
				<form:input path="name" htmlEscape="false" maxlength="50" class="required userName"/>
				<span class="help-inline"><font color="red">*</font> </span>
				<span class="help-inline" ><a id="oldBodyInfo" href="#" style="color: red;">该单位已存在</a></span>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">英文名称:</label>
			<div class="controls">
				<input id="oldEnname" name="oldEnname" type="hidden" value="${role.enname}">
				<form:input path="enname" htmlEscape="false" maxlength="50" class="required abc"/>
				<span class="help-inline"><font color="red">*</font> 工作流用户组标识</span>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">单位类型:</label>
			<div class="controls"><%--
				<form:input path="roleType" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline" title="activiti有3种预定义的组类型：security-role、assignment、user 如果使用Activiti Explorer，需要security-role才能看到manage页签，需要assignment才能claim任务">
					工作流组用户组类型（security-role：管理员、assignment：可进行任务分配、user：普通用户）</span> --%>
				<form:select path="roleType" class="input-medium">
					<form:option value="assignment">监理单位</form:option>
					<%-- <form:option value="security-role">建设单位</form:option> --%>
					<form:option value="user">施工单位</form:option>
				</form:select>
				<span class="help-inline" title="activiti有3种预定义的组类型：security-role、assignment、user 如果使用Activiti Explorer，需要security-role才能看到manage页签，需要assignment才能claim任务">
					工作流组用户组类型（监理单位：assignment、施工单位：user）</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否可用:</label>
			<div class="controls">
				<form:select path="useable">
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline">“是”代表此数据可用，“否”则表示此数据不可用</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">工程授权:</label>
			<div class="controls">
				<div id="officeTree" class="ztree" style="margin-top:3px;float:left;"></div>
				<form:hidden path="officeIds"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/>
			</div>
		</div>
		</div>
		<div class="form-actions">
			<c:if test="${(role.sysData eq fns:getDictValue('是', 'yes_no', '1') && fns:getUser().admin)||!(role.sysData eq fns:getDictValue('是', 'yes_no', '1'))}">
				<shiro:hasPermission name="sys:resBodyRole:edit">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
				</shiro:hasPermission>
			</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	
	<!-- 模态弹出窗内容 -->
	<div class="modal" id="y-myModalAdd"  style="display: none">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">
	          <span aria-hidden="true">×</span>
	        </button>
	        <h4 class="modal-title">&nbsp;</h4>
	      </div>
	      <div class="modal-body">
				<div class="form-horizontal" >
						 <input type="hidden" id="unitDateInfoId" > 
						<table class="table-form">
						<tr>
						<td align="center" colspan="4" width="20%"><b>该单位已经存在,请核对信息 !</b></td>
						</tr>
						<tr>
						<td class="tit" width="20%">社会信用代码:</td>
						<td colspan="3"><label id="unitCreditCode"></label></td>
						</tr>
						<tr>
						<td class="tit" width="20%">单位地址:</td>
						<td colspan="3"> <label id="address"></label> </td>
						</tr>
						<tr>
						<td class="tit" width="20%">单位资质等级:</td>
						<td colspan="3"><label id="qualifications"></label></td>
						</tr>
						<tr>
						<td class="tit" width="20%">项目负责人:</td>
						<td><label id="responsiblePerson"></label></td>
						<td class="tit" width="20%">法人：</td>
						<td><label id="legalPerson"></label></td>
						</tr>
						<tr>
						<td class="tit" width="20%">重置单位类型:</td>
						<td colspan="3">
							<select id="reuseRoleType" class="input-medium">
								<option value="assignment">监理单位</option>
								<option value="user">施工单位</option>
							</select>
						</td>
						</tr>
						</table>
				</div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	        <button id="reuseInfo" type="button" class="btn btn-primary">复用</button>
	      </div>
	    </div>
	  </div>
	</div>

</body>
</html>