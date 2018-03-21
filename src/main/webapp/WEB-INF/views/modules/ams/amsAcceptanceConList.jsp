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
				<th>工程名称</th>
				<th>工程地点</th>
				<th>建设单位</th>				
				<th>立项批准单位</th>
				<th>设计单位</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="amsConstructDes"  varStatus="status">
			<tr>
				<td>
				<input type="hidden" id="id${status.index}" value="${amsConstructDes.id}"/>
					${amsConstructDes.projectName}
				</td>
				<td>${amsConstructDes.address}</td>
				<td>${amsConstructDes.constructionUnit}</td>				
				<td>${amsConstructDes.projectApprovalUnit}</td>
				<td>${amsConstructDes.designUnit}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	</div>
</body>
</html>