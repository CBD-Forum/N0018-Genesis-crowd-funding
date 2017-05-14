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
            <li><a href="<%=path%>/common/myPrivateMessage.html">我的私信</a></li>
            <li><a href="<%=path%>/common/fansManage.html">粉丝管理</a></li>
            <!-- <li><a href="<%=path%>/common/invitefriend.html">邀请码</a></li> -->
        </ul>
      <div class="rightPers">
      		<ul id="rzTab" class="rz_tab">
      			<li class="cur">邮箱认证</li>
      			<li>&nbsp;</li>
      		</ul>
            <div class="clearfix" style="padding:0 0;margin-top:30px;">
                <!-- 收货地址管理 -->
                <div class="addressSet" style="width:360px;margin:0 auto;">
	                <ul class="newAddredd">
	                    <li id="sendTipLi" style="visibility:hidden;color:#ff500b;font-size:14px;text-align:center;border:1px dashed #ff500b;width:309px;"></li>
	                    <li>
	                        <input id="email" class="combobox-input" style="width:295px;" type="text" placeholder="请输入您的电子邮箱">
	                    </li>
	                    <li>
	                        <input id="emailCode" class="combobox-input" style="width:145px;" type="text" placeholder="邮箱认证码" nullMessage="邮箱认证码不能为空">
	                        <a href="javascript:void(0);" class="button" style="cursor:pointer;margin-left:5px;font-size:14px;padding:0 10px;" id="sendBtn">发送邮箱认证码</a>
	                    </li>
	                    <li class="btuPut" style="margin-top:40px;"><a id="saveEmail" href="javascript:void(0);" style="margin-left:60px;">确认</a></li>
	                </ul>
	            </div>
                <!-- 收货地址管理 -->
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="<%=path%>/js/userCenter/emailRZ.js"></script>