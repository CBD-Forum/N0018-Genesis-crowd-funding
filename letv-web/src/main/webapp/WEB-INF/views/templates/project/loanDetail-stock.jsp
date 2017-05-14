<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
String isAuth = (String)session.getAttribute("isAuth"); 
String userId = session.getAttribute("userId")==null?null:session.getAttribute("userId").toString();
%>

<script type="text/javascript">
	var isAuth = "<%=isAuth%>";
	var siteUserId = "<%=userId%>";
</script>
<style>
.header2{ border-bottom: 3px solid #1c2024;}
</style>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/ueditor/third-party/webuploader/webuploader.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/demo.css"/>

<div class="bg-h">
	 <div class="box" style="overflow: initial;">
	  <div class="cp_list clearfix">
	   <div class="zhc_abs" id="loanStateName"></div>
	   <div class="fl lf_img">
		<img src="" id="loanPhoto" width="588px" height="463px" />
	   </div>
	   <div class="fr txt_right">
	    <h3><a id="loanName"></a></h3>
		<span class="pz_text" id="superIndustryName"></span>
		<p class="ft18 col6" id="blockChain-p" style="position: absolute; width: 520px;top: 140px;">
			<span class="fr blockChain" ioc="0"><img src="<%=path%>/images/letv/chartsHover.png">
				<i class="chartsIoc"></i>
				<strong style="display:none;">
					<i class="blockChain-ioc"></i>
					<i class="blockChain-address">
						<em class="blockChain-em"><b class="fr chartsNone"></b><a class="blockChain-Doubt" target="_blank" href="/">攻略</a>区块链查询地址</em>
						<em class="blockChain-copy"><input class="blockChain-input" id="copyInput" readonly="readonly" type="text" value="werwerwerqweqw2412323" ><a target="_blank" id="copyBtn">复制地址</a><a href="https://charts.ripple.com/#/graph/" target="_blank">去查询</a></em>
					</i>
				</strong>
			</span>
		</p>
		<p class="ft18 col6">已筹到 <em id="approveAmt"></em><b class="companyCode"></b></p>
		<div class="benefit-setbacks jdbar">
            	<span class="span1"></span>
            	<span class="span2" id="pBar" style=" width:0;"></span>
            	<span class="span3" id="supportRatio1" style="left:0"></span>
        </div>
		<p class="clearfix col6 ft16 ft_bd" style="margin-top:30px;">
		 <span class="fl" id="miniInvestAmt" ></span>
		 <!-- <span class="fr" id="qtyxhhr_zgbl"></span> -->
		</p>
		<p class="mt20 ft16">此项目必须在 <i id="fundEndTime"></i> 前得到 <i id="fundAmt"></i><b class="companyCode"></b>的支持才可成功！</p>
        <p class="ft16 mt6">剩余<i id="remainDay"></i>天 !</p>
		<a class="gt_btn" id="investLastBtn">跟 投</a>
		<p class="mt25 clearfix detail-fx">
		 <span class="fl">
		  <a class="zan_blk fl" id="attentionNum"></a>
		  <a class="zan_blk zan_bg2" id="praiseNum"></a>
		 </span>
		 <span class="fr" id="loanDetail-share">
			 分享到：<i class="bdsharebuttonbox fr">
            	<a href="#" class="bds_weixin detail-wx" data-cmd="weixin" title="分享到微信"></a>
            	<a href="#" class="bds_tsina detail-wb" data-cmd="tsina" title="分享到新浪微博"></a>
            	<a href="#" class="bds_qzone detail-qq" data-cmd="qzone" title="分享到QQ空间"></a>
            </i>
		 </span>
		</p>
	   </div>
	  </div>
	  <div class="clearfix mt20">
	   <div class="fl lfside clearfix">
	    <ul class="xm_navg clearfix wd123" style="margin-bottom:0" id="detailTab">
		 <li><a class="cur" name="proInfo" url="">项目介绍</a></li>
		 <li><a name="rzxx">融资信息</a></li>
		 <li><a name="team">团队介绍</a></li>
		 <li><a name="proComment" url="/crowdfunding/getCommentList.html">项目评论 <em id="commetNum"></em></a></li>
		 <li><a name="proProgress" url="/crowdfunding/getProgessList.html">项目进展</a></li>
		 <li><a name="rgList" url="/crowdfunding/getSupportList.html" >投后管理</a></li>
		</ul>
		<div id="detailBody">
        	<!-- 项目简介 -->
	        <div class="zcdel_leftbox" id="proInfoContent" h="1" >
	        	<div class="xs_video" id="xs_video"></div>
	        	<div class="loanDetail" id="loanDetail">
	        		<h3 class="tm_title">行业分析</h3>
					<div class="centerDiv" id="industryAnalysis"></div>
					<h3 class="tm_title">核心竞争力</h3>
					<div id="competence" class="centerDiv"></div>
					<h3 class="tm_title">盈利模式</h3>
					<div id="profitModel" class="centerDiv"></div>
					<h3 class="tm_title">风险控制</h3>
					<div class="centerDiv" id="riskMeasure"></div>
					<h3 class="tm_title">项目附件</h3>
					<div class="centerDiv" id="enclosure"></div>
	        	</div>
	       	</div>
        	<div class="zcdel_leftbox" id="rzxxContent" h="1" style="display:none;">
        		
				<h3 class="tm_title">融资计划</h3>
				<div class="centerDiv" id="financePlan"></div>
				<h3 class="tm_title">预计收益</h3>
				<div class="centerDiv" id="expectedReturn"></div>
				<h3 class="tm_title">资金用途</h3>
				<div class="centerDiv" id="capitalUsed"></div>
				<h3 class="tm_title mb25">执照身份信息</h3>
				<div class="licenseIdentity" id="licenseIdentity">
				</div>
        	</div>
        	<div class="zcdel_leftbox" id="teamContent" h="1" style="display:none;">
        		<h3 class="tm_title">团队介绍</h3>
        		<div id="loanTeam" class="centerDiv">
        		</div>
				<h3 class="tm_title">创始团队成员</h3>
				<div id="selectFounderPageList">
				</div>
				
				
        	</div>
        	<!-- 项目进展 -->
	        <div class="clear" style="    min-height: 280px;display:none;" id="proProgressContent">
	        	<div class="jd_content"  id="loanProgress">
					 <div class="jd_content_bor" id="jd_content_bor"></div>
				</div>
	        </div>
        	<!-- 评论 -->
        	<div class="zcdel_leftbox" id="proCommentContent" style="    min-height: 400px;display:none;">
        		<div class="clear pro_pl" style="padding-top:30px;">
		            <div class="pro_pl_are">
		            	<input hidefocus="true" style="font-size:50px;display:none;" name="file" type="file" />
		                <input type="hidden" id="loan_logo_url"/>
		                <textarea id="comVal" placeholder="评论不能少于5个字哦！" onchange="this.value=this.value.substring(0, 200)" onkeydown="this.value=this.value.substring(0, 200)" onkeyup="this.value=this.value.substring(0, 200)"  ></textarea>
		                <div style="color: #F00;">字数限制200字以内！</div>
		                <div style="display:none;" id="imgheadLi"><img id="imghead" width="110" height="110"/></div>
		                <div class="pro_pl_butdiv"><a href="javascript:void(0);" class="pro_pl_but" id="subComment">发表评论</a></div>
		            </div>
		            <div class="pro_pl_list"></div>
		            <div class="pro_pl_tiao" id="commentUl">
		                <div>
		                	
		                </div>
		            </div>
		            <div class="letvPage" id="commonPage"></div>
		        </div>
        	</div>
        	<div class="zcdel_leftbox" id="rgListContent" h="1" style="    min-height: 400px;display:none;">
        		
				
        	</div>
        </div>
		
	   </div>
	   <div class="fr right_side">
	    <div class="rg_cent" style="padding-bottom:20px;">
	     <h3 style="font-size:18px;">项目发起人</h3>
		 <div class="clearfix fqr_xq">
		  <div class="fl mr18">
		   <img src="" id="userPhoto" width="79" height="79" style="border-radius: 50%;" />
		  </div>
		  <div class="fr rg_txt">
		   <p class="clearfix">
		    <span class="fl ft18" id="loanUserName"></span>
			<span class="fr city" id="address">北京</span>
		   </p>
		   <p class="mt10 col8" id="selfDetail"></p>
		   <a class="yt_btn" id="talkAboutBtn">约谈</a>
		  </div>
		 </div>
		</div>
		
		<div class="rg_cent">
		 <h3 style="font-size:18px;">投资记录</h3>
		 <div id="ltrListFr">
		 	<div></div>
		 </div>
		 <div class="Investor_btn" id="Investor_btn">加载更多</div>
		</div>
  
	   </div>
	  </div>
     </div>
 </div>




<!-- 约谈弹出框 -->
<div class="prompt_box prompt_phone" id="talkDiv">
	<a class="prompt_close">关闭</a>
	<h4>约谈</h4>
	<div class="yt">
		<h5 id="prompt_box_name"></h5>
		<input id="dateStart" class="datetime" type="text" onfocus="WdatePicker({minDate:'%y-%M-%d'})" >
		<textarea class="talkArea" id="talkArea" nullMessage="请输入约谈内容"></textarea>
	</div>
	<a class="modify_phone_btu" id="talkBtn" href="javascript:void(0);">确认</a>
</div>
<!-- 工作经历 -->
<div class="prompt_box prompt_phone">
	<a class="prompt_close">关闭</a>
	<h4>工作经历</h4>
	<div class="founderTit clearfix"><span class="s1">公司</span><span class="s2">岗位</span><span class="s3">开始时间</span><span class="s4">离职时间</span></div>
	<div class="founderCenter">
	</div>
</div>
<div class="prompt_box prompt_phone" id="founderDiv" style="width:600px;display:none;">
  <h3 class="clearfix">
   <a class="fr prompt_close">关闭</a>
  </h3>
  <h4 id="name_position"></h4>
  <div class="pers_info" style="padding: 30px 33px 30px 40px;">
   <h5>工作经历</h5>
   <div class="founderTit clearfix"><span class="s1">公司</span><span class="s2">岗位</span><span class="s3">开始时间</span><span class="s4">离职时间</span></div>
	<div class="founderCenter" id="founderCenter">
	</div>
   <h5 class="mt30">创业经历</h5>
   <div id="BusinessCenter"></div>
   <h5 class="mt30">教育背景</h5>
   <div id="EducationsCenter"></div>
  </div>
 </div>


<!-- 点击我要预约弹出框 -->
<div class="tk_box" id="preSupportDiv">
	<div class="gqzc_tit">预约认购</div>
    <ul class="tk_margin">
    	<li class="mb20">*预约认购金额不能低于项目方要求的最低投资金额(<i id="preMinAmt"></i>￥)，最高可填两倍的目标融资金额：(<i id="preMaxAmt"></i>￥).</li>
        <li class="rmb clearfix"><span>预约认购金额：</span><input type="text" class="rmb" id="preSupportInput" nullMessage="预约金额应该为数字" logicMessage="预约金额不能低于最低投资金额" logicMessage1="预约金额不能高于目标投资金额2倍"/><span>&nbsp;&nbsp;(¥)</span></li>
        <li class="tk_but_qr_qx clearfix">
        	<a href="javascript:void(0);" id="preSupportBtn">确定</a>
            <a href="javascript:void(0);" id="canelPreSupportBtn">取消</a>
        </li>
    </ul>
</div>

<input id="yLoanuser" type="hidden"/>
<!-- 项目相关大图片展示-->
<div id="loanBig" class="big_pingbox" name="bigp"><div><img class="imgWd2" src=""></div></div>
<!--定位大图片的1个按钮 -->
<div id="big_close" class="big_pigc" name="bigp"></div> 
<!-- 股权详情单独遮盖曾 -->
<div id="stockBgpop" class="sbgpop" style="z-index:100;"></div>
<div id="bgpop" class="bgpop" style="z-index:100;"></div>

<input id="indexFor" type="hidden" namefor="loan"/>
<script type="text/javascript" src="<%=path%>/js/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/jPages.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/validate.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/copy/swfobject.js"></script>
<script type="text/javascript" src="<%=path%>/js/project/loanDetail-stock.js"></script>
