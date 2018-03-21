<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta charset="utf-8" />
<script src="${ctxStatic}/assets/js/jquery-2.2.4.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/assets/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/assets/js/jquery.bootstrap.wizard.js" type="text/javascript"></script>
<script src="${ctxStatic}/assets/js/gsdk-bootstrap-wizard.js"></script>
<script src="${ctxStatic}/bootstrap/2.3.1/js/bootstrap.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.css" rel="stylesheet" />
<script src="${ctxStatic}/jquery-validation/1.14.0/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery-validation/1.14.0/localization/messages_zh.min.js" type="text/javascript"></script>

<script type="text/javascript">
	jQuery.browser = {};
	(function() {
		jQuery.browser.msie = false;
		jQuery.browser.version = 0;
		if (navigator.userAgent.match(/MSIE ([0-9]+)./)) {
			jQuery.browser.msie = true;
			jQuery.browser.version = RegExp.$1;
		}
	})();
</script>

<script src="${ctxStatic}/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="${ctxStatic}/common/mustache.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/common/ucams.js" type="text/javascript"></script>
<script type="text/javascript">
	var ctx = '${ctx}', ctxStatic = '${ctxStatic}';
</script>
<link href="${ctxStatic}/treeTable/themes/vsStyle/treeTable.min.css" rel="stylesheet" type="text/css" />
<script src="${ctxStatic}/treeTable/jquery.treeTable.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.min.css" rel="stylesheet" type="text/css" />
<link href="${ctxStatic}/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css" rel="stylesheet" type="text/css" />
<script src="${ctxStatic}/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>
<link rel="apple-touch-icon" sizes="76x76" href="${ctxStatic}/assets/img/apple-icon.png">
<link rel="icon" type="image/png" href="${ctxStatic}/assets/img/favicon.png">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>工程报建</title>
<meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport' />
<meta name="viewport" content="width=device-width" />
<link href="${ctxStatic}/bootstrap/2.3.1/css_${not empty cookie.theme.value ? cookie.theme.value : 'cerulean'}/bootstrap.min.css" type="text/css" rel="stylesheet" />
 
<!--     Fonts and icons     -->
<!-- CSS Files -->
<link href="${ctxStatic}/assets/css/bootstrap.min.css" rel="stylesheet" />
<link href="${ctxStatic}/assets/css/gsdk-bootstrap-wizard.css" rel="stylesheet" />
<style>
.form-horizontal .table-form {
	width: 100%;
	border-color: #ddd
	font-family: Helvetica, Georgia, Arial, sans-serif, 瀹嬩綋;
	font-size: 13px;
}

.form-horizontal .table-form, .form-horizontal .table-form td {
font-family: Helvetica, Georgia, Arial, sans-serif, 瀹嬩綋;
	font-size: 13px;
	border: 1px solid #ddd;
	background-color: #fdfdfd;
	padding: 2px
}

.form-horizontal .table-form td.tit {
font-family: Helvetica, Georgia, Arial, sans-serif, 瀹嬩綋;
	font-size: 13px;
	background: #fafafa;
	text-align: center;
	padding-right: 4px;
	white-space: nowrap
}
.resetwh01{
    width: 95% !important;  
	height: 30px !important;
}
.resetwh02{
	height: 30px !important;
}
.tab-content{
	overflow:hidden !important;
}
.jbox-content{
	overflow: !important;
}
.moving-tab{
	width : 180px!important;
}
</style>

<script type="text/javascript">
	$(document).ready(function() {
				$("#submit").on(
						'click',
						function() {
							if ($("#projectName").val() == ''
									|| $("#projectName").val() == undefined) {
								$("#accountC").click();
								return false;
							}
							if ($("#loginName").val() == ''
									|| $("#loginName").val() == undefined) {
								$("#addressaC").click();
								return false;
							}
				});
				$("#inputForm").validate({
					rules : {
						//项目名称验证
						projectName: {
							remote: {
						    url: "${ctx}/ams/amsProjectInfo/checkProjectName",
						    type: "post",
						    dataType: "json",
						    async : false,
						    contentType: "application/x-www-form-urlencoded; charset=utf-8",
						    data: { 
						    	projectName: function() { return $('#projectName').val() } 
						    },
						  }
						},
						//角色名称验证
						name : {
							required : true,
							remote : {
								url : "${ctx}/ams/amsProjectInfo/checkName",
								type : "post", //数据发送方式
								dataType : "json", //接受数据格式
								data : { //要传递的数据
									name : function() {
										return $("#name").val();
									}
								}
							}
						},
						//登录名称验证
						loginName: {remote: "${ctx}/ams/amsProjectInfo/checkLoginName"},
						enname: {remote: "${ctx}/ams/amsProjectInfo/checkEnname"},
						//社会征信验证
						unitCreditCode : {
							required : true,
							remote : {
								url : "${ctx}/ams/amsProjectInfo/checkUnitCreditCode",
								type : "post", //数据发送方式
								dataType : "json", //接受数据格式
								data : { //要传递的数据
									codeName : function() {
										return $("#unitCreditCode").val();
									}
								}
							}
						}
					}
				})
			});

	function downloadFile(url) {
		try {
			var elemIF = document.createElement("iframe");
			elemIF.src = url;
			elemIF.style.display = "none";
			document.body.appendChild(elemIF);
		} catch (e) {

		}
	}

