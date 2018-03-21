<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>文件查看</title>
<meta name="decorator" content="default" />
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/ams/amsFileInfo/formViewVideo?id=${amsFileInfo.id}">文件查看</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="amsFileInfo" action="${ctx}/ams/amsFileInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="unitProjectId"/>
		<form:hidden path="recordId"/>
		<form:hidden path="structureJson"/>
		<form:hidden path="filePath"/>
		<form:hidden path="filesource"/>
		<form:hidden path="dongle"/>
		<form:hidden path="state"/>
		<sys:message content="${message}"/>	
		<table class="table-form">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
			<tr>
				<td class="tit" colspan="4">基本信息</td>
			</tr>
			<tr>
				<td class="tit">文件名：</td>
				<td>${amsFileInfo.fileName}</td>
				<td class="tit">文图号：</td>
				<td>
				${amsFileInfo.fileNo}
				</td>
			</tr>
			<tr>
				<td class="tit">责任者：</td>
				<td>
				${amsFileInfo.author}
				</td>
				<td class="tit">形成日期：</td>
				<td><fmt:formatDate value="${amsFileInfo.formDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
			<tr>
				<td class="tit">文件份数：</td>
				<td>
				${amsFileInfo.filecount}
				</td>
				<td class="tit">文件类型：</td>
				<td>
				${fns:getDictLabel(amsFileInfo.fileType, 'video_file_type', '')}
				</td>
			</tr>
			<tr>
			<td class="tit">备注信息：</td>
			<td colspan="3">
			${amsFileInfo.remarks}
		</td>
		</tr>
		<c:forEach items="${amsFileInfo.amsDesExtendList}" var="amsDesExtend" varStatus="status">
		<c:if test="${status.index%2==0}">
		<tr>
		</c:if>
			<td class="tit">${amsDesExtend.comments}：</td>
			<td>
				<input type="hidden" id="amsDesExtendList[${status.index}]_id" name="amsDesExtendList[${status.index}].id" value="${amsDesExtend.id}"/>
				<input type="hidden" id="amsDesExtendList[${status.index}]_name" name="amsDesExtendList[${status.index}].name" value="${amsDesExtend.name}"/>
				<c:if test="${amsDesExtend.showType==\"text\"}">
				${amsDesExtend.settings}
				</c:if>
				<c:if test="${amsDesExtend.showType==\"textarea\"}">
				${amsDesExtend.settings}
				</c:if>
				<c:if test="${amsDesExtend.showType==\"select\"}">
				${fns:getDictLabel(amsDesExtend.settings, amsDesExtend.dictType, '')}
				</c:if>
				<c:if test="${amsDesExtend.showType==\"date\"}">
				${amsDesExtend.settings}
				</c:if>
			</td>
		<c:if test="${status.index%2==1}">
		<tr>
		</c:if>
		</c:forEach> 
		</table>
		
	</form:form>
	<div class="form-actions" id="readPdf" align="center">
	<c:if test="${amsFileInfo.fileType==5}">
	<img alt="" src="${amsFileInfo.filePath}">
	</c:if>
	<!-- iframe的src设置预览文件地址?file=地址 -->
	<c:if test="${amsFileInfo.fileType==4}">
	<video  height="240" controls="controls">
 	 <source src="${amsFileInfo.filePath}" type="video/mp4">
 		 Your browser does not support the video tag.
     </video>
	</c:if>
	<c:if test="${amsFileInfo.fileType==3}">
 	<audio controls="controls">
		<source src="${amsFileInfo.filePath}" type="audio/mpeg">
		Your browser does not support the audio element.
	 </audio>
	 </c:if>
	</div>
</body>
</body>
</html>