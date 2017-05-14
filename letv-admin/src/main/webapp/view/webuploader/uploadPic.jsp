<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/common_jquery.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传图片</title>
<script type="text/javascript">
var serverUlr='<%=request.getParameter("serverUlr1")%>';
</script>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/webuploader.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/demo2.css"/>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/demo.js"></script>
</head>
<body style="overflow:hidden;">
	<div id="uploader" class="wu-example">
	    <div class="queueList">
	        <div id="dndArea" class="placeholder">
	            <div id="filePicker" class="webuploader-container"><div class="webuploader-pick">点击选择图片</div><div id="rt_rt_19c7jofth1sf117lede1mt31ou41" style="position: absolute; top: 0px; left: 204px; width: 168px; height: 44px; overflow: hidden; bottom: auto; right: auto;"><input type="file" name="file" class="webuploader-element-invisible" multiple="multiple" accept="image/*"><label style="opacity: 0; width: 100%; height: 100%; display: block; cursor: pointer; background-color: rgb(255, 255, 255); background-position: initial initial; background-repeat: initial initial;"></label></div></div>
	            <p>或将照片拖到这里，单次最多可选300张</p>
	        </div>
	    <ul class="filelist"></ul></div>
	    <div class="statusBar" style="display:none;">
	        <div class="progress" style="display: none;">
	            <span class="text">0%</span>
	            <span class="percentage" style="width: 0%;"></span>
	        </div><div class="info">共0张（0B），已上传0张</div>
	        <div class="btns">
	            <div id="filePicker2" class="webuploader-container"><div class="webuploader-pick">继续添加</div><div id="rt_rt_19c7jofu1es41roc1ab41lr6133b6" style="position: absolute; top: 0px; left: 0px; width: 1px; height: 1px; overflow: hidden;"><input type="file" name="file" class="webuploader-element-invisible" multiple="multiple" accept="image/*"><label style="opacity: 0; width: 100%; height: 100%; display: block; cursor: pointer; background-color: rgb(255, 255, 255); background-position: initial initial; background-repeat: initial initial;"></label></div></div><div class="uploadBtn state-pedding">开始上传</div>
	        </div>
	    </div>
	</div>
</body>
</html>