</script>

</head>

<body>
	<div style="background-image: url('')">
		<!--   Creative Tim Branding   -->
		
		<div class="container">
			<div class="row">
				<div class="col-sm-8 col-sm-offset-2">
					<!--      Wizard container        -->
					<div class="wizard-container">

						<div class="card wizard-card" data-color="orange"
							id="wizardProfile">
							<form:form id="inputForm" modelAttribute="amsProjectInfo"
								action="${ctx}/ams/amsProjectInfo/firstSave" method="post"
								class="form-horizontal" enctype="multipart/form-data" onkeydown="if(event.keyCode==13){return false;}">
								<div class="wizard-header">
									<h3>
										<div style="position: absolute; left: 4%; top: 110px;">
										<a href="${ctx}/logout" >
											<img src="${ctxStatic}/assets/img/back.png" title="返回主页"style="width: 40px ;"></a>
										</div>
										<small>请将下列信息填写完整并提交申请</small>
									</h3>
								</div>

								<div class="wizard-navigation">
									<ul>
										<li><a href="#about" id="aboutC" data-toggle="tab">建设单位信息</a></li>
										<li><a href="#account" id="accountC" data-toggle="tab">项目信息</a></li>
										<li><a href="#addressa" id="addressaC" data-toggle="tab">账号信息</a></li>
										<li><a href="#addressa2" data-toggle="tab">协议条款</a></li>
									</ul>
								</div>

								<div class="tab-content">
									<div class="tab-pane" id="about">
										<div class="row">
											<div class="col-sm-10 col-sm-offset-1">
												<div class="form-group">
												<form:hidden path="role.name"/>
													<label>建设单位名称 <small></small></label><input id="name" value=""
														name="name" type="text"  maxlength="30" class="form-control resetwh01 required"
														placeholder="">
												<span class="help-inline"><font color="red">*</font> </span>
												</div>
											 
												<div class="form-group">
													<label>统一社会信用代码 <small></small></label><input 
													 id="unitCreditCode"  name="unitCreditCode"   maxlength="18" type="text" value=""
														class="form-control resetwh01 required society" placeholder="">
														<span class="help-inline"><font color="red">*</font></span>
												</div>
										
												<div class="form-group">
													<label>建设单位地址<small></small></label> <input
														name="amsUnitDetailInfo.address" type="text" value="" maxlength="30"
														class="form-control  resetwh01 required" placeholder="">
												<span class="help-inline"><font color="red">*</font></span>
												</div>
											</div>
										</div>
									</div>
									<div class="tab-pane" id="account">
										<div class="row" style="padding-left: 25px;">
											<table class="table-form" style="width: 98%">
												<tr>
													<td class="tit">项目类别</td>
													<td style="width:38% ;"><form:select path="projectType"
															class="input-xlarge ">
															<form:options
																items="${fns:getAmsDictList('unit_project_type')}"
															maxlength="64"	itemLabel="label" itemValue="value" htmlEscape="false" />
														</form:select>
														</td>
													<td class="tit">项目名称</td>
													<td style="width:38% ;"><form:input path="projectName" htmlEscape="false"
															 maxlength="100" class="input-xlarge resetwh01 required" /> <span
														class="help-inline"><font color="red">*</font> </span></td>
												</tr>
												<tr>
													<td class="tit">项目地点</td>
													<td><sys:treeselect id="local" name="local" 
															value="${office.area.id}" labelName="area.name"
															labelValue="${office.area.name}" title="区域" cssClass="input-large resetwh01  maxlength='21'"
															url="/ams/amsProjectInfo/areaTreeData" /></td>
													<td class="tit">项目地址</td>
													<td><form:input path="address" htmlEscape="false" id="address"
															maxlength="30" class="input-xlarge resetwh01 required" />
															<span
														class="help-inline"><font color="red">*</font> </span>
															</td>
												</tr>
												<tr>
													<td class="tit">立项批准<br />单位
													</td>
													<td><form:input path="projectApprovalUnit"
															htmlEscape="false" maxlength="30"
															class="input-xlarge resetwh01 required" /> <span
														class="help-inline"><font color="red">*</font> </span></td>
													<td class="tit">设计单位</td>
													<td><form:input path="designUnit" htmlEscape="false"
															maxlength="20" class="input-xlarge resetwh01 required" />
															<span class="help-inline"><font color="red">*</font> </span>
													</td>
												</tr>
												<tr>
													<td class="tit">勘察单位</td>
													<td><form:input path="prospectingUnit"
															htmlEscape="false" maxlength="30" class="input-xlarge resetwh01 required" />
															<span
														class="help-inline"><font color="red">*</font> </span>
													</td>
													<td class="tit">监理单位</td>
													<td><form:input path="supervisionUnit"
															htmlEscape="false" maxlength="30" class="input-xlarge  resetwh01 required" />
															<span
														class="help-inline"><font color="red">*</font> </span>
													</td>
												</tr>
												<tr>
													<td class="tit">立项批准<br />文号
													</td>
													<td><form:input path="approvalNumber"
															htmlEscape="false" maxlength="20" class="input-xlarge resetwh01" />
													</td>
													<td class="tit">立项批准<br />附件
													</td>
													<td><input type="file" name="file" accept="image/jpeg,image/tiff"/></td>
												</tr>

												<tr>
													<td class="tit">建设规划<br />许可证号
													</td>
													<td><form:input path="planningLicenseNumber"
															htmlEscape="false" maxlength="20" class="input-xlarge resetwh01 required" />
															<span
														class="help-inline"><font color="red">*</font> </span>
													</td>
													<td class="tit">建设规划<br />许可证附件
													</td>
													<td><input type="file" name="file" accept="image/jpeg,image/tiff" /></td>
												</tr>
												<tr>
													<td class="tit">用地规划<br />许可证号
													</td>
													<td><form:input path="landLicenseNumber"
															htmlEscape="false" maxlength="20" class="input-xlarge resetwh01" />
													</td>
													<td class="tit">用地规划<br />许可证附件
													</td>
													<td>
														<!-- <span>
															<input name="" type="text" id="viewfile" onmouseout="document.getElementById('upload').style.display='none';" 
													        	class="input-large resetwh01"  />
														</span>
												        <label for="unload" onmouseover="document.getElementById('upload').style.display='block';" class="file1">浏览...</label>
												        <input type="file" onchange="document.getElementById('viewfile').value=this.value;this.style.display='none';" class="file" id="upload" /> -->
												        <input type="file" name="file"  accept="image/jpeg,image/tiff"/>
														
													</td>
												</tr>

												<tr>
													<td class="tit">用地许可<br />证号
													</td>
													<td><form:input path="landPermitNumber"
															htmlEscape="false" maxlength="20" class="input-xlarge resetwh01" />
													</td>
													<td class="tit">用地许可<br />证附件<br />
													</td>
													<td><input type="file" name="file" accept="image/jpeg,image/tiff"/></td>
												</tr>

												<tr>
													<td class="tit">开工日期</td>
													<td><input name="startDate" type="text"
														readonly="readonly" maxlength="20"
														class="input-medium Wdate resetwh01   required"
														value="<fmt:formatDate value="${amsProjectInfo.startDate}" pattern="yyyy-MM-dd"/>"
														onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
														<span class="help-inline"><font color="red">*</font>
													</span></td>
													<td class="tit">竣工日期</td>
													<td><input name="finishDate" type="text"
														readonly="readonly" maxlength="20"
														class="input-medium  Wdate resetwh01 "
														value="<fmt:formatDate value="${amsProjectInfo.finishDate}" pattern="yyyy-MM-dd"/>"
														onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
													</td>
												</tr>

												<%-- <tr>
													
													<td  style="display: none;">著录扩<br />展数据
													</td>
													<td  style="display: none;"><form:input path="descriptionJson"
															htmlEscape="false" class="input-xlarge resetwh01" /></td>
												</tr> --%>
												<tr>
													<td class="tit">备注信息</td>
													<td colspan="3"><form:textarea path="remarks"
															htmlEscape="false" rows="4" maxlength="255"
															class="input-xxlarge " /></td>
												</tr>
											</table>

										</div>
									</div>
									<div class="tab-pane" id="addressa">
										<div class="row">
											<div class="col-sm-10 col-sm-offset-1">
												<div class="form-group">
													<label>登录名 <small></small></label> <input name="loginName"
														id="loginName" type="text" class="form-control resetwh02  required"
														placeholder="">
												</div>
												<div class="form-group">
													<label>密码 <small></small></label> <input id="newPassword"
														name="user.newPassword" type="password" maxlength="50"
														minlength="3" class="form-control  resetwh02 required"
														placeholder="">
												</div>
												<div class="form-group">
													<label>确认密码<small></small></label> <input
														id="confirmNewPassword" name="user.confirmNewPassword"
														equalTo="#newPassword" type="password" maxlength="50"
														minlength="3" class="form-control  resetwh02 required"
														placeholder="">
												</div>
											</div>
										</div>
									</div>
									<div class="tab-pane" id="addressa2">
										<div class="row">
											<div class="col-sm-10 col-sm-offset-1">
												<div class="form-group " style="margin: 0 0 0 0; width: 100%; text-align: center;">
													<label><b>《条款协议》</b> </label>
												</div>
											</div>
											<div class="col-sm-10 col-sm-offset-1">
												<div id="clause" class="form-group"
													style="position: absolute; height: 240px; overflow: auto; width: 96%">
													根据《中华人民共和国档案法》、《中华人民共和国城乡规划法》、《建设工程质量管理条例》、《科学技术档案工作条例》、《城市建设档案管理规定》、《城市地下管线工程档案管理办法》等法律、法规的规定，结合实际情况，<br>
													为确保建设单位 ( 甲方 ) 在工程项目竣工验收合格后三个月内及时向乙方报送一套符合要求的建设工程档案，经甲乙双方协商一致，签订建设工程档案报送责任书<br>
													*甲方责任<br> 
													1.领取建设工程规划许可证或建设工程施工许可证前，向工程项目所在地城建档案管理机构登记，并签订责任书；<br> 
													2.配备经城建档案管理机构培训取得合格证的档案资料员，及时收集积累、整理工程各环节的文件资料，并在工程竣工前及时通知乙方进行工程档案预验收。<br> 
													3.在工程项目竣工验收合格后三个月内，向乙方报送一套完整真实的工程档案；地下管线工程应在竣工验收后15个工作日内，<br> 
													    向乙方报送一套完整真实的工程档案；如遇特殊情况，应向乙方提出延期报送申请，经乙方批准后在延期内报送；<br> 
													4.向城建档案管理机构报送的工程档案内容按湘建〔2015〕124号文件规定执行；报送的工程档案应是原件，内容应当真实、准确，文字整洁，图表清晰，签章手续完备，制作和书写材料利于长期保存，案卷归档整理符合《建设工程文件归档规范》GB/T50328-2014的规定。<br>
													*乙方责任<br>
													1.按国家有关规定，对该项建设工程文件材料的形成、积累、整理、归档及其档案报送、移交工作进行阶段性的或应甲方要求进行现 场业务指导；<br>
													2.向甲方提供建设工程档案的专业培训、技术咨询及其相关的服务性工作；<br>
													3.收到甲方档案预验收申请后5个工作日内，对该项工程的档案进行预验收；<br>
													4.工程档案预验收合格后2个工作日内，出具档案预验收意见书；<br>
													5.接收该项建设工程档案后，确保档案安全保管和向甲方提供利用。<br>
													*违约责任<br>
													根据《中华人民共和国城乡规划法》、《建设工程质量管理条例》等法律法规规定，甲方未按规定向乙方报送建设工程档案的，由乙方责令改正，并处1万元以上10万元以下的罚款；对单位直接负责的主管人员和其他直接责任人员处单位罚款数额5%以上10%以下的罚款。<br>

												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="wizard-footer height-wizard">
									<div class="pull-right">
										<input type='button'
											class='btn btn-next btn-fill btn-warning btn-wd btn-sm'
											name='next' value='下一步' /> 
										<input type='submit' id="submit"
											name="submit"
											class='btn btn-finish  btn-fill btn-warning btn-wd btn-sm'
											name='finish' value='同意协议并提交'  />

									</div>
									<div class="pull-left">
										<input type='button'
											class='btn btn-previous btn-fill btn-default btn-wd btn-sm'
											name='previous' value='上一步' />
									</div>
									<div class="clearfix"></div>
								</div>
							</form:form>
						</div>
					</div>
					<!-- wizard container -->
				</div>
			</div>
			<!-- end row -->
		</div>
		<!--  big container -->

		<div class="footer">
			<div class="container">
				<a style="color: #3399CC ;"
					href='javascript:downloadFile("${ctxFixed}/baojian.doc");'>下载《建设工程档案报送责任书》模版</a>
			</div>
		</div>

	</div>



</body>
</html>
