<%@page import="com.fbd.core.common.utils.SysConfigCache"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/webuploader.css"/>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/config/logoConfig.js"></script>
	
	<div id="nodeDiv" style="width: 98%;">
		<form id="logoForm" method="post" style="margin-top:20px;">
			
			<div class="x-form-item">
				<label class="x-form-item-label">LOGO:</label>
				<div class="x-form-element">
					<div id="logoDiv">
					    <div class="filelist"><img src="<%=path%><%=SysConfigCache.getInstance().findValue("admin_logo_url") %>" style="height:100px;"/></div>
					    <div id="logoBtn">选择图片</div>
					</div>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">登录页图片:</label>
				<div class="x-form-element">
					<div id="loginDiv">
					    <div class="filelist"><img src="<%=path%><%=SysConfigCache.getInstance().findValue("admin_login_url") %>" style="height:250px;"/></div>
					    <div id="loginBtn">选择图片</div>
					</div>
				</div>
			</div>
			
			
		</form>
	</div>