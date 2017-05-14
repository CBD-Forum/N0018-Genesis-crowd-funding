<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%
	String path = request.getContextPath();
	String userId = session.getAttribute("userId") == null ? null
			: session.getAttribute("userId").toString();
%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="pragma" content="no-cache" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no"  id="viewport"/>
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="format-detection" content="telephone=no"/>
<link rel="stylesheet" href="<%=path %>/style/down.css" />
<link rel="stylesheet" href="<%=path %>/style/mss-style.css" />
<script type="text/javascript" src="<%=path %>/js/common/jquery.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">
	var path = "<%=path%>";
	var userId = "<%=userId%>";
	$(document).ready(function(){ 

	    var width= $(window).width();
	    var height =  $(window).height();
	    $('#img').attr("width",width);
	    $('#img').attr("height",height);
	    if(isWeiXin()){
	         $("#formbackground").show();
	    }else{
	         window.location.href="https://www.yimu88.com/ymd.apk";
	    } 
	    
	});  

	function isWeiXin(){ 
		var ua = window.navigator.userAgent.toLowerCase(); 
		if(ua.match(/MicroMessenger/i) == 'micromessenger'){ 
			return true; 
		}else{ 
			return false; 
		} 
	}
</script>
<title>首页</title>
</head>
<body>
	<div class="commonWidth" id="formbackground">
    	<div class="pos-black">
        </div>
    	<div class="pos-top">
        	<ul>
            	<li class="clear" style="margin-top:150px;">
                	<div class="pos_num"><span>1</span><p>点击右上角的 <img src="<%=path %>/images/down1.png"/>按钮</p>
                    <img src="<%=path %>/images/down4.png" style="margin:-160px 0 0 30px;"/>
                </li>
            	<li class="clear">
                	<div class="pos_num"><span>2</span><p>选择 <img src="<%=path %>/images/down2.png"/>下载</p>
                </li>
            </ul>
        </div>
    	
    </div>
    <script src="<%=path %>/js/m/m-style.js"></script>
</body>
</html>
