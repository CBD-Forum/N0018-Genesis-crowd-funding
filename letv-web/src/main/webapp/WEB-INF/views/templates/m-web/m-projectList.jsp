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
<title>项目列表</title>
<script type="text/javascript">
	var path = "<%=path%>";
	var userId = "<%=userId%>";
	var userPhoto = "<%=photoUrl%>";
</script>
</head>

<body>
<div class="commonWidth mb100 bgHui" id="parDiv" style="padding-bottom:20px;">
    <div class="head clear back000">
         <div class="search_div">
            <input type="text" id="loanName" placeholder="搜索项目" class="search_text">
            <a id="topSearch" href="javascript:void(0);" class="search_abs"></a>
         </div>
    </div>
    
    <ul class="list_filt clear">
    	<li class="fl">
        	<a id="loanTypeA" href="javascript:void(0);" type="0">项目类型<img src="<%=path %>/images/ichou-web/icon1.png"/></a>
            <div id="loanType" class="pop_div">
            	<a href="javascript:void(0);" code="">全部</a>
            	<a href="javascript:void(0);" code="public_service">筹爱心</a>
            	<a href="javascript:void(0);" code="entity">筹好货</a>
            	<a href="javascript:void(0);" code="house">筹好房</a>
            	<a href="javascript:void(0);" code="stock">筹好业</a>
            </div>
        </li>
    	<li class="fr">
        	<a id="loanProcessA" href="javascript:void(0);" type="0">项目状态<img src="<%=path %>/images/ichou-web/icon1.png"/></a>
            <div id="loanProcess" class="pop_div">
            	<a class="a_cur" href="javascript:void(0);" code="">全部</a>
            	<a href="javascript:void(0);" code="preheat">预热中</a>
            	<a href="javascript:void(0);" code="funding">众筹中</a>
            	<a href="javascript:void(0);" code="success">已完成</a>
            </div>
        </li>
    </ul>
    <ul id="projectList" class="prolist back111"></ul>
    <div class="list_more" id="loanPage" style="font-size:25px; margin:50px 0px 100px;display:none;"><a class="ckgd"><!-- 点击查看更多>> --></a></div>
    </div>
    <div class="foot_fxied">
    	<a href="<%=path%>/common/m-index.html">
        	<span class="home"></span>
            <p>首页</p>
        </a>
    	<a class="cur" href="javascript:void(0);">
        	<span class="repay"></span>
            <p>项目</p>
        </a>
    	<a href="<%=path %>/common/m-transferList.html">
        	<span class="more"></span>
            <p>挂牌</p>
        </a>
    	<a id="myCenter" href="javascript:void(0);">
        	<span class="my"></span>
            <p>我的</p>
        </a>
    </div>
<script type="text/javascript" src="<%=path %>/js/m/projectList.js"></script>
</body>
</html>