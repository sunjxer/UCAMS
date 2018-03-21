<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>文件管理</title>
<meta name="decorator" content="default" />
<%@include file="/WEB-INF/views/include/treeview.jsp"%>
<script type="text/javascript">
$(document).ready(function() {
	
});
function page(n,s){
	$("#pageNo").val(n);
	$("#pageSize").val(s);
	$("#searchForm").submit();
	return false;
}
function reportDo(ids){
	var id=$("#id"+ids).val();
	parent.openArcview(id);
}
function unitInfo(){
	var id=$("#unitId").val();
	parent.openUnitProview(id);
}
</script>

</head>
<body>
	<div class="accordion-inner">
	<sys:message content="${message}" />
	<input type="hidden" id="unitId" value="${unitProjectId}">
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>案卷题名</th>
				<th>编制单位</th>
				<th>载体类型</th>				
				<th>立卷人</th>
				<th>立卷日期</th>
				<th>详细信息</th>
				<c:if test="${!unitProjectId.equals('-1')}">
				<th>单位工程信息 </th>
				</c:if>
				<!-- <th>顺序号</th> -->
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="amsArchivesInfo"  varStatus="status">
			<tr>
				<td>
				<input type="hidden" id="id${status.index}" value="${amsArchivesInfo.id}"/>
				<a href="${ctx}/ams/fileThumbnai/toPreviewFile?groupId=${amsArchivesInfo.id}" target="_Blank">
					${amsArchivesInfo.archivesName}
				</a>
				</td>
				<td>${amsArchivesInfo.makeUnit}</td>
				<td>${fns:getDictLabel(amsArchivesInfo.carrierType, 'carrier_type', '')}</td>				
				<td>${amsArchivesInfo.author}</td>
				<td><fmt:formatDate value="${amsArchivesInfo.makeDate}" pattern="yyyy-MM-dd"/></td>
				<td><a  href='javascript:reportDo(${status.index})' >详细信息</a></td>
				<c:if test="${!unitProjectId.equals('-1')}">
				<td><a  href='javascript:unitInfo()' >单位工程信息</a></td>
				</c:if>
				<%-- <td>${fns:getDictLabel(amsArchivesInfo.fileType, 'file_type', '')}</td>
				<td>${amsArchivesInfo.remarks}</td> --%> 
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	</div>
</body>
</html>