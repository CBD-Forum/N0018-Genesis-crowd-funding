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
<title>新增地址</title>
<script type="text/javascript">
	var path = "<%=path%>";
	var userId = "<%=userId%>";
</script>
</head>

<body>
<div class="head_top back1">
    <p>新增地址</p>
    <a class="back" href="javascript:void(0);"></a>
</div>
<div id="addAddressBox" class="add_address_div" style="padding-bottom:60px;">
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
<ul class="foot_pos">
	<li><a href="<%=path %>/common/m-index.html"><p class="home"></p>首页</a></li>
	<li><a href="<%=path%>/common/m-projectList-stock.html?isProjectPay=1"><p class="pro"></p>项目融资</a></li>
	<li><a href="<%=path%>/common/m-projectList.html?type=entity"><p class="cp"></p>产品众筹</a></li>
	<%if(userId == null){ %>
    <li><a href="<%=path%>/common/m-login.html"><p class="my"></p>我的</a></li>
    <%}else{ %>
    <li><a class="col1" href="<%=path%>/common/m-myCenter.html"><p class="myl"></p>我的</a></li>
    <%} %>
</ul>
<script type="text/javascript" src="<%=path %>/js/m/entityAddress.js"></script>
</body>
</html>