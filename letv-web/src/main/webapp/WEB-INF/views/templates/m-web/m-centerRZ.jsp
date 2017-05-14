<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%
	String path = request.getContextPath();
	String userId = session.getAttribute("userId") == null ? null
			: session.getAttribute("userId").toString();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
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
<title>个人中心</title>
<script type="text/javascript">
	var path = "<%=path%>";
	var userId = "<%=userId%>";
</script>
</head>

<body>
<div class="head_top back1">
    <p>我的认证</p>
    <div class="back"></div>
</div>
<div class="center_rz mt50" style="padding-bottom:60px;">
	<ul id="rzTab" class="rz_tab">
		<li id="gtr">跟投人认证</li>
		<span class="li_sx"></span>
		<li id="ltr" class="cur">领投人认证</li>
	</ul>
	<div id="rzBody" class="rzbodysjs">
		<div id="ltrBody">
			<h2 id="pass2" style="padding:20px 10px 3px 15px;">领投人需符合条件：</h2>
			<ul style="padding:20px;">
				<li>1.应充分认同平台的发展原则及方向；对平台推介项目保持关注，最近一个月约谈过3个项目以上 </li>
				<li>2.在某个行业或领域有丰富的经验，独立的判断力，丰富的行业资源和影响力，很强的风险承受能力；协助项目发起人完成融资。</li>
				<li>3.具有投资管理能力以及投后管理能力；</li>
				<li>4.领投人有投资项目经历，或领投人具有自主成功创业的经验；</li>
				<li>5.最近三年个人年均收入不低于50 万元人民币；金融资产(包括银行存款、股票、债券、基金份额、资产管理计划、银行理财产品、信托计划、保险产品、期货权益等)不低于100 万元人民币。</li>
			</ul>
			<ul class="rz_ul">
				<li>
					<label>真实姓名/机构名称：</label>
					<input type="text" id="realName" nullMessage="真实姓名/机构名称不能为空" placeholder="请输入你的真实姓名"/>
				</li>
				<li style="min-height:100px;">
						<label>身份证号/营业执照注册号</label>
						<p><input type="text" id="cardCode" style="width:230px;" nullMessage="证件号不能为空" placeholder="请输入身份证/营业执照注册号"/></p>
				</li>
			</ul>
			<ul class="rz_ul">
				<li class="yj">
					<label>感兴趣的城市：</label>
					<input type="text" id="concern_city" nullmessage="城市不能为空" placeholder="请输入感兴趣的城市" />
				</li>
				<li class="yj" style="height:auto;">
					<label>关注领域：</label>
					<div id="concern_industryBox" class="clearfix" nullMessage="请勾选投资领域" ></div>
				</li>
			</ul>
			<ul class="rz_ul rzsjs">
			    <li class="cityXQ clearfix" style="border:none;"><p>准备使用总资产的多少比例进行投资？</p></li>
                <li class="cityXQ clearfix"><p>
                	<select id="iPrcent" style="width:180px;height:42px;border:1px solid #ddd;">
                		<option value="5">5</option>
                		<option value="10">10</option>
                		<option value="15">15</option>
                		<option value="20">20</option>
                		<option value="25">25</option>
                		<option value="30">30</option>
                		<option value="35">35</option>
                		<option value="40">40</option>
                		<option value="45">45</option>
                		<option value="50">50</option>
                	</select>
                </p>
                </li>
				<li style="height:auto; padding-bottom:8px;">
					<p>计划投资：</p>
					<label>计划投资项目</label><input type="text" class="rz_tznum" id="planLoanNum" nullMessage="项目个数应该为数字"/><label>个</label>,
					<label>总投资额</label><input type="text" class="rz_tznum" id="playInvestWan" nullMessage="投资金额应该为数字" style="width:50px"/><label>万元</label>
				</li>
				<li>
					<label>投资过的项目：</label>
					<input type="text" id="loanName" nullMessage="项目名称不能为空 " placeholder="请输入项目名称"/>
				</li>
				<li class="clearfix" id="ltLi" style="border-bottom:none;">
					<label class="fl" style="margin-right:20px;">投资身份：</label>
					<label class="fl" style="margin-right:30px;"><input type="radio" name="igj" value="person" checked="checked" class="fl" style="margin-top:10px;" />&nbsp;&nbsp;个人</label>
					<label class="fl"><input type="radio" name="igj" value="institution" class="fl" style="margin-top:10px;" />&nbsp;&nbsp;<span >机构</span> </label>
				</li>
				 <!-- 个人投资的认证人 -->
            	<li class="tabTZ clearfix" id="personRZDiv" style="height:280px;">
            			<div class="clearfix">个人资产总额:&nbsp;<input type="text" id="person_capital" nullMessage="个人资产总额应该为数字" style="width:130px;height:42px;border:1px solid #ddd;"/>&nbsp;万元</div>
                        <div class="clearfix">年收入:&nbsp;<input type="text" id="year_income" nullMessage="年收入应该为数字"style="width:130px;height:42px;border:1px solid #ddd;margin-left:42px;"/>&nbsp;万元</div>
                        <div class="clearfix">行业经历:&nbsp;<textarea class="textarea" type="text" placeholder="介绍你过去的所属行业的背景和经历 " id="user_experience" nullMessage="行业经历不能为空" style="width:270px;height:100px;border:1px solid #ddd;display:block;"></textarea></div>
                </li>
                <!-- 机构投资人的认证 -->
                <li class="tabTZ none clearfix" id="institutionRZDiv" style="height:280px;">
                	<div class="clearfix">所属行业:&nbsp;<input type="text"  id="institution_industry" nullMessage="所属行业不能为空" style="width:130px;height:42px;border:1px solid #ddd;"/></div>
                    <div class="clearfix">公司地址:&nbsp;<input type="text"  id="company_address" nullMessage="公司地址不能为空"  style="width:130px;height:42px;border:1px solid #ddd;"/></div>
                    <div class="clearfix">资产状况说明:&nbsp;<textarea class="textarea" type="text" placeholder="介绍你过去的所属行业的背景和经历 " id="capital_des" nullMessage="资产状况说明不能为空" style="width:270px;height:100px;border:1px solid #ddd;display:block;"></textarea></div>
                </li>
				<!-- <li class="clearfix" style="height:auto;">
					<label>个人简介/机构说明：</label>
					<textarea rows="3" style="width:100%;" id="user_experience" placeholder="介绍你过去的所属行业的背景和经历 " nullMessage="个人简介/机构说明 不能为空"></textarea>
				</li> -->
				<li class="clearfix">
					<label>查看：</label>
					<label><a style="text-decoration:underline; color:#3b9ee3;" href="<%=path %>/common/m-riskBook.html">风险揭示书</a></label>
				</li>
			</ul>
			<div class="rz_bot">
				<label style="line-height:22px; color:#454545; font-size:12px;"><input id="read" type="checkbox" checked="checked" />已阅读并同意风险揭示书、用户服务协议，并承诺以上信息均为真实信息，如填写虚假信息，我将永远失去 申请资格。由虚假信所带来的一切后果，将由我个人承担全部责任。
				</label><br/>
				<p style="margin-top:20px;">
					<a class="sub_btn" href="javascript:void(0);" id="subLTRZBtn">提交申请</a>
				</p>
			</div>
		</div>
		<div id="gtrBody">
			<div class="center_rz">
				<ul class="rz_ul">
				<!-- 	<li class="clearfix">
						<label class="fl" style="margin-right:20px;">投资身份：</label>
						<label class="fl" style="margin-right:30px;"><input type="radio" name="igj1" value="person" checked="checked" class="fl" style="margin-top:10px;" />&nbsp;&nbsp;个人</label>
						<label class="fl"><input type="radio" name="igj1" value="institution" class="fl" style="margin-top:10px;" />&nbsp;&nbsp;机构 </label>
					</li> -->
					<li>
						<label>真实姓名/机构名称：</label>
						<input type="text" id="realName1" nullMessage="输入内容不能为空" placeholder="请输入你的真实姓名"/>
					</li>
					<li style="min-height:100px;">
						<label>身份证号/营业执照注册号</label>
						<p><input type="text" id="cardCode1" style="width:230px;" nullMessage="证件号不能为空" placeholder="请输入身份证/营业执照注册号"/></p>
					</li>
					<!-- <li class="clearfix" style="height:auto;">
						<label>个人简介/机构说明：</label>
						<textarea rows="3" style="width:100%;" id="user_experience1" placeholder="介绍你过去的所属行业的背景和经历 " nullMessage="个人简介/机构说明 不能为空"></textarea>
					</li> -->
				</ul>
				<ul class="rz_ul">
					<li class="yj">
						<label>感兴趣的城市：</label>
						<input type="text" id="concern_city1" nullmessage="城市不能为空" placeholder="请输入感兴趣的城市" />
					</li>
					<li class="yj" style="height:auto;">
						<label>关注领域：</label>
						<div id="concern_industryBox1" class="clearfix" nullMessage="请勾选投资领域" ></div>
					</li>
				</ul>
				<ul class="rz_ul rzsjs">
				
					 <li class="cityXQ clearfix" style="border:none;"><p>准备使用总资产的多少比例进行投资？</p></li>
                      <li class="cityXQ clearfix" ><p>
                      	<select id="iPrcent1" style="width:180px;height:42px;border:1px solid #ddd;">
                      		<option value="5">5</option>
                      		<option value="10">10</option>
                      		<option value="15">15</option>
                      		<option value="20">20</option>
                      		<option value="25">25</option>
                      		<option value="30">30</option>
                      		<option value="35">35</option>
                      		<option value="40">40</option>
                      		<option value="45">45</option>
                      		<option value="50">50</option>
                      	</select>
                      </p>
                     </li>
                    <li>
						<label>投资过的项目：</label>
						<input type="text" id="loanName1" nullMessage="项目名称不能为空 " placeholder="请输入项目名称"/>
					</li>  
					<li style="height:auto; padding-bottom:8px;">
						<p>计划投资：</p>
						<label>计划投资项目</label><input type="text" class="rz_tznum" id="planLoanNum1" nullMessage="项目个数应该为数字"/><label>个</label>,
						<label>总投资额</label><input type="text" class="rz_tznum" id="playInvestWan1" nullMessage="投资金额应该为数字" style="width:50px"/><label>万元</label>
					</li>
					<li>
						<label>查看：</label>
						<label><a style="text-decoration:underline; color:#3b9ee3;" href="<%=path %>/common/m-riskBook.html">风险揭示书</a></label>
					</li>
				</ul>
				<div class="rz_bot">
					<label style="margin-left:10px; line-height:30px;"><input id="read1" checked="checked" type="checkbox" />已阅读并同意风险揭示书、用户服务协议，并承诺以上信息均为真实信息，如填写虚假信息，我将永远失去 申请资格。由虚假信所带来的一切后果，将由我个人承担全部责任。
					</label><br/>
					<p style="margin-top:20px;">
						<a id="subInvestBtn" class="sub_btn">提交申请</a>
					</p>
				</div>
			</div>
		</div>
	</div>
