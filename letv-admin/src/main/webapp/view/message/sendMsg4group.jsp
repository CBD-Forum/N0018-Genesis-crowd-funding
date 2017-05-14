<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<script type="text/javascript" charset="utf-8" src="<%=path %>/js/common/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=path %>/js/common/ueditor/ueditor.all.js"></script>
<script type="text/javascript" src="<%=path%>/js/message/sendMsg4group.js"></script>
	<form id="list_search">
		<div class="seach_div">
			<div><label>用户名:</label><input name="userId" type="text"/></div>
			<div><label>真实姓名:</label><input name="realName" type="text"/></div>
			<div><label>手机号:</label><input name="mobile" type="text"/></div>
			<div><label>邮箱:</label><input name="email" type="text"/></div>
			<div><label>状态:</label><input id="status" name="status" class="easyui-combobox" url="<%=path %>/dictionary/user_state.html" panelHeight="auto"/></div>
			<div><label>注册时间:</label><input type="text" name="startCreateTime" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/> &nbsp;~ <input type="text" name="endCreateTime" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/></div>
			<div class="psb" style="float:right;"><a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a><a id="reset" class="easyui-linkbutton searchBtn">重置</a></div>
		</div>
	</form>
	<table id="userTable"></table>
	
	<div id="sendSMS" class="add">
		<form id="sendSMSForm" method="post">
			<div class="x-form-item">
				<label class="x-form-item-label">发送内容:</label>
				<div class="x-form-element">
					<textarea rows="5" name="SMSContent" placeholder="消息内容"></textarea>
				</div>
			</div>
			
			<div class="psb">
				<a id="sendSMSBtn" class="easyui-linkbutton searchBtn">发送</a><a id="sendSMSCloseBtn" class="easyui-linkbutton searchBtn">取消</a>
			</div>
		</form>
	</div>
	
	<div id="sendEmail" class="add">
		<form id="sendEmailForm" method="post">
			<div class="x-form-item">
				<label class="x-form-item-label">主题:</label>
				<div class="x-form-element">
					<input name="subject" type="text"/>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">发送内容:</label>
				<div class="x-form-element">
					<script id="emailContent" type="text/plain" style="width:720px;height:310px;"></script>
				</div>
			</div>
			<div class="psb">
				<a id="sendEmailBtn" class="easyui-linkbutton searchBtn">发送</a><a id="sendEmailCloseBtn" class="easyui-linkbutton searchBtn">取消</a>
			</div>
		</form>
	</div>
	
	<div id="sendStation" class="add">
		<form id="sendStationForm" method="post">
			<div class="x-form-item">
				<label class="x-form-item-label">发送内容:</label>
				<div class="x-form-element">
					<textarea rows="10" placeholder="站内信内容不能超过60个字！" name="StationContent" id="StationContent" placeholder="内容"></textarea>
				</div>
			</div>
			
			<div class="psb">
				<a id="sendStationBtn" class="easyui-linkbutton searchBtn">发送</a><a id="sendStationCloseBtn" class="easyui-linkbutton searchBtn">取消</a>
			</div>
		</form>
	</div>
	
	<div id="mmSMS" style="width:130px;">
		<div onclick="showSendSMSWin('select')">查询条件下用户</div>
		<div onclick="showSendSMSWin('one')">单个用户</div>
		<div onclick="showSendSMSWin('all')">所有注册用户</div>
	</div>
	
	<div id="mmEmail" style="width:130px;">
		<div onclick="showSendEmailWin('select')">查询条件下用户</div>
		<div onclick="showSendEmailWin('one')">单个用户</div>
		<div onclick="showSendEmailWin('all')">所有注册用户</div>
	</div>
	
	<div id="mmStation" style="width:130px;">
		<div onclick="showSendStationWin('select')">查询条件下用户</div>
		<div onclick="showSendStationWin('one')">单个用户</div>
		<div onclick="showSendStationWin('all')">所有注册用户</div>
	</div>