<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String userId = session.getAttribute("userId")==null?null:session.getAttribute("userId").toString();
String nickName = session.getAttribute("nickName")==null?null:session.getAttribute("nickName").toString();
String userMobile = session.getAttribute("userMobile")==null?null:session.getAttribute("userMobile").toString();
String userphoto = session.getAttribute("userphoto")==null?null:session.getAttribute("userphoto").toString();
%>
<!DOCTYPE html>
<html>
<head>
<title>中华创世纪</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="<%=path%>/style/common.css" />
<link type="text/css" rel="stylesheet" href="<%=path%>/style/style.css" />
<script type="text/javascript" src="<%=path%>/js/common/jquery.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/common.js"></script>
<script type="text/javascript" src="<%=path%>/js/main.js"></script>
<script type="text/javascript">
	var path = "<%=path%>";
	var siteUserId = "<%=userId%>";
	var nickName = "<%=nickName%>";
	var userMobile = "<%=userMobile%>";
	var userphoto = "<%=userphoto%>";
</script>

</head>
<body>
<div class="header1 colorgray">
 <div class="box">
	<div class="fl">
    	<p>欢迎您来到中华创世纪！</p>
    </div> 
    <div class="fr">
	 <i id="user_top"></i>
	 <span class="line">|</span>
	 <a href="http://letvjr.com/index.html" target="_blank">中华创世纪</a>
	 <!-- <span class="line">|</span>
	 <a>乐视金融app</a> -->
	 <span class="line">|</span>
	 <a href="<%=path%>/common/nodeList.html?nodeType=help">帮助中心</a>
	 <span class="line">|</span>
	 <em>热线电话：400-999-5157</em>
    </div>	
 </div>
</div>

<div class="header2">
    <div class="box">
        <div class="logo fl">
            <h1><a href="<%=path%>/common/index.html"><img src="<%=path%>/images/letv/logo.png" title="中华创世纪1" alt="中华创世纪"/></a></h1>
        </div>
        <div class="webMenu fr">
            <ul class="clearfix" id="navUl">
                <li><a href="<%=path%>/common/index.html" name="index">首页</a></li>
                <li><a href="<%=path%>/common/projectList.html" name="loan">众筹项目</a></li>
                <li><a href="<%=path%>/common/enterProjectNav.html" name="entry">申请众筹</a></li>
                <li><a href="<%=path%>/common/transferList.html" name="gua">转让市场</a></li>
                <li><a name="centerRZ" id="centerRZ">投资人认证</a></li>
            </ul>
        </div>
    </div>
</div>
<style>
.header2{ border-bottom: 3px solid #1c2024;}
.bai_box{ background-color: #f4f3f3;overflow:hidden;padding-bottom:30px;}
</style>
<div class="bai_box">
<div class="box">
	<div class="orderBox" style="overflow:hidden;margin-top:30px;">
		<div class="orderTit"><h4 class="orderH4" style="margin: 0;">${result.titleMsg }</h4></div>
		<div class="fin_div" style="padding-bottom:100px;">
		<c:if test="${!empty result.errorMsg}">
			<p class="fs_bold">${result.errorMsg }</p>
		</c:if>
		<c:if test="${!empty result.successMsg}">
			<p class="fs_bold">${result.successMsg }</p>
		</c:if>
		
		<p class="col_blue ft25" style="line-height:32px;">您的众筹令预计会在充入成功后的30分钟之内到账！</p>
	    <div class="clearfix operat_btn wid255" style="margin-bottom:38px;">
		 <a class="cur" href="<%=path%>/common/myCenter.html">返回个人中心</a>
		 <a  href="<%=path%>/common/index.html">返回首页</a>
		</div>
		<p><em id="timeShow">5S</em>后自动返回个人中心</p>
	   </div>
	</div>
</div>
</div>
<div class="footer">
  <div class="box">
   <div class="clearfix pdl_40 xFooter">
   <a href="<%=path%>/common/nodeList_info.html?nodeType=about_letv">关于我们</a><span>|</span><a href="<%=path%>/common/nodeList_info.html?nodeType=lxwm">联系我们</a><span>|</span><a href="<%=path%>/common/nodeList_info.html?nodeType=zxns">招聘信息</a><span>|</span><a href="<%=path%>/common/nodeList_info.html?nodeType=cjwt">常见问题</a><span>|</span><a href="<%=path%>/common/nodeList_info.html?nodeType=loan_standard">项目规范</a><span>|</span><a href="<%=path%>/common/nodeList_info.html?nodeType=invest_rule">投资规则</a><span>|</span><span style="padding:0;">服务热线：400-999-5157（９：00 - 21：00）</span>
	
	<%-- <dl class="fl">
	 <dt>关于</dt>
	 <dd><a href="<%=path%>/common/nodeList_info.html?nodeType=about_letv">关于我们</a></dd>
	 <dd><a href="<%=path%>/common/nodeList_info.html?nodeType=lxwm">联系我们</a></dd>
	 <dd><a href="<%=path%>/common/nodeList_info.html?nodeType=zxns">招聘信息</a></dd>
	</dl>
	<dl class="fl">
	 <dt>帮助</dt>
	 <dd><a href="<%=path%>/common/nodeList_info.html?nodeType=cjwt">常见问题</a></dd>
	 <dd><a href="<%=path%>/common/nodeList_info.html?nodeType=loan_standard">项目规范</a></dd>
	 <dd><a href="<%=path%>/common/nodeList_info.html?nodeType=invest_rule">投资规则</a></dd>
	</dl>
	<dl class="fl">
	 <dt>服务热线</dt>
	 <dd class="cola">400-999-5157</dd>
	 <dd class="cola">上班时间：９: 00 - 21 : 00</dd>
	</dl>
	<dl class="fl">
	 <dt>关注我们</dt>
	 <a class="weibo"></a>
	 <!-- <a class="weixin"></a> -->
	</dl> --%>
   </div>
   <p class="tc mt20 colc">Copyright © 2016 中华创世纪 All Rights Reserved 京ICP备16048721号</p>
  </div>
 </div>
</body> 

<script type="text/javascript">
	 var time=5;
	var timer = window.setInterval(function(){
		time--;
		if (time==0) {
			window.clearInterval(timer);
			window.location.href=path+'/common/myCenter.html';
		}else if(time > 0){
			document.getElementById('timeShow').innerHTML=time+'S';
		}
	},1000); 
</script>
</html>
