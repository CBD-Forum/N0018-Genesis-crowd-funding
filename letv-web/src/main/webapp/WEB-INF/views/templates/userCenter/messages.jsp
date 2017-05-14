<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
String realName = (String)session.getAttribute("realName");
String userId = session.getAttribute("userId")==null?null:session.getAttribute("userId").toString();
String lastLoginTime = (String)session.getAttribute("lastLoginTime");
String isBorrower = (String)session.getAttribute("isBorrower");
String isAuth = (String)session.getAttribute("isAuth");
%>
<script type="text/javascript">
	var realName = "<%=realName%>";
	var userId = "<%=userId%>";
	var lastLoginTime = "<%=lastLoginTime%>";
	var isBorrower = "<%=isBorrower%>";
	var isAuth = "<%=isAuth%>";
</script>
<style>
.header2{ border-bottom: 3px solid #1c2024;}
.bai_box{ background-color: #f4f3f3;overflow:hidden;padding-bottom:30px;}
</style>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/ueditor/third-party/webuploader/webuploader.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/demo.css"/>
<div class="bai_box">
<div class="box pt30">
	<div class="fl xt_news">
	   <div class="cont_xq">
	    <%-- <a href="<%=path %>/common/modifyData.html"><img id="centerUserPhoto" class="rightImg"></a> --%>
	    <div class="tx" id="image_file">
    		<input hidefocus="true" style="font-size:50px;display:none;" name="file" type="file" />
        	<a href="javascript:void(0);" class="photo"><img id="imgheadPhoto" src="" width="113px" height="113px" style="border-radius:50%;"/></a>
            <div class="cover" nullMessage="请上传项目封面" style="height:3px"></div>
            <input type="hidden" id="loan_logo_url"/>
        </div>
		<p class="mt15 ft16" id="userRealName"></p>
		<p class="rzh_cz"><a class="rzh1 cur" href="<%=path %>/common/accountSecurity.html"></a><a class="rzh2" id="realNameRZ"></a><a class="rzh3" id="bankCardRZ"></a></p>
	   </div>
	   <ul class="lf_nav">
		   <li class="bor_top"><a href="<%=path %>/common/myCenter.html">账户总览</a></li>
		   <li class="rel hg225"><a id="rightProjectList">项目管理</a>
		    <div class="abs_div">
			 <p class="bor_top"><a href="<%=path %>/common/administrationProduct.html">产品众筹</a></p>
			 <p><a href="<%=path %>/common/administrationPublic.html">非公开融资</a></p>
			 <p><a href="<%=path %>/common/administrationStock.html">公益众筹</a></p>
			</div>
		   </li>
		   <li><a href="<%=path %>/common/myTrade.html">交易记录</a></li>
		   <li><a href="<%=path %>/common/modifyData.html">个人信息</a></li>
		   <li><a href="<%=path %>/common/accountSecurity.html">安全中心</a></li>
		   <li class="cur"><a href="javascript:void(0)">消息中心</a></li>
		   <li><a href="<%=path %>/common/sealList.html">合同管理</a></li>
		   <li><a href="<%=path %>/common/invitefriend.html">邀请管理</a></li>
	   </ul>
	</div>
	<div class="fr rg_side">
		<h3 class="zhaq_h3">系统消息</h3>
		<ul class="screen clearfix" id="commTitle">
            <li><a href="javascript:void(0);" vname="all">全部</a></li>
            <li><a href="javascript:void(0);" vname="unread">未读</a></li>
            <li><a href="javascript:void(0);" vname="read">已读</a></li>
        </ul>
		<div>
			<div class="address_head">
				<p style="width:35%">标题</p>
				<p style="width:35%">发件时间</p>
				<p style="width:15%">状态</p>
				<p style="width:15%">操作</p>
			</div>
			<div id="messagesBody">
				<div id="allBody" style="display:none;">
					<div id="allListBody">
						<div></div>
						<ul class="address_cont mess_hover"></ul>
					</div>
					<div class="letvPage tr" id="allListPage"></div>
				</div>
				<div id="unreadBody" style="display:none;">
					<div id="unreadListBody">
						<div></div>
						<ul class="address_cont mess_hover"></ul>
					</div>
					<div class="letvPage tr" id="unreadListPage"></div>
				</div>
				<div id="readBody" style="display:none;">
					<div id="readListBody">
						<div></div>
						<ul class="address_cont mess_hover"></ul>
					</div>
					<div class="letvPage tr" id="readListPage"></div>
				</div>
			</div>
		</div>
	</div>
</div>
</div>
<div class="prompt_box prompt_phone" id="MessDiv">
	<a class="prompt_close">关闭</a>
	<h4 id="MessTit"></h4>
	<div id="MessCont" style="padding:30px 40px; line-height: 25px;"></div>
</div>
<div class="sbgpop"></div>
<div class="bgpop" id="bgpop"></div>
<input id="slideFor" type="hidden" snamefor="message"/>
<script type="text/javascript" src="<%=path%>/js/common/jPages.js"></script>
<script type="text/javascript" src="<%=path%>/js/userCenter/modifyData.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/userCenter/messages.js"></script>