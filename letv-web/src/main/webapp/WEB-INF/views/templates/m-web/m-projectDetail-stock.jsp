<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%
	String path = request.getContextPath();
	String userId = session.getAttribute("userId") == null ? null
			: session.getAttribute("userId").toString();
	String photoUrl = session.getAttribute("userphoto") == null ? null
			: session.getAttribute("userphoto").toString(); 
	String level = session.getAttribute("investorLevel")==null?"0":session.getAttribute("investorLevel").toString();
	String userLevel = session.getAttribute("userLevel")==null?null:session.getAttribute("userLevel").toString();
%>

<script type="text/javascript">
	var siteUserId = "<%=userId%>";
</script>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes" />
<META HTTP-EQUIV="Expires" CONTENT="0"> 
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"> 
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<link rel="stylesheet" href="<%=path %>/style/web.css" />
<script type="text/javascript" src="<%=path %>/js/common/jquery.js"></script>
<script type="text/javascript" src="<%=path %>/js/common/common.js"></script>
<script type="text/javascript" src="<%=path %>/js/common/m-validate.js"></script>
<script type="text/javascript" src="<%=path %>/js/common/TouchSlide.1.1.js"></script>
<script type="text/javascript" src="<%=path %>/js/common/jquery.mobile-1.3.2.min.js"></script>

<title></title>
<script type="text/javascript">

	var path = "<%=path%>";
	var userId = "<%=userId%>";
	var userPhoto = "<%=photoUrl%>";
	var investorLevel = "<%=level%>";
	var userLevel = "<%=userLevel%>";
</script>
<style>
body{margin-bottom:100px;}
</style>
</head>

<body>
<div class="equity-info">
	<div class="equity-img" id="container"><img id="loanPhoto" src="<%=path%>/images/letv/logo02.png" style="width:100%;" /></div>
    <div class="equity-pop">此项目必须在 <i id="fundEndTime"></i>前得到<i id="fundAmt"></i>的支持才可成功！</div>
    <div class="equity-tit" id="loanName"></div>
    <div class="equity-Amt" id="loanIntroduction"></div>
    <div class="equity-Amt">目标金额<em id="fundAmtT" style="font-weight: 700;"></em></div>
    <div class="equity-Ratio">
    	<div class="fr supportRatio" id="supportRatio1"></div>
    	<div class="jdBar"><span id="pBar" class="back1"></span></div>
    </div>
    <ul class="equity-jd clearfix">
    	<li>
            <span>起投金额</span><em id="stockPartAmt"></em>
        </li>
        <li>
            <span>已筹金额</span><em id="approveAmt"></em>
        </li>
        <li>
            <span>剩余天数</span><em id="remainDay"></em>
        </li>
        <li style="display:none;">
            <span>参与人数</span><em id="remainTotal"></em>
        </li>
    </ul>
</div>

<div class="equity-tab pb50">
	<ul id="detailTab" class="tab" data-role="content">
    	<li name="proInfo"><a href="javascript:void(0);" class="cur">项目简介</a></li>
        <li name="proBudget"><a href="javascript:void(0);">融资信息</a></li>
        <li name="team" ><a href="javascript:void(0);">团队介绍</a></li>
        <li name="proComment" url="/crowdfunding/getCommentList.html" ><a href="javascript:void(0);">项目评论</a></li>
        <li name="proProgress" ><a href="javascript:void(0);">项目进展</a></li>
        <li name="rgList" url="/crowdfunding/getSupportList.html" ><a href="javascript:void(0);">投后管理</a></li>
    </ul>
    <div class="detailbody" id="detailBody">
    	<div id="proInfoBody">
    		<div id="mobileVideo" style="text-align: center;padding:20px;display:none;border-bottom: 10px #efeef4 solid;"></div>
    		<div class="centerDiv" id="industryAnalysis">
    			<h3 class="tm_title">行业分析</h3>
    			<div></div>
    		</div>
    		<div class="centerDiv" id="competence">
    			<h3 class="tm_title">核心竞争力</h3>
    			<div></div>
    		</div>
			<div class="centerDiv" id="riskMeasure">
				<h3 class="tm_title">风险控制</h3>
				<div></div>
			</div>
			<div class="centerDiv" id="profitModel">
    			<h3 class="tm_title">盈利模式</h3>
    			<div></div>
    		</div>

			<div class="centerDiv" id="enclosure">
				<h3 class="tm_title">项目附件</h3>
				<div class="enclosureList"></div>
			</div>

    	</div>
    	<div id="proBudgetBody" style="display:none;">
    		
    		<div class="centerDiv" id="financePlan">
				<h3 class="tm_title">融资计划</h3>
				<div></div>
			</div>
			<div class="centerDiv" id="capitalUsed">
				<h3 class="tm_title">资金用途</h3>
				<div></div>
			</div>
			
			<div class="centerDiv" id="expectedReturn">
				<h3 class="tm_title">预计收益</h3>
				<div class="enclosureList"></div>
			</div>
			<div class="centerDiv" id="licenseIdentity">
				<h3 class="tm_title">执照身份信息</h3>
				<div class="centerDiv-img"></div>
			</div>
    	</div>
    	<div id="teamBody" style="display:none;">
    		<div class="centerDiv" id="loanTeam">
				<h3 class="tm_title">团队简介</h3>
				<div></div>
			</div>
			<div class="centerDiv" id="selectFounderPageList">
				<h3 class="tm_title">创始团队成员</h3>
				<div class="teamList" style="padding:0;"></div>
			</div>
    	</div>
    	<div id="proProgressBody" style="display:none;">
    		<div class="jd_content" id="loanProgress">
    			<div class="jd_content_bor" id="jd_content_bor" style="height: 205px;"></div>
    		</div>
    	</div>
    	<div class="zcdel_leftbox" id="proCommentContent" style="display:none;">
        		<div class="clear pro_pl">
		            <div class="pro_pl_are" style="padding:20px;">
		                <textarea id="comVal" placeholder="评论不能少于5个字哦！"  ></textarea>
		                <div class="pro_pl_butdiv">字数限制140字以内！ <a href="javascript:void(0);" class="pro_pl_but fr" id="subComment">发表评论</a></div>
		            </div>
		            <div class="pro_pl_list"></div>
		            <div class="pro_pl_tiao" id="commentUl">
		                <div>
		                	
		                </div>
		            </div>
		            <div class="letvPage" id="commonPage" style="display:none;"></div>
		            <div class="list_more" id="loanPage" style="font-size:25px; margin:30px 0px 0 0 ;display:none;"><a class="fs14"> 点击查看更多>></a></div>
    
		        </div>
        	</div>
    	<div id="rgListBody" style="display:none;">
    		<div class="jd_content" id="rgListTable">
    			
    		</div>
    	</div>
    </div>
</div>

<input id="yLoanuser" type="hidden"/>
<!-- 股权详情单独遮盖曾 -->
<div id="bgpop" class="bgpop"></div>

<div id="stockBgpop" class="sbgpop" style="z-index:100;"></div>
<script type="text/javascript" src="<%=path%>/js/common/jPages.js"></script>
<script type="text/javascript" src="<%=path %>/js/m/projectDetail-stock.js"></script>
</body>
</html>