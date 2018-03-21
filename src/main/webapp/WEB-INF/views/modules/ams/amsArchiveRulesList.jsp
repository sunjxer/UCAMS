<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>文件管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			// 复制数据
			$("#copyBtn").click(function() {
				var selectedData = [];
				$(":checkbox:checked").each(function(){
				  var data_tr = $(this).parent().parent();  //获取当前行父节点
				  var code = $(data_tr).children("#tdId").text();//获取父节点下td孩子节点
				  selectedData.push(code); 						//组织string数组
				});
				if(! selectedData.length >0 ){
					top.$.jBox.tip("没有数据被选中 !");
					return;
				}
				$.ajax({
					url : '${ctx}/ams/amsArchiveRules/copyData', 
					async:false,
					data : {
						'selectedData' : selectedData.toString()
					},
					success : function(data) {
						if(data.success){
							 //跳转到当前页面
							 window.location.href="${ctx}/ams/amsArchiveRules/list?amsGenre.id=" + $("#amsGenreId").val();
						}else{
							top.$.jBox.tip("数据复制失败！");
						}
					},
					error : function() {
						 top.$.jBox.tip("系统错误！");
					}
				});
			});
			//复制按钮样式控制
			$(":checkbox").click(function(){    
					//判断是否还有被选择的checkbox
				    if($(':checkbox').is(':checked')) {
				    	$("#copyBtn").attr("class", "btn btn-primary");   
				    	$("#copyBtn").removeAttr("disabled");  //按钮启用
				    }else{
				    	$("#copyBtn").attr("class", "btn btn-primary disabled");   
				    	$("#copyBtn").attr("disabled","true");  //按钮禁用
				    }
				
			});
			//全选控制
			$("#checkedAll").click(function(){   
			    if(this.checked){   
			    	$(":checkbox").attr("checked", true);  
			    }else{   
			    	$(":checkbox").attr("checked", false);
			    	$("#copyBtn").attr("class", "btn btn-primary disabled");
			    	$("#copyBtn").attr("disabled","true");  //按钮禁用
			    }   
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function changeSort(t,oper){
	        var data_tr=$(t).parent().parent(); //获取到触发的tr
	            if(oper=="MoveUp"){    //向上移动
	               if($(data_tr).prev().html()==null){ //获取tr的前一个相同等级的元素是否为空
	                   top.$.jBox.tip("已经是最顶部了!");
	                   return;
	               }{
	            		var prevId = $(data_tr).prev().children("#tdId").text(); //上一行数据ID
	            		var thisId = $(data_tr).children("#tdId").text() //当前行数据ID
	            		$.ajax({
	            	        url:'${ctx}/ams/amsArchiveRules/sortCheck',  //异步修改数据
	            	        data:{'thisId':thisId,'prevId':prevId},
	            	        async:false,
	            	        success:function(data){
	            	        	 if(data ="success"){
	            	        		 $(data_tr).insertBefore($(data_tr).prev()); //将本身插入到目标tr的前面 
	            	        	 }else{
	            	        		 top.$.jBox.tip("移动失败");
	            	        	 }
	            	        },
	            	        error:function(){
	            	        	top.$.jBox.tip("系统错误！");
	            	        }
	            	    });
	               }
	               }else{
	                     if($(data_tr).next().html()==null){
	                     top.$.jBox.tip("已经是最低部了!");
	                     return;
	                 }{
	                	 var nextId = $(data_tr).next().children("#tdId").text(); //上一行数据ID
		            	var thisId = $(data_tr).children("#tdId").text() //当前行数据ID
	                	 $.ajax({
		            	        url:'${ctx}/ams/amsArchiveRules/sortCheck',  //异步修改数据
		            	        data:{'thisId':thisId,'nextId':nextId},
		            	        async:false,
		            	        success:function(data){
		            	        	  if(data ="success"){
			            	        		 $(data_tr).insertAfter($(data_tr).next()); //将本身插入到目标tr的后面 
			            	        	 }else{
			            	        		 top.$.jBox.tip("移动失败");
			            	        	 }
		            	        },
		            	        error:function(){
		            	        	top.$.jBox.tip("系统错误！");
		            	        }
		            	    });
	                 }
	               }
	    }
		
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/ams/amsArchiveRules/list">归档文件列表</a></li>
		<shiro:hasPermission name="ams:amsArchiveRules:edit"><li><a href="${ctx}/ams/amsArchiveRules/form">归档文件添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="amsArchiveRules" action="${ctx}/ams/amsArchiveRules/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="amsGenreId"  name="amsGenre.id"  type="hidden" value="${amsGenreId}">
		<ul class="ul-form">
			<li><label>归档文件名：</label>
				<form:input path="fileName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="copyBtn" class="btn btn-primary disabled" type="button" value="复制" disabled="disabled"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="width: 1%"><input id="checkedAll" type="checkbox"/></th>
				<th style="width: 1%">序号</th>
				<th>单位工程类型</th>
				<th>归档文件名</th>
				<th>生成单位</th>
				<th>备注</th>
				<th style="width: 8%">排序操作</th>
				<shiro:hasPermission name="ams:amsArchiveRules:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		 <c:forEach items="${page.list}" var="amsArchiveRules" varStatus="status">
			<tr>
				<%-- <td>${status.index +1}</td> --%>
				<td><input type="checkbox"/></td>
				<td id="tdId" style="display: none">${amsArchiveRules.id}</td>
				<td id="tdSort">${status.index +1} </td>
				<td id="tdUserDictName">${amsArchiveRules.amsUserDict.name}</td>
				<td id="tdFileName">${amsArchiveRules.fileName}</td>
				<td id="tdCreateUnit">
					<c:choose>
					    <c:when test="${amsArchiveRules.createUnit == 'security-role'}">建设单位</c:when>
					    <c:when test="${amsArchiveRules.createUnit == 'assignment'}">监理单位</c:when>
					    <c:when test="${amsArchiveRules.createUnit == 'user'}">施工单位</c:when>
					    <c:otherwise>无</c:otherwise>
					</c:choose>
				</td>
				<td id="tdRemarks">${amsArchiveRules.remarks}</td>
				<td>
					<a href="javascript:void(0)"  id = "sortUp" onclick="changeSort(this,'MoveUp')">上移</a>
					<a href="javascript:void(0)"  id = "sortDown" onclick="changeSort(this,'MoveDown')">下移</a>
				</td>
				<shiro:hasPermission name="ams:amsArchiveRules:edit"><td>
    				<a href="${ctx}/ams/amsArchiveRules/form?id=${amsArchiveRules.id}">修改</a>
					<a href="${ctx}/ams/amsArchiveRules/delete?id=${amsArchiveRules.id}"  onclick="return confirmx('确认要删除该文件吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach> 
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>