<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
%>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/ueditor/third-party/webuploader/webuploader.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/demo.css"/>
<!-- <div class="xs_banner"></div> -->
<div class="box xs_bgBai">
	<div class="xs_top">
		<div class="xs_boxWidth clearfix">
			<dl class="fl xs_introduction">
				<dt><img id="loanPhoto" src="" title="" alt="" /></dt>
				<dd>
					<div class="xs_title clearfix"><span id="loanName"></span><i class="xs_bg1" id="loanStateName"></i></div>
					<p class="xs_detail1" id="loanDes"></p>
				</dd>
			</dl>
			<!-- 
			<div class="fl xs_share">
				<div class="clearfix">
					<span class="xs_xin" id="attentionBtn"><i>关注</i>(<i id="attentionNum"></i>)</span>
				</div>
				<a class="xs_bg2" href="javascript:void(0);" id="talkAboutBtn">约谈</a>
			</div>
			 -->
		</div>
	</div>
	
	<div class="xs_content xs_boxWidth clearfix">
		<div class="fl xs_leftContent">
			<div class="xs_detail">
				<div class="xs_title" id="detailTab">
					<a class="cur" href="javascript:void(0);" url="" name="proInfo">项目简介</a>
					<a href="javascript:void(0);" url="" name="proBudget">项目预算</a>
					<a href="javascript:void(0);" name="rgList" url="/crowdfunding/getSupportList.html">认购记录</a>
					<a href="javascript:void(0);" name="proProgress" url="/crowdfunding/getProgessList.html">项目进展</a>
					<a href="javascript:void(0);" name="proComment" url="/crowdfunding/getCommentList.html" id="freshComment" >评论<i id="commetNum" style="color:#e3423c;margin-left:5px;">0</i></a>
					<a url="" name="proAgree" class="last"  href="javascript:void(0);">电子协议</a>
				</div>
				<div id="detailBody">
		        	<!-- 项目简介 -->
			        <div class="zcdel_leftbox" id="proInfoContent" h="1" style="padding:5px;"><div class="xs_video" id="xs_video"></div><div class="loanDetail"></div></div>
		        	<div class="zcdel_leftbox" id="proBudgetContent" h="1" style="display:none;padding:5px;"></div>
		        	<div class="zcdel_leftbox" id="rgListContent" style="display:none;padding:5px;">
		        		<table cellspacing="0" cellpadding="0" style="width:645px;padding-bottom:0;">
		        			<tr class="odd">
		                        <td style="width:87px;font-size:17px;text-align:center;">序号</td>
		                        <td style="width:123px;font-size:17px;text-align:center;">参与人</td>
		                        <td style="width:155px;font-size:17px;text-align:center;">众筹金额（元）</td>
		                        <td style="width:200px;font-size:17px;text-align:center;">众筹时间</td>
		                        <td style="width:100px;font-size:17px;text-align:center;">状态</td>
		                    </tr>
		        		</table>
		        		<table cellspacing="0" cellpadding="0" id="rgListTable" style="width:645px;padding-bottom:0;">
		        			
		        		</table>
		        		<div class="page" id="rgPage"></div>
		        	</div>
		        	<!-- 项目进展 -->
			        <div class="clear" style="height:500px;display:none;" id="proProgressContent">
			        	<div class="clear pro_jz" style="height:500px;">
				            <p class="jl"><img src="<%=path %>/images/jl.png" />这里记录了发起人为梦想努力的一些成果，快来看看吧~</p>
				            <ul id="loanProgress"></ul>
				        </div>
			        </div>
		        	<!-- 评论 -->
		        	<div class="zcdel_leftbox" id="proCommentContent" style="display:none;padding-top:10px;">
		        		<div class="clear pro_pl" style="width:630px;margin-left:13px;">
				            <div class="pro_pl_are">
				            	<input hidefocus="true" style="font-size:50px;display:none;" name="file" type="file" />
