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
<title>填写单位信息</title>
<meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport' />
<meta name="viewport" content="width=device-width" />
<link href="${ctxStatic}/bootstrap/2.3.1/css_${not empty cookie.theme.value ? cookie.theme.value : 'cerulean'}/bootstrap.min.css" type="text/css" rel="stylesheet" />
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
.tab-content{
	overflow:hidden !important;
}
.jbox-content{
	overflow: !important;
}
</style>

<script type="text/javascript">
	$(document).ready( function() {
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
				},unitCreditCode : {
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
			});
		});
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
							<form:form id="inputForm" modelAttribute="amsUnitDetailinfo"
								action="${ctx}/ams/amsUnitDetailinfo/supplement" method="post" class="form-horizontal" >
								<div class="wizard-header">
									<h3>		
										<a href="${ctx}/logout"  style="position: absolute; left: 4%;    top: 20%;">
											<img src="${ctxStatic}/assets/img/back.png" title="返回主页"
												 style="width: 40px ;">
										</a>
										<small style="padding-left: ">请将下列信息填写完整并提交</small>
									</h3>
								</div>
								<br>
								<div class="tab-content">
									<div class="" >
										<div class="row" style="padding-left: 25px;">
											<table class="table-form" style="width: 98%">
												<tr>
													<td class="tit">统一社会信用代码</td>
													<td colspan="4">
													<input id="unitCreditCode" name="unitCreditCode" class="form-control resetwh01 society required" maxlength="18" >
													<span class="help-inline"><font color="red">*</font></span>
													</td>
												</tr>
												<tr>
													<td class="tit">单位地址</td>
													<td colspan="4"><input name="address" class="form-control  resetwh01 required" maxlength="50">
													<span class="help-inline"><font color="red">*</font></span>
													</td>
												</tr>
												<tr>
													<td class="tit">单位资质等级</td>
													<td colspan="4"><input name="qualifications" class="form-control resetwh01 required" maxlength="64">
													<span class="help-inline"><font color="red">*</font></span>
													</td>
												</tr>
												<tr>
													<td class="tit">单位资质等级(其他)</td>
													<td colspan="4"><input name="qualificationsOther" class="form-control resetwh01 required" maxlength="20">
													<span class="help-inline"><font color="red">*</font></span>
													</td>
												</tr>
												<tr>
													<td class="tit">单位资质证书号</td>
													<td colspan="4"><input name="qualificationsNumber" class="form-control resetwh01 abc required" maxlength="30">
													<span class="help-inline"><font color="red">*</font></span>
													</td>
												</tr>
												<tr>
													<td class="tit">项目负责人</td>
													<td colspan="4"><input name="responsiblePerson" class="form-control resetwh01 required" maxlength="20">
													<span class="help-inline"><font color="red">*</font></span>
													</td>
													
												</tr>
												<tr>
													<td class="tit">项目负责人身份证号</td>
													<td colspan="4"><input name="responsiblePersonId" class="form-control resetwh01 idcard required" maxlength="18" >
													<span class="help-inline"><font color="red">*</font></span>
													</td>
												</tr>
												<tr>
													<td class="tit">法人</td>
													<td colspan="4"><input name="legalPerson" class="form-control resetwh01 required" maxlength="20">
													<span class="help-inline"><font color="red">*</font></span>
													</td>
												</tr>
												
											</table>

										</div>
									</div>
								</div>
								<div class="wizard-footer height-wizard">
									<div class="pull-right">
									 <input type='submit' id="submit"
											name="submit"
											class='btn  btn-fill btn-warning btn-wd btn-sm'
											name='finish' value='确定并提交' />
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

	</div>
</body>
</html>
