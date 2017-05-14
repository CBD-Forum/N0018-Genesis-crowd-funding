<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
String userId = session.getAttribute("userId")==null?null:session.getAttribute("userId").toString();
%>

<script type="text/javascript">
	var siteUserId = "<%=userId%>";
</script>

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
				<dd>
					<div class="fl xs_share"   style="padding-top:30px;">
						<div class="clearfix">
							<span class="xs_xin" id="attentionBtn"><i>关注</i>(<i id="attentionNum"></i>)</span>
						</div>
						<a class="xs_bg2" href="javascript:void(0);" id="talkAboutBtn">约谈</a>
					</div>
				</dd>
			</dl>
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
					<a href="javascript:void(0);" name="proComment" url="/crowdfunding/getCommentList.html" id="freshComment" style="margin-right:35px;">评论<i id="commetNum" style="color:#e3423c;margin-left:5px;">0</i></a>
					<a url="" name="proAgree" class="last"  href="javascript:void(0);" style="margin-right:0;">电子协议</a>
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
				                <textarea id="comVal" placeholder="评论不能少于5个字哦！" style="width:630px;" maxlength="200"></textarea>
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
					<a href="javascript:void(0);" id="countBtn">计算器</a>
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
						<div class="fl">融资金额<i id="fundAmt1"></i></div>
						<div class="fr">分红比例<i id="qtyxhhr_zgbl"></i></div>
					</div>
					<div class="clearfix">
						<div class="fl" style="display:none;">项目方出资<i id="projectFinanceAmt"></i></div>
						<div class="fl">最低投资额<i id="miniInvestAmt"></i></div>
					</div>
				</div>
				<div class="xs_btnDiv">
					<div class="xs_div1">
						<span class="span1">总份数<i id="financeNum"></i>份</span>
						<span id="buyNum1Span">已认购<i id="buyNum1"></i>份</span>
					</div>
					<div id="buttonDiv">
						
					</div>
				</div>
			</div>
			
			<div class="xs_rightContent xs_border clearfix">
				<h2 id="state2RecordTit"></h2>
				<ul id="preSupportList">
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
<!-- 计算器弹出框 -->
<div class="site_tip_div" id="countDiv" style="width:600px;height:340px;">
	<div class="head">
		<span class="w">收益计算器</span>
	</div>
	<table cellpadding="0" cellspacing="0">
		<tr>
			<td width="30%">合伙人</td>
			<td width="23%">投资额</td>
			<td width="23%">出资比例</td>
			<td width="24%">分红比例</td>
		</tr>
		<tr style="background:#E4E4E4;">
			<td>投资人<br/>（有限合伙人）</td>
			<td><input placeholder="" style="width:100%;height:100%;border:none;text-align:center;" id="countInput"/></td>
			<td id="investPercent"></td>
			<td id="stokePercent"></td>
		</tr>
		<tr>
			<td>投资人<br/>（其他有限合伙人）</td>
			<td id="qtyxhhr"></td>
			<td id="qtyxhhr_czbl"></td>
			<td id="qtyxhhr_zgbl1"></td>
		</tr>
		<tr>
			<td>项目方<br/>（普通合伙人）</td>
			<td id="projectFinanceAmt2"></td>
			<td id="xmfptczblVal"></td>
			<td id="projectBonusRatio"></td>
		</tr>
		<tr>
			<td>项目融资额</td>
			<td id="fundAmt3"></td>
			<td>100%</td>
			<td num="100" id="loanAllAmtPer">100%</td>
		</tr>
	</table>
	<p style="text-align:center;margin-top:20px;"><span class="button" style="padding:5px 50px;" id="counterBtn">计算</span></p>
</div>
<!-- 点击领投弹出框 -->
<div class="gqzc" id="investFirstDiv">
	<div class="gqzc_tit">申请领投人</div>
    <div class="tzr_dd" style="">
        <ul class="qrdd" style="margin-left:100px;border:0;">
        	<li class="rengou">
            	<p>认购份数：</p>
                <a href="javascript:void(0);" class="jia" id="iJIa1">+</a>
                <input type="text" class="duo" style="text-align:center;" id="fenshuNum1" value="1" onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/>
                <a href="javascript:void(0);" class="jia" id="iJian1">-</a>
                <p>&nbsp;&nbsp;份</p>
                <p class="yuan">¥<span class="orl" id="miniInvestAmt2"></span>(单份投资额)</p>
            </li>
            <li>
            	<p>当前剩余份数：<span class="orl" id="remainsFenshu1"></span>份</p>
            </li>
            <li class="tk_textarea clearfix" style="margin-left:0;"><span>申请说明：</span>
	        	<textarea id="applyExplain" nullMessage="请输入申请说明" style="height:auto;"></textarea>
	        </li>
            <li class="zf" style="margin-left:0;">
            	<p class="clear">投资总额：<span class="orl"><i id="fundAmt5">0</i>元</span></p>
                <p class="clear">分红比例：<span class="orl"><i id="couInvestZgbl1">0.00</i>%</span></p>
                <a class="qrzf clear" href="javascript:void(0);" id="investFBtn" style="display:inline-block;margin-top:80px;">确认订单</a>
            </li>
            <li></li>
        </ul>
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
<!-- 点击我要认购/我要投资弹出框 -->
<div class="gqzc" id="investLastDiv">
	<div class="gqzc_tit">投资订单
    	<a href="javascript:void(0);" class="close" title="关闭"></a>
    </div>
    <div class="tzr_dd">
     	<dl class="tzr fl">
        	<dt>请选择领投人</dt>
        	<div id="ltrList"></div>
        </dl>
        <ul class="fl qrdd">
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
<form id="supFomr" action="<%=path%>/fundpool/invest/submitInvest.html" method="post" target="_blank">
		<input type="hidden" name="buyNum" id="formBuyNum" />
		<input type="hidden" name="loanNo" id="formLoanNo" />
		<input type="hidden" name="investType" value="firstInves"/>
		<input type="submit" id="supFormBtn" style="display:none;"/>
</form>
<!-- 我要领投form -->
<form id="lForm" action="<%=path%>/fundpool/leaderPay/submitOrderInvest.html" method="post" target="_blank">
		<input type="hidden" name="buyNum" />
		<input type="hidden" name="loanNo" />
		<input type="hidden" name="applyLeadDes" />
		<input type="submit" id="lFormBtn" style="display:none;"/>
</form>
<input id="yLoanuser" type="hidden"/>
<!-- 股权详情单独遮盖曾 -->
<div id="stockBgpop" class="sbgpop" style="z-index:100;"></div>

<input id="indexFor" type="hidden" namefor="loan"/>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/jPages.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/validate.js"></script>
<script type="text/javascript" src="<%=path%>/js/project/loanDetail-house.js"></script>
