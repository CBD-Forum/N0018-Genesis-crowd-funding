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
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes" />
<META HTTP-EQUIV="Expires" CONTENT="0"> 
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"> 
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<script type="text/javascript">
	var path = "<%=path%>";
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
	.tipBox{width:95%; margin:0 auto;}
	.tipTop{ height:40px; line-height:40px; background:#81868b; color:#fff; font-size:14px;}
	.tipTop a{ padding:0px 9px 0px 8px;  border-right:1px solid #686d71; color:#fff;}
	.tipTop a:hover{color:#fb4d01;}
	.tipTop .tipBox{ text-align:right;}
	.tipLogo{ margin-top:14px; margin-bottom:15px;}
	.tipLogo h1{ display:inline-block; padding-right:10px; border-right:1px solid #676d70;}
	.tipLogo span{ font-size:16px; color:#676d70; margin-left:10px;}
	.hr{ border:none; border-top:1px solid #d9d9d9; margin-bottom:20px;}
	.tipContent{ width:90%; border:1px solid #e1e1ca;}
	.tipContent .title{ height:84px; line-height:84px; background:#edfed0; border-bottom:1px solid #e1e1ca; padding-left:85px; font-size:15px; color:#333;}
	.tipContent .title p{ padding-left:48px;}
	.tipContent .title span{ color:#ff0000; margin-left:8px; margin-right:5px;}
	.tipContent .title .yes{ background:url(../../images/yes.png) left center no-repeat;}
	.tipContent .title .no{ background:url(../../images/no.png) left center no-repeat;}
	.tipContent .cont{width:100%;}
	.tipContent .cont>p{ height:40px; line-height:40px; font-size:16px; color:#979797;text-align:center;}
	.tipContent .cont>div{ text-align:center; padding:40px 0px;}
	.tipContent .cont>div ul{ margin-top:34px;}
	.tipContent .cont>div .li1{ font-size:23px;}
	.tipContent .cont .li1 p{ margin-bottom:7px;}
	.tipContent .cont .li2{ margin-top:35px; font-size:17px; color:#121212; height:29px; line-height:29px;text-align:center;}
	.tipContent .cont .li2 a{ display:inline-block; width:98px; height:27px; text-align:center; line-height:27px; border:1px solid #f54300; color:#fff; background:#f54300; border-radius:3px; margin-left:4px;}
	.tipContent .cont .li2 a:hover{ color:#f54300; background:#fff; }
	.tipContent .cont>div .li1 span{ font-size:15px; color:#979797;text-align:center;}
	.tipContent .cont>div .color{color:#f35216;text-align: center;}
	.tipContent .foot{ height:42px; line-height:42px; border-top:1px dashed #ccc; font-size:14px; margin-bottom:8px;text-align:center; margin:0px 4px;}
	.tipContent .foot a{ color:#0088cc; margin-right:15px;}
	.tipContent .foot a:hover{color:#fb4d01;}
</style>
</head>
<body>
	<div class="tipTop">
		<div class="tipBox">您好，欢迎使用爱筹网网贷系统！&nbsp;&nbsp;唯一热线：400-8705-068</div>
	</div>
	<div class="tipBox tipLogo">
	    <h1><a href="<%=path%>/common/m-index.html"><img src="<%=path%>/images/logo.png?v=1" /></a></h1><span>${result.titleMsg }</span>
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
	        	<div>
		        	<img src="<%=path %>/images/zhifu.png" />
	        	</div>
	            <ul>
	            	<li class="li1">
	                	<p class="color">投再多钱也安心</p>
	                    <span>爱筹网网贷系统安全险，100%&nbsp;本息保障</span>
	                </li>
	                <li class="li2"><a href="<%=path%>/common/m-projectList.html">立即投资</a></li>
	            </ul>
	        </div>
	    </div>
	    <div class="foot">
	    	<a href="<%=path%>/common/m-index.html">回首页</a>
	        <a href="<%=path%>/common/m-projectList.html">查看项目</a>
	        <a href="<%=path%>/common/m-myCenter.html">返回我的账户</a>
	    </div>
	</div>
</body>
<script type="text/javascript">
	var time=5;
	var timer = window.setInterval(function(){
		time--;
		if (time==0) {
			window.clearInterval(timer);
			window.location.href=path+'/common/m-myCenter.html';
		}else if(time > 0){
			document.getElementById('timeShow').innerHTML=time+'秒 后跳转到个人中心';
		}
	},1000);
</script>
</html>
