<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
%>

<script type="text/javascript" src="<%=path%>/js/userCenter/administrationProduct.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/ueditor/third-party/webuploader/webuploader.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/demo.css"/>
<style>
.header2{ border-bottom: 3px solid #1c2024;}
.bai_box{ background-color: #f4f3f3;overflow:hidden;padding-bottom:30px;}
</style>
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
		<p class="rzh_cz"><a class="rzh1 cur" href="<%=path %>/common/accountSecurity.html"></a><a class="rzh2" id="realNameRZ"></a><a class="rzh3"></a></p>
	   </div>
	   <ul class="lf_nav">
		   <li class="bor_top"><a href="<%=path %>/common/myCenter.html">账户总览</a></li>
		   <li class="rel hg225 cur"><a id="rightProjectList">项目管理</a>
		    <div class="abs_div">
			 <p class="bor_top"><a class="col_blue" href="<%=path %>/common/administrationProduct.html">产品众筹</a></p>
			 <p><a href="<%=path %>/common/administrationStock.html">非公开融资</a></p>
			 <p><a href="<%=path %>/common/administrationPublic.html">公益众筹</a></p>
			</div>
		   </li>
		   <li><a href="<%=path %>/common/myTrade.html">交易记录</a></li>
		   <li><a href="<%=path %>/common/modifyData.html">个人信息</a></li>
		   <li><a href="<%=path %>/common/accountSecurity.html">安全中心</a></li>
		   <li><a href="<%=path %>/common/messages.html">消息中心</a></li>
		   <li><a href="<%=path %>/common/sealList.html">合同管理</a></li>
		   <li><a href="<%=path %>/common/invitefriend.html">邀请管理</a></li>
	   </ul>
	</div>
	<div class="fr rg_side clearfix" style="height:888px;"> 
	   <h4 class="tit_xt clearfix mb20">项目管理</h4>
	   <div class="proj_list">
	    <p class="clearfix mb20">
		 <span><i>投资金额：</i>1000,00,00<b class="companyCode"></b></span>
		 <span><i>退款金额：</i>1000,00,00<b class="companyCode"></b></span>
		 <span class="wid3"><i>退款申请提交时间：</i>2016/3/17 12:12:03</span>
		</p>
		<p class="clearfix mb20">
		 <span><i>投资时间：</i>2016/02/03</span>
		 <span><i>退款说明：</i><em class="col_blue">13845754578</em></span>
		 <span class="wid3"><i>项目方同意退款时间：</i>2016/3/17 12:12:03</span>
		</p>
		<p class="clearfix">
		 <span><i class="ft_bold">退款成功时间：</i>2016/02/03 12:12:03</span>
		</p>
		<div class="tk_shm">
		 退款说明
		</div>
	   </div>
	   <div class="proj_list">
	    <p class="clearfix mb20">
		 <span><i>投资金额：</i>1000,00,00<b class="companyCode"></b></span>
		 <span><i>退款金额：</i>1000,00,00<b class="companyCode"></b></span>
		 <span class="wid3"><i>退款申请提交时间：</i>2016/3/17 12:12:03</span>
		</p>
		<p class="clearfix mb20">
		 <span><i>投资时间：</i>2016/02/03</span>
		 <span><i>退款说明：</i><em class="col_blue">13845754578</em></span>
		 <span class="wid3"><i>项目方同意退款时间：</i>2016/3/17 12:12:03</span>
		</p>
		<p class="clearfix">
		 <span><i class="ft_bold">退款成功时间：</i>2016/02/03 12:12:03</span>
		</p>
		<div class="tk_shm">
		 退款说明
		</div>
	   </div>
	   <div class="proj_list">
	    <p class="clearfix mb20">
		 <span><i>投资金额：</i>1000,00,00<b class="companyCode"></b></span>
		 <span><i>退款金额：</i>1000,00,00<b class="companyCode"></b></span>
		 <span class="wid3"><i>退款申请提交时间：</i>2016/3/17 12:12:03</span>
		</p>
		<p class="clearfix mb20">
		 <span><i>投资时间：</i>2016/02/03</span>
		 <span><i>退款说明：</i><em class="col_blue">13845754578</em></span>
		 <span class="wid3"><i>项目方同意退款时间：</i>2016/3/17 12:12:03</span>
		</p>
		<p class="clearfix">
		 <span><i class="ft_bold">退款成功时间：</i>2016/02/03 12:12:03</span>
		</p>
		<div class="tk_shm">
		 退款说明
		</div>
	   </div>
	   <div class="proj_list">
	    <p class="clearfix mb20">
		 <span><i>投资金额：</i>1000,00,00<b class="companyCode"></b></span>
		 <span><i>退款金额：</i>1000,00,00<b class="companyCode"></b></span>
		 <span class="wid3"><i>退款申请提交时间：</i>2016/3/17 12:12:03</span>
		</p>
		<p class="clearfix mb20">
		 <span><i>投资时间：</i>2016/02/03</span>
		 <span><i>退款说明：</i><em class="col_blue">13845754578</em></span>
		 <span class="wid3"><i>项目方同意退款时间：</i>2016/3/17 12:12:03</span>
		</p>
		<p class="clearfix">
		 <span><i class="ft_bold">退款成功时间：</i>2016/02/03 12:12:03</span>
		</p>
		
	   </div>
	   <div class="ym_div clearfix fr" style="margin-right:10px;">
			<a class="cur">1</a>
			<a>2</a>
			<a>3</a>
			<a>4</a>
			<a>...</a>
			<a>16</a>
			<a>></a>
        </div>
	  </div>
	
</div>
</div>
<script type="text/javascript" src="<%=path%>/js/userCenter/modifyData.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>

<script type="text/javascript" src="<%=path%>/js/common/jPages.js"></script>