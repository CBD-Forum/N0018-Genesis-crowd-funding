<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes" />
<META HTTP-EQUIV="Expires" CONTENT="0"> 
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"> 
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<link rel="stylesheet" href="<%=path %>/style/web.css" />
<script type="text/javascript" src="<%=path %>/js/common/jquery.js"></script>
<script type="text/javascript" src="<%=path %>/js/common/common.js"></script>
<script type="text/javascript" src="<%=path %>/js/m/login.js"></script>

<title>登录</title>
<script type="text/javascript">
	var path = "<%=path %>" ;
</script>
</head>

<body>
<div class="head_top back1">
    <p>设置</p>
    <div class="back"></div>
</div>
<div class="center_s">
	<ul class="setsjs">
		<li>
			<label class="hb">支持我们，给个好评</label>
		</li>
		<li>
			<label class="hb">清除缓存</label><label style="float:right;margin-right:10px;">缓存大小5.41M</label>
		</li>
		<li>
			<label class="hb">通知设置</label>
		</li>
	</ul>
	<ul class="setsjs" style="margin-top:15px;">
		<li>
			<label class="hb">常见问题</label>
		</li>
		<li>
			<label class="hb">意见反馈</label>
		</li>
		<li>
			<label class="hb">关于我们</label>
		</li>
	</ul>
</div>	
</body>
</html>