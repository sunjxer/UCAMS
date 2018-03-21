<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>离线文件上传</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#value").focus();
			var file;
			var length=1024*256*4*5;
			var proving=true;
			var uploadFileSplice=function(index, total,filesize,filename) {
				var start=index*length;
				var end=eval(start+length);
				var formData = new FormData();
				formData.append("filename", file.name);
				formData.append("index", index);
				formData.append("total", total);
				formData.append("file", file.slice(start, end));
				$.ajax({
					  url: "${ctx}/ams/amsInterface/uploadOfflineFileSplice",
					  type: "POST",
					  data: formData,
					  //dataType: 'JSON',
			          cache: false,
					  processData: false,  // 告诉jQuery不要去处理发送的数据
					  contentType: false,   // 告诉jQuery不要去设置Content-Type请求头
					  success: function(data){
						  $("#schedule").html((end/filesize*100).toFixed(1)+"%");
						  if(data=="success")
							if(proving){
							  if(end<filesize){
								  uploadFileSplice(eval(index+1), total,filesize,filename);//上传成功，继续下一片
							  }else{
								  $("#schedule").html("100%,正在处理请稍候！");
								  //上传结束，拼接碎片
								  $.post("${ctx}/ams/amsInterface/splitjointOfflineFileSplice", { "filename": filename,"total":total },function(splitdata){
									  if(splitdata.result=="success"){
										  $("#schedule").html("上传成功。");
										  top.$.jBox.confirm("文件上传成功,是否开始检查？", "提示", function (v, h, f) {
											    if (v == true){
											    	$("#schedule").html("开始检查，请稍后。");
											    	//离线包文件检查
											    	$.post("${ctx}/ams/amsInterface/provingOfflineFileData", {"userName":$("#loginName").val(),"password":$("#loginPassword").val()},function(provingdata){
											    		if(provingdata.result=="success"){
											    			
											    			window.location.href="${ctx}/ams/amsInterface/showOfflineFileDetailed";
											    		}else if(provingdata.result=="error"){
															$("#schedule").html(provingdata.data);
															$("#btnSubmit").removeAttr("disabled");
														}else if(provingdata.result=="failure"){
															$("#schedule").html("解析失败。");
															//启用上传按钮
															$("#btnSubmit").removeAttr("disabled");
														}
													},"json");
											    }
											    return true;
											}, { buttons: { '是': true, '否': false} });
									  }else if(splitdata.result=="losefile"){
										$("#schedule").html("文件缺失，请重新上传。");
										//启用上传按钮
										$("#btnSubmit").removeAttr("disabled");
									  }
								},"json");
							  }
							}else{
								$.post("${ctx}/ams/amsInterface/cancelOfflineFileSplice", null,function(data){
									proving=true;
									if(data=="success"){
										$("#schedule").html("撤销成功。");
										//启用上传按钮
										$("#btnSubmit").removeAttr("disabled");
									}else if(data=="finish") $("#schedule").html("撤销失败，文件上传完成。");
									else if(data=="failure") $("#schedule").html("撤销失败。");
								},"text");
							}
						  else top.$.jBox.confirm("上传错误，是否重试", "提示", function (v, h, f) {
							    if (v == true)  
							    	 uploadFileSplice(index, total,filesize,filename);//上传错误，重传碎片
							    else  
							    	$.post("${ctx}/ams/amsInterface/cancelOfflineFileSplice", null,function(data){
										  if(data=="success"){
											top.$.jBox.tip("撤销成功。");
											//启用上传按钮
											$("#btnSubmit").removeAttr("disabled");
										  }
									},"text");
							  
							    return true;  
							}, { buttons: { '重试': true, '撤销': false} });
					  },
						error : function() {
							top.$.jBox.tip("系统错误！");
							//启用上传按钮
							$("#btnSubmit").removeAttr("disabled");
						}
				});
			}
			$("#btnSubmit").click(function() {
				file = document.getElementById('uploadfile').files[0];
				if(file===undefined){
					top.$.jBox.tip("请选择上传文件！");
					return false;
				}
				var filename= file.name;
				if(filename.substring(filename.lastIndexOf("."))!=".sip"){
					top.$.jBox.tip("请上传sip文件！");
					return false;
				}
				var index=0;
				var filesize=file.size;
				var total=Math.ceil(file.size/length);
				uploadFileSplice(index, total,filesize,filename);
				//禁用上传按钮
				$("#btnSubmit").attr({"disabled":"disabled"});
			});
			$("#rescindButton").click(function() {
				top.$.jBox.confirm("确定撤销当前上传文件", "提示", function (v, h, f) {
				    if (v == true){
				    	proving=false;
				    }else{
				    	proving=true;
				    }
				    return true;
				}, { buttons: { '撤销': true, '取消': false} });
			});
			$("#loginName").add("#loginPassword").blur(function(){
				  var loginName =$("#loginName").val();
				  var loginPassword=$("#loginPassword").val();
				  if($.trim(loginName)!=''&&$.trim(loginPassword)!=''){
					  $.post("${ctx}/ams/amsInterface/checkUserNameAndPassword", {"userName":loginName,"password":loginPassword},function(data){
						  if(data.result=="success"){
							$("#officeName").val(data.office.name);
							//$("#unitCreditCode").val(data.office.code);
							$("#schedule").html("");
							$("#uploadfile").removeAttr("disabled");
							$("#btnSubmit").removeAttr("disabled");
						  }else{
							  $("#roleId").val("");
							  $("#uploadfile").attr({"disabled":"disabled"});
							  $("#btnSubmit").attr({"disabled":"disabled"});
							  if(data.result=="incorrect") $("#schedule").html("用户名密码错误！");
						  }
						  
					},"json");
				  }
			});
			
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/ams/amsInterface">离线文件上传</a></li>
	</ul><br/>
	<sys:message content="${message}"/>
	<form:form id="inputForm" action="${ctx}/ams/amsInterface/uploadOfflineFile" method="post" class="form-horizontal" enctype="multipart/form-data">
		<input type="hidden" id="projectId">
		<table class="table-form">
			<tr>
				<td class="titRight">用户名：</td>
				<td>
				<input type="text" id="loginName" name="loginName" class="input-xlarge required" >
				</td>
				<td class="titRight">密码：</td>
				<td>
				<input type="text" id="loginPassword" name="loginPassword" class="input-xlarge required" >
				</td>
			</tr>
			<tr>
				<td class="titRight">单位名称：</td>
				<td>
				<input type="text" id="officeName" class="input-xlarge required" disabled="disabled">
				</td>
				<td class="titRight"><!-- 社会信用代码：--></td>
				<td>
				<!-- <input type="text" id="unitCreditCode" class="input-xlarge required" disabled="disabled">-->
				</td> 
			</tr>
			<tr>
				<td class="titRight">选择离线文件包：</td>
				<td>
				<input type="file" name="file" id="uploadfile" disabled="disabled"/>
				</td>
				<td class="titRight">上传进度：</td>
				<td>
				<span class="help-inline" id="schedule"></span>
				</td>
			</tr>
		</table>
		<div class="form-actions">
			<shiro:hasPermission name="ams:amsOfflineFile:upload">
			<input id="btnSubmit" class="btn btn-primary" type="button" disabled="disabled" value="上 传"/>&nbsp;
			<input id="rescindButton" class="btn btn-primary" type="button" value="撤 销"/>&nbsp;
			</shiro:hasPermission>
		</div>
	</form:form>
</body>
</html>