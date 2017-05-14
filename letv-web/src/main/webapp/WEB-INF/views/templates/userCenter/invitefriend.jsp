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


 <div class="back_colh">
	 <div class="box clearfix">
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
			 <p><a href="<%=path %>/common/administrationStock.html">非公开融资</a></p>
			 <p><a href="<%=path %>/common/administrationPublic.html">公益众筹</a></p>
			</div>
		   </li>
		   <li><a href="<%=path %>/common/myTrade.html">交易记录</a></li>
		   <li><a href="<%=path %>/common/modifyData.html">个人信息</a></li>
		   <li><a href="<%=path %>/common/accountSecurity.html">安全中心</a></li>
		   <li><a href="<%=path %>/common/messages.html">消息中心</a></li>
		   <li><a href="<%=path %>/common/sealList.html">合同管理</a></li>
		   <li class="cur" style="border-bottom: 1px solid #eeeeee;"><a href="javascript:void(0)">邀请管理</a></li>
	   </ul>
	  </div>
	  <div class="fr rg_side clearfix">
	   <h4 class="tit_xt yq_tit" id="invite" ><span id="yqhy" class="cur">邀请好友</span><span id="yqjg">邀请结果</span></h4>
	   <div id="inviteBody">
      	<div id="yqhyBody">
		   <div class="mt30">
		    <img src="<%=path %>/images/letv/letv-43.png"/>
		   </div>
		   <p class="centit">如何邀请好友？</p>
		   <ul class="clearfix step_ul">
		    <li>
			 <div><img src="<%=path %>/images/letv/steps1.png"/></div>
			 <p class="mt20 p1">step1</p>
			 <p class="mt20">分享您的邀请链接给好友</p>
			</li>
			<li class="next_li mrl20"></li>
			<li class="wd230">
			 <div><img src="<%=path %>/images/letv/steps2.png"/></div>
			 <p class="mt20 p1">step2</p>
			 <p class="mt8">好友使用您的邀请链接注册时，</p>
	         <p class="mt8">属于您的邀请码会自动读取</p>
			</li>
			<li class="next_li" style="margin-left:0px"></li>
			<li style="margin-left:7px">
			 <div><img src="<%=path %>/images/letv/steps3.png"/></div>
			 <p class="mt20 p1">step3</p>
			 <p class="mt8">好友完成注册，</p>
	         <p class="mt8">即与您建立了邀请关系</p>
			</li>
		   </ul>
		   <div class="clearfix mt25 mb10">
		    <div class="fl leftsd">
			 <div class="clearfix">
			  <p class="fl tg_tit">我的推荐链接：</p>
			  <p class="fl lianjie" id="recommendUrl" style="width:225px;"></p>
			 </div>
			 <div class="ewm_div" id="eweima"></div>
			</div>
			<div class="fr leftsd" style="width:360px;">
			 <div class="clearfix">
			  <p class="fl tg_tit">短信发送邀请码</p>
			  <a class="fr ck_cotent" id="showInfo">查看短信内容</a>
			 </div>
			 <p class="clearfix ">
			 <input type="text" placeholder="请输入被推荐人的手机号码" class="mt30 yq_input" id="phone" />
			 </p>
			 <p class="clearfix mt30">
			  <input type="text" placeholder="验证码" class=" yq_input fl wid188"  id="valicode" />
			  <img style="width:132px; height:45px;" src="<%=path%>/security/securityCodeImage.html?+new Date().getTime()"
									onclick="this.src='<%=path%>/security/securityCodeImage.html?'+new Date().getTime()" class="fr" alt="点击刷新" id="imageStream1" />
			 </p>
			 <a class="mf_send clearfix " id="inviteBtn" >免费发送</a>
			</div>
		   </div>
	   </div>
	   
	   <div id="yqjgBody" style="display:none;">
	   		<div class="yq_div">
		    <p class="p1">共成功邀请  <em id="invitationNum"></em> 人注册</p>
		  
		   <div class="opt_data clearfix">
		    <span class="fl">注册时间：</span>
			<div class="fl opt_div"><input id="dateStart" class="datetime" type="text" onfocus="WdatePicker({isShowOK:false,isShowClear:false,isShowToday:false,readOnly:true})"></div>
			<i class="fl">-</i>
			<div class="fl opt_div"><input id="dateEnd" class="datetime" type="text" onfocus="WdatePicker({isShowOK:false,isShowClear:false,isShowToday:false,readOnly:true})"></div>
			<a class="sx_btn" id="inviteBtn2" >筛选</a>
		   </div>
		    </div>
		    <div  id="inviteTable">
		    	<ul class="news_table yq_tab">
		    	</ul>
		    </div>
		   
		   <div class="clearfix fr letvPage">
	        </div>
	   </div>
	   </div>
	   
	  </div>
	 </div>
 </div>

<!-- 查看短信内容 -->
<div class="agree_box" id="agree_box">
	<div class="agree_close"></div>
	<div id="agreeContent" class="agree_con"></div>
</div>
<div id="bgpop" class="bgpop" name="bigp"></div>
<script type="text/javascript" src="<%=path%>/js/userCenter/modifyData.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/jquery.qrcode.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/jPages.js"></script>
<script type="text/javascript" src="<%=path%>/js/userCenter/invitefriend.js"></script>