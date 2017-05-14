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
<link rel="stylesheet" href="<%=path %>/style/web.css" />
<link rel="stylesheet" href="<%=path %>/style/m-ichou.css" />
<script type="text/javascript" src="<%=path %>/js/common/jquery.js"></script>
<script type="text/javascript" src="<%=path %>/js/m/ichou.js"></script>
<script type="text/javascript" src="<%=path %>/js/common/common.js"></script>
<title>个人中心</title>
<script type="text/javascript">
	var path = "<%=path%>";
	var userId = "<%=userId%>";
	var investorLevel = "<%=level%>";
	var userPhoto = "<%=photoUrl%>";
	var userLevel = "<%=userLevel%>";
</script>
</head>

<body>
<div class="commonWidth mb100 bgHui">
    <div class="percen_top">
    	<div class="percen_head clear">
            <a id="back" href="javascript:void(0);" class="person fl"><img src="<%=path %>/images/ichou-web/left.png" alt="爱筹网" /></a>
            <span class="company ">个人中心</span>
        </div><!--head-->
        <div class="user_main">
        	<a style="display:block" href="<%=path %>/common/m-modifyData.html"><div class="photo" ><img id="centerUserPhoto" /></div></a>
            <div class="address"><img src="<%=path %>/images/ichou-web/add.png"/><span id="address"></span></div>
            <p class="tel" id="mobile"></p>
        </div>
    </div>
    
    <ul class="user_list">
        <li><a href="<%=path%>/common/m-myStartLoan.html" class="u1"><p>我的发起</p></a></li>
        <li><a href="<%=path%>/common/m-myInvestLoan.html" class="u2"><p>我的投资</p></a></li>
        <li><a href="<%=path%>/common/m-myAttention.html" class="u3"><p>我的关注</p></a></li>
        <li><a href="<%=path%>/common/m-centerRZ.html" class="u10"><p>我的认证</p></a></li>
        <li><a href="<%=path%>/common/m-listing.html" class="u4"><p>我的挂牌</p></a></li>
        <li><a href="<%=path%>/common/m-message.html" class="u11"><p>消息管理</p></a></li>
        <li><a href="<%=path%>/common/m-modifyAddress.html" class="u5"><p>收货地址</p></a></li>
        <li><a href="<%=path%>/common/m-myTrade.html" class="u6"><p>资金管理</p></a></li>
        <li><a href="<%=path%>/common/m-createBankCardAdmi.html" class="u9"><p>支付绑卡管理</p></a></li>
        <li><a href="<%=path%>/common/m-aboutList.html" class="u7"><p>关于爱筹网</p></a></li>
<!--         <li><a class="u8"><p>新手帮助</p></a></li> -->
    </ul>
    <div class="esc_div"><a class="esc_dl" href="javascript:void(0);" onclick="logout();">退出登录</a></div>
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
	<a href="<%=path %>/common/m-transferList.html">
    	<span class="more"></span>
        <p>挂牌</p>
    </a>
	<a class="cur" id="myCenter" href="javascript:void(0);">
    	<span class="my"></span>
        <p>我的</p>
    </a>
</div>
<script type="text/javascript" src="<%=path %>/js/m/myCenter.js"></script>
</body>
</html>