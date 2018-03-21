<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>机构管理</title>
<meta name="decorator" content="default" />

<script src="${ctxStatic}/uploadify/jquery.uploadify.js" type="text/javascript"></script>
<script src="${ctxStatic}/common/uploadifytool.js" type="text/javascript"></script>
<link href="${ctxStatic}/uploadify/uploadify.css" rel="stylesheet" />
<script type="text/javascript">
	//轻量级文件上传 不支持断点续传
	$(function() {
		$("#uploadify").upload({
			actionUrl : "${ctx}/test/upload/uploadFile",
			auto : false,
			multi : false,
			fileTypeDesc : "任意格式",
			fileTypeExts : "*.*", //任意格式
			entityName : "ucams"
		});
		
		$("#checkUpload").click(function(){
			var formData = new FormData($( "#checkPdfFrom" )[0]);  
			$.ajax({
				url : '${ctx}/test/restful/checkFileOfSing',
				data: formData,
				type : "POST",
				async: false,  
		        cache: false,  
		        contentType: false,  
		        processData: false,
				success : function(data) {
					alert(data);
				},
				error : function(data){
					alert(data);
				}
			});
		})
	});
	
	
</script>

<script>  
	/* var socket = io.connect('http://192.168.0.212:3000');
	socket.on('conn', function (data) {
	    console.log(data);
	    socket.emit('other event', { my: 'data' });
	}); */
</script>  
</head>
<body>
	<div class="control-group">
	<label class="control-label">uploadify模式上传: </label>
		<div class="controls">
			<input type="file" name="uploadify" id="uploadify" />
		</div>
	</div>
	<!-- iframe的src设置预览文件地址?file=地址 -->
	<!-- 	<iframe id="readAme"  src="/static/read-pdf/web/viewer.html?file=/fixed/test01.pdf" width="90%" height="800"></iframe> 	</div>
	 -->	
 	
 	<div class="control-group">
 		<label class="control-label">PDF缩略图</label>
		<div class="controls">	
		 	<a href="${ctx}/test/restful/toPreviewFile"  target="_Blank">查看案卷内的文件</a>
 		</div>
	</div>
 	<div class="control-group">
 		<label class="control-label">检查PDF文件签章</label>
		<div class="controls">	
		 	<form  id="checkPdfFrom" >
		 		<input type="file" name="file"/>
		 		<button type="button" class="btn" id="checkUpload">上传</button>
		 	</form>
 		</div>
	</div>
</body>
</html>