<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
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
<script type="text/javascript">
	var path = "<%=path%>";
	var siteUserId = "<%=userId%>";
	var nickName = "<%=nickName%>";
	var userMobile = "<%=userMobile%>";
	var userphoto = "<%=userphoto%>";
</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="<%=path%>/style/common.css" />
<link type="text/css" rel="stylesheet" href="<%=path%>/style/style...css" />
<link type="text/css" rel="stylesheet" href="<%=path%>/style/style.css" />
<script type="text/javascript" src="<%=path%>/js/common/jquery.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/common.js"></script>
<script type="text/javascript" src="<%=path%>/js/yxqq.js"></script> 
<script type="text/javascript" src="<%=path%>/js/main.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/Base64.js"></script>

<title><tiles:insertAttribute name="title"/></title>
<meta name="keywords" content="<tiles:insertAttribute name="keyword"/>" />
<meta name="description" content="<tiles:insertAttribute name="description"/>" />
<!-- <script type="text/javascript">

		// 最小化客服窗口
		function minimumWindow(){
			 //document.getElementById("chatBox").style.display='block';
			 document.getElementById("chatMain").style.display='none';
		}
  		//关闭客服窗口
		 function closeWindow(){
			  //document.getElementById("chatBox").style.display='block';
			  document.getElementById("chatMain").style.display='none';
		}
		// 打开客服窗口
		function openChatWindow(){
				 //document.getElementById("chatBox").style.display='none';
				 document.getElementById("chatMain").style.display='block';
				if(typeof(exec_obj)=='undefined'){
						exec_obj = document.createElement('iframe');
						exec_obj.name = 'tmp_frame';
						exec_obj.src = 'https://jr-webchat.le.com/webchat/new/openChatWind.html';
						exec_obj.style.display = 'none';
						document.body.appendChild(exec_obj);
				}else{
						exec_obj.src = 'https://jr-webchat.le.com/webchat/new/openChatWind.html?' + Math.random();
				}
		}
		// 页面加载完成调用
		function initChatDiv(){
				var parent = document.getElementsByTagName("body")[0];
			　	var outerDiv = document.createElement("div");
				outerDiv.setAttribute("id", "chatMain");
				outerDiv.setAttribute("class", "chat");
				outerDiv.setAttribute("style", "display: none");
				parent.appendChild(outerDiv);
				chatIframe=document.createElement("iframe");
				chatIframe.setAttribute("src","https://jr-webchat.le.com/webchat/index?custId="+siteUserId+"&custName="+nickName+"&mobile="+userMobile+"&channel=raise");
				chatIframe.setAttribute("name","myframe");
				chatIframe.setAttribute("style","width:320px;height:540px;border: 0px");
				var chatMain = document.getElementById("chatMain");
				chatMain.appendChild(chatIframe);
				var chatBoxInnerA=document.getElementById("openA");
				chatBoxInnerA.setAttribute("onclick","openChatWindow()");
				
		}

  </script> -->
</head>
<body>
	
<ul class="floatFight">
	<!-- <li class="rem"></li> -->
	<!-- <li class="app"></li> -->
	<li class="yw"><a href="<%=path%>/common/nodeList.html?nodeType=help"></a></li>
	<!-- <li class="zx"><a id="openA"></a></li> -->

	<li class="top" id="toSiteTop" style="display:none;"></li>
</ul>
<!-- <script type="text/javascript">
			initChatDiv();
	</script> -->
<div class="header1 colorgray">
 <div class="box">
	<div class="fl">
    	<p id="centerT"></p>
    </div> 
    <div class="fr">
	 <i id="user_top"></i>
	 <span class="line">|</span>
	 <!-- <a href="" target="_blank">中华创世纪</a> -->
	 <!-- <span class="line">|</span>
	 <a>中华创世纪</a> -->
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
            <h1><a href="<%=path%>/common/index.html"><img src="" title="中华创世纪" alt="中华创世纪" id="logo"/></a></h1>
        </div>
        <div class="webMenu fr" style="margin:0;">
            <ul class="clearfix" id="navUl">
                <li><a href="<%=path%>/common/index.html" name="index">首页</a></li>
                <li><a href="<%=path%>/common/projectList.html" name="loan">众筹项目</a></li>
                <li><a href="<%=path%>/common/enterProjectNav.html" name="entry">申请众筹</a></li>
                <li><a href="<%=path%>/common/transferList.html" name="gua">转让市场</a></li>
                <li><a name="centerRZ" id="centerRZ">投资人认证</a></li>
                <li><a href="https://charts.ripple.com/#/graph/"  target="_blank" class="charts"></a></li>
            </ul>
        </div>
    </div>
</div>
	
	<div style="min-height:300px;min-width:1200px;"><tiles:insertAttribute name="content"></tiles:insertAttribute></div>
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
	 <dt>条款</dt>
	 <dd><a href="<%=path%>/common/nodeList_info.html?nodeType=yhfwxy">用户服务协议</a></dd>
	 <dd><a href="<%=path%>/common/nodeList_info.html?nodeType=xryaq">信任和安全</a></dd>
	 <dd><a href="<%=path%>/common/nodeList_info.html?nodeType=ystk">隐私条款</a></dd>
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
   <%-- <div class="foot_img">
    <img src="<%=path%>/images/letv/foot1.png"/>
	<img src="<%=path%>/images/letv/foot2.png"/>
	<img src="<%=path%>/images/letv/foot3.png"/>
   </div> --%>
   <p class="tc mt20 colc">Copyright © 2016 <span id="bottomT"></span> All Rights Reserved 京ICP备16048721号</p>
  </div>
 </div>
	
	<script type="text/javascript" src="<%=path%>/js/common/validate.js"></script>
</body>
</html>
