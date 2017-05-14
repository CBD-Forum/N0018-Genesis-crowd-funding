<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%
	String path = request.getContextPath();
	String userId = session.getAttribute("userId") == null ? null
			: session.getAttribute("userId").toString();
	String photoUrl = session.getAttribute("userphoto") == null ? null
			: session.getAttribute("userphoto").toString(); 
%>
<script type="text/javascript">
	var siteUserId = "<%=userId%>";
</script>

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
<script type="text/javascript" src="<%=path %>/js/common/TouchSlide.1.1.js"></script>
<title>筹爱心</title>
<script type="text/javascript">
	var path = "<%=path%>";
	var userId = "<%=userId%>";
	var userPhoto = "<%=photoUrl%>";
</script>
</head>

<body>
<div class="head_top back1">
    <p>筹爱心</p>
    <a href="javascript:void(0);" class="back"></a>
    <div id="indexHw" class="right"></div>
</div>

<div class="equity-info mt50">
	<div class="equity-img">
		<img id="loanCover" />
    	<div class="pop"></div>
        <a class="pop-f" id="loanName"></a>
    </div>
    <ul class="equity-jd">
    	<li>已筹资<span class="col1" id="approveAmt"></span></li>
        <li class="jd">
        	<div class="jdBar"><span id="pBar" class="back2"></span></div>
            <p class="clearfix">
            	<span class="fl" id="supportRatio1"></span>
                <span class="fr">剩余<em id="remainDay"></em>天</span>
            </p>
        </li>
        <li class="bot">
			<span>融资总额：<em id="fundAmt"></em></span>
			<span>发起人：<em id="fqrInfo"></em></span>
			 <p  id="gzimg"><i>关注</i> <img style="width:20px;" src="<%=path%>/images/xin1.png" class="jlzcimg" /></p>
        </li>
    </ul>
</div>

<div class="equity-tab mb50">
	<ul id="entityTab" class="tab">
    	<li code="repay"><a href="javascript:void(0);">项目回报</a></li>
        <li code="info"><a href="javascript:void(0);">项目信息</a></li>
        <li code="progress"><a href="javascript:void(0);">项目进展</a></li>
        <li code="comment"><a href="javascript:void(0);">认购记录</a></li>
    </ul>
    <div id="entityBody">
    	<div id="repayBody">
    	</div>
    	<div id="infoBody">
    		<div id="mobileVideo"  style="text-align: center;padding:20px;"></div>
    		<div class="infobody" id="proInfoContent"><div id="loanDetail" class="proinfo"></div></div>
    	</div>
    	<div id="progressBody">
   			<div class="projectNav">
		        <ul class="clearfix" id="loanProgress"></ul>
		    </div>
    	</div>
    	<div id="commentBody">
    		<table cellspacing="0" cellpadding="0" style="width:100%;padding-bottom:0;">
	        	<tbody>
	        		<tr style="background:#ddd;">
                        <td style="width:33%;font-size:14px;text-align: center;height: 35px;">参与人</td>
                        <td style="width:33%;font-size:14px;text-align: center;height: 35px;">众筹金额(元)</td>
                        <td style="width:33%;font-size:14px;text-align: center;height: 35px;">众筹时间</td>
                    </tr>
	        	</tbody>
	        </table>
	        <table id="suportTable" cellspacing="0" cellpadding="0" style="width:100%;"></table>
	        <div class="list_more" id="backMoreList" style="display: none;">点击查看更多</div>
    		<!-- <ul class="talk-list"></ul>
		    <div class="gq-talk clearfix">
		        <input type="text" id="comVal" class="talk-box" placeholder="评论"/>
		        <a id="subComment" href="javascript:void(0);">发表</a>
		        <a class="exp"></a>
		        <a class="add"></a>
		    </div> -->
    	</div>
    </div>
    
    
</div>

<!--背景遮盖层 -->
<div id="bgpop2" class="bgpop2_m" name="bigp"></div>	
      <!-- 项目相关大图片展示-->
<div id="loanBig" class="big_pingbox_m" name="bigp" ></div>
<!--定位大图片的1个按钮 -->
<div id="big_close" class="big_pigc_m" name="bigp"></div> 
   

<div class="bgpop"></div>
<script type="text/javascript" src="<%=path %>/js/m/projectDetail-entity.js"></script>
</body>
</html>