</div>
<input type="hidden" id="concern_industry"/>
<input type="hidden" id="concern_industry1"/>	
<%-- <ul class="foot_pos">
	<li><a href="<%=path %>/common/m-index.html"><p class="home"></p>首页</a></li>
	<li><a href="<%=path%>/common/m-projectList-stock.html?isProjectPay=1"><p class="pro"></p>项目融资</a></li>
	<li><a href="<%=path%>/common/m-projectList.html?type=entity"><p class="cp"></p>产品众筹</a></li>
	<%if(userId == null){ %>
    <li><a href="<%=path%>/common/m-login.html"><p class="my"></p>我的</a></li>
    <%}else{ %>
    <li><a class="col1" href="<%=path%>/common/m-myCenter.html"><p class="myl"></p>我的</a></li>
    <%} %>
</ul> --%>
<ul class="foot_pos">
	<li><a href="<%=path %>/common/m-index.html"><p class="home"></p>首页</a></li>
	<li><a href="<%=path %>/common/m-projectList.html"><p class="pro"></p>项目</a></li>
	<li><a href="<%=path %>/common/m-transferList.html"><p class="cp"></p>挂牌</a></li>
	<%if(userId == null){ %>
    <li><a href="<%=path%>/common/m-login.html"><p class="my"></p>我的</a></li>
    <%}else{ %>
    <li><a class="col1" href="<%=path%>/common/m-myCenter.html"><p class="myl"></p>我的</a></li>
    <%} %>
</ul> 

<script type="text/javascript" src="<%=path %>/js/m/centerRZ.js"></script>
</body>
</html>