<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>业务指导统计</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExplod").click(function(){
				$("#searchForm").attr("action","${ctx}/ams/guidanceStatistics/expload");
				$("#searchForm").submit();
			})
		});
		/* function page(n,s){
        	//location = '${ctx}/act/task/historic/?pageNo='+n+'&pageSize='+s;
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
			return false;
        } */
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/ams/guidanceStatistics/">业务指导统计</a></li>
		<li ><a href="${ctx}/ams/guidanceStatistics/samePeriod">业务指导同期比统计</a></li>
		<%-- <li><a href="${ctx}/act/task/process/">新建任务</a></li> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="amsGuidance" action="${ctx}/ams/guidanceStatistics/" method="post" class="breadcrumb form-search">		
		<div>
			
			<label>业务指导时间：</label>
			<input id="beginDate" name="act.beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
								value="<fmt:formatDate value="${amsGuidance.act.beginDate}" pattern="yyyy-MM-dd"/>"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				　--　
			 <input id="endDate" name="act.endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" 
				value="<fmt:formatDate value="${amsGuidance.act.endDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			&nbsp;
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
			&nbsp;
			<input id="btnExplod" class="btn btn-primary" type="button" value="导出"/>
		</div>
		
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>姓名</th>
				<th>部门</th>
				<th>业务指导次数</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}" var="amsGuidanceExportDTO" varStatus="status">
				<%--<c:set var="task" value="${act.histTask}" />
				<c:set var="vars" value="${act.vars}" />
				<c:set var="procDef" value="${act.procDef}" />
				<c:set var="procExecUrl" value="${act.procExecUrl}" /> 
				<c:set var="status" value="${act.status}" />--%>
				<tr>
					<td>${status.index +1}</td>
					<td>
						${amsGuidanceExportDTO.userName }
					</td>
					<td>
						${amsGuidanceExportDTO.officeName}
					</td>
					<td>${amsGuidanceExportDTO.count}</td>
					
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
</body>
</html>
