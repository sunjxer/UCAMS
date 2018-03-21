<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String swfFilePath = "http://192.168.8.10:8080/pdf/789.swf";//session.getAttribute("swfpath").toString();
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/flexpaper_flash.js"></script>
		<script type="text/javascript" src="js/flexpaper_flash_debug.js"></script>
		<style type="text/css" media="screen">
html,body {
	height: 100%;
}

body {
	margin: 0;
	padding: 0;
	overflow: auto;
}

#flashContent {
	display: none;
}
</style>
		<title>文档在线预览系统</title>
	</head>
	<body>
		<div style="position: absolute; left: 50px; top: 10px;">
			<a id="viewerPlaceHolder" style="width: 820px; height: 650px; display: block"></a>
			<script type="text/javascript"> 
				var fp = new FlexPaperViewer(	
						 'FlexPaper_1.5.1_flash/FlexPaperViewer',  //这里是FlexPaperViewer.swf的路径，相对根目录
						 //'FlexPaperViewer',
						 'viewerPlaceHolder', //这里是要显示Swf的区域的ID
						 { 
							 config : {
						 SwfFile : escape('<%=swfFilePath%>'),  
						 //编码设置 //这里是要显示的swf的位置，相对根目录
						 Scale : 0.6, //缩放比例
						 ZoomTransition : 'easeOut',//变焦过渡 //Flexpaper中缩放样式，它使用和Tweener一样的样式，默认参数值为easeOut.其他可选值包括: easenone, easeout, linear, easeoutquad
						 ZoomTime : 0.5, //从一个缩放比例变为另外一个缩放比例需要花费的时间，该参数值应该为0或更大。
						 ZoomInterval : 0.2,//缩放滑块-移动的缩放基础[工具栏] //缩放比例之间间隔，默认值为0.1，该值为正数。
						 FitPageOnLoad : true,//自适应页面 //初始化的时候自适应页面，与使用工具栏上的适应页面按钮同样的效果。
						 FitWidthOnLoad : true,//自适应宽度
						 FullScreenAsMaxWindow : false,//全屏按钮-新页面全屏[工具栏]
						 ProgressiveLoading : false,//分割加载
						 MinZoomSize : 0.2,//最小缩放
						 MaxZoomSize : 3,//最大缩放
						 SearchMatchAll : true,
						 InitViewMode : 'Portrait',//初始显示模式(SinglePage,TwoPage,Portrait)
						 
						 ViewModeToolsVisible : true,//显示模式工具栏是否显示
						 ZoomToolsVisible : true,//缩放工具栏是否显示
						 NavToolsVisible : true,//跳页工具栏
						 CursorToolsVisible : false,
						 SearchToolsVisible : true,
  						 PrintPaperAsBitmap:false,
  						 localeChain: 'en_US'
						 }});
	        </script>
		</div>
		22
	</body>
</html>