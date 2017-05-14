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
            <li><a class="a_home" href="javascript:void(0);">密码管理</a></li>
            <li><a href="<%=path%>/common/modifyAddress.html">收货地址管理</a></li>
            <li><a href="<%=path%>/common/messages.html">消息管理</a></li>
            <li><a href="<%=path%>/common/myPrivateMessage.html">我的私信</a></li>
            <li><a href="<%=path%>/common/fansManage.html">粉丝管理</a></li>
            <!-- <li><a href="<%=path%>/common/invitefriend.html">邀请码</a></li> -->
        </ul>
      <div class="rightPers">
            <div class="clearfix" style="padding:0 0;">
                <!-- 修改密码 -->
                <div id="password_data" class="passwordSet">
	            	<ul>
	                	<li><label>原始密码：</label><input id="pass" type="password"></li>
	                    <li><label>新密码：</label><input id="newPass" type="password">
	                    	<div id="pwds" class="pwd"></div>
	                    </li>
	                    <li><label>确认密码：</label><input id="okPass" type="password"></li>
	                    <li class="btuPut"><a id="savePass" href="javascript:void(0);">保存</a></li>
	                </ul>
	                <div class="fr"></div>
	             </div>
                <!-- 修改密码 -->
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="<%=path%>/js/userCenter/modifyPassword.js"></script>