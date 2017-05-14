<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
%>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/ueditor/third-party/webuploader/webuploader.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/demo.css"/>
<style>
.header2{ border-bottom: 3px solid #1c2024;}
</style>
<div class="bg-h">
	 <div class="box" style="overflow: initial;">
	  <div class="cp_list clearfix">
	   <div class="zhc_abs" id="loanStateName"></div>
	   <div class="fl lf_img"><img src="" id="loanCover"  width="588px" height="463px" /></div>
	   <div class="fr txt_right">
	    <h3><a id="loanName"></a></h3>
		<span class="pz_text" id="superIndustryName"></span>
		<p class="ft18 col6" id="blockChain-p" style="position: absolute; width: 520px;top: 140px;">
			<span class="fr blockChain" ioc="0"><img src="<%=path%>/images/letv/chartsHover.png">
				<i class="chartsIoc"></i>
				<strong style="display:none;">
					<i class="blockChain-ioc"></i>
					<i class="blockChain-address">
						<em class="blockChain-em"><b class="fr chartsNone"></b><a class="blockChain-Doubt" href="/"  target="_blank" >攻略</a>区块链查询地址</em>
						<em class="blockChain-copy"><input class="blockChain-input" id="copyInput" readonly="readonly" type="text" value="werwerwerqweqw2412323" ><a id="copyBtn">复制地址</a><a target="_blank" href="https://charts.ripple.com/#/graph/">去查询</a></em>
					</i>
				</strong>
			</span>
		</p>
		<p class="ft18 col6" id="preheatA">已筹到 <em id="approveAmt"></em><b class="companyCode"></b></p>
		<p class="ft18 col6" id="preheatB" style="display:none;" >
			募集总额 <em id="overFundAmt"></em><b class="companyCode"></b>
		</p>
		<div class="benefit-setbacks jdbar">
            	<span class="span1"></span>
            	<span class="span2" id="pBar"></span>
            	<span class="span3" id="supportRatio1" style="left:0"></span>
        </div>
		<p class="clearfix col6 ft16 ft_bd">
		 <span class="fl" id="lockDay"></span>
		 <!-- <span class="fr" id="dailyProfit"></span> -->
		</p>
		<p class="col9 mrt30" id="loanIntroduction"></p>
		<p class="mt30 ft16" id="preheatC">此项目必须在 <i id="fundEndTime"></i> 前得到 <i id="fundAmt"></i><b class="companyCode"></b> 的支持才可成功！</p>
        <p class="ft16 mt6" id="preheatD">剩余<i id="remainDay"></i>天 !</p>
		<p class="mt25 clearfix detail-fx">
		 <span class="fl">
		  <a class="zan_blk" id="attentionNum"></a>
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
	   <div class="fl lfside">
	    <ul class="xm_navg clearfix" id="detailTab">
		 <li><a class="cur" name="proInfo" url="" >项目介绍</a></li>
		 <li><a name="proRecord" url="/crowdfunding/getSupportList.html" >支持记录</a></li>
		 <li><a name="proEvolve" url="">项目进展</a></li>
		 <li><a name="proComment" url="/crowdfunding/getCommentList.html">项目评论 <em id="commentNum"></em></a></li>
		</ul>
		<div id="detailBody">
			<div id="proInfoContent" h="1"></div>
			<div id="proEvolveContent" h="1" style="display:none;">
		        <div class="jd_content"  id="loanProgress">
					 <div class="jd_content_bor" id="jd_content_bor"></div>
				</div>
			</div>
			<div id="proRecordContent" style="display:none;">
				<ul class="agr_tab" id="suportTable">
				</ul>
		        <div class="letvPage" id="suportPage"></div>
			</div>
			<div id="proCommentContent" style="display:none;">
				<div class="clear pro_pl">
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
		</div>
		
	   </div>
	   <div class="fr right_side">
	    <div class="rg_cent">
	     <h3 style="font-size:18px">项目发起人</h3>
		 <div class="clearfix fqr_xq">
		  <div class="fl mr18">
		   <img src=""  id="userPhone" width="79px" height="79px" style="border-radius: 50%;" />
		  </div>
		  <div class="fr rg_txt">
		   <p class="clearfix">
		    <span class="fl ft18" id="loanUser" ></span>
			<span class="fr city" id="loanAddress" ></span>
		   </p>
		   <p class="mt10 col8" id="selfDetail" ></p>
		  </div>
		 </div>
		</div>
		<div id="bottomBackSetList">
			
		
		</div>
		
	   </div>
	  </div>
     </div>
 </div>

<!-- 抽奖规则-->
<div class="prompt_box prompt_phone" id="passwordDiv" style="left:50%; top: 100px;margin-left: -250px;">
	<a class="prompt_close">关闭</a>
	<h4>抽奖规则</h4>
	<div class="prompt_conter" id="prompt_conter">
		
	</div>
</div>
</div>

<input type="hidden" id="PDloanUser">

<!--背景遮盖层 -->
<div id="bgpop2" class="bgpop2" name="bigp"></div>	
<div id="bgpop" class="bgpop" style="z-index:100;"></div>
      <!-- 项目相关大图片展示-->
<div id="loanBig" class="big_pingbox" name="bigp"><div><img class="imgWd2" src=""></div></div>
<!--定位大图片的1个按钮 -->
<div id="big_close" class="big_pigc" name="bigp"></div> 
        
        
<input id="indexFor" type="hidden" namefor="loan"/>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/jPages.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/copy/swfobject.js"></script>
<script type="text/javascript" src="<%=path%>/js/project/loanDetail-product.js"></script>
