<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>统计分析</title>
<meta name="decorator" content="default" />
<script src="${ctxStatic}/echarts-2.2.7/build/dist/echarts.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function() {
	//$("#name").focus();
	$("#inputForm").validate({
		submitHandler: function(form){
			loading('正在提交，请稍等...');
			form.submit();
		},
		errorContainer: "#messageBox",
		errorPlacement: function(error, element) {
			$("#messageBox").text("输入有误，请先更正。");
			if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
				error.appendTo(element.parent().parent());
			} else {
				error.insertAfter(element);
			}
		}
	});
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
	<ul class="nav nav-tabs">
	 <li ><a href="${ctx}/ams/guidanceStatistics/">业务指导统计</a></li>
		<li class="active"><a href="${ctx}/act/guidanceStatistics/samePeriod/">业务指导同期比统计</a></li>
	</ul>
	<sys:message content="${message}" />
	<input type="hidden" id="monthArr" value="${monthArr}"/> 
	<input type="hidden" id="nowCountArr" value="${nowCountArr}"/>
	<input type="hidden" id="beforeCountArr" value="${beforeCountArr}"/>
	<input type="hidden" id="nowYear" value="${nowYear}"/>
	<input type="hidden" id="beforeYear" value="${beforeYear}"/>
	<form:form id="searchForm" modelAttribute="amsGuidance" action="${ctx}/ams/guidanceStatistics/samePeriod" method="post" class="breadcrumb form-search">		
		<div>
			
			<label>统计年份：</label>
			<input id="beginDate" name="act.beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
								value="<fmt:formatDate value="${amsGuidance.act.beginDate}" pattern="yyyy"/>"
								onclick="WdatePicker({dateFmt:'yyyy年',isShowClear:false});"/>
			<label>统计月份：</label>
			<form:select path="exten1" class="input-medium">
				<form:option value="" label="选择起始月"/>
				<form:options items="${fns:getAmsDictList('month')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
			--
			<form:select path="exten2" class="input-medium">
				<form:option value="" label="选择结束月"/>
				<form:options items="${fns:getAmsDictList('month')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
			
			&nbsp;
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
			
		</div>
		
	</form:form>
	<sys:message content="${message}"/>
	
	<div id="main" style="height:400px"></div>
	
	 <script type="text/javascript">
        // 路径配置
        require.config({
            paths: {
                echarts: '${ctxStatic}/echarts-2.2.7/build/dist'
            }
        });
        
        // 使用
        require(
            [
                'echarts',
                'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
            ],
            function (ec) {
                // 基于准备好的dom，初始化echarts图表
                var myChart = ec.init(document.getElementById('main')); 
                
                var option = {
                	title:{
            			text:'业务指导同期比统计'
            			//subtext:'虚拟数据'
            		},
                    legend: {
                        data:[$("#nowYear").val(),$("#beforeYear").val()]
                    },
                    xAxis : [
                        {
                            type : 'category',
                            data :$("#monthArr").val().split(",")
                        }
                    ],
                    yAxis : [
                        {
                            type : 'value'
                        }
                    ],
                    series : [
                        {
                           	name:$("#nowYear").val(),
                            type:"line",
                            data:$("#nowCountArr").val().split(","),
                            itemStyle : { normal: {label : {show: true}}}
                            //data:[11, 11, 15, 13, 12, 13, 10,4,7,16,24]
                            /*  markPoint: {
            	                data: [
            	                    {type: 'max', name: '最大值'},
            	                    {type: 'min', name: '最小值'}
            	                ]
            	            },
            	            markLine:{
            	            	data:[
            	            		{type:'average',name:'平均值',itemStyle:{
            	            			normal:{
            	            				color:'blue'
            	            			}
            	            		}}
            	            	]
            	            }   */
                        },
                        {
                            name:$("#beforeYear").val(),
                            type:"line",
                            data:$("#beforeCountArr").val().split(","),
                            itemStyle : { normal: {label : {show: true}}},
                            //data:[1, 16, 25, 11, 18, 10, 2,5,12,14,34]
                            /* markPoint: {
            	                data: [
            	                    {type: 'max', name: '最大值'},
            	                    {type: 'min', name: '最小值'}
            	                ]
            	            },
            	            markLine:{
            	            	data:[
            	            		{type:'average',name:'平均值',itemStyle:{
            	            			normal:{
            	            				color:'green'
            	            			}
            	            		}}
            	            	]
            	            } */
                        }
                    ]
                }; 
               
        
                // 为echarts对象加载数据 
                myChart.setOption(option); 
            }
        );
    </script>
</body>
</html>