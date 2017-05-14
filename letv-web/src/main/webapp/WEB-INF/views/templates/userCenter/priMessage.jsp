<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
%>

<div class="clearfix">
    <div class="box" style="margin-top:27px;margin-bottom:20px;">
        <ul class="leftPers">
        	<div class="PersCenter clearfix">
            	<a href="<%=path %>/common/modifyData.html" class="write fr">编辑</a><br /><br />
                <img id="centerUserPhoto" src="" width="100" height="100" style="border-radius:50%;"/>
                <p class="name_" id="userRealName"></p>
                <div class="cont">
                   <span><img src="<%=path %>/images/di.png"/><i id="address"></i></span>&nbsp;
                   <span><img src="<%=path %>/images/time.png"/><i id="createTime"></i>加入</span>
                </div>
                <div class="jianjie" id="selfDetail2"></div>
                <!-- <div class="Ceee">资料完整度：35%</div>
                <div class="fl jdBar"><span style="width:20%"></span></div> -->
            </div>
            <li><a href="<%=path%>/common/myCenter.html">项目管理</a></li>
            <li><a href="<%=path%>/common/myTrade.html">资金管理</a></li>
            <li><a href="<%=path%>/common/listing.html">挂牌管理</a></li>
            <li><a href="<%=path%>/common/centerRZ.html">我的认证</a></li>
            <li><a href="<%=path%>/common/modifyData.html">资料管理</a></li>
            <li><a href="<%=path%>/common/modifyPassword.html">密码管理</a></li>
            <li><a href="<%=path%>/common/modifyAddress.html">收货地址管理</a></li>
            <li><a href="<%=path%>/common/messages.html">消息管理</a></li>
            <li><a class="a_home" href="javascript:void(0);">我的私信</a></li>
            <li><a href="<%=path%>/common/fansManage.html">粉丝管理</a></li>
            <!-- <li><a href="<%=path%>/common/invitefriend.html">邀请码</a></li> -->
        </ul>
        <div class="fl comment rightPers" style="background:#fff; min-height:706px; padding-top:30px;">
        	<div id="commTitle" class="title"><a class="cur" id="message" href="javascript:void(0);">我的私信</a></div>
            <!--我的私信-->
            <div id="commInfo">
	            <div id="message_data" class="sysComment bgColorWh sys">
	            	<ul></ul>
	            </div>
	            <div class="page" id="commentPage" style="display:none;"></div>
            </div>
        </div>
    </div>
</div>

<input id="slideFor" type="hidden" snamefor="message"/>
<script type="text/javascript" src="<%=path%>/js/common/jPages.js"></script>
<script type="text/javascript" src="<%=path%>/js/userCenter/myPriMessages.js"></script>