<!-- 				                <div id="image_file" class="pro_pl_sc" style="top:190px;"><a href="javascript:void(0);">上传图片</a></div> -->
				                <input type="hidden" id="loan_logo_url"/>
				                <textarea id="comVal" placeholder="评论不能少于5个字哦！" style="width:630px;"  maxlength="200"></textarea>
				                <div style="display:none;" id="imgheadLi"><img id="imghead" width="110" height="110"/></div>
				                <div class="pro_pl_butdiv"><a href="javascript:void(0);" class="pro_pl_but" id="subComment">发表评论</a></div>
				            </div>
				            <!-- <div class="pro_pl_list">
				                <ul>
				                    <li><a href="javascript:void(0);" class="on">全部</a></li>
				                    <li><a href="javascript:void(0);">只看发起人</a></li>
				                    <li><a href="javascript:void(0);">只看投资人</a></li>
				                </ul>
				            </div> -->
				            <div class="pro_pl_tiao" id="commentDiv">
				                <ul id="commentUl"></ul>
				            </div>
				            <div class="page" id="commonPage"></div>
				        </div>
		        	</div>
		        	<div class="zcdel_leftbox" id="proAgreeContent" h="1" style="display:none;">
		        		<div style="padding:40px;line-height:25px;background:#dbdbdb;">
		        			请您在认购前下载此项目的《电子协议》，本协议内容为最终签约版的模板，协议中数值、比例会根据投资人的认购情况进行相应调整，请您下载后认真阅读，确认并知晓筹好业（无保本）的投资风险，认购 即认同本协议内容。认购后您将不能更改投资人姓名以及转让他人。
		        		</div>
		        		<p style="text-align:center;margin-top:20px;"><span style="width: 301px; height: 81px; line-height: 81px;text-align: center;font-size: 18px;color: #fcfcfc;font-weight: bold; display: block; background: #ff6666;margin: 0 auto;">
		        		<a href="javascript:void(0);" id="eContractDownload">电子协议下载</a></span></p>
		        	</div>
		        </div>
			</div>
		</div>
		<div class="fr xs_rightContent">
			<div class="xs_rightTop xs_border">
				<dl class="clearfix">
					<dt class="fl"><img src="" id="userPhoto"/></dt>
					<dd class="fl">
						<div class="clearfix"><span id="loanUserName"></span><i>发起人</i></div>
						<p>所在城市：<span id="address"></span></p>
						<p style="margin-top:5px;">发起时间：<span id="release_time"></span></p>
					</dd>
				</dl>
				<div class="xs_div">
					<i id="hasSprotTtitle"></i><br /><span>￥<i id="approveAmt"></i></span>
				</div>
				<div class="xs_jd" id="xs_jd">
					<div class="xs_jdt"><span id="pBar"></span></div>
					<div class="xs_jdWrite clearfix">
						<div class="fl"><i id="supportRatio1"></i>%</div>
						<div class="fr">剩余<span id="remainDay"></span>天</div>
					</div>
				</div>
				<div class="xs_xmNum">
					<div class="clearfix">
						<div class="fl">挂牌金额<i id="fundAmt1"></i></div>
						<div class="fr">挂牌分红比例<i id="qtyxhhr_zgbl"></i></div>
					</div>
					<div class="clearfix">
						<div class="fl" style="display:none;">项目方出资<i id="projectFinanceAmt"></i></div>
						<div class="fl">每份金额<i id="miniInvestAmt"></i></div>
					</div>
				</div>
				<div class="xs_btnDiv">
					<div class="xs_div1">
						<span class="span1">挂牌总份数<i id="financeNum"></i>份</span>
						<span id="buyNum1Span">已完成份数<i id="buyNum1"></i>份</span>
					</div>
					<div id="buttonDiv">
					<!-- 	<a class="xs_bg2" href="javascript:void(0);" id="investLastBtn" style="margin-top: 10px;display:none;">我要购买</a> -->
					</div>
				</div>
			</div>
			
			<div class="xs_rightContent xs_border clearfix">
				<h2 id="state2RecordTit">购买记录</h2>
				<ul id="preSupportList" class="buylist">
					
				</ul>
				<a id="more" class="sjs_more" href="javascript:void(0);">查看更多>></a>
			</div>
			
			<div class="xs_bottomContent">
				<div class="xs_title clearfix">
					<span class="first">审核项目</span>
					<span>审核状态</span>
				</div>
				<ul>
					<li><span>法人代表身份证</span><i></i></li>
					<li><span>法人代表个人信用报告</span><i></i></li>
					<li><span>营业执照</span><i></i></li>
					<li><span>营业执照副本</span><i></i></li>
					<li><span>税务登记证</span><i></i></li>
					<li><span>税务登记证副本</span><i></i></li>
					<li><span>组织机构代码证</span><i></i></li>
					<li><span>组织机构代码证副本</span><i></i></li>
					<li><span>公司照片</span><i></i></li>
					<li><span>场地租赁合同</span><i></i></li>
					<li><span>财务报表</span><i></i></li>
					<li><span>行业许可证</span><i></i></li>
				</ul>
				<p>风险提醒：<span>投资项目前，应全面了解相关法律法规、认真阅读《服务协议》、《风险提示书》及相关投资文件，并根据您自身的投资目的、投资投资项目前，应全面了解相关法律法规、认真阅读《服务协议》、《风险提示书》及相关投资文件，并根据您自身的投资目的、投资</span></p>
			</div>
		</div>
	</div>
