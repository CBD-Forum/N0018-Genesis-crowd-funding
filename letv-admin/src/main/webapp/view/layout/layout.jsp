<!DOCTYPE html>
<%@page import="com.fbd.core.common.utils.SysConfigCache"%>
<%@page import="com.fbd.core.app.user.model.AdminUserDetails"%>
<%@page import="com.fbd.admin.web.MyRequestContextHolder"%>
<%@page import="org.springframework.security.core.context.SecurityContextImpl"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.fbd.admin.security.AdminRoleGrantedAuthority"%>
<%@page import="org.springframework.security.core.GrantedAuthority"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ include file="./common_jquery.jsp"%>
<%
List<String> list = new ArrayList<String>();
List<String> topMenuList = new ArrayList<String>();
AdminUserDetails adminUser = MyRequestContextHolder.getCurrentUser();
for (GrantedAuthority authority :  adminUser.getAuthorities()) {
	if (authority instanceof AdminRoleGrantedAuthority) {
		AdminRoleGrantedAuthority roleAuthority = (AdminRoleGrantedAuthority) authority;
		list.add("{\"roleCode\":\""+roleAuthority.getRoleCode()+"\",\"roleName\":\""+roleAuthority.getRoleName()+"\",\"resourceCode\":\""+roleAuthority.getResourceCode()+"\",\"resourceName\":\""+roleAuthority.getResourceName()+"\",\"resourceUrl\":\""+roleAuthority.getResourceUrl()+"\",\"resourcePcode\":\""+roleAuthority.getResourcePcode()+"\"}");
		//顶部菜单 url为空
		//资源编码
		String resourceCode=roleAuthority.getResourceCode();
		//资源URL
		String resourcePcode=roleAuthority.getResourcePcode();
		
		if("mana".equals(resourcePcode)&&resourceCode!=null&&!"".equals(resourceCode)){
		    topMenuList.add("{\"id\":\""+roleAuthority.getResourceCode()+"\",\"text\":\""+roleAuthority.getResourceName()+"\"}");
		}
	}
}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 


<script type="text/javascript">
	var authorities = '<%=list.toString()%>';
	var topMenu = '<%=topMenuList.toString()%>';
	var fileUrl = '<%=SysConfigCache.getInstance().findValue("file_url") %>';
	var userWebUrl = '<%=SysConfigCache.getInstance().findValue("user_web_url") %>';
</script> 	
<script type="text/javascript" src="<%=path%>/js/layout/layout.js"></script>
<title><%=SysConfigCache.getInstance().findValue("admin_tilte_info") %></title>
<meta name="keywords" content="<tiles:insertAttribute name="keyword"/>" />
<meta name="description" content="<tiles:insertAttribute name="description"/>" />
<style>
.logo{height:60px;line-height:60px;float:left;background:url(<%=path%><%=SysConfigCache.getInstance().findValue("admin_logo_url") %>) no-repeat left center;font-size:30px;color:#BA8B18;font-family:"隶书";padding-left:220px;font-weight:bold;}
</style>
</head>
<body style="overflow:hidden;">
	<div class="box">
		<div class="main_t">
			<div class="logo"><%=SysConfigCache.getInstance().findValue("admin_tilte_info") %></div>
			<div class="logout">
				欢迎您：<span><%=adminUser.getAdminId() %></span>
				<a class="easyui-linkbutton c8" style="width:80px;" id="logout">退出</a>
			</div>
		</div>
	</div>
	<!-- 顶部菜单 -->
	<div class="main_head">
		<div class="nav_box">
			<ul id="nav"></ul>
		</div>
	</div>
	<div class="mpath" id="nav1" style="padding-left: 150px;"></div>
	<div id="main">
		<div id="main_snav"></div>
		<div id="main_body">
			<tiles:insertAttribute name="content"></tiles:insertAttribute>
		</div>
	</div>
</body>
</html>