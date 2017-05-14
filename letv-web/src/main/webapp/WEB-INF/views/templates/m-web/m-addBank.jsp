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
<link rel="stylesheet" href="<%=path %>/style/m-ichou.css" />
<script type="text/javascript" src="<%=path %>/js/common/jquery.js"></script>
<script type="text/javascript" src="<%=path %>/js/common/m-validate.js"></script>
<script type="text/javascript" src="<%=path %>/js/common/common.js"></script>
<title>添加提现银行卡</title>
<script type="text/javascript">
	var path = "<%=path %>" ;
	var userId = "<%=userId%>";
</script>
</head>

<body>
<div class="head_top back1">
    <p>绑定银行卡</p>
    <div class="back"></div>
</div>
<div style="padding:60px 0px; font-size:14px; ">
	<ul class="forgetPass_bandbank">
		<li>
			<input type="text" placeholder="请输入真实姓名" id="realname" />
		</li>
		<li>
			<input type="text" placeholder="请输入身份证号码" id="cardCode" />
		</li>
		<li class="clearfix">
			<select id="province" style="width:48%; float:left; margin-top:5px; margin-right:4%;" nullMessage="请选择开户省份"><option>请选择开户省份</option></select>
			<select id="city" style="width:48%; float:left; margin-top:5px;" nullMessage="请选择开户城市"><option>请选择开户城市</option></select>
		</li>
		<li>
			 <select id="bankName" nullMessage="请选择开户银行">
				<option value="">请选择开户银行</option>
				<option value="CMB">招商银行</option>
                <option value="ICBC">工商银行</option>
                <option value="BOC">中国银行</option>
                <option value="CCB">建设银行</option>
                <option value="ABC">农业银行</option>
                <option value="BCM">交通银行</option>
                <option value="PSBC">邮政储蓄</option>
                <option value="CMBC">民生银行</option>
                <option value="SPDB">浦发银行</option>
                <option value="CIB">兴业银行</option>
                <option value="HXB">华夏银行</option>
                <option value="CEB">光大银行</option>
                <option value="GDB">广发银行</option>
                <option value="CNCB">中信银行</option>
			</select>
		</li>
		<li>
			<input type="text" placeholder="请输入开户行具体到支行" id="bankAddress" nullMessage="请输入开户行具体到支行" />
		</li>
		<li>
			<input type="text" placeholder="请输入银行卡号" id="bankNo" nullMessage="请输入银行卡号" />
		</li>
		<li class="clearfix">
			<input type="text" placeholder="请输入手机验证码" id="phoneCode1" maxlength="6" style="width:53%;" nullMessage="请输入手机验证码" /><a href="javascript:void(0);" id="getVertifyb" class="getvertify">获取验证码</a>
		</li>
		<li class="btn">
			<a id="bindCardBtn" href="javascript:void(0);">绑定银行卡</a>
		</li>
	</ul>
</div>
<div class="foot_fxied small_foot_fixed">
	<a href="<%=path %>/common/m-index.html">
    	<span class="home"></span>
        <p>首页</p>
    </a>
	<a href="<%=path %>/common/m-projectList.html">
    	<span class="repay"></span>
        <p>项目</p>
    </a>
	<a href="<%=path %>/common/m-transferList.html">
    	<span class="more"></span>
        <p>挂牌</p>
    </a>
	<a class="cur" id="myCenter" href="javascript:void(0);">
    	<span class="my"></span>
        <p>我的</p>
    </a>
</div>
<div class="bgpop"></div>
<script type="text/javascript" src="<%=path %>/js/m/addBank.js"></script>
</body>
</html>