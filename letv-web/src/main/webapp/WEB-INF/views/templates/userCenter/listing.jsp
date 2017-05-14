<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
%>

<script type="text/javascript" src="<%=path%>/js/userCenter/listing.js"></script>

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
            <li><a href="<%=path%>/common/myTrade.html">资金管理</a></li><!-- 交易管理 -->
            <li><a href="javascript:void(0);" class="a_home">挂牌管理</a></li>
            <li><a href="<%=path%>/common/centerRZ.html">我的认证</a></li>
            <li><a href="<%=path%>/common/modifyData.html">资料管理</a></li>
            <li><a href="<%=path%>/common/modifyPassword.html">密码管理</a></li>
            <li><a href="<%=path%>/common/modifyAddress.html">收货地址管理</a></li>
            <li><a href="<%=path%>/common/messages.html">消息管理</a></li>
            <li><a href="<%=path%>/common/myPrivateMessage.html">我的私信</a></li>
            <li><a href="<%=path%>/common/fansManage.html">粉丝管理</a></li>
            <!-- <li><a href="<%=path%>/common/invitefriend.html">邀请码</a></li> -->
        </ul>
      <div class="rightPers fl">
            <div class="auth">
            	<ul class="rightRen fl">
                	<li><p>我的标签：</p><i id="centerTarget"></i></li>
                    <li class="clearfix">	
                    	<p class="fl">我的认证：</p>
                        <div class="fl" id="myCenterTZDiv">
                            <img id="rzltrIcon" src="<%=path %>/images/v1_1.png" style="cursor:pointer;"/>
                            <img id="rzgtrIcon" src="<%=path %>/images/v2_2.png" style="margin-left:5px;cursor:pointer;"/>
                            <img id="emailIcon" src="" style="margin-left:5px;cursor:pointer;"/>
                            <span class="button" id="centerRZBtn" style="margin-left:80px;visibility:hidden;">认证</span>
                            <!-- <img id="mobileIcon" src="<%=path %>/images/v4.png" style="margin-left:5px;"/> -->
                        </div>
                    </li>
                    <li id="centerGTNum" class="none" style="margin-top: 40px;"><p>跟投人数： </p><span id="followInvestorNum"></span></li>
            	</ul>
              <img class="fl" src="<%=path %>/images/bor.png"/>
              <div class="rightFour fr" style="width:400px;padding-left:10px;">
                	<p class="p1">投资累计总额(元)</p>
                    <p class="p1">投资项目总数(个)</p><br />
                    <p id="totalSupportAmt"></p>
                    <p id="investLoanNum"></p>
                </div>
          	</div>
            
            <div class="clearfix" style="padding:20px 0;">
                <div id="centerLoanTabBody">
                	<div id="investBody" style="position:relative;">
                	<!-- 
	                	<dl class="scr_sx clearfix" style="background:#eee!important" id="centerLoanType">
		                	<dt style="font-weight: bold;">类别</dt>
		                    <dd><a href="javascript:void(0);" code="canListing" class="cur">可挂牌</a></dd>
		                    <dd><a href="javascript:void(0);" code="Auditing">审核中</a></dd>
		                    <dd><a href="javascript:void(0);" code="Audited">已审核</a></dd>
		                    <dd><a href="javascript:void(0);" code="Listing">挂牌中</a></dd>
		                    <dd><a href="javascript:void(0);" code="lisTingEnd">挂牌完成</a></dd>
		                </dl>
		                 -->
		                <style>
	                		#centerLoanTitleDiv dd,#enterListBody dd{
	                			padding-left:15px;
	                			margin-right:0;
	                			text-align:center;
	                		} 
		               	</style>
		               	<ul class="transferTab">
		               		<li><a code="canListing" class="current">可挂牌</a></li>
		               		<li><a code="Auditing">审核中</a></li>
		               		<li><a code="Audited">已审核</a></li>
		               		<li><a code="Listing">挂牌中</a></li>
		               		<li><a code="lisTingEnd">挂牌完成</a></li>
		               	</ul>
		                <div id="centerLoanTitleDiv" style="width:840px;">
			                <dl class="sx_list bb_border clearfix" style="display:block;font-weight:bold;" id="canListingArray">
			                    <dd style="width:130px;">项目名称</dd>
			                    <dd style="width:130px;">认购单价</dd>
			                    <dd style="width:130px;">可挂牌份数</dd>
			                    <dd style="width:130px;">认购时间</dd>
			                    <dd style="width:180px;">操作</dd>
			                </dl>
			                <dl class="sx_list bb_border clearfix" style="display:none;font-weight:bold;" id="AuditingArray">
			                    <dd style="width:130px;">项目名称</dd>
			                    <dd style="width:130px;">挂牌单价</dd>
			                    <dd style="width:130px;">挂牌份数</dd>
			                    <dd style="width:130px;">挂牌天数</dd>
			                    <dd style="width:180px;">申请时间</dd>
			                </dl>
            			    <dl class="sx_list bb_border clearfix" style="display:none;font-weight:bold;" id="AuditedArray">
			                    <dd style="width:100px;">项目名称</dd>
			                    <dd style="width:80px;">挂牌单价</dd>
			                    <dd style="width:60px;">挂牌份数</dd>
			                    <dd style="width:60px;">挂牌天数</dd>
			                    <dd style="width:80px;">审核时间</dd>
			                    <dd style="width:100px;">审核状态</dd>
			                    <dd style="width:100px;">审核原因</dd>
			                    <dd style="width:100px;">操作</dd>
			                </dl>
			                <dl class="sx_list bb_border clearfix" style="display:none;font-weight:bold;" id="ListingArray">
			                    <dd style="width:130px;">项目名称</dd>
			                    <dd style="width:100px;">挂牌单价</dd>
			                    <dd style="width:100px;">挂牌份数</dd>
			                    <dd style="width:100px;">挂牌完成份数</dd>
			                    <dd style="width:100px;">挂牌天数</dd>
			                    <dd style="width:120px;">挂牌开始时间</dd>
			                </dl>
			                <dl class="sx_list bb_border clearfix" style="display:none;font-weight:bold;" id="lisTingEndArray">
			                    <dd style="width:130px;">项目名称</dd>
			                    <dd style="width:100px;">挂牌单价</dd>
			                    <dd style="width:120px;">挂牌份数</dd>
			                    <dd style="width:120px;">挂牌完成份数</dd>
			                    <dd style="width:100px;">挂牌到帐金额</dd>
			                    <dd style="width:120px;">挂牌服务费</dd>
			                </dl>
		                </div>
		                <!-- 
		                <div id="centerLoanSonBody">
		                	<div id="orderBody"><div></div></div>
		                	<div class="page" id="orderPage"></div>
		                </div>
		                 -->
	                </div>
	                <div id="enterBody">
		                <div id="enterListBody"><div style="width:840px;"></div></div>
		                <div class="page" id="enterListPage"></div>
	                </div>
                </div>
            </div>
          
        </div>
    </div>
</div>
<div class="bgpop alertbg"></div>
<div class="alerApply">
	<form id="createListing">
		<div>
			<label for="total">
				挂牌份数:
			</label>
			<input type="text" name="total" id="totalParts"/>
			<span>份</span>
		</div>
		<div>
			<label>
				挂牌单价:
			</label>
			<input type="text" name="unitPrice" id="perAmt"/>
			<span>元</span>
		</div>
		<div>
			<label>
				挂牌天数:
			</label>
			<input type="text" name="days" id="transferDay"/>
			<span>天</span>
		</div>
		<span id="responseMessage"> </span>
		<div class="btn-group">
			<a class="btn" id="sendListing">确定</a>
			<a class="btn" id="cancel">取消</a>
		</div>
	</form>
</div>
<script type="text/javascript" src="<%=path%>/js/common/jPages.js"></script>