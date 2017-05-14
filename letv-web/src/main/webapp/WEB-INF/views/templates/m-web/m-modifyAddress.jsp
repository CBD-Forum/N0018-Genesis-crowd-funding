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
<script type="text/javascript" src="<%=path %>/js/common/common.js"></script>
<script type="text/javascript" src="<%=path %>/js/common/m-validate.js"></script>
<script type="text/javascript" src="<%=path %>/js/common/TouchSlide.1.1.js"></script>
<title>收货地址管理</title>
<script type="text/javascript">
	var path = "<%=path%>";
	var userId = "<%=userId%>";
</script>
</head>

<body>
<div class="head_top back1">
    <p>收货地址管理</p>
    <div class="back"></div>
</div>
<div class="gladdresssjs" style="padding-bottom:60px; font-size:14px;">
	<div class="newbtn"><a id="newAddBtn" href="javascript:void(0);">+新增收货地址</a></div>
	<div id="addNewAddress" vname="no" class="add_address_div addresssjs">
		<div class="addresstip" id="addressTip"></div>
		<p><input type="text" id="postUser" placeholder="请输入收件人"/></p>
		<p><input type="text" placeholder="请输入手机号" id="postPhone"/></p>
		<p>
			<select id="postProvince"><option value="">请选择城市</option></select>
			<select id="postCity"><option value="">请选择地区</option></select>
		</p>
		<p><input type="text" id="postDetail" placeholder="请输入详细地址"/></p>
		<p><input type="text" placeholder="请输入邮编" id="postCode"/></p>
		<p id="isDefaultP" class="defaultp"><span>设置为默认：</span><label><input type="radio" value="1" name="pDefault" checked="true"/>是</label><label><input type="radio" value="0" name="pDefault" style="margin-left:10px;"/>否</label></p>
		<p style="margin-top:20px;">
			<a class="registerbtn" id="saveBtn">保存</a>
		</p>
	</div>
	<div class="addlist">
		<ul id="addressList">
		</ul>
	</div>
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
<div id="bgpop" class="bgpop"></div>
<script type="text/javascript" src="<%=path %>/js/m/modifyAddress.js"></script>
</body>
</html>