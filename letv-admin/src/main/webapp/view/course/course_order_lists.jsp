<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript" src="<%=path%>/js/course/course_order_list.js"></script>
	<form id="list_search">
		<div class="seach_div">
			<div><label>用户名:</label><input name="userId" type="text"/></div>
			<div><label>预约课程:</label><input name="coursename" type="text"/></div>
			<div class="psb" style="float:right;"><a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a><a id="resetBtn" class="easyui-linkbutton searchBtn">重置</a></div>
		</div>
	</form>
	<table id="courseTable"></table>
	<div id="detail" style="display: none;">
		<fieldset style="width: 350px; float:left; display: inline;">
			<legend>基本信息</legend>
		                用户名: <label id="userid"></label> <br/>
		                用户真实姓名: <label id="realname"></label> <br/>
		                用户手机号: <label id="mobile"></label> <br/>
		                预约课程: <label id="coursename"></label> <br/>
		                课程讲师: <label id="teachername"></label> <br/>
		                课程开始时间: <label id="startdate"></label> <br/>
		                课程结束时间: <label id="enddate"></label> <br/>
		                预约时间: <label id="ordertime"></label> <br/>
  		</fieldset>
	</div>
	
