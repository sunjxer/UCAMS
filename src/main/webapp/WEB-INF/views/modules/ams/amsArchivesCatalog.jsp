<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>文件管理</title>
<meta name="decorator" content="default" />
<%@include file="/WEB-INF/views/include/treeview.jsp"%>
<style type="text/css">
		.ztree {overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;}
	</style>
<script type="text/javascript">
	
$(document).ready(function() {
	$("#expload").click(function(){
		var archivesId = $("#archivesId").val();
		var catalogType = $("#catalogType").val();
		location.href = "${ctx}/ams/amsArchivesInfo/exploadCatalog?id="+archivesId+"&catalogType="+catalogType;
	})	
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
	<div class="accordion" id="accordion2">
		<%-- <c:if test="${empty amsProjectInfo.id}">
		<label class="control-label"> ${message}</label>
		<script type="text/javascript">top.$.jBox.tip("项目不存在！");
		</script>
		</c:if>
		<c:if test="${not empty amsProjectInfo.id}"> --%>
		
		<sys:message content="${message}" />
		<br/>
		<div class="accordion-group">
			<form:form id="searchForm"   method="post" class="breadcrumb ">
				<input type="hidden" id="archivesId" value="${archivesId}"/>
				<input type="hidden" id="catalogType" value="${catalogType}"/>
				<input id="expload" class="btn btn-primary" type="button" value="导出"/>
				<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
			</form:form>
			<div id="collapseOne" class="accordion-body collapse in ">
				<div class="accordion-inner">
				
					<table id="contentTable"
						class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th>序号</th>
								<th>文件编号</th>
								<th>责任者</th>
								<th>文件提名</th>
								<th>日期</th>								
								<th>页次</th>
								<th>备注</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list}" var="amsFileInfo" varStatus="status">
								<tr>
									<td id="tdId" style="display: none">${amsFileInfo.amsArchivesFiles.id}</td>
									<td>${status.index +1}</td>
									<td>
									<c:if test="${catalogType==1 }">
										${amsFileInfo.amsGenre.code }
									</c:if>
									<c:if test="${catalogType==0||catalogType==''||catalogType==null }">
										${amsFileInfo.fileNo }
									</c:if>
									</td>
									<td>${amsFileInfo.author }</td>
									<td>
									<c:if test="${catalogType==1 }">
										${amsFileInfo.amsGenre.name }
									</c:if>
									<c:if test="${catalogType==0||catalogType==''||catalogType==null }">
										${amsFileInfo.fileName}
									</c:if>
									</td>
									<td>
									<fmt:formatDate value="${amsFileInfo.formDate}" pattern="yyyy-MM-dd"/>
									</td>
									<td>${amsFileInfo.amsArchivesFiles.startPage }-${amsFileInfo.amsArchivesFiles.endPage }</td>
									<td>${amsFileInfo.remarks }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	
	
</body>
</html>