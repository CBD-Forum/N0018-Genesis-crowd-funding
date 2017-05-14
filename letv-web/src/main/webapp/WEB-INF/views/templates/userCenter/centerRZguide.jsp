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
<div style="background:#000">
 <div class="guideBan">
 	<h4>投资人认证</h4>
  <ul class="rzBox clearfix">
   <li class="li1" id="leadAuthState">
    <a>
   	<div class="img"></div>
   	<div class="btn"></div>
   	<span class="a_btn">领投人认证</span>
   	</a>
   </li>
   <li class="li2" id="investAuthState">
    <a>
   	<div class="img"></div>
   	<div class="btn"></div>
   	<span class="a_btn">跟投人认证</span>
   	</a>
   </li>
   <li class="li3" id="orgLendAuthState">
    <a>
   	<div class="img"></div>
   	<div class="btn"></div>
   	<span class="a_btn">领投机构认证</span>
   	</a>
   </li>
   <li class="li4" id="orgInvestAuthState">
    <a>
   	<div class="img"></div>
   	<div class="btn"></div>
   	<span class="a_btn">跟投机构认证</span>
   	</a>
   </li>
  </ul>
 </div>
</div>
<input id="indexFor" type="hidden" namefor="centerRZ"/>
<script type="text/javascript" src="<%=path%>/js/userCenter/centerRZguide.js"></script>
