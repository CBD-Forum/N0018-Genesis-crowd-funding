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
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/ueditor/third-party/webuploader/webuploader.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/demo.css"/>
<script type="text/javascript">
	var realName = "<%=realName%>";
	var userId = "<%=userId%>";
	var lastLoginTime = "<%=lastLoginTime%>";
	var isBorrower = "<%=isBorrower%>";
	var isAuth = "<%=isAuth%>";
</script>

<div class="bai_box">
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
            <li><a class="a_home" href="javascript:void(0);">粉丝管理</a></li>
            <!-- <li><a href="<%=path%>/common/invitefriend.html">邀请码</a></li> -->
        </ul>
        <div class="rightPers fl" style="padding-bottom:20px;">
      		<dl class="sx_list bb_border clearfix">
            	<dt style="width:22%;text-align:center;"><strong style="font-size:16px;">粉丝名</strong></dt>
                <dd style="width:22%;text-align:center;"><strong style="font-size:16px;">关注项目</strong></dd>
                <dd style="width:22%;text-align:center;"><strong style="font-size:16px;">关注时间</strong></dd>
                <dd style="width:22%;text-align:center;"><strong style="font-size:16px;">操作</strong></dd>
            </dl>
            <div>
	            <div class="attention_list" id="attentionList">
	            </div>
            </div>
        </div>
    </div>
</div>

<!-- 私信弹出框 -->
<div class="site_tip_div" id="priSaleDiv" style="height:240px;">
	<div class="head">
		<span class="w">私信</span>
	</div>
	<div style="padding:20px;">
		<textarea class="talkArea" id="saleArea" placeholder="请输入私信内容" nullMessage="请输入私信内容"></textarea>
	</div>
	<p style="text-align:center;"><span class="button" id="saleBtn">确定</span></p>
</div>
<script type="text/javascript" src="<%=path%>/js/userCenter/fansManage.js"></script>