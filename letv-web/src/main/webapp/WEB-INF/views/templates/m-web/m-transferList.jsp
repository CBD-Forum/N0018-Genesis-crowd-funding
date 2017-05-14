<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%
	String path = request.getContextPath();
	String userId = session.getAttribute("userId") == null ? null
			: session.getAttribute("userId").toString();
	String photoUrl = session.getAttribute("userphoto") == null ? null
			: session.getAttribute("userphoto").toString(); 
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="pragma" content="no-cache" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no"  id="viewport"/>
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="format-detection" content="telephone=no"/>
<link rel="stylesheet" href="<%=path %>/style/web.css" />
<link rel="stylesheet" href="<%=path %>/style/m-ichou.css" />
<script type="text/javascript" src="<%=path %>/js/common/jquery.js"></script>
<script type="text/javascript" src="<%=path %>/js/m/ichou.js"></script>
<script type="text/javascript" src="<%=path %>/js/common/common.js"></script>
<title>挂牌</title>
<script type="text/javascript">
	var path = "<%=path%>";
	var userId = "<%=userId%>";
	var userPhoto = "<%=photoUrl%>";
</script>
</head>

<body>
<div class="commonWidth mb100 bgHui" style="padding-bottom:10px;">
    <div class="head clear back000">
         <div class="search_div">
            <input id="loanName" type="text" placeholder="搜索挂牌项目" class="search_text">
            <a id="loanNameBtn" class="search_abs" href="javascript:void(0);"></a>
         </div>
    </div>
    
    <ul id="tranferType" class="list_filt clear">
    	<li class="fl">
        	<a href="javascript:void(0);" code="Listing" class="cur">挂牌中</a>
        </li>
    	<li class="fr">
        	<a href="javascript:void(0);" code="lisTingEnd">挂牌结束</a>
        </li>
    </ul>
    
    <ul id="projectList" class="prolist back111"></ul>
    <div class="list_more" id="allPage" style="font-size:25px; margin:30px 0px 200px;display:none;"><a class="ckgd">点击查看更多>></a></div>
</div>
<div class="foot_fxied">
	<a href="<%=path %>/common/m-index.html">
    	<span class="home"></span>
        <p>首页</p>
    </a>
	<a href="<%=path %>/common/m-projectList.html">
    	<span class="repay"></span>
        <p>项目</p>
    </a>
	<a class="cur" href="javascript:void(0);">
    	<span class="more"></span>
        <p>挂牌</p>
    </a>
	<a id="myCenter" href="javascript:void(0);">
    	<span class="my"></span>
        <p>我的</p>
    </a>
</div>
<script type="text/javascript" src="<%=path %>/js/m/transferList.js"></script>
</body>
</html>