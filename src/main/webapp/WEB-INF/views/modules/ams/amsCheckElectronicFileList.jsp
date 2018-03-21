<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>电子文件检查</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#thisCheck').click(function(){
				var archivesId = $(this).attr('archives');
				 $.ajax({
					 type:"POST",                      
					 url:"${ctx}/ams/amsCheckElectronicFile/checking",          
					 data:{ id:archivesId },   
					 dataType:"json",                
					 success:function(data){    
						 if(data.obj){
							 var message = "检测报告内容:";
							 for(var i=0;i<data.obj.length;i++){
								 message += "<br>" + data.obj[i];
							 }
							 $('#messageInfo').html(message);
							 $('#messageBox').addClass('alert alert-error hide');
							 $('#messageBox').show();
						 }else{
							 $('#messageInfo').html("检测通过 !!");
							 $('#messageBox').addClass('alert alert-success hide');
							 $('#messageBox').show();
						 }
					 }
				 })
			});
			
		});
		
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/ams/amsCheckElectronicFile/list");
			$("#searchForm").submit();
	    	return false;
	    }
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/ams/amsCheckElectronicFile/list">电子文件列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="amsArchivesInfo" action="${ctx}/ams/amsCheckElectronicFile/list" method="post" class=" form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="officeid" name="officeId" type="hidden" value="${officeId}">
		<input id="officeGrade" name="officeGrade" type="hidden" value="${officeGrade}">
		<ul class="ul-form">
			<li><label>案卷题名：</label><form:input path="archivesName" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/></li>
		</ul>
	</form:form>
	<div id="messageBox" style="display: none;">
	<button onclick="$('#messageBox').hide()" class="close">×</button>
	<div id="messageInfo"></div>
	</div> 	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr>
		<th>档号</th>
		<th>案卷题名</th>
		<th>编制单位</th>
		<th>载体类型</th>
		<th>数量</th>
		<th>密级</th>
		<shiro:hasPermission name="ams:amsCheckElectronicFile:edit"><th>操作</th></shiro:hasPermission>
		</tr></thead>
		<tbody>
		 <c:forEach items="${page.list}" var="archives">
			<tr>
				<td>${archives.archivesCode}</td>
				<td>${archives.archivesName}</td>
				<td>${archives.makeUnit}</td>
				<td>${fns:getDictLabels(archives.carrierType, 'carrier_type', '无')}</td>
				<td>${archives.filesCount}</td>	
				<td>${fns:getDictLabels(archives.degreeSecrets, 'degree_secrets', '无')}</td>
				<shiro:hasPermission name="ams:amsCheckElectronicFile:edit"><td>
    				<c:choose>
    					<c:when test="${archives.exten2 == 0 }">
    					<i id="iconIcon" class="icon-remove" style="color: red"></i>
    					<a href="#" id="thisCheck" archives="${archives.id}" style="color: red">已检查</a>
    					</c:when>
    					<c:when test="${archives.exten2 == 1 }">
    					<i id="iconIcon" class="icon-ok" style="color: green"></i>
    					<a href="#" id="thisCheck" archives="${archives.id}" style="color: green">已检查 </a>
    					</c:when>
    					<c:otherwise>
    					<a href="#" id="thisCheck" archives="${archives.id}">开始检查 </a>
    					</c:otherwise>
    				</c:choose>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach> 
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>