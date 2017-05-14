<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<title>爱筹网</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript">
	var path = "<%=path%>";
	
	//判断当前系统,移动端跳转到/common/register-phone.html
	var sysName = navigator.userAgent.split(";")[0].split('(')[1];//判断系统
	if(sysName.indexOf("Windows") != -1 || sysName.indexOf("linux") != -1 || sysName.indexOf("windows") != -1 || sysName.indexOf("compatible") != -1){ }else{
		window.location.href=path +  "/common/m-payResult.html";
	}
</script>
<style type="text/css">
	*{margin:0; padding:0; list-style:none;}
	input {outline: none;font-family:"Microsoft YaHei","Lucida Sans Unicode","Myriad Pro";}
	img{border:0;}
	h1,h2,h3,h4,h5,h6{font-weight:normal;}
	em{font-style:normal;}
	body{font-family:"Microsoft YaHei","Lucida Sans Unicode","Myriad Pro";font-size:14px; color:#222;}
	a{color:#222; text-decoration:none;}
	a:hover{color:#fb4d01;}
	.fl,.left{float:left;}
	.fr,.right{float:right;}
	
	/*必须要添加的css:*/
	.clearfix:before,.clearfix:after {content:""; display:table;}
	.clearfix:after { clear:both; overflow:hidden;}
	.clearfix {zoom:1;}
	.tipBox{width:950px; margin:0 auto;}
	.tipTop{ height:25px; line-height:25px; background:#81868b; color:#fff; font-size:11px;}
	.tipTop a{ padding:0px 9px 0px 8px;  border-right:1px solid #686d71; color:#fff;}
	.tipTop a:hover{color:#fb4d01;}
	.tipTop .tipBox{ text-align:right;}
	.tipLogo{ margin-top:14px; margin-bottom:15px;}
	.tipLogo h1{ display:inline-block; padding-right:10px; border-right:1px solid #676d70;}
	.tipLogo span{ font-size:16px; color:#676d70; margin-left:10px;}
	.hr{ border:none; border-top:1px solid #d9d9d9; margin-bottom:20px;}
	.tipContent{ width:948px; border:1px solid #e1e1ca;}
	.tipContent .title{ height:84px; line-height:84px; background:#edfed0; border-bottom:1px solid #e1e1ca; padding-left:85px; font-size:15px; color:#333;}
	.tipContent .title p{ padding-left:48px;}
	.tipContent .title span{ color:#ff0000; margin-left:8px; margin-right:5px;}
	.tipContent .title .yes{ background:url(../../images/yes.png) left center no-repeat;}
	.tipContent .title .no{ background:url(../../images/no.png) left center no-repeat;}
	.tipContent .cont{ margin-top:44px; margin-left:134px;}
	.tipContent .cont>p{ font-size:16px; color:#979797;}
	.tipContent .cont>div{margin:39px 0px 50px 88px;}
	.tipContent .cont>div img{ float:left;}
	.tipContent .cont>div ul{ float:left; margin-left:70px; margin-top:34px;}
	.tipContent .cont>div .li1{ font-size:23px;}
	.tipContent .cont .li1 p{ margin-bottom:7px;}
	.tipContent .cont .li2{ margin-top:35px; font-size:17px; color:#121212; height:29px; line-height:29px;}
	.tipContent .cont .li2 a{ display:inline-block; width:98px; height:27px; text-align:center; line-height:27px; border:1px solid #f54300; color:#fff; background:#f54300; border-radius:3px; margin-left:4px;}
	.tipContent .cont .li2 a:hover{ color:#f54300; background:#fff; }
	.tipContent .cont>div .li1 span{ font-size:15px; color:#979797;}
	.tipContent .cont>div .color{ color:#f35216;}
	.tipContent .foot{ height:42px; line-height:42px; border-top:1px dashed #ccc; font-size:14px; margin-bottom:8px; padding-left:124px; margin:0px 4px;}
	.tipContent .foot a{ color:#0088cc; margin-right:15px;}
	.tipContent .foot a:hover{color:#fb4d01;}
</style>
</head>
<body>
	<div class="tipBox tipLogo">
	    <h1><a href="<%=path%>/common/index.html"><img src="<%=path%>/images/logo.png" /></a></h1><span>${result.titleMsg }</span>
	</div>
	<hr class="hr" />
	<div class="tipBox tipContent">
		<div class="title">
		<c:if test="${!empty result.errorMsg}">
			<p class="no">${result.errorMsg }</p>
		</c:if>
		<c:if test="${!empty result.successMsg}">
			<p class="yes">${result.successMsg }</p>
		</c:if>
	    </div>
	    <div class="cont">
	    	<p id="timeShow">5秒 后跳转到个人中心</p>
	        <div class="clearfix">
	        	<img src="<%=path%>/images/zhifu.png" />
	            <ul>
	            	<li class="li1">
	                	<p class="color">实现梦想，从未如此简单</p>
	                    <span>爱筹网</span>
	                </li>
<!-- 	                <li class="li2"><a href="<%=path%>/common/loanList.html">立即支持</a></li> -->
	            </ul>
	        </div>
	    </div>
	    <div class="foot">
	    	<a href="<%=path%>/common/index.html">回首页</a>
	        <a href="<%=path%>/common/projectList.html">查看项目</a>
	        <a href="<%=path%>/common/myCenter.html">返回个人中心</a>
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
			document.getElementById('timeShow').innerHTML=time+'秒 后跳转到个人中心';
		}
	},1000);
</script>
</html>
