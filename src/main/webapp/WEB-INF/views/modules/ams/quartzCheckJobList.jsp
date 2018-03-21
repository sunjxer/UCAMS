<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>定时任务管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	/* 临时接入 */
	var url = ${fns:toJson(fns:getConfig('quartz.checkjob'))};
	$(document).ready(function() {
		 $.ajax({
			 type:"GET",    
			 dataType : "jsonp", 
			 //jsonp:"callback",
			 //jsonpCallback:"success_jsonpCallback",
			 url:url+"/outerJob/queryjob",    
			 data:{
				 pageNum:1,
				 pageSize:10
			 },
			 success:function(data){    
				 if(data){
					var jobList = data.JobAndTrigger;
					var str = "";
					for(var i=0;i<jobList.length;i++){
						 str += "<tr>";
						 str += "<td>" + jobList[i].jOB_NAME +"</td>";
						 str += "<td>" + jobList[i].jOB_GROUP +"</td>";
						 str += "<td>" + jobList[i].jOB_CLASS_NAME +"</td>";
						 str += "<td>" + jobList[i].tRIGGER_NAME +"</td>";
						 str += "<td>" + jobList[i].tRIGGER_GROUP +"</td>";
						 str += "<td>" + jobList[i].cRON_EXPRESSION +"</td>";
						 str += "<td>" + jobList[i].tIME_ZONE_ID +"</td>";
						 str += "<td>";
						 str += "<a href='#' onclick='runJob(this)' className='"+jobList[i].jOB_CLASS_NAME+"' groupName='"+jobList[i].jOB_GROUP+"'>启动</a>&nbsp;&nbsp;";
						 str += "<a href='#' onclick='pausejob(this)' className='"+jobList[i].jOB_CLASS_NAME+"' groupName='"+jobList[i].jOB_GROUP+"'>暂停</a>&nbsp;&nbsp;";
						 str += "<a href='#' onclick='resumejob(this)' className='"+jobList[i].jOB_CLASS_NAME+"' groupName='"+jobList[i].jOB_GROUP+"'>恢复</a>&nbsp;&nbsp;";
						/*  str += "<a href='#'>修改</a>&nbsp;&nbsp;";
						 str += "<a href='#' class='runJob' className='"+jobList[i].jOB_CLASS_NAME+"' groupName='"+jobList[i].jOB_GROUP+"'>删除</a>&nbsp;&nbsp;";
						 */ 
						 str += "</td>";
						 str += "</tr>";
					}
					$('#jobListBody').html(str);
				 }
			 }
		 })
	});
	
	function runJob(data){
		var className = $(data).attr('className');
		var groupName = $(data).attr('groupName');
		 $.ajax({
			 type:"GET",    
			 dataType : "jsonp", 
			 jsonp:"callback",
			 url:url+"/outerJob/runjob",    
			 data:{
				 jobClassName:className,
				 jobGroupName:groupName
			 },
			 success:function(data){
				 console.log('runJob');
			 }
		 })
	}
	function pausejob(data){
		var className = $(data).attr('className');
		var groupName = $(data).attr('groupName');
		 $.ajax({
			 type:"GET",    
			 dataType : "jsonp", 
			 jsonp:"callback",
			 url:url+"/outerJob/pausejob",    
			 data:{
				 jobClassName:className,
				 jobGroupName:groupName
			 },
			 success:function(data){
				 console.log('pausejob');
			 }
		 })
	}
	function resumejob(data){
		var className = $(data).attr('className');
		var groupName = $(data).attr('groupName');
		 $.ajax({
			 type:"GET",    
			 dataType : "jsonp", 
			 jsonp:"callback",
			 url:url+"/outerJob/resumejob",    
			 data:{
				 jobClassName:className,
				 jobGroupName:groupName
			 },
			 success:function(data){
				 console.log('resumejob');
			 }
		 })
	}
	
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="">检查报告列表</a></li>
	</ul>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>任务名称</th>
				<th>任务所在组</th>
				<th>任务类名</th>
				<th>触发器名称</th>
				<th>触发器所在组</th>
				<th>表达式</th>
				<th>时区</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody id="jobListBody"></tbody>
	</table>
	
</body>
</html>