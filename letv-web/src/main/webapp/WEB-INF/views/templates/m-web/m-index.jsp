<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%
	String path = request.getContextPath();
	String userId = session.getAttribute("userId") == null ? null
			: session.getAttribute("userId").toString();
	String level = session.getAttribute("investorLevel")==null?"0":session.getAttribute("investorLevel").toString();
	String photoUrl = session.getAttribute("userphoto") == null ? null
			: session.getAttribute("userphoto").toString(); 
	String userLevel = session.getAttribute("userLevel")==null?null:session.getAttribute("userLevel").toString();
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
<%-- <link rel="stylesheet" href="<%=path %>/style/no1.css" type="text/css" > --%>
<link rel="stylesheet" href="<%=path %>/style/web.css" />
<link rel="stylesheet" href="<%=path %>/style/m-ichou.css" />
<script type="text/javascript" src="<%=path %>/js/common/jquery.js"></script>
<script type="text/javascript" src="<%=path %>/js/m/ichou.js"></script>
<script type="text/javascript" src="<%=path %>/js/common/common.js"></script>
<script type="text/javascript" src="<%=path %>/js/common/TouchSlide.1.1.js"></script>
<%-- <script type="text/javascript" src="<%=path %>/js/common/iscroll.js"></script> --%>
<%-- <script type="text/javascript" src="<%=path %>/js/common/no1.js"></script> --%>

<title>首页</title>
<script type="text/javascript">
	var path = "<%=path%>";
	var userId = "<%=userId%>";
	var investorLevel = "<%=level%>";
	var userPhoto = "<%=photoUrl%>";
	var userLevel = "<%=userLevel%>";
</script>
</head>

<body>
<div class="commonWidth mb100 bgHui" style="padding-bottom:10px;">
    <div class="head clear">
    	<a href="javascript:;" class="indlogo fl "><img src="<%=path %>/images/ichou-web/ind_logo.png" alt="爱筹网" /></a>
        <span class="company ">爱筹网</span>
        <div id="indexHw" href="javascript:void(0);" class="person_1 fr"></div> 
    </div><!--head-->
    <div class="banner">
    	<div id="slideBox" class="slideBox">
			<div class="bd">
				<ul id="banner_pig"></ul>
			</div>
			<div class="hd">
				<ul></ul>
			</div>
		</div>
    </div><!--banner-->
    
    <div class="nav clear">
    		<div style="width:20%;float:left;">
    			<a class="nav_li ht1" href="<%=path %>/common/m-projectList.html?type=public_service">
		        	<i class="nav_left_img"><img src="<%=path %>/images/ichou-web/ht1.png" alt="筹爱心"/></i>
		            <span>筹爱心</span>
		        </a>
    		</div>
    		<div style="width:20%;float:left;">
    			<a class="nav_li ht2" href="<%=path %>/common/m-projectList.html?type=entity">
		        	<i class="nav_left_img"><img src="<%=path %>/images/ichou-web/ht2.png" alt="筹好货"/></i>
		            <span>筹好货</span>
        	   </a>
    		</div>
    		<div style="width:20%;float:left;">
    			<a class="nav_li ht3" href="<%=path %>/common/m-projectList.html?type=house">
		        	<i class="nav_left_img"><img src="<%=path %>/images/ichou-web/ht3.png" alt="筹好房"/></i>
		            <span>筹好房</span>
       			</a>
    		</div>
    		<div style="width:20%;float:left;">
    			<a class="nav_li ht4" href="<%=path %>/common/m-projectList.html?type=stock">
		        	<i class="nav_left_img"><img src="<%=path %>/images/ichou-web/ht4.png" alt="筹好业"/></i>
		            <span>筹好业</span>
        		</a>
    		</div>
    		<div style="width:20%;float:left;">
    			<a class="nav_li ht5" href="<%=path %>/common/m-transferList.html" style="margin-right:0px;">
		        	<i class="nav_left_img"><img src="<%=path %>/images/ichou-web/ht5.png" alt="筹挂牌"/></i>
		            <span>筹挂牌</span>
        		</a>
    		</div>
			
    </div><!--nav-->
    
	    <div id="wrapper">
			<div id="scroller">
				  <div id="pullDown" style="display: none;" align="center"><img src="<%=path%>/ichou-web/hook-spinner.gif"></div>
				  <ul id="projectList" class="prolist"></ul>
				  <div id="pullUp" style="display: none;" align="center"><img src="<%=path%>/ichou-web/hook-spinner.gif"></div>
			</div>
		</div>
       <div class="list-more" style="font-size:25px; margin:30px 0px 100px;display:none;"  id="moreList"><a class="ckgd">点击查看更多>></a></div>
    </div>
    <div class="foot_fxied clearfix">
    	<a class="cur" href="javascript:void(0);">
        	<span class="home"></span>
            <p>首页</p>
        </a>
    	<a href="<%=path %>/common/m-projectList.html">
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
<script type="text/javascript" src="<%=path %>/js/m/index.js"></script>
</body>
</html>