</div>
<!-- 约谈弹出框 -->
<div class="site_tip_div" id="talkDiv">
	<div class="head">
		<span class="w">约谈</span>
	</div>
	<div class="head_s">
		<p>
			<input type="checkbox" id="sendEmailCheck" checked="checked"/><label for="sendEmailCheck">站内信</label>
			<span style="float:right;">接收者：<span id="loanUserName1"></span></span><br/>
		</p>
		<p style="margin-top:10px;">
			<input type="checkbox" id="sendLoanEmail"/><label for="sendLoanEmail">项目方邮箱</label>
		</p>
	</div>
	<div style="padding:0 20px 20px;">
		<textarea class="talkArea" id="talkArea" nullMessage="请输入约谈内容"></textarea>
	</div>
	<p style="text-align:center;"><span class="button" id="talkBtn">确定</span></p>
</div>

<!-- 点击我要认购/我要投资弹出框 -->
<div class="gqzc" id="investLastDiv" style="width:500px;">
	<div class="gqzc_tit">投资订单
    	<a href="javascript:void(0);" class="close" title="关闭"></a>
    </div>
    <div class="tzr_dd" style="width:300px;margin:0 auto;">
        <ul class="fl qrdd" style="border-left-style:none">
        	<li class="rengou">
            	<p>认购份数：</p>
                <a href="javascript:void(0);" class="jia" id="iJIa">+</a>
                <input type="text" class="duo" style="text-align:center;" id="fenshuNum" value="1" onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/>
                <a href="javascript:void(0);" class="jia" id="iJian">-</a>
                <p>&nbsp;&nbsp;份</p>
                <p class="yuan">¥<span class="orl" id="miniInvestAmt1"></span></p>
            </li>
            <li>
            	<p>当前剩余份数：<span class="orl" id="remainsFenshu"></span>份</p>
            </li>
            <li>
            	<p>验证码：</p>
                <input type="text" class="yzm" id="investValiInput"/>
                <p class="yzm_a">
                    <img alt="点击刷新" id="imageInvest" style="cursor:pointer;"
									src="<%=path%>/security/securityCodeImage.html"
									onclick="this.src='<%=path%>/security/securityCodeImage.html?'+new Date().getTime()" /><br />
                    <a href="javascript:void(0);" id="changeValiBtn">看不清,换一张</a>
                </p>
            </li>
            <li class="zf">
            	<p class="clear">投资总额：<span class="orl"><i id="fundAmt4">0.00</i>元</span></p>
                <p class="clear">分红比例：<span class="orl"><i id="couInvestZgbl">0.00</i>%</span></p>
                <a class="qrzf clear" href="javascript:void(0);" id="investLBtn">确认订单</a>
            </li>
            <li></li>
        </ul>
    </div>
</div>
<form id="subumitTransfer">
	<input type="hidden" name="transferNo" id="hideTransferNo"/>
	<input type="hidden" name="buyParts" id="hideBuyParts"/>
</form>
<input id="yLoanuser" type="hidden"/>
<!-- 股权详情单独遮盖曾 -->
<div id="stockBgpop" class="sbgpop" style="z-index:100;"></div>

<input id="indexFor" type="hidden" namefor="gua"/>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/jPages.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/validate.js"></script>
<script type="text/javascript" src="<%=path%>/js/transfer/transferListDetail.js"></script>
