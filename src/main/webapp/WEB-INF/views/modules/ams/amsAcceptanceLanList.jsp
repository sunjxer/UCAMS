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
				<th>用地项目名称</th>
				<th>征地位置</th>
				<th>用地单位</th>				
				<th>被征单位</th>
				<th>地形图号</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="amsLandDes"  varStatus="status">
			<tr>
				<td>
				<input type="hidden" id="id${status.index}" value="${amsLandDes.id}"/>
				
					${amsLandDes.projectName}
				</td>
				<td>${amsLandDes.address}</td>
				<td>${amsLandDes.landUseUnit}</td>				
				<td>${amsLandDes.expropriatedUnit}</td>
				<td>${amsLandDes.topographicMap}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	</div>
</body>
</html>